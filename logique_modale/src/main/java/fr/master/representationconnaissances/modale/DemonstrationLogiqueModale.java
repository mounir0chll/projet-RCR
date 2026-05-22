package fr.master.representationconnaissances.modale;

public class DemonstrationLogiqueModale {

    public static void main(String[] args) {
        ModeleKripke modele = creerModele();
        Monde s0 = new Monde("s0");
        FormuleModale energieSecours = new Proposition("energieSecours");
        FormuleModale reseauRadio = new Proposition("reseauRadio");

        FormuleModale possibleEnergie = new Possible(energieSecours);
        FormuleModale possibleRadio = new Possible(reseauRadio);
        FormuleModale deuxPossibilites = new Et(possibleEnergie, possibleRadio);
        FormuleModale possibiliteConjointe = new Possible(new Et(energieSecours, reseauRadio));
        FormuleModale nonPossibiliteConjointe = new Non(possibiliteConjointe);
        ValidationTweetyModale.ResultatValidation validationTweety = ValidationTweetyModale.validerSyntaxe();

        System.out.println("=== Logique modale : modèle de Kripke ===");
        System.out.println("Exemple original : choix de plans dans un centre de secours.");
        System.out.println("Logique utilisée : logique modale avec mondes possibles et relation d'accessibilité.");
        System.out.println("Toolbox utilisée : TweetyProject Modal Logic pour analyser les formules modales, puis modèle de Kripke interne pour fixer les mondes du domaine.");
        System.out.println("Validation syntaxique TweetyProject : " + (validationTweety.syntaxeValide() ? "vrai" : "faux"));
        System.out.println("Mondes : W = {s0, planA, planB, controle}");
        System.out.println("Relation R :");
        System.out.println("- s0 → planA");
        System.out.println("- s0 → planB");
        System.out.println("- planA → controle");
        System.out.println("- planB → controle");
        System.out.println("Valuation :");
        System.out.println("- energieSecours est vrai dans planA");
        System.out.println("- reseauRadio est vrai dans planB");
        System.out.println("Monde de départ : s0");
        afficherRequete(modele, s0, possibleEnergie);
        afficherRequete(modele, s0, possibleRadio);
        afficherRequete(modele, s0, deuxPossibilites);
        afficherRequete(modele, s0, possibiliteConjointe);
        System.out.println("Conclusion symbolique : " + nonPossibiliteConjointe.afficher());
        System.out.println("Interprétation : un plan peut rendre l'énergie de secours possible et un autre plan peut rendre le réseau radio possible, sans qu'un même plan rende les deux possibles simultanément.");
    }

    public static ModeleKripke creerModele() {
        ModeleKripke modele = new ModeleKripke();
        Monde s0 = new Monde("s0");
        Monde planA = new Monde("planA");
        Monde planB = new Monde("planB");
        Monde controle = new Monde("controle");

        modele.ajouterMonde(s0);
        modele.ajouterMonde(planA);
        modele.ajouterMonde(planB);
        modele.ajouterMonde(controle);

        modele.ajouterAccessibilite(s0, planA);
        modele.ajouterAccessibilite(s0, planB);
        modele.ajouterAccessibilite(planA, controle);
        modele.ajouterAccessibilite(planB, controle);

        modele.rendreVrai(planA, "energieSecours");
        modele.rendreVrai(planB, "reseauRadio");

        return modele;
    }

    private static void afficherRequete(ModeleKripke modele, Monde monde, FormuleModale formule) {
        System.out.println("Requête : " + formule.afficher());
        System.out.println("Résultat : " + (formule.estVraie(modele, monde) ? "vrai" : "faux"));
    }
}
