package com.longtq.contact_manager.di

import com.longtq.contact_manager.data.database.AppDatabase
import com.longtq.contact_manager.data.database.sqldelight.Database
import com.longtq.contact_manager.data.database.sqldelight.DatabaseDriverFactory
import com.longtq.contact_manager.data.database.sqldelight.DatabaseImpl
import com.longtq.contact_manager.data.local.AppLocalDatabase
import com.longtq.contact_manager.data.local.FileReader
import com.longtq.contact_manager.data.local.LocalDatabaseImpl
import com.longtq.contact_manager.data.repository.ContactRepositoryImpl
import com.longtq.contact_manager.domain.repository.IContactRepository
import com.longtq.contact_manager.domain.usecase.AddContactUserCase
import com.longtq.contact_manager.domain.usecase.DeleteContactUseCase
import com.longtq.contact_manager.domain.usecase.GetAllContactsUseCase
import com.longtq.contact_manager.domain.usecase.GetFileFromAssetsUseCase
import com.longtq.contact_manager.domain.usecase.ReadContactFromJsonUseCase
import com.longtq.contact_manager.domain.usecase.SearchContactUseCase
import com.longtq.contact_manager.presentation.screens.add_contact.AddContactViewModel
import com.longtq.contact_manager.presentation.screens.home.HomeViewModel
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(
            viewModelModule,
            dataLocalModule,
            useCasesModule,
            repositoryModule,
            sqlDelightModule,
            dataBaseDriverFactory,

            )

    }

var viewModelModule: Module = module {
    factory { HomeViewModel(get(), get(), get(), get(), get(), get()) }
    factory { AddContactViewModel(get()) }
}

val useCasesModule = module {
    factory { AddContactUserCase(get()) }
    factory { GetAllContactsUseCase(get()) }
    factory { DeleteContactUseCase(get()) }
    factory { SearchContactUseCase(get()) }
    factory { GetFileFromAssetsUseCase(get()) }
    factory { ReadContactFromJsonUseCase(get()) }
}

val repositoryModule = module {
    single<IContactRepository> { ContactRepositoryImpl(get(), get()) }
    single<AppDatabase> { DatabaseImpl(get()) }
    single<AppLocalDatabase> { LocalDatabaseImpl(get()) }
}

val sqlDelightModule = module {
    single { Database(get()) }
}

val dataBaseDriverFactory = module {
    single { DatabaseDriverFactory() }
}

val dataLocalModule = module {
    single { FileReader() }
}


fun initKoin() = initKoin {}
