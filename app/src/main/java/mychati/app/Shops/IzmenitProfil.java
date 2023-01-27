package mychati.app.Shops;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import mychati.app.Client.MyListener;
import mychati.app.R;

public class IzmenitProfil extends AppCompatActivity {
    private CircleImageView myphoto;
    private EditText myname;
    private TextView saveizmen;
    private DatabaseReference shops;
    private String DownloadUrl;
    private StorageReference magPhoto;
    private ProgressDialog progressDialog;
    private ValueEventListener valueEventListenerProfile;
    private List<MyListener>myListeners=new ArrayList<>();
    private Uri imageUri;
    private static final int GALLERYPICK = 1;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_izmenit_profil);
        myname = (EditText) findViewById(R.id.editmynames);
        myphoto = (CircleImageView) findViewById(R.id.myphoto);
        saveizmen = (TextView) findViewById(R.id.textsaveizmen);
        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        magPhoto = FirebaseStorage.getInstance().getReference().child("LogoMagazine");
        shops = FirebaseDatabase.getInstance().getReference().child("shops");









        valueEventListenerProfile=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists() && snapshot.getChildrenCount() > 0) {
                    myname.setText(snapshot.child("MagName").getValue().toString());

                    Picasso.get().load(snapshot.child("MagLogo").getValue().toString()).networkPolicy(NetworkPolicy.OFFLINE).into(myphoto, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError(Exception e) {
Picasso.get().load(snapshot.child("MagLogo").getValue().toString()).into(myphoto);
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        Query query= shops.child(mAuth.getCurrentUser().getUid());
       query.addValueEventListener(valueEventListenerProfile);
       myListeners.add(new MyListener(valueEventListenerProfile,query));
        saveizmen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveIzmen();
            }
        });
        myphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenGallery();
            }
        });


    }

    private void OpenGallery() {
        Intent galleryintent = new Intent();
        galleryintent.setAction(Intent.ACTION_GET_CONTENT);
        galleryintent.setType("image/*");
        startActivityForResult(galleryintent, GALLERYPICK);

    }

    private void SaveIzmen() {

        progressDialog.setTitle("загрузка данных....");
        progressDialog.setMessage("Пожалуйста подождите");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        StorageReference filePath = magPhoto.child(imageUri.getLastPathSegment() + ".jpg");
        final UploadTask uploadTask = filePath.putFile(imageUri);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message = e.toString();
                Toast.makeText(IzmenitProfil.this, "error" + message, Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(IzmenitProfil.this, "Изображение загружено", Toast.LENGTH_SHORT).show();
                Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }
                        DownloadUrl = filePath.getDownloadUrl().toString();

                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            DownloadUrl = task.getResult().toString();


                            SavenewInfo();
                        }


                    }
                });
            }
        });

    }

    private void SavenewInfo() {


        if (TextUtils.isEmpty(myname.getText().toString())) {


            Toast.makeText(this, "Введите название магазина", Toast.LENGTH_SHORT).show();
        } else {
            HashMap<String,Object>Izmena=new HashMap<>();
            Izmena.put("MagName",myname.getText().toString());
            Izmena.put("MagLogo",DownloadUrl);
              shops.child(mAuth.getCurrentUser().getUid()).updateChildren(Izmena).addOnCompleteListener(new OnCompleteListener<Void>() {
                  @Override
                  public void onComplete(@NonNull Task<Void> task) {
                      if (task.isSuccessful()) {
                          progressDialog.dismiss();
                          Toast.makeText(IzmenitProfil.this, "Изменения сохранены", Toast.LENGTH_SHORT).show();
startActivity(new Intent(IzmenitProfil.this,ShopHomeActivity.class) );
finish();

                      } else {
                          progressDialog.dismiss();
                          String message = task.getException().toString();
                          Toast.makeText(IzmenitProfil.this, "Ошибка" + message, Toast.LENGTH_SHORT).show();
                      }
                  }
              });

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERYPICK && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            myphoto.setImageURI(imageUri);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(IzmenitProfil.this,ShopHomeActivity.class));
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        for (MyListener listener:myListeners){
            listener.unsubscribe();
        }
        myListeners.clear();
        valueEventListenerProfile=null;
        progressDialog=null;
        DownloadUrl=null;
        imageUri=null;
        shops=null;
        mAuth=null;
        myphoto=null;
        myname=null;
        saveizmen=null;
        magPhoto=null;
    }
}