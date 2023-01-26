package com.example.schoolmangementsystem1.Attendance;

import java.util.ArrayList;
import java.util.Calendar;

import com.example.schoolmangementsystem1.Interface.onCheckChanged;
import com.example.schoolmangementsystem1.Interface.onClickRVItem;
import com.example.schoolmangementsystem1.Model.Student;
import com.example.schoolmangementsystem1.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TodaysAttendance extends AppCompatActivity {
	AdapterAttendance adapterAttendance;
	ArrayList<Attendance> list;
	RecyclerView recyclerView;
	DatabaseReference databasereferencestudentslist , databaseReferenceattendance ;

	SharedPreferences sharedPreferences;
	String month = "";
	String date = "";
	String timestamp = "";
	String day1 = "";


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_todays_attendance);
		recyclerView = findViewById(R.id._recyclerviewattendance);
		sharedPreferences = getSharedPreferences("my_pref" , MODE_PRIVATE);

		Intent intent = getIntent();

		String day112 = "";
		day112 = intent.getStringExtra("day1");

		if (!day112.equals("")){
			String [] array123 = day112.split(" ");
			day1 = array123[0];

		}


		timestamp = Calendar.getInstance().getTime().toString();

		String[] timestamplist = timestamp.split(" ");
		month = timestamplist[1];
		date = timestamplist[2];
		Toast.makeText(this, "month : "+month, Toast.LENGTH_SHORT).show();
		Toast.makeText(this, "date : "+date, Toast.LENGTH_SHORT).show();
//		Toast.makeText(this, "time : "+timestamplist[0]+timestamplist[1], Toast.LENGTH_SHORT).show();
//
		String uid = "";
		uid = sharedPreferences.getString("uid" , "");

		databasereferencestudentslist = FirebaseDatabase.getInstance().getReference("Classes")
				.child(uid).child("Students");

		databaseReferenceattendance = FirebaseDatabase.getInstance().getReference("Classes")
				.child(uid).child("Attendance").child("2023");

		list = new ArrayList<Attendance>();
		adapterAttendance = new AdapterAttendance(this, list,
				new onCheckChanged() {

					@Override
					public void onCheckChanged(RadioGroup radioGroup, int checkid, int position, String status) {

						Toast.makeText(TodaysAttendance.this, "id:" + list.get(position).getStatus(), Toast.LENGTH_SHORT).show();
						//Toast.makeText(TodaysAttendance.this, "id:"+checkid/(position+1), Toast.LENGTH_SHORT).show();
					}
				}, new onClickRVItem() {

			@Override
			public void onClickRvItem(View view, int position) {
				Intent intent1 = new Intent(TodaysAttendance.this , StdAttenReportActivity.class);
				intent1.putExtra("stdid" , list.get(position).getStdUid()+"");
				startActivity(intent1);
			}
		});

		LinearLayoutManager layoutmanager = new LinearLayoutManager(this);
		recyclerView.setLayoutManager( layoutmanager);
		recyclerView.setAdapter(adapterAttendance);

		if (day1.equals("")){
			LoadStudentList();

		}
		else {
			LoadStudentList2();
		}

		//Data();
	}

	public void Data(){
		list.clear();
		Attendance attendance = new Attendance();
		Attendance attendance1 = new Attendance();
		Attendance attendance2 = new Attendance();
		attendance.setStdrollNo("123443");
		attendance1.setStdrollNo("123443");
		attendance2.setStdrollNo("123443");
		list.add(attendance);
		list.add(attendance1);
		list.add(attendance2);

		adapterAttendance.notifyDataSetChanged();


	}

	public void LoadStudentList(){


		databasereferencestudentslist.addValueEventListener(new ValueEventListener() {

			@Override
			public void onDataChange(@NonNull DataSnapshot snapshot) {
				for (DataSnapshot snapshot1 : snapshot.getChildren()){
					Attendance student = new Attendance();

					student.setStdName(snapshot1.child("stdName").getValue(String.class));
					student.setStdUid(snapshot1.child("stdID").getValue(String.class));
					student.setStdrollNo(snapshot1.child("stdRollNo").getValue(String.class));

					student.setStatus("Present");
					list.add(student);

					adapterAttendance.notifyDataSetChanged();
				}

			}

			@Override
			public void onCancelled(@NonNull DatabaseError error) {

			}
		});

		// listStudents.add(new Student("name 1223" , "LastName" , "56563789"));
		//listStudents.add(student);
		adapterAttendance.notifyDataSetChanged();
	}
	public void LoadStudentList2(){


		databaseReferenceattendance.child(month).child(day1).addValueEventListener(new ValueEventListener() {

			@Override
			public void onDataChange(@NonNull DataSnapshot snapshot) {
				for (DataSnapshot snapshot1 : snapshot.getChildren()){
					Attendance student = new Attendance();

					student.setStdName(snapshot1.child("stdName").getValue(String.class));
					student.setStdUid(snapshot1.child("stdUid").getValue(String.class));
					student.setStdrollNo(snapshot1.child("stdrollNo").getValue(String.class));
					student.setStatus(snapshot1.child("status").getValue(String.class));

				//	Toast.makeText(TodaysAttendance.this, "stte"+snapshot1.child("status").getValue(String.class), Toast.LENGTH_SHORT).show();

					list.add(student);

					adapterAttendance.notifyDataSetChanged();
				}

			}

			@Override
			public void onCancelled(@NonNull DatabaseError error) {

			}
		});

		// listStudents.add(new Student("name 1223" , "LastName" , "56563789"));
		//listStudents.add(student);
		adapterAttendance.notifyDataSetChanged();
	}


	public void textChanged(View view) {

		//databaseReferenceattendance.child(month).child(date);
		for (int i = 0; i<list.size();i++){
			String status = list.get(i).getStatus();
			Toast.makeText(this, "Status:"+status, Toast.LENGTH_SHORT).show();

			Attendance attendance = new Attendance();

			attendance.setStdrollNo(list.get(i).getStdrollNo()+"");

			attendance.setStatus(status);

			attendance.setStdUid(list.get(i).getStdUid()+"");

			attendance.setStdName(list.get(i).getStdName());


			attendance.setAttDate(date+"");
			attendance.setAttmonth(month+"");
			attendance.setAttYear("2023");
			attendance.setTimeStamp(timestamp+"");


			databaseReferenceattendance.child(month).child(date).child(attendance.getStdUid()+"").setValue(attendance);

		}
	}
}