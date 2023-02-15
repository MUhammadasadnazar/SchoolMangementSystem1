package com.example.schoolmangementsystem1.meeting;

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
import android.widget.AbsListView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MeetingList extends AppCompatActivity {
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
		setContentView(R.layout.activity_meeting_list);
		recyclerView = findViewById(R.id._recyclerviewmeeting);
		cardViewadnew = findViewById(R.id._cardviewAddNew);
		list = new ArrayList<Meeting>();
		sharedPreferences = getSharedPreferences(getString(R.string.shared_pref) , MODE_PRIVATE);

		classid = sharedPreferences.getString("uid" , "");
		isStaff = sharedPreferences.getString("isstaff" , "");
		if (isStaff.equals("yes")){
			cardViewadnew.setVisibility(View.VISIBLE);
		}

		adapterMeetingList = new AdapterMeetingList(this , list);

		reference = FirebaseDatabase.getInstance().getReference()
				.child("Classes").child(classid).child("meetings");

		LinearLayoutManager layoutmanager = new LinearLayoutManager(this);
		recyclerView.setLayoutManager( layoutmanager);
		recyclerView.setAdapter(adapterMeetingList);

		LoadMeetingList();


	}
	public void LoadMeetingList(){

		reference.addValueEventListener(new ValueEventListener() {

			@Override
			public void onDataChange(@NonNull DataSnapshot snapshot) {
				list.clear();

				for (DataSnapshot snapshot1 : snapshot.getChildren()){

				//	Toast.makeText(MeetingList.this, "//"+snapshot1.child("meetingAgenda").getValue(String.class), Toast.LENGTH_SHORT).show();
					Meeting meeting = new Meeting();
					String[] array1 = snapshot1.child("meetingdate").getValue(String.class).split(" ");
					meeting.setMeetingdate(array1[0]);
					meeting.setMeetingTime(snapshot1.child("meetingTime").getValue(String.class));
					meeting.setMeetingAgenda(snapshot1.child("meetingAgenda").getValue(String.class));
					list.add(meeting);
					adapterMeetingList.notifyDataSetChanged();
				}


			}

			@Override
			public void onCancelled(@NonNull DatabaseError error) {

			}
		});

		adapterMeetingList.notifyDataSetChanged();

	}

	public void ScheduleNewMeeting(View view) {

		Intent intent = new Intent(MeetingList.this , AddNewMeeting.class);
		startActivity(intent);
	}
}