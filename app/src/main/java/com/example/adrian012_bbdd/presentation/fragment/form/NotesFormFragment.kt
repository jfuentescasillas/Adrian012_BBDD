package com.example.adrian012_bbdd.presentation.fragment.form

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import com.example.adrian012_bbdd.R
import com.example.adrian012_bbdd.base.BaseFragment
import com.example.adrian012_bbdd.base.util.BaseExtraData
import com.example.adrian012_bbdd.databinding.FragmentNotesFormBinding
import com.example.adrian012_bbdd.databinding.FragmentNotesListBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class NotesFormFragment: BaseFragment<NotesFormState, NotesFormViewModel, FragmentNotesFormBinding>() {
    override val viewModelClass: Class<NotesFormViewModel> = NotesFormViewModel::class.java
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentNotesFormBinding = FragmentNotesFormBinding::inflate
    lateinit var vm: NotesFormViewModel

    companion object {
        const val FIELD_KEY_TITLE = "title"
        const val FIELD_KEY_BODY = "body"
    }

    override fun setupView(viewModel: NotesFormViewModel) {
        vm = viewModel

        setupEditText()
        setupButton()
    }

    /**
     * Setup View
     */
    private fun setupEditText() {
        binding.fragmentNotesFormTextInputEditTextTitle.doOnTextChanged{ inputText, _, _, _ ->
            vm.onActionSetTitle(inputText.toString())
        }
        binding.fragmentNotesFormTextInputEditTextBody.doOnTextChanged{ inputText, _, _, _ ->
            vm.onActionSetBody(inputText.toString())
        }
    }


    private fun setupButton() {
        binding.fragmentNotesFormButtonSave.setOnClickListener{
            vm.onActionSaveNote()
        }
    }

    /**
     * States
     */
    override fun onNormal(data: NotesFormState) {
        // Hide errors
        binding.fragmentNotesFormTextInputLayoutTitle.error = null
        binding.fragmentNotesFormTextInputLayoutBody.error = null

        // Set Values
        binding.fragmentNotesFormTextInputEditTextTitle.setText(data.title)
        binding.fragmentNotesFormTextInputEditTextBody.setText(data.body)
    }


    override fun onLoading(dataLoading: BaseExtraData?) {

    }


    override fun onError(dataError: Throwable) {
        // Hide errors
        if (dataError is FieldErrorException) {
            when(dataError.fieldName) {
                FIELD_KEY_TITLE -> binding.fragmentNotesFormTextInputLayoutTitle.error = getString(R.string.notesFormFragmentErrorEmptyField)
                FIELD_KEY_BODY -> binding.fragmentNotesFormTextInputLayoutBody.error = getString(R.string.notesFormFragmentErrorEmptyField)
                else -> {}
            }
        }



    }
}