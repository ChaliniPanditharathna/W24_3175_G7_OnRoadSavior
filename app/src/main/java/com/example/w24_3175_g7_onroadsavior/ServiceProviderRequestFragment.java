package com.example.w24_3175_g7_onroadsavior;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.w24_3175_g7_onroadsavior.Database.DBHelper;
import com.example.w24_3175_g7_onroadsavior.Interface.ProviderRequestInterface;
import com.example.w24_3175_g7_onroadsavior.Model.RequestDetails;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ServiceProviderHistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ServiceProviderRequestFragment extends Fragment implements ProviderRequestInterface {
    RecyclerView recyclerView;
    ArrayList<RequestDetails> requestDetails;
    DBHelper dbHelper;
    ProviderRequetsAdapter requetsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_service_provider_request, container, false);

        dbHelper = new DBHelper(view.getContext());
        requestDetails = new ArrayList<>();
        recyclerView = view.findViewById(R.id.requestrecylerview);
        requetsAdapter = new ProviderRequetsAdapter(view.getContext(), requestDetails, this);
        recyclerView.setAdapter(requetsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        displayData();

        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
        return view;
    }

    public void displayData() {
        Cursor cursor = dbHelper.getRequestData();
        if (cursor.getCount() == 0) {
            Toast.makeText(ServiceProviderRequestFragment.this.getContext(), "No entry exists", Toast.LENGTH_SHORT).show();
            return;
        } else {
            while (cursor.moveToNext()) {
                RequestDetails req = new RequestDetails(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7),
                        cursor.getString(8),
                        cursor.getString(9),
                        cursor.getString(10),
                        cursor.getString(11)

                );

                requestDetails.add(req);

            }
        }
    }

    @Override
    public void OnItemClick(int position) {
        Bundle result = new Bundle();
        RequestDetails req = requestDetails.get(position);
        result.putString("USERNAME", req.getUserName());
        result.putString("USERID", req.getUserId());
        result.putString("BREAKDOWNTYPE", req.getBreakDownType());
        result.putString("PHONENO", req.getPhoneNo());
        result.putString("CREATEDDATE", req.getCreatedDate());
        result.putString("LOCATION", req.getLocation());
        result.putString("DESCRIPTION", req.getDescription());

        if (req.getStatus().equals("Accept")) {
            if (req.getStatus().equals("Done")) {
                result.putString("MESSAGE", "Successfully Done");
            }
            if (req.getStatus().equals("Reject")) {
                result.putString("MESSAGE", "You rejected this request");
            }

            Fragment providerRequestHistoryFragment = new ProviderRequestHistoryFragment();
            providerRequestHistoryFragment.setArguments(result);
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.frame_layout, providerRequestHistoryFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
        if (req.getStatus().equals("Pending")) {
            result.putString("BREAKDOWNREQUESTID", req.getBreakDownRequestId());

            Fragment userRequestAcceptFragment = new UserRequestAcceptFragment();
            userRequestAcceptFragment.setArguments(result);
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.frame_layout, userRequestAcceptFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }

    }

}