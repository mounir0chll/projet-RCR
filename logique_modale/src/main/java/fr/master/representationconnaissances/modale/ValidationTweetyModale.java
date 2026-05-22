package fr.master.representationconnaissances.modale;

import org.tweetyproject.logics.ml.parser.MlParser;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class ValidationTweetyModale {

    public record ResultatValidation(boolean syntaxeValide, List<String> formulesAnalysees, String detail) {
    }

    public static ResultatValidation validerSyntaxe() {
        List<String> textes = List.of(
                "<>(EnergieSecours)",
                "<>(ReseauRadio)",
                "<>(EnergieSecours) && <>(ReseauRadio)",
                "<>(EnergieSecours && ReseauRadio)"
        );
        MlParser analyseur = new MlParser();
        List<String> formules = new ArrayList<>();
        try {
            analyseur.parseBeliefBase(new StringReader("""
                    type(EnergieSecours())
                    type(ReseauRadio())
                    """));
            for (String texte : textes) {
                formules.add(analyseur.parseFormula(texte).toString());
            }
            return new ResultatValidation(true, formules, "");
        } catch (Exception erreur) {
            return new ResultatValidation(false, formules, erreur.getClass().getSimpleName() + " : " + erreur.getMessage());
        }
    }
}
