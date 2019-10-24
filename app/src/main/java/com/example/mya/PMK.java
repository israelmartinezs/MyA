package com.example.mya;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Map;

import javax.crypto.SecretKey;

public class PMK extends AppCompatActivity {
    Uri uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pmk);
        Bundle datos=this.getIntent().getExtras();
        int numeroDeFragmentos=datos.getInt("NF");
        int numeroMinimoDeFragmentos=datos.getInt("NFMPR");
        String path=datos.getString("path");
        //Log.d("11111111111111111",path.toString());
        cifrador ci=new cifrador(uri,this);
        //ci.inicializa();
        SecretKey se= ci.inicializa();
        final byte[] k=se.getEncoded();
        Log.d("KKKKKKKKKKKKKKKKKKKKKKK", Arrays.toString(k));
        final Scheme a=new Scheme(new SecureRandom(),numeroDeFragmentos,numeroMinimoDeFragmentos);
        final Map<Integer, byte[]> admin= a.split(k);
        final byte[] rec=a.join(admin);
        Log.d("LLLLLLLLLLLLLLLLL",Arrays.toString(rec));

    }
}
