package com.tdapps.ricknmorty.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.tdapps.ricknmorty.R
import com.example.ricknmorty.loadImage
import com.tdapps.ricknmorty.models.Character
import com.example.ricknmorty.placeHolderProgressBar
import com.tdapps.ricknmorty.view.CharacterListFragmentDirections
import kotlinx.android.synthetic.main.characters_item.view.*


class CharacterListAdapter(val characters: ArrayList<Character>) :
    RecyclerView.Adapter<CharacterListAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var name: TextView
        var image: ImageView

        init {
            name = itemView.characterName
            image = itemView.characterImage
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.characters_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.characterName.text = characters[position].name
        holder.itemView.characterImage.loadImage(
            characters[position].image,
            placeHolderProgressBar(holder.itemView.context)
        )

        holder.itemView.characterListItem.setOnClickListener {

            val action =
                CharacterListFragmentDirections.actionCharacterListFragmentToCharacterDetailsFragment(
                    characters[position].id
                )
            holder.itemView.findNavController().navigate(action)

        }
    }

    override fun getItemCount(): Int {
        return characters.size
    }

    fun updateCharacterList(newCharacterList: List<Character>){

        characters.clear()
        characters.addAll(newCharacterList)
        notifyDataSetChanged()


    }
}