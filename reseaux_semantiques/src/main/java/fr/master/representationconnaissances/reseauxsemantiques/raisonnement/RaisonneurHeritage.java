package fr.master.representationconnaissances.reseauxsemantiques.raisonnement;

import fr.master.representationconnaissances.reseauxsemantiques.modele.ArcSemantique;
import fr.master.representationconnaissances.reseauxsemantiques.modele.GrapheSemantique;
import fr.master.representationconnaissances.reseauxsemantiques.modele.TypeArc;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class RaisonneurHeritage {
    public Set<String> idsHeritage(GrapheSemantique graphe, String idNoeud) {
        LinkedHashSet<String> resultat = new LinkedHashSet<>();
        ArrayDeque<String> file = new ArrayDeque<>();
        resultat.add(idNoeud);
        file.add(idNoeud);
        while (!file.isEmpty()) {
            String courant = file.removeFirst();
            for (ArcSemantique arc : graphe.arcsSortants(courant)) {
                if ((arc.type() == TypeArc.INSTANCE_DE || arc.type() == TypeArc.EST_UN) && resultat.add(arc.cible())) {
                    file.addLast(arc.cible());
                }
            }
        }
        return resultat;
    }

    public Set<String> etiquettesHeritage(GrapheSemantique graphe, String idNoeud) {
        LinkedHashSet<String> resultat = new LinkedHashSet<>();
        for (String id : idsHeritage(graphe, idNoeud)) {
            if (!id.equals(idNoeud)) {
                resultat.add(graphe.etiquette(id));
            }
        }
        return resultat;
    }

    public boolean estUn(GrapheSemantique graphe, String idNoeud, String idConcept) {
        return idsHeritage(graphe, idNoeud).contains(idConcept);
    }

    public Set<String> proprietesHeriteesIds(GrapheSemantique graphe, String idNoeud) {
        LinkedHashSet<String> resultat = new LinkedHashSet<>();
        for (String id : idsHeritage(graphe, idNoeud)) {
            for (ArcSemantique arc : graphe.arcsSortants(id)) {
                if (arc.type() == TypeArc.PROPRIETE) {
                    resultat.add(arc.cible());
                }
            }
        }
        return resultat;
    }

    public Set<String> proprietesHeritees(GrapheSemantique graphe, String idNoeud) {
        LinkedHashSet<String> resultat = new LinkedHashSet<>();
        for (String id : proprietesHeriteesIds(graphe, idNoeud)) {
            resultat.add(graphe.etiquette(id));
        }
        return resultat;
    }

    public Set<String> partiesHeriteesIds(GrapheSemantique graphe, String idNoeud) {
        LinkedHashSet<String> resultat = new LinkedHashSet<>();
        List<String> ids = new ArrayList<>(idsHeritage(graphe, idNoeud));
        Collections.reverse(ids);
        for (String id : ids) {
            for (ArcSemantique arc : graphe.arcsSortants(id)) {
                if (arc.type() == TypeArc.A_POUR_PARTIE) {
                    resultat.add(arc.cible());
                }
            }
        }
        return resultat;
    }

    public Set<String> partiesHeritees(GrapheSemantique graphe, String idNoeud) {
        LinkedHashSet<String> resultat = new LinkedHashSet<>();
        for (String id : partiesHeriteesIds(graphe, idNoeud)) {
            resultat.add(graphe.etiquette(id));
        }
        return resultat;
    }
}
