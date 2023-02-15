package com.example.schoolmangementsystem1.List;

import java.util.ArrayList;

import com.example.schoolmangementsystem1.Adapter.AdpaterStaffLIst;
import com.example.schoolmangementsystem1.AddNew.CreateNewStaff;
import com.example.schoolmangementsystem1.Interface.onClickRVItem;
import com.example.schoolmangementsystem1.Model.Staff;
import com.example.schoolmangementsystem1.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class StaffList extends AppCompatActivity {
	DatabaseReference reference;
	RecyclerView recyclerView;
	AdpaterStaffLIst adapterstafflist;
	ArrayList<Staff> list;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_staff_list);
		recyclerView = findViewById(R.id._recyclerviewstaflist);
		list = new ArrayList<Staff>();
		adapterstafflist = new AdpaterStaffLIst(this, list, new onClickRVItem() {

			@Override
			public void onClickRvItem(View view, int position) {
				Intent intent = new Intent(StaffList.this , CreateNewStaff.class);
				if (list.get(position).getStaffId() != null){
					intent.putExtra("stfid" , list.get(position).getStaffId());
					startActivity(intent);
				}

				//Toast.makeText(StaffList.this, "Name : "+list.get(position).getStaffName(), Toast.LENGTH_SHORT).show();
			}
		});

		reference = FirebaseDatabase.getInstance().getReference().child("Staff");


		LinearLayoutManager layoutmanager = new LinearLayoutManager(this);
		recyclerView.setLayoutManager( layoutmanager);
		recyclerView.setAdapter(adapterstafflist);

		LoadAtaffList();

	}

	public  void LoadAtaffList(){
		reference.addValueEventListener(new ValueEventListener() {

			@Override
			public void onDataChange(@NonNull DataSnapshot snapshot) {

				list.clear();
				for (DataSnapshot snapshot1 : snapshot.getChildren()){


					Staff staf = new Staff();
					staf.setStaffName(snapshot1.child("staffName").getValue(String.class));
					staf.setStaffId(snapshot1.child("staffId").getValue(String.class));
					staf.setEducation(snapshot1.child("education").getValue(String.class));
					staf.setEmailAddress(snapshot1.child("emailAddress").getValue(String.class));
					staf.setContactNo(snapshot1.child("contactNo").getValue(String.class));
					staf.setAddress(snapshot1.child("address").getValue(String.class));

					list.add(staf);

					adapterstafflist.notifyDataSetChanged();
				}

			}

			@Override
			public void onCancelled(@NonNull DatabaseError error) {

			}
		});
	}

	public void GoToAddNewStaff(View view) {

		Intent intent = new Intent(StaffList.this , CreateNewStaff.class);
		intent.putExtra("stfid" , "");
		startActivity(intent);
	}
}