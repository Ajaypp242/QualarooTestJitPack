package com.qualaroo.internal;

import android.util.Log;

import com.qualaroo.internal.model.Survey;

public class SamplePercentMatcher extends SurveySpecMatcher {

    private final UserGroupPercentageProvider percentageProvider;

    public SamplePercentMatcher(UserGroupPercentageProvider percentageProvider) {
        this.percentageProvider = percentageProvider;
    }

    @Override boolean matches(Survey survey) {
        Integer samplePercent = survey.spec().requireMap().samplePercent();
        if (samplePercent == null) {
            return true;
        }
        int percent = percentageProvider.userGroupPercent(survey);
        Log.d("SPECMATCHERS", "SamplePercentMatcher " + String.valueOf(percent < samplePercent));
        return percent < samplePercent;
    }
}
