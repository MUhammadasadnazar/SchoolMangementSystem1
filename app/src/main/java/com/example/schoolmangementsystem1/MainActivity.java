package com.example.schoolmangementsystem1;

import android.content.SharedPreferences;
import android.util.Log;
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
import com.example.schoolmangementsystem1.List.StudentsListActivity;
import com.example.schoolmangementsystem1.List.SubjectListActivity;
import com.example.schoolmangementsystem1.TimeTable.DaysListActivity;
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

    DatabaseReference databaseReference , databaseReferenceStaff ;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCreateNewStaff = findViewById(R.id._btnCreateNewStaff);
        btnAddNewCLass = findViewById(R.id._btnAddNewClass);

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

           String stdid = sharedPreferences.getString("uid2" , "");
           getStudentClassID(stdid);


        }




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
                    btnAddNewCLass.setVisibility(View.VISIBLE);
                    btnCreateNewStaff.setVisibility(View.VISIBLE);

                }
                else {
                    btnAddNewCLass.setVisibility(View.GONE);
                    btnCreateNewStaff.setVisibility(View.GONE);
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

        Intent intent = new Intent(MainActivity.this , LogInActivity.class);
        startActivity(intent);
    }

    public void AddNewStudent(View view) {
       /* Intent intent = new Intent(MainActivity.this , StudentsListActivity.class);
        startActivity(intent);*/

        Intent intent = new Intent(MainActivity.this , StudentsListActivity.class);
        startActivity(intent);
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
}