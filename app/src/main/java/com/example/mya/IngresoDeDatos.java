package com.example.mya;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import javax.crypto.SecretKey;

public class IngresoDeDatos extends AppCompatActivity {
    EditText NumeroDeFragmentos;
    EditText NumeroMinimo;
    TextView nombreArchivo;
    Button cifrar;
    Button regresar;
    Button archivo;
    Intent myfile;
    String path;
    Uri uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingreso_de_datos);
        NumeroDeFragmentos=(EditText)findViewById(R.id.NFAC);
        NumeroMinimo=(EditText)findViewById(R.id.NFMPR);
        nombreArchivo=(TextView) findViewById(R.id.NombreArchivo);
        cifrar=(Button)findViewById(R.id.Bcifrar);
        regresar=(Button) findViewById(R.id.Bback);
        archivo =(Button) findViewById(R.id.Barchivo);
        //Boton seleccionar Archivo
        archivo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myfile=new Intent(Intent.ACTION_GET_CONTENT);
                myfile.setType("*/*");
                startActivityForResult(myfile,10);
            }
        });
        //boton regresar
        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //boton cifrar
        cifrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cifrador c=new cifrador(uri,v.getContext());
                SecretKey k=c.inicializa();
                try {
                    c.cifra(k);
                } catch (Exception e) {
                    e.printStackTrace();
                }


                Intent intent = new Intent(v.getContext(),PMK.class);
                intent.putExtra("NF", NumeroDeFragmentos.getText());
                intent.putExtra("NFMPR", NumeroMinimo.getText());
                intent.putExtra("path",path);
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        Uri u=null;
        switch (requestCode){
            case 10:
                if (resultCode == RESULT_OK) {
                    u=data.getData();
                    uri=u;
                    nombreArchivo.setText(path);
                }
            break;
        }
    }

}
