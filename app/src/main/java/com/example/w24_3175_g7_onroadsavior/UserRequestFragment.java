package com.example.w24_3175_g7_onroadsavior;

import static com.example.w24_3175_g7_onroadsavior.R.id.imageViewMessage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserRequestFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserRequestFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_user_request, container, false);

        Bundle bundle = this.getArguments();
        String username = bundle.getString("USERNAME");
        String phoneNo = bundle.getString("PHONENO");
        String createdDate = bundle.getString("CREATEDDATE");
        String location = bundle.getString("LOCATION");
        String description = bundle.getString("DESCRIPTION");
        String breakDownType = bundle.getString("BREAKDOWNTYPE");
        String message= bundle.getString("MESSAGE");
        String action = bundle.getString("ACTION");
        ImageView actionImg = v.findViewById(R.id.imageViewMessage);
        
        TextView txtUsername = v.findViewById(R.id.textViewUserName);
        TextView txtLocation = v.findViewById(R.id.textViewLocation);
        TextView txtDescription = v.findViewById(R.id.textViewDescription);
        TextView txtBreakDownType = v.findViewById(R.id.textViewBreakDownType);
        TextView txtCreatedDate = v.findViewById(R.id.textViewCreatedDate);
        TextView txtPhoneNo = v.findViewById(R.id.textViewPhoneNo);
        TextView txtMessage = v.findViewById(R.id.textViewMessage);

        if(action.equals("Accept")){
            actionImg.setImageResource(R.drawable.acceptrequest);
        }
        if(action.equals("Reject")){
            actionImg.setImageResource(R.drawable.rejectrequest);
        }
        txtUsername.setText(username);
        txtBreakDownType.setText(breakDownType);
        txtLocation.setText(location);
        txtDescription.setText(description);

        txtCreatedDate.setText(createdDate);
        txtPhoneNo.setText(phoneNo);
        txtMessage.setText(message);
        return v;
    }
}