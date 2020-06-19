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
    private RadioGroup radioGroupFilter, radioGroupSort;
    private ListView listViewResult;
    private EditText editTextName;
    private TextView textViewScore;
    private Button btnShow, btnBack;
    private ArrayList<Quiz> listOfQuiz;
    private ArrayList<Quiz> currentListOfQuiz;
    private float score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        initialize();
    }

    private void initialize() {
        // initialize elements
        radioGroupFilter = findViewById(R.id.radioGroupFilter);
        radioGroupSort = findViewById(R.id.radioGroupSort);
        listViewResult = findViewById(R.id.listViewResult);
        editTextName = findViewById(R.id.editTextName);
        textViewScore = findViewById(R.id.textViewScore);

        btnShow = findViewById(R.id.btnShow);
        btnShow.setOnClickListener(this);
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);
        // get intent and get bundle data
        Bundle bundle = getIntent().getBundleExtra("intentExtra");
        // get serializable list
        Serializable bundledListOfQuiz = bundle.getSerializable("bundleExtra");
        // assign quiz list to member variable
        listOfQuiz = (ArrayList<Quiz>) bundledListOfQuiz;

        // Create an Adapter for ListView
        ArrayAdapter<Quiz> listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listOfQuiz);
        // Assign the Adapter to the list view
        listViewResult.setAdapter(listAdapter);

        // get user score and set it to score variable
        score = calculateScore();
        textViewScore.setText(String.format("%.0f%%", score));

        // set all data as default quiz data to show
        currentListOfQuiz = listOfQuiz;
    }

    public void showMe(View view) {

        int selected_radio_btn = radioGroupFilter.getCheckedRadioButtonId();
        // declare filter variable to filter quiz data
        String filter;
        // get filter value
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
        // filter quiz data by result(right or wrong)
        currentListOfQuiz = filterQuizByResult(filter);
        // create an adaptor for list view
        ArrayAdapter<Quiz> listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                currentListOfQuiz);
        // assign the adapter to the list view
        listViewResult.setAdapter(listAdapter);
    }

    public void sorting(View view) {
        // get sorting key value
        int selected_radio_btn = radioGroupSort.getCheckedRadioButtonId();
        if (currentListOfQuiz.size() != 0) {
            switch (selected_radio_btn) {
                case R.id.radioButtonAscending:
                    Collections.sort(currentListOfQuiz);
                    break;
                case R.id.radioButtonDescending:
                    Collections.sort(currentListOfQuiz);
                    Collections.reverse(currentListOfQuiz);
                    break;
            }
            // create an adapter for list view
            ArrayAdapter<Quiz> listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                    currentListOfQuiz);
            // assign the adaptor to the list view
            listViewResult.setAdapter(listAdapter);
        }
    }

    // filter quizzes data by result filter
    private ArrayList<Quiz> filterQuizByResult(String filter) {
        ArrayList<Quiz> listOfQuizToShow = new ArrayList<>();
        if (filter.equals("All")) {
            listOfQuizToShow = listOfQuiz;
        } else if (filter.equals("Right")) {
            for (Quiz one : listOfQuiz)
                if (one.getResult().equals("right"))
                    listOfQuizToShow.add(one);
        } else if (filter.equals("Wrong")) {
            for (Quiz one : listOfQuiz)
                if (one.getResult().equals("wrong"))
                    listOfQuizToShow.add(one);
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

    // calculate the user score using quizzes data
    private float calculateScore() {
        int numberOfRightQuiz = 0;
        // get the number of right answer
        for (Quiz one : listOfQuiz) {
            if (one.getResult().equals("right"))
                numberOfRightQuiz += 1;
        }
        // calculate
        if (listOfQuiz.size() != 0)
            return numberOfRightQuiz * 100 / listOfQuiz.size();
        else
            return 0;
    }
}