package com.tdapps.ricknmorty.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tdapps.ricknmorty.models.Character
import com.tdapps.ricknmorty.service.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CharacterDetailsViewModel : ViewModel() {


    val character = MutableLiveData<Character>()
    val characterError = MutableLiveData<Boolean>()
    val characterLoading = MutableLiveData<Boolean>()

    var characterId = 1

    fun showCharacterDetails() {

        getCharacterDetails(characterId)

    }

    private fun getCharacterDetails(characterId : Int) {
        characterLoading.value = true
        RetrofitInstance.getCharacters().getSpecificCharacter(characterId).enqueue(object : Callback<Character?> {
            override fun onResponse(call: Call<Character?>, response: Response<Character?>) {
                character.value = response.body()
                characterError.value = false
               characterLoading.value = false
            }

            override fun onFailure(call: Call<Character?>, t: Throwable) {
                characterLoading.value = false
                characterError.value = true
            }
        })
    }
}