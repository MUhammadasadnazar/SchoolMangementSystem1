package com.example.schoolmangementsystem1;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import kotlin.collections.FloatIterator;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.schoolmangementsystem1.Interface.onCheckChanged;
import com.example.schoolmangementsystem1.services.AutoRunReciever;
import com.example.schoolmangementsystem1.services.MyServiceAdmin;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LogInActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    private FirebaseAuth mAuth;
     EditText edtuname , edtpass;
     SharedPreferences.Editor editor;
     RadioGroup radioGroup;
     RadioButton radiobuttonSrudent , radioButtonStaff;
     String isstaff = "";
     DatabaseReference databaseReferencestafflist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        radioGroup = findViewById(R.id.radiogriup1);

        radiobuttonSrudent = findViewById(R.id._radiobuttonstudent);
        radioButtonStaff = findViewById(R.id._radiobutttonstaff);

        databaseReferencestafflist = FirebaseDatabase.getInstance().getReference().child("Staff");

        radioGroup.setOnCheckedChangeListener(this);
        SharedPreferences sharedPreferences = getSharedPreferences("my_pref" , MODE_PRIVATE);

        editor = sharedPreferences.edit();

        edtuname = findViewById(R.id.edtuname);
        edtpass = findViewById(R.id.edtupassword);

        MyServiceAdmin mYourService = new MyServiceAdmin();
        Intent strtservice = new Intent(LogInActivity.this, mYourService.getClass());

        if (!isMyServiceRunning(mYourService.getClass())) {
            startService(strtservice);
        }

        BroadcastReceiver br = new AutoRunReciever();

        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        filter.addAction(Intent.ACTION_BOOT_COMPLETED);
        registerReceiver(br, filter);

        //   Toast.makeText(LogInActivity.this, "hello", Toast.LENGTH_SHORT).show();



        // FirebaseApp.initializeApp(this);

        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser()!= null){
            Intent intent = new Intent(LogInActivity.this , MainActivity.class);
            intent.putExtra("uid" , mAuth.getCurrentUser().getUid()+"");
           // editor.putString("uid" , mAuth.getCurrentUser().getUid()+"");
           // editor.commit();
            startActivity(intent);
        }
    }

    public boolean CheckIsStaff(String id,FirebaseUser user){
        final Boolean[] isstaff = { false };
        Toast.makeText(this, "id : "+id, Toast.LENGTH_SHORT).show();
        databaseReferencestafflist.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    Toast.makeText(LogInActivity.this, "key : "+snapshot1.getKey(), Toast.LENGTH_SHORT).show();


                    if (snapshot1.getKey().equals(id)){
                        isstaff[0] = true;
                       // break;
                        ProceddToLogIn(true , user);
                        break;
                     //   Toast.makeText(LogInActivity.this, "hello", Toast.LENGTH_SHORT).show();

                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

       // Toast.makeText(LogInActivity.this, "key123 : "+isstaff[0], Toast.LENGTH_SHORT).show();

        return isstaff[0];

    }

    public  void ProceddToLogIn(boolean isstaff , FirebaseUser user){

        if (isstaff){
            Intent intent = new Intent(LogInActivity.this , MainActivity.class);
            intent.putExtra("uid",user.getUid()+"");
            editor.putString("uid" , user.getUid()+"");
            editor.putString("uid2" , user.getUid()+"");
            editor.putString("isstaff" , "yes");
            editor.commit();
            startActivity(intent);
            Toast.makeText(LogInActivity.this, "User"+user.getEmail() , Toast.LENGTH_SHORT).show();


        }
        else if(!isstaff){
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(LogInActivity.this, "Error: User Not Exists", Toast.LENGTH_SHORT).show();
        }
    }
    public void SignInnnnn(View view) {


        // Toast.makeText(this, "Helllooo", Toast.LENGTH_SHORT).show();
        try{

            // mAuth.signInWithEmailAndPassword()
            mAuth.signInWithEmailAndPassword(edtuname.getText().toString().trim()
                            ,edtpass.getText().toString().trim())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("TAG", "signInWithCustomToken:success");
                                FirebaseUser user = mAuth.getCurrentUser();

                                if (isstaff.equals("yes")){

                                 //   Toast.makeText(LogInActivity.this, "in staff yes", Toast.LENGTH_SHORT).show();
                                    Boolean isstaff = CheckIsStaff(user.getUid() , user);


                                  //  Toast.makeText(LogInActivity.this, "istf"+isstaff, Toast.LENGTH_SHORT).show();
                                    //copied cut
                                }
                                else if (isstaff.equals("no")){

                                    Intent intent = new Intent(LogInActivity.this , MainActivity.class);
                                    intent.putExtra("uid",user.getUid()+"");
                                    editor.putString("uid" , user.getUid()+"");
                                    editor.putString("uid2" , user.getUid()+"");
                                    editor.putString("isstaff" , "no");
                                    editor.commit();
                                    startActivity(intent);
                                    Toast.makeText(LogInActivity.this, "User"+user.getEmail() , Toast.LENGTH_SHORT).show();

                                }



                                // updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.d("TAG", "signInWithCustomToken:failure", task.getException());
                                Toast.makeText(LogInActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                // updateUI(null);
                            }
                        }
                    });

        }
        catch (Exception exc){
            Toast.makeText(this, "Error:"+exc.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {

        if (i == radiobuttonSrudent.getId()){

            isstaff = "no";
        }
        else if(i == radioButtonStaff.getId()){
            isstaff = "yes";
        }


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
}