package com.example.schoolmangementsystem1.Adapter;

import java.lang.reflect.Array;
import java.util.ArrayList;

import org.w3c.dom.Text;

import com.example.schoolmangementsystem1.Interface.onClickRVItem;
import com.example.schoolmangementsystem1.Model.Staff;
import com.example.schoolmangementsystem1.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdpaterStaffLIst extends RecyclerView.Adapter<AdpaterStaffLIst.ViewHolder> {
	Context context;
	ArrayList<Staff> list;
	onClickRVItem onClickRVItem;

	public AdpaterStaffLIst(Context context, ArrayList<Staff> list, com.example.schoolmangementsystem1.Interface.onClickRVItem onClickRVItem) {
		this.context = context;
		this.list = list;
		this.onClickRVItem = onClickRVItem;
	}

	public AdpaterStaffLIst(Context context, ArrayList<Staff> list) {
		this.context = context;
		this.list = list;
	}

	@NonNull
	@Override
	public AdpaterStaffLIst.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

		View view = LayoutInflater.from(context).inflate(R.layout.singlestaffxml , parent , false);
		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(@NonNull AdpaterStaffLIst.ViewHolder holder, int position) {
		holder.tvname.setText(list.get(position).getStaffName()+"");
		holder.tvedu.setText(list.get(position).getEducation()+"");
		holder.tvaddress.setText(list.get(position).getAddress()+"");
		holder.tvcontactno.setText(list.get(position).getContactNo()+"");
		holder.linearLayout.setOnClickListener(new View.OnClickListener() {

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
		TextView tvname , tvedu , tvcontactno , tvaddress;
		LinearLayout linearLayout;

		public ViewHolder(@NonNull View itemView) {
			super(itemView);

			tvname = itemView.findViewById(R.id._tvstaffname);
			tvedu = itemView.findViewById(R.id._tvstaffeducation);
			tvcontactno = itemView.findViewById(R.id._tvstaffcnumber);
			tvaddress = itemView.findViewById(R.id._tvstaffhmeaddress);
			linearLayout = itemView.findViewById(R.id._layoutStaff);
		}
	}
}
