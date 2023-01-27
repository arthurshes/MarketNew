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

public class HelloVecherActivity extends AppCompatActivity {
LottieAnimationView lottieAnimationView;
private TextView vecherhello;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_vecher);
        lottieAnimationView=findViewById(R.id.vecheranim);
        vecherhello=(TextView) findViewById(R.id.texthellovecher);
        vecherhello.setText("Добрый вечер,"+getIntent().getExtras().get("name").toString()+"!");
        vecherhello.animate().translationY(500).setDuration(2200).setStartDelay(0);


        int shop=Integer.parseInt(getIntent().getExtras().get("ident").toString());








        if (shop==2) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent mk = new Intent(HelloVecherActivity.this, HomeActivity.class);
                    startActivity(mk);
                    finish();
                }
            }, 4000);
        }else{
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent mka = new Intent(HelloVecherActivity.this, ShopHomeActivity.class);
                    startActivity(mka);
                    finish();
                }
            }, 4000);
        }





















    }
}