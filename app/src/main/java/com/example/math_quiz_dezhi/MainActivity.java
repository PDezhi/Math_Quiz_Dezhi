package com.example.math_quiz_dezhi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.math_quiz_dezhi.model.Quiz;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    final static int REQUEST_CODE = 1;

    private EditText editTextAnswer;
    private TextView textViewOperation, textViewReturnResult;
    private Button btnOne, btnTwo, btnThree, btnFour, btnFive, btnSix, btnSeven, btnEight, btnNine, btnZero;
    private Button btnPoint, btnMinus, btnGenerate, btnValidate, btnClear, btnScore, btnFinish;

    private float rightAnswer;
    private String answer = "", operation;
    private ArrayList<Quiz> listOfQuizzes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
    }

    private void initialize() {
        editTextAnswer = findViewById(R.id.editTextAnswer);
        textViewOperation = findViewById(R.id.textViewOperation);
        btnOne = findViewById(R.id.btnOne);
        btnOne.setOnClickListener(this);
        btnTwo = findViewById(R.id.btnTwo);
        btnTwo.setOnClickListener(this);
        btnThree = findViewById(R.id.btnThree);
        btnThree.setOnClickListener(this);
        btnFour = findViewById(R.id.btnFour);
        btnFour.setOnClickListener(this);
        btnFive = findViewById(R.id.btnFive);
        btnFive.setOnClickListener(this);
        btnSix = findViewById(R.id.btnSix);
        btnSix.setOnClickListener(this);
        btnSeven = findViewById(R.id.btnSeven);
        btnSeven.setOnClickListener(this);
        btnEight = findViewById(R.id.btnEight);
        btnEight.setOnClickListener(this);
        btnNine = findViewById(R.id.btnNine);
        btnNine.setOnClickListener(this);
        btnZero = findViewById(R.id.btnZero);
        btnZero.setOnClickListener(this);
        btnGenerate = findViewById(R.id.btnGenerate);
        btnGenerate.setOnClickListener(this);
        btnValidate = findViewById(R.id.btnValidate);
        btnValidate.setOnClickListener(this);
        btnClear = findViewById(R.id.btnClear);
        btnClear.setOnClickListener(this);
        btnFinish = findViewById(R.id.btnFinish);
        btnFinish.setOnClickListener(this);
        btnScore = findViewById(R.id.btnScore);
        btnScore.setOnClickListener(this);
        btnPoint = findViewById(R.id.btnPoint);
        btnPoint.setOnClickListener(this);
        btnMinus = findViewById(R.id.btnMinus);
        btnMinus.setOnClickListener(this);
        textViewReturnResult = findViewById(R.id.textViewReturnResult);

        listOfQuizzes = new ArrayList<>();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btnOne:
            case R.id.btnTwo:
            case R.id.btnThree:
            case R.id.btnFour:
            case R.id.btnFive:
            case R.id.btnSix:
            case R.id.btnSeven:
            case R.id.btnEight:
            case R.id.btnNine:
            case R.id.btnZero:
            case R.id.btnPoint:
            case R.id.btnMinus:
                getUserInput(view);
                break;
            case R.id.btnGenerate:
                generate();
                break;
            case R.id.btnValidate:
                validate();
                break;
            case R.id.btnClear:
                clear();
                break;
            case R.id.btnFinish:
                finish();
                break;
            case R.id.btnScore:
                showScore();
                break;
        }
    }

    // get data user inputs and show it on the screen
    private void getUserInput(View view) {
        Button currentBtn = (Button) view;
        String inputKey = currentBtn.getText().toString();
        if (inputKey.compareTo(".") == 0 && answer.contains("."))
            showMessage("The data you input does not allow to have more than one points!");
        else if (inputKey.compareTo("-") == 0 && answer != "")
            showMessage("The data you input already has a minus asign!");
        else {
            // Input data using keyboard and buttons
            answer = editTextAnswer.getText().toString();
            if (answer == null)
                answer = "";
            answer += inputKey;
            editTextAnswer.setText(answer);
        }
    }

    // generate a operations randomly and automatically and then show it on the screen
    private void generate() {
        String operators[] = {"+", "-", "*", "/"};
        Random random = new Random();
        int randomIndex = random.nextInt(operators.length);
        String CurrentOperator = operators[randomIndex];
        int operand1 = random.nextInt(10);
        int operand2 = random.nextInt(10);

        // for division as divisor, prevent divisor equals zero
        int operand3 = random.nextInt(9) + 1;

        switch (CurrentOperator) {
            case "+":
                rightAnswer = operand1 + operand2;
                break;
            case "-":
                rightAnswer = operand1 - operand2;
                break;
            case "*":
                rightAnswer = operand1 * operand2;
                break;
            case "/":
                rightAnswer = (float) (Math.round(operand1 * 10 / operand3) / 10.0);
                operand2 = operand3;
                break;
        }

        operation = operand1 + " " + CurrentOperator + " " + operand2;
        textViewOperation.setText(operation);
    }

    // validate the answer user inputted is right or wrong
    private void validate() {
        String strUserAnswer = editTextAnswer.getText().toString();
        String result;
        try {
            float userAnswer = Float.valueOf(strUserAnswer);
            if (rightAnswer == userAnswer)
                result = "right";
            else
                result = "wrong";
            Quiz quiz = new Quiz(operation, rightAnswer, userAnswer, result);
            listOfQuizzes.add(quiz);
            showMessage("Your answer is " + result);
            clear();
        } catch (Exception e) {
            showMessage("The data you input has not right format!");
        }
    }

    // Clear the display
    private void clear() {
        editTextAnswer.setText("");
        textViewOperation.setText("0 + 0");
        answer = "";
    }

    // Go to Result page with data that will be showed there. will be invoked by layout onClick property
    private void showScore() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("bundleExtra", listOfQuizzes);

        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra("intentExtra", bundle);
        startActivityForResult(intent, REQUEST_CODE);
    }

    // show a message passed by parameter on the screen
    private void showMessage(String strMessage) {
        Toast message = Toast.makeText(this, strMessage, Toast.LENGTH_LONG);
        message.setGravity(Gravity.BOTTOM | Gravity.CENTER, 0, 50);
        message.show();
    }

    // get and show return data on main page
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        String receivedName = (String) data.getStringExtra("return_name_tag");
        String receivedScore = (String) data.getStringExtra("return_score_tag");
        textViewReturnResult.setText(receivedName + "  :  " + receivedScore);
    }
}