package fr.master.representationconnaissances.classique;

import fr.master.representationconnaissances.classique.RaisonneurPropositionnel.Formule;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RaisonneurPropositionnelTest {

    @Test
    void evacuationEstDeduiteParAbsurde() {
        RaisonneurPropositionnel raisonneur = new RaisonneurPropositionnel();
        Formule fumee = RaisonneurPropositionnel.atome("fumee");
        Formule alarme = RaisonneurPropositionnel.atome("alarme");
        Formule evacuation = RaisonneurPropositionnel.atome("evacuation");

        List<Formule> base = List.of(
                RaisonneurPropositionnel.implique(fumee, alarme),
                RaisonneurPropositionnel.implique(alarme, evacuation),
                fumee
        );

        assertTrue(raisonneur.estSatisfiable(base));
        assertTrue(raisonneur.deduitParAbsurde(base, evacuation));
        assertFalse(raisonneur.estSatisfiable(List.of(fumee, RaisonneurPropositionnel.non(fumee))));
    }
}
