package com.alexvoz.template.util

import androidx.recyclerview.widget.RecyclerView

abstract class PaginationScrollListener() :
    RecyclerView.OnScrollListener() {

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        recyclerView.canScrollVertically(1)
        if (!isLoading) {
            if (!recyclerView.canScrollVertically(1)) {
                loadMoreItems()
            }
        }
    }

    protected abstract fun loadMoreItems()

    abstract val isLoading: Boolean
}