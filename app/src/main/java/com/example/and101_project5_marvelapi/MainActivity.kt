package com.example.and101_project5_marvelapi

import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import okhttp3.Headers
import org.w3c.dom.Text
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private var imageUrl : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.test_button)
        val name = findViewById<TextView>(R.id.name)
        val description = findViewById<TextView>(R.id.description)
        val image = findViewById<ImageView>(R.id.image1)

        button.setOnClickListener{
            getResponse(name, description)
            getImage(image)
        }


    }

    private fun getImage(imageView: ImageView){
        Glide.with(this)
            .load(imageUrl)
            .fitCenter()
            .into(imageView)
    }

    private fun getResponse(nameView : TextView, descriptionView : TextView){
        val client = AsyncHttpClient()
        val hash = "1214aba0de6f1fb91de25c25ffd66d50"
        val public_key = "16945dc672c2cb9628d201d80acbff33"
        val ts = "1"

        client["https://gateway.marvel.com/v1/public/characters?ts=1&apikey=16945dc672c2cb9628d201d80acbff33&hash=1214aba0de6f1fb91de25c25ffd66d50&limit=50",
            object : JsonHttpResponseHandler(){
                override fun onFailure(
                    statusCode: Int,
                    headers: Headers?,
                    response: String?,
                    throwable: Throwable?
                ) {
                    Log.d("Fail", response.toString())
                }

                override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON?) {
                    val listCharacterObjects = json?.jsonObject?.getJSONObject("data")?.getJSONArray("results")

                    val rand = listCharacterObjects?.let { Random.nextInt(0, it.length()-1) }
                    var name : String
                    var description : String
                    var imageUrl : String

                    if (rand != null) {
                        name = listCharacterObjects?.getJSONObject(rand)?.getString("name") as String
                        description = listCharacterObjects?.getJSONObject(rand)?.getString("description") as String
                        imageUrl = listCharacterObjects?.getJSONObject(rand)?.getJSONObject("thumbnail")?.getString("path") + ".jpg"


                        nameView.text = name
                        descriptionView.text = description
                    }




                }
            }]
    }
}