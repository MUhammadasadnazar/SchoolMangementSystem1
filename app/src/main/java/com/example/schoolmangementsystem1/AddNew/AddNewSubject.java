package com.example.schoolmangementsystem1.AddNew;

import java.util.ArrayList;
import java.util.UUID;

import com.example.schoolmangementsystem1.Model.Staff;
import com.example.schoolmangementsystem1.Model.Subject;
import com.example.schoolmangementsystem1.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.content.SharedPreferences;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;

public class AddNewSubject extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
	Spinner spinner1;


	String InchargeName , InchargeId = "";

	ArrayList<String> StaffNameList;
	ArrayList<Staff> staffArrayList;
	DatabaseReference databaseReference , databaseReferencesubjects;

	EditText edtsbjtitle , edtsbjnolectures , edtsbjoutline;

	SharedPreferences sharedPreferences;
	String uid = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_new_subject);

		edtsbjtitle = findViewById(R.id._edtsbjtitle);
		edtsbjnolectures = findViewById(R.id._edtsbjnolectures);
		edtsbjoutline = findViewById(R.id._sbjcoutline);

		StaffNameList = new ArrayList<String>();
		staffArrayList = new ArrayList<Staff>();

		sharedPreferences = getSharedPreferences("my_pref" , MODE_PRIVATE);

		uid = sharedPreferences.getString("uid", "");


		databaseReference = FirebaseDatabase.getInstance().getReference("Staff");
		databaseReferencesubjects = FirebaseDatabase.getInstance()
				.getReference("Classes").child(uid).child("Subjects");


		spinner1 = (Spinner) findViewById(R.id.spinner1);
		spinner1.setOnItemSelectedListener(this);

		GetStaffList();

		StaffNameList.add("Select Class Instructure");

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this , android.R.layout.simple_spinner_dropdown_item , StaffNameList);

		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner1.setAdapter(adapter);


	}

	public  void GetStaffList() {
		databaseReference.addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot snapshot) {

				for (DataSnapshot snapshot1 : snapshot.getChildren()){
					Staff staff1 = new Staff();
					String stafName = snapshot1.child("staffName").getValue(String.class);
					String stafId = snapshot1.child("staffId").getValue(String.class);

					staff1.setStaffId(stafId);
					staff1.setStaffName(stafName);

					StaffNameList.add(stafName);
					staffArrayList.add(staff1);
					Toast.makeText(AddNewSubject.this, "Name : "+StaffNameList.size(), Toast.LENGTH_SHORT).show();
				}

			}

			@Override
			public void onCancelled(@NonNull DatabaseError error) {
				//Toast.makeText(AddNewClass.this , "Hello" , Toast.LENGTH_LONG).show();

				// Toast.makeText(AddNewClass.this, "", Toast.LENGTH_SHORT).show();
			}
		});

	}


	public void AddNewSubjectt(View view) {

		String uuid = UUID.randomUUID()+"";
		Subject subject = new Subject();

		subject.setSbjTitle(edtsbjtitle.getText().toString());
		subject.setSbjNoLectures(edtsbjnolectures.getText().toString());
		subject.setSbjCourseOutLine(edtsbjoutline.getText().toString());
		subject.setSbjClassId(uid);
		subject.setSbjInsId(InchargeId);
		subject.setSbjInsName(InchargeName);
		subject.setSbjId(uuid);

		databaseReferencesubjects.push().setValue(subject);



	}

	@Override
	public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
		if (staffArrayList.size() > 0 && i != 0){
			// String stfname = staffArrayList.get(0).getStaffName();
			InchargeName = staffArrayList.get((i-1)).getStaffName();
			InchargeId = staffArrayList.get((i-1)).getStaffId();
			Toast.makeText(AddNewSubject.this, "Clicked"+i+InchargeId, Toast.LENGTH_SHORT).show();

		}

	}

	@Override
	public void onNothingSelected(AdapterView<?> adapterView) {

	}
}