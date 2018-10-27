package com.example.harsh24.wastemanagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;


public class MainActivity extends AppCompatActivity {
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(MainActivity.this, AppMainActivity.class));
            finish();
        }
        setContentView(R.layout.activity_main);

        TextView bc = (TextView) findViewById(R.id.citi_bt);
        TextView bw = (TextView) findViewById(R.id.wm_bt);
        bc.setOnClickListener(toCiti);
        bw.setOnClickListener(toWM);
    }

    private View.OnClickListener toCiti = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent it = new Intent(MainActivity.this, main2.class);
            startActivity(it);
        }
    };

    private View.OnClickListener toWM = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent it = new Intent(MainActivity.this, main2.class);
            startActivity(it);
        }
    };
}
