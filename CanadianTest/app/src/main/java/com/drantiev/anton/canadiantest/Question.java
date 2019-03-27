package com.drantiev.anton.canadiantest;

/**
 * Created by anton on 3/27/2019.
 */

public class Question {
    private int Id;
    private String Question;
    private String Answer;
    private String OptionA;
    private String OptionB;
    private String OptionC;

    public Question() {
        Id = 0;
        Question = "";
        Answer = "";
        OptionA = "";
        OptionB = "";
        OptionC = "";
    }
    public Question(String question,String answer, String optionA,String optionB,String optionC){
        Question = question;
        Answer  = answer;
        OptionA = optionA;
        OptionB = optionB;
        OptionC = optionC;
    }


    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String question) {
        Question = question;
    }

    public String getAnswer() {
        return Answer;
    }

    public void setAnswer(String answer) {
        Answer = answer;
    }

    public String getOptionA() {
        return OptionA;
    }

    public void setOptionA(String optionA) {
        OptionA = optionA;
    }

    public String getOptionB() {
        return OptionB;
    }

    public void setOptionB(String optionB) {
        OptionB = optionB;
    }

    public String getOptionC() {
        return OptionC;
    }

    public void setOptionC(String optionC) {
        OptionC = optionC;
    }
}
