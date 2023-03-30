package com.example.test100;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class iniciarSesionActivity extends AppCompatActivity {
    TextView lblCrearCuenta;
    EditText txtInputEmail, txtInputPassword;
    Button btnLogin;
    FirebaseAuth mAuth;

    private ProgressDialog mProgressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        setContentView(R.layout.activity_iniciar_sesion);
        //Titulo de la Activity
        setTitle("Iniciar Sesion");
        //Flecha de navegación para regresar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txtInputEmail = findViewById(R.id.correol);
        txtInputPassword = findViewById(R.id.contraseñal);
        btnLogin = findViewById(R.id.btn_iniciar);
        lblCrearCuenta = findViewById(R.id.btn_crear);

        lblCrearCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(iniciarSesionActivity.this,registrarseActivity.class));
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verificarCredenciales();
            }
        });

        mProgressBar = new ProgressDialog(iniciarSesionActivity.this);
        mAuth = FirebaseAuth.getInstance();
    }

    public void verificarCredenciales(){
        String email = txtInputEmail.getText().toString();
        String password = txtInputPassword.getText().toString();
        if(email.isEmpty() || !email.contains("@")){
            showError(txtInputEmail, "Correo no valido");
        }else if(password.isEmpty()|| password.length()<7){
            showError(txtInputPassword, "Contraseña invalida");
        }else{
            //Mostrar ProgressBar
            mProgressBar.setTitle("Login");
            mProgressBar.setMessage("Iniciando sesión, espere un momento..");
            mProgressBar.setCanceledOnTouchOutside(false);
            mProgressBar.show();
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        //ocultar progressBar
                        mProgressBar.dismiss();
                        Intent intent = new Intent(iniciarSesionActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }else{
                        Toast.makeText(iniciarSesionActivity.this, "No se pudo iniciar sesion. Por favor verifique correo y contraseña", Toast.LENGTH_LONG).show();
                        mProgressBar.dismiss();
                    }
                }
            });
        }
    }
    private void showError(EditText input, String s){
        input.setError(s);
        input.requestFocus();
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }
}