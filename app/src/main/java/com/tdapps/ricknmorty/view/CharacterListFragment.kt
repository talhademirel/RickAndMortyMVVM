package com.tdapps.ricknmorty.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.tdapps.ricknmorty.viewModel.CharacterListViewModel
import com.tdapps.ricknmorty.R
import com.tdapps.ricknmorty.adapter.CharacterListAdapter
import kotlinx.android.synthetic.main.character_list_fragment.*

class CharacterListFragment : Fragment() {

    private lateinit var viewModel: CharacterListViewModel
    private val charactersListAdapter = CharacterListAdapter(arrayListOf())


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(CharacterListViewModel::class.java)
        viewModel.refreshData()

        recyclerView_characters.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = charactersListAdapter
        }

        observeLiveData()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.character_list_fragment, container, false)

    }

    private fun observeLiveData() {

        viewModel.characters.observe(viewLifecycleOwner, Observer { characters ->
            characters?.let {
                recyclerView_characters.visibility = View.VISIBLE
                charactersListAdapter.updateCharacterList(characters)
            }
        })

        viewModel.charactersLoading.observe(viewLifecycleOwner, Observer { loading ->
            loading?.let {
                if (it) {
                    charactersLoading.visibility = View.VISIBLE
                    recyclerView_characters.visibility = View.GONE
                    charactersError.visibility = View.GONE
                } else {
                    charactersLoading.visibility = View.GONE
                }
            }
        })

        viewModel.charactersError.observe(viewLifecycleOwner, Observer { error ->
            error?.let {
                if (it) {
                    charactersError.visibility = View.VISIBLE
                    Toast.makeText(activity, "Network Failed, Try Again!", Toast.LENGTH_LONG).show()
                } else {
                    charactersError.visibility = View.GONE
                }
            }
        })

    }


}