package com.tdapps.ricknmorty.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tdapps.ricknmorty.R
import kotlinx.android.synthetic.main.episodes_item.view.*


class EpisodeListAdapter(var episodes: List<String>):
    RecyclerView.Adapter<EpisodeListAdapter.ViewHolder>() {

    val episodeList = getEpisodeList(episodes)

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val episodeName = view.episodeName
        fun bind(episode: String) {
            episodeName.text = episode
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder (
        LayoutInflater.from(parent.context).inflate(R.layout.episodes_item, parent, false)
    )

    override fun getItemCount() = episodeList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(episodeList[position])

    }

    private fun getEpisodeList(urlList: List<String>): List<String> {
        val episodeList = mutableListOf<String>()

        if (urlList.isNotEmpty()) {
            urlList.forEach {
                val url: String = it.replace("https://rickandmortyapi.com/api/", "")
                    .replace("/", " ")
                    .capitalize()
                episodeList.add(url)
            }
        }
        return episodeList
    }



}