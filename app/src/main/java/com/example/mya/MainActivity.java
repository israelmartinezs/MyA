package com.example.mya;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void cifrar(View view){
        Intent cifrarI = new Intent(this, IngresoDeDatos.class);
        startActivity(cifrarI);
    }
}
