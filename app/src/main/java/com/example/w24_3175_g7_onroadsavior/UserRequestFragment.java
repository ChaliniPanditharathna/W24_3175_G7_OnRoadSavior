package com.example.w24_3175_g7_onroadsavior;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.w24_3175_g7_onroadsavior.database.DBHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserRequestFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserRequestFragment extends Fragment {
    StorageReference storageReference;
    FirebaseStorage storage;
    String providerId;
    DBHelper dbHelper;
    String providerLocation;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_user_request, container, false);
        dbHelper = new DBHelper(v.getContext());

        Bundle bundle = this.getArguments();
        String username = bundle.getString("USERNAME");
        String userId = bundle.getString("USERID");
        String phoneNo = bundle.getString("PHONENO");
        String createdDate = bundle.getString("CREATEDDATE");
        String location = bundle.getString("LOCATION");
        String description = bundle.getString("DESCRIPTION");
        String breakDownType = bundle.getString("BREAKDOWNTYPE");
        String message= bundle.getString("MESSAGE");
        String imageUrl= bundle.getString("IMAGEURL");
        String action = bundle.getString("ACTION");
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        TextView txtUsername = v.findViewById(R.id.textViewUserName);
        TextView txtLocation = v.findViewById(R.id.textViewLocation);
        TextView txtDescription = v.findViewById(R.id.textViewDescription);
        TextView txtBreakDownType = v.findViewById(R.id.textViewBreakDownType);
        TextView txtCreatedDate = v.findViewById(R.id.textViewCreatedDate);
        TextView txtPhoneNo = v.findViewById(R.id.textViewPhoneNo);
        TextView txtMessage = v.findViewById(R.id.textViewMessage);
        ImageView imageViewDesition = v.findViewById(R.id.imageViewDesion);
        ImageView userPic = v.findViewById(R.id.imageViewUserIcon);
        ImageView userBreakdownPic = v.findViewById(R.id.imageViewBreakDown);
        Button buttonTrackRoute = v.findViewById(R.id.trackRoute);


        StorageReference profileImageRef = storageReference.child("profile_images/" +userId+ ".jpg");
        // Check if the ImageView is not null before loading the image
        if (userPic != null) {
            profileImageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                // Load the profile image using Picasso
                Picasso.get().load(uri).into(userPic);
            }).addOnFailureListener(exception -> {
                // Handle failure to load profile image
                Log.e("UserRequestAcceptFragment", "Failed to load profile image: " + exception.getMessage());
            });
        } else {
            Log.e("UserRequestAcceptFragment", "ImageView is null");
        }

        String[] parts = imageUrl.split("/");
        String imageId = parts[parts.length - 1];
        if (userBreakdownPic != null) {
            StorageReference breakdownImageRef = storageReference.child("images/" + imageId);
            breakdownImageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                // Load the breakdown image using Picasso
                Picasso.get().load(uri).into(userBreakdownPic);
            }).addOnFailureListener(exception -> {
                // Handle failure to load breakdown image
                Log.e("UserRequestFragment", "Failed to load breakdown image: " + exception.getMessage());
            });
        } else {
            Log.e("UserRequestFragment", "Breakdown ImageView is null");
        }

        if(action.equals("Accept")){
            imageViewDesition.setImageResource(R.drawable.accepticon);
        }
        if(action.equals("Reject")){
            imageViewDesition.setImageResource(R.drawable.rejecticon);
            buttonTrackRoute.setEnabled(false);
            buttonTrackRoute.setText("Track Route");
            buttonTrackRoute.setTextColor(Color.BLACK);
            buttonTrackRoute.setBackgroundColor(Color.GRAY);
        }
        txtUsername.setText(username);
        txtBreakDownType.setText(breakDownType);
        txtLocation.setText(location);
        txtDescription.setText(description);

        txtCreatedDate.setText(createdDate);
        txtPhoneNo.setText(phoneNo);
        txtMessage.setText(message);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            providerId = user.getUid();
        } else {
            Log.e("Error", "Can't get user Id");
        }

        providerLocation = dbHelper.getProviderLocation(providerId);

        buttonTrackRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("providerLocation" ,providerLocation);
                Log.d("userLocation", location);

                Uri gmmIntentUri = Uri.parse("https://www.google.com/maps/dir/?api=1&origin=" + providerLocation + "&destination=" + location);

                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");

                getContext().startActivity(mapIntent);
            }
        });
        return v;
    }
}