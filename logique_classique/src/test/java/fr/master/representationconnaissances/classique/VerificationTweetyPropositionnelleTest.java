package fr.master.representationconnaissances.classique;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class VerificationTweetyPropositionnelleTest {

    @Test
    void tweetyVerifieLaDeductionEtLaContradiction() {
        VerificationTweetyPropositionnelle.ResultatTweety resultat = VerificationTweetyPropositionnelle.verifier();

        assertTrue(resultat.evacuationDeduite());
        assertTrue(resultat.contradictionDetectee());
        assertTrue(resultat.absurditeValidee());
    }
}
