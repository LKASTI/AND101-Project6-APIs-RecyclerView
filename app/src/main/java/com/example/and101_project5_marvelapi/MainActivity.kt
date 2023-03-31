package com.example.and101_project5_marvelapi

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.annotation.Nullable
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
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

    private var imageUrl : String = ""
    private var nameText : String = ""
    private var descText : String = ""
    private var rand : Int = 0

    @GlideModule
    class MyAppGlideModule : AppGlideModule() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.test_button)
        val name = findViewById<TextView>(R.id.name)
        val description = findViewById<TextView>(R.id.description)
        val image = findViewById<ImageView>(R.id.image1)

        button.setOnClickListener{
                getResponse()

                setImage(image)
                name.text = nameText
                description.text = descText

        }


    }

    private fun setImage(imageView: ImageView){
        Glide.with(this)
            .load(imageUrl)
            .fitCenter()
            .override(300, 300)
            .listener(object : RequestListener<Drawable?> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: com.bumptech.glide.request.target.Target<Drawable?>?,
                    isFirstResource: Boolean
                ): Boolean {
                    Log.d("glide exception:", "$e")
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable?>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }
            })
            .into(imageView)
    }


    private fun getResponse(){
        val client = AsyncHttpClient()

        client["https://api.jikan.moe/v4/random/characters",
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
                    imageUrl = json.jsonObject.getJSONObject("data").getJSONObject("images").getJSONObject("jpg").getString("image_url")
                    nameText = json.jsonObject.getJSONObject("data").getString("name")

                    descText =
                        if(json.jsonObject.getJSONObject("data").getString("about") == "") {
                            json.jsonObject.getJSONObject("data").getString("name_kanji")
                        } else {
                            json.jsonObject.getJSONObject("data").getString("about")
                        }
                }
            }]

    }
}