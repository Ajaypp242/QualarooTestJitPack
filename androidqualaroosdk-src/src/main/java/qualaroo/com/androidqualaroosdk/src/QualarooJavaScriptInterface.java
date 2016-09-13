package qualaroo.com.androidqualaroosdk.src;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;

/**
 * Created by Artem Orynko on 31.09.16.
 * Copyright © 2016 Qualaroo. All rights reserved.
 */

class QualarooJavaScriptInterface {

    private QualarooController  mQualarooController;
    Context mContext;

    // Tag for debug
    private static final String TAG = QualarooJavaScriptInterface.class.getSimpleName();


    QualarooJavaScriptInterface(Context context, QualarooController qualarooController) {
        mContext = context;
        mQualarooController = qualarooController;
    }

    @JavascriptInterface
    public void surveyScreenerReady() {
        Log.d(TAG, "Screener is showing.");
        mQualarooController.performShowSurveyAnimation();
    }

    @JavascriptInterface
    public void surveyShow() {
        Log.d(TAG, "Survey is showing.");
        mQualarooController.performShowSurveyAnimation();
    }

    @JavascriptInterface
    public void surveyClosed() {
        Log.d(TAG, "Survey closed.");

        if (mQualarooController.mWebView.getVisibility() == View.VISIBLE) {
            Log.d(TAG, "Hiding survey.");
            mQualarooController.performHideSurveyAnimation();
        }
    }

    @JavascriptInterface
    public void surveyCloseButtonTapped(boolean isMinimized) {
        mQualarooController.mMinimized = isMinimized;
        Log.d(TAG, "Close/minimize/maximize tapped " + isMinimized);

        if (isMinimized) {
            mQualarooController.mSuggestedHeight = 48;
            mQualarooController.updateHeight();
        } else {
            mQualarooController.updateHeight();
        }
    }

    @JavascriptInterface
    public void surveyHeightChanged(float suggestedHeight) {

        boolean minimized = mQualarooController.mMinimized;

        if (!minimized) {
            Log.d(TAG, "Survey height changed. New height suggested: " + suggestedHeight);

            mQualarooController.mSuggestedHeight = suggestedHeight;
            mQualarooController.updateHeight();
        }
    }

    @JavascriptInterface
    public void globalUnhandledJSError(String error) {
        Log.d(TAG, "Unhandled global JS Error: " + error);
    }
    @JavascriptInterface

    public void surveyUndeliveredAnswerRequest(String request) {
        Log.d(TAG, "Undelivered answer request: " + request);

        if (mQualarooController != null && !request.isEmpty()) {
            mQualarooController.mDelegate.errorSendingReportRequest(request);
        }
    }

    @JavascriptInterface
    public void qualarooScriptLoadSuccess(String message) {
        Log.d(TAG, "Load script: " + message);
        mQualarooController.mQualarooScriptLoaded = true;
        mQualarooController.setupIdentityCode();
    }

    @JavascriptInterface
    public void qualarooScriptLoadError(String message) {
        Log.d(TAG, "Failed to load script: " + message);

        mQualarooController.mQualarooScriptLoaded = false;

        if (mQualarooController != null && !message.isEmpty()) {
            mQualarooController.mDelegate.errorLoadingQualarooScript(message);
        }

    }
}
