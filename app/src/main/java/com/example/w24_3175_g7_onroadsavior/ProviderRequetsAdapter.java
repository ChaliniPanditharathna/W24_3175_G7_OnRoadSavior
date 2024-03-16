package com.example.w24_3175_g7_onroadsavior;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.w24_3175_g7_onroadsavior.Interface.ProviderRequestInterface;
import com.example.w24_3175_g7_onroadsavior.Model.RequestDetails;

import java.util.ArrayList;

public class ProviderRequetsAdapter extends RecyclerView.Adapter<ProviderRequetsAdapter.RequestViewHolder> {

    private final ProviderRequestInterface providerRequestInterface;
    private Context context;
    private ArrayList<RequestDetails> requestDetails = null;

    public ProviderRequetsAdapter(Context context, ArrayList<RequestDetails> requestDetails, ProviderRequestInterface providerRequestInterface) {
        this.context = context;
        this.requestDetails = requestDetails;
        this.providerRequestInterface = providerRequestInterface;
    }

    @NonNull
    @Override
    public RequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View v = LayoutInflater.from(context).inflate(R.layout.service_provider_request, parent, false);
        return new RequestViewHolder(v, providerRequestInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestViewHolder holder, int position) {
        RequestDetails req = requestDetails.get(position);
        holder.userName.setText(req.getUserName());
        holder.breakDownType.setText(req.getBreakDownType());
        holder.location.setText(req.getLocation());
    }

    @Override
    public int getItemCount() {
        return requestDetails.size();
    }

    public class RequestViewHolder extends RecyclerView.ViewHolder {

        TextView  userName, breakDownType,location;
        public RequestViewHolder(@NonNull View itemView, ProviderRequestInterface providerRequestInterface) {
            super(itemView);
            userName=itemView.findViewById(R.id.userName);
            breakDownType=itemView.findViewById(R.id.breakdowntype);
            location=itemView.findViewById(R.id.location);
           itemView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                  if(providerRequestInterface !=null){
                    int  pos = getAdapterPosition();

                    if(pos != RecyclerView.NO_POSITION){
                        providerRequestInterface.OnItemClick(pos);
                      }
                  }
               }
           });
        }
    }
}
