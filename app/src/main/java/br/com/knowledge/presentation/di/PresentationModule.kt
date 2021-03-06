package br.com.knowledge.presentation.di

import br.com.knowledge.presentation.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

object PresentationModule {

    fun load() {
        loadKoinModules(viewModelModules())
    }

    private fun viewModelModules(): Module {
        return module {
            viewModel { LoginViewModel(get(), get()) }
            viewModel { CreateAccountViewModel(get()) }
            viewModel { MainViewModel(get(), get(), get(), get(), get(), get()) }
            viewModel { SplashScreenViewModel(get()) }
            viewModel { EditProfileViewModel(get(), get(), get()) }
            viewModel { ReadArticleViewModel(get()) }
        }
    }
}