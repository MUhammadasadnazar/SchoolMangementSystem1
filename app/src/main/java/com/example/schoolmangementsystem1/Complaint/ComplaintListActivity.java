package com.example.schoolmangementsystem1.Complaint;

import com.example.schoolmangementsystem1.R;

import android.content.Intent;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class ComplaintListActivity extends AppCompatActivity {
	String stdid = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_complaint_list);
		stdid = getIntent().getStringExtra("stdid");
	}

	public void AddNewComplaniint(View view) {
		Intent intent = new Intent(ComplaintListActivity.this , SendNewComplaint.class);
		intent.putExtra("stdid" , stdid);
		startActivity(intent);
	}
}