package com.roman.airtickets.presentation

import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

class CustomItemDecorationHorizontal(private val draw: Drawable) : ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {

        parent.adapter?.let { adapter ->
            outRect.right = when (parent.getChildAdapterPosition(view)) {
                RecyclerView.NO_POSITION,
                adapter.itemCount - 1 -> 0
                else -> draw.intrinsicWidth
            }

        }
    }
}

class CustomItemDecorationVertical(private val dividerDrawable: Drawable) : ItemDecoration() {
    private val dividerHeight = dividerDrawable.intrinsicHeight


    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {

        parent.adapter?.let { adapter ->
            parent.children // Displayed children on screen
                .forEach { view ->
                    val childAdapterPosition = parent.getChildAdapterPosition(view)
                        .let { if (it == RecyclerView.NO_POSITION) return else it }
                    if (childAdapterPosition != adapter.itemCount - 1) {
                        val left = parent.paddingLeft
                        val top = view.top + view.height
                        val right = left + view.width
                        val bottom = top + dividerHeight

                        dividerDrawable.bounds = Rect(left, top, right, bottom)
                        dividerDrawable.draw(canvas)
                    }
                }
        }
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {

        parent.adapter?.let { adapter ->
            outRect.bottom = when (parent.getChildAdapterPosition(view)) {
                RecyclerView.NO_POSITION,
                adapter.itemCount - 1 -> 0
                else -> dividerDrawable.intrinsicHeight
            }

        }
    }
}