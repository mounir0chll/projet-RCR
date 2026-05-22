package fr.master.representationconnaissances.modale;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class ModeleKripke {

    private final Set<Monde> mondes = new LinkedHashSet<>();
    private final Map<Monde, Set<Monde>> accessibilite = new LinkedHashMap<>();
    private final Map<Monde, Set<String>> valuation = new LinkedHashMap<>();

    public void ajouterMonde(Monde monde) {
        mondes.add(monde);
        accessibilite.putIfAbsent(monde, new LinkedHashSet<>());
        valuation.putIfAbsent(monde, new LinkedHashSet<>());
    }

    public void ajouterAccessibilite(Monde source, Monde cible) {
        ajouterMonde(source);
        ajouterMonde(cible);
        accessibilite.get(source).add(cible);
    }

    public void rendreVrai(Monde monde, String proposition) {
        ajouterMonde(monde);
        valuation.get(monde).add(proposition);
    }

    public boolean estVraie(String proposition, Monde monde) {
        return valuation.getOrDefault(monde, Set.of()).contains(proposition);
    }

    public boolean possible(FormuleModale formule, Monde monde) {
        for (Monde accessible : mondesAccessiblesDepuis(monde)) {
            if (formule.estVraie(this, accessible)) {
                return true;
            }
        }
        return false;
    }

    public boolean necessaire(FormuleModale formule, Monde monde) {
        for (Monde accessible : mondesAccessiblesDepuis(monde)) {
            if (!formule.estVraie(this, accessible)) {
                return false;
            }
        }
        return true;
    }

    public Set<Monde> mondes() {
        return Collections.unmodifiableSet(mondes);
    }

    public Set<Monde> mondesAccessiblesDepuis(Monde monde) {
        return Collections.unmodifiableSet(accessibilite.getOrDefault(monde, Set.of()));
    }

    public Set<String> propositionsVraiesDans(Monde monde) {
        return Collections.unmodifiableSet(valuation.getOrDefault(monde, Set.of()));
    }
}
