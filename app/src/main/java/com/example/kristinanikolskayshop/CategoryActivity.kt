package com.example.kristinanikolskayshop

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_category.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.list
import okhttp3.OkHttpClient
import okhttp3.Request

class CategoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        val myUrl = "https://gist.githubusercontent.com/AironCorp/e39a447a7aea5a34e18c4f4b921a1a5b/raw/50aa8ea5109ce87a9d5012ef4f3ebf9d511f64f8/category.json"

        GlobalScope.launch(Dispatchers.Main) {
            val categorysDeferred = GlobalScope.async(Dispatchers.IO) {
                getCategories(myUrl)
            }

            val categorys = categorysDeferred.await()

            categorysListRecycler.adapter = CategorysAdapter(categorys, this@CategoryActivity)

            loadingProgBarCat.visibility = View.INVISIBLE
        }
    }

    fun getCategories(myUrl: String) = run{

        val categorysJson = makeCategoriesResponse(myUrl)

        getCategory(categorysJson)
    }

    fun makeCategoriesResponse(myUrl: String)= run {
        makeRequest(myUrl)
    }

    fun makeRequest(myUrl: String) = run {
        val client = OkHttpClient()

        val request = Request.Builder()
            .url(myUrl)
            .build()

        val response = client.newCall(request).execute()

        response.body()!!.string()
    }

    fun getCategory(json: String) = Json.parse(Category.serializer().list, json)
}
