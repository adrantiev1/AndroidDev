package com.drantiev.anton.canadiantest;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Slide;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.drantiev.anton.canadiantest.data.source.DbHelper;

import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    //declare all needed vars

    private static final String TAG = "CanadianQuiz";
    List<Question> questionList;
    int score = 0;
    int questionId = 0;
    Question currentQuestion;
    TextView txtQuestion;
    RadioButton rbA, rbB, rbC, rbD, rbEasy, rbMedium, rbHard;
    Button btnNext, btnContinue;
    View mainview;
    DbHelper dbHelper;

    //for transition
    ViewGroup transitionsContainerAnswer;
    ViewGroup transitionsContainerQuestion;
    ViewGroup difficultyContainer;
    TextView textResult;

    final Handler handler = new Handler();
    int difficulty = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //init
        mainview = (View) findViewById(R.id.main_view);
        dbHelper = new DbHelper(this);
        transitionsContainerAnswer = (ViewGroup) findViewById(R.id.transitions_container_answer);
        transitionsContainerQuestion = (ViewGroup) findViewById(R.id.transitions_container_question);
        difficultyContainer = (ViewGroup) findViewById(R.id.difficulty_layout);
        btnContinue = (Button)findViewById(R.id.btn_continue);

        //hide questions container
        transitionsContainerQuestion.setVisibility(View.GONE);

        // Difficulty radio buttons
        rbEasy = (RadioButton)findViewById(R.id.rb_easy);
        rbHard = (RadioButton)findViewById(R.id.rb_hard);
        rbMedium = (RadioButton)findViewById(R.id.rb_medium);
        rbEasy.setText("Easy");
        rbMedium.setText("Medium");
        rbHard.setText("Hard");

        //listener for continue button at the difficulty view
        btnContinue.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v) {
                RadioGroup rbGroupDifficulty = (RadioGroup) findViewById(R.id.rg_difficulty);
                RadioButton selectedDifficulty = (RadioButton) findViewById(rbGroupDifficulty.getCheckedRadioButtonId());

                if(selectedDifficulty.getText().equals("Easy"))
                {
                    difficulty = 1;
                }else if(selectedDifficulty.getText().equals("Medium"))
                {
                    difficulty = 2;
                }else if(selectedDifficulty.getText().equals("Hard"))
                {
                    difficulty = 3;
                }

                if (difficulty > 0)
                {
                    //make question container visible
                    transitionsContainerQuestion.setVisibility(View.VISIBLE);
                    //hide difficulty container
                    difficultyContainer.setVisibility(View.GONE);
                    // retrivew all the questions for the selected difficulty
                    questionList = dbHelper.getAllQuestions(difficulty);
                    currentQuestion = questionList.get(questionId);
                    //init question and radio buttons
                    txtQuestion = (TextView) findViewById(R.id.tv_question);
                    rbA = (RadioButton) findViewById(R.id.rb_a);
                    rbB = (RadioButton) findViewById(R.id.rb_b);
                    rbC = (RadioButton) findViewById(R.id.rb_c);
                    rbD = (RadioButton) findViewById(R.id.rb_d);
                    setQuestionView();

                } else
                {
                    difficultyContainer.setVisibility(View.VISIBLE);
                }
            }
        });
        //init all next question button and correct/incorrect textView
        textResult = (TextView) transitionsContainerAnswer.findViewById(R.id.text);
        btnNext = (Button) findViewById(R.id.btn_next);


        btnNext.setOnClickListener(new View.OnClickListener()
        {
            boolean visible;

            @Override
            public void onClick(View v)
            {
                //transition for correct/incorrect answer textView
                Slide slide = new Slide();
                slide.setSlideEdge(Gravity.TOP);

                RadioGroup rbGroup = (RadioGroup) findViewById(R.id.rb_group);
                RadioButton answer = (RadioButton) findViewById(rbGroup.getCheckedRadioButtonId());
                rbGroup.clearCheck();
                //Log.d(TAG, "Your answer: " + answer.getText() + "  Actual answer: " + currentQuestion.getAnswer());
                if (currentQuestion.getAnswer().equals(answer.getText()))
                {
                    score++;
                    Log.d(TAG, "Your score " + score);
                }
                if (questionId < 10)
                {

                    if (!currentQuestion.getAnswer().equals(answer.getText()))
                    {
                        textResult.setTextColor(Color.parseColor("#ff0000"));
                        textResult.setText("Wrong, the correct answer is: " + currentQuestion.getAnswer());
                    } else
                    {
                        textResult.setTextColor(Color.parseColor("#00ff00"));
                        textResult.setText("You right!");
                    }
                    // bring transition to life
                    TransitionManager.beginDelayedTransition(transitionsContainerAnswer, slide);
                    visible = true;
                    textResult.setVisibility(visible ? View.VISIBLE : View.GONE);

                    //delay correct/incorrect answer view
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // Do something after 1s = 1000ms
                            visible = !visible;
                            textResult.setVisibility(visible ? View.VISIBLE : View.GONE);
                        }

                    }, 1200);
                    //delay for setting next question
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // Do something after 1.5s = 1500ms
                            currentQuestion = questionList.get(questionId);
                            setQuestionView();
                        }

                    }, 1800);


                } else
                {
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

    public void setQuestionView() {
        Animation[] animation =
                {
                        AnimationUtils.loadAnimation(this, R.anim.bounce),
                        AnimationUtils.loadAnimation(this, R.anim.fade),
                        AnimationUtils.loadAnimation(this, R.anim.rotate),
                        AnimationUtils.loadAnimation(this, R.anim.sequential),
                        AnimationUtils.loadAnimation(this, R.anim.slide_up),
                        AnimationUtils.loadAnimation(this, R.anim.slide_in),
                        AnimationUtils.loadAnimation(this, R.anim.zoom_out)
                };


        //default checked, to avoid crash in case none is selected
        rbA.setChecked(true);

        Random rnd = new Random();
        int randomNumber = rnd.nextInt(animation.length);

        txtQuestion.setText(currentQuestion.getQuestion());
        rbA.setText(currentQuestion.getOptionA());
        rbB.setText(currentQuestion.getOptionB());
        rbC.setText(currentQuestion.getOptionC());
        rbD.setText(currentQuestion.getOptionD());


        transitionsContainerQuestion.startAnimation(animation[randomNumber]);
        questionId++;
    }
}
