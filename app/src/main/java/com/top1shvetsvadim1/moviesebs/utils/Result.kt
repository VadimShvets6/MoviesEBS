package com.top1shvetsvadim1.moviesebs.utils

//TODO: strange sealed class.
sealed class ResultOperator {
    data class Error(val exception: Exception) : ResultOperator()
}