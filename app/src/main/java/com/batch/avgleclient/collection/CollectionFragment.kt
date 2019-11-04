package com.batch.avgleclient.collection

import android.app.LauncherActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.batch.avgleclient.R
import com.xwray.groupie.Group
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.fragment_collection.*

class CollectionFragment : Fragment() {
    private lateinit var viewModel: CollectionViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_collection, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CollectionViewModel::class.java)
        viewModel.fetchFromRemote(0)

        val groupieAdapter = GroupAdapter<GroupieViewHolder>().apply {
//            spanCount = 2
        }

        collectionRecyclerView.apply {
            layoutManager = GridLayoutManager(context, groupieAdapter.spanCount).apply {
                spanSizeLookup = groupieAdapter.spanSizeLookup
            }
            adapter = groupieAdapter
        }
//        viewModel.collections.value.apply { collection ->
//            groupieAdapter.add(collection[0])
//        }
        observeViewModel(groupieAdapter)
    }

    private fun observeViewModel(adapter: GroupAdapter<GroupieViewHolder>) {
        viewModel.collections.observe(this, Observer {
            it?.let {collection ->
                collectionRecyclerView.visibility = View.VISIBLE
                adapter.update(mutableListOf<Group>()).apply {
                    collection.forEach { collectionItem ->
                        CollectionItem(collectionItem)
                    }
                }
            }
        })
    }
}
