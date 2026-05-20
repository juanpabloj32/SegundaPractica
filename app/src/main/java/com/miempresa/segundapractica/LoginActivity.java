package com.miempresa.segundapractica;

// LoginActivity.java


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText usuario, password;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usuario = findViewById(R.id.etUsuario);
        password = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(v -> iniciarSesion());
    }

    private void iniciarSesion() {

        String user = usuario.getText().toString().trim();
        String pass = password.getText().toString().trim();

        if (user.isEmpty()) {

            usuario.setError("Ingresa tu usuario");
            usuario.requestFocus();
            return;
        }

        if (pass.isEmpty()) {

            password.setError("Ingresa tu contraseña");
            password.requestFocus();
            return;
        }

        if (user.equals("admin") && pass.equals("1234")) {

            Toast.makeText(
                    this,
                    "Bienvenido",
                    Toast.LENGTH_SHORT
            ).show();

            Intent intent = new Intent(
                    LoginActivity.this,
                    MainActivity.class
            );

            startActivity(intent);
            finish();

        } else {

            Toast.makeText(
                    this,
                    "Usuario o contraseña incorrectos",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }
}