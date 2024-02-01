package com.midterm.pbl4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUp extends AppCompatActivity {
    FirebaseAuth mAuth;
    EditText s_password, s_username, s_email;
    Button signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();
        s_password = findViewById(R.id.editTextTextPasswordSignup);
//        s_username = findViewById(R.id.s_UserName);
        s_email = findViewById(R.id.editTextTextEmailAddressSignup);
        signup =findViewById(R.id.btnsignup);

        signup.setOnClickListener(view ->{
            createUser();
        });
    }

    private void createUser() {
        String email, username, password;
        email = s_email.getText().toString();
//                username = String.valueOf(s_username.getText());
        password = s_password.getText().toString();
        try {
            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                throw new Exception("Email và password không được để trống.");
            }
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(SignUp.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(SignUp.this, LoginActivity.class));
                            }else{
                                Toast.makeText(SignUp.this, "Registration Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

            // Thực hiện hành động tiếp theo
        } catch (Exception e) {
            // Hiển thị thông báo lỗi cho người dùng
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        finish();
    }
}