package com.example.test100;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
    public void irIniciar (View view){
        Intent i  = new Intent(this, iniciarSesionActivity.class);
        startActivity(i);
    }
    public void irRegistar (View view){
        Intent i  = new Intent(this, registrarseActivity.class);
        startActivity(i);
    }
    public void jump (View view){
        Intent jump = new Intent(this, MainActivity.class);
        startActivity(jump);
    }
}