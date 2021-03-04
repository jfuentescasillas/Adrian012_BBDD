package com.example.adrian012_bbdd.presentation.fragment.form

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.adrian012_bbdd.R
import com.example.adrian012_bbdd.base.BaseFragment
import com.example.adrian012_bbdd.base.util.BaseExtraData
import com.example.adrian012_bbdd.databinding.FragmentNotesFormBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class NotesFormFragment :
    BaseFragment<NotesFormState, NotesFormViewModel, FragmentNotesFormBinding>() {
    override val viewModelClass: Class<NotesFormViewModel> = NotesFormViewModel::class.java
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentNotesFormBinding =
        FragmentNotesFormBinding::inflate
    lateinit var vm: NotesFormViewModel
    private val args: NotesFormFragmentArgs by navArgs()

    companion object {
        const val FIELD_KEY_TITLE = "title"
        const val FIELD_KEY_BODY = "body"
        const val FIELD_KEY_ALL = "all"
        const val CODE_CONFIRM_UPDATE = 1001
        const val CODE_ALRIGHT = 1000
        const val CODE_EXIT = 1002
    }


    override fun setupView(viewModel: NotesFormViewModel) {
        vm = viewModel
        vm.setNote(args.inputStateNote)

        setupEditText()
        setupButton()
    }

    /**
     * Setup View
     */
    private fun setupEditText() {
        binding.fragmentNotesFormTextInputEditTextTitle.doOnTextChanged { inputText, _, _, _ ->
            vm.onActionSetTitle(inputText.toString())
        }
        binding.fragmentNotesFormTextInputEditTextBody.doOnTextChanged { inputText, _, _, _ ->
            vm.onActionSetBody(inputText.toString())
        }
    }


    private fun setupButton() {
        binding.fragmentNotesFormButtonSave.setOnClickListener {
            vm.onActionSaveNote()
        }
    }

    /**
     * States
     */
    override fun onNormal(data: NotesFormState) {
        // Hide Progress Bar
        binding.fragmentNotesProgressBar.visibility = View.GONE

        // Hide errors
        binding.fragmentNotesFormTextInputLayoutTitle.error = null
        binding.fragmentNotesFormTextInputLayoutBody.error = null

        // Set Values
        binding.fragmentNotesFormTextInputEditTextTitle.setText(data.title)
        binding.fragmentNotesFormTextInputEditTextBody.setText(data.body)
        binding.fragmentNotesFormButtonSave.text =
            if (data.note != null) getString(R.string.notesFormButtonTextUpdate) else getString(R.string.notesFormButtonTextCreate)
    }


    override fun onLoading(dataLoading: BaseExtraData?) {
        dataLoading?.let {
            when (it.type) {
                CODE_ALRIGHT ->
                    MaterialAlertDialogBuilder(requireActivity())
                        .setCancelable(false)
                        .setTitle(getString(R.string.notesFormFragmentDialogTitle))
                        .setMessage(getString(R.string.notesFormFragmentDialogMessage))
                        .setPositiveButton(getString(R.string.notesFormFragmentDialogueButtonAccept)) { _, _ ->
                            findNavController().popBackStack()
                        }
                        .show()

                CODE_CONFIRM_UPDATE ->
                    MaterialAlertDialogBuilder(requireActivity())
                        .setCancelable(false)
                        .setTitle(getString(R.string.notesFormFragmentDialogTitle))
                        .setMessage(getString(R.string.notesFormFragmentDialogMessageConfirmation))
                        .setPositiveButton(getString(R.string.notesFormFragmentDialogueButtonAccept)) { _, _ ->
                            vm.onActionUpdateNote()
                        }
                        .setNegativeButton(getString(R.string.notesFormFragmentDialogueButtonCancel)) { _, _, ->

                        }
                        .show()

                CODE_EXIT -> findNavController().popBackStack()

                else -> {}
            }
        } ?: run {
            binding.fragmentNotesProgressBar.visibility = View.VISIBLE
        }
    }


    override fun onError(dataError: Throwable) {
        // Hide errors
        if (dataError is FieldErrorException) {
            when (dataError.fieldName) {
                FIELD_KEY_TITLE -> binding.fragmentNotesFormTextInputLayoutTitle.error =
                    getString(R.string.notesFormFragmentErrorEmptyField)
                FIELD_KEY_BODY -> binding.fragmentNotesFormTextInputLayoutBody.error =
                    getString(R.string.notesFormFragmentErrorEmptyField)
                FIELD_KEY_ALL -> {
                    binding.fragmentNotesFormTextInputLayoutTitle.error =
                        getString(R.string.notesFormFragmentErrorEmptyField)
                    binding.fragmentNotesFormTextInputLayoutBody.error =
                        getString(R.string.notesFormFragmentErrorEmptyField)
                }
                else -> {
                }
            }
        }
    }
}