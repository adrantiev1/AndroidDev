package com.drantiev.anton.canadiantest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.drantiev.anton.canadiantest.data.source.DbHelper;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    //declare all needed vars
    DbHelper dbHelper;
    List<Question> questionList;
    int score = 0;
    int questionId = 0;
    Question currentQuestion;
    TextView txtQuestion;
    RadioButton rbA,rbB,rbC;
    Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper  = new DbHelper(this);
        currentQuestion = questionList.get(questionId);
        txtQuestion = (TextView)findViewById(R.id.tv_question);
        rbA =(RadioButton)findViewById(R.id.rb_a);
        rbB =(RadioButton)findViewById(R.id.rb_b);
        rbC =(RadioButton)findViewById(R.id.rb_c);
        

    }
}
