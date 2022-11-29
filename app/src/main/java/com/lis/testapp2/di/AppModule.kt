package com.lis.testapp2.di

import com.lis.testapp2.presentation.viewModels.CompanyListViewModel
import com.lis.testapp2.presentation.viewModels.CompanyViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel<CompanyViewModel> {
        CompanyViewModel(responseToModel = get())
    }

    viewModel<CompanyListViewModel>{
        CompanyListViewModel(responseToModel = get())
    }
}