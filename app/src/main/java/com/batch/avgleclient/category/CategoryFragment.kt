package com.batch.avgleclient.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.batch.avgleclient.R
import com.batch.avgleclient.model.AvCategory
import kotlinx.android.synthetic.main.fragment_category.*

class CategoryFragment : Fragment(), CategoryListController.ClickListener {

    private val viewModel: CategoryViewModel by viewModels()
    private val controller by lazy { CategoryListController(this) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.refresh()
        categories_list.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = controller.adapter
        }
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.categories.observe(viewLifecycleOwner) { categories ->
            categories?.let {
                categories_list.visibility = View.VISIBLE
                controller.setData(categories)
            }
        }

        viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            isLoading?.let {
                loading_view.visibility = if (it) View.VISIBLE else View.GONE
                if (it) {
                    categories_list.visibility = View.GONE
                }
            }
        }
    }

    override fun itemClickListener(item: AvCategory.Response.Category) {
        val tabsIntent = CustomTabsIntent.Builder()
            .setShowTitle(true)
            .setToolbarColor(requireContext().getColor(R.color.colorAccent))
            .build()
        tabsIntent.launchUrl(requireContext(), item.categoryUrl.toUri())
    }
}
