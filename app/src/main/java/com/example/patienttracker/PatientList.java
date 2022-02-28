package com.example.patienttracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class PatientList extends AppCompatActivity {

    RecyclerView recyclerView;
    PoisonAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_list);


                recyclerView=(RecyclerView)findViewById(R.id.recycler);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));

                FirebaseRecyclerOptions<poison> options =
                        new FirebaseRecyclerOptions.Builder<poison>()
                                .setQuery(FirebaseDatabase.getInstance().getReference().child("poison"), poison.class)
                                .build();

                adapter=new PoisonAdapter(options,this);
                recyclerView.setAdapter(adapter);
            }

            @Override
            protected void onStart() {
                super.onStart();
                adapter.startListening();
            }

            @Override
            protected void onStop() {
                super.onStop();
                adapter.stopListening();
            }
        }
