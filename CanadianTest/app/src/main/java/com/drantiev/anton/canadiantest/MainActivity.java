package com.drantiev.anton.canadiantest;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
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
    RadioButton rbA,rbB,rbC;
    Button btnNext;
    View mainview;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DbHelper dbHelper  = new DbHelper(this);
        mainview = (View)findViewById(R.id.main_view) ;


//        questionList = dbHelper.getAllQuestions();
        currentQuestion = questionList.get(questionId);
        txtQuestion = (TextView)findViewById(R.id.tv_question);
        rbA =(RadioButton)findViewById(R.id.rb_a);
        rbB =(RadioButton)findViewById(R.id.rb_b);
        rbC =(RadioButton)findViewById(R.id.rb_c);
        btnNext = (Button)findViewById(R.id.btn_next);
        setQuestionView();

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioGroup rbGroup =(RadioGroup)findViewById(R.id.rb_group);
                RadioButton answare = (RadioButton)findViewById(rbGroup.getCheckedRadioButtonId());
                rbGroup.clearCheck();
                if(currentQuestion.getAnswer().equals(answare.getText()));{
                    score++;
                }
                if(questionId<5){
                    currentQuestion = questionList.get(questionId);
                    setQuestionView();
                }else {
                    Intent intent = new Intent(MainActivity.this,ResultsActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("score",score);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainview ,menu);
        return  true;
    }

    private void setQuestionView() {
        txtQuestion.setText(currentQuestion.getQuestion());
        rbA.setText(currentQuestion.getOptionA());
        rbB.setText(currentQuestion.getOptionB());
        rbC.setText(currentQuestion.getOptionC());
        questionId++;
    }
}
