package com.example.w24_3175_g7_onroadsavior;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.w24_3175_g7_onroadsavior.Database.DBHelper;
import com.example.w24_3175_g7_onroadsavior.Model.RequestDetails;
import com.example.w24_3175_g7_onroadsavior.adapter.AdapterBreakdownType;
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
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewUserHome);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        AdapterBreakdownType adapterBreakdownType = new AdapterBreakdownType(breakdownTypesList, position -> {
            Toast.makeText(getContext(), breakdownTypesList.get(position).getBreakdownType(), Toast.LENGTH_SHORT).show();
            Bundle result = new Bundle();
            result.putString("BREAKDOWNTYPE",breakdownTypesList.get(position).getBreakdownType());

            Fragment fragment = new CurrentLocationFragment();
            fragment.setArguments(result);

            replaceFragment(fragment);
        });
        recyclerView.setAdapter(adapterBreakdownType);
        // return inflater.inflate(R.layout.fragment_test, container, false);
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
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}