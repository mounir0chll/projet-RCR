package fr.master.representationconnaissances.modale;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ValidationTweetyModaleTest {

    @Test
    void tweetyAnalyseLesFormulesModales() {
        ValidationTweetyModale.ResultatValidation resultat = ValidationTweetyModale.validerSyntaxe();

        assertTrue(resultat.syntaxeValide(), resultat.detail());
        assertEquals(4, resultat.formulesAnalysees().size());
    }
}
