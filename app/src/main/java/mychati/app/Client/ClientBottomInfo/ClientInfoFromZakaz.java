package mychati.app.Client.ClientBottomInfo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import mychati.app.Client.HomeActivity;
import mychati.app.R;
import mychati.app.Shops.ShopHomeActivity;
import mychati.app.Shops.ShopName;

public class ClientInfoFromZakaz extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference oform;
    private EditText editKv, editPodezd, editAadress, editPhone, editlvl, editdomophon;
    private TextView price,textItogprice;
    private String myName;
    private AppCompatButton oformshopButton;
    private ValueEventListener valueEventListener;
    private HashMap<DatabaseReference,ValueEventListener>hashMap=new HashMap<>();
    private DatabaseReference Korzina;
    private DatabaseReference my;
    private String itogPrice,uids, saveCurrentDate, saveCurrentTime, ProductRandomKey, Date,Moth,Hours,Minute,Tovarss;
    private DatabaseReference docart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_info_from_zakaz);
        mAuth = FirebaseAuth.getInstance();
        textItogprice=(TextView)findViewById(R.id.otextItogInf);
        editlvl = (EditText) findViewById(R.id.edtlvl);
        my = FirebaseDatabase.getInstance().getReference().child("client");
        editKv = (EditText) findViewById(R.id.kvartiraedit);
        Korzina = FirebaseDatabase.getInstance().getReference().child("DoCart");
        editPodezd = (EditText) findViewById(R.id.editpodezd);
        editAadress = (EditText) findViewById(R.id.adressmy);
        editPhone = (EditText) findViewById(R.id.editkontektphone);
        editdomophon = (EditText) findViewById(R.id.editdomphone);
        oformshopButton = (AppCompatButton) findViewById(R.id.buttonoformlenzak);
        oform = FirebaseDatabase.getInstance().getReference().child("oformzakaz");
        docart = FirebaseDatabase.getInstance().getReference().child("DoCart");
        price = (TextView) findViewById(R.id.textpriceoform);
        editlvl = (EditText) findViewById(R.id.edtlvl);
        Intent mn = getIntent();
        uids = mn.getStringExtra("Uid");
        itogPrice=mn.getStringExtra("price");
        Log.d("iop", uids);
textItogprice.setText("Итого "+itogPrice);
     valueEventListener=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists() && snapshot.getChildrenCount() > 0) {
                    myName = snapshot.child("clientName").getValue().toString();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        my.child(mAuth.getCurrentUser().getUid()).addValueEventListener(valueEventListener);
        hashMap.put(my,valueEventListener);

        oformshopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loaddata();

            }
        });


///Копирование и отправка по другому пути///

///Копирование и отправка по другому пути///


    }


    private void moveData(final DatabaseReference fromPath, final DatabaseReference toPath) {
        fromPath.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HashMap<String,Object>kjd=new HashMap<>();
                kjd.put("Zakaz",dataSnapshot.getValue());

                kjd.put("Zakazstatus",uids);
                kjd.put("phone",editPhone.getText().toString());
                kjd.put("ClientUid",mAuth.getCurrentUser().getUid());
                kjd.put("ClientName",myName);
                kjd.put("shopId",uids);
                kjd.put("Moth",Moth);
                kjd.put("Hour",Hours);
                kjd.put("itogPrice",itogPrice);
                kjd.put("Minute",Minute);
                kjd.put("Date",Date);
                kjd.put("Prochitan","1");
                kjd.put("ProductId",ProductRandomKey);
                kjd.put("adress",editAadress.getText().toString());
                kjd.put("podezd",editPodezd.getText().toString());
                kjd.put("kvartira",editKv.getText().toString());
                kjd.put("lvl",editlvl.getText().toString());
                kjd.put("domophone",editdomophon.getText().toString());
                toPath.setValue(kjd, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError firebaseError, DatabaseReference firebase) {
                        if (firebaseError != null) {
                            System.out.println("Copy failed");
                        } else {
                            System.out.println("Success");
fromPath.removeValue();
startActivity(new Intent(ClientInfoFromZakaz.this,HomeActivity.class));
finish();
                        }
                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    private void loaddata() {
        if (TextUtils.isEmpty(editAadress.getText().toString())) {
            Toast.makeText(this, "Введите свой адрес", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(editPhone.getText().toString())) {
            Toast.makeText(this, "Введите свой номер", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(editdomophon.getText().toString())) {
            Toast.makeText(this, "Введите код домофона", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(editlvl.getText().toString())) {
            Toast.makeText(this, "Введите свой этаж", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(editPodezd.getText().toString())) {
            Toast.makeText(this, "Введите свой подъезд", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(editKv.getText().toString())) {
            Toast.makeText(this, "Введите свою квартиру/офис", Toast.LENGTH_SHORT).show();
        } else {


            Calendar calendar = Calendar.getInstance();

            SimpleDateFormat currentDate = new SimpleDateFormat("ddMMyyyy");
            saveCurrentDate = currentDate.format(calendar.getTime());



            SimpleDateFormat currentday=new SimpleDateFormat("dd");

            Date=currentday.format(calendar.getTime());





            SimpleDateFormat hours=new SimpleDateFormat("HH");

            Hours=hours.format(calendar.getTime());
SimpleDateFormat moth=new SimpleDateFormat("MM");
Moth=moth.format(calendar.getTime());


SimpleDateFormat minute=new SimpleDateFormat("mm");

Minute=minute.format(calendar.getTime());



            SimpleDateFormat currentTime = new SimpleDateFormat("HHmmss");
            saveCurrentTime = currentTime.format(calendar.getTime());

            ProductRandomKey = saveCurrentDate + saveCurrentTime;


            moveData(docart.child(mAuth.getCurrentUser().getUid()).child(uids), oform.child(mAuth.getCurrentUser().getUid() + ProductRandomKey + uids));


        }


    }

    @Override
    protected void onStop() {
        super.onStop();
        for (Map.Entry<DatabaseReference, ValueEventListener> entry : hashMap.entrySet()) {
            entry.getKey().removeEventListener(entry.getValue());
        }
        myName=null;
        hashMap.clear();
        hashMap=null;
        valueEventListener=null;
        Moth=null;
        Hours=null;
        Date=null;
        Minute=null;
        itogPrice=null;
        ProductRandomKey=null;
        uids=null;
        mAuth=null;
    }
}
