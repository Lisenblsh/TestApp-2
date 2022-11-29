package com.lis.domain.models

class CompanyListModel : ArrayList<CompanyListModelItem>()


data class CompanyListModelItem(
    val id: String,
    val img: String,
    val name: String
)