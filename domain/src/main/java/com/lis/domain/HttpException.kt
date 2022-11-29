package com.lis.domain

class HttpException(
    override val message: String,
    val code: Int,
) : Exception(message)