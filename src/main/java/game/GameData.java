package game;

import bits_please_api.APIRequestException;
import bits_please_api.QuestionDifficulty;

import java.awt.*;
import java.util.List;
import java.util.*;

public class GameData {
    private static String[] gameCategories = new String[4];
    private static Color[] categoryColors = new Color[4];
    private static Map<Color, String> colorToCategoryMap = new HashMap<>();
    private static Map<String, List<Question>> categoryToQuestionListMap = null;
    private static List<Question> questionList = null;
    private static List<Question> defaultQuestionList = Question.retrieveAllDefaultQuestions();

    private static boolean useDefaultQuestions = true;
    private static QuestionDifficulty minimumDifficulty = QuestionDifficulty.EASY;
    private static QuestionDifficulty maximumDifficulty = QuestionDifficulty.VERY_HARD;

    /**
    * Sets category and color for a given index
    * @param index - int
    * @param category
    * @param color - color for that category
    */
    public static void setCategoryAndColor(int index, String category, Color color) {
        gameCategories[index] = category;
        categoryColors[index] = color;
        colorToCategoryMap.put(color, category);
    }

    /**
    * Inserts a map entry and returns the map
    * @return the color and category map
    */
    public static Map<Color, String> getColorToCategoryMap() {
        colorToCategoryMap.put(Color.WHITE, "ALL");
        return colorToCategoryMap;
    }

    public static String getCategory(int index) {
        return gameCategories[index];
    }

    public static Color getColor(int index) {
        return categoryColors[index];
    }

    public static Color getColorGivenCategory(String category){
        for (int i = 0; i < gameCategories.length; i++) {
            if (category.equals(gameCategories[i])) {return categoryColors[i];}
        }
        return null;
    }

    public static int getUniqueCategoryCount(){
        return uniqCount(gameCategories);
    }

    public static int getUniqueColorCount(){
        return uniqCount(categoryColors);
    }

    /**
    * Note - default q's already retrieved on initialization of this class
    */
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

    /**
    * Flushes categories, detaches associated colors
    */
    public static void flushCategories(){
        gameCategories = new String[4];
        categoryColors = new Color[4];
    }

    /**
    * @param - list - array of objects
    * @return - int number of distinct objects in object array
    */
    private static int uniqCount(Object[] list) {
        if (list.length == 0) {
            return 0;
        }
        return (int)Arrays.stream(list).distinct().count();
    }

    /**
     * Initializes the categoryToQuestionListMap with questions grouped by category.
     *
     * Should be called upon entering Gameplay.
     */
    public static void initializeQuestionMap() throws APIRequestException {
        if (categoryToQuestionListMap != null){  // categoryToQuestionListMap Already Initialized
            return;
        }
        categoryToQuestionListMap = new HashMap<>();
        List<Question> questions = useDefaultQuestions ? defaultQuestionList : questionList;
        if (questions == null){
            throw new APIRequestException("Questions List is null");
        }

        for (Question q : questions) {
            String category = q.getQuestionCategory();

            if (!categoryToQuestionListMap.containsKey(category)) {
                categoryToQuestionListMap.put(category, new ArrayList<>());
            }
            categoryToQuestionListMap.get(category).add(q);
        }
    }

    /**
     * @return - Question - a random question for the specified category.
     * TODO: Should we remove q from bank after asked on first pass thru q's during gameplay
     */
    public static Question getRandomQuestionByCategory(String category) {
        if (category == null){
            return null;
        }
        List<Question> questions = categoryToQuestionListMap.get(category.toLowerCase()); // Questions from DB all lowercase categories

        if (questions == null || questions.isEmpty()) {
            return null; // or throw an exception, based on how you want to handle it
        }

        Random random = new Random();
        return questions.get(random.nextInt(questions.size()));
    }

    /**
    * @return - Question - a random question from all categories
    * TODO: incorporate more than just the default list
    * if this method is even needed
    */
    public static Question getRandomQuestionAllCategories(){
        Random random = new Random();
        return defaultQuestionList.get(random.nextInt(defaultQuestionList.size()));
    }

}
