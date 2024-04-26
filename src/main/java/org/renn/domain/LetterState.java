/**
 * Date: April 26, 2024
 * Name: Renn Gilbert
 * Class: CSC1061
 */

package org.renn.domain;

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