package com.example.adrian012_bbdd.presentation.fragment.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
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


    override fun setupView(viewModel: NotesListViewModel) {
        vm = viewModel

        setupRecyclerView()
        setupButton()
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


    /**
     * Setup Views
     */
    fun setupRecyclerView() {
        mAdapter = NotesListAdapter(listOf(), object: NotesListAdapter.MyClicksListener {
            override fun onEditButtonClicked(item: NoteDomainModel) {

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


    fun setupButton() {
        binding.fragmentNotesListFab.setOnClickListener{

        }
    }
}