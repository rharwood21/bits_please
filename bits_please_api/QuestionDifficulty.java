package bits_please_api;

public enum QuestionDifficulty {
    EASY("1"),
    MEDIUM("2"),
    MODERATE("3"),
    HARD("4"),
    VERY_HARD("5");

    private final String value;

    QuestionDifficulty(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static QuestionDifficulty getValue(int difficulty){
        switch (difficulty){
            case 1:
                return EASY;
            case 2:
                return MEDIUM;
            case 3:
                return MODERATE;
            case 4:
                return HARD;
            case 5:
                return VERY_HARD;
            default:
                throw new RuntimeException("Invalid Question Difficulty");
        }
    }
}
