package mychati.app.Client.OpisanieBottomFrag;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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

import mychati.app.Client.ClientTovarsAdaoter.Docart;
import mychati.app.Client.MyListener;
import mychati.app.Client.TovarActivity;
import mychati.app.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TovarBottomOpisFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TovarBottomOpisFragment extends BottomSheetDialogFragment {
    private String Productid,UidShop,MagLogo,MagName;
    private TextView textopis,textprice,textname,txtPlus,txtMinus,textValue,texttovarno,texttovaryes;
    private FirebaseAuth mAuth;
    private List<MyListener>myListeners=new ArrayList<>();
private static TovarBottomOpisFragment instance=null;
private  String dollar;
    private RoundedImageView imageTovar;
    private static int PHONE_NUMBER=0;
    private ValueEventListener valueEventListenerTwo;
    private ValueEventListener valueEventListener;
    private DatabaseReference.CompletionListener completionListener;
    private DatabaseReference docart;

String priem;

private AppCompatButton pluscarzin,plusdocart;
    private DatabaseReference tovar;
    View vb;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TovarBottomOpisFragment() {
        // Required empty public constructor
    }
    public static TovarBottomOpisFragment newInstance(){
if (instance==null){
    instance=new TovarBottomOpisFragment();
    return instance;
}else{
    return instance;
}
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TovarBottomOpisFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TovarBottomOpisFragment newInstance(String param1, String param2) {
        TovarBottomOpisFragment fragment = new TovarBottomOpisFragment();
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
        vb = inflater.inflate(R.layout.fragment_tovar_bottom_opis, container, false);



Productid=getArguments().getString("ProdId");
UidShop=getArguments().getString("Uid");
tovar= FirebaseDatabase.getInstance().getReference().child("Tovars");

       docart=FirebaseDatabase.getInstance().getReference().child("DoCart");
textopis=vb.findViewById(R.id.textopisalser);

txtPlus=vb.findViewById(R.id.tovarpluscart);
txtMinus=vb.findViewById(R.id.tovarminuscart);
textValue=vb.findViewById(R.id.tovarcartfrag);
texttovarno=vb.findViewById(R.id.texttovarstatnonalbotfrag);
texttovaryes=vb.findViewById(R.id.texttovarstatyesnalbotfrag);
textprice=vb.findViewById(R.id.textopisprice);
mAuth=FirebaseAuth.getInstance();
        plusdocart=vb.findViewById(R.id.plusdocart);


textname=vb.findViewById(R.id.textnazopiser);
imageTovar=vb.findViewById(R.id.roundedImageViewOpiser);



valueEventListenerTwo=new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        if (snapshot.exists() && snapshot.getChildrenCount() > 0) {
            textopis.setText(snapshot.child("TovarOpisanie").getValue().toString());
            textname.setText(snapshot.child("TovarName").getValue().toString());
            textname.setHint(snapshot.child("TovarPrice").getValue().toString());
            textprice.setText(snapshot.child("TovarPrice").getValue().toString() + "₽");
            textopis.setHint(snapshot.child("ShopUid").getValue().toString());
            txtMinus.setHint(snapshot.child("TovarPrice").getValue().toString());
            textprice.setHint(snapshot.child("TovarImage").getValue().toString());
            MagLogo = snapshot.child("MagLogo").getValue().toString();
            MagName = snapshot.child("MagName").getValue().toString();
            String sa = "1";
            int nak1 = Integer.parseInt(snapshot.child("TovarStatus").getValue().toString());
            int nak2 = Integer.parseInt(sa);


            if (nak1 == nak2) {
                texttovaryes.setVisibility(View.VISIBLE);
                plusdocart.setEnabled(true);
            } else {
                texttovarno.setVisibility(View.VISIBLE);
                plusdocart.setVisibility(View.INVISIBLE);
                plusdocart.setEnabled(false);
                txtMinus.setEnabled(false);
                txtPlus.setEnabled(false);
            }


            Transformation transformation = new RoundedTransformationBuilder().borderColor(Color.WHITE).borderWidthDp(3).cornerRadius(20).oval(false).build();


            Picasso.get().load(snapshot.child("TovarImage").getValue().toString()).networkPolicy(NetworkPolicy.OFFLINE).transform(transformation).into(imageTovar, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError(Exception e) {
Picasso.get().load(snapshot.child("TovarImage").getValue().toString()).transform(transformation).into(imageTovar);
                }
            });
        }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
};

        Query querys=tovar.child(Productid);
        querys.addValueEventListener(valueEventListenerTwo);
      myListeners.add(new MyListener(valueEventListenerTwo,querys));



valueEventListener=new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        if (snapshot.exists() && snapshot.getChildrenCount() > 0) {
            String si=snapshot.child("TovarValue").getValue().toString();
  textValue.setText(si);

  priem=snapshot.child("TovarValue").getValue().toString();

            String s="0";
            int n1=Integer.parseInt(snapshot.child("TovarValue").getValue().toString());
            int n2=Integer.parseInt(s);


            if (n1==n2){
                txtMinus.setEnabled(false);
                docart.child(mAuth.getCurrentUser().getUid()).child(UidShop).child(Productid+mAuth.getCurrentUser().getUid()).removeValue();
            }else{
                txtMinus.setEnabled(true);
            }
        }
    }
    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
};

      Query query=  docart.child(mAuth.getCurrentUser().getUid()).child(UidShop).child(Productid+mAuth.getCurrentUser().getUid());
      query.addValueEventListener(valueEventListener);
        myListeners.add(new MyListener(valueEventListener,query));













txtPlus.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        String  CurrentValue=textValue.getText().toString();
        int value=Integer.parseInt(CurrentValue);
        value++;
     textValue.setText(String.valueOf(value));
        int   test=Integer.valueOf(textValue.getText().toString());
        int   priceint=Integer.valueOf(txtMinus.getHint().toString());
         dollar=String.valueOf(test*priceint);
        HashMap<String,Object> docarty=new HashMap<>();
        docarty.put("TovarValue",textValue.getText().toString());
        docarty.put("Price",dollar);

       docart.child(mAuth.getCurrentUser().getUid()).child(UidShop).child(Productid+mAuth.getCurrentUser().getUid()).updateChildren(docarty).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                } else {
                }
            }
        });
    }
});
txtMinus.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        String  CurrentValue=textValue.getText().toString();
        int value=Integer.parseInt(CurrentValue);
        value--;
        textValue.setText(String.valueOf(value));
     int   test=Integer.valueOf(textValue.getText().toString());
      int   priceint=Integer.valueOf(txtMinus.getHint().toString());
     dollar=String.valueOf(test*priceint);
        HashMap<String,Object> docarty=new HashMap<>();
        docarty.put("TovarValue",textValue.getText().toString());
        docarty.put("Price",dollar);
        docart.child(mAuth.getCurrentUser().getUid()).child(UidShop).child(Productid+mAuth.getCurrentUser().getUid()).updateChildren(docarty).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                } else {
                }
            }
        });
    }
});
Log.d("Jp",textValue.getText().toString());
Log.d("Jp",priem+"priemstring");
plusdocart.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        HashMap<String,Object>newtovar=new HashMap<>();
    newtovar.put("MagName",MagName);
        newtovar.put("MagLogo",MagLogo);

       newtovar.put("ShopUid",UidShop);

        newtovar.put("MyUID",mAuth.getCurrentUser().getUid());

     docart.child(mAuth.getCurrentUser().getUid()).child(UidShop).updateChildren(newtovar).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
HashMap<String,Object>twos=new HashMap<>();
                   twos.put("tovarname",textname.getText().toString());
                    twos.put("MyUID",mAuth.getCurrentUser().getUid());
                    twos.put("tovarcartShopuid",UidShop);
                    if (dollar==null){
                        twos.put("Price",textname.getHint().toString());
                    }
                    twos.put("TovarValue",textValue.getText().toString());
                    twos.put("Fixprice",textname.getHint().toString());
                    twos.put("tovarImage",textprice.getHint().toString());
                    twos.put("ProductId",Productid);
                   docart.child(mAuth.getCurrentUser().getUid()).child(UidShop).child(Productid+mAuth.getCurrentUser().getUid()).updateChildren(twos).addOnCompleteListener(new OnCompleteListener<Void>() {
                       @Override
                       public void onComplete(@NonNull Task<Void> task) {

                       }
                   });

                } else {


                }
            }
        });
    }
});
return vb;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        for (MyListener listener:myListeners){
            listener.unsubscribe();
        }

        MagName=null;
        MagLogo=null;
        Productid=null;
        UidShop=null;
        textprice=null;
        textname=null;
        textopis=null;
        priem=null;
        imageTovar=null;
        vb=null;
        texttovaryes=null;
        texttovarno=null;
        plusdocart=null;
        docart=null;
        tovar=null;
        mAuth=null;
        valueEventListenerTwo=null;
        valueEventListener=null;
myListeners.clear();
    }
}