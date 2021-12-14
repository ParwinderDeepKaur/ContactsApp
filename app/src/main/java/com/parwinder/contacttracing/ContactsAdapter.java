package com.parwinder.contacttracing;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.MyViewHolder> {

    Context context;
    ArrayList<ContactsData> contactsList;
    public OnItemClickListener clickListener;

    /*constructor with context and array list params*/
    public ContactsAdapter(Context context, ArrayList<ContactsData> contactsList) {
        this.context = context;
        this.contactsList = contactsList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflate the view for the item
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_contact, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // set visibility of views according to the position
        if (position == 0) {
            holder.dividerTV.setVisibility(View.VISIBLE);
        } else {
            // set visibility of view of the view if the text is different from previous view
            if (contactsList.get(position).getName().charAt(0) != contactsList.get(position - 1).getName().charAt(0)) {
                holder.dividerTV.setVisibility(View.VISIBLE);
            } else {
                holder.dividerTV.setVisibility(View.GONE);
            }
        }

        // bind data with views
        holder.bind(contactsList.get(position));

        // set click listener on views
        holder.menuIV.setOnClickListener(view1 -> clickListener.onClick(view1, position, "MENU"));
        holder.contactCV.setOnClickListener(view12 -> clickListener.onClick(view12, position, "ITEM"));
    }

    @Override
    public int getItemCount() {
        return contactsList.size();
    }

    // initialize the interface
    public void setClickListener(OnItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView menuIV;
        CardView contactCV;
        TextView numberTV, nameTV, emailTV, dividerTV;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            // find view ids
            menuIV = itemView.findViewById(R.id.menuIV);
            numberTV = itemView.findViewById(R.id.numberTV);
            nameTV = itemView.findViewById(R.id.nameTV);
            emailTV = itemView.findViewById(R.id.emailTV);
            dividerTV = itemView.findViewById(R.id.dividerTV);
            contactCV = itemView.findViewById(R.id.contactCV);
        }

        /*
         * bind the data with views
         * set visibility of views conditionally*/
        public void bind(ContactsData contactsData) {
            dividerTV.setText(String.valueOf(contactsData.getName().charAt(0)));
            nameTV.setText(contactsData.getName());
            numberTV.setText(contactsData.getNumber());
            if (!contactsData.getEmail().isEmpty()) {
                emailTV.setVisibility(View.VISIBLE);
                emailTV.setText(contactsData.getEmail());
            } else {
                emailTV.setVisibility(View.GONE);
            }
        }
    }
}