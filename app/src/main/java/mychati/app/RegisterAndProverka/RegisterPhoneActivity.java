package mychati.app.RegisterAndProverka;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthSettings;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import mychati.app.R;

public class RegisterPhoneActivity extends AppCompatActivity {
private EditText edit_phonenum,verifysms;
private AppCompatButton getcode,verifycode;
private FirebaseAuth mAuth;
    private GoogleSignInClient client;
    private String verificationID;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
private static int PHONE_NUMBER=12;

private String smscode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_phone);
        edit_phonenum=(EditText) findViewById(R.id.edit_phonenum);
        getcode=(AppCompatButton) findViewById(R.id.getcode);
mAuth=FirebaseAuth.getInstance();
        ImageView imagegoogle = (ImageView) findViewById(R.id.imagegoogle);
        FirebaseAuthSettings firebaseAuthSettings = mAuth.getFirebaseAuthSettings();
getcode.setEnabled(false);

        verifycode=(AppCompatButton)findViewById(R.id.verifycode) ;
        verifysms=(EditText)findViewById(R.id.verifysms) ;
        verifycode.setEnabled(false);
///Регистрация гугла///
        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        client = GoogleSignIn.getClient(this, options);
        imagegoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=client.getSignInIntent();
                startActivityForResult(i,1234);
            }
        });
        ///регистрация гугла///
        edit_phonenum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length()==PHONE_NUMBER){
                    getcode.setEnabled(true);

                }else{
                    getcode.setEnabled(false);
                }


                Log.d("Arthur",editable.toString());
            }
        });
        getcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              String  phoneNumber=edit_phonenum.getText().toString();
PhoneVery(phoneNumber);

verifycode.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        verifycode(verifysms.getText().toString());
    }
});

                ///startActivity(new Intent(RegisterPhoneActivity.this,SmsRegisterActivity.class));
            }
        });



    }

    private void PhoneVery(String phoneNumber) {





        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(RegisterPhoneActivity.this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);









    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential credential)
        {
            final String code = credential.getSmsCode();
            if(code!=null)
            {
                verifycode(code);
            }
        }
        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(RegisterPhoneActivity.this, "Verification Failed", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCodeSent(@NonNull String s,
                               @NonNull PhoneAuthProvider.ForceResendingToken token)
        {
            super.onCodeSent(s, token);
            verificationID = s;
            Toast.makeText(RegisterPhoneActivity.this, "Code sent", Toast.LENGTH_SHORT).show();
            verifycode.setEnabled(true);
verifycode.setVisibility(View.VISIBLE);
verifysms.setVisibility(View.VISIBLE);
getcode.setEnabled(false);
getcode.setVisibility(View.INVISIBLE);
        }};
    private void verifycode(String Code)
    {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationID,Code);
        signinbyCredentials(credential);
    }

    private void signinbyCredentials(PhoneAuthCredential credential)
    {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task)
                    {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(RegisterPhoneActivity.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                           Intent sucsees=new Intent(RegisterPhoneActivity.this, SplashScreenActivity.class);

                           startActivity(sucsees);
                           finish();
                        }

                    }
                });}



    // [END sign_in_with_phone]


////Google register////
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1234){
            Task<GoogleSignInAccount> task=GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account=task.getResult(ApiException.class);
                AuthCredential credential= GoogleAuthProvider.getCredential(account.getIdToken(),null);

                FirebaseAuth.getInstance().signInWithCredential(credential)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    Intent intent=new Intent(getApplicationContext(), SplashScreenActivity.class);
                                    startActivity(intent);
                                }
                                else {
                                    Toast.makeText(RegisterPhoneActivity.this, "error", Toast.LENGTH_SHORT).show();

                                }
                            }
                        });

            } catch (ApiException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        if (user !=null){
            Intent vxod=new Intent(RegisterPhoneActivity.this, SplashScreenActivity.class);

            startActivity(vxod);
            finish();
        }
    }
    ////GoogleReg end////
}