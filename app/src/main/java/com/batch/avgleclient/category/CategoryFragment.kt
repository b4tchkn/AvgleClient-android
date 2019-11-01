package com.batch.avgleclient.category

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.ViewModelProviders

class CategoryFragment : Fragment() {
    private val viewModel by lazy { ViewModelProviders.of(this).get(CategoryViewModel::class.java) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetchFromRemote()
    }
}
