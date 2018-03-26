package com.example.samfisher.lifecycleaware.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.example.samfisher.lifecycleaware.TaskEntity;
import com.example.samfisher.lifecycleaware.view.adapter.TaskListAdapter;
import com.example.samfisher.lifecycleaware.viewmodel.TaskViewModel;
import com.example.samfisher.lifecycleaware.R;
import com.example.samfisher.lifecycleaware.di.Resource;
import com.example.samfisher.lifecycleaware.viewmodel.ContactViewModelFactory;
import dagger.android.support.AndroidSupportInjection;
import java.util.List;
import javax.inject.Inject;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link TaskListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaskListFragment extends Fragment {

  public static final String TAG = "TaskListFragment";
  @Inject
  ContactViewModelFactory contactViewModelFactory;
  @Inject
  TaskListAdapter taskListAdapter;
  @BindView(R.id.rc_contact)
  RecyclerView recyclerView;
  private Unbinder binder;
  private TaskViewModel taskViewModel;

  public TaskListFragment() {
  }

  public static TaskListFragment newInstance() {
    return new TaskListFragment();
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.fragment_contact_list, container, false);
    binder = ButterKnife.bind(this, v);
    setupContactListView();
    setOnClickListener();
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
    taskViewModel = TaskListActivity.obtainViewModel(getActivity(), contactViewModelFactory);
    observeTasks();
  }

  @Override
  public void onStart() {
    super.onStart();
  }

  /**
   *
   */
  private void setupContactListView() {
    recyclerView.setHasFixedSize(true);
    LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
    recyclerView.setLayoutManager(mLayoutManager);
  }

  /**
   * Set task id
   *
   */
  private void setOnClickListener() {
    taskListAdapter.setOnItemClickListener(position -> {
      int id = taskListAdapter.getContactList().get(position).getId();
      Log.d(TAG, "setOnClickListener position: " + position);
      Log.d(TAG, "setOnClickListener position: " + taskListAdapter.getContactList().get(position).getId());
      taskViewModel.setTaskId(id);
    });
    recyclerView.setAdapter(taskListAdapter);
  }

  /**
   * Observe tasks changes
   */
  private void observeTasks() {
    taskViewModel
        .getTasks()
        .observe(this, this::handleState);
  }

  /**
   * Handle state emitted by Task LiveData
   */
  private void handleState(Resource resource) {
    switch (resource.status) {
      case SUCCESS:
        taskListAdapter.setContactList((List<TaskEntity>) resource.data);
        taskListAdapter.notifyDataSetChanged();
        break;

      case ERROR:
        Log.d(TAG, "handleState: " + "error");
        break;

      case EMPTY:
        break;

      case LOADING:
        break;
    }
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    binder.unbind();
  }
}
