package com.example.mya;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.security.SecureRandom;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class PDA extends AppCompatActivity {
    Button selecciona;
    Button descifra;
    TextView nombreArchivo;
    private Intent myfile;
    private Uri file2cipher;
    private String textoSalida;
    SecretKey ree;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pd);
        selecciona=(Button)findViewById(R.id.botonSel);
        descifra=(Button)findViewById(R.id.botonDes);
        nombreArchivo=(TextView)findViewById(R.id.textoNombreArchvo);
        //enviar=(Button)findViewById(R.id.botonEnviar);
        Bundle datos=this.getIntent().getExtras();
        /// solo obtener una cadena y es a se mandara
         final String cadenas=datos.getString("cadena");

        selecciona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myfile=new Intent(Intent.ACTION_OPEN_DOCUMENT);
                myfile.setType("*/*");
                startActivityForResult(myfile,10);
            }
        });
        descifra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    System.out.println("acac "+cadenas);
                    final Map<Integer, byte[]> admin=recupera(cadenas);
                    //textoSalida = descifrar(readSavedDataR(file2cipher),ree);
                    final Scheme a=new Scheme(new SecureRandom(),9,9);
                      final byte[] rec=a.recuperar(admin);
                    SecretKey ley= new SecretKeySpec(rec,0,rec.length,"AES");
                    final byte[] kettobyte=ley.getEncoded();
                    Log.d("recuperada",Arrays.toString(kettobyte));
                    Toast.makeText(v.getContext(),"llave recuperada",Toast.LENGTH_SHORT).show();
                    //alterDocument(file2cipher,textoSalida.getBytes());
                    textoSalida = descifrar(readSavedDataR(file2cipher),ley);
                    //llaveguardada=ley;
                    //tvTexto.setText(textoSalida);
                    alterDocument(file2cipher,textoSalida.getBytes());
                }catch (Exception e){
                    e.printStackTrace();
                }


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
                    file2cipher=u;
                    Log.d("uri",file2cipher.toString());
                    //nombreArchivo.setText(path);
                    nombreArchivo.setText(file2cipher.getPath());
                }
                break;
        }
    }
    public String readSavedDataR ( Uri uri) throws FileNotFoundException {
        StringBuffer datax = new StringBuffer("");
        ParcelFileDescriptor pfd = this.getContentResolver().openFileDescriptor(uri, "r");
        System.out.println(uri.getLastPathSegment());
        try {
            FileInputStream fIn =new  FileInputStream(pfd.getFileDescriptor()) ;
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
        Log.d("hollllla",datax.toString());
        return datax.toString() ;
    }
    private String descifrar(String datos, SecretKey key) throws Exception{
        //SecretKeySpec secretKey = generateKey(password);
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] datosDescoficados = Base64.decode(datos, Base64.DEFAULT);
        byte[] datosDesencriptadosByte = cipher.doFinal(datosDescoficados);
        String datosDesencriptadosString = new String(datosDesencriptadosByte);
        return datosDesencriptadosString;
    }
    private void alterDocument(Uri uri,byte[] bytes) {
        try {
            ParcelFileDescriptor pfd =this.getContentResolver().openFileDescriptor(uri, "w");
            //FileInputStream fis=new FileInputStream(pfd.getFileDescriptor());
            //String tex=readSavedData(pfd);
            //int i=fis.read();
            //Log.d("file d", "("+tex+")");
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
    /*
    public Map<Integer, byte[]> recupera(String s){
        String [] datos=s.split("/*");
        for (int i ; i<datos.length; i++){
            System.out.println(datos[i]);
        }

    }*/
    public Map<Integer, byte[]> recupera(String s){
        String [] datos=s.split("[*]");

        final int n=datos.length;
        final Map<Integer, byte[]> parts = new HashMap <> (n);
        for (int i=0 ; i<datos.length; i++){
            String [] puntos=datos[i].split("[?]");
            Integer a= Integer.valueOf(puntos[0]);
            byte [] b= bytes(puntos[1]);
            System.out.println("x"+a+ "Y"+Arrays.toString(b));
            parts.put(a,b);
            //by(puntos[1]);
        }
        return Collections.unmodifiableMap(parts);

    }
    public byte [] bytes(String cadi){
        cadi=cadi.substring(1, cadi.length()-1);
        cadi= cadi.replaceAll(" ", "");
        String [] numeros= cadi.split("[,]");

        int [] numerosInt= new int[numeros.length];
        byte [] salida= new byte[numeros.length];
        for (int i = 0; i < numeros.length; i++) {
            numerosInt[i]=Integer.parseInt(numeros[i]);
            System.out.println("numeros: "+ numerosInt[i]);
            salida[i]=(byte)numerosInt[i];
        }
        return salida;
    }
    /*
    public void by(String a){
        String [] dato=a.split("\[");
        for (int j=0;j<dato.length; j++){
            System.out.println("acaando"+dato[j]);
        }
    }*/
}
