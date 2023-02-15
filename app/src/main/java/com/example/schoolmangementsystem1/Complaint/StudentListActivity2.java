package com.example.schoolmangementsystem1.Complaint;

import java.util.ArrayList;

import com.example.schoolmangementsystem1.Adapter.AdapterStudentList;
import com.example.schoolmangementsystem1.AddNew.AddNewStudent;
import com.example.schoolmangementsystem1.Interface.onClickRVItem;
import com.example.schoolmangementsystem1.List.StudentsListActivity;
import com.example.schoolmangementsystem1.Model.Student;
import com.example.schoolmangementsystem1.R;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class StudentListActivity2 extends AppCompatActivity {

	RecyclerView recyclerViewstdlist;
	AdapterStudentList adapterStudentList;
	ArrayList<Student> listStudents;
	DatabaseReference databasereference ;
	FirebaseUser firebaseUser;
	SharedPreferences sharedPreferences;
	String isstaff = "";
	CardView cardviewaddnewstdent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_student_list2);

		sharedPreferences = getSharedPreferences("my_pref" , MODE_PRIVATE);
		// firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
		//String uid = firebaseUser.getUid();
		String uid = "";
		uid = sharedPreferences.getString("uid" , "");
		isstaff = sharedPreferences.getString("isstaff" , "");

		databasereference = FirebaseDatabase.getInstance().getReference("Classes")
				.child(uid).child("Students");


		try{
			recyclerViewstdlist = (RecyclerView) findViewById(R.id._recyclerviewstdlist);
			listStudents = new ArrayList<>();

			// listStudents.add(new Student("name 1223" , "LastName" , "56563789"));

			adapterStudentList = new AdapterStudentList(StudentListActivity2.this, listStudents, new onClickRVItem() {

				@Override
				public void onClickRvItem(View view, int position) {

					if (isstaff.equals("yes")){
						Intent intent = new Intent(StudentListActivity2.this , ComplaintListActivity.class);
						intent.putExtra("stdid" , listStudents.get(position).getStdID());
						startActivity(intent);

					}
				}
			});
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
				listStudents.clear();
				for (DataSnapshot snapshot1 : snapshot.getChildren()){
					Student student = new Student();

					student.setStdName(snapshot1.child("stdName").getValue(String.class));
					student.setStdLastName(snapshot1.child("stdLastName").getValue(String.class));
					student.setStdRollNo(snapshot1.child("stdRollNo").getValue(String.class));
					// student.setstd(snapshot1.child("stdRollNo").getValue(String.class));
					student.setStdID(snapshot1.child("stdID").getValue(String.class));

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

}