package com.example.w24_3175_g7_onroadsavior;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.w24_3175_g7_onroadsavior.Database.DBHelper;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.UUID;


public class ProfileFragment extends Fragment {

    FirebaseUser currentUser;
    //initialize local db
    DBHelper DB;

    TextView txtViewFullName, txtViewUserName;
    EditText fullName, email, contactNum, location, serviceType;
    String fullNameVal, usernameVal, emailVal, contactNumVal, userTypeVal, locationVal, serviceTypeVal;
    ImageButton imgBtnUploadProPic;
    ImageView profilePic;
    FirebaseStorage storage;
    StorageReference storageReference;
    Uri selectImageUri;
    private String imageUrl;

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
        imgBtnUploadProPic = view.findViewById(R.id.imgBtnUploadProPic);
        profilePic = view.findViewById(R.id.imgViewProfileImg);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        imgBtnUploadProPic.setOnClickListener(v -> {
            // Call the method to choose camera or photos
            chooseCameraOrPhotos();
        });


        Bundle args = getArguments();

        if (args != null) {
            currentUser = args.getParcelable("CURRENT_USER");
            if (currentUser != null) {
                DB = new DBHelper(view.getContext());
                UserHelperClass user = DB.getUserData(currentUser.getUid());

                if(user.getUserType().equalsIgnoreCase("Service Provider")){

                    locationVal = DB.getServiceProviderData(currentUser.getUid()).getLocation();
                    serviceTypeVal = DB.getServiceProviderData(currentUser.getUid()).getServiceType();
                }

                displayUserProfile(user);
                loadProfileImage(currentUser.getUid());
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

    @Override
    public void onResume() {
        super.onResume();
        // Load the profile picture when the fragment is resumed
        if (imageUrl != null && !imageUrl.isEmpty()) {
            Picasso.get().load(imageUrl).into(profilePic);
        }
    }

    private void chooseCameraOrPhotos() {
        final CharSequence[] items = {"Camera", "Photos"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Complete action using");
        builder.setItems(items, (dialog, item) -> {
            if (items[item].equals("Camera")) {
                pickFromCamera();
            } else if (items[item].equals("Photos")) {
                pickMedia.launch(new PickVisualMediaRequest.Builder()
                        .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                        .build());
            }
        });
        builder.show();
    }

    public void pickFromCamera() {
        ContentValues values = new ContentValues();
        selectImageUri = getContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, selectImageUri);

        camera.launch(cameraIntent);
    }

    ActivityResultLauncher<Intent> camera = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == -1) {
                    profilePic.setImageURI(selectImageUri);
                    imgBtnUploadProPic.setEnabled(true);
                    uploadProfilePicture(); // Call uploadProfilePicture after capturing the image
                } else {
                    Log.d("Camera", "No media selected");
                }
            }
    );

    ActivityResultLauncher<PickVisualMediaRequest> pickMedia = registerForActivityResult(
            new ActivityResultContracts.PickVisualMedia(), uri -> {
                if (uri != null) {
                    profilePic.setImageURI(uri);
                    imgBtnUploadProPic.setEnabled(true);
                    selectImageUri = uri;
                    uploadProfilePicture(); // Call uploadProfilePicture after selecting from photos
                } else {
                    Log.d("PhotoPicker", "No media selected");
                }
            }
    );

    private void uploadProfilePicture() {
        if (selectImageUri != null) {
            UploadTask uploadTask = storageReference.child("profile_images/" + currentUser.getUid() + ".jpg").putFile(selectImageUri);

            uploadTask.addOnSuccessListener(taskSnapshot -> {
                // Get the download URL
                storageReference.child("profile_images/" + currentUser.getUid() + ".jpg").getDownloadUrl().addOnSuccessListener(uri -> {
                    // Load the profile image using Picasso
                    Picasso.get().load(uri).into(profilePic);
                    // Update the image URL
                    imageUrl = uri.toString();
                }).addOnFailureListener(exception -> {
                    Toast.makeText(getContext(), "Failed to get download URL.", Toast.LENGTH_SHORT).show();
                });
            }).addOnFailureListener(e -> {
                Toast.makeText(getContext(), "Profile picture upload failed! Please try again..", Toast.LENGTH_LONG).show();
            });
        } else {
            Toast.makeText(getContext(), "No image selected.", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadProfileImage(String userId) {
        StorageReference profileImageRef = storageReference.child("profile_images/" + userId + ".jpg");

        profileImageRef.getDownloadUrl().addOnSuccessListener(uri -> {
            // Load the profile image using Picasso
            Picasso.get().load(uri).into(profilePic);
        }).addOnFailureListener(exception -> {
            // Handle failure to load profile image
            Log.e("ProfileFragment", "Failed to load profile image: " + exception.getMessage());
        });
    }
}