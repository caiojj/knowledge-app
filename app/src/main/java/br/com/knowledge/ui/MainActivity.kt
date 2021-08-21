package br.com.knowledge.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.knowledge.core.extensions.createDialog
import br.com.knowledge.core.extensions.createProgressDialog
import br.com.knowledge.core.extensions.hideSoftKeyboard
import br.com.knowledge.data.module.Login
import br.com.knowledge.databinding.ActivityMainBinding

import br.com.knowledge.domain.MainViewModel
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
                    createDialog {
                        setMessage("Login bem sucedido ${it.body.name}")
                    }.show()
                }
                is MainViewModel.State.Error -> {
                    dialog.dismiss()
                    createDialog {
                        setMessage("${it}")
                    }.show()
                }
            }
        }
    }

    private fun bindingListeners() {

        binding.btnLogin.setOnClickListener {
            it.hideSoftKeyboard()
            val email = binding.tieEmail.text.toString()
            val password = binding.tiePassword.text.toString()
            val login = Login(email, password)
            viewModel.executeLogin(login)
        }

        binding.cpForgotPassword.setOnClickListener {

        }

        binding.tvCreateAccounter.setOnClickListener {
            val intent = Intent(this@MainActivity, CreateAccountActivity::class.java)
            startActivity(intent)
        }
    }
}