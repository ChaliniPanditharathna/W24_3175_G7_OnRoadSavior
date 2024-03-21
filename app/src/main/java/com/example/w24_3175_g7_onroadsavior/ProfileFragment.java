package com.example.w24_3175_g7_onroadsavior;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.w24_3175_g7_onroadsavior.Database.DBHelper;
import com.google.firebase.auth.FirebaseUser;


public class ProfileFragment extends Fragment {

    //initialize local db
    DBHelper DB;

    TextView txtViewFullName, txtViewUserName;
    EditText fullName, email, contactNum, location, serviceType;
    String fullNameVal, usernameVal, emailVal, contactNumVal, userTypeVal, locationVal, serviceTypeVal;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);

        txtViewFullName = view.findViewById(R.id.txtViewFullName);
        txtViewUserName = view.findViewById(R.id.txtViewUsername);

        fullName = view.findViewById(R.id.editTextFullName);
        email = view.findViewById(R.id.editTextEmail);
        contactNum = view.findViewById(R.id.editTextContactNum);
        location = view.findViewById(R.id.editTextLocation);
        serviceType = view.findViewById(R.id.editTextServiceType);


        Bundle args = getArguments();

        if (args != null) {
            FirebaseUser currentUser = args.getParcelable("CURRENT_USER");
            if (currentUser != null) {
                DB = new DBHelper(view.getContext());
                UserHelperClass user = DB.getUserData(currentUser.getUid());

                if(user.getUserType().equalsIgnoreCase("Service Provider")){

                    locationVal = DB.getServiceProviderData(currentUser.getUid()).getLocation();
                    serviceTypeVal = DB.getServiceProviderData(currentUser.getUid()).getServiceType();
                }

                displayUserProfile(user);
            } else {
                displayUserNotAvailableMessage();
            }
        }

        return view;
    }

    private void displayUserProfile(UserHelperClass user){


        fullNameVal = user.getFullName();
        usernameVal = user.getUserName();
        emailVal = user.getEmail();
        contactNumVal = user.getContactNumber();
        userTypeVal = user.getUserType();

        txtViewFullName.setText(fullNameVal);
        txtViewUserName.setText(usernameVal);
        fullName.setText(fullNameVal);
        email.setText(emailVal);
        contactNum.setText(contactNumVal);


        if(userTypeVal.equalsIgnoreCase("Service Requester")){

            serviceType.setVisibility(View.GONE);
            location.setVisibility(View.GONE);

        } else if(userTypeVal.equalsIgnoreCase("Service Provider")){

            serviceType.setVisibility(View.VISIBLE);
            location.setVisibility(View.VISIBLE);

            serviceType.setText(serviceTypeVal);
            location.setText(locationVal);
        }

    }

    private void displayUserNotAvailableMessage() {
        Toast.makeText(getActivity(), "User data is not available", Toast.LENGTH_SHORT).show();
    }
}