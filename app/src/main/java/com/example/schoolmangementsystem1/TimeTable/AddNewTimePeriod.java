package com.example.schoolmangementsystem1.TimeTable;

import java.util.ArrayList;
import java.util.Calendar;

import com.example.schoolmangementsystem1.AddNew.AddNewSubject;
import com.example.schoolmangementsystem1.Model.Staff;
import com.example.schoolmangementsystem1.Model.Subject;
import com.example.schoolmangementsystem1.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.os.Build;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class AddNewTimePeriod extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
	TimePicker timePickerstart , timePickerend;
	Spinner spinnerclasstype , spinnerstafflist , spinnersubjectlist;
	ArrayList<String> classTypeList;
	DatabaseReference databaseReference , databaseReferencesubjects , databaseReferenceperiod;
	String uid = "";
	SharedPreferences sharedPreferences;
	ArrayList<String> StaffNameList;
	ArrayList<Staff> staffArrayList;


	ArrayList<String> SubjectNameList;
	ArrayList<Subject> SubjectArrayList;
	String day = "";

	String sbjId = "" , sbjTitle = "" , sbjIntructor = "" , sbjinstrId = "" , sbjType = ""
			, strttime = "" , endtime  = "" ;




	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_new_time_period);
		timePickerstart = findViewById(R.id._tpickerfrom);
		timePickerend = findViewById(R.id._tpickerto);

		day = getIntent().getStringExtra("day");

		spinnerclasstype = (Spinner) findViewById(R.id.spinnerclasstype);
		spinnerstafflist = (Spinner) findViewById(R.id.spinnerstafflist);
		spinnersubjectlist = (Spinner) findViewById(R.id.spinnersubjectlist);

		spinnerclasstype.setOnItemSelectedListener(this);
		spinnerstafflist.setOnItemSelectedListener(this);
		spinnersubjectlist.setOnItemSelectedListener(this);

		sharedPreferences = getSharedPreferences("my_pref" , MODE_PRIVATE);

		uid = sharedPreferences.getString("uid", "");


		databaseReference = FirebaseDatabase.getInstance().getReference("Staff");
		databaseReferencesubjects = FirebaseDatabase.getInstance()
				.getReference("Classes").child(uid).child("Subjects");

		databaseReferenceperiod = FirebaseDatabase.getInstance()
				.getReference("Classes").child(uid).child("TimeTable").child(day);

		//Class Type List And SPinner
		classTypeList = new ArrayList<String>();
		classTypeList.add("Select Class Type");
		classTypeList.add("Regular Class");
		classTypeList.add("MakeUp Class");
		classTypeList.add("Fun And Arts");
		classTypeList.add("Ethics");



		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this , android.R.layout.simple_spinner_dropdown_item , classTypeList);

		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerclasstype.setAdapter(adapter);

		//Staff List And Spinner

		StaffNameList = new ArrayList<String>();
		staffArrayList = new ArrayList<Staff>();
		GetStaffList();

		StaffNameList.add("Select Staff Name");

		ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this , android.R.layout.simple_spinner_dropdown_item , StaffNameList);

		adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerstafflist.setAdapter(adapter1);

		//Sbject List And Spinner
		SubjectNameList = new ArrayList<String>();
		SubjectArrayList = new ArrayList<Subject>();
		GetSubjectList();

		SubjectNameList.add("Select Subject");


		ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this , android.R.layout.simple_spinner_dropdown_item , SubjectNameList);

		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnersubjectlist.setAdapter(adapter2);


	}

	public void showTimePIcker(View view) {


		TimePickerDialog timePickerDialog = new TimePickerDialog(AddNewTimePeriod.this, new TimePickerDialog.OnTimeSetListener() {
			@RequiresApi(api = Build.VERSION_CODES.O)
			@Override
			public void onTimeSet(TimePicker timePicker, int i, int i1) {
				String time = i+":"+i1;

				if (view.getId() == R.id._tpickerfrom){
					timePickerstart.setHour(i);
					timePickerstart.setMinute(i1);
					 strttime = i+":"+i1;
				}
				else if (view.getId() == R.id._tpickerto){
					timePickerend.setHour(i);
					timePickerend.setMinute(i1);
					endtime = i+":"+i1;
				}

				//tvpickdateandtime.setText(prefered_Date +" \n "+time);
				//prefered_time = time ;

			}
		},Calendar.HOUR_OF_DAY, Calendar.MINUTE,true);
		timePickerDialog.show();
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

					if (stafName != null){

						StaffNameList.add(stafName);
						staffArrayList.add(staff1);
					}

					//Toast.makeText(AddNewTimePeriod.this, "Name : "+StaffNameList.size(), Toast.LENGTH_SHORT).show();
				}

			}

			@Override
			public void onCancelled(@NonNull DatabaseError error) {
				//Toast.makeText(AddNewClass.this , "Hello" , Toast.LENGTH_LONG).show();

				// Toast.makeText(AddNewClass.this, "", Toast.LENGTH_SHORT).show();
			}
		});

	}

	public void GetSubjectList(){

		databaseReferencesubjects.addValueEventListener(new ValueEventListener() {

			@Override
			public void onDataChange(@NonNull DataSnapshot snapshot) {
				for (DataSnapshot snapshot1 : snapshot.getChildren()){
					Subject subject = new Subject();

					subject.setSbjTitle(snapshot1.child("sbjTitle").getValue(String.class));
					subject.setSbjInsName(snapshot1.child("sbjInsName").getValue(String.class));
					subject.setSbjId(snapshot1.child("sbjId").getValue(String.class));
					subject.setSbjInsId(snapshot1.child("sbjInsId").getValue(String.class));
					subject.setSbjcourseobjective(snapshot1.child("sbjCourseOutLine").getValue(String.class));

					SubjectNameList.add(subject.getSbjTitle());
					SubjectArrayList.add(subject);
					//list.add(subject);

				//	adapterSubjectsList.notifyDataSetChanged();
				}

			}

			@Override
			public void onCancelled(@NonNull DatabaseError error) {

			}
		});

		// listStudents.add(new Student("name 1223" , "LastName" , "56563789"));
		//listStudents.add(student);
		//adapterSubjectsList.notifyDataSetChanged();

	}



	@Override
	public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
 		if (adapterView.getId() == R.id.spinnerclasstype){
			if (i != 0){
			 String type = 	classTypeList.get(i);
			 sbjType = type;

			 if (type.equals("Regular Class") || type.equals("MakeUp Class")){
				 spinnersubjectlist.setVisibility(View.VISIBLE);
				 spinnerstafflist.setVisibility(View.GONE);

			 }
			 else if (type.equals("Fun And Arts") || type.equals("Ethics")){

				 spinnerstafflist.setVisibility(View.VISIBLE);
				 spinnersubjectlist.setVisibility(View.GONE);
				 sbjId = "000";
				 sbjTitle = sbjType;

			 }
				//Toast.makeText(this, "Type"+type, Toast.LENGTH_SHORT).show();
  			}
		}
		 else if (adapterView.getId() == R.id.spinnerstafflist){

		    if (staffArrayList.size() > 0 && i != 0){
			    // String stfname = staffArrayList.get(0).getStaffName();
			    String InchargeName = staffArrayList.get((i-1)).getStaffName();
			    String InchargeId = staffArrayList.get((i-1)).getStaffId();
				sbjinstrId = InchargeId+"";
				sbjIntructor = InchargeName+"";
			   // Toast.makeText(AddNewTimePeriod.this, "Clicked"+i+InchargeId, Toast.LENGTH_SHORT).show();

		    }
	    }
		 else if (adapterView.getId() == R.id.spinnersubjectlist){

		    if (staffArrayList.size() > 0 && i != 0){
			    // String stfname = staffArrayList.get(0).getStaffName();
			    String InchargeName = SubjectArrayList.get((i-1)).getSbjTitle();
			    String InchargeId = SubjectArrayList.get((i-1)).getSbjId();
			  //  Toast.makeText(AddNewTimePeriod.this, "Clicked"+i+InchargeName, Toast.LENGTH_SHORT).show();

				sbjIntructor = SubjectArrayList.get((i-1)).getSbjInsName();
				sbjTitle = SubjectArrayList.get((i-1)).getSbjTitle();
				sbjinstrId = SubjectArrayList.get((i-1)).getSbjInsId();
				sbjId = SubjectArrayList.get((i-1)).getSbjId();


		    }
	    }
	}

	@Override
	public void onNothingSelected(AdapterView<?> adapterView) {

	}

	public void saveNewTimeTable(View view) {


		if (strttime.equals("") || endtime.equals("") || sbjId.equals("") || sbjType.equals("")){
			Toast.makeText(this, "Please fill all the Information Carefully", Toast.LENGTH_SHORT).show();
		}
		else {
			Period period = new Period();

			period.setLectureTimeStart(strttime+"");
			period.setLectureTimeEnd(endtime+"");
			period.setLectureInstructureId(sbjinstrId);
			period.setLectureInstructureName(sbjIntructor+"");
			period.setLectureType(sbjType+"");
			period.setLectureSubject(sbjTitle+"");
			period.setLectureSubjectID(sbjId+"");

			databaseReferenceperiod.child(sbjTitle).setValue(period);

			Toast.makeText(this, "Success MSG", Toast.LENGTH_SHORT).show();

			finish();
		}



	}
}