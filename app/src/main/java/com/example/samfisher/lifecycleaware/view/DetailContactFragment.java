package com.example.samfisher.lifecycleaware.view;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.example.samfisher.lifecycleaware.ContactViewModel;
import com.example.samfisher.lifecycleaware.R;
import com.example.samfisher.lifecycleaware.di.ViewModelFactory;
import dagger.android.support.AndroidSupportInjection;
import javax.inject.Inject;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link DetailContactFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailContactFragment extends Fragment {

  // TODO: Rename parameter arguments, choose names that match
  public static final String TAG = "DetailContactFragment";
  RecyclerView recyclerView;
  FloatingActionButton fab;
  @Inject
  ViewModelFactory viewModelFactory;
  ContactViewModel model;
  private Unbinder binder;

  public DetailContactFragment() {
  }

  /**
   * Use this factory method to create a new instance of
   * this fragment using the provided parameters.
   *
   * @return A new instance of fragment DetailContactFragment.
   */
  // TODO: Rename and change types and number of parameters
  public static DetailContactFragment newInstance() {
    DetailContactFragment fragment = new DetailContactFragment();
    Bundle args = new Bundle();
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    AndroidSupportInjection.inject(this);
    super.onCreate(savedInstanceState);
    model = ViewModelProviders.of(getActivity(), viewModelFactory).get(ContactViewModel.class);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.fragment_contact_detail, container, false);
    binder = ButterKnife.bind(this, v);
    return v;
  }

  @Override
  public void onStart() {
    super.onStart();
    Bundle bundle = getArguments();
    if (bundle != null) {

    }
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    binder.unbind();
  }
}
