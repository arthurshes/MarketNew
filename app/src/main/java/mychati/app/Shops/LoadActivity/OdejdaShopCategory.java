package mychati.app.Shops.LoadActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import mychati.app.R;
import mychati.app.Shops.NewTovarRwoTwo;

public class OdejdaShopCategory extends AppCompatActivity {
private TextView odej_text_man,odej_text_woman;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_shop_category);
        odej_text_man=(TextView) findViewById(R.id.odej_text_man);
        odej_text_woman=(TextView) findViewById(R.id.odej_text_woman);


        odej_text_woman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent woman=new Intent(OdejdaShopCategory.this, NewTovarRwoTwo.class);
                woman.putExtra("category","Женское");
                startActivity(woman);
            }
        });
        odej_text_man.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent man=new Intent(OdejdaShopCategory.this, NewTovarRwoTwo.class);
                man.putExtra("category","Мужское");
                startActivity(man);
            }
        });
    }
}