package com.example.madproject;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class VehicleAdapter extends RecyclerView.Adapter<VehicleAdapter.VehicleViewHolder> {

    List<Vehicle> vehicleList;

    public VehicleAdapter(List<Vehicle> vehicleList) {
        this.vehicleList = vehicleList;
    }

    @NonNull
    @Override
    public VehicleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vehicle_item, parent, false);
        return new VehicleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VehicleViewHolder holder, int position) {
        Vehicle vehicle = vehicleList.get(position);
        holder.vehicleModelText.setText(vehicle.getVehicleModel());
        holder.licensePlateText.setText(vehicle.getLicensePlate());
    }

    @Override
    public int getItemCount() {
        return vehicleList.size();
    }

    static class VehicleViewHolder extends RecyclerView.ViewHolder {
        TextView vehicleModelText, licensePlateText;

        public VehicleViewHolder(@NonNull View itemView) {
            super(itemView);
            vehicleModelText = itemView.findViewById(R.id.vehicleModelText);
            licensePlateText = itemView.findViewById(R.id.licensePlateText);
        }
    }
}

