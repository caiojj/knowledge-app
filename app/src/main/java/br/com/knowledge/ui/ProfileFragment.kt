package br.com.knowledge.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import br.com.knowledge.R
import br.com.knowledge.data.model.ActiveUser
import br.com.knowledge.presentation.MainViewModel
import br.com.knowledge.databinding.FragmentProfileBinding
import br.com.knowledge.presentation.State
import com.google.android.material.bottomsheet.BottomSheetDialog
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment: Fragment() {

    private val binding by lazy { FragmentProfileBinding.inflate(layoutInflater) }
    private val viewModel by viewModel<MainViewModel>()
    private val adapter by lazy { ArticlesListAdapter() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?{
        bindingObserver()
        viewModel.getActiveUser()
        bindingListeners()
        viewModel.getToken()
        binding.rvMyArticles.adapter = adapter
        return binding.root
    }

    private fun bindingListeners() {
        binding.ivLogout.setOnClickListener {
            viewModel.getEmail()
        }

        binding.ivProfile.setOnLongClickListener {
            showSheetBottomDialog()
            true
        }
    }

    private fun showSheetBottomDialog() {
        val dialog = BottomSheetDialog(this.requireContext(), R.style.AppBottomSheetDialogTheme)
        val inflate = layoutInflater.inflate(R.layout.sheets_bottom_profile, null)
        dialog.setContentView(inflate)

        val tvEditProfile = dialog.findViewById(R.id.tv_edit_profile) as TextView?

        /**
        * Events
         * */
        tvEditProfile?.setOnClickListener {
            val intent = Intent(context, EditProfileActivity::class.java)
            startActivity(intent)
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun initComponents(activeUser: ActiveUser) {
        binding.tvName.text = activeUser.name
        binding.tvUserName.text = "@caiojj"
    }
    private fun bindingObserver() {
        viewModel.state.observe(viewLifecycleOwner) {
            when(it) {
                is State.Error -> {}
                is State.ObtainedToken -> {}
                State.LoadingAllArticles -> {}
                is State.ObtainedArticles -> {}
                State.LoadingMyArticles -> {
                    binding.pbProfile.visibility = View.VISIBLE
                }
                State.Deleted -> {
                    val intent = Intent(context, LoginActivity::class.java)
                    startActivity(intent)
                    activity?.finish()
                }
                is State.ObtainedEmail -> {
                    Log.e("ObtainedEmail", "$it")
                    if (it.email != null)
                        viewModel.deleteActivityUser(it.email)
                }
                is State.ObtainedMyArticles -> {
                    if (it.articles.isSuccessful) {
                        binding.pbProfile.visibility = View.GONE
                        adapter.submitList(it.articles.body()?.data)
                    } else {
                        Log.e("ERRO", "Erro na requeisição ${it.articles.errorBody()?.charStream()?.readText()}")
                    }
                }
                is State.ObtainedUser -> {
                    if (it.activeUser.firstOrNull() != null) {
                        val user = it.activeUser.first()
                        initComponents(user)
                        viewModel.getMyArticles(user.token, user.id)
                    }
                }
            }
        }
    }
}