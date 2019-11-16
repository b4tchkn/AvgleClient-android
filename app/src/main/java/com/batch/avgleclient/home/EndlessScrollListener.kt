package com.batch.avgleclient.home

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class EndlessScrollListener(
    private val layoutManager: LinearLayoutManager,
    private val loadMore: (Int) -> Unit
) : RecyclerView.OnScrollListener() {
    private var firstVisibleItem = 0
    private var visibleItemCount = 0
    private var totalItemCount = 0
    private var previousTotal = 0
    var loading = false
    private var currentPage = 0
    private val threshold = 2


    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        visibleItemCount = recyclerView.childCount
        totalItemCount = layoutManager.itemCount
        firstVisibleItem = layoutManager.findFirstVisibleItemPosition()

        if (loading) {
            if (totalItemCount > previousTotal) {
                previousTotal = totalItemCount
            }
        }
        if (!loading &&
            (totalItemCount - visibleItemCount) <= (firstVisibleItem + threshold)
        ) {
            currentPage++
            loadMore(currentPage)
        }
    }
}