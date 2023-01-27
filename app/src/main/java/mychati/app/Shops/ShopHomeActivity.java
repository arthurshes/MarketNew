package mychati.app.Shops;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import mychati.app.R;
import mychati.app.databinding.ActivityShopHomeBinding;

public class ShopHomeActivity extends AppCompatActivity {
private FirebaseAuth mAuth;
private DatabaseReference shopRef;

    ActivityShopHomeBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_home);
        binding=ActivityShopHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mAuth=FirebaseAuth.getInstance();
        shopRef= FirebaseDatabase.getInstance().getReference().child("shops");
        Fragments(new ZakazShopopFragment());
        binding.bottomHomeShop.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.chat_shop:
                  startActivity(new Intent(ShopHomeActivity.this, NewTovarRwoTwo.class));
                  finish();
                    break;
                case R.id.zakaz_shop:
                    Fragments(new ZakazShopopFragment());
                    break;
                case R.id.shop_profile:
                 startActivity(new Intent(ShopHomeActivity.this,ShopProfileActivity.class));
                 finish();
                    break;

            }
            return true;
        });
    }
    private void Fragments(Fragment fragment){


        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_shop,fragment);
        fragmentTransaction.commit();
    }
}