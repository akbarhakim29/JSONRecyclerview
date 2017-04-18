package com.bymankind.jsonrecyclerview.helper;

/**
 * Created by Server-Panduit on 4/13/2017.
 */

public interface ItemTouchHelperAdapter {

    //Called when an item has been dragged far enough to trigger a move. This is called every time an item is shifted, and not at the end of a "drop" event.
    boolean onItemMove(int fromPosition, int toPosition);

    //Called when an item has been dismissed by a swipe
    void onItemDismiss(int position);
}
