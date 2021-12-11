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
        if (position == 0) {
            holder.dividerTV.setVisibility(View.VISIBLE);
        } else {
            if (contactsList.get(position).getName().charAt(0) != contactsList.get(position - 1).getName().charAt(0)) {
                holder.dividerTV.setVisibility(View.VISIBLE);
            } else {
                holder.dividerTV.setVisibility(View.GONE);
            }
        }
        holder.bind(contactsList.get(position));
    }

    @Override
    public int getItemCount() {
        return contactsList.size();
    }

    public void setClickListener(OnItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView menuIV;
        CardView contactCV;
        TextView numberTV, nameTV, emailTV, dividerTV;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            menuIV = itemView.findViewById(R.id.menuIV);
            numberTV = itemView.findViewById(R.id.numberTV);
            nameTV = itemView.findViewById(R.id.nameTV);
            emailTV = itemView.findViewById(R.id.emailTV);
            dividerTV = itemView.findViewById(R.id.dividerTV);
            contactCV = itemView.findViewById(R.id.contactCV);
            itemView.setTag(itemView);
            itemView.setOnClickListener(this);
        }

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

        @Override
        public void onClick(View view) {
            if (clickListener != null) {
                menuIV.setOnClickListener(view1 -> clickListener.onClick(view1, getAdapterPosition(), "MENU"));
                contactCV.setOnClickListener(view12 -> clickListener.onClick(view12, getAdapterPosition(), "ITEM"));
            }
        }
    }
}