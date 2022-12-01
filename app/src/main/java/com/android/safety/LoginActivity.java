package com.android.safety;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.safety.databinding.ActivityLoginBinding;

import java.util.List;
import java.util.Objects;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;


public class LoginActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {
    private static final int RC_CALL_PHONE = 101;
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        handleClickListeners();
    }

    private void handleClickListeners() {
        binding.btnStop.setOnClickListener(view -> {
            handlePermissionAndCall();
        });
        binding.btnLogin.setOnClickListener(view -> {
            handleLogin();
        });
        binding.btnSignup.setOnClickListener(view -> {

    });
    }

    private void handleLogin() {
        String userName = Objects.requireNonNull(binding.evUserName.getText()).toString().trim();
        String password = Objects.requireNonNull(binding.evPassword.getText()).toString().trim();
        if (userName.equalsIgnoreCase("test") && password.equalsIgnoreCase("test@123")) {
            startActivity(new Intent(this, MainActivity.class));
        } else {
            Toast.makeText(this, "Incorrect username or password.", Toast.LENGTH_SHORT).show();
        }
    }

    private void handlePermissionAndCall() {
        String[] perms = {Manifest.permission.CALL_PHONE};
        if (EasyPermissions.hasPermissions(this, perms)) {
           callEmergency();
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.please_provide_call_permission),
                    RC_CALL_PHONE, perms);
        }

    }

    @AfterPermissionGranted(RC_CALL_PHONE)
    private void callEmergency() {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:+911"));
        startActivity(intent);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        callEmergency();
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
}