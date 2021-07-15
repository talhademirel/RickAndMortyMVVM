package com.tdapps.ricknmorty.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import com.example.ricknmorty.*
import com.tdapps.ricknmorty.R
import com.tdapps.ricknmorty.view.CharacterDetailsFragmentArgs
import com.tdapps.ricknmorty.view.CharacterDetailsFragmentDirections
import com.tdapps.ricknmorty.adapter.EpisodeListAdapter
import com.tdapps.ricknmorty.viewModel.CharacterDetailsViewModel
import kotlinx.android.synthetic.main.character_details_fragment.*

class CharacterDetailsFragment : Fragment() {

    private val args by navArgs<CharacterDetailsFragmentArgs>()
    private lateinit var viewModel: CharacterDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.character_details_fragment, container, false)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(CharacterDetailsViewModel::class.java)
        viewModel.characterId = args.characterId
        viewModel.showCharacterDetails()

        observeViewModel()

        exitButton.setOnClickListener {
            val action =
                CharacterDetailsFragmentDirections.actionCharacterDetailsFragmentToCharacterListFragment()
            findNavController().navigate(action)
        }


    }

    private fun observeViewModel() {


        viewModel.character.observe(viewLifecycleOwner, Observer { character ->
            character?.let {
                characterImage2.loadImage(
                    viewModel.character.value!!.image,
                    placeHolderProgressBar(characterImage2.context)
                )
                characterName2.text = viewModel.character.value!!.name
                val status = viewModel.character.value!!.status
                val species = viewModel.character.value!!.species
                characterStatusSpecies.text = ("$status, $species")
                characterGender.text = viewModel.character.value!!.gender


                episodesList.apply {
                    layoutManager = LinearLayoutManager(activity)
                    adapter = EpisodeListAdapter(viewModel.character.value!!.episode)
                    setHasFixedSize(true)
                }



                expandView.setOnClickListener {
                    if (expandable_layout.visibility == View.GONE){
                        TransitionManager.beginDelayedTransition(episodesView,AutoTransition())
                        expandable_layout.visibility = View.VISIBLE
                        expandView.rotation = 90f
                    } else{
                        TransitionManager.beginDelayedTransition(episodesView,AutoTransition())
                        expandable_layout.visibility = View.GONE
                        expandView.rotation = 270f
                    }

                }



            }
        })

        viewModel.characterLoading.observe(viewLifecycleOwner, Observer { loading ->
            loading?.let {
                if (it) {
                    characterLoading.visibility = View.VISIBLE
                    episodesView.visibility = View.VISIBLE
                    characterError.visibility = View.GONE
                } else {
                    characterLoading.visibility = View.GONE
                }
            }
        })

        viewModel.characterError.observe(viewLifecycleOwner, Observer { error ->
            error?.let {
                if (it) {
                    characterError.visibility = View.VISIBLE
                    episodesView.visibility = View.GONE
                    Toast.makeText(activity,"Network Failed, Try Again!", Toast.LENGTH_LONG).show()
                } else {
                    characterError.visibility = View.GONE
                }
            }
        })

    }

}