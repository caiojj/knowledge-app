package br.com.knowledge.data.di

import android.util.Log
import br.com.knowledge.data.repository.KnowledgeRepository
import br.com.knowledge.data.repository.KnowledgeRepositoryImp
import br.com.knowledge.data.services.LoginServices
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DataModule {
    private const val HTTP_TAG = "okHTTP"

    fun load() {
        loadKoinModules(networkModule() + repositoryModule())
    }

    private fun repositoryModule(): Module {
        return module {
            single<KnowledgeRepository> {
                KnowledgeRepositoryImp(get())
            }
        }
    }

    private fun networkModule(): Module {
        return module {
            single {
                val interceptor = HttpLoggingInterceptor {
                    Log.e(HTTP_TAG, "$it")
                }
                interceptor.level = HttpLoggingInterceptor.Level.BODY

                OkHttpClient
                    .Builder()
                    .addInterceptor(interceptor)
                    .build()
            }

            single {
                GsonConverterFactory.create(GsonBuilder().create())
            }

            single {
                createService<LoginServices>(get(), get())
            }
        }
    }

    private inline fun <reified T> createService(client: OkHttpClient, factory: GsonConverterFactory): T {
        return Retrofit
            .Builder()
            .baseUrl("http://10.0.2.2:4000")
            .client(client)
            .addConverterFactory(factory)
            .build()
            .create(T::class.java)
    }
}