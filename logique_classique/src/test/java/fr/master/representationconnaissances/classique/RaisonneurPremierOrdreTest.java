package fr.master.representationconnaissances.classique;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class RaisonneurPremierOrdreTest {

    @Test
    void noraEstAutoriseeAIntervenir() {
        RaisonneurPremierOrdre raisonneur = new RaisonneurPremierOrdre();
        raisonneur.ajouterRegleUniverselle("TechnicienCertifie", "AutoriseIntervention");
        raisonneur.ajouterFait("TechnicienCertifie", "Nora");

        raisonneur.saturer();

        assertTrue(raisonneur.estVrai("AutoriseIntervention", "Nora"));
    }
}
