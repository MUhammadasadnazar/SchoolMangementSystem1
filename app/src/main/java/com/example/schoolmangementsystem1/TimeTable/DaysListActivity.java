package com.example.schoolmangementsystem1.TimeTable;

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

public class DaysListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
	ListView listViewDayslist;
	ArrayList<String> dayslist;

	SharedPreferences sharedPreferences;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_days_list);
		listViewDayslist = findViewById(R.id._listviewdays);
		sharedPreferences = getSharedPreferences(getString(R.string.shared_pref) , MODE_PRIVATE);
		dayslist = new ArrayList<>();
		dayslist.add("Monday");
		dayslist.add("Tuesday");
		dayslist.add("Wednesday");
		dayslist.add("Thursday");
		dayslist.add("Friday");
		dayslist.add("Saturday");
		dayslist.add("Sunday");

		for (int i = 0; i<2;i++){

			long count1 = GetNoOfLectures(dayslist.get(i).toString() , i);
			//Toast.makeText(this, "count "+count1, Toast.LENGTH_SHORT).show();
			/*if (count1 > 0){
				dayslist.set(i , dayslist.get(i)+"\t"+count1+" Lectures");
			}*/
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this , android.R.layout.simple_list_item_1 , dayslist);

		adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);

		listViewDayslist.setAdapter(adapter);

		listViewDayslist.setOnItemClickListener(this);


	}

	public long GetNoOfLectures(String day , int i){

		final long[] count234 = new long[1];
		String classid = sharedPreferences.getString("uid" , "");
		DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
				.child("Classes").child(classid).child("TimeTable").child(day);

		reference.addValueEventListener(new ValueEventListener() {

			@Override
			public void onDataChange(@NonNull DataSnapshot snapshot) {

			//	for (DataSnapshot snapshot1 : snapshot.getChildren()){

					count234[0] = 	snapshot.getChildrenCount();

				if (count234[0] > 0){
					dayslist.set(i , dayslist.get(i)+"\t"+count234[0]+" Lectures");
				}
					//Toast.makeText(DaysListActivity.this, "val"+count[0], Toast.LENGTH_SHORT).show();

			//	}


			}

			@Override
			public void onCancelled(@NonNull DatabaseError error) {
				Toast.makeText(DaysListActivity.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();

			}
		});

		return  count234[0];

	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
		Intent intent = new Intent(DaysListActivity.this , TimeTableList.class);
		String[] array1 = dayslist.get(i).toString().split(("\t"));
		intent.putExtra("day" , array1[0]);
		startActivity(intent);
		//Toast.makeText(this, "list"+dayslist.get(i).toString(), Toast.LENGTH_SHORT).show();
	}
}