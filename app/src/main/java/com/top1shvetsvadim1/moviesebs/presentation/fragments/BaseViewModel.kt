package com.top1shvetsvadim1.moviesebs.presentation.fragments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.top1shvetsvadim1.moviesebs.utils.ResultOperator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.UnknownHostException

abstract class BaseViewModel : ViewModel() {

//    protected val _error: MutableLiveData<Exception> = MutableLiveData()
//    val error: LiveData<Exception>
//        get() = _error

    val error: MutableLiveData<ResultOperator> = MutableLiveData()

    protected inline fun <T> CoroutineScope.launchSafe(
        successLiveData: MutableLiveData<T>,
        crossinline launch: suspend CoroutineScope.() -> Flow<T>
    ): Job {
        return launch(Dispatchers.IO) {
            try {
                launch().collectLatest {
                    successLiveData.postValue(it)
                }
            } catch (ex: Exception) {
                error.postValue(ResultOperator.Error(ex))
            } catch (ex: UnknownHostException) {
                error.postValue(ResultOperator.Error(ex))
            } catch (ex: HttpException) {
                error.postValue(ResultOperator.Error(ex))
            }
        }
    }

    protected inline fun <T> CoroutineScope.launchDetail(
        crossinline launch: suspend CoroutineScope.() -> T
    ): Job {
        return launch(Dispatchers.IO) {
            try {
                launch()
            } catch (ex: Exception) {
                when (ex) {
                    is UnknownHostException -> error.postValue(ResultOperator.Error(ex))
                }
            }
        }
    }
}