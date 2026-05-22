package fr.master.representationconnaissances.defauts;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ValidationTweetyDefautsTest {

    @Test
    void tweetyChargeEtValideUneTheorieDeDefauts() {
        ValidationTweetyDefauts.ResultatValidation resultat = ValidationTweetyDefauts.validerTheorieSimple();

        assertTrue(resultat.theorieChargee(), resultat.detail());
        assertTrue(resultat.theorieAncree(), resultat.detail());
    }
}
