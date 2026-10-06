package com.skplanet.app.skpadbenefitsample.ui.pop;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.skplanet.app.skpadbenefitsample.BaseAdActivity;

/**
 * POP 화면 Activity
 *
 * POP 옵션을 저장하면 PopFragment가 shouldRestartApp을 true로 설정합니다.
 * 이 경우 화면을 닫을 때 앱 프로세스를 재시작하여 MainApplication에서 새 PopConfig로 SDK를 다시 초기화하고,
 * 재시작 후 POP 화면으로 자동 이동합니다.
 */
public class PopActivity extends BaseAdActivity {

    public static boolean shouldRestartApp = false;

    @NonNull
    @Override
    protected Fragment createFragment() {
        return new PopFragment();
    }

    @NonNull
    @Override
    protected String getAdTitle() {
        return "POP";
    }

    @Override
    public void finish() {
        if (shouldRestartApp) {
            shouldRestartApp = false;
            // [샘플 앱 전용 – 실제 앱에서 사용하지 마세요]
            // POP 옵션은 SDK 초기화(PopConfig) 시점에만 반영되므로, 샘플에서는 옵션 테스트를 위해
            // 프로세스를 강제 종료하고 앱을 다시 시작합니다. 실제 앱은 PopConfig를 고정해 두고 재시작하지 않습니다.
            Intent intent = getPackageManager().getLaunchIntentForPackage(getPackageName());
            if (intent != null) {
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("navigate_to", "POP");
                startActivity(intent);
            }
            super.finish();
            Runtime.getRuntime().exit(0);
        } else {
            super.finish();
        }
    }
}
