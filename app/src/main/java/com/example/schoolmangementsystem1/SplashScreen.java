package com.example.schoolmangementsystem1;

import android.content.Intent;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);

		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {

				Intent intent = new Intent(SplashScreen.this , LogInActivity.class);
				//String iddd = sharedPreferences.getString("uid" , "");

				// if (!iddd.equals("")){
				//   intent.putExtra("uid" , iddd+"");

				// }
				//  intent.putExtra("uid" , mAuth.getCurrentUser().getUid()+"");
				// editor.putString("uid" , mAuth.getCurrentUser().getUid()+"");
				// editor.commit();
				finish();
				startActivity(intent);
				//showInterstitial();


			}
		}, 4000);
	}
}