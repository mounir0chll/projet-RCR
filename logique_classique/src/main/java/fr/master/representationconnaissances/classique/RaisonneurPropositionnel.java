package fr.master.representationconnaissances.classique;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class RaisonneurPropositionnel {

    public interface Formule {
        boolean evaluer(Map<String, Boolean> valuation);

        Set<String> variables();

        String texte();
    }

    public record Atome(String nom) implements Formule {
        @Override
        public boolean evaluer(Map<String, Boolean> valuation) {
            return valuation.getOrDefault(nom, false);
        }

        @Override
        public Set<String> variables() {
            return Set.of(nom);
        }

        @Override
        public String texte() {
            return nom;
        }
    }

    public record Non(Formule formule) implements Formule {
        @Override
        public boolean evaluer(Map<String, Boolean> valuation) {
            return !formule.evaluer(valuation);
        }

        @Override
        public Set<String> variables() {
            return formule.variables();
        }

        @Override
        public String texte() {
            return "¬" + formule.texte();
        }
    }

    public record Et(Formule gauche, Formule droite) implements Formule {
        @Override
        public boolean evaluer(Map<String, Boolean> valuation) {
            return gauche.evaluer(valuation) && droite.evaluer(valuation);
        }

        @Override
        public Set<String> variables() {
            LinkedHashSet<String> variables = new LinkedHashSet<>(gauche.variables());
            variables.addAll(droite.variables());
            return variables;
        }

        @Override
        public String texte() {
            return "(" + gauche.texte() + " ∧ " + droite.texte() + ")";
        }
    }

    public record Implique(Formule antecedent, Formule consequent) implements Formule {
        @Override
        public boolean evaluer(Map<String, Boolean> valuation) {
            return !antecedent.evaluer(valuation) || consequent.evaluer(valuation);
        }

        @Override
        public Set<String> variables() {
            LinkedHashSet<String> variables = new LinkedHashSet<>(antecedent.variables());
            variables.addAll(consequent.variables());
            return variables;
        }

        @Override
        public String texte() {
            return antecedent.texte() + " → " + consequent.texte();
        }
    }

    public static Formule atome(String nom) {
        return new Atome(nom);
    }

    public static Formule non(Formule formule) {
        return new Non(formule);
    }

    public static Formule et(Formule gauche, Formule droite) {
        return new Et(gauche, droite);
    }

    public static Formule implique(Formule antecedent, Formule consequent) {
        return new Implique(antecedent, consequent);
    }

    public boolean estSatisfiable(Collection<Formule> formules) {
        return trouverModele(formules).isPresent();
    }

    public boolean estIncoherente(Collection<Formule> formules) {
        return !estSatisfiable(formules);
    }

    public boolean deduitParAbsurde(Collection<Formule> base, Formule requete) {
        ArrayList<Formule> avecNegation = new ArrayList<>(base);
        avecNegation.add(non(requete));
        return estIncoherente(avecNegation);
    }

    public Optional<Map<String, Boolean>> trouverModele(Collection<Formule> formules) {
        List<String> variables = variablesDe(formules);
        return chercherModele(formules, variables, 0, new LinkedHashMap<>());
    }

    private Optional<Map<String, Boolean>> chercherModele(Collection<Formule> formules, List<String> variables, int position, Map<String, Boolean> valuation) {
        if (position == variables.size()) {
            if (verifieToutes(formules, valuation)) {
                return Optional.of(new LinkedHashMap<>(valuation));
            }
            return Optional.empty();
        }
        String variable = variables.get(position);
        valuation.put(variable, false);
        Optional<Map<String, Boolean>> modeleFaux = chercherModele(formules, variables, position + 1, valuation);
        if (modeleFaux.isPresent()) {
            return modeleFaux;
        }
        valuation.put(variable, true);
        Optional<Map<String, Boolean>> modeleVrai = chercherModele(formules, variables, position + 1, valuation);
        valuation.remove(variable);
        return modeleVrai;
    }

    private boolean verifieToutes(Collection<Formule> formules, Map<String, Boolean> valuation) {
        for (Formule formule : formules) {
            if (!formule.evaluer(valuation)) {
                return false;
            }
        }
        return true;
    }

    private List<String> variablesDe(Collection<Formule> formules) {
        LinkedHashSet<String> variables = new LinkedHashSet<>();
        for (Formule formule : formules) {
            variables.addAll(formule.variables());
        }
        return new ArrayList<>(variables);
    }
}
