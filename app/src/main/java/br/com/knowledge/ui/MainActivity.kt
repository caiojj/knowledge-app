package br.com.knowledge.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.knowledge.core.extensions.createDialog
import br.com.knowledge.core.extensions.createProgressDialog
import br.com.knowledge.core.extensions.hideSoftKeyboard
import br.com.knowledge.data.model.RequestLogin
import br.com.knowledge.databinding.ActivityMainBinding
import br.com.knowledge.presentation.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val viewModel by viewModel<MainViewModel>()
    private val dialog by lazy { createProgressDialog() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        bindingListeners()
        bindingObserver()
    }

    private fun bindingObserver() {
        viewModel.state.observe(this) {
            when(it) {
                MainViewModel.State.Loading -> dialog.show()
                is MainViewModel.State.Logged -> {
                    dialog.dismiss()
                    if (it.body.isSuccessful) {
                        val intent = Intent(this, ArticlesActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        createDialog {
                            setMessage("${it.body.errorBody()?.charStream()?.readText()}")
                        }.show()
                    }
                }
                is MainViewModel.State.Error -> {
                    dialog.dismiss()
                    createDialog {
                        setMessage("${it.error}")
                    }.show()
                }
                MainViewModel.State.Saved -> {
                    dialog.dismiss()
                    createDialog {
                        setMessage("Contéudo savo com sucesso.")
                    }
                }
            }
        }
    }

    private fun bindingListeners() {

        binding.btnLogin.setOnClickListener {
            it.hideSoftKeyboard()
            val email = binding.tieEmail.text.toString()
            val password = binding.tiePassword.text.toString()
            val login = RequestLogin(email, password)
            viewModel.executeLogin(login)
        }

        binding.cpForgotPassword.setOnClickListener {
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }

        binding.tvCreateAccounter.setOnClickListener {
            val intent = Intent(this@MainActivity, CreateAccountActivity::class.java)
            startActivity(intent)
        }
    }
}