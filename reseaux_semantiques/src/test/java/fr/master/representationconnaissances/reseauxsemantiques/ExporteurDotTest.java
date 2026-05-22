package fr.master.representationconnaissances.reseauxsemantiques;

import fr.master.representationconnaissances.reseauxsemantiques.demo.DemonstrationReseauxSemantiques;
import fr.master.representationconnaissances.reseauxsemantiques.export.ExporteurDot;
import fr.master.representationconnaissances.reseauxsemantiques.modele.GrapheSemantique;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ExporteurDotTest {
    @Test
    void exportDotContientLesElementsPrincipaux() {
        GrapheSemantique graphe = DemonstrationReseauxSemantiques.construireReseauTaxonomique();
        ExporteurDot exporteur = new ExporteurDot();

        String dot = exporteur.genererDot(graphe);

        assertTrue(dot.contains("digraph"));
        assertTrue(dot.contains("MediBot7"));
        assertTrue(dot.contains("RobotLivraison"));
        assertTrue(dot.contains("RessourceHospitaliere"));
        assertTrue(dot.contains("EST_UN"));
        assertTrue(dot.contains("INSTANCE_DE"));
        assertTrue(dot.contains("A_POUR_PARTIE"));
    }
}
