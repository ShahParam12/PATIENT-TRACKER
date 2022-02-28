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

public class Doctor1 extends AppCompatActivity {
    TextView textView;
    Button btn_doc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor1);

        btn_doc = findViewById(R.id.btn_doc);
        btn_doc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Doctor1.this,Doctor2.class);
                startActivity(i);
            }
        });

        textView=(TextView)findViewById(R.id.te);
        FirebaseDatabase.getInstance().getReference("Doct")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String s="";
                        for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                            s = s + "\n" + "Name: "  + snapshot.child("name").getValue().toString() + "\n"
                                    + "Last_name: "  + snapshot.child("lastname").getValue().toString() + "\n"
                                    + "Specialist: "  +snapshot.child("specialist").getValue().toString() + "\n"
                                    + "Age: "  +snapshot.child("age").getValue().toString() + "\n"
                                    + "Phone: "  +snapshot.child("phone").getValue().toString() + "\n";

                        }
                        textView.setText(s);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }
}
