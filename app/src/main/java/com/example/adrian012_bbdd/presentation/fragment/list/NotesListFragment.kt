package com.example.adrian012_bbdd.presentation.fragment.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adrian012_bbdd.R
import com.example.adrian012_bbdd.base.BaseFragment
import com.example.adrian012_bbdd.base.util.BaseExtraData
import com.example.adrian012_bbdd.databinding.FragmentNotesListBinding
import com.example.adrian012_bbdd.domain.model.NoteDomainModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class NotesListFragment: BaseFragment<NotesListState, NotesListViewModel, FragmentNotesListBinding>() {
    override val viewModelClass: Class<NotesListViewModel> = NotesListViewModel::class.java
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentNotesListBinding = FragmentNotesListBinding::inflate
    lateinit var mAdapter: NotesListAdapter
    lateinit var vm: NotesListViewModel


    /**
     * Setup Views
     */
    override fun setupView(viewModel: NotesListViewModel) {
        vm = viewModel

        setupButton()
        setupRecyclerView()
    }


    /**
     * Setup Views
     */
    fun setupButton() {
        // When create a new Note, we don't pass any information since we'll have a blank Note to fill in
        binding.fragmentNotesListFab.setOnClickListener{
            findNavController().navigate(NotesListFragmentDirections.actionNotesListFragmentToNotesFormFragment())
        }
    }


    fun setupRecyclerView() {
        // When "Edit" button is clicked, we send the "item" (the content of the listFragment) to the FormFragment in order to modify it
        mAdapter = NotesListAdapter(listOf(), object: NotesListAdapter.MyClicksListener {
            override fun onEditButtonClicked(item: NoteDomainModel) {
                findNavController().navigate(NotesListFragmentDirections.actionNotesListFragmentToNotesFormFragment(item))
            }


            override fun onDeleteButtonClicked(item: NoteDomainModel) {
                vm.onActionDeleteNote(item)
            }
        })

        binding.fragmentNotesListRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = mAdapter
            itemAnimator = DefaultItemAnimator()
        }
    }


    /**
     * Status
     */
    override fun onNormal(data: NotesListState) {
        binding.fragmentNotesListImageViewNoContent.visibility = if (data.notesList.isNotEmpty()) View.GONE else View.VISIBLE
        binding.fragmentNotesListRecyclerView.visibility = if (data.notesList.isEmpty()) View.GONE else View.VISIBLE

        if (data.notesList.isNotEmpty()) {
            mAdapter.updateList(data.notesList)
        }
    }


    override fun onLoading(dataLoading: BaseExtraData?) {

    }


    override fun onError(dataError: Throwable) {

    }
}