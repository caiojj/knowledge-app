package br.com.knowledge.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.knowledge.data.model.ActiveUser
import br.com.knowledge.presentation.MainViewModel
import br.com.knowledge.databinding.FragmentProfileBinding
import br.com.knowledge.presentation.State
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : Fragment() {

    private val binding by lazy { FragmentProfileBinding.inflate(layoutInflater) }
    private val viewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingObserver()
        viewModel.getActiveUser()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?{
        return binding.root
    }

    private fun initComponents(activeUser: ActiveUser) {
        binding.tvName.text = activeUser.name
        binding.tvUserName.text = "@caiojj"
    }

    private fun bindingObserver() {
        viewModel.state.observe(this) {
            when(it) {
                State.Loading -> {
                    
                }
                is State.User -> {
                    initComponents(it.activeUser.first())
                }
                is State.Error -> {
                    // tratar erro
                }
                is State.Success -> {}
            }
        }
    }

}