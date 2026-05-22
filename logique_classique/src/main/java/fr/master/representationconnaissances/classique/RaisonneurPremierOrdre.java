package fr.master.representationconnaissances.classique;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class RaisonneurPremierOrdre {

    private final Set<String> faits = new LinkedHashSet<>();
    private final List<RegleUniverselle> regles = new ArrayList<>();

    public record RegleUniverselle(String antecedent, String consequent) {
    }

    public void ajouterFait(String predicat, String individu) {
        faits.add(formater(predicat, individu));
    }

    public void ajouterRegleUniverselle(String antecedent, String consequent) {
        regles.add(new RegleUniverselle(antecedent, consequent));
    }

    public void saturer() {
        boolean changement;
        do {
            changement = false;
            List<String> faitsCourants = new ArrayList<>(faits);
            for (RegleUniverselle regle : regles) {
                for (String fait : faitsCourants) {
                    if (predicat(fait).equals(regle.antecedent())) {
                        String nouveauFait = formater(regle.consequent(), individu(fait));
                        if (faits.add(nouveauFait)) {
                            changement = true;
                        }
                    }
                }
            }
        } while (changement);
    }

    public boolean estVrai(String predicat, String individu) {
        return faits.contains(formater(predicat, individu));
    }

    public Set<String> faits() {
        return Collections.unmodifiableSet(faits);
    }

    private String formater(String predicat, String individu) {
        return predicat + "(" + individu + ")";
    }

    private String predicat(String fait) {
        return fait.substring(0, fait.indexOf('('));
    }

    private String individu(String fait) {
        return fait.substring(fait.indexOf('(') + 1, fait.indexOf(')'));
    }
}
