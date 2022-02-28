package com.example.patienttracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class feedback extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        final EditText edit1=(EditText)findViewById(R.id.edit1);
        final EditText edit2 =findViewById(R.id.edit);
        Button btn=(Button)findViewById(R.id.button);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("Message/app");
                intent.putExtra(Intent.EXTRA_EMAIL, new String(""));
                intent.putExtra(Intent.EXTRA_SUBJECT, "FeedBack From Patient Tracker");
                intent.putExtra(Intent.EXTRA_TEXT, "NAME:" + edit1.getText() + "\n Message:" + edit2.getText());
                try {
                    startActivity(Intent.createChooser(intent, "Please select Application"));
                } catch (android.content.ActivityNotFoundException ex) {

                    Toast.makeText(feedback.this, "THERE are no Email Client", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

    }

