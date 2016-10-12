package com.veronica.medaily.interfaces;

import android.view.View;

/**
 * Created by Veronica on 10/9/2016.
 */
public interface IRecyclerItemClickedListener {
    //open
    void onItemClicked(View view,int position);
    //delete
    void onItemSlipped(View view,int position);
    //edit
    void onItemLongPress(View view,int position);
}
