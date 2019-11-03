package com.batch.avgleclient.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.batch.avgleclient.R
import com.batch.avgleclient.databinding.ItemCategoryBinding
import com.batch.avgleclient.model.AvCategory

class CategoryAdapter(private val avCategories: ArrayList<AvCategory.Response.Category>) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {
    class CategoryViewHolder(var view: ItemCategoryBinding) : RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemCategoryBinding>(inflater, R.layout.item_category, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.view.category = avCategories[position]
    }

    override fun getItemCount() = avCategories.size

    fun updateCategoryList(newCategoryList: List<AvCategory.Response.Category>) {
        avCategories.clear()
        avCategories.addAll(newCategoryList)
    }
}