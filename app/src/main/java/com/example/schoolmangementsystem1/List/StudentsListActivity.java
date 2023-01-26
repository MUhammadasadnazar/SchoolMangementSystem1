package com.example.schoolmangementsystem1.List;

import java.util.ArrayList;

import com.example.schoolmangementsystem1.Adapter.AdapterStudentList;
import com.example.schoolmangementsystem1.AddNew.AddNewStudent;
import com.example.schoolmangementsystem1.Model.Student;
import com.example.schoolmangementsystem1.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class StudentsListActivity extends AppCompatActivity {

    RecyclerView recyclerViewstdlist;
    AdapterStudentList adapterStudentList;
    ArrayList<Student> listStudents;
    DatabaseReference databasereference ;
    FirebaseUser firebaseUser;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_list);
        sharedPreferences = getSharedPreferences("my_pref" , MODE_PRIVATE);
       // firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        //String uid = firebaseUser.getUid();
        String uid = "";
        uid = sharedPreferences.getString("uid" , "");

        //String uid = "vHKiHvEXm9fFYVamj27NgQEodxR2";
       // String uid = "zhiRCFXtJHgKa5emMTbDpUdpNKg2";
        databasereference = FirebaseDatabase.getInstance().getReference("Classes")
                .child(uid).child("Students");


        try{
        recyclerViewstdlist = (RecyclerView) findViewById(R.id._recyclerviewstdlist);
        listStudents = new ArrayList<>();

           // listStudents.add(new Student("name 1223" , "LastName" , "56563789"));

            adapterStudentList = new AdapterStudentList(StudentsListActivity.this , listStudents);
        LinearLayoutManager layoutmanager = new LinearLayoutManager(this);
        recyclerViewstdlist.setLayoutManager( layoutmanager);
        recyclerViewstdlist.setAdapter(adapterStudentList);
        //adapterStudentList.notifyDataSetChanged();
            LoadStudentList();


        }
        catch (Exception exc){
            Log.d("Errorrrr" , ""+exc.getMessage());
            Toast.makeText(this, "Exc : "+exc.getMessage(), Toast.LENGTH_SHORT).show();
        }



    }

    public void LoadStudentList(){
       /* student.setStdName("Name111");
        student.setStdLastName("LastName");
        student.setStdRollNo("4556768");*/

        databasereference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    Student student = new Student();

                    student.setStdName(snapshot1.child("stdName").getValue(String.class));
                     student.setStdLastName(snapshot1.child("stdLastName").getValue(String.class));
                     student.setStdRollNo(snapshot1.child("stdRollNo").getValue(String.class));

                     listStudents.add(student);

                     adapterStudentList.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

       // listStudents.add(new Student("name 1223" , "LastName" , "56563789"));
        //listStudents.add(student);
        adapterStudentList.notifyDataSetChanged();
    }

    public void AddNewStuentt(View view) {
        Intent intent = new Intent(StudentsListActivity.this , AddNewStudent.class);
        startActivity(intent);
    }
}