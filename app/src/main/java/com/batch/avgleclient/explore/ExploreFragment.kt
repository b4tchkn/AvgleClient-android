package com.batch.avgleclient.explore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.batch.avgleclient.R
import com.batch.avgleclient.databinding.FragmentExploreBinding
import com.xwray.groupie.Group
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.groupiex.plusAssign

class ExploreFragment : Fragment() {

    private val viewModel: ExploreViewModel by viewModels()
    private lateinit var binding: FragmentExploreBinding
    private val adapter = GroupAdapter<GroupieViewHolder>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_explore, container, false)
        binding = FragmentExploreBinding.bind(view)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.fetchCategory()

        binding.exploreRecyclerView.apply {
            adapter = this@ExploreFragment.adapter
            layoutManager = LinearLayoutManager(context)
        }

        viewModel.category.observe(viewLifecycleOwner) { categoryList ->
            val groupList = mutableListOf<Group>()
            adapter += groupList
            categoryList.forEach {
                groupList.add(CategoryItem(category = it))
            }
            adapter.update(groupList)
        }
    }
}
