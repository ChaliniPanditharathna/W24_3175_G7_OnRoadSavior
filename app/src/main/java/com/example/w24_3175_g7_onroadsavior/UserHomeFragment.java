package com.example.w24_3175_g7_onroadsavior;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.w24_3175_g7_onroadsavior.adapter.BreakdownTypeAdapter;
import com.example.w24_3175_g7_onroadsavior.Model.BreakdownTypes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserHomeFragment extends Fragment {

    List<BreakdownTypes> breakdownTypesList = new ArrayList<>();

    List<String> breakdownType = new ArrayList<>(Arrays.asList("Tow", "Lockout", "Fuel Delivery",
            "Tire Change", "Jump Start"));
    List<Integer> breakdownTypeIcon = new ArrayList<>(Arrays.asList(R.drawable.tow, R.drawable.lockout,
            R.drawable.fueldelivery, R.drawable.tierchange, R.drawable.jumpstart));

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        loadDataModel();
        View view = inflater.inflate(R.layout.fragment_user_home, container, false);
        ImageView imageView =view.findViewById(R.id.imageViewUserHome);
        imageView.setImageResource(R.drawable.bannerassistance);
        ListView listViewBreakdownTypes = view.findViewById(R.id.listViewUserHome);
        BreakdownTypeAdapter breakdownTypeAdapter = new BreakdownTypeAdapter(breakdownTypesList);
        listViewBreakdownTypes.setAdapter(breakdownTypeAdapter);
        listViewBreakdownTypes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                replaceFragment(new CurrentLocationFragment());
            }
        });

        return view;
    }

    public void loadDataModel(){

        for(int i =0; i < breakdownType.size(); i++){
            BreakdownTypes eachBreakdownType = new BreakdownTypes(breakdownType.get(i),
                    breakdownTypeIcon.get(i));
            breakdownTypesList.add(eachBreakdownType);
        }
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}