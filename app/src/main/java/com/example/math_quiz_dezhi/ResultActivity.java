package com.example.math_quiz_dezhi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.math_quiz_dezhi.model.Quiz;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class ResultActivity extends AppCompatActivity implements View.OnClickListener {
    RadioGroup radioGroupFilter, radioGroupSort;
    ListView listViewResult;
    EditText editTextName;
    TextView textViewScore;
    Button btnShow, btnBack;
    ArrayList<Quiz> listOfQuiz;
    ArrayList<Quiz> currentListOfQuiz;
    float score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        initialize();
    }

    private void initialize() {
        radioGroupFilter = findViewById(R.id.radioGroupFilter);
        radioGroupSort =findViewById(R.id.radioGroupSort);
        listViewResult = findViewById(R.id.listViewResult);
        editTextName = findViewById(R.id.editTextName);
        textViewScore = findViewById(R.id.textViewScore);

        btnShow = findViewById(R.id.btnShow);
        btnShow.setOnClickListener(this);
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);

        Bundle bundle = getIntent().getBundleExtra("intentExtra");
        Serializable bundledListOfQuiz = bundle.getSerializable("bundleExtra");
        listOfQuiz = (ArrayList<Quiz>) bundledListOfQuiz;

        ArrayAdapter<Quiz> listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                listOfQuiz);
        listViewResult.setAdapter(listAdapter);
        score = calculateScore();
        textViewScore.setText(String.format("%.0f%%", score));
        currentListOfQuiz = listOfQuiz;
    }

    public void showMe(View view) {
        int selected_radio_btn = radioGroupFilter.getCheckedRadioButtonId();
        String filter;

        switch (selected_radio_btn) {
            case R.id.radioButtonRight:
                filter = "Right";
                break;
            case R.id.radioButtonWrong:
                filter = "Wrong";
                break;
            case R.id.radioButtonAll:
            default:
                filter = "All";
                break;
        }
        currentListOfQuiz = filterQuizByResult(filter);
        ArrayAdapter<Quiz> listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                currentListOfQuiz);
        listViewResult.setAdapter(listAdapter);

    }
    public void sorting(View view) {
        int selected_radio_btn = radioGroupSort.getCheckedRadioButtonId();
        if(currentListOfQuiz.size()!=0) {
            switch (selected_radio_btn) {
                case R.id.radioButtonAscending:
                    Collections.sort(currentListOfQuiz);
                    break;
                case R.id.radioButtonDescending:
                    Collections.sort(currentListOfQuiz);
                    Collections.reverse(currentListOfQuiz);
                    break;
            }
            ArrayAdapter<Quiz> listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                    currentListOfQuiz);
            listViewResult.setAdapter(listAdapter);
        }
    }

    private ArrayList<Quiz> filterQuizByResult(String filter) {
        ArrayList<Quiz> listOfQuizToShow = new ArrayList<>();
        if (filter.compareTo("All") == 0) {
            listOfQuizToShow = listOfQuiz;
        } else if (filter.compareTo("Right") == 0) {
            for (Quiz one : listOfQuiz) {
                if (one.getResult().compareTo("right") == 0)
                    listOfQuizToShow.add(one);
            }
        } else if (filter.compareTo("Wrong") == 0) {
            for (Quiz one : listOfQuiz) {
                if (one.getResult().compareTo("wrong") == 0)
                    listOfQuizToShow.add(one);
            }
        }
        return listOfQuizToShow;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnBack:
                goBack();
                break;
            case R.id.btnShow:
                break;
        }
    }



    public void goBack() {
        String name = editTextName.getText().toString();
        Intent intent = new Intent();
        intent.putExtra("return_name_tag", editTextName.getText().toString());
        intent.putExtra("return_score_tag", String.format("%.0f%%", score));

        setResult(RESULT_OK, intent);
        finish();
    }

    private float calculateScore() {
        int numberOfRightQuiz = 0;
        for (Quiz one : listOfQuiz) {
            if (one.getResult().compareTo("right") == 0)
                numberOfRightQuiz += 1;
        }
        if (listOfQuiz.size() != 0)
            return numberOfRightQuiz * 100 / listOfQuiz.size();
        else
            return 0;
    }
}