package com.example.schoolmangementsystem1.Complaint;

import java.util.ArrayList;

import com.example.schoolmangementsystem1.R;
import com.example.schoolmangementsystem1.meeting.AdapterMeetingList;
import com.example.schoolmangementsystem1.meeting.Meeting;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ComplaintListActivity extends AppCompatActivity {
	String stdid = "";

	RecyclerView recyclerView;
	ArrayList<Meeting> list;
	AdapterMeetingList adapterMeetingList;
	DatabaseReference reference;
	String classid = "";
	String isStaff = "";
	CardView cardViewadnew;

	SharedPreferences sharedPreferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_complaint_list);
		stdid = getIntent().getStringExtra("stdid");
		cardViewadnew = findViewById(R.id._cardviewaddnewcomplaint);


		recyclerView = findViewById(R.id._recyclerviewcmplntlist);
		//cardViewadnew = findViewById(R.id._cardviewAddNew);
		list = new ArrayList<Meeting>();
		sharedPreferences = getSharedPreferences(getString(R.string.shared_pref) , MODE_PRIVATE);

		classid = sharedPreferences.getString("uid" , "");
		isStaff = sharedPreferences.getString("isstaff" , "");
		if (isStaff.equals("yes")){
			cardViewadnew.setVisibility(View.VISIBLE);
		}

		adapterMeetingList = new AdapterMeetingList(this , list);

		reference = FirebaseDatabase.getInstance().getReference()
				.child("Classes").child(classid).child("complaints");

		LinearLayoutManager layoutmanager = new LinearLayoutManager(this);
		recyclerView.setLayoutManager( layoutmanager);
		recyclerView.setAdapter(adapterMeetingList);

		LoadComplaintList();
	}

	public void LoadComplaintList(){

		reference.addValueEventListener(new ValueEventListener() {

			@Override
			public void onDataChange(@NonNull DataSnapshot snapshot) {
				list.clear();

				for (DataSnapshot snapshot1 : snapshot.getChildren()){

					if (snapshot1.child("stdID").getValue(String.class).equals(stdid))
					{
						Meeting meeting = new Meeting();
						String[] array1 = snapshot1.child("complaintTimeStamp").getValue(String.class).split(" ");
						meeting.setMeetingdate(array1[0]);
						meeting.setMeetingTime(snapshot1.child("complaintTitle").getValue(String.class));
						meeting.setMeetingAgenda(snapshot1.child("complaintDetail").getValue(String.class));
						list.add(meeting);
						adapterMeetingList.notifyDataSetChanged();

					}

					//	Toast.makeText(MeetingList.this, "//"+snapshot1.child("meetingAgenda").getValue(String.class), Toast.LENGTH_SHORT).show();

				}


			}

			@Override
			public void onCancelled(@NonNull DatabaseError error) {

			}
		});

		adapterMeetingList.notifyDataSetChanged();

	}


	public void AddNewComplaniint(View view) {
		Intent intent = new Intent(ComplaintListActivity.this , SendNewComplaint.class);
		intent.putExtra("stdid" , stdid);
		startActivity(intent);
	}
}