package com.example.mya;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
    }
    public void cifrar(View view){
        Intent cifrarI = new Intent(this, IngresoDeDatos.class);
        startActivity(cifrarI);
    }
    public void visualizar(View view){
        Intent intent = new Intent(this,PVF.class);
        startActivity(intent);
    }

}
