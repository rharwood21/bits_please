package game;

import java.util.Arrays;

public class GameData {
    private static String[] gameCategories = new String[4];
    private static String[] categoryColors = new String[4];

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
