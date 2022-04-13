package br.com.exampleviewstate

import android.app.Application
import br.com.exampleviewstate.data.DataSource
import br.com.exampleviewstate.data.IDataSource
import org.koin.android.ext.android.get
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(
                listOf(
                    module { single { DataSource() as IDataSource } },
                    module {
                        viewModel { LoginViewModel(get()) }
                    }
                )
            )
        }
    }
}