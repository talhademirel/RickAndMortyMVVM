package com.tdapps.ricknmorty.viewModel

import android.app.Activity
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tdapps.ricknmorty.models.Character
import com.tdapps.ricknmorty.models.CharacterList
import com.tdapps.ricknmorty.service.RetrofitInstance
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CharacterListViewModel : ViewModel() {


    val characters = MutableLiveData<List<Character>>()
    val charactersError = MutableLiveData<Boolean>()
    val charactersLoading = MutableLiveData<Boolean>()

    fun refreshData() {

        getDataFromAPI()

    }

    private fun getDataFromAPI() {
        charactersLoading.value = true

        RetrofitInstance.getCharacters().getAllCharacters()
            .enqueue(object : Callback<CharacterList?> {
                override fun onResponse(
                    call: Call<CharacterList?>,
                    response: Response<CharacterList?>
                ) {
                    characters.value = response.body()!!.results
                    charactersError.value = false
                    charactersLoading.value = false
                }

                override fun onFailure(call: Call<CharacterList?>, t: Throwable) {
                    charactersLoading.value = false
                    charactersError.value = true


                }
            })
    }


}