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

public class death extends AppCompatActivity {
    private TextView textView;
    private Button btn_pat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_death);

        textView=(TextView)findViewById(R.id.te);
        btn_pat= (Button)findViewById(R.id.btn_patient);
        btn_pat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(death.this,PatientList.class);
                startActivity(i);
            }
        });

        FirebaseDatabase.getInstance().getReference().child("poison")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String s="";
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                            s = s + "\n" + "Name: "  + snapshot.child("n1").getValue().toString() + "\n"
                                    + "Last Name: "  + snapshot.child("ln1").getValue().toString() + "\n"
                                    + "Age: "  +snapshot.child("age1").getValue().toString() + "\n"
                                    + "Disease: "  +snapshot.child("dis1").getValue().toString() + "\n"
                                    + "Address: "  +snapshot.child("add1").getValue().toString() + "\n";


                        }


                        textView.setText(s);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }
}
