package com.batch.avgleclient.home


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.batch.avgleclient.R
import com.batch.avgleclient.databinding.FragmentHomeBinding
import com.batch.avgleclient.model.AvVideo
import com.google.firebase.auth.FirebaseAuth

class HomeFragment : Fragment(), VideoListController.ClickListener {

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var binding: FragmentHomeBinding
    private val controller by lazy { VideoListController(this) }
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
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
            viewModel.refresh()
        }
        observeVideos()
        observeLoading()
        initRecyclerView()
    }

    private fun observeVideos() {
        viewModel.topVideos.observe(viewLifecycleOwner) {
            controller.submitList(it)
        }
    }

    private fun observeLoading() {
        viewModel.loading.observe(viewLifecycleOwner) {
            controller.isLoading = it
        }
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

    override fun moreClickListener(item: AvVideo.Response.Video) {
        Toast.makeText(requireContext(), "${item.title}\nを後で見るに追加", Toast.LENGTH_SHORT).show()
    }
}
