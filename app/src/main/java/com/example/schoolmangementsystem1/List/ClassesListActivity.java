package com.example.schoolmangementsystem1.List;

import java.util.ArrayList;

import com.example.schoolmangementsystem1.Adapter.AdapterStudentList;
import com.example.schoolmangementsystem1.Adapter.AdpaterStaffLIst;
import com.example.schoolmangementsystem1.AddNew.AddNewClass;
import com.example.schoolmangementsystem1.Interface.onClickRVItem;
import com.example.schoolmangementsystem1.Model.Class1;
import com.example.schoolmangementsystem1.Model.Staff;
import com.example.schoolmangementsystem1.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.content.Intent;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ClassesListActivity extends AppCompatActivity {
	DatabaseReference reference;
	RecyclerView recyclerView;
	AdpaterStaffLIst adapterstafflist;
	ArrayList<Staff> list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_classes_list);

		recyclerView = findViewById(R.id._recyclerviewclasses);
		list = new ArrayList<Staff>();

		reference = FirebaseDatabase.getInstance().getReference().child("Classes");

		adapterstafflist = new AdpaterStaffLIst(this, list, new onClickRVItem() {

			@Override
			public void onClickRvItem(View view, int position) {

			}
		} , true);


		LinearLayoutManager layoutmanager = new LinearLayoutManager(this);
		recyclerView.setLayoutManager( layoutmanager);
		recyclerView.setAdapter(adapterstafflist);

		LoadClassList();
	}

	private void LoadClassList() {


		reference.addValueEventListener(new ValueEventListener() {

			@Override
			public void onDataChange(@NonNull DataSnapshot snapshot) {

				list.clear();

				for (DataSnapshot snapshot1 : snapshot.getChildren()){


					for (DataSnapshot snapshot2 : snapshot1.getChildren()){

						if (snapshot2.child("classTitle").getValue(String.class) != null){
							Staff class1 = new Staff();


							class1.setStaffName(snapshot2.child("classTitle").getValue(String.class));
							class1.setEducation(snapshot2.child("inchargeName").getValue(String.class));
							//class1.set(snapshot1.child("classTitle").getValue(String.class));
							list.add(class1);
							adapterstafflist.notifyDataSetChanged();
						}



					}



				}


			}


			@Override
			public void onCancelled(@NonNull DatabaseError error) {

			}


		});


	}

	public void GoToAddNewClass(View view) {

		Intent intent = new Intent(ClassesListActivity.this , AddNewClass.class);
		startActivity(intent);
	}
}