package com.example.samfisher.lifecycleaware.view;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.example.samfisher.lifecycleaware.R;
import com.example.samfisher.lifecycleaware.TaskEntity;
import com.example.samfisher.lifecycleaware.viewmodel.TaskDetailViewModel;
import com.example.samfisher.lifecycleaware.viewmodel.TaskDetailViewModelFactory;
import com.jakewharton.rxbinding2.widget.RxSearchView;
import dagger.android.support.AndroidSupportInjection;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link DetailTaskFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailTaskFragment extends Fragment {

  public static final String TAG = "DetailTaskFragment";
  public static final String TASK_ID = "TASK_ID";
  @BindView(R.id.title)
  TextView title;
  @BindView(R.id.description)
  TextView description;
  @Inject
  TaskDetailViewModelFactory detailViewModelFactory;
  TaskDetailViewModel detailViewModel;
  @BindView(R.id.search)
  SearchView searchView;
  private Unbinder binder;
  private Disposable disposable;
  private CompositeDisposable compositeDisposable = new CompositeDisposable();

  public DetailTaskFragment() {
  }

  /**
   * Use this factory method to create a new instance of
   * this fragment using the provided parameters.
   *
   * @return A new instance of fragment DetailTaskFragment.
   */
  public static DetailTaskFragment newInstance(int taskId) {
    DetailTaskFragment fragment = new DetailTaskFragment();
    Bundle args = new Bundle();
    args.putInt(TASK_ID, taskId);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    AndroidSupportInjection.inject(this);
    super.onCreate(savedInstanceState);
    detailViewModel = ViewModelProviders.of(getActivity(), detailViewModelFactory)
        .get(TaskDetailViewModel.class);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.fragment_contact_detail, container, false);
    binder = ButterKnife.bind(this, v);
    return v;
  }

  @Override
  public void onStart() {
    super.onStart();
  }

  private void observeTask() {
    /*detailViewModel.getTask().observe(this, resource -> {
      //Log.d(TAG, "observeTask: " + ((TaskEntity)resource.data));
      //title.setText(((TaskEntity) resource.data).getTitle());
      //description.setText(((TaskEntity) resource.data).getDescription());
    });
    */
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    binder.unbind();
  }

  /**
   * Set up search view
   */
  private void setUpSearchView() {
/*    searchView.setOnQueryTextListener(new OnQueryTextListener() {
      @Override
      public boolean onQueryTextSubmit(String query) {
        return false;
      }

      @Override
      public boolean onQueryTextChange(String newText) {
        Log.d(TAG, "onQueryTextSubmit: " + newText);
        detailViewModel.loadSearchTask(newText);
        return false;
      }
    });*/

    disposable = RxSearchView.queryTextChanges(searchView)
        .debounce(200, TimeUnit.MILLISECONDS)
        .doOnNext(new Consumer<CharSequence>() {
          @Override
          public void accept(CharSequence charSequence) throws Exception {
            Log.d(TAG, "doOnNexy: " + Thread.currentThread().getName());
          }
        })
        .switchMap(charSequence -> {
          Log.d(TAG, "apply: " + charSequence);
          Log.d(TAG, "Inside switch map thread: " + Thread.currentThread().getName());
          return Observable.just(charSequence).delay(2, TimeUnit.SECONDS);
        })
        .doOnSubscribe(disposable -> Log.d(TAG, "doOnSubscribe: " + disposable))
        //.subscribeOn(Schedulers.computation())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(charSequence -> {
          Log.d(TAG, "subscribe: " + charSequence);
          Log.d(TAG, "Thread: " + Thread.currentThread().getName());
          detailViewModel.setCharSequence(charSequence);
        });
    Log.d(TAG, "disposable: " + disposable);
    compositeDisposable.add(disposable);
  }

  /**
   * Observe search text
   */
  private void observeSearchSequence() {
    detailViewModel
        .searchTask()
        .observe(this, taskEntityResource -> {
          if (taskEntityResource != null) {
            Log.d(TAG, "observeSearchSequence: " + taskEntityResource.data);
          }
        });
  }

  @Override
  public void onStop() {
    super.onStop();
    compositeDisposable.dispose();
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    Bundle bundle = getArguments();
    setUpSearchView();
    observeSearchSequence();
    if (bundle != null) {
      int taskId = bundle.getInt(TASK_ID);
      detailViewModel.setTaskId(taskId);
      observeTask();
    }
  }
}
