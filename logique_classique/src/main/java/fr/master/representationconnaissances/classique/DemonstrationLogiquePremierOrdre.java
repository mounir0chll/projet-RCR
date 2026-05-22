package fr.master.representationconnaissances.classique;

public class DemonstrationLogiquePremierOrdre {

    public static void executer() {
        RaisonneurPremierOrdre raisonneur = new RaisonneurPremierOrdre();
        raisonneur.ajouterRegleUniverselle("TechnicienCertifie", "AutoriseIntervention");
        raisonneur.ajouterFait("TechnicienCertifie", "Nora");
        raisonneur.saturer();

        boolean resultat = raisonneur.estVrai("AutoriseIntervention", "Nora");

        System.out.println("=== Logique classique : petit exemple du premier ordre ===");
        System.out.println("Exemple original : accès à une salle technique.");
        System.out.println("Base de connaissances :");
        System.out.println("- TechnicienCertifie(Nora)");
        System.out.println("- ∀x(TechnicienCertifie(x) → AutoriseIntervention(x))");
        System.out.println("Requête testée : BC ⊨ AutoriseIntervention(Nora)");
        System.out.println("Résultat : " + (resultat ? "vrai" : "faux"));
        System.out.println("Interprétation : la règle universelle s'applique à l'individu Nora, donc Nora est autorisée à intervenir.");
        System.out.println();
    }
}
