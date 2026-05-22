package fr.master.representationconnaissances.classique;

import fr.master.representationconnaissances.classique.RaisonneurPropositionnel.Formule;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DemonstrationLogiquePropositionnelle {

    public static void executer() {
        RaisonneurPropositionnel raisonneur = new RaisonneurPropositionnel();
        Formule fumee = RaisonneurPropositionnel.atome("fumee");
        Formule alarme = RaisonneurPropositionnel.atome("alarme");
        Formule evacuation = RaisonneurPropositionnel.atome("evacuation");

        List<Formule> base = List.of(
                RaisonneurPropositionnel.implique(fumee, alarme),
                RaisonneurPropositionnel.implique(alarme, evacuation),
                fumee
        );

        List<Formule> incoherente = List.of(fumee, RaisonneurPropositionnel.non(fumee));
        ArrayList<Formule> baseAvecNonEvacuation = new ArrayList<>(base);
        baseAvecNonEvacuation.add(RaisonneurPropositionnel.non(evacuation));

        boolean satisfiable = raisonneur.estSatisfiable(base);
        boolean contradiction = raisonneur.estIncoherente(incoherente);
        boolean absurdite = raisonneur.estIncoherente(baseAvecNonEvacuation);
        boolean deduit = raisonneur.deduitParAbsurde(base, evacuation);
        VerificationTweetyPropositionnelle.ResultatTweety resultatTweety = VerificationTweetyPropositionnelle.verifier();

        System.out.println("=== Logique classique : logique propositionnelle ===");
        System.out.println("Exemple original : système d'alarme d'un laboratoire.");
        System.out.println("Toolbox utilisée : TweetyProject Propositional Logic pour vérifier l'inférence, et fichiers CNF exploitables avec UBCSAT.");
        System.out.println("Base de connaissances :");
        for (Formule formule : base) {
            System.out.println("- " + formule.texte());
        }
        System.out.println("Requête testée : BC ⊨ evacuation");
        System.out.println("Satisfiabilité de la base : " + afficherBooleen(satisfiable));
        System.out.println("Test d'incohérence de {fumee, ¬fumee} : " + afficherBooleen(contradiction));
        System.out.println("Preuve par l'absurde : BC ∪ {¬evacuation} est insatisfiable : " + afficherBooleen(absurdite));
        System.out.println("Résultat : BC ⊨ evacuation : " + afficherBooleen(deduit));
        System.out.println("Vérification TweetyProject : BC ⊨ evacuation = " + afficherBooleen(resultatTweety.evacuationDeduite()));
        System.out.println("Vérification TweetyProject : contradiction {fumee, ¬fumee} = " + afficherBooleen(resultatTweety.contradictionDetectee()));
        System.out.println("Vérification TweetyProject : BC ∪ {¬evacuation} insatisfiable = " + afficherBooleen(resultatTweety.absurditeValidee()));
        System.out.println("Interprétation : si la fumée déclenche l'alarme, si l'alarme impose l'évacuation et si la fumée est observée, alors l'évacuation est une conséquence logique stricte.");
        raisonneur.trouverModele(base).ifPresent(DemonstrationLogiquePropositionnelle::afficherModele);
        System.out.println();
    }

    private static void afficherModele(Map<String, Boolean> modele) {
        System.out.println("Exemple de valuation satisfaisant la base : " + modele);
    }

    private static String afficherBooleen(boolean valeur) {
        return valeur ? "vrai" : "faux";
    }
}
