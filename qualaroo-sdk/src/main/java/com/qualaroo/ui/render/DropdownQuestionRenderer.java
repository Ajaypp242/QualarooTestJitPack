package com.qualaroo.ui.render;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.qualaroo.R;
import com.qualaroo.internal.ImageProvider;
import com.qualaroo.internal.model.Answer;
import com.qualaroo.internal.model.Question;
import com.qualaroo.internal.model.UserResponse;
import com.qualaroo.ui.OnAnsweredListener;
import com.qualaroo.util.DebouncingOnClickListener;

import java.util.ArrayList;
import java.util.List;

import static androidx.annotation.RestrictTo.Scope.LIBRARY;

@RestrictTo(LIBRARY)
public final class DropdownQuestionRenderer extends QuestionRenderer {

    DropdownQuestionRenderer(Theme theme) {
        super(theme);
    }

    @Override public RestorableView render(Context context, final Question question, final OnAnsweredListener onAnsweredListener) {
        View view = LayoutInflater.from(context).inflate(R.layout.qualaroo__view_question_dropdown, null);
        final Button confirmButton = view.findViewById(R.id.qualaroo__view_question_dropdown_confirm);
        final Spinner spinner = view.findViewById(R.id.qualaroo__view_question_dropdown_spinner);
        confirmButton.setText(question.sendText());
        ThemeUtils.applyTheme(confirmButton, getTheme());
        ArrayAdapter<AnswerItem> adapter = new CustomSpinnerAdapter(context, getTheme());
        List<AnswerItem> spinnerItems = new ArrayList<>();
        for (Answer answer : question.answerList()) {
            spinnerItems.add(new AnswerItem(answer));
        }
        adapter.addAll(spinnerItems);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                confirmButton.setEnabled(true);
            }

            @Override public void onNothingSelected(AdapterView<?> parent) {
                confirmButton.setEnabled(false);
            }
        });
        confirmButton.setOnClickListener(new DebouncingOnClickListener() {
            @Override public void doClick(View v) {
                AnswerItem answerItem = (AnswerItem) spinner.getSelectedItem();
                onAnsweredListener.onResponse(
                        new UserResponse.Builder(question.id())
                                .addChoiceAnswer(answerItem.answer.id())
                                .build()
                );
            }
        });

        ThemeUtils.applyTheme(spinner, getTheme());
        return RestorableView.withId(question.id())
                .view(view)
                .build();
    }

    private static class CustomSpinnerAdapter extends ArrayAdapter<AnswerItem> {

        private final Theme theme;
        private final LayoutInflater inflater;

        CustomSpinnerAdapter(@NonNull Context context, Theme theme) {
            super(context, 0);
            this.inflater = LayoutInflater.from(context);
            this.theme = theme;
        }

        @NonNull @Override public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.qualaroo__view_question_dropdown_selected_item, parent, false);
            }
            AnswerItem item = getItem(position);
            TextView text = convertView.findViewById(R.id.qualaroo__view_question_dropdown_item_text);
            text.setText(item.answer.title());
            text.setTextColor(theme.textColor());
            return convertView;
        }

        @Override public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            parent.setBackgroundColor(theme.backgroundColor());
            ViewHolder holder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.qualaroo__view_question_dropdown_item, parent, false);
                holder = new ViewHolder();
                holder.text = convertView.findViewById(R.id.qualaroo__view_question_dropdown_item_text);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            AnswerItem item = getItem(position);
            convertView.findViewById(R.id.qualaroo__view_question_dropdown_item_text);
            holder.text.setText(item.answer.title());
            holder.text.setTextColor(theme.textColor());
            return convertView;
        }

        private static class ViewHolder {
            TextView text;
        }
    }

    private static class AnswerItem {
        final Answer answer;

        AnswerItem(Answer answer) {
            this.answer = answer;
        }

        @Override public String toString() {
            return answer.title();
        }
    }

}
