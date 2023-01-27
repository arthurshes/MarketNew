package mychati.app.Shops;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
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

import mychati.app.Client.MyListener;
import mychati.app.R;

public class IzmenitTovarActivity extends AppCompatActivity {
private EditText nameTovar,opisanietovar,priceTovar;
private RoundedImageView tovarImage;
private TextView odej_text_careg_izmena,textstat;
private FirebaseAuth mAuth;
private ProgressDialog progressDialog;
private ValueEventListener valueEventListener;
private List<MyListener> myListenerList=new ArrayList<>();
private DatabaseReference mytovar;
private String DownloadImageUrl;
private StorageReference photoIzmena;
private Uri ImageUri;
    private static final int GALLERYPICK = 1;
private AppCompatButton buttonizmenatovar,buttonstatyes,buttonstatno;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_izmenit_tovar);
        progressDialog=new ProgressDialog(this);
        tovarImage=(RoundedImageView) findViewById(R.id.roundedImageViewTovarIzmena);
        nameTovar=(EditText) findViewById(R.id.editnametovarIzmena);
        textstat=(TextView)findViewById(R.id.textstat);
        priceTovar=(EditText) findViewById(R.id.editpriceIzmena);
        photoIzmena= FirebaseStorage.getInstance().getReference().child("TovarImage");
        buttonizmenatovar=(AppCompatButton)findViewById(R.id.buttonizmenatovar);
        opisanietovar=(EditText) findViewById(R.id.editopisannieIzmena);
        odej_text_careg_izmena=(TextView)findViewById(R.id.odej_text_careg_izmena);
mytovar= FirebaseDatabase.getInstance().getReference().child("Tovars");
        buttonstatyes=(AppCompatButton)findViewById(R.id.buttonstatyes);
        buttonstatno=(AppCompatButton)findViewById(R.id.buttonstatno);
        mAuth=FirebaseAuth.getInstance();








        valueEventListener=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists() && snapshot.getChildrenCount() > 0) {
                    nameTovar.setText(snapshot.child("TovarName").getValue().toString());
                    priceTovar.setText(snapshot.child("TovarPrice").getValue().toString());
                    opisanietovar.setText(snapshot.child("TovarOpisanie").getValue().toString());
                    odej_text_careg_izmena.setText(snapshot.child("TovarCateg").getValue().toString());
                    textstat.setText(snapshot.child("TovarStatus").getValue().toString());
                    Transformation transformation=new RoundedTransformationBuilder().borderColor(Color.WHITE).borderWidthDp(3).cornerRadius(12).oval(false).build();


                    Picasso.get().load(snapshot.child("TovarImage").getValue().toString()).networkPolicy(NetworkPolicy.OFFLINE).transform(transformation).into(tovarImage, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError(Exception e) {
Picasso.get().load(snapshot.child("TovarImage").getValue().toString()).transform(transformation).into(tovarImage);
                        }
                    });


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        Query query= mytovar.child(getIntent().getExtras().get("ProdId").toString());
      query .addValueEventListener(valueEventListener);
      myListenerList.add(new MyListener(valueEventListener,query));



 tovarImage.setOnClickListener(new View.OnClickListener() {
     @Override
     public void onClick(View view) {
         OpenGallery();
     }
 });



        buttonizmenatovar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadIzmena();
            }
        });





        buttonstatyes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String,Object>kid=new HashMap<>();
                kid.put("TovarStatus","1");
                mytovar.child(getIntent().getExtras().get("ProdId").toString()).updateChildren(kid).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            Toast.makeText(IzmenitTovarActivity.this, "Статус изменен", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(IzmenitTovarActivity.this,ShopProfileActivity.class));


                        } else {
                            progressDialog.dismiss();
                            String message = task.getException().toString();
                            Toast.makeText(IzmenitTovarActivity.this, "Ошибка" + message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

        buttonstatno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String,Object>kid=new HashMap<>();
                kid.put("TovarStatus","0");
                mytovar.child(getIntent().getExtras().get("ProdId").toString()).updateChildren(kid).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            Toast.makeText(IzmenitTovarActivity.this, "Статус изменен", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(IzmenitTovarActivity.this,ShopProfileActivity.class));


                        } else {
                            progressDialog.dismiss();
                            String message = task.getException().toString();
                            Toast.makeText(IzmenitTovarActivity.this, "Ошибка" + message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });





















    }

    private void loadIzmena() {

        progressDialog.setTitle("загрузка данных....");
        progressDialog.setMessage("Пожалуйста подождите");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        StorageReference filePath = photoIzmena.child(ImageUri.getLastPathSegment() + ".jpg");
        final UploadTask uploadTask = filePath.putFile(ImageUri);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message = e.toString();
                Toast.makeText(IzmenitTovarActivity.this, "error" + message, Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(IzmenitTovarActivity.this, "Изображение загружено", Toast.LENGTH_SHORT).show();
                Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }
                        DownloadImageUrl = filePath.getDownloadUrl().toString();

                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                       DownloadImageUrl = task.getResult().toString();


                            SavenewInfo();
                        }


                    }
                });
            }
        });





    }

    private void SavenewInfo() {
        if (TextUtils.isEmpty(nameTovar.getText().toString())) {


            Toast.makeText(this, "Введите название товара", Toast.LENGTH_SHORT).show();
        }if (TextUtils.isEmpty(priceTovar.getText().toString())){
            Toast.makeText(this, "Введите цену товара", Toast.LENGTH_SHORT).show();
        }if (TextUtils.isEmpty(opisanietovar.getText().toString())){
            Toast.makeText(this, "Введите описание товара", Toast.LENGTH_SHORT).show();
        }else{
            HashMap<String,Object>Izmena=new HashMap<>();
            Izmena.put("TovarName",nameTovar.getText().toString());
            Izmena.put("TovarOpisanie",opisanietovar.getText().toString());
            Izmena.put("TovarPrice",priceTovar.getText().toString());
            Izmena.put("TovarImage",DownloadImageUrl);

            mytovar.child(getIntent().getExtras().get("ProdId").toString()).updateChildren(Izmena).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        progressDialog.dismiss();
                        Toast.makeText(IzmenitTovarActivity.this, "Товар изменен", Toast.LENGTH_SHORT).show();

startActivity(new Intent(IzmenitTovarActivity.this,ShopProfileActivity.class));
finish();
                    } else {
                        progressDialog.dismiss();
                        String message = task.getException().toString();
                        Toast.makeText(IzmenitTovarActivity.this, "Ошибка" + message, Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }













    }

    private void OpenGallery() {
        Intent galleryintent = new Intent();
        galleryintent.setAction(Intent.ACTION_GET_CONTENT);
        galleryintent.setType("image/*");
        startActivityForResult(galleryintent, GALLERYPICK);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERYPICK && resultCode == RESULT_OK && data != null) {
          ImageUri= data.getData();
           tovarImage.setImageURI(ImageUri);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(IzmenitTovarActivity.this,ShopProfileActivity.class));
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();

        for (MyListener listener:myListenerList){
            listener.unsubscribe();
        }
        myListenerList.clear();
        valueEventListener=null;
        ImageUri=null;
        tovarImage=null;
        mytovar=null;
        mAuth=null;
        DownloadImageUrl=null;

    }
}