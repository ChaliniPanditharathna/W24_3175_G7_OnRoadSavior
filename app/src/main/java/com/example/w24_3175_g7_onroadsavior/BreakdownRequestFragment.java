package com.example.w24_3175_g7_onroadsavior;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.w24_3175_g7_onroadsavior.Database.DBHelper;
import com.example.w24_3175_g7_onroadsavior.Model.BreakdownRequestDetails;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class BreakdownRequestFragment extends Fragment {

    DBHelper dbHelper;
    String userId;
    String providerId;
    String description;
    String status;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_breakdown_request, container, false);
        dbHelper = new DBHelper(view.getContext());
        Button buttonRequestSubmit = view.findViewById(R.id.buttonRequestSubmit);
        EditText editTextDescription = view.findViewById(R.id.editTextRequestDescription);

        Bundle bundle = this.getArguments();
        String breakdownType = bundle.getString("BREAKDOWNTYPE");
        String address = bundle.getString("CURRENTLOCATION");
        String imageUrl = bundle.getString("IMAGE_URL");

        buttonRequestSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String currentDateAndTime = sdf.format(new Date());

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                if (user != null) {
                    userId = user.getUid();
                } else {
                    Log.e("Error", "Can't get user Id");
                }
                providerId = "5utKiBSA4Ec60prqeE1kGs56uq63";

                description = editTextDescription.getText().toString();

                status = "Pending";

                dbHelper.addRequest(currentDateAndTime, currentDateAndTime, userId, providerId, breakdownType, address, description, imageUrl, status);

                // Show results in a popup dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Breakdown Request Details");
                builder.setMessage("Created Date: " + currentDateAndTime + "\n\n" +
                        "Updated Date: " + currentDateAndTime + "\n\n" +
                        "User ID: " + userId + "\n\n" +
                        "Provider ID: " + providerId + "\n\n" +
                        "Breakdown Type: " + breakdownType + "\n\n" +
                        "Current Location: " + address + "\n\n" +
                        "Description: " + description + "\n\n" +
                        "Image: " + imageUrl +"\n\n" +
                        "Status: " + status);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();

                Fragment fragment = new HistroyFragment();
                replaceFragment(fragment);
            }
        });

        return view;
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}