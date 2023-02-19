package com.example.schoolmangementsystem1.TimeTable;

import java.util.ArrayList;

import org.w3c.dom.Text;

import com.example.schoolmangementsystem1.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterTimeTable extends RecyclerView.Adapter<AdapterTimeTable.ViewHolder> {
	Context context;
	ArrayList<Period> list;

	public AdapterTimeTable(Context context, ArrayList<Period> list) {
		this.context = context;
		this.list = list;
	}

	@NonNull
	@Override
	public AdapterTimeTable.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(context).inflate(R.layout.singlelayouttimetable , parent , false);
		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(@NonNull AdapterTimeTable.ViewHolder holder, int position) {

		holder.textViewtime.setText(list.get(position).getLectureTimeStart()+" \t-\t "+list.get(position).getLectureTimeEnd());

		holder.textViewsubject.setText(list.get(position).getLectureSubject()+"");
		holder.textViewtype.setText(list.get(position).getLectureType()+" \t( "+list.get(position).getLectureInstructureName()+" )");
	}

	@Override
	public int getItemCount() {
		return list.size();
	}

	public class ViewHolder extends RecyclerView.ViewHolder {
		TextView textViewtime , textViewtype , textViewsubject;

		public ViewHolder(@NonNull View itemView) {
			super(itemView);
			textViewtime = itemView.findViewById(R.id._tvtime);
			textViewtype = itemView.findViewById(R.id._tvsbjtitle);
			textViewsubject = itemView.findViewById(R.id._tvsbjtype);
		}
	}
}
