package com.example.schoolmangementsystem1.LeaveRequest;

import java.util.ArrayList;

import com.example.schoolmangementsystem1.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterLeaveRequestList  extends RecyclerView.Adapter<AdapterLeaveRequestList.ViewHolder> {

	Context context;
	ArrayList<LeaveReq> list;

	public AdapterLeaveRequestList(Context context, ArrayList<LeaveReq> list) {
		this.context = context;
		this.list = list;
	}

	@NonNull
	@Override
	public AdapterLeaveRequestList.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(context).inflate(R.layout.singleleaverequest   ,parent , false);
		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(@NonNull AdapterLeaveRequestList.ViewHolder holder, int position) {


		String[]  array = new String[2];
		String[]  array2 = new String[2];
		if (list.get(position).getDate2() != null){
			array = list.get(position).getDate2().split(" ");

		}
		if (list.get(position).getDate1() != null){
			array2 = list.get(position).getDate1().split(" ");

		}
		holder.tv1.setText(array2[0]+" / "+array[0]);
		holder.tv2.setText(list.get(position).getReqStatus()+"");
		holder.tv3.setText(list.get(position).getReqReason()+"");
		holder.tv4.setText(list.get(position).getReqRemarks()+"");

	}

	@Override
	public int getItemCount() {
		return list.size();
	}

	public class ViewHolder extends RecyclerView.ViewHolder {
		TextView tv1 , tv2 , tv3 , tv4;


		public ViewHolder(@NonNull View itemView) {
			super(itemView);

			tv1 = itemView.findViewById(R.id.reqdate1);
			tv2 = itemView.findViewById(R.id.reqremarks);
			tv3 = itemView.findViewById(R.id.tv3);
			tv4 = itemView.findViewById(R.id.tv4);
		}
	}
}
