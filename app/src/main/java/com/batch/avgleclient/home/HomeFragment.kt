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
import com.batch.avgleclient.R
import com.batch.avgleclient.databinding.FragmentHomeBinding
import com.batch.avgleclient.model.AvVideo
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.OnItemClickListener

class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private val topVideoListAdapter = GroupAdapter<GroupieViewHolder>()

    private lateinit var binding: FragmentHomeBinding

    private val loadingItem = LoadingItem()

    lateinit var scrollListener: EndlessScrollListener

    private val onItemClickListener = OnItemClickListener { item, view ->
        val index = this.topVideoListAdapter.getAdapterPosition(item)
        val videoUrl = viewModel.topVideos.value!![index].videoUrl
        val tabsIntent = CustomTabsIntent.Builder()
            .setShowTitle(true)
            .setToolbarColor(view.context.getColor(R.color.colorAccent))
            .build()
        tabsIntent.launchUrl(view.context, videoUrl.toUri())
    }

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
        initRecyclerView()
        binding.refreshLayout.setOnRefreshListener {
            viewModel.loading.value = true
            topVideoListAdapter.clear()
            viewModel.init()
            viewModel.isRefreshing.value = true
        }
        viewModel.init()
    }

    private fun observeVideos() {
        viewModel.topVideos.observe(this, Observer {
            viewModel.loading.value = false
            scrollListener.loading = false
            topVideoListAdapter.apply {
                if (itemCount > 0 && getItemViewType(itemCount - 1) == loadingItem.viewType) {
                    remove(loadingItem)
                }
                addAll(it.toVideoListItem())
                setOnItemClickListener(onItemClickListener)
            }
        })
    }

    private fun initRecyclerView() {
        val manager = LinearLayoutManager(context)
        val loadMore = { page: Int ->
            scrollListener.loading = true
            topVideoListAdapter.add(loadingItem)
            viewModel.fetchNext(page)
        }
        scrollListener = EndlessScrollListener(manager, loadMore)
        binding.topVideosList.apply {
            layoutManager = manager
            setHasFixedSize(true)
            adapter = topVideoListAdapter
            addOnScrollListener(scrollListener)
        }
    }

    private fun List<AvVideo.Response.Videos>.toVideoListItem(): List<VideoListItem> {
        return this.map {
            VideoListItem(it)
        }
    }
}
