package com.example.w24_3175_g7_onroadsavior.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.w24_3175_g7_onroadsavior.Model.BreakdownRequestDetails;
import com.example.w24_3175_g7_onroadsavior.R;

import java.util.List;

public class AdapterBreakdownRequestDetails extends RecyclerView.Adapter<AdapterBreakdownRequestDetails.ViewHolder> {
    private List<BreakdownRequestDetails> breakdownRequestDetailsList;

    public AdapterBreakdownRequestDetails(List<BreakdownRequestDetails> breakdownRequestDetailsList) {
        this.breakdownRequestDetailsList = breakdownRequestDetailsList;
    }

    public List<BreakdownRequestDetails> getBreakdownRequestDetailsList() {
        return breakdownRequestDetailsList;
    }

    public void setBreakdownRequestDetailsList(List<BreakdownRequestDetails> breakdownRequestDetailsList) {
        this.breakdownRequestDetailsList = breakdownRequestDetailsList;
    }

    public AdapterBreakdownRequestDetails() {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.breakdown_request_detail, parent, false);
        AdapterBreakdownRequestDetails.ViewHolder viewHolder =new AdapterBreakdownRequestDetails.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BreakdownRequestDetails requestDetails = breakdownRequestDetailsList.get(position);

        holder.textViewCreatedDate.setText(requestDetails.getCreatedDate());
        holder.textViewUpdatedDate.setText(requestDetails.getUpdatedDate());
        holder.textViewUserId.setText(requestDetails.getUserId());
        holder.textViewProviderId.setText(requestDetails.getProviderId());
        holder.textViewBreakdownType.setText(requestDetails.getBreakdownType());
        holder.textViewCurrentLocation.setText(requestDetails.getCurrentLocation());
        holder.textViewDescription.setText(requestDetails.getDescription());
        // Load image using Glide or any other image loading library
        Glide.with(holder.itemView).load(requestDetails.getImageUrl()).into(holder.imageViewImage);
        holder.textViewStatus.setText(requestDetails.getStatus());
    }

    @Override
    public int getItemCount() {
        return breakdownRequestDetailsList.size();
    }

    // Implement onCreateViewHolder, onBindViewHolder, and getItemCount methods

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Declare your ViewHolder views here

        TextView textViewCreatedDate;
        TextView textViewUpdatedDate;
        TextView textViewUserId;
        TextView textViewProviderId;
        TextView textViewBreakdownType;
        TextView textViewCurrentLocation;
        TextView textViewDescription;
        ImageView imageViewImage; // Add ImageView if displaying images
        TextView textViewStatus;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Initialize your views here
            textViewCreatedDate = itemView.findViewById(R.id.textViewCreatedDate);
            textViewUpdatedDate = itemView.findViewById(R.id.textViewUpdatedDate);
            textViewUserId = itemView.findViewById(R.id.textViewUserId);
            textViewProviderId = itemView.findViewById(R.id.textViewProviderId);
            textViewBreakdownType = itemView.findViewById(R.id.textViewBreakdownType);
            textViewCurrentLocation = itemView.findViewById(R.id.textViewCurrentLocation);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);
            imageViewImage = itemView.findViewById(R.id.imageViewImage); // Initialize ImageView if displaying images
            textViewStatus = itemView.findViewById(R.id.textViewStatus);
        }
    }

}
