package com.sonusourav.MILErecorder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

    public class CallAdapter extends RecyclerView.Adapter<CallAdapter.MyViewHolder> {

        private List<ListItem> callList;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            private TextView name, phone, time;

            public MyViewHolder(View view) {
                super(view);
                name = (TextView) view.findViewById(R.id.item_name);
                phone = (TextView) view.findViewById(R.id.item_phone);
                time = (TextView) view.findViewById(R.id.item_time);
            }
        }


        public CallAdapter(List<ListItem> callList) {
            this.callList = callList;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            ListItem call = callList.get(position);
            holder.name.setText(call.getName());
            holder.phone.setText(call.getPhone());
            holder.time.setText(call.getTime());
        }

        @Override
        public int getItemCount() {
            return callList.size();
        }
    }

