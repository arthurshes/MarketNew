package mychati.app.Client;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import mychati.app.Client.ClientShopsHolders.HomeFragment;
import mychati.app.R;
import mychati.app.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity {
///ActivityHomeBinding binding;
private BottomNavigationView bottomNavigationView;
private HomeFragment homeFragment=HomeFragment.newInstance();
private CartFragment cartFragment=CartFragment.newInstance();
private ProfileFragment profileFragment=ProfileFragment.newInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
      //  binding=ActivityHomeBinding.inflate(getLayoutInflater());
      // setContentView(binding.getRoot());
       bottomNavigationView=findViewById(R.id.bottom_home);
       bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
           @Override
           public boolean onNavigationItemSelected(@NonNull MenuItem item) {
               switch (item.getItemId()){
                   case R.id.home:
                       getSupportFragmentManager().beginTransaction().replace(R.id.frame,homeFragment).commit();
                       return true;
                   case R.id.cart:
                       getSupportFragmentManager().beginTransaction().replace(R.id.frame,cartFragment).commit();
                       return true;
                   case R.id.profil:
                       getSupportFragmentManager().beginTransaction().replace(R.id.frame,profileFragment).commit();
                       return true;
               }
               return false;
           }
       });
     if (savedInstanceState==null){
         getSupportFragmentManager().beginTransaction().replace(R.id.frame,homeFragment).commit();
      }



//binding.bottomHome.setOnItemSelectedListener(item -> {
//switch (item.getItemId()){
 //  case R.id.home:
//Fragments(new HomeFragment());
 //       break;
 //  case R.id.cart:
 //    Fragments(new CartFragment());
  //     break;
  // case R.id.profil:
   //    Fragments(new ProfileFragment());
   //     break;

//}
//return true;
//});
    }
   // private void Fragments(Fragment fragment){


      // FragmentManager fragmentManager=getSupportFragmentManager();
     //   FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
     // fragmentTransaction.replace(R.id.frame,fragment);
     //  fragmentTransaction.commit();
 ///  }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        homeFragment.onDestroy();
        cartFragment.onDestroy();
        profileFragment.onDestroy();
        bottomNavigationView=null;
        profileFragment=null;
        cartFragment=null;
        homeFragment=null;
    }
}