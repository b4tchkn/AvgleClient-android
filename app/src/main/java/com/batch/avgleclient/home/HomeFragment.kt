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
import com.batch.avgleclient.R
import com.batch.avgleclient.databinding.FragmentHomeBinding
import com.batch.avgleclient.model.AvVideo

class HomeFragment : Fragment(), VideoListController.ClickListener {

    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding
    private val controller by lazy { VideoListController(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        observeVideos()
        observeLoading()
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
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.refreshLayout.setOnRefreshListener {
            viewModel.isRefreshing.value = true
            observeVideos()
        }
        initRecyclerView()
    }

    private fun observeVideos() {
        viewModel.topVideos.observe(this, Observer {
            controller.submitList(it)
        })
    }

    private fun observeLoading() {
        viewModel.loading.observe(this, Observer {
            controller.isLoading = it
        })
    }

    private fun initRecyclerView() {
        binding.topVideosList.apply {
            setHasFixedSize(true)
            setController(controller)
        }
    }

    override fun itemClickListener(item: AvVideo.Response.Video) {
        val tabsIntent = CustomTabsIntent.Builder()
            .setShowTitle(true)
            .setToolbarColor(requireContext().getColor(R.color.colorAccent))
            .build()
        tabsIntent.launchUrl(requireContext(), item.videoUrl.toUri())
    }
}
