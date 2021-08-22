package br.com.knowledge.ui

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.knowledge.R
import br.com.knowledge.core.extensions.createDialog
import br.com.knowledge.core.extensions.createProgressDialog
import br.com.knowledge.data.module.AccountData
import br.com.knowledge.databinding.ActivityCreateAccountBinding
import br.com.knowledge.presentation.CreateAccountViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CreateAccountActivity : AppCompatActivity() {

    private val binding by lazy { ActivityCreateAccountBinding.inflate(layoutInflater) }
    private val viewModel by viewModel<CreateAccountViewModel>()
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
                CreateAccountViewModel.State.Loading -> dialog.show()
                is CreateAccountViewModel.State.Success -> {
                    dialog.dismiss()

                    if (it.msg.isSuccessful) {
                        createDialog {
                            setMessage("Cadastro realizado com sucesso, por favor faça login.")
                            setPositiveButton("ok") { _, _ ->
                                finish()
                            }
                        }.show()
                    } else {
                        createDialog {
                            setMessage("código:${it.msg.code()} : ${it.msg.errorBody()?.charStream()?.readText()}")
                        }.show()
                    }
                }
                is CreateAccountViewModel.State.Error -> {
                    dialog.dismiss()
                    createDialog {
                        setMessage("Por favor verifique sua conexão.")
                    }.show()
                }
            }
        }
    }

    private fun bindingListeners() {
        binding.btnCreateAccounter.setOnClickListener {
            val name = binding.tieName.text.toString()
            val email = binding.tieEmail.text.toString()
            val password = binding.tiePassword.text.toString()
            val confirmPassword = binding.tieConfirmPassword.text.toString()

            val newAccount = AccountData(
                name = name,
                email = email,
                password = password,
                confirmPassword = confirmPassword
            )

            viewModel.createAccount(newAccount)
        }
    }
}