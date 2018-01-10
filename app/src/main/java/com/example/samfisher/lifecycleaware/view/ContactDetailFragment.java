package com.example.samfisher.lifecycleaware.view;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.example.samfisher.lifecycleaware.Contact;
import com.example.samfisher.lifecycleaware.ContactViewModel;
import com.example.samfisher.lifecycleaware.R;
import com.example.samfisher.lifecycleaware.di.ViewModelFactory;
import javax.inject.Inject;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link ContactDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContactDetailFragment extends Fragment {

  // TODO: Rename parameter arguments, choose names that match
  public static final String TAG = "ContactDetailFragment";
  RecyclerView recyclerView;
  FloatingActionButton fab;
  @Inject
  ViewModelFactory viewModelFactory;

  // TODO: Rename and change types of parameters
  private String mParam1;
  private String mParam2;
  ContactViewModel model;
  private Unbinder binder;

  private void observeOnContactSelected() {
    model.getContactById().observe(this,
        resource -> Log
            .d(TAG, "observeOnContactSelected: " + ((Contact) resource.data).getCompany()));
  }

  public ContactDetailFragment() {
    // Required empty public constructor
  }

  /**
   * Use this factory method to create a new instance of
   * this fragment using the provided parameters.
   *
   * @return A new instance of fragment ContactDetailFragment.
   */
  // TODO: Rename and change types and number of parameters
  public static ContactDetailFragment newInstance() {
    ContactDetailFragment fragment = new ContactDetailFragment();
    Bundle args = new Bundle();
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    model = ViewModelProviders.of(getActivity(), viewModelFactory).get(ContactViewModel.class);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.fragment_contact_detail, container, false);
    binder =  ButterKnife.bind(this, v);
    return v;
  }


  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
  }

  @Override
  public void onDetach() {
    super.onDetach();
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    binder.unbind();
  }
}
