package com.batch.avgleclient.collection

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.batch.avgleclient.R
import com.batch.avgleclient.model.AvCollection
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
        viewModel.fetchFromRemote(1)
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.collections.observe(this, Observer {
//            initRecyclerView()
            initRecyclerView(it.toCollectionItem())
        })
    }

    private fun initRecyclerView(collectionItem: List<CollectionItem>) {
        val cAdapter = GroupAdapter<GroupieViewHolder>().apply {
            // grid 2列にする
            spanCount = 2
            addAll(collectionItem)
        }
        collectionRecyclerView.apply {
            //layoutManager = LinearLayoutManager(context)
            layoutManager = GridLayoutManager(context, cAdapter.spanCount).apply {
                spanSizeLookup = cAdapter.spanSizeLookup
            }
            setHasFixedSize(true)
            adapter = cAdapter
        }
    }

    fun List<AvCollection.Response.Collection>.toCollectionItem() : List<CollectionItem> {
        return this.map {
            CollectionItem(it)
        }
    }
}
