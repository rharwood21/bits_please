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
}
