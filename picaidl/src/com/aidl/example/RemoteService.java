package com.aidl.example;

import java.util.LinkedList;
import java.util.List;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import com.aidl.example.aidl.IMyService;
import com.aidl.example.aidl.Person;

public class RemoteService extends Service {  
	  
    private LinkedList<Person> personList = new LinkedList<Person>();  
      
    @Override  
    public IBinder onBind(Intent intent) {  
            return mBinder;  
    }  
  
	private final IMyService.Stub mBinder = new IMyService.Stub() {

		@Override
		public void savePersonInfo(Person person) throws RemoteException {
			if (person != null) {
				personList.add(person);
			}
		}

		@Override
		public List<Person> getAllPerson() throws RemoteException {
			return personList;
		}

		@Override
		public String sayHello() throws RemoteException {
			return "欢迎你通过AIDL访问服务器端";
		}
	};
}  
