package com.example.w24_3175_g7_onroadsavior;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class BreakdownTypeAdapter extends BaseAdapter {

    List<BreakdownTypes> adapterBreakdownTypes;

    public BreakdownTypeAdapter(List<BreakdownTypes> adapterBreakdownTypes) {
        this.adapterBreakdownTypes = adapterBreakdownTypes;
    }

    public List<BreakdownTypes> getAdapterBreakdownTypes() {
        return adapterBreakdownTypes;
    }

    public void setAdapterBreakdownTypes(List<BreakdownTypes> adapterBreakdownTypes) {
        this.adapterBreakdownTypes = adapterBreakdownTypes;
    }

    @Override
    public int getCount() {
        return adapterBreakdownTypes.size();
    }

    @Override
    public Object getItem(int position) {
        return adapterBreakdownTypes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.useroptionslayout , parent, false);
        }

        ImageView imageViewBreajdownType = convertView.findViewById(R.id.imageViewUserOption);
        TextView textViewBreakdownType = convertView.findViewById(R.id.textViewUserOption);

        imageViewBreajdownType.setImageResource(adapterBreakdownTypes.get(position).getBreakdownIcon());
        textViewBreakdownType.setText(adapterBreakdownTypes.get(position).getBreakdownType());

        return convertView;
    }
}
