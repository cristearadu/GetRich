package com.examplee.radu.getrich.Saraci;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.examplee.radu.getrich.R;
import com.examplee.radu.getrich.User.Profile;

import java.util.ArrayList;

public class SaraciAdapter extends RecyclerView.Adapter<SaraciAdapter.MyViewHolder>{

    Context context;
    ArrayList<Profile> profiles;

    public SaraciAdapter(Context c, ArrayList<Profile> p)
    {
        context = c;
        profiles = p;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_sarac,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(profiles.get(position).getName());
        holder.level.setText(profiles.get(position).getLevel());
        String value = Long.toString(profiles.get(position).getValue());
        holder.value.setText(value);
        holder.location.setText(profiles.get(position).getLocation());
        holder.description.setText(profiles.get(position).getDescription());

    }

    @Override
    public int getItemCount() {
        return profiles.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView name, level, value, location, description;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.name);
            level = (TextView) itemView.findViewById(R.id.level);
            value = (TextView) itemView.findViewById(R.id.value);
            location = (TextView) itemView.findViewById(R.id.location);
            description = (TextView) itemView.findViewById(R.id.description);

        }
    }
}
