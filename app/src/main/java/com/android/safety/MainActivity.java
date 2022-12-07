package com.android.safety;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;

import com.android.safety.databinding.ActivityLoginBinding;
import com.android.safety.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        handleClickListeners();
    }

    private void getContactList() {
        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);

        if ((cur != null ? cur.getCount() : 0) > 0) {
            while (cur != null && cur.moveToNext()) {
                @SuppressLint("Range") String id = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts._ID));
                @SuppressLint("Range") String name = cur.getString(cur.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME));
                Integer num = cur.getColumnIndex(
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
                    }
                    pCur.close();
                }
            }
        }
        if(cur!=null){
            cur.close();
        }
    }

    private void handleClickListeners() {
        binding.layoutHeader.ivBack.setOnClickListener(view -> {
            finish();
        });

        binding.btnCall.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "8545784151"));
            startActivity(intent);
        });

        binding.btnTripWithFriends.setOnClickListener(view -> {
            try {
                String uri = "uber://?action=setPickup&pickup=my_location";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(uri));
                startActivity(intent);
            } catch (Exception e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=com.ubercab")));
            }
        });

        binding.btnRoutes.setOnClickListener(view -> {
            // Create a Uri from an intent string. Use the result to create an Intent.
            Uri gmmIntentUri = Uri.parse("geo:0,0?q=iconic places");

            // Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            // Make the Intent explicit by setting the Google Maps package
            mapIntent.setPackage("com.google.android.apps.maps");

            // Attempt to start an activity that can handle the Intent
            startActivity(mapIntent);
        });

        binding.btnShareTrip.setOnClickListener(view -> {
            String uri = "https://www.google.com/maps/dir/43.2345,-76.2345/43.12345,-76.12345" ;

            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String ShareSub = "Here is my location";
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, ShareSub);
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, uri);
            startActivity(Intent.createChooser(sharingIntent, "Share via"));
        });

        binding.btnCallList.setOnClickListener(view -> {
            boolean hasPermissions = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED;
            if (Build.VERSION.SDK_INT >= 23) {
                String[] PERMISSIONS = {android.Manifest.permission.READ_CONTACTS};
                if (!hasPermissions) {
                    ActivityCompat.requestPermissions((Activity) this, PERMISSIONS, 1);
                } else {
                    getContactList();
                }
            } else {
                getContactList();
            }

        });
    }
}