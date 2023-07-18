package game;

import bits_please_api.QuestionDifficulty;

import java.util.Arrays;
import java.util.List;

public class GameData {
    private static String[] gameCategories = new String[4];
    private static String[] categoryColors = new String[4];

    private static List<Question> questionList = null;
    private static List<Question> defaultQuestionList = Question.retrieveAllDefaultQuestions();

    private static boolean useDefaultQuestions = true;
    private static QuestionDifficulty minimumDifficulty = QuestionDifficulty.EASY;
    private static QuestionDifficulty maximumDifficulty = QuestionDifficulty.VERY_HARD;

    public static void setCategoryAndColor(int index, String category, String color) {
    	gameCategories[index] = category;
    	categoryColors[index] = color;
    }

    public static String getCategory(int index) {
        return gameCategories[index];
    }

    public static String getColor(int index) {
        return categoryColors[index];
    }

    public static int getUniqueCategoryCount(){
        return uniqCount(gameCategories);
    }

    public static int getUniqueColorCount(){
        return uniqCount(categoryColors);
    }

    public static List<Question> getDefaultQuestionList() {
        return defaultQuestionList;
    }

    public static boolean useDefaultQuestions() {
        return useDefaultQuestions;
    }

    public static void setUseDefaultQuestions(boolean useDefaultQuestions) {
        GameData.useDefaultQuestions = useDefaultQuestions;
    }
    public static void setDifficultyRange(int minimum, int maximum){
        if (minimum < 1 || minimum > 5 || maximum < 1 || maximum > 5){
            throw new RuntimeException("Difficulty must be within 1-5");
        } if (maximum < minimum){
            throw new RuntimeException("Minimimum difficulty must be less than maximum.");
        }
        GameData.minimumDifficulty = QuestionDifficulty.getValue(minimum);
        GameData.maximumDifficulty = QuestionDifficulty.getValue(maximum);
    }

    public static void setQuestionList(List<Question> questionList) {
        GameData.questionList = questionList;
    }

    public static void flushCategories(){
    	gameCategories = new String[4];
    	categoryColors = new String[4];
    }
    
    private static int uniqCount(String[] list) {
    	if (list.length == 0) {
    		return 0;
    	}
    	return (int)Arrays.stream(list).distinct().count(); 
    }
}
