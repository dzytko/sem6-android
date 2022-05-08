package com.example.project2.views;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.project2.R;
import com.example.project2.entities.Phone;

import java.util.List;

public class PhoneListAdapter extends RecyclerView.Adapter<PhoneListAdapter.PhoneViewHolder> {
    private final LayoutInflater inflater;
    private List<Phone> phoneList;

    public PhoneListAdapter(Activity activity) {
        inflater = activity.getLayoutInflater();
        phoneList = null;
    }

    @Override
    public PhoneViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = inflater.inflate(R.layout.phone_list_item, parent, false);
        return new PhoneViewHolder(row);
    }

    @Override
    public void onBindViewHolder(PhoneViewHolder holder, int position) {
        Phone phone = phoneList.get(position);
        holder.manufacturerTextView.setText(phone.getManufacturer());
        holder.modelTextView.setText(phone.getModel());
    }

    @Override
    public int getItemCount() {
        return phoneList != null ? phoneList.size() : 0;
    }

    public void setPhones(List<Phone> phones) {
        phoneList = phones;
        notifyDataSetChanged();
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
}
