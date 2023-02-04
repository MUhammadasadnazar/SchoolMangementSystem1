package com.example.schoolmangementsystem1.AddNew;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.schoolmangementsystem1.MainActivity;
import com.example.schoolmangementsystem1.Model.Staff;
import com.example.schoolmangementsystem1.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateNewStaff extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
   // FirebaseDatabase databaseReference;
    EditText edtname , edtaddress , edteducation , edtcontactno , edtemailaddress , edtpass;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_staff);

        databaseReference = FirebaseDatabase.getInstance().getReference("Staff");

        edtname = findViewById(R.id._edtstaffname);
        edtaddress = findViewById(R.id._edtAddress);
        edteducation = findViewById(R.id._edteducation);
        edtcontactno = findViewById(R.id._edtcontactNo);
        edtemailaddress = findViewById(R.id._edtemailaddress);
        edtpass = findViewById(R.id._edtpass);

        firebaseAuth = FirebaseAuth.getInstance();


      //  String uuid = UUID.randomUUID()+"";
    }

    public  void CreateNEwStaff(View view){

       // Toast.makeText(this, "ok:"+validtaion(), Toast.LENGTH_SHORT).show();
       // if (edtpass.getText().toString().trim().equals("")){
        if (!validtaion()){
            Toast.makeText(this, "Make Sure You have Entered All Credentials...", Toast.LENGTH_SHORT).show();
        }else
        {

            firebaseAuth.createUserWithEmailAndPassword
                            (edtemailaddress.getText().toString(),"123456")
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(CreateNewStaff.this, "User Created", Toast.LENGTH_SHORT).show();
//                    userlist.add(new User(firebaseAuth.getCurrentUser().getUid(),edtname.getText().toString(),edtpass.getText().toString()));


                             Staff staff = new Staff();
                             staff.setStaffId(firebaseAuth.getCurrentUser().getUid()+"");
                             staff.setStaffName(edtname.getText().toString()+"");
                             staff.setAddress(edtaddress.getText().toString()+"");
                             staff.setEducation(edteducation.getText().toString()+"");
                             staff.setContactNo(edtcontactno.getText().toString()+"");
                             staff.setEmailAddress(edtemailaddress.getText().toString()+"");
                             staff.setAdmin(false);
                             staff.setPassword("123456");


                                databaseReference.child(firebaseAuth.getCurrentUser().getUid())
                                        .setValue(staff);

                                Toast.makeText(CreateNewStaff.this, "Operation Successfull...", Toast.LENGTH_SHORT).show();
                                 Intent intent= new Intent(CreateNewStaff.this, MainActivity.class);
                                finish();
                                startActivity(intent);
                            }
                            else
                            if (!task.isSuccessful()){
                                Toast.makeText(CreateNewStaff.this, ""+task.getException(), Toast.LENGTH_SHORT).show();

                            }
                        }
                    });

        }

    }

    public boolean validtaion(){
        boolean isok = true;

        if (edtname.getText().toString().trim().equals("")||
                edtaddress.getText().toString().trim().equals("")||
        edtcontactno.getText().toString().trim().equals("")||
        edteducation.getText().toString().trim().equals("")||
                edtemailaddress.getText().toString().trim().equals("")){


            isok = false;
        }
        return isok;
    }
}