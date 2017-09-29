package com.qualaroo.ui.render;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.RestrictTo;
import android.support.v7.widget.AppCompatTextView;
import android.widget.TextView;

import com.qualaroo.internal.model.Question;
import com.qualaroo.ui.OnAnsweredListener;

import java.util.Locale;

import static android.support.annotation.RestrictTo.Scope.LIBRARY;

@RestrictTo(LIBRARY)
final class UnknownQuestionTypeRenderer extends QuestionRenderer {

    UnknownQuestionTypeRenderer(Theme theme) {
        super(theme);
    }

    @Override public QuestionView render(Context context, Question question, OnAnsweredListener onAnsweredListener) {
        TextView view = new AppCompatTextView(context);
        view.setText(String.format(Locale.ROOT,"We're sorry! Question with id %d is not supported yet", question.id()));
        view.setTextColor(Color.RED);
        return QuestionView.forQuestionId(question.id())
                .setView(view)
                .build();
    }
}
