package com.tdapps.ricknmorty.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private val BASE_URL = "https://rickandmortyapi.com/api/"

    fun getCharacters(): RickAndMortyAPI {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RickAndMortyAPI::class.java)
    }

    /*
    fun getEpisodes(): RickAndMortyAPI {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RickAndMortyAPI::class.java)
    }

     */


}