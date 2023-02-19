package com.example.schoolmangementsystem1.LeaveRequest;

import java.util.ArrayList;
import java.util.Calendar;

import com.example.schoolmangementsystem1.Interface.onClickRVItem;
import com.example.schoolmangementsystem1.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
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
	CardView cardview;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_std_request_list);
		sharedPreferences = getSharedPreferences("my_pref" , MODE_PRIVATE);
		stdclassid = sharedPreferences.getString("uid","");
		isstaff = sharedPreferences.getString("isstaff","");
		cardview = findViewById(R.id._cardviewaddnew);

		if (isstaff.equals("no")){
			cardview.setVisibility(View.VISIBLE);
		}

		recyclerView = findViewById(R.id._recyclerview1);

		list = new ArrayList<LeaveReq>();

		adapterLeaveRequestList = new AdapterLeaveRequestList(this, list, true, new onClickRVItem() {

			@Override
			public void onClickRvItem(View view, int position) {

			}
		});

		if (isstaff.equals("yes")){

			adapterLeaveRequestList = new AdapterLeaveRequestList(this, list, false, new onClickRVItem() {

				@Override
				public void onClickRvItem(View view, int position) {
					ShowDialoge(list.get(position).getReqid());
					//Toast.makeText(StdRequestList.this, "Hello"+list.get(position).getReqRemarks(), Toast.LENGTH_SHORT).show();
				}
			});

		}


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


	//For Student
	public void LoadRequestList(){

		reference.child("Leave").addValueEventListener(new ValueEventListener() {

			@Override
			public void onDataChange(@NonNull DataSnapshot snapshot) {
				list.clear();

				for (DataSnapshot snapshot1 : snapshot.getChildren()){

					if (snapshot1.child("stdUid").getValue(String.class).equals(stduid)){

						LeaveReq req = new LeaveReq();

						//for (DataSnapshot snapshot2 : snapshot1.getChildren()){

							req.setReqStatus(snapshot1.child("reqStatus").getValue(String.class));

							req.setReqid(snapshot1.child("reqid").getValue(String.class));
							req.setDate1(snapshot1.child("date1").getValue(String.class));
							req.setDate2(snapshot1.child("date2").getValue(String.class));
						req.setReqReason(snapshot1.child("reqReason").getValue(String.class));
						req.setReqRemarks(snapshot1.child("reqRemarks").getValue(String.class));
							list.add(req);
					//	}


						//adapterLeaveRequestList.notifyDataSetChanged();


					}
				}

			}

			@Override
			public void onCancelled(@NonNull DatabaseError error) {

			}
		});

		adapterLeaveRequestList.notifyDataSetChanged();


	}

	//For Staff
	public void LoadRequestList2(){

		reference.child("Leave").addValueEventListener(new ValueEventListener() {

			@Override
			public void onDataChange(@NonNull DataSnapshot snapshot) {
				list.clear();

				for (DataSnapshot snapshot1 : snapshot.getChildren()){

					snapshot.getKey();
					snapshot1.getKey();

					         String stdID  = snapshot1.child("stdUid").getValue(String.class);
							 StdNameById(stdID , snapshot1);
							snapshot1.getKey();

					//	}
				//	}
				}

			}

			@Override
			public void onCancelled(@NonNull DatabaseError error) {

			}
		});

		//adapterLeaveRequestList.notifyDataSetChanged();

	}

	public void StdNameById(String uid , DataSnapshot snapshot1){

		DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference().child("Classes")
				.child(stdclassid).child("Students").child(uid);
		reference1.addValueEventListener(new ValueEventListener() {

			@Override
			public void onDataChange(@NonNull DataSnapshot snapshot) {

				final String[] stdName = { "" };
				final String[] atdrollno = { "" };

				  stdName[0] = snapshot.child("stdName").getValue(String.class);
				  atdrollno[0] = snapshot.child("stdRollNo").getValue(String.class);

				AddToList(snapshot1 , stdName[0] , atdrollno[0]);


				//	Toast.makeText(StdRequestList.this, "name"+stdName[0], Toast.LENGTH_SHORT).show();

			}

			@Override
			public void onCancelled(@NonNull DatabaseError error) {

			}
		});


		//return  stdName[0]+" / "+atdrollno[0];
	}

	public  void AddToList(DataSnapshot snapshot1 , String stdname , String stdroll){

		LeaveReq req = new LeaveReq();

	//	Toast.makeText(StdRequestList.this, "name"+stdname, Toast.LENGTH_SHORT).show();

		req.setReqStatus(snapshot1.child("reqStatus").getValue(String.class));
		req.setStdName(stdname);
		req.setStdRollNo(stdroll);

		req.setDate1(snapshot1.child("date1").getValue(String.class));
		req.setDate2(snapshot1.child("date2").getValue(String.class));
		req.setReqReason(snapshot1.child("reqReason").getValue(String.class));
		req.setReqRemarks(snapshot1.child("reqRemarks").getValue(String.class));
		req.setReqid(snapshot1.child("reqid").getValue(String.class));

		//req.setDate1(snapshot2.child("date1").getValue(String.class));
		list.add(req);
		adapterLeaveRequestList.notifyDataSetChanged();

	}
	public void GoToNewLeaveRequest(View view) {

		Intent intent = new Intent(StdRequestList.this , NewLeaveRequest.class);
		intent.putExtra("uid", stduid);
		startActivity(intent);
	}

	public void ShowDialoge(String reqid){
		AlertDialog dialog = new AlertDialog.Builder(this).create();
		View view = LayoutInflater.from(this).inflate(R.layout.dialogeacceptreject , null);
		EditText editTextremarks = view.findViewById(R.id._edtapprovalremarks);

		Button btnaccept = view.findViewById(R.id._btnaccept);
		Button btnreject = view.findViewById(R.id._btnreject);



			btnaccept.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View view) {
					if (editTextremarks.getText().toString().trim().equals("")){
						Toast.makeText(StdRequestList.this, "Please Enter Approval remarks...", Toast.LENGTH_SHORT).show();
					}
					else {
						String timestamp = Calendar.getInstance().getTime().toString();


						reference.child("Leave").child(reqid).child("reqStatus").setValue("Approved");
						reference.child("Leave").child(reqid).child("ReqApprovalRemarks").setValue(editTextremarks.getText().toString().trim());
						reference.child("Leave").child(reqid).child("ReqApproveTimeSpan").setValue(timestamp);

						dialog.cancel();
						Toast.makeText(StdRequestList.this, "Success MSG", Toast.LENGTH_SHORT).show();

					}

				}
			});
			btnreject.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View view) {
					if (editTextremarks.getText().toString().trim().equals("")){
						Toast.makeText(StdRequestList.this, "Please Enter Approval remarks...", Toast.LENGTH_SHORT).show();
					}
					else {
						String timestamp = Calendar.getInstance().getTime().toString();


						reference.child("Leave").child(reqid).child("reqStatus").setValue("Rejected");
						reference.child("Leave").child(reqid).child("ReqApprovalRemarks").setValue(editTextremarks.getText().toString().trim());
						reference.child("Leave").child(reqid).child("ReqApproveTimeSpan").setValue(timestamp);

						dialog.cancel();
						Toast.makeText(StdRequestList.this, "Success MSG", Toast.LENGTH_SHORT).show();

					}

				}
			});


		//reference.child("Leave").child(reqid).child("reqStatus").setValue("Approved");
		//Toast.makeText(this, "id: "+reqid, Toast.LENGTH_SHORT).show();

		dialog.setView(view);
		dialog.show();
	}
}