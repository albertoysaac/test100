package com.example.test100;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
//import android.widget.TextView;

public class registrarseActivity extends AppCompatActivity {
    //TextView tieneCuenta;
    Button btnRegistrar;
    EditText txtInputUsername, txtInputEmail, txtInputPassword, txtInputConfirmPassword;

    private ProgressDialog mProgressBar;
    //Firebase
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);
        //Titulo de la Activity
        setTitle("Registrarse");
        //Flecha de navegación para regresar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txtInputUsername = findViewById(R.id.nombre);
        txtInputEmail = findViewById(R.id.correo);
        txtInputPassword = findViewById(R.id.contraseña);
        txtInputConfirmPassword = findViewById(R.id.contraseñaC);

        btnRegistrar = findViewById(R.id.btn_crear);
        //tieneCuenta =findViewById(R.id.alreadyHaveAccount);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verificarCredenciales();
            }
        });

        /*tieneCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(registrarseActivity.this,contentHomeActivity.class));
            }
        });*/
        mAuth = FirebaseAuth.getInstance();

        mProgressBar = new ProgressDialog(registrarseActivity.this);

    }

    public void verificarCredenciales(){
        String username = txtInputUsername.getText().toString();
        String email = txtInputEmail.getText().toString();
        String password = txtInputPassword.getText().toString();
        String confirmPass = txtInputConfirmPassword.getText().toString();
        if(username.isEmpty() || username.length() < 5){
            showError(txtInputUsername,"Username no valido");
        }else if (email.isEmpty() || !email.contains("@")){
            showError(txtInputEmail,"Correo no valido");
        }else if (password.isEmpty() || password.length() < 7){
            showError(txtInputPassword,"Contraseña no valida minimo 7 caracteres");
        }else if (confirmPass.isEmpty() || !confirmPass.equals(password)){
            showError(txtInputConfirmPassword,"Contraseña no valida, no coincide.");
        }else{
            //Mostrar ProgressBar
            mProgressBar.setTitle("Proceso de Registro");
            mProgressBar.setMessage("Registrando usuario, espere un momento");
            mProgressBar.setCanceledOnTouchOutside(false);
            mProgressBar.show();
            //Registrar usuario
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        mProgressBar.dismiss();
                        //redireccionar - intent a login
                        Intent intent = new Intent(registrarseActivity.this, iniciarSesionActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        Toast.makeText(registrarseActivity.this, "Registrado correctamente", Toast.LENGTH_SHORT).show();
                        
                    }else{
                        Toast.makeText(registrarseActivity.this, "No se pudo registrar", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            //ocultar progressBar
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

