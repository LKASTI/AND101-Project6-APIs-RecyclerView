package com.example.and101_project5_marvelapi

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.annotation.Nullable
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import org.json.JSONArray
import org.json.JSONObject
import java.util.*
import kotlin.random.Random


class MainActivity : AppCompatActivity() {

    private lateinit var characterList : MutableList<JSONObject>
    private lateinit var rvCharacters : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        characterList = mutableListOf()
        rvCharacters = findViewById(R.id.character_list)

        getResponse()

    }

    private fun getResponse(){
        val client = AsyncHttpClient()

        client["https://api.jikan.moe/v4/anime/1/characters",
                object : JsonHttpResponseHandler(){
                    override fun onFailure(
                        statusCode: Int,
                        headers: Headers?,
                        response: String?,
                        throwable: Throwable?
                    ) {
                        Log.d("Fail", response.toString())
                    }

                    override fun onSuccess(statusCode: Int, headers: Headers?, json: JsonHttpResponseHandler.JSON) {
                        val characterArray = json.jsonObject.getJSONArray("data")

                        for(i in 0 until characterArray.length())
                        {
                            var character = JSONObject()
                            val obj = characterArray.getJSONObject(i)
                            character.put("name", "name: " + obj.getJSONObject("character").getString("name"))
                            character.put("role", "role: " +obj.getString("role")+ " character")
                            character.put("image_url", obj.getJSONObject("character").getJSONObject("images")
                                .getJSONObject("webp").getString("image_url"))

                            characterList.add(character)
                        }

                        var adapter = CharacterAdapter(characterList)
                        rvCharacters.adapter = adapter
                        rvCharacters.layoutManager = LinearLayoutManager(this@MainActivity)
//                        Log.d("list ", "$characterList")

                    }
                }]

    }

//    private fun setImage(imageView: ImageView){
//        Glide.with(this)
//            .load(imageUrl)
//            .fitCenter()
//            .override(300, 300)
//            .listener(object : RequestListener<Drawable?> {
//                override fun onLoadFailed(
//                    e: GlideException?,
//                    model: Any?,
//                    target: com.bumptech.glide.request.target.Target<Drawable?>?,
//                    isFirstResource: Boolean
//                ): Boolean {
//                    Log.d("glide exception:", "$e")
//                    return false
//                }
//
//                override fun onResourceReady(
//                    resource: Drawable?,
//                    model: Any?,
//                    target: Target<Drawable?>?,
//                    dataSource: DataSource?,
//                    isFirstResource: Boolean
//                ): Boolean {
//                    return false
//                }
//            })
//            .into(imageView)
//    }



}