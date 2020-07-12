package com.example.notes

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class EventsItemDecorator(private val verticalPadding:Int,private val horizontalPadding:Int):RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.top = verticalPadding
        outRect.bottom = verticalPadding
        outRect.left = horizontalPadding
        outRect.right = horizontalPadding
    }
}