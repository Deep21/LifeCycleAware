package com.example.samfisher.lifecycleaware.view;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.example.samfisher.lifecycleaware.R;
import com.example.samfisher.lifecycleaware.TaskEntity;
import com.example.samfisher.lifecycleaware.view.adapter.SearchTaskListDiffCallback;
import com.example.samfisher.lifecycleaware.view.adapter.TaskListAdapter;
import com.example.samfisher.lifecycleaware.viewmodel.TaskDetailViewModel;
import com.example.samfisher.lifecycleaware.viewmodel.TaskDetailViewModelFactory;
import dagger.android.support.AndroidSupportInjection;
import java.util.List;
import javax.inject.Inject;


public class SearchTaskFragment extends Fragment {

  public static final String TAG = "SearchTaskFragment";
  TaskDetailViewModel detailViewModel;
  private Unbinder binder;
  @BindView(R.id.search_task)
  RecyclerView recyclerView;
  @Inject
  TaskListAdapter taskListAdapter;
  @Inject
  TaskDetailViewModelFactory detailViewModelFactory;

  public SearchTaskFragment() {
  }

  public static SearchTaskFragment newInstance() {
    SearchTaskFragment fragment = new SearchTaskFragment();
    Bundle args = new Bundle();
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    super.onCreateOptionsMenu(menu, inflater);
    inflater.inflate(R.menu.menu, menu);
    MenuItem menuItem = menu.findItem(R.id.action_search);
    SearchView searchView = (SearchView) menuItem.getActionView();
    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
      @Override
      public boolean onQueryTextSubmit(String query) {
        return false;
      }

      @Override
      public boolean onQueryTextChange(String newText) {
        detailViewModel.setPublishSubject(newText);
        return true;
      }
    });

  }

  /**
   * Observe search text
   */
  private void observeSearchQueries() {
    detailViewModel
        .getSearchLiveData()
        .observe(this, resource -> {
          taskListAdapter.setContactList((List<TaskEntity>) resource.data);
          taskListAdapter.notifyDataSetChanged();

        });
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    binder.unbind();
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    AndroidSupportInjection.inject(this);
    super.onCreate(savedInstanceState);
    detailViewModel = ViewModelProviders.of(getActivity(), detailViewModelFactory)
        .get(TaskDetailViewModel.class);
  }

  private void setupTaskListView() {
    recyclerView.setHasFixedSize(true);
    LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
    recyclerView.setLayoutManager(mLayoutManager);
    recyclerView.setAdapter(taskListAdapter);
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    setupTaskListView();
    observeSearchQueries();
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    setHasOptionsMenu(true);
    View v = inflater.inflate(R.layout.fragment_search_task, container, false);
    binder = ButterKnife.bind(this, v);
    return v;
  }


}
