package com.example.schoolmangementsystem1;

import android.app.ActivityManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import kotlin.text.UStringsKt;

import com.example.schoolmangementsystem1.AddNew.AddNewClass;
import com.example.schoolmangementsystem1.AddNew.CreateNewStaff;
import com.example.schoolmangementsystem1.Attendance.MonthListActivity;
import com.example.schoolmangementsystem1.LeaveRequest.StdRequestList;
import com.example.schoolmangementsystem1.List.StudentsListActivity;
import com.example.schoolmangementsystem1.List.SubjectListActivity;
import com.example.schoolmangementsystem1.TimeTable.DaysListActivity;
import com.example.schoolmangementsystem1.services.MyServiceAdmin;
import com.example.schoolmangementsystem1.services.Restarter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

public class MainActivity extends AppCompatActivity {
    String uid = "";
    String isstaff = "";
    FirebaseStorage storageReference;
    Button btnCreateNewStaff , btnAddNewCLass;
    LinearLayout linearlayoutstdentsubject;

    DatabaseReference databaseReference , databaseReferenceStaff ;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String stdid = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCreateNewStaff = findViewById(R.id._btnCreateNewStaff);
        btnAddNewCLass = findViewById(R.id._btnAddNewClass);
        linearlayoutstdentsubject = ( LinearLayout) findViewById(R.id._linearlayoutstdentsubject);

        //uid as class id and uid2 as USer ID

        sharedPreferences = getSharedPreferences("my_pref" , MODE_PRIVATE);
        editor = sharedPreferences.edit();
       //clAss ID And Incharge ID
        isstaff = sharedPreferences.getString("isstaff" , "");

       // Intent intent = getIntent();
       // uid = intent.getStringExtra("uid");
       // uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        if (isstaff.equals("yes")){

            uid = sharedPreferences.getString("uid" , "");

            //  databaseReference = FirebaseDatabase.getInstance().getReference().child("Classes").child(uid);
            databaseReferenceStaff = FirebaseDatabase.getInstance().getReference().child("Staff").child(uid);

            LoadClass();



        }
        else if(isstaff.equals("no")){
            stdid = sharedPreferences.getString("uid2" , "");
           getStudentClassID(stdid);


        }

        MyServiceAdmin mYourService = new MyServiceAdmin();
        Intent strtservice = new Intent(MainActivity.this, mYourService.getClass());

        if (!isMyServiceRunning(mYourService.getClass())) {
            startService(strtservice);
        }
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();

        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction("restartservice");
        broadcastIntent.setClass(this, Restarter.class);
        this.sendBroadcast(broadcastIntent);
        //  super.onDestroy();
    }
    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.i ("Service status", "Running");
                return true;
            }
        }
        Log.i ("Service status", "Not running");
        return false;
    }

    public void getStudentClassID(String stdid){

       // String uiddd = "";
        FirebaseDatabase.getInstance().getReference().child("Stdclassid").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    if (dataSnapshot.getKey().equals(stdid)){
                      uid  = dataSnapshot.child("id").getValue(String.class);
                      editor.putString("uid" , uid);
                      editor.commit();

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void LoadClass(){

        databaseReferenceStaff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Boolean isAdmin = snapshot.child("admin").getValue(Boolean.class);
                if (isAdmin){
                    linearlayoutstdentsubject.setVisibility(View.VISIBLE);
                   // btnAddNewCLass.setVisibility(View.VISIBLE);
                   // btnCreateNewStaff.setVisibility(View.VISIBLE);

                }
                else {
                    linearlayoutstdentsubject.setVisibility(View.GONE);

                    //  btnAddNewCLass.setVisibility(View.GONE);
                    // btnCreateNewStaff.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    public void CreateNewStaff(View view) {
        Intent intent = new Intent(MainActivity.this , CreateNewStaff.class);
        startActivity(intent);
    }

    public void AddNewClass(View view) {
        Intent intent = new Intent(MainActivity.this , AddNewClass.class);
        startActivity(intent);
    }

    public void LogOut(View view) {
        FirebaseAuth.getInstance().signOut();
        sharedPreferences.edit().clear();
        sharedPreferences.edit().apply();

        Intent intent = new Intent(MainActivity.this , LogInActivity.class);
        finish();
        startActivity(intent);
    }

    public void AddNewStudent(View view) {
       /* Intent intent = new Intent(MainActivity.this , StudentsListActivity.class);
        startActivity(intent);*/

        if (isstaff.equals("yes")){

            Intent intent = new Intent(MainActivity.this , StudentsListActivity.class);
            startActivity(intent);
        }
        else {
            Intent intent = new Intent(MainActivity.this , StudentProfileActivity.class);
            startActivity(intent);
        }


    }

    public void GoToSubjectList(View view) {
        Intent intent = new Intent(MainActivity.this , SubjectListActivity.class);
        startActivity(intent);
    }

    public void GoToTimeTable(View view) {
        Intent intent = new Intent(MainActivity.this , DaysListActivity.class);
        startActivity(intent);
    }

    public void GoToAttendance(View view) {
        Intent intent = new Intent(MainActivity.this , MonthListActivity.class);
        startActivity(intent);
    }

    public void goToLeaveRequest(View view) {

       // if(isstaff.equals("no")){
            Intent intent = new Intent(MainActivity.this , StdRequestList.class);
            intent.putExtra("uid" , stdid);
            startActivity(intent);
      //  }
    }
}