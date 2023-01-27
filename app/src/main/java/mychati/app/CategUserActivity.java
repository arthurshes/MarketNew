package mychati.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import mychati.app.Client.ClientNameActivity;
import mychati.app.Client.ClientReg;
import mychati.app.Client.ClientShopsHolders.HomeFragment;
import mychati.app.Client.HomeActivity;
import mychati.app.Shops.ShopCategoryActivity;

public class CategUserActivity extends AppCompatActivity {
private CardView card_client,card_shop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categ_user);

        card_shop=(CardView) findViewById(R.id.card_shop);
        card_client=(CardView) findViewById(R.id.card_client);

////Временные изменения////
        card_shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shop=new Intent(CategUserActivity.this, ShopCategoryActivity.class);
                startActivity(shop);
                finish();
            }
        });
        card_client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent client=new Intent(CategUserActivity.this, ClientNameActivity.class);
                startActivity(client);
                finish();
            }
        });
///Временные изменения////
    }
}