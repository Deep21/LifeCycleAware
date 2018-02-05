package com.example.samfisher.lifecycleaware.view;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.EditText;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.example.samfisher.lifecycleaware.model.Task;
import com.example.samfisher.lifecycleaware.viewmodel.AddTaskViewModel;
import com.example.samfisher.lifecycleaware.R;
import com.example.samfisher.lifecycleaware.viewmodel.ContactAddViewModelFactory;
import dagger.android.support.AndroidSupportInjection;
import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link TaskAddFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaskAddFragment extends Fragment {

  // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
  public static final String TAG = "TaskAddFragment";
  AddTaskViewModel addContactViewModel;
  @Inject
  ContactAddViewModelFactory contactAddViewModelFactory;
  private Unbinder binder;
  @BindView(R.id.title)
  EditText title;
  @BindView(R.id.description)
  EditText description;

  public TaskAddFragment() {
  }

  /**
   * Use this factory method to create a new instance of
   * this fragment using the provided parameters.
   *
   * @return A new instance of fragment TaskAddFragment.
   */
  public static TaskAddFragment newInstance() {
    TaskAddFragment fragment = new TaskAddFragment();
    Bundle args = new Bundle();
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    AndroidSupportInjection.inject(this);
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
    }

  }

  @OnClick(R.id.button)
  public void onClick() {
    Task task = new Task();
    task.setTitle(title.getText().toString());
    task.setDescription(description.getText().toString());
    addContactViewModel.setTask(task);
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    addContactViewModel = ViewModelProviders.of(getActivity(), contactAddViewModelFactory).get(AddTaskViewModel.class);
    addContactViewModel
        .saveTask()
        .observe(this, resource -> Log.d(TAG, "onChanged: " + resource));
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View v = inflater.inflate(R.layout.fragment_contact_add, container, false);
    binder = ButterKnife.bind(this, v);
    return v;
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    binder.unbind();
  }
}
