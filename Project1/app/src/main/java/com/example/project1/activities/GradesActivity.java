package com.example.project1.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.Button;

import com.example.project1.R;
import com.example.project1.models.Grade;
import com.example.project1.views.GradesRecyclerAdapter;

import java.util.ArrayList;

public class GradesActivity extends AppCompatActivity {
    private ArrayList<Grade> grades;
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("grades", grades);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        grades = savedInstanceState.getParcelableArrayList("grades");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grades);

        grades = new ArrayList<>();
        Button button = findViewById(R.id.calculateAverageButton);
        button.setOnClickListener(view -> {
            int sum = 0;
            for (Grade grade : grades) {
                sum += grade.getGradeValue();
            }
            double average = (double) sum / (double) grades.size();
            Intent intent = new Intent(GradesActivity.this, MainActivity.class);
            intent.putExtra("average", average);
            setResult(RESULT_OK, intent);
            finish();
        });

        Bundle bundle = getIntent().getExtras();
        int subjectCount = bundle.getInt("subject_count");
        String[] subjectNames = getResources().getStringArray(R.array.subjects);
        for (int i = 0; i < subjectNames.length && i < subjectCount; i++) {
            grades.add(new Grade(subjectNames[i], 2));
        }

        GradesRecyclerAdapter adapter = new GradesRecyclerAdapter(this, grades);
        RecyclerView recyclerView = findViewById(R.id.gradeRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter);
    }
}