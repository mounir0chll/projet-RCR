package fr.master.representationconnaissances.reseauxsemantiques.raisonnement;

import fr.master.representationconnaissances.reseauxsemantiques.modele.ArcSemantique;
import fr.master.representationconnaissances.reseauxsemantiques.modele.GrapheSemantique;
import fr.master.representationconnaissances.reseauxsemantiques.modele.NoeudSemantique;
import fr.master.representationconnaissances.reseauxsemantiques.modele.TypeArc;

import java.util.ArrayDeque;
import java.util.LinkedHashSet;
import java.util.Set;

public class PropagateurMarqueurs {
    public Set<NoeudSemantique> rechercherConteneurs(GrapheSemantique graphe, String idRacine, String idContenu) {
        Set<String> marqueurM1 = propagerInverse(graphe, idRacine, TypeArc.EST_UN);
        Set<String> marqueurM2 = new LinkedHashSet<>();
        marqueurM2.add(idContenu);
        LinkedHashSet<NoeudSemantique> resultats = new LinkedHashSet<>();
        for (String id : marqueurM1) {
            for (ArcSemantique arc : graphe.arcsSortants(id)) {
                if (arc.type() == TypeArc.CONTIENT && marqueurM2.contains(arc.cible())) {
                    graphe.trouverNoeud(id).ifPresent(resultats::add);
                }
            }
        }
        return resultats;
    }

    public Set<String> propagerInverse(GrapheSemantique graphe, String idDepart, TypeArc typeArc) {
        LinkedHashSet<String> marques = new LinkedHashSet<>();
        ArrayDeque<String> file = new ArrayDeque<>();
        marques.add(idDepart);
        file.add(idDepart);
        while (!file.isEmpty()) {
            String courant = file.removeFirst();
            for (ArcSemantique arc : graphe.arcsEntrants(courant)) {
                if (arc.type() == typeArc && marques.add(arc.source())) {
                    file.addLast(arc.source());
                }
            }
        }
        return marques;
    }
}
