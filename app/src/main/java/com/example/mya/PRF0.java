package com.example.mya;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PRF0 extends AppCompatActivity {
    String cadena;
    TextView fragmento;
    Button almacenar;
    Button concatenar;
    Button descifrar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prf0);
        fragmento=(TextView)findViewById(R.id.NombreArchivo);
        almacenar=(Button)findViewById(R.id.botonAlmacenar);
        concatenar=(Button)findViewById(R.id.botonConcatenar);
        descifrar=(Button)findViewById(R.id.botonDescifrar);
        almacenar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });
        concatenar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        descifrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    @Override
    protected void onResume(){
        super.onResume();
        Intent intent=getIntent();
        if(NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())) {
            Parcelable[] rawMessages = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            NdefMessage message = (NdefMessage) rawMessages[0];
            cadena=new String(message.getRecords()[0].getPayload());
            System.out.println(cadena);
            fragmento.setText(cadena);
            //textView.setText(new String(message.getRecords()[0].getPayload()));

        }else{

        }
            //textView.setText("esperando por NDEF");
    }
}
