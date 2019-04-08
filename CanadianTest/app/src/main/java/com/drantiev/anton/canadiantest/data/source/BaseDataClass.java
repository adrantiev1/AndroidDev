package com.drantiev.anton.canadiantest.data.source;

/**
 * Created by anton on 3/27/2019.
 */

public class BaseDataClass {
    public static final String TABLE_QUESTIONS = "questions";
    //Columns Questions Table
    public static final String C_ID = "id";
    public static final String C_DIFFICULTY_ID = "difficultyId";
    public static final String C_QUESTION = "question";
    public static final String C_ANSWER = "answer";
    public static final String C_OPTION_A = "optionA";
    public static final String C_OPTION_B = "optionB";
    public static final String C_OPTION_C = "optionC";
    public static final String C_OPTION_D = "optionD";

    //Columns Difficulty Table
    public static final String TABLE_DIFFICULTY = "difficulty";
    public static final String C_DIFFICULTY_TITLE= "difficultyTitle";
}
