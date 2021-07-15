package com.tdapps.ricknmorty.service

import com.tdapps.ricknmorty.models.Character
import com.tdapps.ricknmorty.models.CharacterList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickAndMortyAPI {

    @GET("character")
    fun getAllCharacters(): Call<CharacterList>


    @GET("character/{characterId}")
    fun getSpecificCharacter(@Path("characterId") characterId: Int): Call<Character>

/*
    @GET("episode")
    suspend fun getAllEpisodes(@Query("page") currentPage: Int): Call<EpisodeData>


 */


}