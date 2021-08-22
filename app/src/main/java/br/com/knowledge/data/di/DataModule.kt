package br.com.knowledge.data.di

import android.util.Log
import br.com.knowledge.data.database.AppDataBase
import br.com.knowledge.data.repository.KnowledgeRepository
import br.com.knowledge.data.repository.KnowledgeRepositoryImp
import br.com.knowledge.data.services.LoginServices
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DataModule {
    private const val BASE_URL_EMULADOR = "http://10.0.2.2:4000"
    private const val BASE_URL_MOTOG = "http://192.168.15.10:4000"
    private const val HTTP_TAG = "okHTTP"

    fun load() {
        loadKoinModules(networkModule() + repositoryModule() + databaseModule())
    }

    private fun databaseModule(): Module {
        return module {
            single {
                AppDataBase.getInstance(androidApplication())
            }
        }
    }

    private fun repositoryModule(): Module {
        return module {
            single<KnowledgeRepository> {
                KnowledgeRepositoryImp(get(), get())
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
            .baseUrl(BASE_URL_MOTOG)
            .client(client)
            .addConverterFactory(factory)
            .build()
            .create(T::class.java)
    }
}