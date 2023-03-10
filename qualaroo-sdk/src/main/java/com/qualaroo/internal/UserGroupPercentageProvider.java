package com.qualaroo.internal;

import android.util.Log;

import com.qualaroo.internal.model.Survey;
import com.qualaroo.internal.storage.LocalStorage;

import java.util.Random;

public class UserGroupPercentageProvider {

    private final LocalStorage localStorage;
    private final Random random;

    public UserGroupPercentageProvider(LocalStorage localStorage, Random random) {
        this.localStorage = localStorage;
        this.random = random;
    }

    int userGroupPercent(Survey survey) {
        Integer percent = localStorage.getUserGroupPercent(survey);
        Log.d("SAMPLE", String.valueOf(percent));
        if (percent == null) {
            percent = random.nextInt(100);
            localStorage.storeUserGroupPercent(survey, percent);
        }
        return percent;
    }

}
