package com.bymankind.jsonrecyclerview.helper;

import android.support.v7.widget.RecyclerView;

/**
 * Created by Server-Panduit on 4/13/2017.
 */

public interface OnStartDragListener {

    // Called when a view is requesting a start of a drag.
    void onStartDrag(RecyclerView.ViewHolder viewHolder);
}
