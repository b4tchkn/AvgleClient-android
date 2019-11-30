package com.batch.avgleclient.home


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.batch.avgleclient.R
import com.batch.avgleclient.databinding.FragmentHomeBinding
import com.batch.avgleclient.model.AvVideo
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder

class HomeFragment : Fragment(), VideoListController.ClickListener {

    private lateinit var viewModel: HomeViewModel
    private val topVideoListAdapter = GroupAdapter<GroupieViewHolder>()
    private lateinit var binding: FragmentHomeBinding
//    private val loadingItem = LoadingItem()
//    private lateinit var scrollListener: EndlessScrollListener
    private lateinit var scrollListener: EndlessRecyclerViewScrollListener
    private val controller by lazy { VideoListController(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        observeVideos()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        binding = FragmentHomeBinding.bind(view)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loading.value = true
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.refreshLayout.setOnRefreshListener {
            viewModel.loading.value = true
            // epoxyのadapterをclearにしてgroupieのadapter使わないようにしたい
//            binding.topVideosList.clear()
            topVideoListAdapter.clear()
            viewModel.init()
            viewModel.isRefreshing.value = true
        }
        val linearLayoutManager = LinearLayoutManager(requireContext())
        scrollListener = object : EndlessRecyclerViewScrollListener(linearLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                viewModel.fetchNext(page)
                binding.topVideosList.apply {
                    clear()
                    adapter?.notifyDataSetChanged()
                    scrollListener.resetState()
                }
            }
        }
        initRecyclerView()
        scrollListener.resetState()
        viewModel.init()
    }

    private fun observeVideos() {
        viewModel.topVideos.observe(this, Observer {
            viewModel.loading.value = false
//            scrollListener.loading = false
            controller.setData(it)
//            topVideoListAdapter.apply {
//                if (itemCount > 0 && getItemViewType(itemCount - 1) == loadingItem.viewType) {
//                    remove(loadingItem)
//                }
//            }
        })
    }

    private fun initRecyclerView() {
//        val manager = LinearLayoutManager(context)
//        val loadMore = { page: Int ->
//            scrollListener.loading = true
//            topVideoListAdapter.add(loadingItem)
//            viewModel.fetchNext(page)
//        }
//        scrollListener = EndlessScrollListener(manager, loadMore)
        binding.topVideosList.apply {
            setHasFixedSize(true)
            setController(controller)
            addOnScrollListener(scrollListener)
        }
    }

    override fun itemClickListener(item: AvVideo.Response.Videos) {
        val tabsIntent = CustomTabsIntent.Builder()
            .setShowTitle(true)
            .setToolbarColor(requireContext().getColor(R.color.colorAccent))
            .build()
        tabsIntent.launchUrl(requireContext(), item.videoUrl.toUri())
    }
}
