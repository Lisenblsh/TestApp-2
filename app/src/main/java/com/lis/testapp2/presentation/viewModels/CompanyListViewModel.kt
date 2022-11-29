package com.lis.testapp2.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.lis.domain.converters.ResponseToModel
import com.lis.domain.models.CompanyListModel
import com.lis.domain.models.CompanyListModelItem
import com.lis.domain.pagingSource.CompanyPagingSource
import kotlinx.coroutines.flow.Flow

class CompanyListViewModel(private val responseToModel: ResponseToModel) : ViewModel() {

    val companyPagingData: Flow<PagingData<CompanyListModelItem>>

    init {
        companyPagingData = getCompanyList()
    }

    private fun getCompanyList(): Flow<PagingData<CompanyListModelItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false,
                initialLoadSize = INITIAL_SIZE
            )
        ) {
            CompanyPagingSource(responseToModel)
        }.flow
    }

    companion object {
        const val PAGE_SIZE = 10
        const val INITIAL_SIZE = 20
    }

}