package com.skplanet.app.skpadbenefitsample.ui.pop;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import com.skplanet.app.skpadbenefitsample.Constants;
import com.skplanet.app.skpadbenefitsample.R;
import com.skplanet.app.skpadbenefitsample.data.OptionsPreferences;
import com.skplanet.skpad.benefit.pop.SKPAdPop;

/**
 * POP 광고 화면
 *
 * POP 표시/종료와 POP 옵션 설정을 제공합니다.
 * POP 옵션은 SDK 초기화 시점(MainApplication)의 PopConfig에 반영되므로,
 * "설정 저장" 시 옵션을 디스크에 동기 저장한 뒤 앱을 재시작합니다(PopActivity.shouldRestartApp).
 */
public class PopFragment extends Fragment {

    private static final String TAG = "PopFragment";
    private static final int REQUEST_CODE_OVERLAY = 100;

    @Nullable private OptionsPreferences optionsPrefs;

    @Nullable private TextView tvStatus;
    @Nullable private Button btnShow;
    @Nullable private Button btnHide;
    @Nullable private Button btnSaveSettings;

    // 설정 UI 컴포넌트들
    @Nullable private Spinner spinnerIdleMode;
    @Nullable private EditText etIdleTime;
    @Nullable private EditText etPreviewDuration;
    @Nullable private SwitchCompat swCustomToolbar;
    @Nullable private SwitchCompat swCustomHeader;
    @Nullable private SwitchCompat swCustomIcon;
    @Nullable private SwitchCompat swCustomAdMessage;
    @Nullable private SwitchCompat swCustomFeedback;
    @Nullable private SwitchCompat swUtilityHandler;

    @Nullable private SKPAdPop skpAdPop;

    private boolean isSpinnerInitialized = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pop, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        optionsPrefs = new OptionsPreferences(requireContext());

        tvStatus = view.findViewById(R.id.tv_status);
        btnShow = view.findViewById(R.id.btn_show);
        btnHide = view.findViewById(R.id.btn_hide);
        btnSaveSettings = view.findViewById(R.id.btn_save_settings);
        spinnerIdleMode = view.findViewById(R.id.spinner_idle_mode);
        etIdleTime = view.findViewById(R.id.et_idle_time);
        etPreviewDuration = view.findViewById(R.id.et_preview_duration);
        swCustomToolbar = view.findViewById(R.id.sw_custom_toolbar);
        swCustomHeader = view.findViewById(R.id.sw_custom_header);
        swCustomIcon = view.findViewById(R.id.sw_custom_icon);
        swCustomAdMessage = view.findViewById(R.id.sw_custom_ad_message);
        swCustomFeedback = view.findViewById(R.id.sw_custom_feedback);
        swUtilityHandler = view.findViewById(R.id.sw_utility_handler);

        // 저장된 옵션 로드 및 저장 리스너 설정 (항상 실행되어야 함)
        loadSavedOptions();
        setupOptionsSaveListeners();

        // 설정 저장 버튼
        if (btnSaveSettings != null) {
            btnSaveSettings.setOnClickListener(v -> saveSettingsAndRestart());
        }

        final String unitId = Constants.POP_UNIT_ID;

        if (!SKPAdPop.hasPermission(requireContext())) {
            if (tvStatus != null) tvStatus.setText("오버레이 권한이 필요합니다. 버튼을 눌러 권한을 허용하세요.");
            if (btnShow != null) {
                btnShow.setText("권한 요청");
                btnShow.setOnClickListener(v -> SKPAdPop.requestPermission(requireActivity(), REQUEST_CODE_OVERLAY));
            }
            if (btnHide != null) btnHide.setEnabled(false);
            return;
        }

        bindPopButtons(unitId);
        updateStatus(unitId);
    }

    private void bindPopButtons(String unitId) {
        if (btnShow != null) btnShow.setOnClickListener(v -> showPop(unitId));
        if (btnHide != null) btnHide.setOnClickListener(v -> hidePop(unitId));
    }

    private void loadSavedOptions() {
        OptionsPreferences prefs = optionsPrefs;
        if (prefs == null) return;
        if (spinnerIdleMode != null) spinnerIdleMode.setSelection(prefs.getPopIdleMode());
        if (etIdleTime != null) etIdleTime.setText(String.valueOf(prefs.getPopIdleTime()));
        if (etPreviewDuration != null) etPreviewDuration.setText(String.valueOf(prefs.getPopPreviewDuration()));
        if (swCustomToolbar != null) swCustomToolbar.setChecked(prefs.getPopCustomToolbar());
        if (swCustomHeader != null) swCustomHeader.setChecked(prefs.getPopCustomHeader());
        if (swCustomIcon != null) swCustomIcon.setChecked(prefs.getPopCustomIcon());
        if (swCustomAdMessage != null) swCustomAdMessage.setChecked(prefs.getPopCustomAdMessage());
        if (swCustomFeedback != null) swCustomFeedback.setChecked(prefs.getPopCustomFeedback());
        if (swUtilityHandler != null) swUtilityHandler.setChecked(prefs.getPopUtilityHandler());

        Log.d(TAG, "loadSavedOptions - idleTime: " + prefs.getPopIdleTime() + ", previewDuration: " + prefs.getPopPreviewDuration());
        Log.d(TAG, "loadSavedOptions - CustomHeader: " + prefs.getPopCustomHeader() + ", CustomFeedback: " + prefs.getPopCustomFeedback()
                + ", Utility: " + prefs.getPopUtilityHandler());
    }

    private void setupOptionsSaveListeners() {
        OptionsPreferences prefs = optionsPrefs;
        if (prefs == null) return;

        if (spinnerIdleMode != null) {
            spinnerIdleMode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    // Spinner는 리스너 설정 시 초기 콜백이 자동 발생하므로,
                    // 첫 번째 콜백은 무시하여 저장된 값이 0으로 덮어쓰이는 것을 방지
                    if (!isSpinnerInitialized) {
                        isSpinnerInitialized = true;
                        return;
                    }
                    prefs.setPopIdleMode(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }

        if (etIdleTime != null) {
            etIdleTime.setOnFocusChangeListener((v, hasFocus) -> {
                Long value = parseLongOrNull(etIdleTime);
                if (!hasFocus && value != null) prefs.setPopIdleTime(value);
            });
        }
        if (etPreviewDuration != null) {
            etPreviewDuration.setOnFocusChangeListener((v, hasFocus) -> {
                Long value = parseLongOrNull(etPreviewDuration);
                if (!hasFocus && value != null) prefs.setPopPreviewDuration(value);
            });
        }

        if (swCustomToolbar != null) swCustomToolbar.setOnCheckedChangeListener((b, c) -> prefs.setPopCustomToolbar(c));
        if (swCustomHeader != null) swCustomHeader.setOnCheckedChangeListener((b, c) -> prefs.setPopCustomHeader(c));
        if (swCustomIcon != null) swCustomIcon.setOnCheckedChangeListener((b, c) -> prefs.setPopCustomIcon(c));
        if (swCustomAdMessage != null) swCustomAdMessage.setOnCheckedChangeListener((b, c) -> prefs.setPopCustomAdMessage(c));
        if (swCustomFeedback != null) swCustomFeedback.setOnCheckedChangeListener((b, c) -> prefs.setPopCustomFeedback(c));
        if (swUtilityHandler != null) swUtilityHandler.setOnCheckedChangeListener((b, c) -> prefs.setPopUtilityHandler(c));
    }

    @SuppressWarnings("deprecation")
    private void showPop(String unitId) {
        Log.d(TAG, "showPop() called for unitId: " + unitId);
        if (tvStatus != null) tvStatus.setText("POP 광고 로딩 중...");

        // 기존 POP가 있다면 먼저 제거
        if (SKPAdPop.isPopControlServiceRunning(requireContext())) {
            Log.d(TAG, "POP service is running, removing it first...");
            if (tvStatus != null) tvStatus.setText("기존 POP 종료 중...");
            removePop(unitId);

            // 서비스가 완전히 종료될 때까지 충분히 대기 후 재시작
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                if (!isAdded()) return;
                Log.d(TAG, "Restarting POP...");
                launchPop(unitId);
            }, 1000);
        } else {
            Log.d(TAG, "No POP service running, starting fresh...");
            launchPop(unitId);
        }
    }

    private void launchPop(String unitId) {
        skpAdPop = new SKPAdPop(requireContext(), unitId);
        Log.d(TAG, "Calling preloadAndShowPop()...");
        skpAdPop.preloadAndShowPop();
        if (tvStatus != null) tvStatus.setText("POP 표시 중");
    }

    private void hidePop(String unitId) {
        Log.d(TAG, "hidePop() called");
        removePop(unitId);
        skpAdPop = null;
        if (tvStatus != null) tvStatus.setText("POP 종료됨");
    }

    private void removePop(String unitId) {
        if (skpAdPop != null) {
            skpAdPop.removePop(requireContext());
        } else {
            new SKPAdPop(requireContext(), unitId).removePop(requireContext());
        }
    }

    private void updateStatus(String unitId) {
        OptionsPreferences prefs = optionsPrefs;
        if (prefs == null || tvStatus == null) return;
        boolean isRunning = SKPAdPop.isPopControlServiceRunning(requireContext());
        String options = "(idle: " + prefs.getPopIdleTime() + "ms, preview: " + prefs.getPopPreviewDuration() + "ms)";
        tvStatus.setText(isRunning ? "POP 표시 중 " + options : "POP Unit: " + unitId + " " + options);
    }

    private void saveSettingsAndRestart() {
        Log.d(TAG, "saveSettingsAndRestart() called");

        Long idleTimeInput = parseLongOrNull(etIdleTime);
        Long previewDurationInput = parseLongOrNull(etPreviewDuration);
        long idleTime = idleTimeInput != null ? idleTimeInput : 5000L;
        long previewDuration = previewDurationInput != null ? previewDurationInput : 5000L;

        Log.d(TAG, "Saving options - idleTime: " + idleTime + ", previewDuration: " + previewDuration);

        // 동기적으로 저장 (commit 사용) - 프로세스 종료 전 디스크에 확실히 기록
        if (optionsPrefs != null) {
            optionsPrefs.commitPopOptions(
                    spinnerIdleMode != null ? spinnerIdleMode.getSelectedItemPosition() : 0,
                    idleTime,
                    5000L,
                    previewDuration,
                    isChecked(swCustomToolbar),
                    isChecked(swCustomHeader),
                    isChecked(swCustomAdMessage),
                    isChecked(swCustomFeedback),
                    isChecked(swCustomIcon),
                    isChecked(swUtilityHandler)
            );
        }

        Log.d(TAG, "Settings committed to disk. Restarting app...");

        // 기존 POP 완전히 제거
        if (SKPAdPop.isPopControlServiceRunning(requireContext())) {
            removePop(Constants.POP_UNIT_ID);
            skpAdPop = null;
        }

        PopActivity.shouldRestartApp = true;
        requireActivity().finish();
    }

    @Override
    public void onResume() {
        super.onResume();
        // 오버레이 권한 허용 후 돌아온 경우 버튼 상태를 갱신
        if (SKPAdPop.hasPermission(requireContext())) {
            String unitId = Constants.POP_UNIT_ID;
            if (btnShow != null) {
                btnShow.setText("POP 표시");
                btnShow.setEnabled(true);
            }
            if (btnHide != null) btnHide.setEnabled(true);
            bindPopButtons(unitId);
            updateStatus(unitId);
        }
    }

    private static boolean isChecked(@Nullable SwitchCompat sw) {
        return sw != null && sw.isChecked();
    }

    @Nullable
    private static Long parseLongOrNull(@Nullable EditText editText) {
        if (editText == null) return null;
        try {
            return Long.parseLong(editText.getText().toString().trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        SwitchCompat[] switches = {swCustomToolbar, swCustomHeader, swCustomIcon,
                swCustomAdMessage, swCustomFeedback, swUtilityHandler};
        for (SwitchCompat sw : switches) {
            if (sw != null) sw.setOnCheckedChangeListener(null);
        }
        if (spinnerIdleMode != null) spinnerIdleMode.setOnItemSelectedListener(null);
        if (etIdleTime != null) etIdleTime.setOnFocusChangeListener(null);
        if (etPreviewDuration != null) etPreviewDuration.setOnFocusChangeListener(null);
        if (btnShow != null) btnShow.setOnClickListener(null);
        if (btnHide != null) btnHide.setOnClickListener(null);
        if (btnSaveSettings != null) btnSaveSettings.setOnClickListener(null);

        tvStatus = null;
        btnShow = null;
        btnHide = null;
        btnSaveSettings = null;
        spinnerIdleMode = null;
        etIdleTime = null;
        etPreviewDuration = null;
        swCustomToolbar = null;
        swCustomHeader = null;
        swCustomIcon = null;
        swCustomAdMessage = null;
        swCustomFeedback = null;
        swUtilityHandler = null;

        optionsPrefs = null;
        skpAdPop = null;
    }
}
