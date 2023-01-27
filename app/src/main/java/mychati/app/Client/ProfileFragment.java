package mychati.app.Client;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mychati.app.Client.ClientShopsHolders.ClientShopHolder;
import mychati.app.Client.ClientShopsModel.ShopAdapter;
import mychati.app.Client.OpisanieBottomFrag.TovarBottomOpisFragment;
import mychati.app.Client.ProfileAdapter.ProfileAdapter;
import mychati.app.Client.ProfileHolders.ProfileHolder;
import mychati.app.R;
import mychati.app.TestHolders.ParentHolder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    private AppCompatButton appCompatButtonIzmennameCli;
    private DatabaseReference MyRef;
private FirebaseRecyclerAdapter<ProfileAdapter, ProfileHolder> adapters;
    private static ProfileFragment instance=null;
    private ValueEventListener mRefUserListener;
    private HashMap <DatabaseReference,ValueEventListener> value=new HashMap<>() ;
    private List<MyListener>myListeners=new ArrayList<>();
    private RecyclerView myZakazrecrCli;
    private int status;
    private DatabaseReference zakazes;
    private int data;
    private EditText editClientNameprofile;
    private FirebaseAuth mAuth;
    RecyclerView.LayoutManager layoutManager;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }
    public static ProfileFragment newInstance(){
        if (instance==null){
            instance=new ProfileFragment();
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
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
       return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MyRef= FirebaseDatabase.getInstance().getReference().child("client");
        editClientNameprofile=view.findViewById(R.id.editClientNameprofile);
        myZakazrecrCli=view.findViewById(R.id.myZakazrecrCli);
        mAuth=FirebaseAuth.getInstance();
        layoutManager=new LinearLayoutManager(this.getContext());
        zakazes=FirebaseDatabase.getInstance().getReference().child("oformzakaz");
        myZakazrecrCli.setHasFixedSize(true);
        myZakazrecrCli.setLayoutManager(layoutManager);
        appCompatButtonIzmennameCli=view.findViewById(R.id.appCompatButtonIzmennameCli);




        mRefUserListener=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists() && snapshot.getChildrenCount() > 0) {
                    editClientNameprofile.setText(snapshot.child("clientName").getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        Query query=  MyRef.child(mAuth.getCurrentUser().getUid());
     query.addValueEventListener(mRefUserListener);
myListeners.add(new MyListener(mRefUserListener,query));










        appCompatButtonIzmennameCli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                load();
            }
        });


















        FirebaseRecyclerOptions<ProfileAdapter> options=new FirebaseRecyclerOptions.Builder<ProfileAdapter>()
                .setQuery(zakazes.orderByChild("ClientUid").equalTo(mAuth.getCurrentUser().getUid()),ProfileAdapter.class).build();
        adapters=new FirebaseRecyclerAdapter<ProfileAdapter, ProfileHolder>(options) {
            @Override
            protected void onBindViewHolder(@androidx.annotation.NonNull ProfileHolder holder, int position, @androidx.annotation.NonNull ProfileAdapter model) {
                holder.TextadressStat.setText(model.getAdress());
                data=Integer.parseInt(model.getMoth());


                holder.textDate.setHint(model.getShopId());
                holder.statuActiv.setHint(model.getProductId());


                status=Integer.parseInt(model.getProchitan());


                if (status==1){
                    holder.statuActiv.setText("Статус:не просмотрен");
                }else if (status==2){
                    holder.statuActiv.setText("Статус:просмотрен");
                }









                if (data==1){
                    holder.textDate.setText(model.getDate()+" января."+model.getHour()+":"+model.getMinute());
                }else if (data==2){
                    holder.textDate.setText(model.getDate()+" февраля."+model.getHour()+":"+model.getMinute());
                }else if (data==3){
                    holder.textDate.setText(model.getDate()+" марта."+model.getHour()+":"+model.getMinute());
                }else if (data==4){
                    holder.textDate.setText(model.getDate()+" апреля."+model.getHour()+":"+model.getMinute());
                }else if (data==5){
                    holder.textDate.setText(model.getDate()+" мая."+model.getHour()+":"+model.getMinute());
                }else if (data==6){
                    holder.textDate.setText(model.getDate()+" июня."+model.getHour()+":"+model.getMinute());
                }else if (data==7){
                    holder.textDate.setText(model.getDate()+" июля."+model.getHour()+":"+model.getMinute());
                }else if (data==8){
                    holder.textDate.setText(model.getDate()+" августа."+model.getHour()+":"+model.getMinute());
                }else if (data==9){
                    holder.textDate.setText(model.getDate()+" сентября."+model.getHour()+":"+model.getMinute());
                }else if (data==10){
                    holder.textDate.setText(model.getDate()+" октября."+model.getHour()+":"+model.getMinute());
                }else if (data==11){
                    holder.textDate.setText(model.getDate()+" ноября."+model.getHour()+":"+model.getMinute());
                }else if (data==12){
                    holder.textDate.setText(model.getDate()+" декабря."+model.getHour()+":"+model.getMinute());
                }

            }

            @Override
            public ProfileHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_zakazes,parent,false);
                ProfileHolder holder=new ProfileHolder (view);


                return holder;
            }
        };
        myZakazrecrCli.setAdapter(adapters);
        adapters.startListening();
    }

    private void load() {
        if (TextUtils.isEmpty(editClientNameprofile.getText().toString())){
            Toast.makeText(getContext(), "Введите свое имя", Toast.LENGTH_SHORT).show();
        }else{
            HashMap<String,Object>name=new HashMap<>();
            name.put("clientName",editClientNameprofile.getText().toString());
            MyRef.child(mAuth.getCurrentUser().getUid()).updateChildren(name).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(getContext(), "Изменения сохранены", Toast.LENGTH_SHORT).show();
                    }else{
                        String message = task.getException().toString();
                        Toast.makeText(getContext(), "Ошибка" + message, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }




    @Override
    public void onStop() {
        super.onStop();
        for (MyListener listener:myListeners){
            listener.unsubscribe();
        }
        adapters=null;
        myListeners.clear();
        mRefUserListener=null;
        mAuth=null;
        myZakazrecrCli=null;
        layoutManager=null;
        editClientNameprofile=null;
        appCompatButtonIzmennameCli=null;
        MyRef=null;
        zakazes=null;
    }
}