package com.example.schoolmangementsystem1.Complaint;

import java.util.UUID;

import com.example.MyServerDatetime;
import com.example.schoolmangementsystem1.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.content.SharedPreferences;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class SendNewComplaint extends AppCompatActivity {

	String stdid = "";
	DatabaseReference reference;
	String classId = "";
	EditText edttitle , edtdescription;

	SharedPreferences sharedPreferences;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_send_new_complaint);
		stdid = getIntent().getStringExtra("stdid");
		edttitle = findViewById(R.id._edttitle);
		edtdescription = findViewById(R.id._edtdes);

		sharedPreferences = getSharedPreferences(getString(R.string.shared_pref) , MODE_PRIVATE);

		  classId = sharedPreferences.getString("uid" , "");

		reference = FirebaseDatabase.getInstance().getReference()
				.child("Classes").child(classId).child("complaints");

		MyServerDatetime myServerDatetime = new MyServerDatetime();

		myServerDatetime.getServerDateTime(this);

	}

	public void SendComplaint(View view) {
	    String	timestmp = sharedPreferences.getString("tmee" , "");
		String uuid = UUID.randomUUID().toString();

		if (edttitle.getText().toString() == null || edttitle.getText().toString().equals("")
				|| edtdescription.getText().toString() == null || edtdescription.getText().toString().equals("")){
			{
				Toast.makeText(this, "Please Fill All the Fields...", Toast.LENGTH_SHORT).show();

			}
		}
		else {

			Complaint complaint = new Complaint();

			complaint.setComplaintId(uuid);
			complaint.setComplaintDetail(edtdescription.getText().toString()+"");
			complaint.setStdID(stdid);
			complaint.setComplaintTitle(edttitle.getText().toString()+"");
			complaint.setComplaintTimeStamp(timestmp+"");

			reference.child(uuid).setValue(complaint);
			Toast.makeText(this, "Complaint Sent SuccessFully...", Toast.LENGTH_SHORT).show();
			finish();
		}
	}
}