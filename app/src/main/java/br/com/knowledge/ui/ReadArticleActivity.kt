package br.com.knowledge.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import br.com.knowledge.core.extensions.createDialog
import br.com.knowledge.core.extensions.createProgressDialog
import br.com.knowledge.databinding.ActivityReadArticleBinding
import br.com.knowledge.presentation.ReadArticleViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ReadArticleActivity : AppCompatActivity() {

    private val binding by lazy { ActivityReadArticleBinding.inflate(layoutInflater) }
    private val dialog by lazy { createProgressDialog() }
    private val viewModel by viewModel<ReadArticleViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        bindingObserver()
        getContentArticleApi()
    }

    private fun getContentArticleApi() {
        val id = intent.getLongExtra("id", 0)
        val token = intent.getStringExtra("token") ?: ""
        viewModel.getContentArticle(token, id)
    }

    private fun initComponent(content: String) {
        binding.tvTitleArticle.text = intent.getStringExtra("title")
        binding.tvContentArticle.text = content
    }

    private fun bindingObserver() {
        viewModel.state.observe(this) {
            when(it) {
                ReadArticleViewModel.State.Loading -> dialog.show()
                is ReadArticleViewModel.State.Error -> {
                    dialog.dismiss()
                    createDialog {
                        setMessage(it.error.message)
                    }.show()
                }
                is ReadArticleViewModel.State.ObtainedContent -> {
                    dialog.dismiss()
                    if(it.content.isSuccessful) {
                        it.content.body()?.let { contentArticle ->
                            initComponent(contentArticle.content)
                        }
                    } else {
                        Log.e("Error", "${it.content.errorBody()}")
                    }
                }
            }
        }
    }
}
