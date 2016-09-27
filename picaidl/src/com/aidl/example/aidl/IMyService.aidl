package com.aidl.example.aidl;

import com.aidl.example.aidl.Person;

interface IMyService {  
     void savePersonInfo(in Person person);  
     List<Person> getAllPerson();  
     String sayHello();  
}  