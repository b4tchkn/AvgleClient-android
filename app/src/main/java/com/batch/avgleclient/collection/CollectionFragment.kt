package com.batch.avgleclient.collection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import com.batch.avgleclient.R
import com.batch.avgleclient.databinding.FragmentCollectionBinding
import com.batch.avgleclient.model.AvCollection
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.fragment_collection.*

class CollectionFragment : Fragment() {

    private val viewModel: CollectionViewModel by viewModels()
    private lateinit var binding: FragmentCollectionBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_collection, container, false)
        binding = FragmentCollectionBinding.bind(view)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetchFromRemote(1)
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.collections.observe(viewLifecycleOwner) {
            initRecyclerView(it.toCollectionItem())
        }
    }

    private fun initRecyclerView(collectionItem: List<CollectionItem>) {
        val cAdapter = GroupAdapter<GroupieViewHolder>().apply {
            // grid 2列にする
            spanCount = 2
            addAll(collectionItem)
        }
        collection_list.apply {
            layoutManager = GridLayoutManager(context, cAdapter.spanCount).apply {
                spanSizeLookup = cAdapter.spanSizeLookup
            }
            setHasFixedSize(true)
            adapter = cAdapter
        }
    }

    private fun List<AvCollection.Response.Collection>.toCollectionItem(): List<CollectionItem> {
        return this.map {
            CollectionItem(it)
        }
    }
}
