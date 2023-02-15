package com.example.schoolmangementsystem1.Attendance;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.example.schoolmangementsystem1.R;
import com.example.schoolmangementsystem1.TimeTable.DaysListActivity;
import com.example.schoolmangementsystem1.TimeTable.TimeTableList;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.functions.HttpsCallableResult;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MonthListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
 	// ...

	ListView listViewMonthlist;
	ArrayList<String> MonthList;
	DatabaseReference databaseReferencemonthlist;
	SharedPreferences sharedPreferences;

	ArrayAdapter<String> adapter;
	Button btntodayattendance;
	String uid , isstaff = "";




	TextView tv1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_month_list);
		listViewMonthlist = findViewById(R.id._listviewmonthlist);
		btntodayattendance = findViewById(R.id._btntodayattendance);
		MonthList = new ArrayList<>();
		sharedPreferences = getSharedPreferences("my_pref" , MODE_PRIVATE);


		uid = sharedPreferences.getString("uid" , "");
		isstaff = sharedPreferences.getString("isstaff" , "");

		if (isstaff.equals("no")){
			btntodayattendance.setVisibility(View.GONE);
		}

		databaseReferencemonthlist = FirebaseDatabase.getInstance().getReference("Classes")
				.child(uid).child("Attendance").child("2023");

		 adapter = new ArrayAdapter<String>(this , android.R.layout.simple_list_item_1 , MonthList);

		adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);

		listViewMonthlist.setAdapter(adapter);

		listViewMonthlist.setOnItemClickListener(this);
		LoadMonthList();





	}

	private void LoadMonthList() {

		databaseReferencemonthlist.addValueEventListener(new ValueEventListener() {

			@Override
			public void onDataChange(@NonNull DataSnapshot snapshot) {

				for (DataSnapshot snapshot1 : snapshot.getChildren()){

					MonthList.add(snapshot1.getKey()+"");


					adapter.notifyDataSetChanged();
				//Toast.makeText(MonthListActivity.this, "val"+snapshot1.getKey(), Toast.LENGTH_SHORT).show();

				}
			}

			@Override
			public void onCancelled(@NonNull DatabaseError error) {

			}
		});


	}

	public void GoToTodayAttendance(View view) {
		Intent intent = new Intent(MonthListActivity.this , TodaysAttendance.class);
		intent.putExtra("day1" , "");
		startActivity(intent);
	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

		if (isstaff.equals("yes")){

			Intent intent = new Intent(MonthListActivity.this , attendancedaysactivity.class);
			intent.putExtra("attmonth" , MonthList.get(i).toString());
			startActivity(intent);
		}
		else if (isstaff.equals("no")){
			String stduid = sharedPreferences.getString("uid2" , "");

			Intent intent = new Intent(MonthListActivity.this , StdAttenReportActivity.class);
			intent.putExtra("stdid" , stduid);
			intent.putExtra("moonth" , MonthList.get(i).toString());
			startActivity(intent);
		}

	}
}