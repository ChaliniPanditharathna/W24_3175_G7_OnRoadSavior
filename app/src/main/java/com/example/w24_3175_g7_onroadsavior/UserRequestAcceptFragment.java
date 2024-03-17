package com.example.w24_3175_g7_onroadsavior;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.w24_3175_g7_onroadsavior.Database.DBHelper;

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
        DBHelper dbHelper = new DBHelper(v.getContext());
        TextView txtUsername = v.findViewById(R.id.textViewUserName);
        TextView txtLocation = v.findViewById(R.id.textViewLocation);
        TextView txtDescription = v.findViewById(R.id.textViewDescription);
        TextView txtBreakDownType = v.findViewById(R.id.textViewBreakDownType);
        TextView txtCreatedDate = v.findViewById(R.id.textViewCreatedDate);
        TextView txtPhoneNo = v.findViewById(R.id.textViewPhoneNo);
        Button btnAccept = v.findViewById(R.id.buttonAcceptRequest);
        Button btnReject = v.findViewById(R.id.buttonRejectRequest);
        Bundle bundle = this.getArguments();
        String username = bundle.getString("USERNAME");
        String phoneNo = bundle.getString("PHONENO");
        String createdDate = bundle.getString("CREATEDDATE");
        String location = bundle.getString("LOCATION");
        String description = bundle.getString("DESCRIPTION");
        String breakDownType = bundle.getString("BREAKDOWNTYPE");
        String breakDownRequestId= bundle.getString("BREAKDOWNREQUESTID");
        txtUsername.setText(username);
        txtBreakDownType.setText(breakDownType);
        txtLocation.setText(location);
        txtDescription.setText(description);

        txtCreatedDate.setText(createdDate);
        txtPhoneNo.setText(phoneNo);

        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               dbHelper.acceptRequest(Integer.parseInt(breakDownRequestId));
               Toast.makeText(v.getContext(), "Successfully accept the Request", Toast.LENGTH_SHORT).show();
                btnReject.setEnabled(false);
            }
        });
        btnReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.rejectRequest(Integer.parseInt(breakDownRequestId));
                Toast.makeText(v.getContext(), "Successfully reject the Request", Toast.LENGTH_SHORT).show();
                btnAccept.setEnabled(false);
            }
        });


        return  v;
    }
}