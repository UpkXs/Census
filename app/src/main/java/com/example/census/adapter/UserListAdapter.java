package com.example.census.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.census.R;
import com.example.census.model.Role;
import com.example.census.page.UserDetailsActivity;

import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ViewHolder> {
    private List<String> mUserNames;
    private Role role;

    public UserListAdapter(List<String> userNames, Role role) {
        this.mUserNames = userNames;
        this.role = role;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView userLink;

        public ViewHolder(View itemView) {
            super(itemView);
            userLink = itemView.findViewById(R.id.user_link);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_user, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String userName = mUserNames.get(position);
        holder.userLink.setText(userName);
        holder.userLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = holder.userLink.getContext();
                Intent userDetailsActivity  = new Intent(context, UserDetailsActivity.class);
                System.out.println("t7aSdVfu :: role : " + role);
                System.out.println("C63x132l :: userName : " + userName);
                userDetailsActivity.putExtra("role", role);
                userDetailsActivity.putExtra("username", userName);
                context.startActivity(userDetailsActivity);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUserNames.size();
    }
}
