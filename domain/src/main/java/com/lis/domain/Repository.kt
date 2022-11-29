package com.lis.domain

import com.lis.domain.models.CompanyListModel
import com.lis.domain.models.CompanyModel
import retrofit2.Response

interface Repository {
    suspend fun getCompanyList(): Response<CompanyListModel>

    suspend fun getCompanyData(id: String): Response<CompanyModel>
}