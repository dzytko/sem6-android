package com.example.project1.views;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project1.R;
import com.example.project1.models.Grade;

import java.util.List;

public class GradesRecyclerAdapter extends RecyclerView.Adapter<GradesRecyclerAdapter.GradesViewHolder> {
    private final List<Grade> gradeList;
    private final LayoutInflater inflater;

    public GradesRecyclerAdapter(Activity context, List<Grade> gradeList) {
        this.inflater = context.getLayoutInflater();
        this.gradeList = gradeList;
    }

    @NonNull
    @Override
    public GradesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = inflater.inflate(R.layout.grade_row, parent, false);
        return new GradesViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull GradesViewHolder holder, int position) {
        Grade gradeModel = gradeList.get(position);
        holder.subjectNameTextView.setText(gradeModel.getName());
        holder.radioGroup.check(R.id.gradeRadio2);
        holder.radioGroup.setTag(gradeModel);
        holder.radioGroup.setOnCheckedChangeListener(holder);
    }

    @Override
    public int getItemCount() {
        return gradeList.size();
    }

    protected static class GradesViewHolder extends RecyclerView.ViewHolder implements RadioGroup.OnCheckedChangeListener {
        TextView subjectNameTextView;
        RadioGroup radioGroup;

        public GradesViewHolder(@NonNull View itemView) {
            super(itemView);
            subjectNameTextView = itemView.findViewById(R.id.subjectLabel);
            radioGroup = itemView.findViewById(R.id.radioGroup);
        }

        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
            Grade grade = (Grade) radioGroup.getTag();
            for (int i = 0; i < radioGroup.getChildCount(); i++) {
                RadioButton radioButton = (RadioButton) radioGroup.getChildAt(i);
                if (radioButton.getId() == checkedId) {
                    grade.setGradeValue(Integer.parseInt(radioButton.getText().toString()));
                }
            }
        }
    }
}