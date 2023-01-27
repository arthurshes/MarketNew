package mychati.app.Shops.BottomFragShopZakInfo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mychati.app.Client.ClientBottomInfo.ClientInfoFromZakaz;
import mychati.app.Client.MyListener;
import mychati.app.Client.OpisanieBottomFrag.TovarBottomOpisFragment;
import mychati.app.R;
import mychati.app.Shops.ShopHomeActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link bottomInfoZakazshopsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class bottomInfoZakazshopsFragment extends BottomSheetDialogFragment {
View hl;
private TextView kvartira,podezd,lvl,adress,domophone,phone,name,itogpricetx;
private AppCompatButton pozvon,otpravil;
private FirebaseAuth mAuth;
    private static bottomInfoZakazshopsFragment instance=null;
private String varnevar;
private DatabaseReference myName;
private ValueEventListener valueEventListener;
private List<MyListener> myListenerList=new ArrayList<>();
private DatabaseReference zakazRef;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public bottomInfoZakazshopsFragment() {
        // Required empty public constructor
    }
    public static bottomInfoZakazshopsFragment newInstanceb(){
        if (instance==null){
            instance=new bottomInfoZakazshopsFragment();
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
     * @return A new instance of fragment bottomInfoZakazshopsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static bottomInfoZakazshopsFragment newInstance(String param1, String param2) {
        bottomInfoZakazshopsFragment fragment = new bottomInfoZakazshopsFragment();
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
   hl= inflater.inflate(R.layout.fragment_bottom_info_zakazshops, container, false);
   kvartira=hl.findViewById(R.id.kvartiratextshop);
   podezd=hl.findViewById(R.id.textpodezd);
   adress=hl.findViewById(R.id.textadressshop);
   lvl=hl.findViewById(R.id.lvltextshop);
   domophone=hl.findViewById(R.id.domophonetextsho);
   itogpricetx=hl.findViewById(R.id.textitogprice);
   myName=FirebaseDatabase.getInstance().getReference().child("shops");
   phone=hl.findViewById(R.id.contactsphone);
   zakazRef= FirebaseDatabase.getInstance().getReference().child("oformzakaz");
        otpravil=hl.findViewById(R.id.otpravil);
        pozvon=hl.findViewById(R.id.pozvon);
   name=hl.findViewById(R.id.clientnametext);
   adress.setText(getArguments().getString("adress"));
   podezd.setText(getArguments().getString("podezd")+"Подъезд,");
   kvartira.setText(getArguments().getString("kvart")+"Квартира");
   itogpricetx.setText(getArguments().getString("itogPrice"));
   domophone.setText("Домофон:"+getArguments().getString("domophone"));
   lvl.setText(getArguments().getString("lvl")+"Этаж,");
   phone.setText(getArguments().getString("phone"));
name.setText("Имя клиента:"+getArguments().getString("name"));


mAuth=FirebaseAuth.getInstance();







valueEventListener=new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        varnevar=snapshot.child("MagName").getValue().toString();
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
};
        Query query= myName.child(mAuth.getCurrentUser().getUid());
       query.addValueEventListener(valueEventListener);
myListenerList.add(new MyListener(valueEventListener,myName));

        otpravil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String,Object>otpravil=new HashMap<>();
                otpravil.put("Zakazstatus",getArguments().getString("uidClient"));
                otpravil.put("ZaverName",varnevar);

                zakazRef.child(getArguments().getString("uidClient")+getArguments().getString("keyOk")+mAuth.getCurrentUser().getUid()).updateChildren(otpravil).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {

                            Toast.makeText(getContext(), "Заказ завершен", Toast.LENGTH_SHORT).show();
startActivity(new Intent(getContext(), ShopHomeActivity.class));
getActivity().finish();
                        } else {

                            String message = task.getException().toString();
                            Toast.makeText(getContext(), "Ошибка" + message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

   pozvon.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View view) {
           Intent mk=new Intent(Intent.ACTION_DIAL);
           mk.setData(Uri.parse("tel:"+getArguments().getString("phone")));
           startActivity(mk);
       }
   });





   return hl;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        for (MyListener listener:myListenerList){
            listener.unsubscribe();
        }
        myListenerList.clear();
        varnevar=null;
        myName=null;
        zakazRef=null;
    }
}