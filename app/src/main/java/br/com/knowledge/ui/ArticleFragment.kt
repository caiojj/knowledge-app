package br.com.knowledge.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.com.knowledge.databinding.FragmentArticleBinding
import br.com.knowledge.presentation.MainViewModel
import br.com.knowledge.presentation.State
import org.koin.androidx.viewmodel.ext.android.viewModel

class ArticleFragment : Fragment() {

    private val binding by lazy { FragmentArticleBinding.inflate(layoutInflater) }
    private val adapter by lazy { ArticlesListAdapter() }
    private val viewModel by viewModel<MainViewModel>()
    private var token: String? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        binding.rvArticles.adapter = adapter
        bindingObserver()
        authenticationRequest()
        bindingListeners()
        return binding.root
    }

    private fun bindingListeners() {
        adapter.readArticleListener = {
            val intent = Intent(context, ReadArticleActivity::class.java)
            intent.putExtra("id", it.id)
            intent.putExtra("title", it.name)
            intent.putExtra("token", token)
            startActivity(intent)
        }
    }

    private fun authenticationRequest() {
        viewModel.getToken()
    }

    private fun bindingObserver() {
        viewModel.state.observe(viewLifecycleOwner) {
            when(it) {
                State.LoadingAllArticles -> {
                    binding.pbArticles.visibility = View.VISIBLE
                }
                is State.ObtainedUser -> {

                }
                is State.ObtainedArticles -> {
                    if(it.articles.isSuccessful) {
                        binding.pbArticles.visibility = View.GONE
                        adapter.submitList(it.articles.body()?.data)
                    }
                }
                is State.Error -> {
                    // Tratar erros
                }
                is State.ObtainedToken -> {
                    it.token.let { token ->
                        this.token = token
                        viewModel.getArticles(token!!)
                    }
                }
                else -> {}
            }
        }
    }
}