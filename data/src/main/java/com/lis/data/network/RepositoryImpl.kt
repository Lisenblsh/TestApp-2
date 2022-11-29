package com.lis.data.network

import com.lis.domain.Repository
import com.lis.domain.models.CompanyListModel
import com.lis.domain.models.CompanyModel
import retrofit2.Response

class RepositoryImpl(private val service: RetrofitService) : Repository {
    override suspend fun getCompanyList(): Response<CompanyListModel> {
        return service.getCompanyList()
    }

    override suspend fun getCompanyData(id: String): Response<CompanyModel> {
        return service.getCompanyData(id)
    }
}