package br.com.knowledge.ui

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.rvArticles.adapter = adapter
        bindingObserver()
        authenticationRequest()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        return binding.root
    }

    private fun authenticationRequest() {
        viewModel.getToken()
    }

    private fun bindingObserver() {
        viewModel.state.observe(this) {
            when(it) {
                State.LoadingAllArticles -> {
                    binding.pbArticles.visibility = View.VISIBLE
                }
                is State.ObtainedArticles -> {
                    if(it.articles.isSuccessful) {
                        binding.pbArticles.visibility = View.GONE
                        adapter.submitList(it.articles.body()?.data)
                    }
                }
                is State.ObtainedToken -> {
                    it.token.let { token ->
                        viewModel.getArticles(token!!)
                    }
                }
                else -> {}
            }
        }
    }
}