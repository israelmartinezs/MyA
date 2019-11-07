package com.example.mya;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.util.Base64;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class cifrador {
    Uri uri;
    //KeyGenerator keygen;
    Context context;
    String text;

    public cifrador(Uri uri, String text,Context context) {
        this.uri = uri;
        this.text=text;
        this.context=context;
    }

    public void cifra(SecretKey key) throws Exception {
        String textoSalida = cifrarF(text, key);
        alterDocument(uri,textoSalida.getBytes());
        Log.d("listo cifrado","hola");


    }

    public  void fragmenta(int n,int k,SecretKey keys){
        final byte[] key=keys.getEncoded();
        Log.d("KKKKKKKKKKKKKKKKKKKKKKK", Arrays.toString(key));
        final Scheme a=new Scheme(new SecureRandom(),n,k);
        final Map<Integer, byte[]> admin= a.generarSecretos(key);
        final byte[] rec=a.recuperar(admin);
        Log.d("LLLLLLLLLLLLLLLLL",Arrays.toString(rec));
        //tv.setText("texto cifrado");
    }
    public void  descifra(SecretKey key) throws Exception {
        String textoSalida = descifrar(text, key);
        //tvTexto.setText(textoSalida);
        alterDocument(uri,textoSalida.getBytes());
    }

    public String cifrarF(String datos, SecretKey key) throws Exception{
        //SecretKeySpec secretKey = generateKey(password);
        Log.d("adentro de cifraf","aqui");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] datosEncriptadosBytes = cipher.doFinal(datos.getBytes());
        String datosEncriptadosString = Base64.encodeToString(datosEncriptadosBytes, Base64.DEFAULT);
        return datosEncriptadosString;
        ////hola
    }
    public String descifrar(String datos, SecretKey key) throws Exception{
        //SecretKeySpec secretKey = generateKey(password);
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] datosDescoficados = Base64.decode(datos, Base64.DEFAULT);
        byte[] datosDesencriptadosByte = cipher.doFinal(datosDescoficados);
        String datosDesencriptadosString = new String(datosDesencriptadosByte);
        return datosDesencriptadosString;
    }
    public void alterDocument(Uri uri,byte[] bytes) {
        try {
            ParcelFileDescriptor pfd = context.getContentResolver().
                    openFileDescriptor(uri, "w");
            FileInputStream fis=new FileInputStream(pfd.getFileDescriptor());
            String tex=readSavedData(pfd);
            //int i=fis.read();
            Log.d("file d", "("+tex+")");
            FileOutputStream fileOutputStream =
                    new FileOutputStream(pfd.getFileDescriptor());

            fileOutputStream.write(bytes);
            /*
            fileOutputStream.write(("Overwritten by MyCloud at " +
                    System.currentTimeMillis() + "\n").getBytes());*/
            // Let the document provider know you're done by closing the stream.
            fileOutputStream.flush();
            fileOutputStream.close();
            pfd.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String readSavedData ( ParcelFileDescriptor p) {
        StringBuffer datax = new StringBuffer("");
        try {
            FileInputStream fIn =new  FileInputStream(p.getFileDescriptor()) ;
            InputStreamReader isr = new InputStreamReader ( fIn ) ;
            BufferedReader buffreader = new BufferedReader ( isr ) ;

            String readString = buffreader.readLine ( ) ;
            while ( readString != null ) {
                datax.append(readString);
                readString = buffreader.readLine ( ) ;
            }

            isr.close ( ) ;
        } catch ( IOException ioe ) {
            ioe.printStackTrace ( ) ;
        }
        return datax.toString() ;
    }




    public SecretKey inicializa(){
        //FileReader fr= null;
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
        KeyGenerator keygenz= null;
        try {
            keygenz = KeyGenerator.getInstance("AES","BC");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
        keygenz.init(128);
        final SecretKey key = keygenz.generateKey();
        //this.keygen=key;
        return key;
    }


}
