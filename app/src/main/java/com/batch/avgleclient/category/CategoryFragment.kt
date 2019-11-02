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
//    private val viewModel by lazy { ViewModelProviders.of(this).get(CategoryViewModel::class.java) }
    private lateinit var viewModel: CategoryViewModel

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
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.categories.observe(viewLifecycleOwner, Observer { category ->
            if (category != null) {
            } else {
            }
        })
    }
}
