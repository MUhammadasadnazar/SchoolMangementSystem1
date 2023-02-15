package com.example;

import static android.content.Context.MODE_PRIVATE;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.example.schoolmangementsystem1.MainActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;
import androidx.annotation.NonNull;

public class MyServerDatetime {
	SharedPreferences sharedPreferences ;
	SharedPreferences.Editor editor;

	public MyServerDatetime() {


	}

	public String getServerDateTime(Context context){
		  String[] date123456 = { "" };
		  sharedPreferences = context.getSharedPreferences("my_pref" , MODE_PRIVATE);

		  editor = sharedPreferences.edit();

		DatabaseReference offsetRef = FirebaseDatabase.getInstance().getReference(".info/serverTimeOffset");
		offsetRef.addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot snapshot) {

				//  Timestamp stmp = snapshot.getValue(TIMESTAMP);

				double offset = snapshot.getValue(Double.class);

				double milliSeconds =  new Date().getTime() + offset;
				// double /estimatedServerTimeMs = System.currentTimeMillis() + offset;

				DateFormat formatter = new SimpleDateFormat("dd/MMM/yyyy HH:mm:ss");

				// long milliSeconds= Long.parseLong(x);
				System.out.println(milliSeconds);

				Calendar calendar = Calendar.getInstance();
				calendar.setTimeInMillis((long) milliSeconds);
				date123456[0] = formatter.format(calendar.getTime());

				editor.putString("tmee" , date123456[0]);
				editor.commit();

				//Toast.makeText(context, "adte : "+ date123456[0], Toast.LENGTH_SHORT).show();
				// Toast.makeText(MainActivity.this, "adte : "+snapshot.getValue(ServerValue.TIMESTAMP), Toast.LENGTH_SHORT).show();
				// System.out.println(formatter.format(calendar.getTime()));
				//Toast.makeText(MainActivity.this, "", Toast.LENGTH_SHORT).show();
				//Date date = new Date();
			}

			@Override
			public void onCancelled(@NonNull DatabaseError error) {
				// Log.w(TAG, "Listener was cancelled");
			}
		});


		return "hellloooo"+date123456[0];
	}

}
