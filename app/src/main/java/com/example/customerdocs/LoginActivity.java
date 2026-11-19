package com.example.customerdocs;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private EditText etEmail, etPassword;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) { openForm(); return; }

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        Button btnLogin = findViewById(R.id.btnLogin);
        Button btnSignup = findViewById(R.id.btnSignup);

        btnLogin.setOnClickListener(v -> {
            if (!valid()) return;
            auth.signInWithEmailAndPassword(text(etEmail), text(etPassword))
                .addOnSuccessListener(r -> openForm())
                .addOnFailureListener(e -> toast("Login failed: " + e.getMessage()));
        });

        btnSignup.setOnClickListener(v -> {
            if (!valid()) return;
            auth.createUserWithEmailAndPassword(text(etEmail), text(etPassword))
                .addOnSuccessListener(r -> { toast("Account created"); openForm(); })
                .addOnFailureListener(e -> toast("Sign up failed: " + e.getMessage()));
        });
    }

    private boolean valid() {
        if (!Patterns.EMAIL_ADDRESS.matcher(text(etEmail)).matches()) {
            etEmail.setError("Enter valid email"); return false;
        }
        if (text(etPassword).length() < 6) {
            etPassword.setError("Min 6 characters"); return false;
        }
        return true;
    }

    private String text(EditText e) { return e.getText().toString().trim(); }
    private void toast(String m) { Toast.makeText(this, m, Toast.LENGTH_LONG).show(); }
    private void openForm() {
        startActivity(new Intent(this, CustomerFormActivity.class));
        finish();
    }
}
