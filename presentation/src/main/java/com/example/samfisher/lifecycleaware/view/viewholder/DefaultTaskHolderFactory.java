package com.example.samfisher.lifecycleaware.view.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import com.example.samfisher.lifecycleaware.view.adapter.OnItemClickListener;
import javax.inject.Inject;

/**
 * Created by Samfisher on 30/01/2018.
 */

public class DefaultTaskHolderFactory implements ViewHolderFactoryInterface {

  @Inject
  public DefaultTaskHolderFactory() {
  }

  @Override
  public RecyclerView.ViewHolder create(ViewGroup viewGroup, OnItemClickListener onItemClickListener) {
    return new DefaultTaskViewHolder(viewGroup, onItemClickListener);
  }

}
