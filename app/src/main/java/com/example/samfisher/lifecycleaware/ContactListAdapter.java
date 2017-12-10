package com.example.samfisher.lifecycleaware;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by Samfisher on 17/09/2017.
 */

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ViewHolder> {

  private List<ContactModel> contactList;
  private Context context;
  private OnItemClickListener onItemClickListener;

  public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
    this.onItemClickListener = onItemClickListener;
  }

  public interface OnItemClickListener {

    void onUserItemClicked(int position);
  }

  @Inject
  ContactListAdapter(Context context) {
    this.context = context;
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater
        .from(context).inflate(R.layout.fragment_contact_list, parent, false);
    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    final int pos = position;

    holder.name.setText(contactList.get(position).getFirstname());
    holder.company.setText(contactList.get(position).getCompany());
    holder.itemView.setOnClickListener(v -> onItemClickListener.onUserItemClicked(pos));

  }

  @Override
  public int getItemCount() {
    return contactList != null ? contactList.size() : 0;
  }

  public void setContactList(List<ContactModel> contactList) {
    this.contactList = contactList;
  }

  class ViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.name)
    TextView name;

    @BindView(R.id.company)
    TextView company;

    View itemView;

    ViewHolder(View itemView) {
      super(itemView);
      this.itemView = itemView;
      ButterKnife.bind(this, itemView);
    }
  }
}
