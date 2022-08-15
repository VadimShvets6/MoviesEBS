package com.top1shvetsvadim1.moviesebs.utils

sealed class ResultOperator {
    data class Error(val exception: Exception) : ResultOperator()
}