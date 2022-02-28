package com.example.patienttracker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class appointment extends AppCompatActivity {

    private  static final String TAG="MainActivity";

    private Button mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    Button btn_app;
    private  Button mdisplayTime;
    TimePickerDialog timePickerDialog;
    EditText editn,editr;
    DatabaseReference reff;
    Member member;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);
        btn_app=(Button)findViewById(R.id.show);
        editn=(EditText)findViewById(R.id.editname);
        editr=(EditText)findViewById(R.id.editreason);
        member=new Member();
        reff= FirebaseDatabase.getInstance().getReference().child("Member");
        btn_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                member.setName(editn.getText().toString().trim());
                member.setDate(mDisplayDate.getText().toString().trim());
                member.setTime(mdisplayTime.getText().toString().trim());
                member.setReason(editr.getText().toString().trim());
                reff.push().setValue(member);
                    Toast.makeText(appointment.this,"DATA HAS BEEN SEND SUCCESSFULLY", Toast.LENGTH_LONG).show();
                            Intent i=new Intent(appointment.this,appointment1.class);
                    startActivity(i);

                }

        });



        mdisplayTime=(Button) findViewById(R.id.time);
        mdisplayTime.setOnClickListener(new View.OnClickListener() {
            Calendar calendar=Calendar.getInstance();
            int hour=calendar.get(Calendar.HOUR);
            int minute=calendar.get(Calendar.MINUTE);
            @Override
            public void onClick(View v) {

                timePickerDialog=new TimePickerDialog(appointment.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hour, int minute) {

                        String am_pm;
                        if (hour>12) {
                            am_pm="AM";
                            mdisplayTime.setText(hour + ":" + minute+ ":" +am_pm);
                        }
                        else if (hour==12){
                            am_pm="PM";
                            mdisplayTime.setText(hour + ":" + minute+ ":" +am_pm);
                        }
                        else {
                            am_pm="PM";
                            mdisplayTime.setText(hour + ":" + minute+ ":" +am_pm);
                        }

                    }
                },hour,minute,true);
                timePickerDialog.show();
            }
        });


        mDisplayDate=(Button) findViewById(R.id.date);

        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog=new DatePickerDialog(appointment.this,
                        android.R.style.Theme_Black_NoTitleBar_Fullscreen,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });


        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyy:" + month +"/"+ day + "/" +year);

                String date = month + "/" + day + "/" + year;
                mDisplayDate.setText(date);

            }
        };
    }
}
