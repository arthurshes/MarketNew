package mychati.app.Client;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import mychati.app.R;

public class ClientReg extends AppCompatActivity {
private EditText edit_city_client,edit_phone_client,edit_nameclient;
private AppCompatButton  reg_client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_reg);
        edit_nameclient=(EditText) findViewById(R.id.edit_nameclient);
        edit_city_client=(EditText) findViewById(R.id.edit_city_client);
        edit_phone_client=(EditText) findViewById(R.id.edit_phone_client);
        reg_client=(AppCompatButton) findViewById(R.id.reg_client);


        reg_client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nm=new Intent(ClientReg.this,HomeActivity.class);
                startActivity(nm);
            }
        });
    }
}