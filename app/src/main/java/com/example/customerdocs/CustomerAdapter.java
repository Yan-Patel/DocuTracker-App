package com.example.customerdocs;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.VH> {
    private final List<Customer> list;
    public CustomerAdapter(List<Customer> list) { this.list = list; }

    static class VH extends RecyclerView.ViewHolder {
        TextView name, details, doc;
        VH(View v) {
            super(v);
            name = v.findViewById(R.id.tvName);
            details = v.findViewById(R.id.tvDetails);
            doc = v.findViewById(R.id.tvDoc);
        }
    }

    @NonNull @Override
    public VH onCreateViewHolder(@NonNull ViewGroup p, int t) {
        return new VH(LayoutInflater.from(p.getContext()).inflate(R.layout.item_customer, p, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int i) {
        Customer c = list.get(i);
        h.name.setText(c.name);
        h.details.setText("Phone: " + c.phone + "\nDOB: " + c.dob + "\nEmail: " + c.email
            + "\nAddress: " + c.address);
        h.doc.setText("Document: " + c.docType + " - " + c.docNumber);
    }

    @Override public int getItemCount() { return list.size(); }
}
