package com.example.schoolmangementsystem1.AddNew;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.schoolmangementsystem1.MainActivity;
import com.example.schoolmangementsystem1.Model.Class1;
import com.example.schoolmangementsystem1.Model.Staff;
import com.example.schoolmangementsystem1.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.UUID;

public class AddNewClass extends AppCompatActivity  implements AdapterView.OnItemSelectedListener {
    Spinner spinner1;
    EditText edtClassTitle , edtNoStudents ;
    String InchargeName = "" , InchargeId = "";


     ArrayList<String> StaffNameList;
    ArrayList<Staff> staffArrayList;
    DatabaseReference databaseReference , databaseReference1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_class);

        edtClassTitle = findViewById(R.id._edtclasstitle);
        edtNoStudents = findViewById(R.id._edtnostudents);

        StaffNameList = new ArrayList<String>();
        staffArrayList = new ArrayList<Staff>();

        databaseReference = FirebaseDatabase.getInstance().getReference("Staff");
        databaseReference1 = FirebaseDatabase.getInstance().getReference("Classes");
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner1.setOnItemSelectedListener(this);


        GetStaffList();

        StaffNameList.add("Select Staff Name");

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
                   // Toast.makeText(AddNewClass.this, "Name : "+StaffNameList.size(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //Toast.makeText(AddNewClass.this , "Hello" , Toast.LENGTH_LONG).show();

               // Toast.makeText(AddNewClass.this, "", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


        if (staffArrayList.size() > 0 && i != 0){
           // String stfname = staffArrayList.get(0).getStaffName();
             InchargeName = staffArrayList.get((i-1)).getStaffName();
             InchargeId = staffArrayList.get((i-1)).getStaffId();
          //  Toast.makeText(AddNewClass.this, "Clicked"+i+InchargeId, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void AddNewCLass(View view) {
          String uuid = UUID.randomUUID()+"";


        if (!validtaion()){
            Toast.makeText(this, "Make Sure You have Entered All Credentials...", Toast.LENGTH_SHORT).show();
        }else {
            Class1 class1 = new Class1();

            class1.setClassTitle(edtClassTitle.getText().toString().trim());
            class1.setInchargeName(InchargeName + "");
            class1.setInchargeId(InchargeId + "");
            if (edtNoStudents.getText().toString() != null) {
                class1.setNoofStudents(Integer.parseInt(edtNoStudents.getText().toString()));
            }
            class1.setClassID(uuid);

            databaseReference1.child(InchargeId).child("Info").setValue(class1);

            Toast.makeText(this, "Class Created Successfully...", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(AddNewClass.this , MainActivity.class);
            finish();
            startActivity(intent);

        }
    }

    public boolean validtaion(){
        boolean isok = true;

        if (edtClassTitle.getText().toString().trim().equals("")||
                edtNoStudents.getText().toString().trim().equals("")||
                InchargeName.trim().equals("")||
                InchargeId.toString().trim().equals("")){


            isok = false;
        }
        return isok;
    }

}