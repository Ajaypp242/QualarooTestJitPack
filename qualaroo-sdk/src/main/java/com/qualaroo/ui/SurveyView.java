package com.qualaroo.ui;

import androidx.annotation.FloatRange;
import androidx.annotation.RestrictTo;

import com.qualaroo.internal.model.Message;
import com.qualaroo.internal.model.QScreen;
import com.qualaroo.internal.model.Question;
import com.qualaroo.ui.render.Theme;

import java.util.List;

import static androidx.annotation.RestrictTo.Scope.LIBRARY;

@RestrictTo(LIBRARY)
public interface SurveyView {
    void setup(SurveyViewModel surveyViewModel);
    void showWithAnimation();
    void showImmediately();
    void showQuestion(Question question);
    void showMessage(Message message, boolean withAnimation);
    void showLeadGen(QScreen qscreen, List<Question> questions);
    void setProgress(@FloatRange(from=0.0f, to=1.0f) float progress);
    void forceShowKeyboardWithDelay(long timeInMillis);
    void closeSurvey();
}
