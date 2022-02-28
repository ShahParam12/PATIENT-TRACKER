package com.example.patienttracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class appointment1 extends AppCompatActivity {

     private  TextView text;

     Button btn_listt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment1);

        btn_listt=(Button)findViewById(R.id.button4);
        btn_listt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(appointment1.this,AppointmentList.class);
                startActivity(i);
            }
        });

        text=(TextView)findViewById(R.id.textapp);

        FirebaseDatabase.getInstance().getReference().child("Member")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String s="";
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                            s = s + "\n" + "DATE: "  + snapshot.child("date").getValue().toString() + "\n"
                                    + "TIME: "  + snapshot.child("time").getValue().toString() + "\n"
                                    + "NAME: "  +snapshot.child("name").getValue().toString() + "\n"
                                    + "REASON: "  +snapshot.child("reason").getValue().toString() + "\n";

                        }


                        text.setText(s);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });



    }
}
