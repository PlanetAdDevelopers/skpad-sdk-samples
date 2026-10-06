package com.skplanet.app.skpadbenefitsample.ui.pop;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.widget.ImageViewCompat;

import com.skplanet.app.skpadbenefitsample.R;
import com.skplanet.skpad.benefit.pop.feedutility.PopUtilityLayoutHandler;

/**
 * POP 피드 하단 유틸리티 영역을 직접 구성하는 예시
 *
 * PopConfig.Builder.popUtilityLayoutHandlerClass(CustomPopUtilityLayoutHandler.class)로 적용합니다.
 * 카메라, 갤러리, 전화, 지도, YouTube, 공유 바로가기 버튼을 추가합니다.
 */
public class CustomPopUtilityLayoutHandler extends PopUtilityLayoutHandler {

    private final Context context;

    public CustomPopUtilityLayoutHandler(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public void onLayoutCreated(@NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.custom_pop_utility_layout, parent, false);
        parent.addView(layout);

        // 1. 카메라 열기
        addUtilityButton(layout, android.R.drawable.ic_menu_camera, "카메라", () -> {
            Intent intent = new Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivitySafely(intent, "카메라 앱을 찾을 수 없습니다");
        });

        // 2. 갤러리 열기
        addUtilityButton(layout, android.R.drawable.ic_menu_gallery, "갤러리", () -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setType("image/*");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivitySafely(intent, "갤러리 앱을 찾을 수 없습니다");
        });

        // 3. 전화 다이얼 열기
        addUtilityButton(layout, android.R.drawable.sym_action_call, "전화", () -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivitySafely(intent, "전화 앱을 찾을 수 없습니다");
        });

        // 4. 지도 열기 (서울 검색)
        addUtilityButton(layout, com.skplanet.skpad.benefit.pop.R.drawable.skpad_ic_search, "지도", () -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=서울"));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivitySafely(intent, "지도 앱을 찾을 수 없습니다");
        });

        // 5. YouTube 앱 열기 (앱이 없으면 브라우저)
        addUtilityButton(layout, android.R.drawable.ic_media_play, "YouTube", () -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com"));
            intent.setPackage("com.google.android.youtube");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            // YouTube 앱이 없으면 브라우저로 열기
            if (intent.resolveActivity(context.getPackageManager()) == null) {
                intent.setPackage(null); // 패키지 제한 해제
            }
            startActivitySafely(intent, "YouTube를 열 수 없습니다");
        });

        // 6. 공유하기
        addUtilityButton(layout, android.R.drawable.ic_menu_share, "공유", () -> {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, "SKP Ad Benefit POP에서 공유합니다!");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Intent chooser = Intent.createChooser(intent, "공유하기");
            chooser.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivitySafely(chooser, "공유할 앱을 찾을 수 없습니다");
        });
    }

    private void addUtilityButton(LinearLayout layout, int iconResId, String description, Runnable onClick) {
        LayoutInflater inflater = LayoutInflater.from(context);
        ImageView button = (ImageView) inflater.inflate(R.layout.custom_pop_utility_item, layout, false);
        button.setImageResource(iconResId);
        button.setContentDescription(description);

        // 녹색 계열로 아이콘 색상 변경
        ImageViewCompat.setImageTintList(
                button,
                ColorStateList.valueOf(ContextCompat.getColor(context, android.R.color.holo_green_light))
        );

        button.setOnClickListener(v -> onClick.run());
        layout.addView(button);
    }

    private void startActivitySafely(Intent intent, String errorMessage) {
        try {
            context.startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
        }
    }
}
