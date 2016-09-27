package com.aidl.example.pic;

import java.util.List;

import com.aidl.example.R;
import com.aidl.example.RemoteService;
import com.aidl.example.aidl.IMyService;
import com.aidl.example.aidl.Person;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class RemoteClientActivity extends Activity {

	private TextView textHello;
	private IMyService myService;
	private Button btnSave;
	private Button btnGet;

	private ServiceConnection conn = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub
			myService = null;
		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// TODO Auto-generated method stub
			myService = IMyService.Stub.asInterface(service);
			try {
				textHello.setText(myService.sayHello());
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pic);
		textHello = (TextView) this.findViewById(R.id.textView1);
		btnSave = (Button) this.findViewById(R.id.button1);
		btnGet = (Button) this.findViewById(R.id.button2);

		Intent intent = new Intent(RemoteClientActivity.this,RemoteService.class);
		bindService(intent, conn, BIND_AUTO_CREATE);

		btnSave.setOnClickListener(new OnClickListener() {
			private int index = 0;

			@Override
			public void onClick(View v) {
				Person person = new Person();
				index = index + 1;
				person.setName("Person" + index);
				person.setAge(20);
				person.setTelNumber("123456");
				try {
					myService.savePersonInfo(person);
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
		});
		btnGet.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				List<Person> list = null;

				try {
					list = myService.getAllPerson();
				} catch (RemoteException e) {
					e.printStackTrace();
				}

				if (list != null) {
					StringBuilder text = new StringBuilder();

					for (Person person : list) {
						text.append("\n联系人:");
						text.append(person.getName());
						text.append("\n            年龄:");
						text.append(person.getAge( ));
						text.append("\n 电话:");
						text.append(person.getTelNumber());
					}

					textHello.setText(text);
				} else {
					Toast.makeText(RemoteClientActivity.this, "得到数据出错",
							Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unbindService(conn);
	}
}
