package com.example.customerdocs;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import java.util.List;

public class CustomerListActivity extends AppCompatActivity {
    private final List<Customer> customers = new ArrayList<>();
    private CustomerAdapter adapter;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_list);
        db = FirebaseFirestore.getInstance();

        RecyclerView rv = findViewById(R.id.rvCustomers);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CustomerAdapter(customers);
        rv.setAdapter(adapter);

        ((Button) findViewById(R.id.btnDummy)).setOnClickListener(v -> addDummyData());
        loadCustomers();
    }

    private void loadCustomers() {
        db.collection("customers").get()
            .addOnSuccessListener(snap -> {
                customers.clear();
                for (QueryDocumentSnapshot d : snap) customers.add(d.toObject(Customer.class));
                adapter.notifyDataSetChanged();
                if (customers.isEmpty())
                    Toast.makeText(this, "No data. Tap 'Add Dummy Data'", Toast.LENGTH_SHORT).show();
            })
            .addOnFailureListener(e ->
                Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show());
    }

    private void addDummyData() {
        Customer[] dummy = {
            new Customer("Rahul Sharma", "9876543210", "12/05/1999", "rahul@gmail.com",
                "12, MG Road, Ahmedabad", "Aadhaar Card", "1234 5678 9012"),
            new Customer("Priya Patel", "9123456780", "03/11/2000", "priya@gmail.com",
                "45, SG Highway, Gandhinagar", "PAN Card", "ABCDE1234F"),
            new Customer("Amit Desai", "9988776655", "21/08/1998", "amit@gmail.com",
                "7, Ring Road, Surat", "Passport", "K1234567")
        };
        for (Customer c : dummy) db.collection("customers").add(c);
        Toast.makeText(this, "Dummy data added", Toast.LENGTH_SHORT).show();
        findViewById(R.id.rvCustomers).postDelayed(this::loadCustomers, 1200);
    }
}
