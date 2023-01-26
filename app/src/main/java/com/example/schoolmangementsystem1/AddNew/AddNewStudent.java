package com.example.schoolmangementsystem1.AddNew;

import android.content.SharedPreferences;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.schoolmangementsystem1.Model.Student;
import com.example.schoolmangementsystem1.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddNewStudent extends AppCompatActivity {
    EditText edtfname , edtlname , edtRollNo , edtMobileNo , edtEmailAddress ,
            edtHomeAddress , edtGaurdianName , edtgaurdianPhoennO;

    FirebaseAuth firebaseAuth , firebaseAuthStudent;
    DatabaseReference databaseReference , databaseReferencestdClassId;
    FirebaseUser user;
    SharedPreferences sharedPreferences;
    String uid = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_student);
        edtfname = findViewById(R.id._stdfname);
        edtlname = findViewById(R.id._stdlname);
        edtRollNo = findViewById(R.id._stdRollNo);
        edtMobileNo = findViewById(R.id._stdmobileNo);
        edtEmailAddress = findViewById(R.id._stdEmailAddress);

        edtHomeAddress = findViewById(R.id._stdHomeAddress);
        edtGaurdianName = findViewById(R.id._stdGaurdianName);
        edtgaurdianPhoennO = findViewById(R.id._stdGaurdianPhoneNo);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuthStudent = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        sharedPreferences = getSharedPreferences("my_pref" , MODE_PRIVATE);
        //clAss ID And Incharge ID
        uid = sharedPreferences.getString("uid" , "");

        databaseReference = FirebaseDatabase.getInstance().getReference("Classes")
                .child(uid).child("Students");

        databaseReferencestdClassId = FirebaseDatabase.getInstance().getReference("Stdclassid");
                //.child(uid).child("Students");


        //Toast.makeText(this, "Id : "+user.getUid(), Toast.LENGTH_SHORT).show();
    }

    public void SaveNewStudent(View view) {


        Student student = new Student();
        student.setStdName(edtfname.getText().toString());
        student.setStdLastName(edtlname.getText().toString());
        student.setStdRollNo(edtRollNo.getText().toString());
        student.setStdEmailAddres(edtEmailAddress.getText().toString());
        student.setStdHomeAddress(edtHomeAddress.getText().toString());
        student.setStdMobileNo(edtMobileNo.getText().toString());
        student.setGaurdianName(edtGaurdianName.getText().toString());
        student.setGaurdianPhoneNo(edtgaurdianPhoennO.getText().toString());
        student.setStdPassword("123456");
        student.setStdClassId(uid);

        firebaseAuthStudent.createUserWithEmailAndPassword(edtEmailAddress.getText().toString() , "123456")
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){
                            student.setStdID(firebaseAuthStudent.getCurrentUser().getUid());

                            databaseReference.child(firebaseAuthStudent.getCurrentUser().getUid())
                                    .setValue(student);

                            databaseReferencestdClassId.child(firebaseAuthStudent.getCurrentUser().getUid())
                                    .child("id").setValue(uid);

                        }
                        else {
                            Log.d("StdError" , "er"+task.getException());
                            Toast.makeText(AddNewStudent.this, "Exception"+task.getException(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }
}