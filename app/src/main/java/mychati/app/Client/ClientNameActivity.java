package mychati.app.Client;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import mychati.app.HelloActivity.HelloClientActivity;
import mychati.app.HelloActivity.HelloDayActivity;
import mychati.app.HelloActivity.HelloVecherActivity;
import mychati.app.R;
import mychati.app.RegisterAndProverka.SplashScreenActivity;
import mychati.app.Shops.ShopHomeActivity;
import mychati.app.Shops.ShopName;

public class ClientNameActivity extends AppCompatActivity {
private EditText editname;
private AppCompatButton buttonnamesave;
private FirebaseAuth mAuth;
private DatabaseReference nameRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_name);


        nameRef= FirebaseDatabase.getInstance().getReference().child("client");


       mAuth=FirebaseAuth.getInstance();
       editname=(EditText) findViewById(R.id.editclientname);
       buttonnamesave=(AppCompatButton) findViewById(R.id.buttonnameclient);




        buttonnamesave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savename();
            }
        });


    }

    private void savename() {
        if (TextUtils.isEmpty(editname.getText().toString())){
            Toast.makeText(this, "Введите свое имя", Toast.LENGTH_SHORT).show();
        }else{
            HashMap<String,Object>name=new HashMap<>();
            name.put("uid",mAuth.getCurrentUser().getUid());
            name.put("clientName",editname.getText().toString());
            nameRef.child(mAuth.getCurrentUser().getUid()).updateChildren(name).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {

                        Toast.makeText(ClientNameActivity.this, "Успешная регистрация", Toast.LENGTH_SHORT).show();


String name=editname.getText().toString();
Date currentDate=new Date();
int hour=currentDate.getHours();
                        if (hour>= 6 && hour < 12) {
                            Intent mk=new Intent(ClientNameActivity.this,HelloClientActivity.class);
                            mk.putExtra("name", name);
                            mk.putExtra("ident", "2");
                            startActivity(mk);
                            finish();
                        }
                        else if (hour >= 12 && hour < 16) {
                            Intent mks=new Intent(ClientNameActivity.this,HelloDayActivity.class);
                            mks.putExtra("name", name);
                            mks.putExtra("ident", "2");
                            startActivity(mks);
                            finish();
                        }
                        else if (hour >= 16 && hour < 24) {
                            Intent mksi=new Intent(ClientNameActivity.this,HelloVecherActivity.class);
                            mksi.putExtra("name", name);
                            mksi.putExtra("ident", "2");
                            startActivity(mksi);
                            finish();
                        }
openActivity(name,"2");

















                    } else {

                        String message = task.getException().toString();
                        Toast.makeText(ClientNameActivity.this, "Ошибка" + message, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }













    private void openActivity(String name, String value) {
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        ClientNameActivity.PartOfTheDay part = getPartOfDayByHours(hour);
        Class<?> destination = getDestinationClassByPartOfTheDay(part);

        if (destination == null) return;

        Intent intent = new Intent(
                ClientNameActivity.this,
                destination);
        intent.putExtra("name", name);
        intent.putExtra("ident", value);

        startActivity(intent);
        finish();
    }

    enum PartOfTheDay {
        MORNING,
        AFTERNOON,
        EVENING,
        NIGHT

    }

    private ClientNameActivity.PartOfTheDay getPartOfDayByHours(int hour) {
        if (hour >= 6 && hour < 12) {
            return ClientNameActivity.PartOfTheDay.MORNING;
        }
        if (hour >= 12 && hour < 16) {
            return ClientNameActivity.PartOfTheDay.AFTERNOON;
        }
        if (hour >= 16 && hour < 24) {
            return ClientNameActivity.PartOfTheDay.EVENING;
        }
        if (hour >= 0 && hour < 6) {
            return ClientNameActivity.PartOfTheDay.NIGHT;
        }
        throw new IllegalStateException("Unknown hour:" + hour);
    }

    private Class<?> getDestinationClassByPartOfTheDay(ClientNameActivity.PartOfTheDay partOfTheDay) {
        switch (partOfTheDay) {
            case MORNING:
                return HelloClientActivity.class;
            case AFTERNOON:
                return HelloDayActivity.class;
            case EVENING:
                return HelloVecherActivity.class;
            case NIGHT:
                return null;
        }
        throw new IllegalStateException("Unknown part of day");
    }
}