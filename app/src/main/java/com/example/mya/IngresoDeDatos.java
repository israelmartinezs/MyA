package com.example.mya;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import static java.lang.Integer.parseInt;

public class IngresoDeDatos extends AppCompatActivity {
    EditText NumeroDeFragmentos;
    EditText NumeroMinimo;
    TextView nombreArchivo;
    Button cifrar;
    Button regresar;
    Button archivo;
    Intent myfile;
    String path;
    byte[] recuperada;
    Uri uri;
    ////
    private EditText etTexto, etPassword;
    private TextView tvTexto;
    private Button btEncriptar, btDesEncriptar, btApiEncriptada;
    private String textoSalida;
    private Button descifrar;
    int numero1;
    int numero2;
    SecretKey ree;
    Uri file2cipher=null;//archivo a cifrar
    //final Map<Integer, byte[]> fragmentos;
    ////
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
        descifrar=(Button) findViewById(R.id.bDescifrar);
        //final Map<Integer, byte[]>[] fragmentos = new Map<Integer, byte[]>[1];
        //NumeroMinimo.getTex
        //key generate

        KeyGenerator keygen= null;
        try {
            keygen = KeyGenerator.getInstance("AES","BC");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
        keygen.init(128);
        final SecretKey key = keygen.generateKey();
        ///
        //Boton seleccionar Archivo
        archivo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myfile=new Intent(Intent.ACTION_OPEN_DOCUMENT);
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
                try{
                    numero1=Integer.parseInt(NumeroDeFragmentos.getText().toString());
                    numero2=Integer.parseInt(NumeroMinimo.getText().toString());
                    textoSalida = cifrarF(readSavedDataR(file2cipher), key);
                    //Log.d("texto",textoSalida);
                    alterDocument(file2cipher,textoSalida.getBytes("UTF-8"));
                    //System.out.println(file2cipher.);
                    //Secret Sharing
                    final byte[] k=key.getEncoded();
                    Log.d("original", Arrays.toString(k));
                    final Scheme a=new Scheme(new SecureRandom(),numero1,numero2);
                    final Map<Integer, byte[]> admin= a.generarSecretos(k);

                    final byte[] rec=a.recuperar(admin);
                    SecretKey ley= new SecretKeySpec(rec,0,rec.length,"AES");
                    ree=ley;
                    final byte[] kettobyte=ley.getEncoded();
                    recuperada=rec;
                    Log.d("recuperada",Arrays.toString(kettobyte));
                    ////////////////////
                    ///////
                    Integer nume[]= new Integer[numero1];
                    byte[][] bytes=new byte[numero1][16];

                    int ko=0;
                    //HashMap<Integer,byte[]> adminhash=(HashMap<Integer, byte[]>) admin;
                    for (Map.Entry <Integer, byte[]> fragmento : admin.entrySet()){
                        Integer clave = fragmento.getKey();
                        byte[] valor = fragmento.getValue();
                        nume[ko]=clave;
                        bytes[ko]=valor;

                        System.out.println(nume[ko]+"  ->  "+Arrays.toString(bytes[ko]));
                        ko++;
                    }
                    final Date date = new Date();
                    DateFormat hourdateFormat = new SimpleDateFormat("HH:mm:ss*dd.MM.yyyy");
                    String hora=hourdateFormat.format(date);
                    System.out.println("almacenar");
                    guarda(nume[0]+"?"+Arrays.toString(bytes[0])+"*",hora+".key");///cadena,hora.key

                    Intent intent = new Intent(v.getContext(),FCEF.class);
                    /////////////
                    intent.putExtra("x",nume);
                    intent.putExtra("y",bytes);
                    intent.putExtra("numero",numero1);
                    //intent.putExtra("map",adminhash);
                    //intent.write;
                    //intent.putExtra("llaves", (Serializable) admin);
                    startActivity(intent);
                    //tvTexto.setText(textoSalida);
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        /////boton descifrar
        descifrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    textoSalida = descifrar(readSavedDataR(file2cipher),ree);
                    //tvTexto.setText(textoSalida);
                    alterDocument(file2cipher,textoSalida.getBytes("UTF-8"));
                }catch (Exception e){
                    e.printStackTrace();
                }
                /////////pegar esto en cifrar
                ////
                /////
                /*
                Intent intent = new Intent(v.getContext(),FCEF.class);
                intent.putExtra("llaves",)
                startActivity(intent);*/

            }
        });
        //cifrar.setOnClickListener(new View.OnClickListener() {
            //@Override
            /*public void onClick(View v) {
                String a=null;
                try {
                    String texto=readSavedDataR(uri);
                    a=texto;
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                cifrador c=new cifrador(uri,a,getBaseContext());
                SecretKey k=c.inicializa();
                Log.d("pppppppppppppppppppppppppp","hola");
                Log.d("key", Arrays.toString(new SecretKey[]{k}));
                try {
                    c.cifra(k);
                    Log.d("jojojojojoj","cifrando");
                    //c.fragmenta(parce NumeroDeFragmentos.getText().toString(),NumeroMinimo,k);
                    c.fragmenta( Integer.parseInt(NumeroDeFragmentos.getText().toString()),Integer.parseInt(NumeroMinimo.getText().toString()),k);
                } catch (Exception e) {
                    e.printStackTrace();
                }


                Intent intent = new Intent(v.getContext(),PMK.class);
                intent.putExtra("NF", NumeroDeFragmentos.getText());
                intent.putExtra("NFMPR", NumeroMinimo.getText());
                intent.putExtra("path",path);
                intent.putExtra("se",k);
                startActivity(intent);
            }*/
        //});


    }
    public void guarda(String cadena,String nombre){
        try {
            OutputStreamWriter archivo= new OutputStreamWriter(openFileOutput(nombre, Activity.MODE_PRIVATE),"UTF-8");
            //archivo.write(editText.getText().toString());
            archivo.write(cadena );
            archivo.flush();
            archivo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Toast.makeText(this,"guardado fragmento 1",Toast.LENGTH_SHORT).show();
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
        String salida="";
        try {
            FileInputStream fIn =new  FileInputStream(pfd.getFileDescriptor()) ;
            InputStreamReader isr = new InputStreamReader ( fIn ,"UTF-8") ;
            BufferedReader buffreader = new BufferedReader ( isr ) ;

            String readString = buffreader.readLine ( ) ;
            while ( readString != null ) {
                datax.append(readString);
                readString =buffreader.readLine ( ) ;
            }
            salida=readString;

            isr.close ( ) ;
        } catch ( IOException ioe ) {
            ioe.printStackTrace ( ) ;
        }
        Log.d("hollllla",datax.toString());

        return datax.toString() ;
        //return salida;
    }
    private void writeToFile(String data, Context context){
        OutputStreamWriter outputStreamWriter= null;
        try {
            outputStreamWriter = new OutputStreamWriter(context.openFileOutput("nombre.secret", Context.MODE_APPEND));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }

    }
    private String cifrarF(String datos, SecretKey key) throws Exception{
        //SecretKeySpec secretKey = generateKey(password);
        //Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");

          Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] datosEncriptadosBytes = cipher.doFinal(datos.getBytes("UTF-8"));
        String datosEncriptadosString = Base64.encodeToString(datosEncriptadosBytes, Base64.DEFAULT);
        return datosEncriptadosString;
        ////hola
    }
    private String descifrar(String datos, SecretKey key) throws Exception{
        //SecretKeySpec secretKey = generateKey(password);
        Cipher cipher = Cipher.getInstance("AES");
        //Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");

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
    public String readSavedData ( ParcelFileDescriptor p) {
        StringBuffer datax = new StringBuffer("");
        String salida="";
        try {
            FileInputStream fIn =new  FileInputStream(p.getFileDescriptor()) ;
            InputStreamReader isr = new InputStreamReader ( fIn ) ;
            BufferedReader buffreader = new BufferedReader ( isr ) ;

            String readString = buffreader.readLine ( ) ;
            while ( readString != null ) {
                //datax.append(readString);
                readString =readString + buffreader.readLine ( ) ;
            }
            salida=readString;

            isr.close ( ) ;
        } catch ( IOException ioe ) {
            ioe.printStackTrace ( ) ;
        }
        return  salida;
    }

}
