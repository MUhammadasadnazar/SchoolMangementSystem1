package com.example.schoolmangementsystem1.meeting;

import java.util.ArrayList;

import com.example.schoolmangementsystem1.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterMeetingList extends RecyclerView.Adapter<AdapterMeetingList.ViewHolder> {

	Context context;
	ArrayList<Meeting> list;

	public AdapterMeetingList(Context context, ArrayList<Meeting> list) {
		this.context = context;
		this.list = list;
	}

	@NonNull
	@Override
	public AdapterMeetingList.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(context).inflate(R.layout.singlemeetingxml , parent , false);
		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(@NonNull AdapterMeetingList.ViewHolder holder, int position) {
		holder.tvdatetime.setText(list.get(position).getMeetingdate()+"\t\t\t\t\t"+list.get(position).getMeetingTime());
		holder.tvremarks.setText(list.get(position).getMeetingAgenda());

	}

	@Override
	public int getItemCount() {
		return list.size();
	}

	public class ViewHolder extends RecyclerView.ViewHolder {
		TextView tvdatetime , tvremarks;

		public ViewHolder(@NonNull View itemView) {
			super(itemView);
			tvdatetime = itemView.findViewById(R.id.datetime);
			tvremarks = itemView.findViewById(R.id._meetdes);
		}
	}
}
