package com.example.schoolmangementsystem1.Attendance;

import java.lang.reflect.Array;
import java.util.ArrayList;

import com.example.schoolmangementsystem1.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterAttReport extends RecyclerView.Adapter<AdapterAttReport.ViewHolder> {
	ArrayList<Attendance> list;
	Context context;

	public AdapterAttReport(ArrayList<Attendance> list, Context context) {
		this.list = list;
		this.context = context;
	}

	@NonNull
	@Override
	public AdapterAttReport.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(context).inflate(R.layout.singleattendancereportxml , parent , false);
		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(@NonNull AdapterAttReport.ViewHolder holder, int position) {

		holder.tv1.setText(list.get(position).getAttDate()+" "+list.get(position).getAttmonth());

		holder.tv2.setText(list.get(position).getStatus()+"");
		//holder.tv2.setText("hello");

	}

	@Override
	public int getItemCount() {
		return list.size();
	}

	public class ViewHolder extends RecyclerView.ViewHolder {

		TextView tv1 , tv2;

		public ViewHolder(@NonNull View itemView) {
			super(itemView);

			tv1 = itemView.findViewById(R.id._tvattdate);
			tv2 = itemView.findViewById(R.id._tvattstatus);

		}
	}
}
