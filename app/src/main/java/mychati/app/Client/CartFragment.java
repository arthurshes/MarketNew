package mychati.app.Client;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.facebook.shimmer.ShimmerFrameLayout;
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
import com.makeramen.roundedimageview.RoundedImageView;
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
import mychati.app.Client.CartAdapter.MartAdapter;
import mychati.app.Client.CartHolder.cartHolder;
import mychati.app.Client.ClientBottomInfo.ClientInfoFromZakaz;
import mychati.app.Client.ClientShopsHolders.ClientShopHolder;
import mychati.app.Client.ClientShopsModel.ShopAdapter;
import mychati.app.Client.OpisanieBottomFrag.TovarBottomOpisFragment;
import mychati.app.R;
import mychati.app.TestHolders.ChildHolder;
import mychati.app.TestHolders.ParentHolder;


/**
 * A simple {@link Fragment} subclass.

 * create an instance of this fragment.
 */
public class CartFragment extends Fragment {


    private FirebaseRecyclerAdapter<CartAdapter, ParentHolder> adapters;
    private FirebaseRecyclerAdapter<MartAdapter, ChildHolder> adapterTwo;
    private DatabaseReference karzinaRef;
    private ShimmerFrameLayout shimmerFrameLayout;

 ///   private LottieAnimationView lottieAnimationView;
    private RoundedImageView shopimege;
    private int totalnyPrice;
    private int diablo;
    private ValueEventListener mRefUserListener;
    //   int dodo = 0;
    private int kol;
    private List<MyListener>myListeners=new ArrayList<>();
    private static CartFragment instance=null;
    private AppCompatButton buybutton;
    private int overTovar;
    private TextView textpricecarti, textkarzininziv, textViewnocart;
    private RecyclerView reccart;
    private FirebaseAuth mAuth;
    private DatabaseReference hophh;

    private ImageView deletezakazcart;
    RecyclerView.LayoutManager layoutManager;


    public static CartFragment newInstance(){
        if (instance==null){
            instance=new CartFragment();
            return instance;
        }else{
            return instance;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        reccart = view.findViewById(R.id.reccart);
        layoutManager = new LinearLayoutManager(this.getContext());


        reccart.setHasFixedSize(true);
        reccart.setLayoutManager(layoutManager);
        karzinaRef = FirebaseDatabase.getInstance().getReference().child("DoCart");


        mAuth = FirebaseAuth.getInstance();
        //   lottieAnimationView = cart.findViewById(R.id.lottieAnimationViewnottovarcart);
        textViewnocart = view.findViewById(R.id.texxtnottovercart);

        shimmerFrameLayout=view.findViewById(R.id.shiimertwo);
        shimmerFrameLayout.startShimmer();
        FirebaseRecyclerOptions<CartAdapter> options = new FirebaseRecyclerOptions.Builder<CartAdapter>()
                .setQuery(karzinaRef.child(mAuth.getCurrentUser().getUid()), CartAdapter.class).build();
        adapters = new FirebaseRecyclerAdapter<CartAdapter, ParentHolder>(options) {
            @Override
            protected void onBindViewHolder(@androidx.annotation.NonNull ParentHolder parentHolder, int position, @androidx.annotation.NonNull CartAdapter model) {


                Transformation transformation = new RoundedTransformationBuilder().borderColor(Color.WHITE).borderWidthDp(3).cornerRadius(8).oval(false).build();
                Picasso.get().load(model.getMagLogo()).networkPolicy(NetworkPolicy.OFFLINE).transform(transformation).into(parentHolder.roundedImagemaglogoparent, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(Exception e) {
                        Picasso.get().load(model.getMagLogo()).transform(transformation).into(parentHolder.roundedImagemaglogoparent);

                    }
                });
                parentHolder.buylist.setText("оформить заказ из  <<" + model.getMagName() + ">>");
                parentHolder.buylist.setHint(model.getShopUid());
                parentHolder.dodo = 0;
                parentHolder.imagedeletemag.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        karzinaRef.child(mAuth.getCurrentUser().getUid()).child(parentHolder.buylist.getHint().toString()).removeValue();
                    }
                });




                FirebaseRecyclerOptions<MartAdapter> options = new FirebaseRecyclerOptions.Builder<MartAdapter>()
                        .setQuery(karzinaRef.child(mAuth.getCurrentUser().getUid()).child(parentHolder.buylist.getHint().toString()).orderByChild("tovarcartShopuid").equalTo(parentHolder.buylist.getHint().toString()), MartAdapter.class).build();
                adapterTwo = new FirebaseRecyclerAdapter<MartAdapter, ChildHolder>(options) {
                    int soul;

                    @Override


                    protected void onBindViewHolder(@androidx.annotation.NonNull ChildHolder holder, int position, @androidx.annotation.NonNull MartAdapter model) {
                        holder.textnamechild.setText(model.getTovarname());
                        holder.textpricechildtovar.setHint(model.getFixprice());
                        holder.tovarpluschildto.setHint(model.getProductId());

                        holder.tovarcartchild.setHint(model.getPrice());
                        holder.textnamechild.setHint(model.getTovarcartShopuid());
                        Transformation transformation = new RoundedTransformationBuilder().borderColor(Color.WHITE).borderWidthDp(3).cornerRadius(15).oval(false).build();
                        Picasso.get().load(model.getTovarImage()).networkPolicy(NetworkPolicy.OFFLINE).transform(transformation).into(holder.imagetovarchild, new Callback() {
                            @Override
                            public void onSuccess() {
                                reccart.setVisibility(View.VISIBLE);
                                shimmerFrameLayout.setVisibility(View.GONE);
                                shimmerFrameLayout.stopShimmer();
                            }

                            @Override
                            public void onError(Exception e) {
                                Picasso.get().load(model.getTovarImage()).transform(transformation).into(holder.imagetovarchild);
                                reccart.setVisibility(View.VISIBLE);
                                shimmerFrameLayout.setVisibility(View.GONE);
                                shimmerFrameLayout.stopShimmer();
                            }
                        });


                        soul = Integer.valueOf(model.getPrice());



                        parentHolder.dodo = parentHolder.dodo + soul;
                        Log.d("pr", String.valueOf(parentHolder.dodo));

                        parentHolder.textitogParent.setText(""+parentHolder.dodo+"₽");

                        parentHolder.buylist.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent nmk = new Intent(getActivity(), ClientInfoFromZakaz.class);
                                nmk.putExtra("Uid", parentHolder.buylist.getHint().toString());
                                nmk.putExtra("price",parentHolder.textitogParent.getText().toString());
                                startActivity(nmk);
                                getActivity().finish();
                            }
                        });
                        mRefUserListener = new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists() && snapshot.getChildrenCount() > 0) {
                                    holder.tovarcartchild.setText(snapshot.child("TovarValue").getValue().toString());
                                    holder.textpricechildtovar.setText(snapshot.child("Price").getValue().toString() +"₽");
                                    holder.tovarminuschildto.setHint(snapshot.child("Price").getValue().toString());


                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        };

                        Query query= karzinaRef.child(mAuth.getCurrentUser().getUid()).child(holder.textnamechild.getHint().toString()).child(holder.tovarpluschildto.getHint().toString() + mAuth.getCurrentUser().getUid());
                        query.addValueEventListener(mRefUserListener);
myListeners.add(new MyListener(mRefUserListener,query));

                        ///           karzinaRef.child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                        //                  @Override
                        //                   public void onDataChange(@NonNull DataSnapshot snapshot) {
                        ///                        if (snapshot.exists() && snapshot.getChildrenCount() > 0) {

//


                        ///                       }else {
                        //                        lottieAnimationView.setVisibility(View.VISIBLE);
                        //                           textViewnocart.setVisibility(View.VISIBLE);
                        //                }
                        ///                 }

                        //               @Override
                        //                public void onCancelled(@NonNull DatabaseError error) {
//
                        //                }
                        ///           });


                        holder.tovarpluschildto.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String CurrentValue = holder.tovarcartchild.getText().toString();
                                int value = Integer.parseInt(CurrentValue);
                                value++;
                                holder.tovarcartchild.setText(String.valueOf(value));
                                int pricInt = Integer.valueOf(holder.textpricechildtovar.getHint().toString());
                                int valInt = Integer.valueOf(holder.tovarcartchild.getHint().toString());
                                int itogov = valInt + pricInt;
                                parentHolder.dodo = parentHolder.dodo + pricInt;
                                Log.d("pr", String.valueOf(parentHolder.dodo));
                                String iogovStr = String.valueOf(itogov);
                                HashMap<String, Object> hip = new HashMap<>();
                                hip.put("TovarValue", holder.tovarcartchild.getText().toString());
                                hip.put("Price", iogovStr);
                                karzinaRef.child(mAuth.getCurrentUser().getUid()).child(holder.textnamechild.getHint().toString()).child(holder.tovarpluschildto.getHint().toString() + mAuth.getCurrentUser().getUid()).updateChildren(hip).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {


                                        } else {
                                            String message = task.getException().toString();
                                            Toast.makeText(getContext(), "РћС€РёР±РєР°" + message, Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        });


                        holder.tovarminuschildto.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String CurrentValue = holder.tovarcartchild.getText().toString();
                                int value = Integer.parseInt(CurrentValue);
                                value--;
                                holder.tovarcartchild.setText(String.valueOf(value));


                                int pricInt = Integer.valueOf(holder.textpricechildtovar.getHint().toString());
                                int valInt = Integer.valueOf(holder.tovarcartchild.getHint().toString());
                                int itogov = valInt - pricInt;
                                parentHolder.dodo = parentHolder.dodo - pricInt;
                                String iogovStr = String.valueOf(itogov);

                                Log.d("pr", String.valueOf(parentHolder.dodo));
                                parentHolder.textitogParent.setText(String.valueOf(parentHolder.dodo));
                                HashMap<String, Object> hip = new HashMap<>();
                                hip.put("TovarValue", holder.tovarcartchild.getText().toString());
                                hip.put("Price", iogovStr);
                                karzinaRef.child(mAuth.getCurrentUser().getUid()).child(holder.textnamechild.getHint().toString()).child(holder.tovarpluschildto.getHint().toString() + mAuth.getCurrentUser().getUid()).updateChildren(hip).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {

                                        } else {
                                            String message = task.getException().toString();
                                            Toast.makeText(getContext(), "РћС€РёР±РєР°" + message, Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        });


                    }

                    @Override
                    public ChildHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.child_item, parent, false);
                        ChildHolder holder = new ChildHolder(view);
                        return holder;
                    }
                };
                parentHolder.nestedrecer.setAdapter(adapterTwo);
                adapterTwo.startListening();


            }

            @Override
            public ParentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
                ParentHolder holder = new ParentHolder(view);
                return holder;
            }
        };
        reccart.setAdapter(adapters);
        adapters.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        for (MyListener listener:myListeners){
            listener.unsubscribe();
        }
        shimmerFrameLayout=null;
        reccart=null;
        layoutManager=null;
        karzinaRef=null;
        mRefUserListener=null;
        myListeners.clear();
        mAuth=null;

        if (adapters!=null){
            adapters.stopListening();
        }
        adapters=null;
        if (adapterTwo!=null){
            adapterTwo.stopListening();
        }
        adapterTwo=null;
        textViewnocart=null;
    }

}