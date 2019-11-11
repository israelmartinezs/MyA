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
/*
import android.support.v7.app.AppCompatActivity;
import android.app.Activity;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.os.Build;
import android.os.Parcelable;
import android.security.keystore.KeyProperties;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
*/
import java.util.Arrays;

public class PCELL extends AppCompatActivity implements NfcAdapter.CreateNdefMessageCallback {
    Button boton;
    String cadena;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pcell);
        NfcAdapter nfcAdapter=NfcAdapter.getDefaultAdapter(this);//inicializador
        nfcAdapter.setNdefPushMessageCallback(this,this);
        Bundle datos=this.getIntent().getExtras();
        /// solo obtener una cadena y es a se mandara
        String cadena=datos.getString("cadena");
        System.out.println("cadena que se va a enviar es : " + cadena);
        String saludo=datos.getString("saludo");
        this.cadena=cadena;
        Integer numerox=(Integer) datos.get("x");
        byte[] numeroy=(byte[])datos.get("y");
        System.out.println("adentro");
        //System.out.println(numerox+ Arrays.toString(numeroy));
        //cadena=numerox.toString()+"?"+Arrays.toString(numeroy)+"*";
        System.out.println("salida"+cadena);/////////////////////////////////////
        Toast.makeText(this,"envia",Toast.LENGTH_SHORT).show();
        boton=(Button)findViewById(R.id.BotonPCEELRegresar);

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
