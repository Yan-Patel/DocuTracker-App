package com.example.customerdocs;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.Calendar;
import java.util.Locale;

public class CustomerFormActivity extends AppCompatActivity {
    private EditText etName, etPhone, etDob, etEmail, etAddress, etDocNumber;
    private Spinner spDocType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_form);

        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);
        etDob = findViewById(R.id.etDob);
        etEmail = findViewById(R.id.etEmail);
        etAddress = findViewById(R.id.etAddress);
        etDocNumber = findViewById(R.id.etDocNumber);
        spDocType = findViewById(R.id.spDocType);

        spDocType.setAdapter(new ArrayAdapter<>(this,
            android.R.layout.simple_spinner_dropdown_item,
            new String[]{"Aadhaar Card", "PAN Card", "Passport", "Driving License", "Voter ID"}));

        etDob.setFocusable(false);
        etDob.setOnClickListener(v -> {
            Calendar c = Calendar.getInstance();
            new DatePickerDialog(this, (view, y, m, d) ->
                etDob.setText(String.format(Locale.US, "%02d/%02d/%04d", d, m + 1, y)),
                c.get(Calendar.YEAR) - 20, c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
        });

        ((Button) findViewById(R.id.btnSubmit)).setOnClickListener(v -> save());
        ((Button) findViewById(R.id.btnView)).setOnClickListener(v ->
            startActivity(new Intent(this, CustomerListActivity.class)));
        ((Button) findViewById(R.id.btnLogout)).setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }

    private void save() {
        String name = s(etName), phone = s(etPhone), dob = s(etDob),
               email = s(etEmail), addr = s(etAddress), docNo = s(etDocNumber);

        if (name.isEmpty()) { etName.setError("Required"); return; }
        if (!phone.matches("\\d{10}")) { etPhone.setError("Enter 10 digit number"); return; }
        if (dob.isEmpty()) { etDob.setError("Select DOB"); return; }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) { etEmail.setError("Invalid email"); return; }
        if (addr.isEmpty()) { etAddress.setError("Required"); return; }
        if (docNo.isEmpty()) { etDocNumber.setError("Required"); return; }

        Customer c = new Customer(name, phone, dob, email, addr,
            spDocType.getSelectedItem().toString(), docNo);

        FirebaseFirestore.getInstance().collection("customers").add(c)
            .addOnSuccessListener(r -> {
                Toast.makeText(this, "Customer saved", Toast.LENGTH_SHORT).show();
                etName.setText(""); etPhone.setText(""); etDob.setText("");
                etEmail.setText(""); etAddress.setText(""); etDocNumber.setText("");
            })
            .addOnFailureListener(e ->
                Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show());
    }

    private String s(EditText e) { return e.getText().toString().trim(); }
}
