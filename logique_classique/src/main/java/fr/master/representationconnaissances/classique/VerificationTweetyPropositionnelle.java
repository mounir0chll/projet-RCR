package fr.master.representationconnaissances.classique;

import org.tweetyproject.logics.pl.reasoner.SimplePlReasoner;
import org.tweetyproject.logics.pl.syntax.Contradiction;
import org.tweetyproject.logics.pl.syntax.Implication;
import org.tweetyproject.logics.pl.syntax.Negation;
import org.tweetyproject.logics.pl.syntax.PlBeliefSet;
import org.tweetyproject.logics.pl.syntax.Proposition;

public class VerificationTweetyPropositionnelle {

    public record ResultatTweety(boolean evacuationDeduite, boolean contradictionDetectee, boolean absurditeValidee) {
    }

    public static ResultatTweety verifier() {
        Proposition fumee = new Proposition("fumee");
        Proposition alarme = new Proposition("alarme");
        Proposition evacuation = new Proposition("evacuation");

        PlBeliefSet base = new PlBeliefSet();
        base.add(new Implication(fumee, alarme));
        base.add(new Implication(alarme, evacuation));
        base.add(fumee);

        PlBeliefSet incoherente = new PlBeliefSet();
        incoherente.add(fumee);
        incoherente.add(new Negation(fumee));

        PlBeliefSet baseAvecNegation = new PlBeliefSet();
        baseAvecNegation.addAll(base);
        baseAvecNegation.add(new Negation(evacuation));

        SimplePlReasoner raisonneur = new SimplePlReasoner();
        boolean evacuationDeduite = raisonneur.query(base, evacuation);
        boolean contradictionDetectee = raisonneur.query(incoherente, new Contradiction());
        boolean absurditeValidee = raisonneur.query(baseAvecNegation, new Contradiction());

        return new ResultatTweety(evacuationDeduite, contradictionDetectee, absurditeValidee);
    }
}
