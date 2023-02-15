package com.example.schoolmangementsystem1.Adapter;

import java.util.ArrayList;

import org.w3c.dom.Text;

import com.example.schoolmangementsystem1.Interface.onClickRVItem;
import com.example.schoolmangementsystem1.Model.Student;
import com.example.schoolmangementsystem1.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterStudentList extends RecyclerView.Adapter<AdapterStudentList.ViewHolder> {
	Context context;
	ArrayList<Student> list;
	onClickRVItem onClickRVItem;

	public AdapterStudentList(Context context, ArrayList<Student> list, com.example.schoolmangementsystem1.Interface.onClickRVItem onClickRVItem) {
		this.context = context;
		this.list = list;
		this.onClickRVItem = onClickRVItem;
	}

	public AdapterStudentList(Context context, ArrayList<Student> list) {
		this.context = context;
		this.list = list;
	}

	@NonNull
	@Override
	public AdapterStudentList.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(context).inflate(R.layout.layoutsinglestudent , parent , false);
		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(@NonNull AdapterStudentList.ViewHolder holder, int position) {
		holder.tvStdname.setText(list.get(position).getStdName()+" "+list.get(position).getStdLastName());
		holder.tvstdRollno.setText(list.get(position).getStdRollNo()+"");
		holder.cardView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				onClickRVItem.onClickRvItem(view , position);
			}
		});

	}

	@Override
	public int getItemCount() {
		return list.size();
	}

	public class ViewHolder extends RecyclerView.ViewHolder {
		TextView tvStdname , tvstdRollno;
		CardView cardView;

		public ViewHolder(@NonNull View itemView) {
			super(itemView);
			tvStdname = itemView.findViewById(R.id._tvstdname);
			tvstdRollno = itemView.findViewById(R.id._tvstdrollno);
			cardView = itemView.findViewById(R.id._cardviewstd);
		}
	}
}
