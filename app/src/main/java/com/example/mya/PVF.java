package com.example.mya;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class PVF extends AppCompatActivity {
    LinearLayout botonera;
    Button regresar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pvf);
        regresar=(Button)findViewById(R.id.botonRegresar);
        String [] files=fileList();
        botonera=(LinearLayout)findViewById(R.id.botonesVF);
        LinearLayout.LayoutParams lp= new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        for(int i=0;i<files.length;i++){
            Button boton=new Button(this);
            boton.setLayoutParams(lp);
            boton.setText(files[i]);
            botonera.addView(boton);
            boton.setOnClickListener(new PVF.ButtonsOnClickListener(this,i));
        }
        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    class ButtonsOnClickListener implements View.OnClickListener
    {
        Context context;
        //String texto;
        int numb;
        public ButtonsOnClickListener(Context context, int numB){
            this.context=context;
            this.numb=numB;
            //this.texto=fragmento;
        }
        @Override
        public void onClick(View v)
        {
            String [] files=fileList();
            //Toast.makeText(getApplicationContext(),"Pulsado"+numb,Toast.LENGTH_SHORT).show();

            //texto=texto+readFile(files[numb]);
            //System.out.println("texto a enviar: "+texto);
            //Intent intent=new Intent(v.getContext(),PCELL.class);
            Intent intent= new Intent(v.getContext(),PSA.class);//////////////////////////////////////////////////////
            intent.putExtra("nombre",files[numb]);
            //intent.putExtra("cadena",texto);
            //intent.putExtra("x",numeros[numb]);
            //intent.putExtra("y",y[numb]);

            startActivity(intent);
        }
        public String readFile(String name){
            InputStreamReader archivo= null;
            try {
                archivo = new InputStreamReader(openFileInput(name));
                BufferedReader br= new BufferedReader(archivo);
                String line=br.readLine();
                br.close();
                archivo.close();
                return line;

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "";
        }

    };
}
