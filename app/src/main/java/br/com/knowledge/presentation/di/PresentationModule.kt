package br.com.knowledge.presentation.di

import br.com.knowledge.presentation.ArticlesViewModel
import br.com.knowledge.presentation.MainViewModel
import br.com.knowledge.presentation.CreateAccountViewModel
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
            viewModel { MainViewModel(get(), get()) }
            viewModel { CreateAccountViewModel(get()) }
            viewModel { ArticlesViewModel(get()) }
        }
    }
}