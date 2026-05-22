package fr.master.representationconnaissances.description;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class LogiqueDescriptionTest {

    @Test
    void familleEstChargeableEtMaryEstGrandMere() throws Exception {
        DemonstrationLogiqueDescription.ResultatVerification resultat = DemonstrationLogiqueDescription.verifierMaryGrandMere();

        assertTrue(resultat.ontologieChargee());
        assertTrue(resultat.maryGrandMere());
    }
}
