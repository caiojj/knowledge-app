package br.com.knowledge.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.knowledge.core.extensions.createDialog
import br.com.knowledge.core.extensions.createProgressDialog
import br.com.knowledge.core.extensions.hideSoftKeyboard
import br.com.knowledge.data.model.ActiveUser
import br.com.knowledge.data.model.RequestLogin
import br.com.knowledge.databinding.ActivityLoginBinding
import br.com.knowledge.presentation.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {

    private val BEARER = "bearer "
    private val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }
    private val viewModel by viewModel<LoginViewModel>()
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
                LoginViewModel.State.Loading -> dialog.show()
                is LoginViewModel.State.Logged -> {
                    if(it.body.isSuccessful) {
                        viewModel.insert(ActiveUser(
                            it.body.body()!!.id,
                            it.body.body()!!.name,
                            it.body.body()!!.email,
                            it.body.body()!!.admin,
                            BEARER + it.body.body()!!.token
                        ))
                    } else {
                        dialog.dismiss()
                        createDialog {
                            setMessage("${it.body.errorBody()?.charStream()?.readText()}")
                        }.show()
                    }
                }
                is LoginViewModel.State.Error -> {
                    dialog.dismiss()
                    createDialog {
                        setMessage("${it.error}")
                    }.show()
                }
                LoginViewModel.State.Saved -> {
                    dialog.dismiss()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
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
            val intent = Intent(this@LoginActivity, CreateAccountActivity::class.java)
            startActivity(intent)
        }
    }
}