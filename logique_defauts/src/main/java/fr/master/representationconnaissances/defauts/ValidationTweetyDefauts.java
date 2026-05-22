package fr.master.representationconnaissances.defauts;

import org.tweetyproject.logics.rdl.parser.RdlParser;
import org.tweetyproject.logics.rdl.syntax.DefaultTheory;

import java.io.StringReader;

public class ValidationTweetyDefauts {

    public record ResultatValidation(boolean theorieChargee, boolean theorieAncree, int nombreReglesValidees, String detail) {
    }

    public static ResultatValidation validerTheorieSimple() {
        String theorie = """
                type(DroneMaintenance())
                type(Operationnel())
                type(DroneEndommage())
                type(NonOperationnel())

                DroneMaintenance
                DroneEndommage

                DroneMaintenance::Operationnel/Operationnel
                DroneEndommage::NonOperationnel/NonOperationnel
                """;

        try {
            RdlParser analyseur = new RdlParser();
            DefaultTheory theorieDefauts = analyseur.parseBeliefBase(new StringReader(theorie));
            DefaultTheory theorieAncree = theorieDefauts.ground();
            int nombreRegles = theorieAncree.getDefaults().size();
            return new ResultatValidation(true, nombreRegles > 0, nombreRegles, "");
        } catch (Exception erreur) {
            return new ResultatValidation(false, false, 0, erreur.getClass().getSimpleName() + " : " + erreur.getMessage());
        }
    }
}
