package com.example.samfisher.lifecycleaware.view.adapter;

import android.support.v7.util.DiffUtil;
import com.example.samfisher.lifecycleaware.TaskEntity;
import java.util.List;

/**
 * Created by Samfisher on 08/02/2018.
 */

public class SearchTaskListDiffCallback extends DiffUtil.Callback {

  private final List<TaskEntity> oldList;
  private final List<TaskEntity> newList;

  public SearchTaskListDiffCallback(List<TaskEntity> oldList, List<TaskEntity> newList) {
    this.oldList = oldList;
    this.newList = newList;
  }

  @Override
  public int getOldListSize() {
    return oldList.size();
  }

  @Override
  public int getNewListSize() {
    return newList.size();
  }

  @Override
  public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
    return false;
  }

  @Override
  public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
    return false;
  }
}
