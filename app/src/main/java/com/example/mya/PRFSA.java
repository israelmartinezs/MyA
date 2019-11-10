package com.example.mya;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PRFSA extends AppCompatActivity {
    Button desCifrar;
    Button enviar;
    String cadena;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prfs);
        desCifrar=(Button)findViewById(R.id.botonDescifrar);
        enviar=(Button)findViewById(R.id.botonEnviar);
        Bundle datos=this.getIntent().getExtras();
        /// solo obtener una cadena y es a se mandara
        final String cadena=datos.getString("cadena");
        System.out.println("cadena que se va a enviar es : " + cadena);
        desCifrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),PDA.class);
                intent.putExtra("cadena",cadena);
                startActivity(intent);
            }
        });
        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(v.getContext(),PCELL.class);
                //Intent intent= new Intent(v.getContext(),PRFSA.class);
                intent.putExtra("saludo","hola");
                intent.putExtra("cadena",cadena);
                startActivity(intent);
            }
        });
    }
}
