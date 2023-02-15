package com.example.schoolmangementsystem1.Adapter;

import java.util.ArrayList;

import org.w3c.dom.Text;

import com.example.schoolmangementsystem1.Interface.onClickRVItem;
import com.example.schoolmangementsystem1.Model.Subject;
import com.example.schoolmangementsystem1.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterSubjectsList extends RecyclerView.Adapter<AdapterSubjectsList.ViewHolder> {

	Context context;
	ArrayList<Subject> list;
	onClickRVItem onClickRvItem;

	public AdapterSubjectsList(Context context, ArrayList<Subject> list, onClickRVItem onClickRvItem) {
		this.context = context;
		this.list = list;
		this.onClickRvItem = onClickRvItem;
	}

	public AdapterSubjectsList(Context context, ArrayList<Subject> list) {
		this.context = context;
		this.list = list;
	}

	@NonNull
	@Override
	public AdapterSubjectsList.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(context).inflate(R.layout.layoutsinglesubject , parent , false);
		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(@NonNull AdapterSubjectsList.ViewHolder holder, int position) {
		holder.tvsbjname.setText(list.get(position).getSbjTitle()+"");
		holder.tvsbjinstructure.setText(list.get(position).getSbjInsName()+"");
		holder.tvsbjdes.setText(list.get(position).getSbjcourseobjective()+"");
		holder.ivselete.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				onClickRvItem.onClickRvItem(view , position);
			}
		});

	}

	@Override
	public int getItemCount() {
		return list.size();
	}

	public class ViewHolder extends RecyclerView.ViewHolder {
		TextView tvsbjname , tvsbjinstructure , tvsbjdes;
		ImageView ivselete;


		public ViewHolder(@NonNull View itemView) {
			super(itemView);
			tvsbjname = itemView.findViewById(R.id._tvsbjtitle);
			tvsbjinstructure = itemView.findViewById(R.id._tvsbjinstructor);
			tvsbjdes = itemView.findViewById(R.id._tvsbjcourseoutline);
			ivselete = itemView.findViewById(R.id._ivdelete);

		}
	}
}
