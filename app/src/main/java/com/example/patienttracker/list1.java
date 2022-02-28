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

public class list1 extends AppCompatActivity {

    Button btn_app;
    Button btn_pat;
    Button btn_doc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list1);

        btn_doc=(Button) findViewById(R.id.doctor);
        btn_doc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(list1.this ,Doctor2.class);
                startActivity(i);
            }
        });

        btn_app=(Button)findViewById(R.id.app);
        btn_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(list1.this,AppointmentList.class);
                startActivity(intent);


            }
        });


    }
}
