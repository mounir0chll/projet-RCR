package fr.master.representationconnaissances.reseauxsemantiques;

import fr.master.representationconnaissances.reseauxsemantiques.demo.DemonstrationReseauxSemantiques;
import fr.master.representationconnaissances.reseauxsemantiques.modele.GrapheSemantique;
import fr.master.representationconnaissances.reseauxsemantiques.raisonnement.GestionnaireExceptions;
import fr.master.representationconnaissances.reseauxsemantiques.raisonnement.RaisonneurHeritage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GestionnaireExceptionsTest {
    @Test
    void robotEnPanneBloqueLaDistributionDeMedicaments() {
        GrapheSemantique graphe = DemonstrationReseauxSemantiques.construireReseauTaxonomique();
        RaisonneurHeritage heritage = new RaisonneurHeritage();
        GestionnaireExceptions exceptions = new GestionnaireExceptions();

        assertTrue(heritage.estUn(graphe, "RobotEnPanne", "RobotLivraison"));
        assertFalse(exceptions.heritePropriete(graphe, "RobotEnPanne", "DistribuerMedicaments"));
        assertTrue(exceptions.possedeProprieteNegativeStricte(graphe, "RobotEnPanne", "DistribuerMedicaments"));
        assertTrue(exceptions.proprieteBloquee(graphe, "RobotEnPanne", "DistribuerMedicaments"));
    }
}
