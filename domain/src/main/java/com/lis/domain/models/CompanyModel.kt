package com.lis.domain.models

class CompanyModel : ArrayList<CompanyModelItem>()

data class CompanyModelItem(
    val description: String,
    val id: String,
    val img: String,
    val lat: Double,
    val lon: Double,
    val name: String,
    val phone: String,
    val www: String
)