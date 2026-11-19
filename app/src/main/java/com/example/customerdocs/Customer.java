package com.example.customerdocs;

public class Customer {
    public String name, phone, dob, email, address, docType, docNumber;

    public Customer() { } // required by Firestore

    public Customer(String name, String phone, String dob, String email,
                    String address, String docType, String docNumber) {
        this.name = name; this.phone = phone; this.dob = dob; this.email = email;
        this.address = address; this.docType = docType; this.docNumber = docNumber;
    }
}
