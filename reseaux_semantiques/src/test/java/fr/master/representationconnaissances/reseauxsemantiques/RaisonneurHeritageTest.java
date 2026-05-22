package fr.master.representationconnaissances.reseauxsemantiques;

import fr.master.representationconnaissances.reseauxsemantiques.demo.DemonstrationReseauxSemantiques;
import fr.master.representationconnaissances.reseauxsemantiques.modele.GrapheSemantique;
import fr.master.representationconnaissances.reseauxsemantiques.raisonnement.RaisonneurHeritage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class RaisonneurHeritageTest {
    @Test
    void mediBotHeriteDesConceptsPartiesEtProprietes() {
        GrapheSemantique graphe = DemonstrationReseauxSemantiques.construireReseauTaxonomique();
        RaisonneurHeritage raisonneur = new RaisonneurHeritage();

        assertTrue(raisonneur.estUn(graphe, "MediBot7", "RobotMedical"));
        assertTrue(raisonneur.estUn(graphe, "MediBot7", "AppareilMedical"));
        assertTrue(raisonneur.estUn(graphe, "MediBot7", "RessourceHospitaliere"));
        assertTrue(raisonneur.proprietesHeriteesIds(graphe, "MediBot7").contains("Tracable"));
        assertTrue(raisonneur.proprietesHeriteesIds(graphe, "MediBot7").contains("DistribuerMedicaments"));
        assertTrue(raisonneur.partiesHeriteesIds(graphe, "MediBot7").contains("Batterie"));
        assertTrue(raisonneur.partiesHeriteesIds(graphe, "MediBot7").contains("Capteurs"));
        assertTrue(raisonneur.partiesHeriteesIds(graphe, "MediBot7").contains("CompartimentSterile"));
        assertTrue(raisonneur.partiesHeriteesIds(graphe, "MediBot7").contains("Roues"));
    }
}
