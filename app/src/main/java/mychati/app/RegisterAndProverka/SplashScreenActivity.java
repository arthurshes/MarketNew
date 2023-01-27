package mychati.app.RegisterAndProverka;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import mychati.app.CategUserActivity;
import mychati.app.HelloActivity.HelloClientActivity;
import mychati.app.HelloActivity.HelloDayActivity;
import mychati.app.HelloActivity.HelloVecherActivity;
import mychati.app.R;


public class SplashScreenActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private DatabaseReference shop;


    private String name;
    private DatabaseReference clientse;
    private HashMap<DatabaseReference, ValueEventListener> listeners = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        mAuth = FirebaseAuth.getInstance();


        shop = FirebaseDatabase.getInstance().getReference().child("shops");
        clientse = FirebaseDatabase.getInstance().getReference().child("client");
        currentUser = mAuth.getCurrentUser();


        if (currentUser != null) {
            openShop();


        } else {
            startActivity(new Intent(SplashScreenActivity.this, RegisterPhoneActivity.class));
            finish();
        }
    }

    private void openShop() {

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists() && snapshot.getChildrenCount() > 0) {
                    name = snapshot.child("MagName").getValue().toString();
                    Date currentdate=new Date();
                    int hours=currentdate.getHours();
                    if (hours >= 6 && hours < 12) {
                      Intent mk=new Intent(SplashScreenActivity.this,HelloClientActivity.class);
                        mk.putExtra("name", name);
                        mk.putExtra("ident", "1");
                        startActivity(mk);
                        finish();
                    }
                    else if (hours >= 12 && hours < 16) {
                        Intent mks=new Intent(SplashScreenActivity.this,HelloDayActivity.class);
                        mks.putExtra("name", name);
                        mks.putExtra("ident", "1");
                        startActivity(mks);
                        finish();
                    }
                    else if (hours >= 16 && hours < 24) {
                        Intent mksi=new Intent(SplashScreenActivity.this,HelloVecherActivity.class);
                        mksi.putExtra("name", name);
                        mksi.putExtra("ident", "1");
                        startActivity(mksi);
                        finish();
                    }

                } else {
                    openClient();
                    Toast.makeText(SplashScreenActivity.this, "Не курьер", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        DatabaseReference reference = shop.child(currentUser.getUid());
        listeners.put(reference, listener);
        reference.addValueEventListener(listener);


    }



    private void openClient() {
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists() && snapshot.getChildrenCount() > 0) {
                    name = snapshot.child("clientName").getValue().toString();

                    Date currentdate=new Date();

                    int hours=currentdate.getHours();
                    Log.d("name", String.valueOf(hours));
                    if (hours>= 6 && hours < 12) {
                        Intent mk=new Intent(SplashScreenActivity.this,HelloClientActivity.class);
                        mk.putExtra("name", name);
                        mk.putExtra("ident", "2");
                        startActivity(mk);
                        finish();
                    }
                    else if (hours >= 12 && hours < 16) {
                        Intent mks=new Intent(SplashScreenActivity.this,HelloDayActivity.class);
                        mks.putExtra("name", name);
                        mks.putExtra("ident", "2");
                        startActivity(mks);
                        finish();
                    }
                    else if (hours >= 16 && hours < 24) {
                        Intent mksi=new Intent(SplashScreenActivity.this,HelloVecherActivity.class);
                        mksi.putExtra("name", name);
                        mksi.putExtra("ident", "2");
                        startActivity(mksi);
                        finish();
                    }

                } else {
                    startActivity(new Intent(SplashScreenActivity.this, CategUserActivity.class));
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        DatabaseReference reference = clientse.child(currentUser.getUid());
        listeners.put(reference, listener);
        reference.addValueEventListener(listener);

    }

    @Override
    protected void onPause() {
        super.onPause();

        for(Map.Entry<DatabaseReference, ValueEventListener> entry: listeners.entrySet()){
            entry.getKey().removeEventListener(entry.getValue());
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        listeners.clear();
        listeners=null;
    }
}