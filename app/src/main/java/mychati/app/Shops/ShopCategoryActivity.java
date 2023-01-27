package mychati.app.Shops;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import mychati.app.R;

public class ShopCategoryActivity extends AppCompatActivity {
private TextView perekup,odejda,igrushki,cakemag,ortopedie,build,electroni,mebel,apteka;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_category);
        perekup=(TextView) findViewById(R.id.perekup);
        odejda=(TextView) findViewById(R.id.odejda);
        igrushki=(TextView) findViewById(R.id.igrushki);
        cakemag=(TextView) findViewById(R.id.cakemag);
        ortopedie=(TextView) findViewById(R.id.ortopedie);
        build=(TextView) findViewById(R.id.build);
        electroni=(TextView) findViewById(R.id.electroni);
        mebel=(TextView) findViewById(R.id.mebel);
        apteka=(TextView) findViewById(R.id.apteka);



        apteka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent apteka=new Intent(ShopCategoryActivity.this, ShopName.class);
                apteka.putExtra("category","Аптека");
                startActivity(apteka);
                finish();
            }
        });  mebel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent mebel=new Intent(ShopCategoryActivity.this,ShopName.class);
              mebel.putExtra("category","Мебельный салон");
                startActivity(mebel);
                finish();
            }
        });
        ortopedie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent orto=new Intent(ShopCategoryActivity.this,ShopName.class);
              orto.putExtra("category","Ортопедия");
                startActivity(orto);
                finish();
            }
        });
       electroni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent electronic=new Intent(ShopCategoryActivity.this,ShopName.class);
                electronic.putExtra("category","Электронника");
                startActivity(electronic);
                finish();
            }
        });
       build.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent builda=new Intent(ShopCategoryActivity.this,ShopName.class);
               builda.putExtra("category","Строительный магазин");
                startActivity(builda);
                finish();
            }
        });   cakemag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent cake=new Intent(ShopCategoryActivity.this,ShopName.class);
               cake.putExtra("category","Кондитерская");
                startActivity(cake);
                finish();
            }
        });
      odejda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent odejda=new Intent(ShopCategoryActivity.this,ShopName.class);
                odejda.putExtra("category","Одежда и обувь");
                startActivity(odejda);
                finish();
            }
        });
      igrushki.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              Intent igrushka=new Intent(ShopCategoryActivity.this,ShopName.class);
              igrushka.putExtra("category","Игрушки");
              startActivity(igrushka);
              finish();
          }
      });
     perekup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent apteka=new Intent(ShopCategoryActivity.this,ShopName.class);
                apteka.putExtra("category","перекуп");

                startActivity(apteka);
                finish();
            }
        });
    }
}