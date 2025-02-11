package com.example.mylogin;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity implements OnClickListener
{
EditText Username, EmailID, Password; Button Insert,Delete,Update,View,ViewAll; SQLiteDatabase db;
@Override
public void onCreate(Bundle savedInstanceState)
{
super.onCreate(savedInstanceState); setContentView(R.layout.activity_main);

Username= findViewById(R.id.username); EmailID = findViewById(R.id.EmailID); Password = findViewById(R.id.Password); Insert= findViewById(R.id.Insert);
 
Delete= findViewById(R.id.Delete); Update= findViewById(R.id.Update); View= findViewById(R.id.View); ViewAll=findViewById(R.id.ViewAll);

Insert.setOnClickListener(this); Delete.setOnClickListener(this); Update.setOnClickListener(this); View.setOnClickListener(this); ViewAll.setOnClickListener(this);

// Creating database and table db=openOrCreateDatabase("StudentDB", Context.MODE_PRIVATE, null); db.execSQL("CREATE TABLE IF NOT EXISTS student(username
VARCHAR,emailid VARCHAR,password VARCHAR);");
}
public void onClick(View view)
{
// Inserting a record to the Student table
if(view==Insert)
{
// Checking for empty fields
if(Username.getText().toString().trim().length()==0|| EmailID.getText().toString().trim().length()==0|| Password.getText().toString().trim().length()==0)
{
showMessage("Error", "Please enter all values"); return;
}
db.execSQL("INSERT INTO student VALUES('"+Username.getText()+"','"+ EmailID.getText()+
"','"+ Password.getText()+"');"); showMessage("Success", "Record added"); clearText();
}
// Deleting a record from the Student table
if(view==Delete)
{
// Checking for empty roll number
if(Username.getText().toString().trim().length()==0)
{
showMessage("Error", "Please enter User name"); return;
}
@SuppressLint("Recycle") Cursor c=db.rawQuery("SELECT * FROM student WHERE User name='"+Username.getText()+"'", null);
if(c.moveToFirst())
{
db.execSQL("DELETE FROM student WHERE rollno='"+Username.getText()+"'");
showMessage("Success", "Record Deleted");
}
else
{
showMessage("Error", "Invalid User name");
}
clearText();
 
}
// Updating a record in the Student table
if(view==Update)
{
// Checking for empty roll number
if(Username.getText().toString().trim().length()==0)
{
showMessage("Error", "Please enter User name"); return;
}
@SuppressLint("Recycle") Cursor c=db.rawQuery("SELECT * FROM student WHERE User name='"+Username.getText()+"'", null);
if(c.moveToFirst()) {
db.execSQL("UPDATE student SET name='" + EmailID.getText() +
"',marks='" + Password.getText() +
"' WHERE rollno='"+Username.getText()+"'"); showMessage("Success", "Record Modified");
}
else {
showMessage("Error", "Invalid User name");
}
clearText();
}
// Display a record from the Student table
if(view==View)
{
// Checking for empty roll number
if(Username.getText().toString().trim().length()==0)
{
showMessage("Error", "Please enter User name"); return;
}
@SuppressLint("Recycle") Cursor c=db.rawQuery("SELECT * FROM student WHERE User name='"+Username.getText()+"'", null);
if(c.moveToFirst())
{
EmailID.setText(c.getString(1)); Password.setText(c.getString(2));
}
else
{
showMessage("Error", "Invalid Rollno"); clearText();
}
}
// Displaying all the records
if(view==ViewAll)
{
@SuppressLint("Recycle") Cursor c=db.rawQuery("SELECT * FROM student", null);
if(c.getCount()==0)
{
showMessage("Error", "No records found"); return;
}
StringBuilder buffer=new StringBuilder();
while(c.moveToNext())
 
{
buffer.append("User name:
").append(c.getString(0)).append("\n");
buffer.append("EmailID: ").append(c.getString(1)).append("\n");
buffer.append("Password: ").append(c.getString(2)).append("\n\n");
}
showMessage("Student Details", buffer.toString());
}
}
public void showMessage(String title,String message)
{
Builder builder=new Builder(this); builder.setCancelable(true); builder.setTitle(title); builder.setMessage(message); builder.show();
}
public void clearText()
{
Username.setText(""); EmailID.setText(""); Password.setText(""); Username.requestFocus();
}
}
