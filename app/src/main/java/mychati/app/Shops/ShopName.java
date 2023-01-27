package mychati.app.Shops;

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import mychati.app.R;

public class ShopName extends AppCompatActivity {
    private TextView categ_mag,number_text;
    private Uri ImageUriTwo;
    private String DownloadImageUrlTwo,categ;
    private StorageReference magPhoto;
    private String number;
    private EditText edit_city, edit_adress, edit_number, edit_magname;
    private RoundedImageView ImageMagLogo;
    private AppCompatButton button_magreg;
    private DatabaseReference magazine;

    private ProgressDialog progressDialog;
    private static final int GALLERYPICK = 1;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_name);
        categ_mag = (TextView) findViewById(R.id.categ_mag);
        number_text=(TextView)findViewById(R.id.number_text) ;
        button_magreg = (AppCompatButton) findViewById(R.id.button_magreg);
        edit_adress = (EditText) findViewById(R.id.edit_adress);

        edit_city = (EditText) findViewById(R.id.edit_city);
        edit_number = (EditText) findViewById(R.id.edit_number);
        progressDialog = new ProgressDialog(this);

        edit_magname = (EditText) findViewById(R.id.edit_magname);
        magPhoto = FirebaseStorage.getInstance().getReference().child("LogoMagazine");
        mAuth = FirebaseAuth.getInstance();

        ImageMagLogo = (RoundedImageView) findViewById(R.id.ImageMagLogo);
        magazine = FirebaseDatabase.getInstance().getReference().child("shops");










categ=getIntent().getExtras().get("category").toString();




categ_mag.setText(getIntent().getExtras().get("category").toString());





///if (mAuth.getCurrentUser().getPhoneNumber().isEmpty()){


///}else{
   edit_number.setText(mAuth.getCurrentUser().getPhoneNumber());

///}




        ImageMagLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenGallery();
            }
        });

        button_magreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadMagInfo();
            }
        });

    }

    private void loadMagInfo() {


        progressDialog.setTitle("загрузка данных....");
        progressDialog.setMessage("Пожалуйста подождите");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        StorageReference filePath = magPhoto.child(ImageUriTwo.getLastPathSegment() + ".jpg");





     byte[] bytes=new byte[0];
        try {
            Bitmap bitmap=MediaStore.Images.Media.getBitmap(this.getContentResolver(),ImageUriTwo);
            ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,8,byteArrayOutputStream);
            bytes=byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }























        final UploadTask uploadTask = filePath.putBytes(bytes);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message = e.toString();
                Toast.makeText(ShopName.this, "error" + message, Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(ShopName.this, "Изображение загружено", Toast.LENGTH_SHORT).show();
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


                            REGMAG();
                        }


                    }
                });
            }
        });



    }


    private void OpenGallery() {




















        Intent galleryintent = new Intent();
        galleryintent.setAction(Intent.ACTION_GET_CONTENT);
        galleryintent.setType("image/*");
        startActivityForResult(galleryintent, GALLERYPICK);


    }

    private void REGMAG() {
        if (TextUtils.isEmpty(edit_adress.getText().toString())){
            Toast.makeText(this, "Введите адрес", Toast.LENGTH_SHORT).show();
        }if (TextUtils.isEmpty(edit_city.getText().toString())){
            Toast.makeText(this, "Введите город", Toast.LENGTH_SHORT).show();
        }if (TextUtils.isEmpty(edit_magname.getText().toString())){
            Toast.makeText(this, "Введите название магазина", Toast.LENGTH_SHORT).show();
        }else {

                HashMap<String, Object> magInfo = new HashMap<>();
                magInfo.put("MagCategory", categ_mag.getText().toString());
                magInfo.put("MagName", edit_magname.getText().toString());
                magInfo.put("MagNumber", edit_number.getText().toString());
                magInfo.put("MagNumberReg", mAuth.getCurrentUser().getPhoneNumber());
                magInfo.put("MagCity", edit_city.getText().toString());
                magInfo.put("MagAdress", edit_adress.getText().toString());
                magInfo.put("MagUid", mAuth.getCurrentUser().getUid());
                magInfo.put("MagLogo", DownloadImageUrlTwo);


                magazine.child(mAuth.getCurrentUser().getUid()).updateChildren(magInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            Toast.makeText(ShopName.this, "Успешная регистрация", Toast.LENGTH_SHORT).show();

startActivity(new Intent(ShopName.this,ShopHomeActivity.class));
finish();
                        } else {
                            progressDialog.dismiss();
                            String message = task.getException().toString();
                            Toast.makeText(ShopName.this, "Ошибка" + message, Toast.LENGTH_SHORT).show();
                        }
                    }

                });

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERYPICK && resultCode == RESULT_OK && data != null) {
            ImageUriTwo = data.getData();
            ImageMagLogo.setImageURI(ImageUriTwo);
            Log.d("blue",ImageUriTwo.toString());
        }

    }
}
