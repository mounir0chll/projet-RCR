package fr.master.representationconnaissances.reseauxsemantiques.raisonnement;

import fr.master.representationconnaissances.reseauxsemantiques.modele.ArcSemantique;
import fr.master.representationconnaissances.reseauxsemantiques.modele.GrapheSemantique;
import fr.master.representationconnaissances.reseauxsemantiques.modele.TypeArc;

import java.util.Set;

public class GestionnaireExceptions {
    private final RaisonneurHeritage raisonneurHeritage;

    public GestionnaireExceptions() {
        this.raisonneurHeritage = new RaisonneurHeritage();
    }

    public boolean heritePropriete(GrapheSemantique graphe, String idNoeud, String idPropriete) {
        return raisonneurHeritage.proprietesHeriteesIds(graphe, idNoeud).contains(idPropriete)
                && !proprieteBloquee(graphe, idNoeud, idPropriete);
    }

    public boolean possedeProprieteNegativeStricte(GrapheSemantique graphe, String idNoeud, String idPropriete) {
        for (String id : raisonneurHeritage.idsHeritage(graphe, idNoeud)) {
            for (ArcSemantique arc : graphe.arcsSortants(id)) {
                if (arc.type() == TypeArc.PROPRIETE_NEGATIVE && arc.cible().equals(idPropriete) && arc.strict()) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean proprieteBloquee(GrapheSemantique graphe, String idNoeud, String idPropriete) {
        if (!possedeProprieteNegativeStricte(graphe, idNoeud, idPropriete)) {
            return false;
        }
        return proprietePositiveHeriteeNonStricte(graphe, idNoeud, idPropriete)
                || exceptionSpecifique(graphe, idNoeud, idPropriete);
    }

    private boolean proprietePositiveHeriteeNonStricte(GrapheSemantique graphe, String idNoeud, String idPropriete) {
        Set<String> ids = raisonneurHeritage.idsHeritage(graphe, idNoeud);
        for (String id : ids) {
            if (id.equals(idNoeud)) {
                continue;
            }
            for (ArcSemantique arc : graphe.arcsSortants(id)) {
                if (arc.type() == TypeArc.PROPRIETE && arc.cible().equals(idPropriete) && !arc.strict()) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean exceptionSpecifique(GrapheSemantique graphe, String idNoeud, String idPropriete) {
        Set<String> ids = raisonneurHeritage.idsHeritage(graphe, idNoeud);
        for (String id : ids) {
            for (ArcSemantique exception : graphe.arcsSortants(id)) {
                if (exception.type() != TypeArc.EXCEPTION_A) {
                    continue;
                }
                for (ArcSemantique arc : graphe.arcsSortants(exception.cible())) {
                    if (arc.type() == TypeArc.PROPRIETE && arc.cible().equals(idPropriete) && !arc.strict()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
