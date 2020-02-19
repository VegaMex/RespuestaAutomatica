package com.vegamex.respuestaautomatica;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Telephony;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText txtMensaje, txtNumero;
    Button btnMensaje, btnNumero;
    Receiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtMensaje = findViewById(R.id.txtMensaje);
        txtNumero = findViewById(R.id.txtNumero);
        btnMensaje = findViewById(R.id.btnMensaje);
        btnNumero = findViewById(R.id.btnNumero);

        btnMensaje.setOnClickListener(this);
        btnNumero.setOnClickListener(this);

        App.mensaje = "Me mandaste un mensaje y yo te respondo con otro c:";

        String[] PERMISOS = {
                Manifest.permission.SEND_SMS, Manifest.permission.RECEIVE_SMS
        };

        if (!hasPermissions(this, PERMISOS)) {
            ActivityCompat.requestPermissions(this, PERMISOS, 0);
        }

        IntentFilter intentFilter = new IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION);
        receiver = new Receiver();
        registerReceiver(receiver, intentFilter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnMensaje:
                App.mensaje = btnMensaje.getText().toString();
                break;
            case R.id.btnNumero:
                App.numero = btnNumero.getText().toString();
                break;
        }
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
}
