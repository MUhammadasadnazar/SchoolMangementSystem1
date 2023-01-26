package com.example.schoolmangementsystem1.Attendance;

import java.util.ArrayList;

import com.example.schoolmangementsystem1.Model.Student;
import com.example.schoolmangementsystem1.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class StdAttenReportActivity extends AppCompatActivity {

	RecyclerView recyclerView;
	ArrayList<Attendance> list ;
	AdapterAttReport adapterAttReport;
	SharedPreferences sharedPreferences;
	DatabaseReference databaseReferenceattreprt;
	String stdid = "";


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_std_atten_report);
		recyclerView = findViewById(R.id._recyclerviewattreport);
		list =  new ArrayList<Attendance>();
		adapterAttReport = new AdapterAttReport(list , this);
		sharedPreferences = getSharedPreferences("my_pref" , MODE_PRIVATE);

		stdid = getIntent().getStringExtra("stdid");

		String uid = "";
		//uid = "vHKiHvEXm9fFYVamj27NgQEodxR2";
		uid = sharedPreferences.getString("uid" , "");

		databaseReferenceattreprt = FirebaseDatabase.getInstance().getReference("Classes")
				.child(uid).child("Attendance").child("2023").child("Jan");

		Toast.makeText(this, ""+stdid, Toast.LENGTH_LONG).show();

		Log.d("reference123" , databaseReferenceattreprt+"");

		LinearLayoutManager layoutmanager = new GridLayoutManager(this , 3);
		recyclerView.setLayoutManager( layoutmanager);
		recyclerView.setAdapter(adapterAttReport);

		LoadAttReport();
	}

	public  void LoadAttReport(){

		databaseReferenceattreprt.addValueEventListener(new ValueEventListener() {

			@Override
			public void onDataChange(@NonNull DataSnapshot snapshot) {
				//Toast.makeText(StdAttenReportActivity.this, "toast", Toast.LENGTH_SHORT).show();

				for (DataSnapshot snapshot1 : snapshot.getChildren()){

					for (DataSnapshot snapshot2 : snapshot1.getChildren()){

						Toast.makeText(StdAttenReportActivity.this, "toast1"+snapshot2.getKey(), Toast.LENGTH_SHORT).show();
						Attendance attendance = new Attendance();

						if (snapshot2.getKey().toString().equals(stdid)){
							String date = snapshot2.child("attDate").getValue(String.class);
							String month = snapshot2.child("attmonth").getValue(String.class);
							String status = snapshot2.child("status").getValue(String.class);

							Toast.makeText(StdAttenReportActivity.this, "toast1"+status, Toast.LENGTH_SHORT).show();


							attendance.setAttDate(date+"");
							attendance.setStatus(status+"");
							attendance.setAttmonth(month+"");

							list.add(attendance);

							adapterAttReport.notifyDataSetChanged();
						}
					}




				}

			}

			@Override
			public void onCancelled(@NonNull DatabaseError error) {

			}
		});

		adapterAttReport.notifyDataSetChanged();


	}
}