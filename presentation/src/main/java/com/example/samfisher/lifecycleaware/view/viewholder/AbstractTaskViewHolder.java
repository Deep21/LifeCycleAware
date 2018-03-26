package com.example.samfisher.lifecycleaware.view.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.example.samfisher.lifecycleaware.TaskEntity;

/**
 * Created by Samfisher on 30/01/2018.
 */
public abstract class AbstractTaskViewHolder extends RecyclerView.ViewHolder {

  public AbstractTaskViewHolder(View itemView) {
    super(itemView);
  }

  public abstract void bind(TaskEntity taskEntity);

}
