package com.example.w24_3175_g7_onroadsavior;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class SignUpActivity extends AppCompatActivity {

    FirebaseDatabase rootNode;
    DatabaseReference reference;
    int serviceRequesterCount = 0;
    int serviceProviderCount = 0;


    String fullName, username, email, contactNumber, password, userType, serviceType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //widgets
        TextInputLayout regFullName = findViewById(R.id.name);
        TextInputLayout regUserName = findViewById(R.id.username);
        TextInputLayout regEmail = findViewById(R.id.email);
        TextInputLayout regContactNumber = findViewById(R.id.contactNumber);
        TextInputLayout regPassword = findViewById(R.id.password);
        Spinner userTypeSpinner = findViewById(R.id.userTypeSpinner);
        Spinner serviceTypeSpinner = findViewById(R.id.serviceTypeSpinner);

        Button btnSignUp = findViewById(R.id.btnSignUp);
        Button btnBackToSignIn = findViewById(R.id.btnBackToSignIn);

        //change visibility of service type spinner
        userTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                //service requester
                if(position == 0){
                    serviceTypeSpinner.setVisibility(View.GONE);
                } else if (position == 1) {
                    serviceTypeSpinner.setVisibility(View.VISIBLE);
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        //set data in Firebase on button click
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //get values
                fullName = regFullName.getEditText().getText().toString();
                username = regUserName.getEditText().getText().toString();
                email = regEmail.getEditText().getText().toString();
                contactNumber = regContactNumber.getEditText().getText().toString();
                password = regPassword.getEditText().getText().toString();


                int userTypeInd = userTypeSpinner.getSelectedItemPosition();

                if (userTypeInd == 0){
                    userType = "Service Requester";
                    serviceType = "";

                    generateRecode(fullName, username, email, contactNumber, password, userTypeInd, userType, serviceType);
;
                } else if (userTypeInd == 1){
                    userType = "Service Provider";

                    int serviceTypeInd = serviceTypeSpinner.getSelectedItemPosition();
                    serviceType = getServiceType(serviceTypeInd);

                    generateRecode(fullName, username, email, contactNumber, password, userTypeInd, userType, serviceType);
                }
            }
        });


        btnBackToSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, LogInActivity.class));
            }
        });


    }

    private void generateRecode(String fullname, String userName, String email, String contactNumber, String password, int userTypeInd, String userType, String serviceType){

        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("Users");

        DatabaseReference userNameRef = reference.child(userName);
        ValueEventListener eventListener = new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(!snapshot.exists()) {
                    //create new user

                    UserHelperClass user = new UserHelperClass(fullName, username, email, contactNumber, password, userType, serviceType);

                    // Save the user data in the appropriate table based on userType
                    reference.child(username).setValue(user);

                    Toast.makeText(SignUpActivity.this, "User registered successfully!", Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(SignUpActivity.this, LogInActivity.class));

                } else {
                    Toast.makeText(SignUpActivity.this, "User already exists!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        userNameRef.addListenerForSingleValueEvent(eventListener);

    }

    public static String getServiceType(int pos){

        String serviceType = "";

        switch (pos){
            case 0:
                serviceType = "Tow";
                break;
            case 1:
                serviceType = "Lockout";
                break;
            case 2:
                serviceType = "Fuel Delivery";
                break;
            case 3:
                serviceType = "Tire Change";
                break;
            case 4:
                serviceType = "Jump Start";
                break;
        }

        return serviceType;
    }


}