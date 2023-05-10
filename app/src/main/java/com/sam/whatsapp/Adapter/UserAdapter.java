package com.sam.whatsapp.Adapter;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sam.whatsapp.Models.Users;
import com.sam.whatsapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder>{

    ArrayList<Users> list;
    Context context;

    public UserAdapter(ArrayList<Users> list, Context context) {
        this.list = list;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(context).inflate(R.layout.sample_show_user,parent,false);
    return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Users users=list.get(position);
        Picasso.get().load(users.getProfilepic()).placeholder(R.drawable.avatar).into(holder.image);
        holder.name.setText(users.getUsername());


    }


    @Override
    public int getItemCount() {
        return list.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,lastmessage;
        ImageView image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

         name=itemView.findViewById(R.id.name);
         lastmessage=itemView.findViewById(R.id.lastMessage);
         image=itemView.findViewById(R.id.profile_image);

        }
    }
}
