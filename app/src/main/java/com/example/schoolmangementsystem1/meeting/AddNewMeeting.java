package com.example.schoolmangementsystem1.meeting;

import java.util.Calendar;
import java.util.UUID;

import com.example.MyServerDatetime;
import com.example.schoolmangementsystem1.LeaveRequest.NewLeaveRequest;
import com.example.schoolmangementsystem1.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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

public class AddNewMeeting extends AppCompatActivity {
	DatePicker datePicker1,datePicker2;
	TimePicker timePicker;
	String date1 = "";
	String time = "";
	EditText edtmeetingpoints;
	String classid="";
	SharedPreferences sharedPreferences;
	MyServerDatetime myServerDatetime;
	String datestamp;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_new_meeting);
		datePicker1 = findViewById(R.id._datepicker1);
		timePicker = findViewById(R.id._tpickerfrom);
		edtmeetingpoints = findViewById(R.id._edtmeetingpoints);

		sharedPreferences = getSharedPreferences(getString(R.string.shared_pref) , MODE_PRIVATE);
		classid = sharedPreferences.getString("uid" ,"");
		myServerDatetime = new MyServerDatetime();
		myServerDatetime.getServerDateTime(this);

		datestamp  = sharedPreferences.getString("tmee","");



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

						//datePicker.updateDate(i , ri1 , i2);
						//tvpickdateandtime.setText(date);
						//	prefered_Date = date ;
						//Toast.makeText(NewLeaveRequest.this, "Date : "+date, Toast.LENGTH_SHORT).show();



					}
				},
						Calendar.getInstance().get(Calendar.YEAR),
						Calendar.getInstance().get(Calendar.MONTH),
						Calendar.getInstance().get(Calendar.DATE));

		datePickerDialog.show();
		//}

	}

	public void ShowTimePicker(View view){
		TimePickerDialog timePickerDialog = new TimePickerDialog(AddNewMeeting.this, new TimePickerDialog.OnTimeSetListener() {
			@RequiresApi(api = Build.VERSION_CODES.O)
			@Override
			public void onTimeSet(TimePicker timePicker1, int i, int i1) {
				time = i+":"+i1;
				String mnts = "00";

				timePicker.setHour(i);
				timePicker.setMinute(i1);

				if (i1 < 10){
					mnts = "0"+i1;
				}
				else if (i1 > 9){
					mnts = i1+"";
				}
				if (i < 10){
					time = "0"+i+":"+mnts;

				}

				//	tvpickdateandtime.setText(prefered_Date +" \n "+time);
				//	prefered_time = time ;

			}
		}, Calendar.HOUR_OF_DAY,Calendar.MINUTE,true);
		timePickerDialog.show();
	}

	public void saveClicked(View view) {
		String uuid = UUID.randomUUID()+"";

		if (date1.equals("")|| time.equals("")||edtmeetingpoints.getText().toString().equals("")){
			Toast.makeText(this, "Please add all fields....", Toast.LENGTH_SHORT).show();
		}
		else {
			Meeting meeting = new Meeting();
			meeting.setMeetingAgenda(edtmeetingpoints.getText().toString());
			meeting.setMeetingdate(date1);
			meeting.setMeetingTime(time);
			meeting.setMeetingStatus("New");
			meeting.setMeetingtimeStamp(datestamp);
			meeting.setMeetingid(uuid);

			DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
					.child("Classes").child(classid).child("meetings");

			reference.child(uuid).setValue(meeting);

			Toast.makeText(this, "Meeting Request Sent Successfully....", Toast.LENGTH_SHORT).show();

			finish();
		}

		//Toast.makeText(this, "Date : "+date1, Toast.LENGTH_SHORT).show();
	//	Toast.makeText(this, "Time : "+time, Toast.LENGTH_SHORT).show();
	}
}