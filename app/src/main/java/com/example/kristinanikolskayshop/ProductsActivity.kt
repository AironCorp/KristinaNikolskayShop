package com.example.kristinanikolskayshop

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_products.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlinx.serialization.list
import okhttp3.OkHttpClient
import okhttp3.Request

class ProductsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products)

        val productURL = intent.getStringExtra("categoryURL")

        GlobalScope.launch(Dispatchers.Main) {
            val productsDeferred = GlobalScope.async(Dispatchers.IO) {
                getProducts(productURL)
            }

            val products = productsDeferred.await()

            productListRecycler.adapter = ProductAdapter(products, this@ProductsActivity)

            loadingProgBar.visibility = View.INVISIBLE
        }
    }

    fun getProducts(productURL: String) = run{

        val productsJson = makeProductsResponse(productURL)

        getProgucts(productsJson)
    }

    fun makeProductsResponse(productURL: String) = run {
        makeRequest(productURL)
    }

    fun makeRequest(productURL: String) = run {
        val client = OkHttpClient()

        val request = Request.Builder()
            .url(productURL)
            .build()

        val response = client.newCall(request).execute()

        response.body()!!.string()
    }

    fun getProgucts(json: String) = Json.parse(Product.serializer().list, json)
}

