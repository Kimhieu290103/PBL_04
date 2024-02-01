package com.midterm.pbl4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    EditText l_password, l_username, l_email;
    Button login, forgotpass;
    LinearLayout lnSignup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        l_password = findViewById(R.id.editTextTextPassword);
//        l_username = findViewById(R.id.l_UserName);
        l_email = findViewById(R.id.editTextTextEmailAddress);
        login =findViewById(R.id.btnlogin);
        forgotpass =findViewById(R.id.forgotpass);
        lnSignup = findViewById(R.id.llSignup);
        lnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUp.class);
                startActivity(intent);
            }
        });
        forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgotPass.class);
                startActivity(intent);
            }
        });
        login.setOnClickListener(view -> {
            loginUser();
        });
    }

    private void loginUser() {
        String email, username, password;
        email = l_email.getText().toString();
        password = l_password.getText().toString();
        try {
            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                throw new Exception("Email và password không được để trống.");
            }
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, "User Login successfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(LoginActivity.this, MainActivity2.class));
                            } else {
                                Toast.makeText(LoginActivity.this, "Login Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

            // Thực hiện hành động tiếp theo
        } catch (Exception e) {
            // Hiển thị thông báo lỗi cho người dùng
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}