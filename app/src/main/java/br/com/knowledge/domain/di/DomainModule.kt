package br.com.knowledge.domain.di

import br.com.knowledge.domain.*
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
            factory { GetActiveUserUseCase(get()) }
            factory { SaveActiveUserUseCase(get()) }
            factory { DeleteActiveUserUseCase(get()) }
            factory { GetArticlesUseCase(get()) }
        }
    }
}