package com.example.schoolmangementsystem1.services;

import java.sql.Connection;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import com.example.schoolmangementsystem1.MainActivity;
import com.example.schoolmangementsystem1.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

public class MyServiceAdmin extends Service {

	String ticket_number_query = "select isnull(max(ticketno),0)  as NewTicketNo from Maintenance";
	public int counter=0;

	Connection connection;
	SharedPreferences sharedPreferences;
	float spticket = 0;
	int size = 0 ;
	String id ;
	SharedPreferences.Editor editor;
	DatabaseReference databaseReferenceAllMessages;



	@Override
	public void onCreate() {
		super.onCreate();
		//myConnectionClass = new MyConnectionClass();
		// connection = myConnectionClass.connetionClass();
		sharedPreferences = getSharedPreferences(getString(R.string.shared_pref) , MODE_PRIVATE);
		editor = sharedPreferences.edit();
		// list = new ArrayList<RequestModelClass>();


		if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O){

			startMyOwnForeground();

		}
		else
			startForeground(1, new Notification());
	}

	@RequiresApi(Build.VERSION_CODES.O)
	private void startMyOwnForeground()
	{
		Intent intent = new Intent(this , MainActivity.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(this , 0 ,intent , PendingIntent.FLAG_IMMUTABLE );
		//        String NOTIFICATION_CHANNEL_ID = "example.permanence";
		String NOTIFICATION_CHANNEL_ID = MyApp.CHANNEL_ID;

		String channelName = "Background Service";
		NotificationChannel chan = new NotificationChannel(NOTIFICATION_CHANNEL_ID, MyApp.CHANNEL_NAME, NotificationManager.IMPORTANCE_NONE);
		chan.setLightColor(Color.BLUE);
		chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

		NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		assert manager != null;
		manager.createNotificationChannel(chan);

		NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
		Notification notification = notificationBuilder.setOngoing(true)
				.setContentTitle("Find Your Soulmate  is running in background")
				.setPriority(NotificationManager.IMPORTANCE_DEFAULT)
				.setContentIntent(pendingIntent)
				.build();
		startForeground(2, notification);

		// IsDataChanged();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		super.onStartCommand(intent, flags, startId);
		startTimer();


		IsDataChanged();
		IsMeetingDataChanged();

		return START_STICKY;
	}
	@Override
	public void onDestroy() {
		super.onDestroy();
		stoptimertask();
		Intent broadcastIntent = new Intent();
		broadcastIntent.setAction("restartservice");
		broadcastIntent.setClass(this, Restarter.class);
		this.sendBroadcast(broadcastIntent);
		stopForeground(true);

	}

	private Timer timer;
	private TimerTask timerTask;
	public void startTimer() {
		timer = new Timer();
		timerTask = new TimerTask() {
			public void run() {
				boolean isam = false ;

				spticket = sharedPreferences.getFloat("latestticketno" , 0);
				size = sharedPreferences.getInt("size" , 0);
				id = sharedPreferences.getString("id" , null);

				// float listsize = getAssignedRequests();

				// float tct = getTicketNumber();

				Log.i("Count", "=========  "+ (counter++));
			}
		};
		timer.schedule(timerTask, 5000, 2000); //
	}
	@Nullable
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	public void stoptimertask() {
		if (timer != null) {
			timer.cancel();
			timer = null;
		}

	}



	public void notif() {

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			CharSequence name = "CHANNEL_ID";
			int importance = NotificationManager.IMPORTANCE_DEFAULT;
			NotificationChannel channel = new NotificationChannel("CHANNEL_ID", name, importance);
			NotificationManager notificationManager = getSystemService(NotificationManager.class);
			notificationManager.createNotificationChannel(channel);
		}
		Intent intent = new Intent(this , MainActivity.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(this , 0 , intent , PendingIntent.FLAG_IMMUTABLE);
		// int waitno = getWaitingNumber();

		NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "CHANNEL_ID")
				.setSmallIcon(R.drawable.ic_launcher_background)
				.setContentTitle("You Got A new Notification...")
				.setContentText("Hi Requesting are in Waiting Status")
				.setPriority(NotificationCompat.PRIORITY_DEFAULT)
				.setContentIntent(pendingIntent)
				.setAutoCancel(true);

		NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.notify(0, builder.build());
		AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(ALARM_SERVICE);
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.HOUR, 1);
		Intent intent1 = new Intent("android.action.DISPLAY_NOTIFICATION");
		intent1.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
		PendingIntent broadcast = PendingIntent.getBroadcast(this, 100, intent, PendingIntent.FLAG_IMMUTABLE);
		alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), broadcast);

	}
	public void notif2(String key) {

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			CharSequence name = "CHANNEL_ID";
			int importance = NotificationManager.IMPORTANCE_DEFAULT;
			NotificationChannel channel = new NotificationChannel("CHANNEL_ID", name, importance);
			NotificationManager notificationManager = getSystemService(NotificationManager.class);
			notificationManager.createNotificationChannel(channel);
		}
		Intent intent = new Intent(this , MainActivity.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(this , 0 , intent , PendingIntent.FLAG_IMMUTABLE);
		// int waitno = getWaitingNumber();

		NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "CHANNEL_ID")
				.setSmallIcon(R.drawable.attendance)
				.setContentTitle("New Leave Request")
				.setContentText("A New Leave Req is there in Waiting ....")
				.setPriority(NotificationCompat.PRIORITY_DEFAULT)
				.setContentIntent(pendingIntent)
				.setAutoCancel(false);

		NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.notify(0, builder.build());
		AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(ALARM_SERVICE);
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.HOUR, 1);
		Intent intent1 = new Intent("android.action.DISPLAY_NOTIFICATION");
		intent1.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
		PendingIntent broadcast = PendingIntent.getBroadcast(this, 100, intent, PendingIntent.FLAG_IMMUTABLE);
		alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), broadcast);

	}
	public void notif2meeting(String key) {

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			CharSequence name = "CHANNEL_ID";
			int importance = NotificationManager.IMPORTANCE_DEFAULT;
			NotificationChannel channel = new NotificationChannel("CHANNEL_ID", name, importance);
			NotificationManager notificationManager = getSystemService(NotificationManager.class);
			notificationManager.createNotificationChannel(channel);
		}
		Intent intent = new Intent(this , MainActivity.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(this , 0 , intent , PendingIntent.FLAG_IMMUTABLE);
		// int waitno = getWaitingNumber();

		int no = 787788;


		NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "CHANNEL_ID")
				.setSmallIcon(R.drawable.meeting)
				.setContentTitle("New Meeting Request")
				.setContentText("A New Meeting Req is there.....")
				.setPriority(NotificationCompat.PRIORITY_DEFAULT)
				.setContentIntent(pendingIntent)
				.setAutoCancel(false);

		NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.notify(0, builder.build());
		AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(ALARM_SERVICE);
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.HOUR, 1);
		Intent intent1 = new Intent("android.action.DISPLAY_NOTIFICATION");
		intent1.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
		PendingIntent broadcast = PendingIntent.getBroadcast(this, 100, intent, PendingIntent.FLAG_IMMUTABLE);
		alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), broadcast);

	}


	public void IsDataChanged(){


		SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.shared_pref) , MODE_PRIVATE);
		//class id
		String uid = sharedPreferences.getString("uid" , "");


		 String isstaff = sharedPreferences.getString("isstaff" , "");

		//String[] mailarray = mailll.split("@");
		//String uname = mailarray[0];



			databaseReferenceAllMessages = FirebaseDatabase.getInstance().getReference("Classes")
					.child(uid).child("Leave");
			databaseReferenceAllMessages.addValueEventListener(new ValueEventListener() {
				@Override
				public void onDataChange(@NonNull DataSnapshot snapshot) {

					for (DataSnapshot snap : snapshot.getChildren()){
						String key = snap.getKey();
						//for (DataSnapshot dataSnapshot2 : snap.getChildren()){
							try {
								if (snap.child("reqStatus").getValue(String.class).equals("waiting"))
								{
									if (isstaff.equals("yes")) {

										notif2(key);
									}
									//  Toast.makeText(MyServiceAdmin.this, "key : true", Toast.LENGTH_SHORT).show();

								}
							}catch (Exception exc){

							}
							//  String hasunread = snap.child("hasunread").getValue(String.class);
					//	}
					}


				}

				@Override
				public void onCancelled(@NonNull DatabaseError error) {

				}
			});

	//	}



	}
	public void IsMeetingDataChanged(){


		SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.shared_pref) , MODE_PRIVATE);
		//class id
		String uid = sharedPreferences.getString("uid" , "");


		 String isstaff = sharedPreferences.getString("isstaff" , "");

		//String[] mailarray = mailll.split("@");
		//String uname = mailarray[0];



			DatabaseReference databaseReferenceAllMessages = FirebaseDatabase.getInstance().getReference("Classes")
					.child(uid).child("meetings");
			databaseReferenceAllMessages.addValueEventListener(new ValueEventListener() {
				@Override
				public void onDataChange(@NonNull DataSnapshot snapshot) {

					for (DataSnapshot snap : snapshot.getChildren()){
						String key = snap.getKey();
						//for (DataSnapshot dataSnapshot2 : snap.getChildren()){
							try {
								if (snap.child("meetingStatus").getValue(String.class).equals("New"))
								{
									if (isstaff.equals("no")) {

										notif2meeting(key);
										//notif2(key);
									}
									//  Toast.makeText(MyServiceAdmin.this, "key : true", Toast.LENGTH_SHORT).show();

								}
							}catch (Exception exc){

							}
							//  String hasunread = snap.child("hasunread").getValue(String.class);
					//	}
					}


				}

				@Override
				public void onCancelled(@NonNull DatabaseError error) {

				}
			});

	//	}



	}



}
