package com.drantiev.anton.canadiantest;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.drantiev.anton.canadiantest.data.source.DbHelper;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    //declare all needed vars

    List<Question> questionList;
    int score = 0;
    int questionId = 0;
    Question currentQuestion;
    TextView txtQuestion;
    RadioButton rbA, rbB, rbC, rbD;
    Button btnNext;
    View mainview;
    DbHelper dbHelper;

    //for transition
    //ViewGroup transitionsContainer = (ViewGroup) findViewById(R.id.transitions_container);
    //TextView text = (TextView) transitionsContainer.findViewById(R.id.text);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainview = (View) findViewById(R.id.main_view);

        dbHelper = new DbHelper(this);
        questionList = dbHelper.getAllQuestions();
        currentQuestion = questionList.get(questionId);
        txtQuestion = (TextView) findViewById(R.id.tv_question);
        rbA = (RadioButton) findViewById(R.id.rb_a);
        rbB = (RadioButton) findViewById(R.id.rb_b);
        rbC = (RadioButton) findViewById(R.id.rb_c);
        rbD = (RadioButton) findViewById(R.id.rb_d);
        btnNext = (Button) findViewById(R.id.btn_next);
        setQuestionView();

        btnNext.setOnClickListener(new View.OnClickListener() {
            boolean visible;


            @Override
            public void onClick(View v) {

                //transition
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//                    TransitionManager.beginDelayedTransition(transitionsContainer);
//                }
//                visible = !visible;
//                text.setVisibility(visible ? View.VISIBLE : View.GONE);


                RadioGroup rbGroup = (RadioGroup) findViewById(R.id.rb_group);
                RadioButton answare = (RadioButton) findViewById(rbGroup.getCheckedRadioButtonId());
                rbGroup.clearCheck();
                if (currentQuestion.getAnswer().equals(answare.getText())) ;
                {
                    score++;
                }
                if (questionId < 5) {
                    currentQuestion = questionList.get(questionId);
                    setQuestionView();
                } else {
                    Intent intent = new Intent(MainActivity.this, ResultsActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("score", score);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainview, menu);
        return true;
    }

    private void setQuestionView() {
        txtQuestion.setText(currentQuestion.getQuestion());
        rbA.setText(currentQuestion.getOptionA());
        rbB.setText(currentQuestion.getOptionB());
        rbC.setText(currentQuestion.getOptionC());
        rbD.setText(currentQuestion.getOptionD());
        questionId++;
    }
}
