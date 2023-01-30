package com.example.schoolmangementsystem1.services;

import com.example.schoolmangementsystem1.LogInActivity;
import com.example.schoolmangementsystem1.MainActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AutoRunReciever extends BroadcastReceiver {

	//    BroadcastReceiver br = new AutoRunReciever();

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d("Reciverrrr" , intent.getAction());

		//        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
		//        filter.addAction(Intent.ACTION_BOOT_COMPLETED);
		//        context.registerReceiver(br, filter);

		if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())){

			Intent i = new Intent(context, LogInActivity.class);
			i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(i);
		}



	}
}
