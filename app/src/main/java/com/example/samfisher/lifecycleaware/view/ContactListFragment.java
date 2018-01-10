package com.example.samfisher.lifecycleaware.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.example.samfisher.lifecycleaware.Contact;
import com.example.samfisher.lifecycleaware.ContactViewModel;
import com.example.samfisher.lifecycleaware.R;
import com.example.samfisher.lifecycleaware.di.Resource;
import com.example.samfisher.lifecycleaware.di.ViewModelFactory;
import dagger.android.support.AndroidSupportInjection;
import java.util.List;
import javax.inject.Inject;
import timber.log.Timber;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link ContactListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContactListFragment extends Fragment {

  public static final String TAG = "ContactListFragment";
  @Inject
  ViewModelFactory viewModelFactory;
  @Inject
  ContactListAdapter contactListAdapter;
  @BindView(R.id.rc_contact)
  RecyclerView recyclerView;

  private Unbinder binder;
  private ContactViewModel contactViewModel;

  public ContactListFragment() {
  }

  // TODO: Rename and change types and number of parameters
  public static ContactListFragment newInstance() {
    ContactListFragment fragment = new ContactListFragment();
    return fragment;
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.fragment_contact_list, container, false);
    binder = ButterKnife.bind(this, v);
    setupContactListView();
    return v;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    AndroidSupportInjection.inject(this);
    super.onCreate(savedInstanceState);
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    contactViewModel = MainActivity.obtainViewModel(getActivity(), viewModelFactory);
    observeContacts();
  }

  /**
   *
   */
  private void setupContactListView() {
    recyclerView.setHasFixedSize(true);
    LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
    recyclerView.setLayoutManager(mLayoutManager);
    contactListAdapter.setOnItemClickListener(position -> contactViewModel.setContactId(position));
    recyclerView.setAdapter(contactListAdapter);
  }

  /**
   * Observe contact changes
   *
   */
  private void observeContacts() {
    contactViewModel
        .getContacts()
        .observe(this, this::handleState);
  }

  /**
   * @param resource
   */
  private void handleState(Resource resource) {
    switch (resource.status) {
      case SUCCESS:
        Timber.i(resource + "");
        contactListAdapter.setContactList((List<Contact>) resource.data);
        contactListAdapter.notifyDataSetChanged();
        break;

      case ERROR:
        Timber.e(resource + "");
        break;

      case EMPTY:
        Timber.i(resource + "");
        break;

      case LOADING:
        Timber.e(resource + "");
        break;
    }
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    binder.unbind();
  }
}
