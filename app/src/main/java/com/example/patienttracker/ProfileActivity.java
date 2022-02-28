package com.example.patienttracker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ProfileActivity extends AppCompatActivity {

    EditText name,email;
    Button btn_save,btn_apply;
    Switch switch1;
    DatabaseReference ref;
    profilee profilee;
    private ImageView profileimage;
    private final static int PICK_IMAGE = 1;

    OutputStream outputStream;

    Uri ImageUri;

    public static final String SHARED_PREFS ="sharedPrefs";
    public static final String TEXT="text";
    public static final String TEXT1="text1";


    private String text,text1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile2);


        profileimage=(ImageView)findViewById(R.id.avatarIv);
        profileimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent gallery = new Intent(Intent.ACTION_GET_CONTENT);
                gallery.setType("image/*");
                startActivityForResult(Intent.createChooser(gallery,"select picture"),PICK_IMAGE);
            }
        });

        btn_apply=(Button)findViewById(R.id.apply);
        name=(EditText) findViewById(R.id.nameTv);
        email=(EditText) findViewById(R.id.emailTv);
        btn_save=(Button)findViewById(R.id.save);

        btn_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name.setText(name.getText().toString());
                email.setText(email.getText().toString());

            }
        });
        profilee=new profilee();
        ref=FirebaseDatabase.getInstance().getReference().child("profilee");

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                profilee.setName(name.getText().toString().trim());
                profilee.setEmail(email.getText().toString().trim());

                ref.push().setValue(profilee);
                Toast.makeText(ProfileActivity.this,"DATA HAS BEEN SUCCESSFULLY",Toast.LENGTH_LONG).show();
                Intent i = new Intent(ProfileActivity.this,MainActivity.class);
                startActivity(i);
                saveData();


            }
        });

        loadData();
        updateView();

    }

    public void saveData(){

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();

        editor.putString(TEXT,name.getText().toString());
        editor.putString(TEXT1,email.getText().toString());



        editor.apply();

        Toast.makeText(this, "DATA SAVED", Toast.LENGTH_SHORT).show();

    }

    public void loadData(){

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        text = sharedPreferences.getString(TEXT,"");
        text1 = sharedPreferences.getString(TEXT1,"");

    }

    public void updateView(){

        name.setText(text);
        email.setText(text1);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            ImageUri = data.getData();
            try {

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),ImageUri);
                profileimage.setImageBitmap(bitmap);
            }catch (IOException e){
                e.printStackTrace();
            }

        }
    }
}



