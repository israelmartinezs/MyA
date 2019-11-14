package com.example.mya;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Map;

public class FCEF extends AppCompatActivity {
    int numero=4;
    LinearLayout botonera;
    Integer numeros[];
    String enviar;
    Button regresar;
    byte y[][];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fcef);
        regresar=(Button)findViewById(R.id.botonFinish);
        Bundle datos=this.getIntent().getExtras();
        Integer  indice[]= (Integer[]) datos.get("x");
        byte resultado[][]=(byte[][]) datos.get("y");
        //enviar=indice+"?"+
        numeros=indice;
        y=resultado;
        numero=datos.getInt("numero");
        System.out.println(indice[0]);
        System.out.println(Arrays.toString(resultado[0]));
        //Map<Integer, byte[]>[] fragmentos=  (Map<Integer, byte[]>[]) datos.getSerializable("map");
        //Log.d("frgamento 0", String.valueOf(fragmentos.length));
        Log.d("hola","jajajajaja");
        botonera=(LinearLayout)findViewById(R.id.botones);
        LinearLayout.LayoutParams lp= new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        for(int i=1;i<numero;i++){
            Button boton=new Button(this);
            boton.setLayoutParams(lp);
            boton.setText("Fragmento: "+i);
            botonera.addView(boton);
            boton.setOnClickListener(new ButtonsOnClickListener(this,i));
        }
        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }
    class ButtonsOnClickListener implements View.OnClickListener
    {
        Context context;
        int numb;
        public ButtonsOnClickListener(Context context, int numB){
            this.context=context;
            this.numb=numB;
        }
        @Override
        public void onClick(View v)
        {
            Toast.makeText(getApplicationContext(),"Pulsado"+numb,Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(v.getContext(),PCELL.class);
            enviar=numeros[numb]+"?"+Arrays.toString(y[numb])+"*";
            intent.putExtra("saludo","hola");
            //intent.putExtra("x",numeros[numb]);
            //intent.putExtra("y",y[numb]);
            intent.putExtra("cadena",enviar);
            startActivity(intent);
        }

    };
}

