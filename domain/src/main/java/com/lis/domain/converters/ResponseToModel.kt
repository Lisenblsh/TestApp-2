package com.lis.domain.converters

import com.lis.domain.HttpException
import com.lis.domain.Repository
import com.lis.domain.models.CompanyListModel
import com.lis.domain.models.CompanyModelItem

class ResponseToModel(private val repository: Repository) {
    suspend fun getCompanyList(): CompanyListModel {
        val response = repository.getCompanyList()
        if (response.isSuccessful) {
            return checkNotNull(response.body())
        }
        throw HttpException(response.message(), response.code())
    }

    suspend fun getCompanyData(id: String): CompanyModelItem {
        val response = repository.getCompanyData(id)
        if (response.isSuccessful) {
            return checkNotNull(response.body()?.get(0))
        }
        throw HttpException(response.message(), response.code())
    }
}