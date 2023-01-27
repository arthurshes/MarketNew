package mychati.app.Shops;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import mychati.app.Client.ClientShopsHolders.ClientShopHolder;
import mychati.app.Client.ClientShopsModel.ShopAdapter;
import mychati.app.R;
import mychati.app.Shops.ZakazAdapter.ZakazAdapter;
import mychati.app.Shops.ZakazHolder.ZakazHolders;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ZakazShopopFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ZakazShopopFragment extends Fragment {
    private FirebaseAuth mAuth;


    private RecyclerView recyclerView;
private DatabaseReference zakazy;
    RecyclerView.LayoutManager layoutManager;
private FirebaseRecyclerAdapter<ZakazAdapter, ZakazHolders> adapters;





View vbj;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ZakazShopopFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ZakazShopopFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ZakazShopopFragment newInstance(String param1, String param2) {
        ZakazShopopFragment fragment = new ZakazShopopFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
    vbj= inflater.inflate(R.layout.fragment_zakaz_shopop, container, false);

    zakazy= FirebaseDatabase.getInstance().getReference().child("oformzakaz");
    mAuth=FirebaseAuth.getInstance();
    recyclerView=vbj.findViewById(R.id.reczakazov);
        layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);





















        FirebaseRecyclerOptions<ZakazAdapter> options=new FirebaseRecyclerOptions.Builder<ZakazAdapter>()
                .setQuery(zakazy
        .orderByChild("shopId").equalTo(mAuth.getCurrentUser().getUid()),ZakazAdapter.class).build();
       adapters=new FirebaseRecyclerAdapter<ZakazAdapter, ZakazHolders>(options) {
           @Override
           protected void onBindViewHolder( @androidx.annotation.NonNull ZakazHolders holder, int position,  @androidx.annotation.NonNull ZakazAdapter model) {

holder.price.setText(model.getItogPrice());
holder.phone.setText("Контактный номер "+model.getPhone());
holder.phone.setHint(model.getClientUid());
holder.price.setHint(model.getProductId());



holder.card.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent mn=new Intent(getActivity(),ShopZakazInfoActivivty.class);
        mn.putExtra("uidc",holder.phone.getHint().toString());
        mn.putExtra("prodid",holder.price.getHint().toString());
        startActivity(mn);
        getActivity().finish();

    }
});














           }

           @Override
            public ZakazHolders onCreateViewHolder(ViewGroup parent, int viewType) {
               View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_zakaz_shopfor,parent,false);
             ZakazHolders holder=new ZakazHolders(view);


               return holder;
           }
        };
       recyclerView.setAdapter(adapters);
       adapters.startListening();










    return vbj;
    }



    @Override
    public void onStop() {
        super.onStop();
        adapters.stopListening();
        adapters=null;
        layoutManager=null;

        mAuth=null;
        recyclerView=null;
        zakazy=null;
    }
}