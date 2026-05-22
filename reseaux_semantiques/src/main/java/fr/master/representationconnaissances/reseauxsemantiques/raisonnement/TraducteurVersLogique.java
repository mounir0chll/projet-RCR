package fr.master.representationconnaissances.reseauxsemantiques.raisonnement;

import fr.master.representationconnaissances.reseauxsemantiques.modele.ArcSemantique;
import fr.master.representationconnaissances.reseauxsemantiques.modele.GrapheSemantique;
import fr.master.representationconnaissances.reseauxsemantiques.modele.TypeArc;

import java.text.Normalizer;
import java.util.List;

public class TraducteurVersLogique {
    public String traduireArc(GrapheSemantique graphe, ArcSemantique arc) {
        String source = nomLogique(graphe.etiquette(arc.source()));
        String cible = nomLogique(graphe.etiquette(arc.cible()));
        if (arc.type() == TypeArc.INSTANCE_DE) {
            return cible + "(" + source + ")";
        }
        if (arc.type() == TypeArc.EST_UN) {
            return "is_a(" + source + "," + cible + ")";
        }
        if (arc.type() == TypeArc.PROPRIETE) {
            return "propriete(" + source + "," + cible + ")";
        }
        return predicat(arc.type()) + "(" + source + "," + cible + ")";
    }

    public List<String> traduireArcs(GrapheSemantique graphe, List<ArcSemantique> arcs) {
        return arcs.stream().map(arc -> traduireArc(graphe, arc)).toList();
    }

    private String predicat(TypeArc type) {
        return switch (type) {
            case AGENT -> "Agent";
            case OBJET -> "Objet";
            case DESTINATAIRE -> "Destinataire";
            case EXPEDITEUR -> "Expediteur";
            case BENEFICIAIRE -> "Beneficiaire";
            case ARGUMENT -> "Argument";
            case A_POUR_PARTIE -> "a_pour_partie";
            case CONTIENT -> "contient";
            case DANS_CONTEXTE -> "dans_contexte";
            case NEGATION_DE -> "negation_de";
            case EXCEPTION_A -> "exception_a";
            case PROPRIETE_NEGATIVE -> "propriete_negative";
            default -> type.name().toLowerCase();
        };
    }

    private String nomLogique(String etiquette) {
        String normalise = Normalizer.normalize(etiquette, Normalizer.Form.NFD).replaceAll("\\p{M}", "");
        return normalise.replaceAll("[^A-Za-z0-9]+", "_").replaceAll("^_+|_+$", "");
    }
}
