package com.example.samfisher.lifecycleaware.view.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.samfisher.lifecycleaware.R;
import com.example.samfisher.lifecycleaware.TaskEntity;
import com.example.samfisher.lifecycleaware.view.adapter.OnItemClickListener;


/**
 * Created by Samfisher on 30/01/2018.
 */

public class DefaultTaskViewHolder extends AbstractTaskViewHolder {

  @BindView(R.id.name)
  TextView name;
  @BindView(R.id.company)
  TextView company;
  View view;
  private OnItemClickListener onItemClickListener;


  public DefaultTaskViewHolder(ViewGroup viewGroup, OnItemClickListener onItemClickListener) {
    super(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_contact_list_layout, viewGroup, false));
    this.onItemClickListener = onItemClickListener;
    view = super.itemView;
    ButterKnife.bind(this, view);
  }

  @Override
  public void bind(TaskEntity taskEntity) {
    name.setText(taskEntity.getTitle());
    company.setText(taskEntity.getDescription());
    view.setOnClickListener(v -> onItemClickListener.onTaskClicked(taskEntity.getId()));
  }
}
