package com.skplanet.app.skpadscreensample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.skplanet.skpadscreen.sdk.SKPAdScreen;
import com.skplanet.skpadscreen.sdk.UserProfile;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "SKPADScreenApp";


    private SKPAdScreen.OnActivationListener onActivationListener = new SKPAdScreen.OnActivationListener() {
        @Override
        public void onActivated() {
            Toast.makeText(MainActivity.this, "Screen Acivated!", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onDeactivated() {
            Toast.makeText(MainActivity.this, "Screen Deactivated!", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 앱 실행시 처음 실행되는 액티비티에 추가해 준다.
        SKPAdScreen.getInstance().launch();

        showOverlayPermissionGuideDialogIfNeeded();

        Button activateButton = findViewById(R.id.locker_activate);
        activateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setupUserProfile();
                setActivationListener();

                SKPAdScreen.getInstance().setAutoplayType(SKPAdScreen.AutoplayType.ON_WIFI);
                // 스크린 SDK 활성화
                SKPAdScreen.getInstance().activate();
            }
        });

        Button deactivateButton = findViewById(R.id.locker_deactivate);
        deactivateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SKPAdScreen.getInstance().deactivate();
            }
        });

        Button logoutButton = findViewById(R.id.locker_logout);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SKPAdScreen.getInstance().logout();
            }
        });

        Button deactivateUntilRebootButton = findViewById(R.id.btnDeactivateUntilReboot);
        deactivateUntilRebootButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SKPAdScreen.getInstance().deactivateUntilReboot();
            }
        });

        Button snoozeButton = findViewById(R.id.btnSnooze);
        snoozeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SKPAdScreen.getInstance().snooze(Constants.DEFAULT_SNOOZE_SECONDS);
            }
        });
    }

    private void setupUserProfile() {
        UserProfile userProfile = SKPAdScreen.getInstance().getUserProfile();

        userProfile.setUserId(Constants.USER_ID);
        // 포인트 적립을 위해서는 setUserId를 반드시 호출해야 함

        // 캠페인 할당을 위한 타게팅 정보
        userProfile.setBirthYear(1985);
        userProfile.setGender(Constants.GENDER);
        userProfile.setRegion(Constants.REGION);
        userProfile.setFavoriteCategories(Arrays.asList(Constants.FAVORITE_CATEGORY.split(",")));
    }

    private void setActivationListener() {
        SKPAdScreen.getInstance().registerOnActivationListener(onActivationListener);
    }

    private void showOverlayPermissionGuideDialogIfNeeded() {
        SKPAdScreen.getInstance().showOverlayPermissionGuideDialogIfNeeded(new SKPAdScreen.OverlayPermissionListener() {
            @Override
            public void onGranted() {
                Toast.makeText(MainActivity.this, "Permission has been granted.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailed(Throwable error) {
                SKPAdScreen.getInstance().deactivate();
                Toast.makeText(MainActivity.this, "Deactivating lockscreen because granting permission has been canceled.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}