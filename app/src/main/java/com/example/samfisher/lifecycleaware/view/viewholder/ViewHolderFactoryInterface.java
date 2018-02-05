package com.example.samfisher.lifecycleaware.view.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import com.example.samfisher.lifecycleaware.view.adapter.TaskListAdapter.OnItemClickListener;

/**
 * Created by Samfisher on 30/01/2018.
 */

public interface ViewHolderFactoryInterface {

  RecyclerView.ViewHolder create(ViewGroup viewGroup, OnItemClickListener onItemClickListener);
}
