package mychati.app.Shops;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import mychati.app.Client.ClientShopsHolders.ClientShopHolder;
import mychati.app.Client.ClientShopsModel.ShopAdapter;
import mychati.app.Client.ClientTovarsAdaoter.TovarsAdapter;
import mychati.app.R;
import mychati.app.Shops.MyTovarHolder.MyTovarHolder;

public class ShopProfileActivity extends AppCompatActivity {
    private CircleImageView profileImage;
    private DatabaseReference MyRef;
    private TextView textnamemag;
    private DatabaseReference MyTovar;
    private ImageView karandash;
    private ValueEventListener valueEventListener;
    private HashMap<DatabaseReference,ValueEventListener>hashMap=new HashMap<>();
    private FirebaseRecyclerAdapter<TovarsAdapter, MyTovarHolder> adapters;
    private FirebaseAuth mAuth;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_profile);

        profileImage=(CircleImageView) findViewById(R.id.imageprofile);
        textnamemag=(TextView) findViewById(R.id.textname);

        karandash=(ImageView) findViewById(R.id.imageimenitlogoz);
        mAuth=FirebaseAuth.getInstance();
        MyTovar= FirebaseDatabase.getInstance().getReference().child("Tovars");
        MyRef= FirebaseDatabase.getInstance().getReference().child("shops");

        recyclerView=(RecyclerView) findViewById(R.id.recmytovar);
        layoutManager=new GridLayoutManager(this,2);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);





    valueEventListener=new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            if (snapshot.exists() && snapshot.getChildrenCount() > 0) {
                textnamemag.setText(snapshot.child("MagName").getValue().toString());



                Picasso.get().load(snapshot.child("MagLogo").getValue().toString()).networkPolicy(NetworkPolicy.OFFLINE).into(profileImage, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(Exception e) {
Picasso.get().load(snapshot.child("MagLogo").getValue().toString()).into(profileImage);
                    }
                });

            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };

        MyRef.child(mAuth.getCurrentUser().getUid()).addValueEventListener( valueEventListener);
        hashMap.put(MyRef,valueEventListener);

        karandash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nh=new Intent(ShopProfileActivity.this,IzmenitProfil.class);
                startActivity(nh);
                finish();
            }
        });



    }
    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<TovarsAdapter> options=new FirebaseRecyclerOptions.Builder<TovarsAdapter>()
        .setQuery(MyTovar.orderByChild("ShopUid").equalTo(mAuth.getCurrentUser().getUid()),TovarsAdapter.class).build();
       adapters=new FirebaseRecyclerAdapter<TovarsAdapter, MyTovarHolder>(options) {
            @Override
            protected void onBindViewHolder(@androidx.annotation.NonNull MyTovarHolder holder, int position, @androidx.annotation.NonNull TovarsAdapter model) {

               holder.textMytovarname.setText(model.getTovarName());
               holder.textmytovarPrice.setText(model.getTovarPrice());
               holder.textmytovarPrice.setHint(model.getProductTime());
               holder.textMytovarname.setHint(model.getTovarStatus());
                Transformation transformation=new RoundedTransformationBuilder().borderColor(Color.WHITE).borderWidthDp(3).cornerRadius(20).oval(false).build();


                Picasso.get().load(model.getTovarImage()).networkPolicy(NetworkPolicy.OFFLINE).transform(transformation).into(holder.myTovarImage, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(Exception e) {
Picasso.get().load(model.getTovarImage()).transform(transformation).into(holder.myTovarImage);
                    }
                });


                String s="1";
                int n1=Integer.parseInt(model.getTovarStatus());
                int n2=Integer.parseInt(s);


                if (n2==n1){
                    holder.txtStatusyes.setVisibility(View.VISIBLE);
                }else{
                    holder.txtStatusno.setVisibility(View.VISIBLE);
                }










                holder.myTovarCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent tov=new Intent(ShopProfileActivity.this,IzmenitTovarActivity.class);
                        tov.putExtra("ProdId",holder.textmytovarPrice.getHint().toString());
                        startActivity(tov);
                        finish();
                    }
                });

String status="В наличии";


if (holder.textmytovarPrice.getHint().toString()==status){
    Toast.makeText(ShopProfileActivity.this, "efefef", Toast.LENGTH_SHORT).show();
}


            }

            @Override
            public MyTovarHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mytovar,parent,false);
               MyTovarHolder holder=new MyTovarHolder(view);


                return holder;
            }
        };
        recyclerView.setAdapter(adapters);
        adapters.startListening();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(ShopProfileActivity.this,ShopHomeActivity.class));
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        adapters.stopListening();

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        adapters.stopListening();
        adapters=null;
        recyclerView=null;
        MyTovar=null;
        mAuth=null;
        layoutManager=null;
        MyRef=null;
        hashMap.clear();
        hashMap=null;
        valueEventListener=null;
        karandash=null;
    }
}