package com.lis.testapp2.di

import com.lis.domain.converters.ResponseToModel
import org.koin.dsl.module

val domainModule = module {
    factory<ResponseToModel> {
        ResponseToModel(repository = get())
    }
}