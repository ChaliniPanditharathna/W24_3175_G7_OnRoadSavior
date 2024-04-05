package com.example.w24_3175_g7_onroadsavior;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.w24_3175_g7_onroadsavior.Database.DBHelper;
import com.example.w24_3175_g7_onroadsavior.Model.BreakdownRequestDetails;
import com.example.w24_3175_g7_onroadsavior.Model.RequestDetails;
import com.example.w24_3175_g7_onroadsavior.adapter.AdapterBreakdownRequestDetails;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HistroyFragment extends Fragment {

    private RecyclerView recyclerViewHistory;
    private AdapterBreakdownRequestDetails adapterBreakdownRequestDetails;
    private List<BreakdownRequestDetails> breakdownRequestDetailsList;
    DBHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_histroy, container, false);
        dbHelper = new DBHelper(view.getContext());
        recyclerViewHistory = view.findViewById(R.id.recyclerViewHistory);
        recyclerViewHistory.setLayoutManager(new LinearLayoutManager(getContext()));
        breakdownRequestDetailsList = new ArrayList<>();
        adapterBreakdownRequestDetails = new AdapterBreakdownRequestDetails(breakdownRequestDetailsList);

        AdapterBreakdownRequestDetails.OnRatingProvidedListener ratingListener = new AdapterBreakdownRequestDetails.OnRatingProvidedListener() {
            @Override
            public void onRatingProvided(int position, float rating) {
                BreakdownRequestDetails request = breakdownRequestDetailsList.get(position);
                dbHelper.updateProviderRating(request.getProviderId(), rating);
                request.setProviderRating(rating);
                adapterBreakdownRequestDetails.notifyItemChanged(position);
            }
        };

        adapterBreakdownRequestDetails = new AdapterBreakdownRequestDetails(breakdownRequestDetailsList, ratingListener);

        recyclerViewHistory.setAdapter(adapterBreakdownRequestDetails);

        Cursor cursor = dbHelper.getBreakdownRequestData();
        if (cursor != null && cursor.moveToFirst()) {
            int createdDateIndex = cursor.getColumnIndex("Created_Date");
            int updatedDateIndex = cursor.getColumnIndex("Updated_Date");
            int userIdIndex = cursor.getColumnIndex("User_ID");
            int providerIdIndex = cursor.getColumnIndex("Provider_ID");
            int breakdownTypeIndex = cursor.getColumnIndex("Breakdown_Type");
            int locationIndex = cursor.getColumnIndex("Location");
            int descriptionIndex = cursor.getColumnIndex("Description");
            int imageIndex = cursor.getColumnIndex("Image");
            int statusIndex = cursor.getColumnIndex("Status");

            do {
                String createdDate = (createdDateIndex != -1) ? cursor.getString(createdDateIndex) : "";
                String updatedDate = (updatedDateIndex != -1) ? cursor.getString(updatedDateIndex) : "";
                String userId = (userIdIndex != -1) ? cursor.getString(userIdIndex) : "";
                String providerId = (providerIdIndex != -1) ? cursor.getString(providerIdIndex) : "";
                String breakdownType = (breakdownTypeIndex != -1) ? cursor.getString(breakdownTypeIndex) : "";
                String location = (locationIndex != -1) ? cursor.getString(locationIndex) : "";
                String description = (descriptionIndex != -1) ? cursor.getString(descriptionIndex) : "";
                String image = (imageIndex != -1) ? cursor.getString(imageIndex) : "";
                String status = (statusIndex != -1) ? cursor.getString(statusIndex) : "";
               // String status ="Completed";

                float providerRating = dbHelper.getProviderRating(providerId);
                BreakdownRequestDetails req = new BreakdownRequestDetails(
                        createdDate,
                        updatedDate,
                        userId,
                        providerId,
                        breakdownType,
                        location,
                        description,
                        image,
                        status,
                        providerRating
                );
                if (status.equals("Completed") && providerRating == 0.0) {
                    showRatingDialog(providerId);
                }
                breakdownRequestDetailsList.add(req);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return view;
    }

    public void updateRequestDetails(List<BreakdownRequestDetails> newRequestDetailsList) {
        breakdownRequestDetailsList.clear();
        breakdownRequestDetailsList.addAll(newRequestDetailsList);
        adapterBreakdownRequestDetails.notifyDataSetChanged();
    }

    private void showRatingDialog(String providerId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.rating_dialog, null);

        RatingBar ratingBar = dialogView.findViewById(R.id.dialog_rating_bar);

        builder.setView(dialogView)
                .setTitle("Rate Provider")
                .setMessage("Please rate your experience with the provider.")
                .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        float userRating = ratingBar.getRating();
                        dbHelper.updateProviderRating(providerId, userRating);

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Handle cancellation
                        Toast.makeText(getContext(), "Rating cancelled", Toast.LENGTH_SHORT).show();
                    }
                })
                .create().show();
    }
}