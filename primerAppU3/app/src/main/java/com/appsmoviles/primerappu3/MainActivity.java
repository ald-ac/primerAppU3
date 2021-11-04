package com.appsmoviles.primerappu3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    //UI
    EditText etNumero, etIp, etPuerto;
    TextView tvResultado;
    Button btnCalcular;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNumero = findViewById(R.id.et_numero);
        etIp = findViewById(R.id.et_ip);
        etPuerto = findViewById(R.id.et_puerto);
        tvResultado = findViewById(R.id.tv_resultado);
        btnCalcular = findViewById(R.id.btn_calcular);

        btnCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String numDecimal, ip, puerto;
                try { //Validar numero entero
                    Integer.parseInt(etNumero.getText().toString().trim());
                    numDecimal = etNumero.getText().toString().trim();
                    ip = etIp.getText().toString().trim();
                    puerto = etPuerto.getText().toString().trim();

                    Tarea T = new Tarea();
                    //Mandar a llamar doInBackground de tarea asincrona con parametros
                    T.execute(numDecimal,ip,puerto);
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Se necesita un numero entero", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public String ejecutaCliente(int numDecimal,String ipserv,int puerto) {
        String resultado="";
        try {
            //Abrir socket al servidor en su puerto
            Socket sk = new Socket(ipserv,puerto);
            //Buffer para obtener lo enviado por el servidor
            BufferedReader entrada = new BufferedReader(new InputStreamReader(sk.getInputStream()));
            //Buffer para mandar al servidor
            PrintWriter salida = new PrintWriter(new OutputStreamWriter(sk.getOutputStream()),true);
            //Enviar parametro al serv
            salida.println(numDecimal);
            //Leer respuesta del serv
            String temp = entrada.readLine();
            String [] linea = temp.split("-");
            resultado += linea[0] + "\n";
            resultado += linea[1] + "\n";
            resultado += linea[2] + "\n";
            resultado += linea[3];
            //Cerrar socket
            sk.close();
        }
        catch (Exception e) {
            resultado="Error !!\n " + e.toString()+"\n";
        }
        return resultado;
    } //ejecutaCliente

    public class Tarea extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... strings) {
            return ejecutaCliente(Integer.parseInt(strings[0]),strings[1],Integer.parseInt(strings[2]));
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //Asignar a textView resultado del serv
            tvResultado.setText(s);
        }
    }
}