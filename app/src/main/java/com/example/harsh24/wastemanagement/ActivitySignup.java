package com.example.harsh24.wastemanagement;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.harsh24.wastemanagement.MainActivity;
import com.example.harsh24.wastemanagement.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ActivitySignup extends AppCompatActivity {

  ImageView back;
  private FirebaseAuth auth;
    public static String Username;
    public static String EmailAddress;
    public static String Password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        back = (ImageView)findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent it = new Intent(ActivitySignup.this, MainActivity.class);
                startActivity(it);

            }
        });


        TextView signup = findViewById(R.id.signupbt);

        auth = FirebaseAuth.getInstance();


        final EditText rusername = (EditText) findViewById(R.id.unr);
        final EditText remailAddress = (EditText) findViewById(R.id.mailr);
        final EditText rinputPassword = (EditText) findViewById(R.id.passwordr);
        final EditText crinputPassword = (EditText) findViewById(R.id.confirmpasswordr);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String libId,emailAddress,password;
                libId = rusername.getText().toString().trim();
                Username = libId;
                emailAddress = remailAddress.getText().toString().trim();
                EmailAddress = emailAddress;
                password = rinputPassword.getText().toString().trim();
                Password = password;
                final String confPassword = crinputPassword.getText().toString().trim();

                if (TextUtils.isEmpty(libId)) {
                    Toast.makeText(getApplicationContext(), "Enter Username!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(emailAddress)) {
                    Toast.makeText(getApplicationContext(), "Enter Email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter Password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(confPassword)) {
                    Toast.makeText(getApplicationContext(), "Confirm your password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.compareTo(confPassword) != 0) {
                    Toast.makeText(getApplicationContext(), "Passwords do not match!", Toast.LENGTH_SHORT).show();
                    return;
                }

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

                databaseReference.child("users").child(Username)
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot != null) {
                                    User user = dataSnapshot.getValue(User.class);
                                    if (user != null) {
                                        Toast.makeText(ActivitySignup.this, "User Already Registered!", Toast.LENGTH_SHORT).show();
                                    }
                                    else{
                                        Log.e("DER", "hua1");
                                        generateUser(libId, emailAddress);
                                        FirebaseAuth aut = FirebaseAuth.getInstance();
                                        aut.createUserWithEmailAndPassword(emailAddress, password)
                                                .addOnCompleteListener(ActivitySignup.this, new OnCompleteListener<AuthResult>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<AuthResult> task) {

                                                        if (task.isSuccessful()) {
                                                            Log.e("DER", "hua");
                                                            Toast.makeText(ActivitySignup.this, "Account Created" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                                            finish();
                                                        }
                                                    }
                                                });
                                        Toast.makeText(getApplicationContext(), "Database entry created!", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(ActivitySignup.this, AppMainActivity.class));
                                    }

                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Toast.makeText(getApplicationContext(), "Database entry error!", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }

    public void generateUser(String username, String email)
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference users = database.getReference();
        User user = new User(username, email);
        users.child("users").child(username).setValue(user);
        Toast.makeText(ActivitySignup.this, "Created", Toast.LENGTH_SHORT).show();
    }
}
