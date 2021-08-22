package br.com.knowledge.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.knowledge.core.extensions.createDialog
import br.com.knowledge.core.extensions.createProgressDialog
import br.com.knowledge.databinding.ActivityArticlesBinding
import br.com.knowledge.presentation.ArticlesViewModel
import br.com.knowledge.presentation.State
import org.koin.androidx.viewmodel.ext.android.viewModel

class ArticlesActivity : AppCompatActivity() {

    private val binding by lazy { ActivityArticlesBinding.inflate(layoutInflater) }
    private val adapter by lazy { ArticlesListAdapter() }
    private val dialog by lazy { createProgressDialog() }
    private val viewModel by viewModel<ArticlesViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.rvArticles.adapter = adapter
        bindingObserver()
        viewModel.getArticles("bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6MTksIm5hbWUiOiJMdWNhcyBMZWFsIiwiZW1haWwiOiJsdWNhMUBnbWFpbC5jb20iLCJhZG1pbiI6ZmFsc2UsImlhdCI6MTYyOTU5NTczODg1NywiZXhwIjoxNjI5NTk4NDE3MjU3fQ.hq_sp82uapcwc-0PMNe7UdnX67FtyHQ0u6eV2SLSh0M")
    }

    private fun bindingObserver() {
        viewModel.state.observe(this) {
            when(it) {
                State.Loading -> dialog.show()
                is State.Success -> {
                    dialog.dismiss()
                    adapter.submitList(it.articles.body()?.data)
                    createDialog {
                        setMessage("${it}")
                    }
                }
                is State.Error -> {
                    dialog.dismiss()
                    createDialog {
                        setMessage("Errro ao tentar acessar o servidor ${it.error.message}")
                    }
                }
            }
        }
    }
}