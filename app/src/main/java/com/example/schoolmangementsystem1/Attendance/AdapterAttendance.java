package com.example.schoolmangementsystem1.Attendance;

import java.util.ArrayList;

import com.example.schoolmangementsystem1.Interface.onCheckChanged;
import com.example.schoolmangementsystem1.Interface.onClickRVItem;
import com.example.schoolmangementsystem1.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterAttendance extends RecyclerView.Adapter<AdapterAttendance.ViewHolder> {

	Context context;
	ArrayList<Attendance> List;
	onCheckChanged IonCheckChanged;
	onClickRVItem IonClcikItem;

	public AdapterAttendance(Context context, ArrayList<Attendance> list, onCheckChanged ionCheckChanged,
			onClickRVItem ionClcikItem) {
		this.context = context;
		List = list;
		IonCheckChanged = ionCheckChanged;
		IonClcikItem = ionClcikItem;
	}

	public AdapterAttendance(Context context, ArrayList<Attendance> list, onCheckChanged ionCheckChanged) {
		this.context = context;
		List = list;
		IonCheckChanged = ionCheckChanged;
	}

	public AdapterAttendance(Context context, ArrayList<Attendance> list) {
		this.context = context;
		List = list;
	}

	@NonNull
	@Override
	public AdapterAttendance.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(context).inflate(R.layout.singleattendancelayout,parent , false);
		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(@NonNull AdapterAttendance.ViewHolder holder, int position) {
		holder.textView1.setText(List.get(position).getStdName()+"\n"+List.get(position).getStdrollNo()+"");

		holder.textView1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				IonClcikItem.onClickRvItem(view , position);
			}
		});
		try{
			if (List.get(position).getStatus().equals("Present")){
				holder.rb1.setChecked(true);
			}else if (List.get(position).getStatus().equals("Absent")){
				holder.rb2.setChecked(true);
			}
			else if (List.get(position).getStatus().equals("Leave")){
				holder.rb3.setChecked(true);
			}
		}
		catch (Exception exc){

		}

		holder.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup radioGroup, int i) {


				String status = "";
				if (i == holder.rb1.getId()){
					status = "Present";
				}if (i == holder.rb2.getId()){
					status = "Absent";
				}if (i == holder.rb3.getId()){
					status = "Leave";

				}

				List.get(position).setStatus(status);
				IonCheckChanged.onCheckChanged(radioGroup , i , position , status);


			}
		});

	}

	@Override
	public int getItemCount() {
		return List.size();
	}

	public class ViewHolder extends RecyclerView.ViewHolder {
		TextView textView1; RadioGroup radioGroup;
		RadioButton rb1 , rb2 , rb3;

		public ViewHolder(@NonNull View itemView) {
			super(itemView);
			textView1 = itemView.findViewById(R.id._tvattstdroll);
			radioGroup = itemView.findViewById(R.id._radiogroup);

			rb1 = itemView.findViewById(R.id._radiobpresent);
			rb2 = itemView.findViewById(R.id.radiobabsent);
			rb3 = itemView.findViewById(R.id.radiobleave);
		}
	}
}
