package com.roman.airtickets.presentation.adapters

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager

class MyLinearLayoutManager(context:Context):LinearLayoutManager(context) {
    override fun canScrollVertically(): Boolean {
        return false
    }
}