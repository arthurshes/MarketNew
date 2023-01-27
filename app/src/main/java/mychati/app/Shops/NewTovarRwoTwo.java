package mychati.app.Shops;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mychati.app.Client.MyListener;
import mychati.app.R;

public class NewTovarRwoTwo extends AppCompatActivity {
    private RoundedImageView roundedImageViewTovar;
    private AppCompatButton savebutton;
    private DatabaseReference Shop;
private List<MyListener> myListenerList=new ArrayList<>();
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;
    private static final int GALLERYPICK = 1;
    private ValueEventListener valueEventListenert;
    private ValueEventListener valueEventListener;
    private Uri ImageUriTwo;
    private EditText editprice,editopisanie,editnametovar;
    private String DownloadImageUrlTwo,MyPhone,saveCurrentDate,saveCurrentTime,ProductRandomKey,MyRanKey,MyName,MyLogo;
    private StorageReference imagetovar;
    private DatabaseReference tovars;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_tovar_rwo_two);

        imagetovar= FirebaseStorage.getInstance().getReference().child("TovarImage");
        Shop= FirebaseDatabase.getInstance().getReference().child("shops");
        editnametovar=(EditText)findViewById(R.id.editnametovar);
        editprice=(EditText)findViewById(R.id.editprice);
        editopisanie=(EditText)findViewById(R.id.editopisannie);
        progressDialog = new ProgressDialog(this);
        tovars= FirebaseDatabase.getInstance().getReference().child("Tovars");
        savebutton=(AppCompatButton)findViewById(R.id.buttonsavetovar);
        roundedImageViewTovar=(RoundedImageView)findViewById(R.id.roundedImageViewTovar);

        mAuth=FirebaseAuth.getInstance();


valueEventListenert=new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        if (snapshot.exists() && snapshot.getChildrenCount() > 0) {
            MyName=snapshot.child("MagName").getValue().toString();
            MyLogo=snapshot.child("MagLogo").getValue().toString();
        }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
};
Query query= Shop.child(mAuth.getCurrentUser().getUid());
query.addValueEventListener(valueEventListenert);
myListenerList.add(new MyListener(valueEventListenert,query));


        if (mAuth.getCurrentUser().getPhoneNumber()==null){
            valueEventListener=new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists() && snapshot.getChildrenCount() > 0) {
                        MyPhone=snapshot.child("MagNumber").getValue().toString();

                        Log.d("Mavro",MyPhone);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            };
       Query query1=Shop.child(mAuth.getCurrentUser().getUid());
       query1.addValueEventListener(valueEventListener);
myListenerList.add(new MyListener(valueEventListener,query1));
        }




        roundedImageViewTovar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpneGallery();
            }
        });
        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadtovar();
            }
        });
    }
    private void loadtovar() {

        Calendar calendar=Calendar.getInstance();

        SimpleDateFormat currentDate=new SimpleDateFormat("ddMMyyyy");
        saveCurrentDate=currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime=new SimpleDateFormat("HHmmss");
        saveCurrentTime=currentTime.format(calendar.getTime());

        ProductRandomKey=saveCurrentDate+saveCurrentTime;
        MyRanKey=mAuth.getCurrentUser().getUid()+ProductRandomKey;
        progressDialog.setTitle("загрузка данных....");
        progressDialog.setMessage("Пожалуйста подождите");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();


        StorageReference filePath = imagetovar.child(ImageUriTwo.getLastPathSegment() + ".jpg");





        byte[] bytes=new byte[0];
        try {
            Bitmap bitmap= MediaStore.Images.Media.getBitmap(this.getContentResolver(),ImageUriTwo);
            ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,10,byteArrayOutputStream);
            bytes=byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }












        final UploadTask uploadTask = filePath.putBytes(bytes);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message = e.toString();
                Toast.makeText(NewTovarRwoTwo.this, "error" + message, Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText( NewTovarRwoTwo.this, "Изображение загружено", Toast.LENGTH_SHORT).show();
                Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }
                        DownloadImageUrlTwo = filePath.getDownloadUrl().toString();

                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            DownloadImageUrlTwo = task.getResult().toString();


                            SaveTovarInfo();
                        }


                    }
                });
            }
        });



    }

    private void SaveTovarInfo() {
        if (TextUtils.isEmpty(editnametovar.getText().toString())){
            Toast.makeText(this, "Введите название товара", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(editopisanie.getText().toString())){
            Toast.makeText(this, "Введите описание товара", Toast.LENGTH_SHORT).show();
        }if (TextUtils.isEmpty(editprice.getText().toString())){
            Toast.makeText(this, "Введите цену товара", Toast.LENGTH_SHORT).show();
        }else{
            HashMap<String,Object> tovarMap=new HashMap<>();
            tovarMap.put("TovarName",editnametovar.getText().toString());
            tovarMap.put("TovarOpisanie",editopisanie.getText().toString());
            tovarMap.put("MagName",MyName);
            tovarMap.put("MagLogo",MyLogo);
            tovarMap.put("TovarPrice",editprice.getText().toString());
            tovarMap.put("TovarImage",DownloadImageUrlTwo);
            tovarMap.put("ShopUid",mAuth.getCurrentUser().getUid());
            tovarMap.put("ShopPhone",mAuth.getCurrentUser().getPhoneNumber());
            tovarMap.put("ShopPhoneReg",MyPhone);
            tovarMap.put("TovarStatus","1");

            tovarMap.put("ProductTime",MyRanKey);
            tovars.child(MyRanKey).updateChildren(tovarMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        progressDialog.dismiss();
                        Toast.makeText(NewTovarRwoTwo.this, "Товар добавлен", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(NewTovarRwoTwo.this,ShopProfileActivity.class));
finish();

                    } else {
                        progressDialog.dismiss();
                        String message = task.getException().toString();
                        Toast.makeText(NewTovarRwoTwo.this, "Ошибка" + message, Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }















    }

    private void OpneGallery() {
        Intent galleryintent = new Intent();
        galleryintent.setAction(Intent.ACTION_GET_CONTENT);
        galleryintent.setType("image/*");
        startActivityForResult(galleryintent, GALLERYPICK);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERYPICK && resultCode == RESULT_OK && data != null) {
            ImageUriTwo = data.getData();
            roundedImageViewTovar.setImageURI(ImageUriTwo);
            Log.d("Hip",ImageUriTwo.toString());
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(NewTovarRwoTwo.this,ShopHomeActivity.class));
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        for (MyListener listener:myListenerList){
            listener.unsubscribe();
        }
        myListenerList.clear();
        Shop=null;
        tovars=null;
        MyLogo=null;
        MyRanKey=null;
        MyName=null;
        MyPhone=null;
        roundedImageViewTovar=null;
        progressDialog=null;savebutton=null;
        editnametovar=null;
        editopisanie=null;
        editprice=null;
        ImageUriTwo=null;
        mAuth=null;
        ProductRandomKey=null;
        saveCurrentDate=null;
        saveCurrentTime=null;
        DownloadImageUrlTwo=null;
        imagetovar=null;
    }
}