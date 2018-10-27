package com.example.harsh24.wastemanagement;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ActivitySignin extends AppCompatActivity {


    private FirebaseAuth auth;
    private EditText lusername, lpassword;

     ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);

        back = (ImageView)findViewById(R.id.back);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(ActivitySignin.this, MainActivity.class);
                startActivity(it);
            }
        });

        TextView signin = (TextView) findViewById(R.id.signinbt);
        lusername = findViewById(R.id.unl);
        lpassword = findViewById(R.id.passwordl);

        auth = FirebaseAuth.getInstance();

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String username = lusername.getText().toString();
                final String password = lpassword.getText().toString();

                if (TextUtils.isEmpty(username)) {
                    Toast.makeText(getApplicationContext(), "Enter Username", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter Password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

                databaseReference.child("users").child(username)
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if(dataSnapshot!=null) {
                                    User user = dataSnapshot.getValue(User.class);
                                    String emailAddress;
                                    if(user != null)
                                    {
                                        emailAddress = user.getEmail();
                                        auth.signInWithEmailAndPassword(emailAddress, password)
                                                .addOnCompleteListener(ActivitySignin.this, new OnCompleteListener<AuthResult>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                                        if (!task.isSuccessful()) {
                                                            // there was an error
                                                            if (password.length() < 6) {
                                                                lpassword.setError(getString(R.string.minimum_password));
                                                            } else {
                                                                Toast.makeText(ActivitySignin.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                                                            }
                                                        } else {
                                                            Intent intent = new Intent(ActivitySignin.this, MainActivity.class);
                                                            startActivity(intent);
                                                            finish();
                                                        }
                                                    }
                                                });
                                    }
                                    else
                                    {
                                        Toast.makeText(getApplicationContext(), "User does not exist!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Toast.makeText(getApplicationContext(), "Database Error", Toast.LENGTH_SHORT).show();
                            }
                        });


            }
        });
    }
}
