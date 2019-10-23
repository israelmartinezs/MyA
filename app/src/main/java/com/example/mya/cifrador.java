package com.example.mya;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class cifrador {
    String path;
    KeyGenerator keygen=null;

    public cifrador(String path) {
        this.path = path;
    }
    public SecretKey inicializa(){
        FileReader fr= null;
    /*
        try {
            File archivoE= new File(path);
            int size = (int) archivoE.length();
            byte[] bytes= new byte[size];
            //BufferedReader fin= new BufferedReader(new InputStreamReader(openFileInput(path)));

            //File ArchivoS=new File(path);
            //FileInputStream inputStream= new FileInputStream(archivoE);
            //inputStream.read(bytes);
            //fr=new FileReader(archivoE);
            //BufferedReader br= new BufferedReader(fr);
            //BufferedInputStream buf = new BufferedInputStream(new FileInputStream(archivoE));
            //buf.read(bytes,0,bytes.length);
            Log.d("hollllllllllllllllllllllla","jajajajaja");
            Log.d("texto",bytes.toString());
            //buf.close();
            inputStream.close();
            String data=null;
            //while((data=br.read()))
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }

///listo para cifrar
//documents example android segirdad
*/
        try {
            keygen = KeyGenerator.getInstance("AES","BC");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
        keygen.init(128);
        final SecretKey key = keygen.generateKey();
        return key;
    }


}
