package fr.master.representationconnaissances.defauts;

import java.util.Optional;

public class RegleDefaut {

    private final String condition;
    private final String justification;
    private final String conclusion;
    private final int priorite;

    public RegleDefaut(String condition, String conclusion, int priorite) {
        this(condition, conclusion, conclusion, priorite);
    }

    public RegleDefaut(String condition, String justification, String conclusion, int priorite) {
        this.condition = condition;
        this.justification = justification;
        this.conclusion = conclusion;
        this.priorite = priorite;
    }

    public String condition() {
        return condition;
    }

    public String justification() {
        return justification;
    }

    public String conclusion() {
        return conclusion;
    }

    public int priorite() {
        return priorite;
    }

    public Optional<String> conclusionPour(String fait) {
        if (!predicat(fait).equals(predicat(condition))) {
            return Optional.empty();
        }
        return Optional.of(formater(predicat(conclusion), individu(fait)));
    }

    public static String predicat(String expression) {
        return expression.substring(0, expression.indexOf('('));
    }

    public static String individu(String expression) {
        return expression.substring(expression.indexOf('(') + 1, expression.indexOf(')'));
    }

    public static String formater(String predicat, String individu) {
        return predicat + "(" + individu + ")";
    }
}
