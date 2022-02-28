package com.example.patienttracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    Button buttonn;

    private Button mlogoutbtn;
    ImageView imageView;



    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    Button b1;
    Button b4;
    Button b5;
    Button b6;
    private Object NonNull;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        imageView=(ImageView) findViewById(R.id.image);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,ProfileActivity.class);
                startActivity(i);
            }
        });


        CheckPermission();

        CheckConnection();





        b1=(Button)findViewById(R.id.buttonhealth);
        b5=(Button)findViewById(R.id.report);
        b4=(Button)findViewById(R.id.buttoncomfort);
        b6=(Button)findViewById(R.id.buttondoctor);

        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,Reports.class);
                startActivity(i);
            }
        });
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,doctor.class);
                startActivity(intent);
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,list1.class);
                startActivity(intent);
            }
        });


        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,feedback.class);
                startActivity(intent);
            }
        });



                mAuth = FirebaseAuth.getInstance();
                mAuthListener = new FirebaseAuth.AuthStateListener() {
                    @Override
                    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                        if (firebaseAuth.getCurrentUser() == null) {

                            startActivity(new Intent(MainActivity.this, login_form.class));
                        }
                    }
                };

                mlogoutbtn = (Button) findViewById(R.id.logoutbtn);

                mlogoutbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        mAuth.signOut();
                    }
                });




        buttonn=findViewById(R.id.buttonlogin);
                buttonn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent i =new Intent(MainActivity.this,login_form.class);
                        startActivity(i);

                    }
                });


        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView toolbarTitle=findViewById(R.id.titleText);
        TextView toolbarCounter = findViewById(R.id.titleCounter);


        toolbar.setTitle("");
        toolbarTitle.setText("PATIENT'S LOGIN");
        toolbarCounter.setText("");
        setSupportActionBar(toolbar);



        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


                ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer,toolbar,
                        R.string.navigation_drawer_open, R.string.navigation_drawer_close);
                drawer.addDrawerListener(toggle);
                toggle.syncState();


        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new homefragment()).commit();
        navigationView.setCheckedItem(R.id.nav_home);

    }

            @Override
            public void onBackPressed() {
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    super.onBackPressed();
                }
            }

    @Override
    protected void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(mAuthListener);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {


        switch (item.getItemId()){

            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new homefragment()).commit();
                Toast.makeText(getApplicationContext(),"home",Toast.LENGTH_LONG).show();
                break;


            case R.id.nav_about:
                Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://myraah.io/webs/1584352057290182/about.html"));
                startActivity(intent);
                Toast.makeText(getApplicationContext(),"ABOUT",Toast.LENGTH_LONG).show();
                break;



            case R.id.nav_msg:
                Uri uri = Uri.parse("smsto:" + "");
                Intent i = new Intent(Intent.ACTION_SENDTO,uri);
                i.setPackage("com.android.mms");
                startActivity(i);
                break;


            case R.id.nav_help:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new helppfragment()).commit();
                Toast.makeText(getApplicationContext(),"HELP",Toast.LENGTH_LONG).show();
                break;






            case R.id.nav_share:
                Intent shareintent = new Intent();
                shareintent.setAction(Intent.ACTION_SEND);
                shareintent.putExtra(Intent.EXTRA_TEXT,"https://play.google.com/store/apps");
                shareintent.setType("text/plain");
                startActivity(Intent.createChooser(shareintent,"share Via"));
                break;


        }
        return true;
    }

    public void CheckPermission() {



        Dexter.withActivity(this)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {


                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {

                        Intent i =new Intent();
                        i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package",getPackageName(),null);
                        startActivity(i);

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                        token.continuePermissionRequest();

                    }
                }).check();
    }

    public void CheckConnection(){

        ConnectivityManager manager = (ConnectivityManager)
                getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = manager.getActiveNetworkInfo();

        if (null!=activeNetwork){

            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {

                Toast.makeText(this,"WIFI ENABLED",Toast.LENGTH_LONG).show();
            }
            else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                Toast.makeText(this,"DATA NETWORK ENABLED",Toast.LENGTH_LONG).show();
            }

            }

            else {
                 Toast.makeText(this,"NO INTERNET CONNECTION ",Toast.LENGTH_LONG).show();
        }

    }
}


