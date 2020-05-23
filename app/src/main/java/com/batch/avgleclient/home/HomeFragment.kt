package com.batch.avgleclient.home


import android.os.Bundle
import android.view.*
import android.widget.PopupMenu
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.batch.avgleclient.R
import com.batch.avgleclient.databinding.FragmentHomeBinding
import com.batch.avgleclient.login.LoginActivity
import com.batch.avgleclient.model.AvVideo
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.item_video.*
import timber.log.Timber

class HomeFragment : Fragment(), VideoListController.ClickListener {

    private val viewModel: HomeViewModel by lazy {
        ViewModelProvider(this).get(HomeViewModel::class.java)
    }
    private lateinit var binding: FragmentHomeBinding
    private val controller by lazy { VideoListController(this) }
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
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
            viewModel.refresh()
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

    override fun moreClickListener(item: AvVideo.Response.Video) {
//        val menuBuilder = MenuBuilder(requireContext())
//        val inflater = MenuInflater(requireContext())
//        inflater.inflate(R.menu.video_menu, menuBuilder)
//        val optionMenu = MenuPopupHelper(requireContext(), menuBuilder)
//        optionMenu.setForceShowIcon(true)
//        menuBuilder.setCallback {
//
//        }

        Toast.makeText(requireContext(), item.title, Toast.LENGTH_SHORT).show()
        val backgroundColor = ContextThemeWrapper(requireContext(), R.style.PopupMenu)
//        val popup = PopupMenu(backgroundColor, button_more)
        val popup = PopupMenu(backgroundColor, button_more)
        popup.inflate(R.menu.video_menu)
        popup.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item: MenuItem? ->
            when (item?.itemId) {
                R.id.watchlater -> {
                }
                R.id.playlist -> {
                }
            }
            true
        })
        popup.show()

//        popup.menuInflater.inflate(R.menu.video_menu, popup.menu)
//        popup.setOnMenuItemClickListener { item ->
//            val user = auth.currentUser
//            when (item.itemId) {
//                R.id.watchlater -> {
//                    if (user != null) {
//                        Toast.makeText(
//                            requireContext(),
//                            "Save to Watch later: ${user?.uid}",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    } else {
//                        val intent = LoginActivity.createIntent(requireActivity())
//                        startActivity(intent)
//                    }
//                }
//                R.id.playlist -> {
//                    if (user != null) {
//                        Toast.makeText(
//                            requireContext(),
//                            "Save to playlist: $user",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    } else {
//                        val intent = LoginActivity.createIntent(requireActivity())
//                        startActivity(intent)
//                    }
//                }
//            }
//            true
//        }
    }
}
