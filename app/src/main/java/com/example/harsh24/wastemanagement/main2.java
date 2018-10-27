package com.example.harsh24.wastemanagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class main2 extends AppCompatActivity {

    TextView signin;
    TextView signup;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);



        signin = (TextView)findViewById(R.id.signin);
        signup = (TextView)findViewById(R.id.signup);


        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent it = new Intent(main2.this, ActivitySignin.class);
                startActivity(it);
            }
        });


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent it = new Intent(main2.this, ActivitySignup.class);
                startActivity(it);

            }
        });
    }
}
