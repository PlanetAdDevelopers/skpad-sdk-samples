package com.skplanet.app.skpadbenefitsample.data;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 샘플 앱의 모든 UI 옵션을 SharedPreferences로 관리하는 래퍼 클래스
 *
 * 각 광고 타입별 설정, POP 옵션, 커스터마이징 옵션 등을 저장하고 로드합니다.
 * - 각 옵션의 setter는 즉시 apply()로 저장합니다.
 * - POP 옵션은 앱 재시작 전에 commitPopOptions()로 디스크에 동기 저장합니다.
 * - resetToDefault()로 모든 옵션을 기본값으로 되돌립니다.
 */
public class OptionsPreferences {

    private static final String PREFS_NAME = "benefit_compact_options";
    private static final String KEY_VIDEO_AUTOPLAY = "video_autoplay";
    private static final String KEY_POP_IDLE_MODE = "pop_idle_mode";
    private static final String KEY_POP_IDLE_TIME = "pop_idle_time";
    private static final String KEY_POP_PREVIEW_INTERVAL = "pop_preview_interval";
    private static final String KEY_POP_PREVIEW_DURATION = "pop_preview_duration";
    private static final String KEY_POP_CUSTOM_TOOLBAR = "pop_custom_toolbar";
    private static final String KEY_POP_CUSTOM_HEADER = "pop_custom_header";
    private static final String KEY_POP_CUSTOM_AD_MESSAGE = "pop_custom_ad_message";
    private static final String KEY_POP_CUSTOM_FEEDBACK = "pop_custom_feedback";
    private static final String KEY_POP_CUSTOM_ICON = "pop_custom_icon";
    private static final String KEY_POP_UTILITY_HANDLER = "pop_utility_handler";
    private static final String KEY_NO_EDGE_CUSTOM_ADAPTER = "no_edge_custom_adapter";
    private static final String KEY_CAROUSEL_COUNT = "carousel_count";
    private static final String KEY_CAROUSEL_DURATION = "carousel_duration";
    private static final String KEY_CAROUSEL_SIDE_PEEK = "carousel_side_peek";
    private static final String KEY_CAROUSEL_ITEM_SPACING = "carousel_item_spacing";
    private static final String KEY_CAROUSEL_LOOP = "carousel_loop";
    private static final String KEY_CAROUSEL_INDICATOR = "carousel_indicator";
    private static final String KEY_CAROUSEL_AUTO_PAGING = "carousel_auto_paging";
    private static final String KEY_FEED_INQUIRY = "feed_inquiry";
    private static final String KEY_FEED_GRID_LAYOUT = "feed_grid_layout";
    private static final String KEY_DIALOG_TOP_ICON = "dialog_top_icon";
    private static final String KEY_DIALOG_SHOW_INQUIRY = "dialog_show_inquiry";
    private static final String KEY_DIALOG_BG_COLOR = "dialog_bg_color";
    private static final String KEY_DIALOG_TITLE_COLOR = "dialog_title_color";
    private static final String KEY_DIALOG_CTA_ICON = "dialog_cta_icon";
    private static final String KEY_DIALOG_CTA_BG_COLOR = "dialog_cta_bg_color";
    private static final String KEY_BOTTOMSHEET_AD_COUNT = "bottomsheet_ad_count";
    private static final String KEY_BOTTOMSHEET_TOP_ICON = "bottomsheet_top_icon";
    private static final String KEY_BOTTOMSHEET_SHOW_INQUIRY = "bottomsheet_show_inquiry";
    private static final String KEY_BOTTOMSHEET_BG_COLOR = "bottomsheet_bg_color";
    private static final String KEY_BOTTOMSHEET_TITLE_COLOR = "bottomsheet_title_color";
    private static final String KEY_BOTTOMSHEET_CTA_ICON = "bottomsheet_cta_icon";
    private static final String KEY_BOTTOMSHEET_CTA_BG_COLOR = "bottomsheet_cta_bg_color";
    private static final String KEY_FULLSCREEN_SHOW_INQUIRY = "fullscreen_show_inquiry";
    private static final String KEY_FULLSCREEN_TITLE_COLOR = "fullscreen_title_color";
    private static final String KEY_FULLSCREEN_CUSTOM_ADAPTER = "fullscreen_custom_adapter";
    private static final String KEY_FULLSCREEN_ERROR_VIEW = "fullscreen_error_view";
    public static final int DEFAULT_VIDEO_AUTOPLAY = 0;
    public static final int DEFAULT_POP_IDLE_MODE = 0;
    public static final long DEFAULT_POP_IDLE_TIME = 5000L;
    public static final long DEFAULT_POP_PREVIEW_INTERVAL = 5000L;
    public static final long DEFAULT_POP_PREVIEW_DURATION = 5000L;
    public static final boolean DEFAULT_POP_CUSTOM_TOOLBAR = false;
    public static final boolean DEFAULT_POP_CUSTOM_HEADER = false;
    public static final boolean DEFAULT_POP_CUSTOM_AD_MESSAGE = false;
    public static final boolean DEFAULT_POP_CUSTOM_FEEDBACK = false;
    public static final boolean DEFAULT_POP_CUSTOM_ICON = false;
    public static final boolean DEFAULT_POP_UTILITY_HANDLER = false;
    public static final boolean DEFAULT_NO_EDGE_CUSTOM_ADAPTER = false;
    public static final int DEFAULT_CAROUSEL_COUNT = 4;
    public static final int DEFAULT_CAROUSEL_DURATION = 2000;
    public static final int DEFAULT_CAROUSEL_SIDE_PEEK = 0;
    public static final int DEFAULT_CAROUSEL_ITEM_SPACING = 0;
    public static final boolean DEFAULT_CAROUSEL_LOOP = true;
    public static final boolean DEFAULT_CAROUSEL_INDICATOR = true;
    public static final boolean DEFAULT_CAROUSEL_AUTO_PAGING = false;
    public static final boolean DEFAULT_FEED_INQUIRY = false;
    public static final boolean DEFAULT_FEED_GRID_LAYOUT = false;
    public static final boolean DEFAULT_DIALOG_TOP_ICON = false;
    public static final boolean DEFAULT_DIALOG_SHOW_INQUIRY = true;
    public static final boolean DEFAULT_DIALOG_BG_COLOR = false;
    public static final boolean DEFAULT_DIALOG_TITLE_COLOR = false;
    public static final boolean DEFAULT_DIALOG_CTA_ICON = false;
    public static final boolean DEFAULT_DIALOG_CTA_BG_COLOR = false;
    public static final int DEFAULT_BOTTOMSHEET_AD_COUNT = 3;
    public static final boolean DEFAULT_BOTTOMSHEET_TOP_ICON = false;
    public static final boolean DEFAULT_BOTTOMSHEET_SHOW_INQUIRY = true;
    public static final boolean DEFAULT_BOTTOMSHEET_BG_COLOR = false;
    public static final boolean DEFAULT_BOTTOMSHEET_TITLE_COLOR = false;
    public static final boolean DEFAULT_BOTTOMSHEET_CTA_ICON = false;
    public static final boolean DEFAULT_BOTTOMSHEET_CTA_BG_COLOR = false;
    public static final boolean DEFAULT_FULLSCREEN_SHOW_INQUIRY = true;
    public static final boolean DEFAULT_FULLSCREEN_TITLE_COLOR = false;
    public static final boolean DEFAULT_FULLSCREEN_CUSTOM_ADAPTER = false;
    public static final boolean DEFAULT_FULLSCREEN_ERROR_VIEW = false;

    private final SharedPreferences prefs;

    public OptionsPreferences(Context context) {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    /**
     * POP 옵션을 동기적으로 저장합니다.
     * 앱 재시작 전에 호출하여 프로세스 종료 전 디스크에 확실히 기록합니다.
     */
    public void commitPopOptions(int idleMode, long idleTime, long previewInterval, long previewDuration,
                                 boolean customToolbar, boolean customHeader,
                                 boolean customAdMessage, boolean customFeedback, boolean customIcon,
                                 boolean utilityHandler) {
        prefs.edit()
                .putInt(KEY_POP_IDLE_MODE, idleMode)
                .putLong(KEY_POP_IDLE_TIME, idleTime)
                .putLong(KEY_POP_PREVIEW_INTERVAL, previewInterval)
                .putLong(KEY_POP_PREVIEW_DURATION, previewDuration)
                .putBoolean(KEY_POP_CUSTOM_TOOLBAR, customToolbar)
                .putBoolean(KEY_POP_CUSTOM_HEADER, customHeader)
                .putBoolean(KEY_POP_CUSTOM_AD_MESSAGE, customAdMessage)
                .putBoolean(KEY_POP_CUSTOM_FEEDBACK, customFeedback)
                .putBoolean(KEY_POP_CUSTOM_ICON, customIcon)
                .putBoolean(KEY_POP_UTILITY_HANDLER, utilityHandler)
                .commit(); // commit() = 동기 저장, apply() = 비동기
    }

    public int getVideoAutoplay() {
        return prefs.getInt(KEY_VIDEO_AUTOPLAY, DEFAULT_VIDEO_AUTOPLAY);
    }

    public void setVideoAutoplay(int value) {
        prefs.edit().putInt(KEY_VIDEO_AUTOPLAY, value).apply();
    }

    public int getPopIdleMode() {
        return prefs.getInt(KEY_POP_IDLE_MODE, DEFAULT_POP_IDLE_MODE);
    }

    public void setPopIdleMode(int value) {
        prefs.edit().putInt(KEY_POP_IDLE_MODE, value).apply();
    }

    public long getPopIdleTime() {
        return prefs.getLong(KEY_POP_IDLE_TIME, DEFAULT_POP_IDLE_TIME);
    }

    public void setPopIdleTime(long value) {
        prefs.edit().putLong(KEY_POP_IDLE_TIME, value).apply();
    }

    public long getPopPreviewInterval() {
        return prefs.getLong(KEY_POP_PREVIEW_INTERVAL, DEFAULT_POP_PREVIEW_INTERVAL);
    }

    public void setPopPreviewInterval(long value) {
        prefs.edit().putLong(KEY_POP_PREVIEW_INTERVAL, value).apply();
    }

    public boolean getPopCustomToolbar() {
        return prefs.getBoolean(KEY_POP_CUSTOM_TOOLBAR, DEFAULT_POP_CUSTOM_TOOLBAR);
    }

    public void setPopCustomToolbar(boolean value) {
        prefs.edit().putBoolean(KEY_POP_CUSTOM_TOOLBAR, value).apply();
    }

    public boolean getPopCustomHeader() {
        return prefs.getBoolean(KEY_POP_CUSTOM_HEADER, DEFAULT_POP_CUSTOM_HEADER);
    }

    public void setPopCustomHeader(boolean value) {
        prefs.edit().putBoolean(KEY_POP_CUSTOM_HEADER, value).apply();
    }

    public boolean getPopCustomAdMessage() {
        return prefs.getBoolean(KEY_POP_CUSTOM_AD_MESSAGE, DEFAULT_POP_CUSTOM_AD_MESSAGE);
    }

    public void setPopCustomAdMessage(boolean value) {
        prefs.edit().putBoolean(KEY_POP_CUSTOM_AD_MESSAGE, value).apply();
    }

    public boolean getPopCustomFeedback() {
        return prefs.getBoolean(KEY_POP_CUSTOM_FEEDBACK, DEFAULT_POP_CUSTOM_FEEDBACK);
    }

    public void setPopCustomFeedback(boolean value) {
        prefs.edit().putBoolean(KEY_POP_CUSTOM_FEEDBACK, value).apply();
    }

    public boolean getPopCustomIcon() {
        return prefs.getBoolean(KEY_POP_CUSTOM_ICON, DEFAULT_POP_CUSTOM_ICON);
    }

    public void setPopCustomIcon(boolean value) {
        prefs.edit().putBoolean(KEY_POP_CUSTOM_ICON, value).apply();
    }

    public boolean getPopUtilityHandler() {
        return prefs.getBoolean(KEY_POP_UTILITY_HANDLER, DEFAULT_POP_UTILITY_HANDLER);
    }

    public void setPopUtilityHandler(boolean value) {
        prefs.edit().putBoolean(KEY_POP_UTILITY_HANDLER, value).apply();
    }

    public boolean getNoEdgeCustomAdapter() {
        return prefs.getBoolean(KEY_NO_EDGE_CUSTOM_ADAPTER, DEFAULT_NO_EDGE_CUSTOM_ADAPTER);
    }

    public void setNoEdgeCustomAdapter(boolean value) {
        prefs.edit().putBoolean(KEY_NO_EDGE_CUSTOM_ADAPTER, value).apply();
    }

    public int getCarouselCount() {
        return prefs.getInt(KEY_CAROUSEL_COUNT, DEFAULT_CAROUSEL_COUNT);
    }

    public void setCarouselCount(int value) {
        prefs.edit().putInt(KEY_CAROUSEL_COUNT, value).apply();
    }

    public int getCarouselDuration() {
        return prefs.getInt(KEY_CAROUSEL_DURATION, DEFAULT_CAROUSEL_DURATION);
    }

    public void setCarouselDuration(int value) {
        prefs.edit().putInt(KEY_CAROUSEL_DURATION, value).apply();
    }

    public int getCarouselSidePeek() {
        return prefs.getInt(KEY_CAROUSEL_SIDE_PEEK, DEFAULT_CAROUSEL_SIDE_PEEK);
    }

    public void setCarouselSidePeek(int value) {
        prefs.edit().putInt(KEY_CAROUSEL_SIDE_PEEK, value).apply();
    }

    public int getCarouselItemSpacing() {
        return prefs.getInt(KEY_CAROUSEL_ITEM_SPACING, DEFAULT_CAROUSEL_ITEM_SPACING);
    }

    public void setCarouselItemSpacing(int value) {
        prefs.edit().putInt(KEY_CAROUSEL_ITEM_SPACING, value).apply();
    }

    public boolean getCarouselLoop() {
        return prefs.getBoolean(KEY_CAROUSEL_LOOP, DEFAULT_CAROUSEL_LOOP);
    }

    public void setCarouselLoop(boolean value) {
        prefs.edit().putBoolean(KEY_CAROUSEL_LOOP, value).apply();
    }

    public boolean getCarouselIndicator() {
        return prefs.getBoolean(KEY_CAROUSEL_INDICATOR, DEFAULT_CAROUSEL_INDICATOR);
    }

    public void setCarouselIndicator(boolean value) {
        prefs.edit().putBoolean(KEY_CAROUSEL_INDICATOR, value).apply();
    }

    public boolean getCarouselAutoPaging() {
        return prefs.getBoolean(KEY_CAROUSEL_AUTO_PAGING, DEFAULT_CAROUSEL_AUTO_PAGING);
    }

    public void setCarouselAutoPaging(boolean value) {
        prefs.edit().putBoolean(KEY_CAROUSEL_AUTO_PAGING, value).apply();
    }

    public boolean getFeedInquiry() {
        return prefs.getBoolean(KEY_FEED_INQUIRY, DEFAULT_FEED_INQUIRY);
    }

    public void setFeedInquiry(boolean value) {
        prefs.edit().putBoolean(KEY_FEED_INQUIRY, value).apply();
    }

    public boolean getFeedGridLayout() {
        return prefs.getBoolean(KEY_FEED_GRID_LAYOUT, DEFAULT_FEED_GRID_LAYOUT);
    }

    public void setFeedGridLayout(boolean value) {
        prefs.edit().putBoolean(KEY_FEED_GRID_LAYOUT, value).apply();
    }

    public boolean getDialogTopIcon() {
        return prefs.getBoolean(KEY_DIALOG_TOP_ICON, DEFAULT_DIALOG_TOP_ICON);
    }

    public void setDialogTopIcon(boolean value) {
        prefs.edit().putBoolean(KEY_DIALOG_TOP_ICON, value).apply();
    }

    public boolean getDialogShowInquiry() {
        return prefs.getBoolean(KEY_DIALOG_SHOW_INQUIRY, DEFAULT_DIALOG_SHOW_INQUIRY);
    }

    public void setDialogShowInquiry(boolean value) {
        prefs.edit().putBoolean(KEY_DIALOG_SHOW_INQUIRY, value).apply();
    }

    public boolean getDialogBgColor() {
        return prefs.getBoolean(KEY_DIALOG_BG_COLOR, DEFAULT_DIALOG_BG_COLOR);
    }

    public void setDialogBgColor(boolean value) {
        prefs.edit().putBoolean(KEY_DIALOG_BG_COLOR, value).apply();
    }

    public boolean getDialogTitleColor() {
        return prefs.getBoolean(KEY_DIALOG_TITLE_COLOR, DEFAULT_DIALOG_TITLE_COLOR);
    }

    public void setDialogTitleColor(boolean value) {
        prefs.edit().putBoolean(KEY_DIALOG_TITLE_COLOR, value).apply();
    }

    public boolean getDialogCtaIcon() {
        return prefs.getBoolean(KEY_DIALOG_CTA_ICON, DEFAULT_DIALOG_CTA_ICON);
    }

    public void setDialogCtaIcon(boolean value) {
        prefs.edit().putBoolean(KEY_DIALOG_CTA_ICON, value).apply();
    }

    public boolean getDialogCtaBgColor() {
        return prefs.getBoolean(KEY_DIALOG_CTA_BG_COLOR, DEFAULT_DIALOG_CTA_BG_COLOR);
    }

    public void setDialogCtaBgColor(boolean value) {
        prefs.edit().putBoolean(KEY_DIALOG_CTA_BG_COLOR, value).apply();
    }

    public int getBottomSheetAdCount() {
        return prefs.getInt(KEY_BOTTOMSHEET_AD_COUNT, DEFAULT_BOTTOMSHEET_AD_COUNT);
    }

    public void setBottomSheetAdCount(int value) {
        prefs.edit().putInt(KEY_BOTTOMSHEET_AD_COUNT, value).apply();
    }

    public boolean getBottomSheetTopIcon() {
        return prefs.getBoolean(KEY_BOTTOMSHEET_TOP_ICON, DEFAULT_BOTTOMSHEET_TOP_ICON);
    }

    public void setBottomSheetTopIcon(boolean value) {
        prefs.edit().putBoolean(KEY_BOTTOMSHEET_TOP_ICON, value).apply();
    }

    public boolean getBottomSheetShowInquiry() {
        return prefs.getBoolean(KEY_BOTTOMSHEET_SHOW_INQUIRY, DEFAULT_BOTTOMSHEET_SHOW_INQUIRY);
    }

    public void setBottomSheetShowInquiry(boolean value) {
        prefs.edit().putBoolean(KEY_BOTTOMSHEET_SHOW_INQUIRY, value).apply();
    }

    public boolean getBottomSheetBgColor() {
        return prefs.getBoolean(KEY_BOTTOMSHEET_BG_COLOR, DEFAULT_BOTTOMSHEET_BG_COLOR);
    }

    public void setBottomSheetBgColor(boolean value) {
        prefs.edit().putBoolean(KEY_BOTTOMSHEET_BG_COLOR, value).apply();
    }

    public boolean getBottomSheetTitleColor() {
        return prefs.getBoolean(KEY_BOTTOMSHEET_TITLE_COLOR, DEFAULT_BOTTOMSHEET_TITLE_COLOR);
    }

    public void setBottomSheetTitleColor(boolean value) {
        prefs.edit().putBoolean(KEY_BOTTOMSHEET_TITLE_COLOR, value).apply();
    }

    public boolean getBottomSheetCtaIcon() {
        return prefs.getBoolean(KEY_BOTTOMSHEET_CTA_ICON, DEFAULT_BOTTOMSHEET_CTA_ICON);
    }

    public void setBottomSheetCtaIcon(boolean value) {
        prefs.edit().putBoolean(KEY_BOTTOMSHEET_CTA_ICON, value).apply();
    }

    public boolean getBottomSheetCtaBgColor() {
        return prefs.getBoolean(KEY_BOTTOMSHEET_CTA_BG_COLOR, DEFAULT_BOTTOMSHEET_CTA_BG_COLOR);
    }

    public void setBottomSheetCtaBgColor(boolean value) {
        prefs.edit().putBoolean(KEY_BOTTOMSHEET_CTA_BG_COLOR, value).apply();
    }

    public boolean getFullscreenShowInquiry() {
        return prefs.getBoolean(KEY_FULLSCREEN_SHOW_INQUIRY, DEFAULT_FULLSCREEN_SHOW_INQUIRY);
    }

    public void setFullscreenShowInquiry(boolean value) {
        prefs.edit().putBoolean(KEY_FULLSCREEN_SHOW_INQUIRY, value).apply();
    }

    public boolean getFullscreenTitleColor() {
        return prefs.getBoolean(KEY_FULLSCREEN_TITLE_COLOR, DEFAULT_FULLSCREEN_TITLE_COLOR);
    }

    public void setFullscreenTitleColor(boolean value) {
        prefs.edit().putBoolean(KEY_FULLSCREEN_TITLE_COLOR, value).apply();
    }

    public boolean getFullscreenCustomAdapter() {
        return prefs.getBoolean(KEY_FULLSCREEN_CUSTOM_ADAPTER, DEFAULT_FULLSCREEN_CUSTOM_ADAPTER);
    }

    public void setFullscreenCustomAdapter(boolean value) {
        prefs.edit().putBoolean(KEY_FULLSCREEN_CUSTOM_ADAPTER, value).apply();
    }

    public boolean getFullscreenErrorView() {
        return prefs.getBoolean(KEY_FULLSCREEN_ERROR_VIEW, DEFAULT_FULLSCREEN_ERROR_VIEW);
    }

    public void setFullscreenErrorView(boolean value) {
        prefs.edit().putBoolean(KEY_FULLSCREEN_ERROR_VIEW, value).apply();
    }

    public long getPopPreviewDuration() {
        // 마이그레이션: 이전에 Int(초)로 저장된 값이 있으면 Long(밀리초)으로 변환
        try {
            return prefs.getLong(KEY_POP_PREVIEW_DURATION, DEFAULT_POP_PREVIEW_DURATION);
        } catch (ClassCastException e) {
            long newValueInMs = prefs.getInt(KEY_POP_PREVIEW_DURATION, 5) * 1000L;
            prefs.edit().putLong(KEY_POP_PREVIEW_DURATION, newValueInMs).apply();
            return newValueInMs;
        }
    }

    public void setPopPreviewDuration(long value) {
        prefs.edit().putLong(KEY_POP_PREVIEW_DURATION, value).apply();
    }

    /** 모든 옵션을 기본값으로 초기화합니다. */
    public void resetToDefault() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(KEY_VIDEO_AUTOPLAY, DEFAULT_VIDEO_AUTOPLAY);
        editor.putInt(KEY_POP_IDLE_MODE, DEFAULT_POP_IDLE_MODE);
        editor.putLong(KEY_POP_IDLE_TIME, DEFAULT_POP_IDLE_TIME);
        editor.putLong(KEY_POP_PREVIEW_INTERVAL, DEFAULT_POP_PREVIEW_INTERVAL);
        editor.putLong(KEY_POP_PREVIEW_DURATION, DEFAULT_POP_PREVIEW_DURATION);
        editor.putBoolean(KEY_POP_CUSTOM_TOOLBAR, DEFAULT_POP_CUSTOM_TOOLBAR);
        editor.putBoolean(KEY_POP_CUSTOM_HEADER, DEFAULT_POP_CUSTOM_HEADER);
        editor.putBoolean(KEY_POP_CUSTOM_AD_MESSAGE, DEFAULT_POP_CUSTOM_AD_MESSAGE);
        editor.putBoolean(KEY_POP_CUSTOM_FEEDBACK, DEFAULT_POP_CUSTOM_FEEDBACK);
        editor.putBoolean(KEY_POP_CUSTOM_ICON, DEFAULT_POP_CUSTOM_ICON);
        editor.putBoolean(KEY_POP_UTILITY_HANDLER, DEFAULT_POP_UTILITY_HANDLER);
        editor.putBoolean(KEY_NO_EDGE_CUSTOM_ADAPTER, DEFAULT_NO_EDGE_CUSTOM_ADAPTER);

        // Carousel
        editor.putInt(KEY_CAROUSEL_COUNT, DEFAULT_CAROUSEL_COUNT);
        editor.putInt(KEY_CAROUSEL_DURATION, DEFAULT_CAROUSEL_DURATION);
        editor.putInt(KEY_CAROUSEL_SIDE_PEEK, DEFAULT_CAROUSEL_SIDE_PEEK);
        editor.putInt(KEY_CAROUSEL_ITEM_SPACING, DEFAULT_CAROUSEL_ITEM_SPACING);
        editor.putBoolean(KEY_CAROUSEL_LOOP, DEFAULT_CAROUSEL_LOOP);
        editor.putBoolean(KEY_CAROUSEL_INDICATOR, DEFAULT_CAROUSEL_INDICATOR);
        editor.putBoolean(KEY_CAROUSEL_AUTO_PAGING, DEFAULT_CAROUSEL_AUTO_PAGING);

        // Feed
        editor.putBoolean(KEY_FEED_INQUIRY, DEFAULT_FEED_INQUIRY);
        editor.putBoolean(KEY_FEED_GRID_LAYOUT, DEFAULT_FEED_GRID_LAYOUT);

        // Dialog
        editor.putBoolean(KEY_DIALOG_TOP_ICON, DEFAULT_DIALOG_TOP_ICON);
        editor.putBoolean(KEY_DIALOG_SHOW_INQUIRY, DEFAULT_DIALOG_SHOW_INQUIRY);
        editor.putBoolean(KEY_DIALOG_BG_COLOR, DEFAULT_DIALOG_BG_COLOR);
        editor.putBoolean(KEY_DIALOG_TITLE_COLOR, DEFAULT_DIALOG_TITLE_COLOR);
        editor.putBoolean(KEY_DIALOG_CTA_ICON, DEFAULT_DIALOG_CTA_ICON);
        editor.putBoolean(KEY_DIALOG_CTA_BG_COLOR, DEFAULT_DIALOG_CTA_BG_COLOR);

        // BottomSheet
        editor.putInt(KEY_BOTTOMSHEET_AD_COUNT, DEFAULT_BOTTOMSHEET_AD_COUNT);
        editor.putBoolean(KEY_BOTTOMSHEET_TOP_ICON, DEFAULT_BOTTOMSHEET_TOP_ICON);
        editor.putBoolean(KEY_BOTTOMSHEET_SHOW_INQUIRY, DEFAULT_BOTTOMSHEET_SHOW_INQUIRY);
        editor.putBoolean(KEY_BOTTOMSHEET_BG_COLOR, DEFAULT_BOTTOMSHEET_BG_COLOR);
        editor.putBoolean(KEY_BOTTOMSHEET_TITLE_COLOR, DEFAULT_BOTTOMSHEET_TITLE_COLOR);
        editor.putBoolean(KEY_BOTTOMSHEET_CTA_ICON, DEFAULT_BOTTOMSHEET_CTA_ICON);
        editor.putBoolean(KEY_BOTTOMSHEET_CTA_BG_COLOR, DEFAULT_BOTTOMSHEET_CTA_BG_COLOR);

        // Fullscreen
        editor.putBoolean(KEY_FULLSCREEN_SHOW_INQUIRY, DEFAULT_FULLSCREEN_SHOW_INQUIRY);
        editor.putBoolean(KEY_FULLSCREEN_TITLE_COLOR, DEFAULT_FULLSCREEN_TITLE_COLOR);
        editor.putBoolean(KEY_FULLSCREEN_CUSTOM_ADAPTER, DEFAULT_FULLSCREEN_CUSTOM_ADAPTER);
        editor.putBoolean(KEY_FULLSCREEN_ERROR_VIEW, DEFAULT_FULLSCREEN_ERROR_VIEW);
        editor.apply();
    }
}

