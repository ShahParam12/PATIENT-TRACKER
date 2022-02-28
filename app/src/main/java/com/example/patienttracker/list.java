package com.example.patienttracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class list extends AppCompatActivity {

    Button btn_app;
    Button btn_pat;
    Button btn_doc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);


        btn_app=(Button)findViewById(R.id.app);
        btn_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(list.this,AppointmentList.class);
                startActivity(intent);


            }
        });

        btn_pat=(Button)findViewById(R.id.patient);
        btn_pat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(list.this,PatientList.class);
                startActivity(intent);
            }
        });


    }
}