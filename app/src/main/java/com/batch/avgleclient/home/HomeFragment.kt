package com.batch.avgleclient.home


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.net.toUri
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager

import com.batch.avgleclient.R
import com.batch.avgleclient.model.AvVideo
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.OnItemClickListener
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {
    private lateinit var viewModel: HomeViewModel
    private val topVideoListAdapter = GroupAdapter<GroupieViewHolder>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        viewModel.fetchFromRemote()
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.topVideos.observe(this, Observer {
            initRecyclerView(it.toVideoListItem())
        })
    }

    private fun initRecyclerView(videoListItem: List<VideoListItem>) {
        topVideoListAdapter.apply {
            update(videoListItem)
            setOnItemClickListener(onItemClickListener)
        }
        topVideosList.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = topVideoListAdapter
        }
    }

    fun List<AvVideo.Response.Videos>.toVideoListItem(): List<VideoListItem> {
        return this.map {
            VideoListItem(it)
        }
    }

    private val onItemClickListener = OnItemClickListener { item, view ->
        val index = this.topVideoListAdapter.getAdapterPosition(item)
        val videoUrl = viewModel.topVideos.value!![index].videoUrl
        val tabsIntent = CustomTabsIntent.Builder()
            .setShowTitle(true)
            .setToolbarColor(view.context.getColor(R.color.colorAccent))
            .build()
        tabsIntent.launchUrl(view.context, videoUrl.toUri())
    }

}
