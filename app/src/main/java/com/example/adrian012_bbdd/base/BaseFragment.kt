package com.example.adrian012_bbdd.base

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.adrian012_bbdd.base.util.BaseExtraData
import com.example.adrian012_bbdd.base.util.BaseState


abstract class BaseFragment<VS: BaseViewState, VM: BaseViewModel<VS>, B: ViewDataBinding>: Fragment() {
    /**
     * ViewModel
     */
    lateinit var viewModel: VM
    abstract val viewModelClass: Class<VM>

    /**
     * Data Binding
     */
    protected lateinit var binding: B
    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> B

    /**
     * onCreateView
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = bindingInflater.invoke(inflater, container, false)

        return binding.root
    }

    /**
     * OnPause
     */
    override fun onPause() {
        super.onPause()

        // Hide Keyboard
        (requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(view?.windowToken, 0)
    }

    /**
     * onResume
     */
    override fun onResume() {
        super.onResume()

        // Get or create the viewModel
        viewModel = ViewModelProvider(this).get(viewModelClass)

        // Setup Observers
        viewModel.getObservableState().observe(viewLifecycleOwner, { state ->
            onNormal(state.data) // This will clean all (errors, view, etc.)

            when (state) {
                is BaseState.Loading -> onLoading(state.dataLoading)
                is BaseState.Error -> onError(state.dataError)
                else -> { }
            }
        })

        // Setup view sending the view model
        setupView(viewModel)

        // Fragment Start
        viewModel.onStart()
    }

    /**
     * Setup view when viewModel exists
     */
    abstract fun setupView(viewModel: VM)

    /**
     * Manage State Functions
     */
    abstract fun onNormal(data: VS)
    abstract fun onLoading(dataLoading: BaseExtraData?)
    abstract fun onError(dataError: Throwable)
}