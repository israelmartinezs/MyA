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

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;

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
                    //textoSalida = descifrar(readSavedDataR(file2cipher),ree);

                    //alterDocument(file2cipher,textoSalida.getBytes());
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
}
