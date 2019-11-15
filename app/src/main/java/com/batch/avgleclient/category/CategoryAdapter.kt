package com.batch.avgleclient.category

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.net.toUri
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.batch.avgleclient.R
import com.batch.avgleclient.databinding.ItemCategoryBinding
import com.batch.avgleclient.model.AvCategory
import kotlinx.android.synthetic.main.fragment_category.view.*

class CategoryAdapter(private val avCategories: ArrayList<AvCategory.Response.Category>) :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {
    class CategoryViewHolder(var view: ItemCategoryBinding) : RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemCategoryBinding>(inflater, R.layout.item_category, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.view.category = avCategories[position]
        // databindingにしたい
//        override fun onCategoryClicked(category: AvCategory.Response.Category) {
//            Log.d("TAGTAGTAG", category.categoryUrl)
//        }
        holder.view.categoryItemView.setOnClickListener {
            val tabsIntent = CustomTabsIntent.Builder()
                .setShowTitle(true)
                .setToolbarColor(it.context.getColor(R.color.colorAccent))
                .build()
            tabsIntent.launchUrl(it.context, avCategories[position].categoryUrl.toUri())
        }
//        holder.view.categoryCardView.setOnClickListener {
//            Toast.makeText(it.context, avCategories[position].categoryUrl, Toast.LENGTH_SHORT).show()
//            val tabsIntent = CustomTabsIntent.Builder()
//                .setShowTitle(true)
//                .setToolbarColor(it.context.getColor(R.color.colorAccent))
//                .build()
//            tabsIntent.launchUrl(it.context, avCategories[position].categoryUrl.toUri())
//        }
    }

    override fun getItemCount() = avCategories.size

    fun updateCategoryList(newCategoryList: List<AvCategory.Response.Category>) {
        avCategories.clear()
        avCategories.addAll(newCategoryList)
        notifyDataSetChanged()
    }
}