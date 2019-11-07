package com.example.mya;

import androidx.appcompat.app.AppCompatActivity;

import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
//import android.support.v7.app.AppCompatActivity;

import java.util.Arrays;

public class PCELL extends AppCompatActivity implements NfcAdapter.CreateNdefMessageCallback {
    Button boton;
    String cadena;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pcell);
        NfcAdapter nfcAdapter=NfcAdapter.getDefaultAdapter(this);//inicializador
        Bundle datos=this.getIntent().getExtras();
        String saludo=datos.getString("saludo");
        Integer numerox=(Integer) datos.get("x");
        byte[] numeroy=(byte[])datos.get("y");
        System.out.println("adentro");
        System.out.println(numerox+ Arrays.toString(numeroy));
        cadena=numerox.toString()+"?"+Arrays.toString(numeroy)+"*";
        System.out.println("salida"+cadena);/////////////////////////////////////
        Toast.makeText(this,saludo,Toast.LENGTH_SHORT).show();
        boton=(Button)findViewById(R.id.BotonPCEELRegresar);
        nfcAdapter.setNdefPushMessageCallback(this,this);
        boton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    @Override
    public NdefMessage createNdefMessage(NfcEvent nfcEvent) {
        //String message = editText.getText().toString();
        NdefRecord ndefRecord = NdefRecord.createMime("text/plain", cadena.getBytes());
        NdefMessage ndefMessage = new NdefMessage(ndefRecord);
        return ndefMessage;
    }
}
