package com.example.basma.adaptar;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.basma.R;
import com.example.basma.database.DataBaseHelper;
import com.example.basma.model.contact;

import java.util.ArrayList;

public class RecContactAdapter  extends RecyclerView.Adapter<RecContactAdapter.viewHolder>{
    ArrayList<contact> contactList;
    OnItemCliked cliked;
    DataBaseHelper database;
    Context context;

    public RecContactAdapter( Context context,ArrayList<contact> contactList, OnItemCliked cliked) {
        this.context=context;
        this.contactList = contactList;
        this.cliked = cliked;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        database=DataBaseHelper.getInstance(context);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.coustam_item_contact, parent, false);
        return new viewHolder(view);
    }

    public interface OnItemCliked {
        void OnClickIem(int posation);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        contact c = contactList.get(position);
        if (c.getImageUrl().length!=0){
            Bitmap bitmap = BitmapFactory.decodeByteArray(c.getImageUrl(),0,c.getImageUrl().length);
            holder.img_contact.setImageBitmap(bitmap);
        }
        holder.tv_username.setText(c.getName());



        holder.itemView.setOnClickListener(view -> {
            cliked.OnClickIem(position);

        });



    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    class viewHolder extends RecyclerView.ViewHolder {
        ImageView img_contact;
        TextView tv_username, tv_phoneNumber;


        public viewHolder(@NonNull View itemView) {
            super(itemView);
            img_contact = itemView.findViewById(R.id.image_bookItem);
            tv_username = itemView.findViewById(R.id.tv_bookNameItem);

        }
    }
}

