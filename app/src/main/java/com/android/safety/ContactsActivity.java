package com.android.safety;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;

import com.android.safety.databinding.ActivityContactsBinding;
import com.android.safety.locations.Contact;
import com.android.safety.locations.Feature;
import com.android.safety.locations.LocationData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import android.provider.ContactsContract;
import android.util.Log;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.database.Cursor;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.pm.PackageManager;
import android.os.Build;
import android.Manifest;
import android.widget.Toast;

public class ContactsActivity extends AppCompatActivity {

    private ActivityContactsBinding binding;
    private FirebaseAuth firebaseAuth;
    private ArrayList<Contact> contactsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityContactsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        binding.layoutHeader.setIsMainScreen(false);
        firebaseAuth = FirebaseAuth.getInstance();
        contactsList = new ArrayList<>();
        handleClickListeners();

        boolean hasPermissions = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED;
        if (Build.VERSION.SDK_INT >= 23) {
            String[] PERMISSIONS = {android.Manifest.permission.READ_CONTACTS};
            if (!hasPermissions) {
                ActivityCompat.requestPermissions(this, PERMISSIONS, 1);
            } else {
                getContactList();
            }
        } else {
            getContactList();
        }
    }

    private void handleClickListeners() {
        binding.layoutHeader.ivBack.setOnClickListener(view -> finish());

        binding.layoutHeader.tvLogout.setOnClickListener(view -> {
            firebaseAuth.signOut();
            startActivity(new Intent(ContactsActivity.this, LoginActivity.class));
            finish();
        });
    }

    private void getContactList() {
        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);


        if ((cur != null ? cur.getCount() : 0) > 0) {
            while (cur.moveToNext()) {
                @SuppressLint("Range") String id = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts._ID));
                @SuppressLint("Range") String name = cur.getString(cur.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME));
                int num = cur.getColumnIndex(
                        ContactsContract.Contacts.HAS_PHONE_NUMBER);
                if (cur.getInt(num) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        @SuppressLint("Range") String phoneNo = pCur.getString(pCur.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER));
                        Log.i("TAG", "Name: " + name);
                        Log.i("TAG", "Phone Number: " + phoneNo);
                        final Contact contact = new Contact();
                        contact.setName(name);
                        contact.setPhoneNumber(phoneNo);
                        contactsList.add(contact);

                    }

                    pCur.close();
                }
            }
        }
        if (cur != null) {
            cur.close();
        }
        ContactsRecyclerViewAdapter myRecyclerViewAdapter = new ContactsRecyclerViewAdapter(contactsList, ContactsActivity.this);
        binding.setMyAdapter(myRecyclerViewAdapter);

        if (contactsList.size() > 0) {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myDatabase = database.getReference("users");
            if (firebaseAuth.getCurrentUser() != null && firebaseAuth.getCurrentUser().getEmail() != null) {
                myDatabase.orderByChild("Email").equalTo(firebaseAuth.getCurrentUser().getEmail()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists() && snapshot.getChildrenCount() >= 1) {
                            for (DataSnapshot child : snapshot.getChildren()) {
                                Log.d("User key", child.getKey());
                                snapshot.getRef().child(Objects.requireNonNull(child.getKey())).child("contacts").setValue(contactsList).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(ContactsActivity.this, "Contacts updated!", Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        }
    }
}