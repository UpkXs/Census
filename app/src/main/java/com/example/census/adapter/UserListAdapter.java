package com.example.census.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.census.R;
import com.example.census.enums.Action;
import com.example.census.enums.Role;
import com.example.census.page.StationaryAndControllerDeleteActivity;
import com.example.census.page.StationaryAndControllerDetailsActivity;
import com.example.census.page.UserDetailsActivity;
import com.example.census.page.UserDetailsChangeActivity;

import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ViewHolder> {
    private List<String> mUserNames;
    private Role role;
    private Action action;

    public UserListAdapter(List<String> userNames, Role role, Action action) {
        this.mUserNames = userNames;
        this.role = role;
        this.action = action;
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
        holder.userLink.setPaintFlags(holder.userLink.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        holder.userLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = holder.userLink.getContext();
                System.out.println("t7aSdVfu :: role : " + role);
                System.out.println("C63x132l :: userName : " + userName);
                System.out.println("f27sa2a :: action : " + action);
                if (action.label.equals(Action.VIEW.label) && role.label.equals(Role.CONTROLLER.label)) {
                    Intent userDetailsActivity  = new Intent(context, UserDetailsActivity.class);
                    userDetailsActivity.putExtra("role", role);
                    userDetailsActivity.putExtra("username", userName);
                    context.startActivity(userDetailsActivity);
                } else if (action.label.equals(Action.CHANGE.label) && role.label.equals(Role.STATIONARY.label)) {
                    Intent userDetailsChangeActivity  = new Intent(context, UserDetailsChangeActivity.class);
                    userDetailsChangeActivity.putExtra("role", role);
                    userDetailsChangeActivity.putExtra("username", userName);
                    context.startActivity(userDetailsChangeActivity);
                } else if (action.label.equals(Action.CHANGE_ADMIN.label)) {
                    Intent stationaryAndControllerDetailsActivity  = new Intent(context, StationaryAndControllerDetailsActivity.class);
                    stationaryAndControllerDetailsActivity.putExtra("role", role);
                    stationaryAndControllerDetailsActivity.putExtra("username", userName);
                    context.startActivity(stationaryAndControllerDetailsActivity);
                } else if (action.label.equals(Action.DELETE.label)) {
                    Intent stationaryAndControllerDeleteActivity = new Intent(context, StationaryAndControllerDeleteActivity.class);
                    stationaryAndControllerDeleteActivity.putExtra("role", role);
                    stationaryAndControllerDeleteActivity.putExtra("username", userName);
                    context.startActivity(stationaryAndControllerDeleteActivity);
                } else {
                    System.out.println("5J7105f4 :: role : " + role);
                    System.out.println("srHEfhT1 :: userName : " + userName);
                    System.out.println("tTVwGM2P :: action : " + action);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUserNames.size();
    }
}
