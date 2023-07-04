package com.skplanet.app.skpadbenefitsample;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.skplanet.skpad.benefit.presentation.LaunchInfo;
import com.skplanet.skpad.benefit.presentation.Launcher;
import com.skplanet.skpad.browser.SKPAdJavascriptInterface;

import java.io.Serializable;
import java.util.List;

public class CustomLauncher implements Serializable, Launcher {
    @Override
    public void launch(@NonNull Context context, @NonNull LaunchInfo launchInfo) {
        launch(context, launchInfo, null);
    }

    @Override
    public void launch(@NonNull Context context, @NonNull LaunchInfo launchInfo, @Nullable LauncherEventListener listener) {
        launch(context, launchInfo, listener, null);
    }

    @Override
    public void launch(@NonNull Context context, @NonNull LaunchInfo launchInfo, @Nullable LauncherEventListener listener, @Nullable List<Class<? extends SKPAdJavascriptInterface>> javascriptInterfaces) {
        Intent intent = new Intent(context, CustomInAppBrowserActivity.class);
        intent.putExtra(CustomInAppBrowserActivity.EXTRA_LANDING_URL, launchInfo.getUri().toString());
        context.startActivity(intent);
    }
}
