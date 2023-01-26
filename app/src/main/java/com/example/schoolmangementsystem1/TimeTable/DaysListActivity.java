package com.example.schoolmangementsystem1.TimeTable;

import java.util.ArrayList;

import com.example.schoolmangementsystem1.R;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class DaysListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
	ListView listViewDayslist;
	ArrayList<String> dayslist;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_days_list);
		listViewDayslist = findViewById(R.id._listviewdays);
		dayslist = new ArrayList<>();
		dayslist.add("Monday");
		dayslist.add("Tuesday");
		dayslist.add("Wednesday");
		dayslist.add("Thursday");
		dayslist.add("Friday");
		dayslist.add("Saturday");
		dayslist.add("Sunday");
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this , android.R.layout.simple_list_item_1 , dayslist);

		adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);

		listViewDayslist.setAdapter(adapter);

		listViewDayslist.setOnItemClickListener(this);


	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
		Intent intent = new Intent(DaysListActivity.this , TimeTableList.class);
		intent.putExtra("day" , dayslist.get(i).toString());
		startActivity(intent);
		//Toast.makeText(this, "list"+dayslist.get(i).toString(), Toast.LENGTH_SHORT).show();
	}
}