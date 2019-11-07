package com.example.mya;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Arrays;

public class PCELL extends AppCompatActivity {
    Button boton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pcell);
        Bundle datos=this.getIntent().getExtras();
        String saludo=datos.getString("saludo");
        Integer numerox=(Integer) datos.get("x");
        byte[] numeroy=(byte[])datos.get("y");
        System.out.println("adentro");
        System.out.println(numerox+ Arrays.toString(numeroy));
        Toast.makeText(this,saludo,Toast.LENGTH_SHORT).show();
        boton=(Button)findViewById(R.id.BotonPCEELRegresar);
        boton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
