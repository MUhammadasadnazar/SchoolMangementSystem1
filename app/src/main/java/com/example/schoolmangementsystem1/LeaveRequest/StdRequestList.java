package com.example.schoolmangementsystem1.LeaveRequest;

import java.util.ArrayList;

import com.example.schoolmangementsystem1.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class StdRequestList extends AppCompatActivity {
	String stduid = "";
	String stdclassid = "";
	SharedPreferences sharedPreferences;
	DatabaseReference reference;
	ArrayList<LeaveReq> list;
	AdapterLeaveRequestList adapterLeaveRequestList;
	RecyclerView recyclerView;
	String isstaff = "";


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_std_request_list);
		sharedPreferences = getSharedPreferences("my_pref" , MODE_PRIVATE);
		stdclassid = sharedPreferences.getString("uid","");
		isstaff = sharedPreferences.getString("isstaff","");

		recyclerView = findViewById(R.id._recyclerview1);

		list = new ArrayList<LeaveReq>();

		adapterLeaveRequestList = new AdapterLeaveRequestList(this , list);

		LinearLayoutManager layoutmanager = new LinearLayoutManager(this);
		recyclerView.setLayoutManager( layoutmanager);
		recyclerView.setAdapter(adapterLeaveRequestList);

		stduid = getIntent().getStringExtra("uid");

		reference = FirebaseDatabase.getInstance().getReference().child("Classes").child(stdclassid);


		if(isstaff.equals("yes")){
			LoadRequestList2();

		}
		else  if (isstaff.equals("no")){
			LoadRequestList();

		}


		//Toast.makeText(this, "uid : "+stduid, Toast.LENGTH_SHORT).show();
	//	Toast.makeText(this, "classid : "+stdclassid, Toast.LENGTH_SHORT).show();
		//stduid = getIntent().getStringExtra("uid");
	}


	public void LoadRequestList(){

		LeaveReq req = new LeaveReq();
		reference.child("Leave").addValueEventListener(new ValueEventListener() {

			@Override
			public void onDataChange(@NonNull DataSnapshot snapshot) {
				for (DataSnapshot snapshot1 : snapshot.getChildren()){

					if (snapshot1.child("stdUid").getValue(String.class).equals(stduid)){

						//for (DataSnapshot snapshot2 : snapshot1.getChildren()){

							req.setReqStatus(snapshot1.child("reqStatus").getValue(String.class));

							req.setDate1(snapshot1.child("date1").getValue(String.class));
							list.add(req);
					//	}


						adapterLeaveRequestList.notifyDataSetChanged();


					}
				}

			}

			@Override
			public void onCancelled(@NonNull DatabaseError error) {

			}
		});

		adapterLeaveRequestList.notifyDataSetChanged();


	}

	public void LoadRequestList2(){

		list.clear();
		reference.child("Leave").addValueEventListener(new ValueEventListener() {

			@Override
			public void onDataChange(@NonNull DataSnapshot snapshot) {
				for (DataSnapshot snapshot1 : snapshot.getChildren()){

					snapshot.getKey();
					snapshot1.getKey();
					LeaveReq req = new LeaveReq();

					//if (snapshot1.getKey().equals(stduid)){

					//	for (DataSnapshot snapshot2 : snapshot1.getChildren()){

							snapshot1.getKey();
							req.setReqStatus(snapshot1.child("reqStatus").getValue(String.class));

							req.setDate1(snapshot1.child("reqReason").getValue(String.class));
							//req.setDate1(snapshot2.child("date1").getValue(String.class));
							list.add(req);
					adapterLeaveRequestList.notifyDataSetChanged();

					//	}
				//	}
				}

			}

			@Override
			public void onCancelled(@NonNull DatabaseError error) {

			}
		});

		adapterLeaveRequestList.notifyDataSetChanged();

	}

	public void GoToNewLeaveRequest(View view) {

		Intent intent = new Intent(StdRequestList.this , NewLeaveRequest.class);
		intent.putExtra("uid", stduid);
		startActivity(intent);
	}
}