package com.example.and101_project5_marvelapi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.json.JSONObject
import org.w3c.dom.Text

//import kotlinx.coroutines.NonDisposableHandle.parent

class CharacterAdapter (private val characterList: List<JSONObject>) : RecyclerView.Adapter<CharacterAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val characterImage: ImageView
        val characterName: TextView
        val characterRole: TextView

        init {
            // Find our RecyclerView item's ImageView for future use
            characterImage = view.findViewById(R.id.image)
            characterName = view.findViewById(R.id.name)
            characterRole = view.findViewById(R.id.role)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterAdapter.ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.character_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: CharacterAdapter.ViewHolder, position: Int) {
        holder.characterName.text = characterList[position].getString("name")
        holder.characterRole.text = characterList[position].getString("role")

        Glide.with(holder.itemView)
            .load(characterList[position].getString("image_url"))
            .centerCrop()
            .into(holder.characterImage)


    }

    override fun getItemCount() = characterList.size


}