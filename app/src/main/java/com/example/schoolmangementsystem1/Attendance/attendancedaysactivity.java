package com.example.schoolmangementsystem1.Attendance;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class attendancedaysactivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

	ListView listViewDayslist;
	ArrayList<String> dayslist;
	ArrayAdapter<String> adapter;
	DatabaseReference databaseReferencedayslist;
	SharedPreferences sharedPreferences;
	String attmonth = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_attendancedaysactivity);

		listViewDayslist = findViewById(R.id._listviewattendays);
		dayslist = new ArrayList<>();

		attmonth = getIntent().getStringExtra("attmonth");

		sharedPreferences = getSharedPreferences("my_pref" , MODE_PRIVATE);


		String uid = "";
		uid = sharedPreferences.getString("uid" , "");

		databaseReferencedayslist = FirebaseDatabase.getInstance().getReference("Classes")
				.child(uid).child("Attendance").child("2023").child(attmonth);

		 adapter = new ArrayAdapter<String>(this , android.R.layout.simple_list_item_1 , dayslist);

		adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);

		listViewDayslist.setAdapter(adapter);

		listViewDayslist.setOnItemClickListener(this);

		getDaysList();
	}

	public void getDaysList(){

		databaseReferencedayslist.addValueEventListener(new ValueEventListener() {

			@Override
			public void onDataChange(@NonNull DataSnapshot snapshot) {
				for(DataSnapshot snapshot1 : snapshot.getChildren()){

					dayslist.add(snapshot1.getKey()+" "+attmonth);
					adapter.notifyDataSetChanged();
					//Toast.makeText(attendancedaysactivity.this, "days"+snapshot1.getKey(), Toast.LENGTH_SHORT).show();

				}
			}

			@Override
			public void onCancelled(@NonNull DatabaseError error) {

			}
		});

	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
		Intent intent = new Intent(attendancedaysactivity.this , TodaysAttendance.class);
		intent.putExtra("day1" , dayslist.get(i));
		startActivity(intent);

	}
}