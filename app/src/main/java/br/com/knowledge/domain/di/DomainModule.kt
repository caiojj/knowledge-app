package br.com.knowledge.domain.di

import br.com.knowledge.domain.CreateAccountUseCase
import br.com.knowledge.domain.ResponseLoginUseCase
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

object DomainModule {

    fun load() {
        loadKoinModules(useCaseModules())
    }

    private fun useCaseModules(): Module {
        return module {
            factory { ResponseLoginUseCase(get()) }
            factory { CreateAccountUseCase(get()) }
        }
    }
}