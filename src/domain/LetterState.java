package domain;

public enum LetterState {
    GREEN("green"), YELLOW("goldenrod"), GRAY("grey"), WHITE("lightgrey");

    private String name;

    private LetterState(String s) {
        this.name = s;
    }

    @Override
    public String toString() {
        return name;
    }
}