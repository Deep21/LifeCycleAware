package com.example.samfisher.lifecycleaware.view.adapter;

import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import com.example.samfisher.lifecycleaware.TaskEntity;
import com.example.samfisher.lifecycleaware.view.viewholder.AbstractTaskViewHolder;
import com.example.samfisher.lifecycleaware.view.viewholder.ViewHolderFactoryInterface;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;

/**
 * Created by Samfisher on 17/09/2017.
 */

public class TaskListAdapter extends RecyclerView.Adapter {

  private List<TaskEntity> taskList;
  private Map<Integer, ViewHolderFactoryInterface> viewHolderFactories;
  private OnItemClickListener onItemClickListener;

  public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
    this.onItemClickListener = onItemClickListener;
  }

  @Inject
  TaskListAdapter(Map<Integer, ViewHolderFactoryInterface> viewHolderFactories) {
    this.viewHolderFactories = viewHolderFactories;
  }

  public void updateList(List<TaskEntity> newList) {
    DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new SearchTaskListDiffCallback(this.taskList, newList));
    this.taskList.clear();
    this.taskList = newList;
    diffResult.dispatchUpdatesTo(this);
  }

  public List<TaskEntity> getContactList() {
    return taskList;
  }

  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return viewHolderFactories.get(viewType).create(parent, onItemClickListener);
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    ((AbstractTaskViewHolder) holder).bind(getContactList().get(position));
  }

  @Override
  public int getItemViewType(int position) {
    return TaskEntity.TYPE_NORMAL;
  }

  @Override
  public int getItemCount() {
    return taskList != null ? taskList.size() : 0;
  }

  public void setContactList(List<TaskEntity> taskList) {
    this.taskList = taskList;
  }

}
