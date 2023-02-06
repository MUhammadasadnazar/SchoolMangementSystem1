package com.example.schoolmangementsystem1.LeaveRequest;

import java.util.Calendar;
import java.util.UUID;

import com.example.schoolmangementsystem1.MainActivity;
import com.example.schoolmangementsystem1.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class NewLeaveRequest extends AppCompatActivity {
	DatePicker datePicker1,datePicker2;
	String date1 , date2 , remarks , submittedby , reason = "";
	EditText edtsubmittedby , edtreqremarks , edtreqreason ;
	String stduid , stdclassid = "";
	SharedPreferences sharedPreferences;

	DatabaseReference reference;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_leave_request);
		datePicker1 = findViewById(R.id._datepicker1);
		datePicker2 = findViewById(R.id._datepicker2);
		edtreqreason = findViewById(R.id._edtreqreason);
		edtreqremarks = findViewById(R.id._edtreqremarks);
		edtsubmittedby = findViewById(R.id._edtreqsubmitby);
		sharedPreferences = getSharedPreferences("my_pref" , MODE_PRIVATE);

		stduid = getIntent().getStringExtra("uid");
		stdclassid = sharedPreferences.getString("uid" , "");

		reference = FirebaseDatabase.getInstance().getReference().child("Classes").child(stdclassid);

	}

	public void SubmitNewRequest(View view){
		String timestamp = Calendar.getInstance().getTime().toString();

		String uuid = UUID.randomUUID()+"";

		LeaveReq leaveReq = new LeaveReq();
		leaveReq.date1 = date1;
		leaveReq.date2 = date2;
		leaveReq.ReqBy = edtsubmittedby.getText().toString()+"";
		leaveReq.ReqReason = edtreqreason.getText().toString()+"";
		leaveReq.ReqRemarks = edtreqremarks.getText().toString()+"";
		leaveReq.ReqStatus = "waiting";
		leaveReq.Timespan = timestamp;
		leaveReq.stdUid = stduid;
		leaveReq.reqid = uuid;

		reference.child("Leave").child(uuid).setValue(leaveReq);

		Toast.makeText(this, "Requested Submitted Successfully...", Toast.LENGTH_SHORT).show();

		Intent intent = new Intent(NewLeaveRequest.this , MainActivity.class);
		startActivity(intent);
		finish();

	}

	public void ShowDatePicker(View view){

		//DatePickerDialog datePickerDialog;

	//	if (view.getId() == R.id._datepicker1){

			DatePickerDialog datePickerDialog =
					new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
						@Override
						public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
							String date ;
							int ri1 = i1+1;
							if (ri1<10){
								date =  i+"/0"+ri1+"/"+i2+" 00:00:00";
							}
							else {
								date = i + "/" + ri1 + "/" + i2 + " 00:00:00";
							}

							if (view.getId() == R.id._datepicker1){

								datePicker1.updateDate(i , ri1 , i2);
								date1 = date;

							}
							else if(view.getId() == R.id._datepicker2){
								datePicker2.updateDate(i , ri1 , i2);
								date2 = date;

							}
							//datePicker.updateDate(i , ri1 , i2);
							//tvpickdateandtime.setText(date);
						//	prefered_Date = date ;
							//Toast.makeText(NewLeaveRequest.this, "Date : "+date, Toast.LENGTH_SHORT).show();

							TimePickerDialog timePickerDialog = new TimePickerDialog(NewLeaveRequest.this, new TimePickerDialog.OnTimeSetListener() {
								@RequiresApi(api = Build.VERSION_CODES.O)
								@Override
								public void onTimeSet(TimePicker timePicker, int i, int i1) {
									String time = i+":"+i1;
								//	tvpickdateandtime.setText(prefered_Date +" \n "+time);
								//	prefered_time = time ;

								}
							},Calendar.HOUR_OF_DAY,Calendar.MINUTE,true);
							//timePickerDialog.show();

						}
					},
							Calendar.getInstance().get(Calendar.YEAR),
							Calendar.getInstance().get(Calendar.MONTH),
							Calendar.getInstance().get(Calendar.DATE));

			datePickerDialog.show();
		//}

 	}
}