package com.batch.avgleclient.category

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.batch.avgleclient.R
import com.batch.avgleclient.model.AvCategory
import kotlinx.android.synthetic.main.fragment_category.*

class CategoryFragment : Fragment() {
    private lateinit var viewModel: CategoryViewModel
    private var categoryAdapter = CategoryAdapter(arrayListOf())
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CategoryViewModel::class.java)
        viewModel.fetchFromRemote()
        categoriesList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = categoryAdapter
        }
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.categories.observe(this, Observer { categories ->
            categories?.let {
                categoriesList.visibility = View.VISIBLE
                categoryAdapter.updateCategoryList(categories)
            }
        })
    }
}
