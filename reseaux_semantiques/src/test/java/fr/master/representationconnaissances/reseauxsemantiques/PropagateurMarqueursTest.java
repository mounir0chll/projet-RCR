package fr.master.representationconnaissances.reseauxsemantiques;

import fr.master.representationconnaissances.reseauxsemantiques.demo.DemonstrationReseauxSemantiques;
import fr.master.representationconnaissances.reseauxsemantiques.modele.GrapheSemantique;
import fr.master.representationconnaissances.reseauxsemantiques.modele.NoeudSemantique;
import fr.master.representationconnaissances.reseauxsemantiques.raisonnement.PropagateurMarqueurs;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PropagateurMarqueursTest {
    @Test
    void protocoleTeleconsultationEstUtiliseParDeuxServices() {
        GrapheSemantique graphe = DemonstrationReseauxSemantiques.construireReseauProtocoles();
        PropagateurMarqueurs propagateur = new PropagateurMarqueurs();

        Set<String> reponses = propagateur.rechercherConteneurs(graphe, "OrganisationHopital", "TeleconsultationSecuree").stream()
                .map(NoeudSemantique::etiquette)
                .collect(Collectors.toSet());

        assertEquals(2, reponses.size());
        assertTrue(reponses.contains("Service de cardiologie"));
        assertTrue(reponses.contains("Cellule de télémédecine"));
        assertFalse(reponses.contains("Service de radiologie"));
        assertFalse(reponses.contains("Cellule de maintenance"));
    }
}
