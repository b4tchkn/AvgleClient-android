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
import com.xwray.groupie.Group
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.groupiex.plusAssign
import kotlinx.android.synthetic.main.fragment_collection.*

class CollectionFragment : Fragment() {

    private val viewModel: CollectionViewModel by viewModels()
    private lateinit var binding: FragmentCollectionBinding
    private val adapter = GroupAdapter<GroupieViewHolder>()

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

        collection_list.apply {
            adapter = this@CollectionFragment.adapter
            layoutManager = GridLayoutManager(context, 2)
        }

        adapter.apply {
            // Itemタップ時の動作
        }

        viewModel.collections.observe(viewLifecycleOwner) { list ->
            val groupList = mutableListOf<Group>()
            adapter += groupList
            list.forEach {
                groupList.add(CollectionItem(collection = it))
            }
            adapter.update(groupList)
        }
    }
}
