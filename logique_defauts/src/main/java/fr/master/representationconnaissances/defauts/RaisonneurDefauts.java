package fr.master.representationconnaissances.defauts;

import fr.master.representationconnaissances.defauts.TheorieDefauts.RegleStricte;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class RaisonneurDefauts {

    public Set<String> calculerExtension(TheorieDefauts theorie) {
        LinkedHashSet<String> extension = new LinkedHashSet<>(theorie.faitsStricts());
        appliquerReglesStrictes(extension, theorie.reglesStrictes());

        List<RegleDefaut> defauts = new ArrayList<>(theorie.reglesDefaut());
        defauts.sort(Comparator.comparingInt(RegleDefaut::priorite).reversed());

        boolean changement;
        do {
            changement = false;
            for (RegleDefaut defaut : defauts) {
                List<String> faitsCourants = new ArrayList<>(extension);
                for (String fait : faitsCourants) {
                    for (String conclusion : defaut.conclusionPour(fait).stream().toList()) {
                        if (!extension.contains(conclusion) && estCoherent(extension, conclusion)) {
                            extension.add(conclusion);
                            appliquerReglesStrictes(extension, theorie.reglesStrictes());
                            changement = true;
                        }
                    }
                }
            }
        } while (changement);

        return extension;
    }

    public boolean contient(Set<String> extension, String fait) {
        return extension.contains(fait);
    }

    private void appliquerReglesStrictes(Set<String> extension, List<RegleStricte> reglesStrictes) {
        boolean changement;
        do {
            changement = false;
            List<String> faitsCourants = new ArrayList<>(extension);
            for (RegleStricte regle : reglesStrictes) {
                for (String fait : faitsCourants) {
                    if (RegleDefaut.predicat(fait).equals(RegleDefaut.predicat(regle.condition()))) {
                        String nouveauFait = RegleDefaut.formater(RegleDefaut.predicat(regle.conclusion()), RegleDefaut.individu(fait));
                        if (extension.add(nouveauFait)) {
                            changement = true;
                        }
                    }
                }
            }
        } while (changement);
    }

    private boolean estCoherent(Set<String> extension, String conclusion) {
        return !extension.contains(contraire(conclusion));
    }

    private String contraire(String fait) {
        String predicat = RegleDefaut.predicat(fait);
        String individu = RegleDefaut.individu(fait);
        if (predicat.equals("Vole")) {
            return RegleDefaut.formater("NeVolePas", individu);
        }
        if (predicat.equals("NeVolePas")) {
            return RegleDefaut.formater("Vole", individu);
        }
        if (predicat.startsWith("Non") && predicat.length() > 3) {
            return RegleDefaut.formater(predicat.substring(3), individu);
        }
        return RegleDefaut.formater("Non" + predicat, individu);
    }
}
