package mychati.app.Shops;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mychati.app.Client.CartAdapter.CartAdapter;
import mychati.app.Client.ClientShopsHolders.ClientShopHolder;
import mychati.app.Client.ClientShopsModel.ShopAdapter;
import mychati.app.Client.MyListener;
import mychati.app.Client.OpisanieBottomFrag.TovarBottomOpisFragment;
import mychati.app.R;
import mychati.app.Shops.BottomFragShopZakInfo.bottomInfoZakazshopsFragment;
import mychati.app.Shops.ZakazAdapter.ZakazAdapter;
import mychati.app.Shops.ZakaznoyTovarHolder.ZakaznyTovarHolder;

public class ShopZakazInfoActivivty extends AppCompatActivity {
private FirebaseAuth mAuth;
private DatabaseReference zakaz;
private ImageView callshopcl;
private FirebaseRecyclerAdapter<CartAdapter, ZakaznyTovarHolder> adapters;
private AppCompatButton btnopen;
private RecyclerView recyclerView;
private ValueEventListener valueEventListenerValue;
private List<MyListener>myListeners=new ArrayList<>();
    RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_zakaz_info_activivty);
        recyclerView=(RecyclerView)findViewById(R.id.reccartshopsdops) ;
        layoutManager=new LinearLayoutManager(this);

btnopen=(AppCompatButton)findViewById(R.id.buttonopendialog) ;

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        mAuth=FirebaseAuth.getInstance();
zakaz= FirebaseDatabase.getInstance().getReference().child("oformzakaz");

//ключ заказа////
        Log.d("jip",getIntent().getExtras().get("uidc").toString()+mAuth.getCurrentUser().getUid());
        ///ключ заказа///
        valueEventListenerValue=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists() && snapshot.getChildrenCount() > 0){

                    btnopen.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            bottomInfoZakazshopsFragment bottomInfoZakazshopsFragments=bottomInfoZakazshopsFragment.newInstanceb();

                            Bundle datainf=new Bundle();
                            datainf.putString("uidClient",snapshot.child("ClientUid").getValue().toString());
                            datainf.putString("name",snapshot.child("ClientName").getValue().toString());
                            datainf.putString("keyOk",getIntent().getExtras().get("prodid").toString());
                            datainf.putString("itogPrice",snapshot.child("itogPrice").getValue().toString());
                            datainf.putString("adress",snapshot.child("adress").getValue().toString());
                            datainf.putString("podezd",snapshot.child("podezd").getValue().toString());
                            datainf.putString("lvl",snapshot.child("lvl").getValue().toString());
                            datainf.putString("kvart",snapshot.child("kvartira").getValue().toString());
                            datainf.putString("domophone",snapshot.child("domophone").getValue().toString());
                            datainf.putString("phone",snapshot.child("phone").getValue().toString());
                            bottomInfoZakazshopsFragments.setArguments(datainf);
                            bottomInfoZakazshopsFragments.show(getSupportFragmentManager(),"TAG2");


                            HashMap<String,Object>testi=new HashMap<>();
                            testi.put("Prochitan","2");
                            zakaz.child(snapshot.child("ClientUid").getValue().toString()+getIntent().getExtras().get("prodid").toString()+mAuth.getCurrentUser().getUid()).updateChildren(testi).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                }
                            });
                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        Query query=   zakaz.child(getIntent().getExtras().get("uidc").toString()+getIntent().getExtras().get("prodid").toString()+mAuth.getCurrentUser().getUid());
        query.addValueEventListener(valueEventListenerValue);
        myListeners.add(new MyListener(valueEventListenerValue,query));
        FirebaseRecyclerOptions<CartAdapter> options=new FirebaseRecyclerOptions.Builder<CartAdapter>()
                .setQuery(zakaz.child(getIntent().getExtras().get("uidc").toString()+getIntent().getExtras().get("prodid").toString()+mAuth.getCurrentUser().getUid()).child("Zakaz").orderByChild("tovarcartShopuid").equalTo(mAuth.getCurrentUser().getUid()),CartAdapter.class).build();
       adapters=new FirebaseRecyclerAdapter<CartAdapter, ZakaznyTovarHolder>(options) {
            @Override
            protected void onBindViewHolder( @androidx.annotation.NonNull ZakaznyTovarHolder holder, int position,  @androidx.annotation.NonNull CartAdapter model) {

          holder.tovarnameshop.setText(model.getTovarname());
          holder.tovarvalueshop.setText(model.getTovarValue()+"шт");
                holder.tovarpriceshop.setText(model.getPrice()+"₽");
                Transformation transformation=new RoundedTransformationBuilder().borderColor(Color.WHITE).borderWidthDp(3).cornerRadius(10).oval(false).build();


                Picasso.get().load(model.getTovarImage()).networkPolicy(NetworkPolicy.OFFLINE).transform(transformation).into(holder.tovarphotoshop, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(Exception e) {
Picasso.get().load(model.getTovarImage()).transform(transformation).into(holder.tovarphotoshop);
                    }
                });
            }

            @Override
            public ZakaznyTovarHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_zakazno_tovars,parent,false);
              ZakaznyTovarHolder holder=new ZakaznyTovarHolder(view);


                return holder;
            }
        };
        recyclerView.setAdapter(adapters);
        adapters.startListening();



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(ShopZakazInfoActivivty.this,ShopHomeActivity.class));
        finish();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        adapters.stopListening();
        adapters=null;
        for (MyListener listener:myListeners){
            listener.unsubscribe();
        }
        myListeners.clear();
        recyclerView=null;
        zakaz=null;
        mAuth=null;
        layoutManager=null;
    }
}