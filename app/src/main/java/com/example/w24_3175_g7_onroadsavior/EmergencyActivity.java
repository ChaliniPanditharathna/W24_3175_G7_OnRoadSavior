package com.example.w24_3175_g7_onroadsavior;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.w24_3175_g7_onroadsavior.Model.EmergencyContact;
import com.example.w24_3175_g7_onroadsavior.adapter.AdapterEmergencyContact;

import java.util.ArrayList;

public class EmergencyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);

        Toolbar toolbar = findViewById(R.id.toolbarEmergency);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EmergencyActivity.this, FragmentHandler.class);
                startActivity(intent);
            }
        });

        ArrayList<EmergencyContact> contactList = generateItemsList(); // calls function to get items list

        // instantiate the custom list adapter
        AdapterEmergencyContact adapter = new AdapterEmergencyContact(this, contactList);

        // get the ListView and attach the adapter
        ListView itemsListView = findViewById(R.id.listViewEmergencyContacts);

        itemsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String phone = contactList.get(position).getPhoneNumber();
                Uri uri = Uri.parse("tel:" + phone);
                Intent intent = new Intent(Intent.ACTION_DIAL, uri);
                startActivity(intent);
            }
        });

        itemsListView.setAdapter(adapter);
    }

    public ArrayList<EmergencyContact> generateItemsList() {
        ArrayList<EmergencyContact> contacts = new ArrayList<>();
        contacts.add(new EmergencyContact("Emergency Hotline", "911"));
        contacts.add(new EmergencyContact("Report a spill", "1-800-663-3456"));
        contacts.add(new EmergencyContact("Wildfire", "1-800-663-5555"));
        contacts.add(new EmergencyContact("ICBC", "1-800-663-3051"));
        contacts.add(new EmergencyContact("Video Relay Services", "1-604-215-5101"));
        return contacts;
    }
}