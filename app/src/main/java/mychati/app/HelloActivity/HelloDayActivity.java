package mychati.app.HelloActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

import mychati.app.Client.HomeActivity;
import mychati.app.R;
import mychati.app.Shops.ShopHomeActivity;

public class HelloDayActivity extends AppCompatActivity {
LottieAnimationView lottieAnimationView;
private TextView hello;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_day);
        lottieAnimationView=findViewById(R.id.lottieday);
        hello=(TextView) findViewById(R.id.texthelloday);

        hello.setText("Добрый день,"+getIntent().getExtras().get("name").toString()+"!");


int shop=Integer.parseInt(getIntent().getExtras().get("ident").toString());
       hello.animate().translationY(500).setDuration(2200).setStartDelay(0);
if (shop==2) {
    new Handler().postDelayed(new Runnable() {
        @Override
        public void run() {
            Intent mk = new Intent(HelloDayActivity.this, HomeActivity.class);
            startActivity(mk);
            finish();
        }
    }, 3000);
}else{
    new Handler().postDelayed(new Runnable() {
        @Override
        public void run() {
            Intent mka = new Intent(HelloDayActivity.this, ShopHomeActivity.class);
            startActivity(mka);
            finish();
        }
    }, 3000);
}
    }
}