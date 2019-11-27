package com.batch.avgleclient.category

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.batch.avgleclient.R
import kotlinx.android.synthetic.main.fragment_category.*

class CategoryFragment : Fragment() {
    private lateinit var viewModel: CategoryViewModel
//    private var categoryAdapter = CategoryAdapter(arrayListOf())

    private val controller by lazy { CategoryListController() }

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
        viewModel.refresh()
        categoriesList.apply {
            layoutManager = LinearLayoutManager(context)
//            adapter = categoryAdapter
            adapter = controller.adapter
        }
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.categories.observe(this, Observer { categories ->
            categories?.let {
                categoriesList.visibility = View.VISIBLE
                controller.setData(categories)
//                categoryAdapter.updateCategoryList(categories)
            }
        })
        viewModel.loading.observe(this, Observer { isLoading ->
            isLoading?.let {
                loadingView.visibility = if(it) View.VISIBLE else View.GONE
                if (it) {
                    categoriesList.visibility = View.GONE
                }
            }
        })
    }
}
