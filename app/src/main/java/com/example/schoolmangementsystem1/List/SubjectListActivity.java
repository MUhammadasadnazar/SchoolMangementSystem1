package com.example.schoolmangementsystem1.List;


import java.util.ArrayList;

import com.example.schoolmangementsystem1.Adapter.AdapterSubjectsList;
import com.example.schoolmangementsystem1.AddNew.AddNewSubject;
import com.example.schoolmangementsystem1.Interface.onClickRVItem;
import com.example.schoolmangementsystem1.Model.Student;
import com.example.schoolmangementsystem1.Model.Subject;
import com.example.schoolmangementsystem1.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SubjectListActivity extends AppCompatActivity {
	AdapterSubjectsList adapterSubjectsList;
	ArrayList<Subject> list;
	RecyclerView recyclerView;
	DatabaseReference databasereference ;
	String uid , isstaff = "";
	SharedPreferences sharedPreferences;
	CardView cardviewAddSbj;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_subject_list);
		cardviewAddSbj = findViewById(R.id._cardviewAddSbj);

		recyclerView = findViewById(R.id._recyclerviewsubjectlist);
		sharedPreferences = getSharedPreferences("my_pref" , MODE_PRIVATE);

		uid = sharedPreferences.getString("uid" , "");
		isstaff = sharedPreferences.getString("isstaff" , "");

		if (isstaff.equals("no")){
			cardviewAddSbj.setVisibility(View.GONE);
		}


		databasereference = FirebaseDatabase.getInstance().getReference("Classes")
				.child(uid).child("Subjects");

		list = new ArrayList<Subject>();
		adapterSubjectsList = new AdapterSubjectsList(this, list, new onClickRVItem() {

			@Override
			public void onClickRvItem(View view, int position) {
				if (isstaff.equals("yes")){
					String sbjidd = list.get(position).getSbjId();
					databasereference.child(sbjidd).removeValue();
					adapterSubjectsList.notifyDataSetChanged();

				}
			}
		});

		LinearLayoutManager layoutmanager = new LinearLayoutManager(this);
		recyclerView.setLayoutManager( layoutmanager);
		recyclerView.setAdapter(adapterSubjectsList);

		GetSubjectList();

	}

	public void GetSubjectList(){

		databasereference.addValueEventListener(new ValueEventListener() {

			@Override
			public void onDataChange(@NonNull DataSnapshot snapshot) {
				list.clear();
				for (DataSnapshot snapshot1 : snapshot.getChildren()){
					Subject subject = new Subject();

					subject.setSbjTitle(snapshot1.child("sbjTitle").getValue(String.class));
					subject.setSbjInsName(snapshot1.child("sbjInsName").getValue(String.class));
					subject.setSbjcourseobjective(snapshot1.child("sbjCourseOutLine").getValue(String.class));

					subject.setSbjId(snapshot1.child("sbjId").getValue(String.class));
					list.add(subject);

					adapterSubjectsList.notifyDataSetChanged();
				}

			}

			@Override
			public void onCancelled(@NonNull DatabaseError error) {

			}
		});

		// listStudents.add(new Student("name 1223" , "LastName" , "56563789"));
		//listStudents.add(student);
		adapterSubjectsList.notifyDataSetChanged();

	}

	public void AddNewSubject(View view) {
		Intent intent = new Intent(SubjectListActivity.this , AddNewSubject.class);
		startActivity(intent);
	}
}