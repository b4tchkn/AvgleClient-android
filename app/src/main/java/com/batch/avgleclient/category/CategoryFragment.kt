package com.batch.avgleclient.category

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.batch.avgleclient.R

class CategoryFragment : Fragment() {
    private val viewModel by lazy { ViewModelProviders.of(this).get(CategoryViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("avavav", "fjdakfjl")
        viewModel.fetchFromRemote()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        observeViewModel(viewModel)
        return inflater.inflate(R.layout.fragment_category, container, false)
    }

    private fun observeViewModel(viewModel: CategoryViewModel) {
        viewModel.categories.observe(viewLifecycleOwner, Observer { category ->
            if (category != null) {
                Log.d("avavav", category.toString())
            } else {
                Log.d("avavav", category.toString())
            }
        })
    }
}
