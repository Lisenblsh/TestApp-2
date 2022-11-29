package com.lis.testapp2.di

import com.lis.data.network.RepositoryImpl
import com.lis.data.network.RetrofitService
import com.lis.domain.Repository
import org.koin.dsl.module

val dataModule = module {
    single<RetrofitService> {
        RetrofitService.create()
    }

    single<Repository> {
        RepositoryImpl(service = get())
    }
}