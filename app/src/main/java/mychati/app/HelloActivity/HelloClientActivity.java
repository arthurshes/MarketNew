package mychati.app.HelloActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import mychati.app.Client.HomeActivity;
import mychati.app.R;
import mychati.app.Shops.ShopHomeActivity;

public class HelloClientActivity extends AppCompatActivity {
private LottieAnimationView lottieAnimationView;
private TextView name;
private DatabaseReference shop;
private DatabaseReference cli;

private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_client);
        lottieAnimationView=findViewById(R.id.lottiemorr);
mAuth=FirebaseAuth.getInstance();

name=(TextView)findViewById(R.id.textView12chhhchchh) ;
cli= FirebaseDatabase.getInstance().getReference().child("client");
shop=FirebaseDatabase.getInstance().getReference().child("shops");
        Log.d("madre",getIntent().getExtras().get("ident").toString());

name.setText("Доброе утро,"+getIntent().getExtras().get("name").toString()+"!");

int shop=Integer.parseInt(getIntent().getExtras().get("ident").toString());
name.animate().translationY(-1000).setDuration(2200).setStartDelay(0);
if (shop==2) {
    new Handler().postDelayed(new Runnable() {
        @Override
        public void run() {
            Intent mk = new Intent(HelloClientActivity.this, HomeActivity.class);
            startActivity(mk);
            finish();
        }
    }, 3000);


}else {


    new Handler().postDelayed(new Runnable() {
        @Override
        public void run() {
            Intent mka = new Intent(HelloClientActivity.this, ShopHomeActivity.class);
            startActivity(mka);
            finish();
        }
    }, 3000);


}









    }




}