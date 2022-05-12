package com.example.project2.views;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project2.R;
import com.example.project2.entities.Phone;

import java.util.List;

public class PhoneListAdapter extends RecyclerView.Adapter<PhoneListAdapter.PhoneViewHolder> {
    private final OnItemClickListener rowOnClickListener;
    private final LayoutInflater inflater;
    private List<Phone> phoneList;

    public PhoneListAdapter(Activity activity, OnItemClickListener rowOnClickListener) {
        this.rowOnClickListener = rowOnClickListener;
        inflater = activity.getLayoutInflater();
        phoneList = null;
    }

    @NonNull
    @Override
    public PhoneViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = inflater.inflate(R.layout.phone_list_item, parent, false);
        return new PhoneViewHolder(row);
    }

    @Override
    public void onBindViewHolder(PhoneViewHolder holder, int position) {
        Phone phone = phoneList.get(position);
        holder.manufacturerTextView.setText(phone.getManufacturer());
        holder.modelTextView.setText(phone.getModel());
        holder.itemView.setOnClickListener(v -> rowOnClickListener.onItemClickListener(phoneList.get(position)));
    }

    @Override
    public int getItemCount() {
        return phoneList != null ? phoneList.size() : 0;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setPhones(List<Phone> phones) {
        phoneList = phones;
        notifyDataSetChanged();
    }

    public Phone getPhoneAt(int position) {
        return phoneList.get(position);
    }

    static class PhoneViewHolder extends RecyclerView.ViewHolder {
        TextView manufacturerTextView;
        TextView modelTextView;

        public PhoneViewHolder(View itemView) {
            super(itemView);
            manufacturerTextView = itemView.findViewById(R.id.manufacturerTextView);
            modelTextView = itemView.findViewById(R.id.modelTextView);
        }
    }

    public interface OnItemClickListener {
        void onItemClickListener(Phone phone);
    }
}
