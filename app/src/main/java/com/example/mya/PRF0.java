package com.example.mya;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class PRF0 extends AppCompatActivity {
    String cadena;
    TextView fragmento;
    Button almacenar;
    Button concatenar;
    Button descifrar;
    String arch1="1?[-45, 97, 109, -91, -61, -87, 68, -79, -92, 82, 75, -125, 0, -115, 80, -109]*";
    String arch2="4?[-81, 11, -124, 124, -70, 6, 91, -47, 79, -110, 27, -111, -19, 89, 92, -29]*";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prf0);
        fragmento=(TextView)findViewById(R.id.VerTexto);
        almacenar=(Button)findViewById(R.id.botonAlmacenar);
        concatenar=(Button)findViewById(R.id.botonConcatenar);
        descifrar=(Button)findViewById(R.id.botonDescifrar);
        String [] archivos=fileList();
        if (existeA("llaveprueba.key",archivos)){

        }
        else {
            guarda(arch1,"llaveprueba.key");

        }
        almacenar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("almacenar");
                guarda(arch2,"llavedos.key");
            }
        });
        concatenar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("concatenar");
                Intent intent= new Intent(v.getContext(),PCF.class);
                intent.putExtra("fragmentoRec",arch2);
                startActivity(intent);

            }
        });
        descifrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("descifrar");
            }
        });
    }
    private boolean existeA(String nombre,String [] files){
        for(int i=0 ; i<files.length;i++)
            if (nombre.equals(files[i]))
                return true;
        return false;
    }
    public void guarda(String cadena,String nombre){
        try {
            OutputStreamWriter archivo= new OutputStreamWriter(openFileOutput(nombre, Activity.MODE_PRIVATE));
            //archivo.write(editText.getText().toString());
            archivo.write(cadena);
            archivo.flush();
            archivo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Toast.makeText(this,"guardado",Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onResume(){
        super.onResume();
        Intent intent=getIntent();
        if(NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())) {
            Parcelable[] rawMessages = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            NdefMessage message = (NdefMessage) rawMessages[0];
            cadena=new String(message.getRecords()[0].getPayload());
            System.out.println(cadena+"holaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa ");
            fragmento.setText(cadena);
            //textView.setText(new String(message.getRecords()[0].getPayload()));

        }else{
            fragmento.setText("esperando por NDEF");
        }

    }

}
