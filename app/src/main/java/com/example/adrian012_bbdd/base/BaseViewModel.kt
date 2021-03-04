package com.example.adrian012_bbdd.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.adrian012_bbdd.base.util.BaseExtraData
import com.example.adrian012_bbdd.base.util.BaseState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.lang.Exception


abstract class BaseViewModel<VS: BaseViewState>: ViewModel() {
    private var baseState: BaseState<VS>? = null
    private val observableState: MutableLiveData<BaseState<VS>> = MutableLiveData()
    fun getObservableState(): LiveData<BaseState<VS>> = observableState

    /**
     * OnStart 1st time. Check if this fragment has run for the 1st time
     */
    fun onStart() {
        if (baseState == null) {
            baseState = BaseState.Normal(defaultState)
            onStartFirstTime()
        }

        onResume()
        observableState.postValue(baseState)
    }

    abstract val defaultState: VS
    abstract fun onStartFirstTime()
    open fun onResume() { }  // It executes every time the fragment is created

    /**
     * State Management
     */
    fun updateToNormalState(viewState: VS) {
        updateView(BaseState.Normal(viewState))
    }


    fun updateDataState(viewState: VS) {
        baseState = BaseState.Normal(viewState)
    }


    fun updateToLoadingState(loadingData: BaseExtraData? = null) {
        baseState?.let {
            updateView(BaseState.Loading(it.data, loadingData))
        }?: updateView(BaseState.Loading(defaultState, loadingData))
    }


    fun updateToErrorState(errorData: Throwable = Throwable()) {
        baseState?.let {
            updateView(BaseState.Error(it.data, errorData))
        }?: updateView(BaseState.Error(defaultState, errorData))
    }


    fun <T> checkDataState(checkDataStateFunction: (VS) -> T): T {
        return baseState?.let {
            checkDataStateFunction(it.data)
        }?: checkDataStateFunction(defaultState)
    }


    private fun updateView(state: BaseState<VS>) {
        baseState = state
        observableState.postValue(baseState)
    }


    /**
     * Coroutines
     */
    fun executeCoroutines(
        block: suspend CoroutineScope.() -> Unit,
        exceptionBlock: suspend CoroutineScope.(Throwable) -> Unit
    ) {
        viewModelScope.launch {
            try {
                block()
            } catch (e: Exception) {
                exceptionBlock(e)
            }
        }
    }
}