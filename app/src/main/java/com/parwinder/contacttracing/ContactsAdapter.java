package com.parwinder.contacttracing;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.MyViewHolder> {

    private final Context context;
    private final ArrayList<ContactsData> contactsList;

    public ContactsAdapter(Context context, ArrayList<ContactsData> contactsList) {
        this.context = context;
        this.contactsList = contactsList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_contact, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.nameTV.setText(contactsList.get(position).getName());
        holder.emailTV.setText(contactsList.get(position).getEmail());
        holder.numberTV.setText(contactsList.get(position).getNumber());
    }

    @Override
    public int getItemCount() {
        return contactsList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView menuIV;
        TextView numberTV, nameTV, emailTV;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            menuIV = itemView.findViewById(R.id.menuIV);
            numberTV = itemView.findViewById(R.id.numberTV);
            nameTV = itemView.findViewById(R.id.nameTV);
            emailTV = itemView.findViewById(R.id.emailTV);
        }

        @Override
        public void onClick(View view) {
            menuIV.setOnClickListener(this);
        }
    }
}
