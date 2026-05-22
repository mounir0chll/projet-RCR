package fr.master.representationconnaissances.defauts;

import java.util.Set;
import java.util.stream.Collectors;

public class DemonstrationLogiqueDefauts {

    public static void main(String[] args) {
        RaisonneurDefauts raisonneur = new RaisonneurDefauts();

        TheorieDefauts scenarioDroneDisponible = scenarioDroneDisponible();
        Set<String> extensionDroneDisponible = raisonneur.calculerExtension(scenarioDroneDisponible);

        TheorieDefauts scenarioDroneEndommage = scenarioDroneEndommage();
        Set<String> extensionDroneEndommage = raisonneur.calculerExtension(scenarioDroneEndommage);
        ValidationTweetyDefauts.ResultatValidation validationTweety = ValidationTweetyDefauts.validerTheorieSimple();

        System.out.println("=== Logique des défauts : raisonnement non monotone ===");
        System.out.println("Exemple original : supervision de drones de maintenance.");
        System.out.println("Logique utilisée : logique des défauts avec règles normales A : B / B.");
        System.out.println("Toolbox utilisée : TweetyProject Reiter's Default Logic pour charger et valider une théorie de défauts.");
        System.out.println("Validation TweetyProject : théorie chargée = " + afficherBooleen(validationTweety.theorieChargee()) + ", règles de défaut validées = " + validationTweety.nombreReglesValidees());
        System.out.println();
        afficherScenarioDroneDisponible(raisonneur, extensionDroneDisponible);
        afficherScenarioDroneEndommage(raisonneur, extensionDroneEndommage);
        System.out.println("Interprétation : quand le drone est seulement connu comme drone de maintenance, le défaut général permet de conclure qu'il est opérationnel. Quand on ajoute qu'il est endommagé, l'exception bloque le défaut général et permet de conclure qu'il n'est pas opérationnel.");
    }

    public static TheorieDefauts scenarioDroneDisponible() {
        TheorieDefauts theorie = new TheorieDefauts();
        theorie.ajouterFaitStrict("DroneMaintenance(droneAlpha)");
        theorie.ajouterRegleDefaut(new RegleDefaut("DroneMaintenance(x)", "Operationnel(x)", 1));
        return theorie;
    }

    public static TheorieDefauts scenarioDroneEndommage() {
        TheorieDefauts theorie = new TheorieDefauts();
        theorie.ajouterFaitStrict("DroneEndommage(droneAlpha)");
        theorie.ajouterRegleStricte("DroneEndommage(x)", "DroneMaintenance(x)");
        theorie.ajouterRegleDefaut(new RegleDefaut("DroneEndommage(x)", "NonOperationnel(x)", 2));
        theorie.ajouterRegleDefaut(new RegleDefaut("DroneMaintenance(x)", "Operationnel(x)", 1));
        return theorie;
    }

    private static void afficherScenarioDroneDisponible(RaisonneurDefauts raisonneur, Set<String> extension) {
        System.out.println("Scénario 1 : droneAlpha est seulement connu comme drone de maintenance.");
        System.out.println("Base stricte :");
        System.out.println("- DroneMaintenance(droneAlpha)");
        System.out.println("Défaut :");
        System.out.println("- DroneMaintenance(x) : Operationnel(x) / Operationnel(x)");
        System.out.println("Extension obtenue : " + afficherExtension(extension));
        System.out.println("Requête : Operationnel(droneAlpha)");
        System.out.println("Résultat : " + afficherBooleen(raisonneur.contient(extension, "Operationnel(droneAlpha)")));
        System.out.println();
    }

    private static void afficherScenarioDroneEndommage(RaisonneurDefauts raisonneur, Set<String> extension) {
        System.out.println("Scénario 2 : droneAlpha est un drone endommagé.");
        System.out.println("Base stricte :");
        System.out.println("- DroneEndommage(droneAlpha)");
        System.out.println("- DroneEndommage(x) → DroneMaintenance(x)");
        System.out.println("Défauts :");
        System.out.println("- DroneMaintenance(x) : Operationnel(x) / Operationnel(x)");
        System.out.println("- DroneEndommage(x) : NonOperationnel(x) / NonOperationnel(x)");
        System.out.println("Extension obtenue : " + afficherExtension(extension));
        System.out.println("Requête : DroneMaintenance(droneAlpha)");
        System.out.println("Résultat : " + afficherBooleen(raisonneur.contient(extension, "DroneMaintenance(droneAlpha)")));
        System.out.println("Requête : Operationnel(droneAlpha)");
        System.out.println("Résultat : " + afficherBooleen(raisonneur.contient(extension, "Operationnel(droneAlpha)")) + " (défaut général bloqué)");
        System.out.println("Requête : NonOperationnel(droneAlpha)");
        System.out.println("Résultat : " + afficherBooleen(raisonneur.contient(extension, "NonOperationnel(droneAlpha)")));
        System.out.println();
    }

    private static String afficherBooleen(boolean valeur) {
        return valeur ? "vrai" : "faux";
    }

    private static String afficherExtension(Set<String> extension) {
        return extension.stream().collect(Collectors.joining(", ", "{", "}"));
    }
}
