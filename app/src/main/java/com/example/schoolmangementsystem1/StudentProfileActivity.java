package com.example.schoolmangementsystem1;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class StudentProfileActivity extends AppCompatActivity {

	SharedPreferences sharedPreferences;
	String stdclassid = "";
	String stduid = "";

	DatabaseReference reference;

	TextView tvname , tvstdrollno , tvstdmobleno , tvemailaddress , tvgname , tvgpno , tvhomeaddress;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_student_profile);
		
		
		tvname = findViewById(R.id._tvfname);
		tvstdrollno = findViewById(R.id._tvrollno);
		tvstdmobleno = findViewById(R.id._Mobileno);
		tvemailaddress = findViewById(R.id._emailaddress);

		tvgname = findViewById(R.id._GMobileno);
		tvgpno = findViewById(R.id._Gemailaddress);
		tvhomeaddress = findViewById(R.id._Ghomeaddress);

		sharedPreferences = getSharedPreferences(getString(R.string.shared_pref) , MODE_PRIVATE);

		stdclassid = sharedPreferences.getString("uid" , "");
		stduid = sharedPreferences.getString("uid2" , "");
		reference = FirebaseDatabase.getInstance().getReference()
				.child("Classes").child(stdclassid).child("Students").child(stduid);

		Toast.makeText(this, "uid"+stduid, Toast.LENGTH_SHORT).show();
		Toast.makeText(this, "classid"+stdclassid, Toast.LENGTH_SHORT).show();

		getStdProfileDetails();




	}

	public void getStdProfileDetails(){
		reference.addValueEventListener(new ValueEventListener() {

			@Override
			public void onDataChange(@NonNull DataSnapshot snapshot) {

			//	for (DataSnapshot snapshot1 : snapshot.getChildren()){
					String stdfname = snapshot.child("stdName").getValue(String.class);
					String stdlname = snapshot.child("stdLastName").getValue(String.class);
					String stdrollno = snapshot.child("stdRollNo").getValue(String.class);
					String stdmobleno = snapshot.child("stdMobileNo").getValue(String.class);
					String emailaddress = snapshot.child("stdEmailAddres").getValue(String.class);
					String gaurdianName = snapshot.child("gaurdianName").getValue(String.class);
					String gaurdianPhoneNo = snapshot.child("gaurdianPhoneNo").getValue(String.class);
					String stdHomeAddress = snapshot.child("stdHomeAddress").getValue(String.class);

				//stdHomeAddress


				tvname.setText(stdfname+"\t"+stdlname);
					tvstdrollno.setText(stdrollno+"");
				     tvstdmobleno.setText(stdmobleno);
					 tvemailaddress.setText(emailaddress);

					 tvgname.setText(gaurdianName+"");
					 tvgpno.setText(gaurdianPhoneNo+"");
					 tvhomeaddress.setText(stdHomeAddress+"");


				//	}

			}

			@Override
			public void onCancelled(@NonNull DatabaseError error) {

			}
		});
	}
}