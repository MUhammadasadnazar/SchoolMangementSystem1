package com.example.schoolmangementsystem1.TimeTable;

import java.util.ArrayList;

import com.example.schoolmangementsystem1.Model.Staff;
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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TimeTableList extends AppCompatActivity {
	String day = "";
	String uid = "";
	DatabaseReference databaseReferencetimetable;
	SharedPreferences sharedPreferences;
	RecyclerView recyclerView;
	AdapterTimeTable adapterTimeTable;
	ArrayList<Period> list;
	CardView cardviewtimetable;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_time_table_list);
		recyclerView = findViewById(R.id.listviewtimetable);
		cardviewtimetable = findViewById(R.id._cardviewtimetable);
		sharedPreferences = getSharedPreferences("my_pref" , MODE_PRIVATE);

		String isstaff = sharedPreferences.getString("isstaff" , "");

		if (isstaff.equals("yes")){

			cardviewtimetable.setVisibility(View.VISIBLE);
		}

		list = new ArrayList<Period>();

		uid = sharedPreferences.getString("uid", "");

		day = getIntent().getStringExtra("day");

		//Toast.makeText(this, "uid: "+uid, Toast.LENGTH_SHORT).show();

		databaseReferencetimetable = FirebaseDatabase.getInstance()
				.getReference("Classes").child(uid).child("TimeTable").child(day);

		adapterTimeTable = new AdapterTimeTable(this , list);

		LinearLayoutManager layoutmanager = new LinearLayoutManager(this);
		recyclerView.setLayoutManager( layoutmanager);
		recyclerView.setAdapter(adapterTimeTable);

		getTimeTableList();

	}

	public  void getTimeTableList(){

		databaseReferencetimetable.orderByChild("lectureNo").addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot snapshot) {
				list.clear();


				for (DataSnapshot snapshot1 : snapshot.getChildren()){
					//Staff staff1 = new Staff();
					Period period = new Period();
					String strttime = snapshot1.child("lectureTimeStart").getValue(String.class);
					String strtend = snapshot1.child("lectureTimeEnd").getValue(String.class);
					String subject1 = snapshot1.child("lectureSubject").getValue(String.class);
					String lectureType = snapshot1.child("lectureType").getValue(String.class);

					period.setLectureTimeStart(strttime);
					period.setLectureTimeEnd(strtend);
					period.setLectureType(lectureType);
					period.setLectureSubject(subject1);

					list.add(period);

					adapterTimeTable.notifyDataSetChanged();
					//String stafId = snapshot1.child("staffId").getValue(String.class);


					//Toast.makeText(TimeTableList.this, "Name : "+strttime, Toast.LENGTH_SHORT).show();
				}

			}

			@Override
			public void onCancelled(@NonNull DatabaseError error) {
				//Toast.makeText(AddNewClass.this , "Hello" , Toast.LENGTH_LONG).show();

				// Toast.makeText(AddNewClass.this, "", Toast.LENGTH_SHORT).show();
			}
		});


	}

	public void AddNewtimePeriod(View view) {
		Intent intent = new Intent(TimeTableList.this , AddNewTimePeriod.class);
		intent.putExtra("day" , day);
		startActivity(intent);
	}
}