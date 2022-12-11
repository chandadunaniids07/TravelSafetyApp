package com.android.safety;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.safety.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {

    private ActivitySignUpBinding binding;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        handleClickListeners();

        firebaseAuth = FirebaseAuth.getInstance();
    }

    private void handleClickListeners() {
        binding.btnLogin.setOnClickListener(view -> {
            startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            finish();
        });
        binding.btnSignup.setOnClickListener(view -> {
            handleSignup();
        });
    }

    private void handleSignup() {
        binding.evUserName.setError(null);
        binding.evPassword.setError(null);
        binding.evEmail.setError(null);
        binding.evConfPassword.setError(null);

        String userName = Objects.requireNonNull(binding.evUserName.getText()).toString().trim();
        String password = Objects.requireNonNull(binding.evPassword.getText()).toString().trim();
        String email = Objects.requireNonNull(binding.evEmail.getText()).toString().trim();
        String confPassword = Objects.requireNonNull(binding.evConfPassword.getText()).toString().trim();

        if(userName.length() == 0) {
            binding.evUserName.setError("Please enter username");
        } else if(email.length() == 0) {
            binding.evEmail.setError("Please enter email");
        } else if(password.length() < 8) {
            binding.evPassword.setError("Password must be at least 8 characters long");
        } else if(confPassword.length() == 0) {
            binding.evConfPassword.setError("Please enter confirm password");
        } else if(!password.equalsIgnoreCase(confPassword)) {
            binding.evConfPassword.setError("Password and confirm password must be same");
        } else {

            firebaseAuth.createUserWithEmailAndPassword(binding.evEmail.getText().toString(), binding.evPassword.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            //checking if success
                            if(task.isSuccessful()){
                                //display some message here
                                Toast.makeText(SignUpActivity.this,"Successfully registered",Toast.LENGTH_LONG).show();
                                FirebaseDatabase database = FirebaseDatabase.getInstance();

                                HashMap<String, String> dataMap = new HashMap<String, String>();
                                dataMap.put("Username", binding.evUserName.getText().toString());
                                dataMap.put("Email", binding.evEmail.getText().toString());
                                dataMap.put("Key", binding.evEmail.getText().toString());
                                dataMap.put("Password", binding.evPassword.getText().toString());

                                DatabaseReference myDatabase = database.getReference("users");
                                myDatabase.push().setValue(dataMap);

                                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                                finish();
                            }else{
                                //display some message here
                                Toast.makeText(SignUpActivity.this,"Registration Error",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
    }

}