package com.github.freitzzz.gameboydb.view.adapter

import android.view.View
import android.widget.LinearLayout
import androidx.annotation.LayoutRes

private const val STANDARD_NEXT_PAGE_FACTOR = 0.8f

/**
 * A [RecyclerViewAdapter] for paginated data.
 *
 * The [nextPageFactor] value is used to control when to request more data using the [onNextPage] callback.
 * This value is applied to the dataset size in order to find the next best position to request the next page.
 *
 * (e.g., `Dataset size = 10, Next page factor = 0.8, Position = 10*0.8 = 8`)
 */
class PaginatedRecyclerViewAdapter<T>(
    @LayoutRes itemLayoutId: Int,
    onBind: View.(data: T) -> Unit,
    onLayoutParams: LinearLayout.LayoutParams.() -> Unit = {},
    private val onNextPage: () -> Unit,
    private val nextPageFactor: Float = STANDARD_NEXT_PAGE_FACTOR,
) : RecyclerViewAdapter<T>(
    itemLayoutId = itemLayoutId,
    onBind = onBind,
    onLayoutParams = onLayoutParams,
) {
    private var thresholdPosition = 0

    override fun onBindViewHolder(holder: ItemViewHolder<T>, position: Int) {
        super.onBindViewHolder(holder, position)

        if (position == thresholdPosition) {
            onNextPage()
        }
    }

    fun iterate(page: Page<T>) {
        if (page.cursor == 1) {
            super.set(page.data)
        } else {
            super.addAll(page.data)
        }

        thresholdPosition = (itemCount * 0.8).toInt()
    }
}

/**
 * Holds the results of a paginated query.
 *
 * The [cursor] value indicate the page number and is used
 * to control whether to refresh the dataset (by replacing the dataset with page data) or to append
 * new data.
 */
data class Page<T>(
    val data: List<T> = listOf(),
    val cursor: Int = 1,
)