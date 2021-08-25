package br.com.knowledge.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import br.com.knowledge.core.extensions.createDialog
import br.com.knowledge.databinding.ActivitySplashScreenBinding
import br.com.knowledge.presentation.SplashScreenViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashScreenActivity : AppCompatActivity() {

    private val binding by lazy { ActivitySplashScreenBinding.inflate(layoutInflater) }
    private val viewModel by viewModel<SplashScreenViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        bindingObserver()
        viewModel.getToken()
    }

    private fun openingProcessing(token: String?) {
        Handler(Looper.getMainLooper()).postDelayed({
            loginOrArticles(token)
        }, 2000)
    }

    private fun loginOrArticles(token: String?) {
        if(token.isNullOrEmpty()) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        } else {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        finish()
    }

    private fun bindingObserver() {
        viewModel.state.observe(this) {
            when(it) {
                is SplashScreenViewModel.State.ObtainedToken -> {
                    openingProcessing(it.token.firstOrNull())
                }
                is SplashScreenViewModel.State.Error -> {
                    createDialog {
                        setMessage("${it.error.message}")
                    }
                }
            }
        }
    }
}
