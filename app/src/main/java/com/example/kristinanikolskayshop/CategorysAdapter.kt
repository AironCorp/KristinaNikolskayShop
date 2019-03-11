package com.example.kristinanikolskayshop

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.category_item.view.*
import org.jetbrains.anko.layoutInflater
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivity

class CategorysAdapter(
    val categorys : List<Category>,
    val context: Context
) : RecyclerView.Adapter<CategorysAdapter.ViewHolder>() {
                override fun onCreateViewHolder(recyclerView: ViewGroup, viewType: Int) = run {
                    val view = context.layoutInflater.inflate(
                        R.layout.category_item,
                        recyclerView,
                        false
                    )
                    ViewHolder(view)
                }

    override fun getItemCount(): Int = categorys.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = categorys.get(position)
        holder.itemView.titleViewCategory.text = category.title
        Picasso.get()
            .load(category.imageURL)
            .into(holder.itemView.pictureViewCategory)
        holder.itemView.onClick {
            context.startActivity<ProductsActivity>("categoryURL" to category.productURL)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}