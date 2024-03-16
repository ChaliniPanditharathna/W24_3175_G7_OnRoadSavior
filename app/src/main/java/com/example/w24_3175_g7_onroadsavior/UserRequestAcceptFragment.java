package com.example.w24_3175_g7_onroadsavior;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserRequestAcceptFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserRequestAcceptFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_user_request_accept, container, false);
        TextView txtUsername = v.findViewById(R.id.textViewUserName);
        TextView txtLocation = v.findViewById(R.id.textViewLocation);
        TextView txtDescription = v.findViewById(R.id.textViewDescription);
        TextView txtBreakDownType = v.findViewById(R.id.textViewBreakDownType);
        TextView txtCreatedDate = v.findViewById(R.id.textViewCreatedDate);
        TextView txtPhoneNo = v.findViewById(R.id.textViewPhoneNo);
        Bundle bundle = this.getArguments();
        String username = bundle.getString("USERNAME");
        String phoneNo = bundle.getString("PHONENO");
        String createdDate = bundle.getString("CREATEDDATE");
        String location = bundle.getString("LOCATION");
        String description = bundle.getString("DESCRIPTION");
        String breakDownType = bundle.getString("BREAKDOWNTYPE");
        txtUsername.setText(username);
        txtBreakDownType.setText(breakDownType);
        txtLocation.setText(location);
        txtDescription.setText(description);

        txtCreatedDate.setText(createdDate);
        txtPhoneNo.setText(phoneNo);
        return  v;
    }
}