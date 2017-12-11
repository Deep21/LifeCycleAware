package com.example.samfisher.lifecycleaware.view;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.samfisher.lifecycleaware.Contact;
import com.example.samfisher.lifecycleaware.MyViewModel;
import com.example.samfisher.lifecycleaware.R;
import com.example.samfisher.lifecycleaware.di.Resource;
import com.example.samfisher.lifecycleaware.di.ViewModelFactory;

import java.util.List;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link ContactFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContactFragment extends Fragment implements Injectable {
    @Inject
    ViewModelFactory viewModelFactory;
    Button btn;

    public ContactFragment() {
    }

    public static ContactFragment newInstance() {
        ContactFragment fragment = new ContactFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MyViewModel model = ViewModelProviders.of(getActivity(), viewModelFactory).get(MyViewModel.class);
        btn.setOnClickListener(v -> {
            List<Contact> contactList = model.getContacts();
            Timber.d(contactList + "");
            Exception exception = new Exception();
            model.error.setValue(Resource.error("Error", exception));

        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_contact, container, false);
        btn = v.findViewById(R.id.button);
        return v;
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }
}
