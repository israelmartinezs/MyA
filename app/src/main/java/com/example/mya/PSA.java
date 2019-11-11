package com.example.mya;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class PSA extends AppCompatActivity {
    Button enviar;
    Button eliminar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ps);
        enviar=(Button)findViewById(R.id.botonEnviar);
        eliminar=(Button)findViewById(R.id.botonEliminar);
        Bundle datos=this.getIntent().getExtras();
        final String nombre=datos.getString("nombre");
        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cadena= readFile(nombre);
                Intent intent= new Intent(v.getContext(),PCELL.class);
                intent.putExtra("cadena",cadena);
                startActivity(intent);
            }
        });
        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.getContext().deleteFile(nombre);
                Toast.makeText(v.getContext(),"Eliminado",Toast.LENGTH_SHORT).show();
                Intent intent= new Intent(v.getContext(),MainActivity.class);
                finish();
                startActivity(intent);
            }
        });

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
}
