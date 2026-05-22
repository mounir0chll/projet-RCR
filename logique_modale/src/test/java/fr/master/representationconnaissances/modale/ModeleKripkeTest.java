package fr.master.representationconnaissances.modale;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ModeleKripkeTest {

    @Test
    void possibilitesDansM1() {
        ModeleKripke modele = DemonstrationLogiqueModale.creerModele();
        Monde s0 = new Monde("s0");
        FormuleModale energieSecours = new Proposition("energieSecours");
        FormuleModale reseauRadio = new Proposition("reseauRadio");

        assertTrue(new Possible(energieSecours).estVraie(modele, s0));
        assertTrue(new Possible(reseauRadio).estVraie(modele, s0));
        assertTrue(new Et(new Possible(energieSecours), new Possible(reseauRadio)).estVraie(modele, s0));
        assertFalse(new Possible(new Et(energieSecours, reseauRadio)).estVraie(modele, s0));
    }
}
