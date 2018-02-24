package com.example.samfisher.lifecycleaware.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.example.samfisher.lifecycleaware.R;
import com.example.samfisher.lifecycleaware.TaskEntity;
import com.example.samfisher.lifecycleaware.di.Resource;
import com.example.samfisher.lifecycleaware.view.adapter.TaskListAdapter;
import com.example.samfisher.lifecycleaware.viewmodel.TaskDetailViewModel;
import com.example.samfisher.lifecycleaware.viewmodel.TaskDetailViewModelFactory;
import com.jakewharton.rxbinding2.widget.RxSearchView;
import dagger.android.support.AndroidSupportInjection;
import io.reactivex.Flowable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;
import java.util.List;
import java.util.Observable;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;


public class SearchTaskFragment extends Fragment {

  // TODO: Rename parameter arguments, choose names that match
  // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
  private static final String ARG_PARAM1 = "param1";
  private static final String ARG_PARAM2 = "param2";
  public static final String TAG = "SearchTaskFragment";
  TaskDetailViewModel detailViewModel;
  @BindView(R.id.search)
  SearchView searchView;
  private Unbinder binder;
  @BindView(R.id.search_task)
  RecyclerView recyclerView;
  @Inject
  TaskListAdapter taskListAdapter;
  @Inject
  TaskDetailViewModelFactory detailViewModelFactory;
  private CompositeDisposable compositeDisposable = new CompositeDisposable();
  BehaviorSubject<String> source = BehaviorSubject.create();

  public SearchTaskFragment() {
    // Required empty public constructor
  }

  public static SearchTaskFragment newInstance() {
    SearchTaskFragment fragment = new SearchTaskFragment();
    Bundle args = new Bundle();
    fragment.setArguments(args);
    return fragment;
  }

  /**
   * Set up search view
   */
  private void setUpSearchView() {
    /*
    Disposable disposable = RxSearchView.queryTextChanges(searchView)
        .debounce(300, TimeUnit.MILLISECONDS)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<CharSequence>() {
          @Override
          public void accept(CharSequence charSequence) throws Exception {

          }
        });
    compositeDisposable.add(disposable);*/
    searchView.setOnQueryTextListener(new OnQueryTextListener() {
      @Override
      public boolean onQueryTextSubmit(String query) {
        return false;
      }

      @Override
      public boolean onQueryTextChange(String newText) {
        source.onNext(newText);
        
        return false;
      }
    });
    source
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribe(s -> {
          Log.d(TAG, "setUpSearchView: " + s);
        });
  }

  /**
   * Observe search text
   */
  private void observeSearchSequence() {
    detailViewModel
        .getData()
        .observe(this, taskEntityResource -> {
          Log.d(TAG, "observeSearchSequence: " + taskEntityResource);
/*         taskListAdapter.setContactList((List<TaskEntity>)taskEntityResource.data);
         taskListAdapter.notifyDataSetChanged();*/
        });
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    binder.unbind();
  }

  @Override
  public void onStop() {
    super.onStop();
    compositeDisposable.dispose();
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    AndroidSupportInjection.inject(this);
    super.onCreate(savedInstanceState);
    detailViewModel = ViewModelProviders.of(getActivity(), detailViewModelFactory)
        .get(TaskDetailViewModel.class);
  }

  private void setupContactListView() {
    recyclerView.setHasFixedSize(true);
    LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
    recyclerView.setLayoutManager(mLayoutManager);
    recyclerView.setAdapter(taskListAdapter);
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    setUpSearchView();
    setupContactListView();
    /*
    observeSearchSequence();
    detailViewModel.searchTask().observe(this, new Observer<Resource>() {
      @Override
      public void onChanged(@Nullable Resource resource) {
        Log.d(TAG, "onChanged: " + resource);
      }
    });*/
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View v = inflater.inflate(R.layout.fragment_search_task, container, false);
    binder = ButterKnife.bind(this, v);
    return v;
  }


}
