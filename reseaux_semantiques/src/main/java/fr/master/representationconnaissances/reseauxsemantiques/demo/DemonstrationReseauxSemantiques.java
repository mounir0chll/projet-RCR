package fr.master.representationconnaissances.reseauxsemantiques.demo;

import fr.master.representationconnaissances.reseauxsemantiques.export.ExporteurDot;
import fr.master.representationconnaissances.reseauxsemantiques.modele.ArcSemantique;
import fr.master.representationconnaissances.reseauxsemantiques.modele.ContexteSemantique;
import fr.master.representationconnaissances.reseauxsemantiques.modele.GrapheSemantique;
import fr.master.representationconnaissances.reseauxsemantiques.modele.NoeudSemantique;
import fr.master.representationconnaissances.reseauxsemantiques.modele.TypeArc;
import fr.master.representationconnaissances.reseauxsemantiques.modele.TypeNoeud;
import fr.master.representationconnaissances.reseauxsemantiques.raisonnement.GestionnaireExceptions;
import fr.master.representationconnaissances.reseauxsemantiques.raisonnement.PropagateurMarqueurs;
import fr.master.representationconnaissances.reseauxsemantiques.raisonnement.RaisonneurHeritage;
import fr.master.representationconnaissances.reseauxsemantiques.raisonnement.TraducteurVersLogique;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DemonstrationReseauxSemantiques {
    public static void main(String[] args) throws IOException {
        GrapheSemantique graphe = construireReseauComplet();
        RaisonneurHeritage heritage = new RaisonneurHeritage();
        GestionnaireExceptions exceptions = new GestionnaireExceptions();
        PropagateurMarqueurs propagateur = new PropagateurMarqueurs();
        TraducteurVersLogique traducteur = new TraducteurVersLogique();
        ExporteurDot exporteur = new ExporteurDot();

        System.out.println("=== Réseaux sémantiques : centre hospitalier intelligent ===");
        System.out.println("Représentation utilisée : concepts, individus, événements, contextes et arcs typés.");
        System.out.println("Exemple original : gestion de robots médicaux, services et protocoles hospitaliers.");
        System.out.println("Toolbox utilisée : structure Java en mémoire et export Graphviz DOT.");
        System.out.println();

        System.out.println("1. Taxonomie et héritage");
        System.out.println("Base : MediBot7 INSTANCE_DE RobotLivraison, RobotLivraison EST_UN RobotMedical, RobotMedical EST_UN AppareilMedical, AppareilMedical EST_UN RessourceHospitaliere.");
        System.out.println("MediBot7 est un RobotMedical : " + booleen(heritage.estUn(graphe, "MediBot7", "RobotMedical")));
        System.out.println("MediBot7 est un AppareilMedical : " + booleen(heritage.estUn(graphe, "MediBot7", "AppareilMedical")));
        System.out.println("MediBot7 est une RessourceHospitaliere : " + booleen(heritage.estUn(graphe, "MediBot7", "RessourceHospitaliere")));
        System.out.println("MediBot7 est traçable : " + booleen(heritage.proprietesHeriteesIds(graphe, "MediBot7").contains("Tracable")));
        System.out.println("MediBot7 a pour partie : " + String.join(", ", heritage.partiesHeritees(graphe, "MediBot7")));
        System.out.println("MediBot7 hérite de la propriété DistribuerMedicaments : " + booleen(heritage.proprietesHeriteesIds(graphe, "MediBot7").contains("DistribuerMedicaments")));
        System.out.println();

        System.out.println("2. Exceptions et blocage d'inférence");
        System.out.println("RobotLivraison possède typiquement la propriété DistribuerMedicaments.");
        System.out.println("RobotEnPanne est un RobotLivraison : " + booleen(heritage.estUn(graphe, "RobotEnPanne", "RobotLivraison")));
        System.out.println("RobotEnPanne possède localement la propriété négative Ne pas DistribuerMedicaments : " + booleen(exceptions.possedeProprieteNegativeStricte(graphe, "RobotEnPanne", "DistribuerMedicaments")));
        System.out.println("Conclusion : l'héritage de DistribuerMedicaments est bloqué pour RobotEnPanne : " + booleen(!exceptions.heritePropriete(graphe, "RobotEnPanne", "DistribuerMedicaments")));
        System.out.println();

        System.out.println("3. Propagation de marqueurs");
        System.out.println("Question : quels services du centre hospitalier utilisent le protocole TeleconsultationSecuree ?");
        Set<NoeudSemantique> reponses = propagateur.rechercherConteneurs(graphe, "OrganisationHopital", "TeleconsultationSecuree");
        System.out.println("Réponses : " + reponses.stream().map(NoeudSemantique::etiquette).collect(Collectors.joining(", ")));
        System.out.println();

        System.out.println("4. Actions et rôles sémantiques");
        System.out.println(graphe.decrireEvenement("Livrer-1"));
        System.out.println(graphe.decrireEvenement("Livrer-2"));
        System.out.println();

        System.out.println("5. Contextes et partitions");
        System.out.println("Contexte Croyance_ChefService : le chef de service croit une proposition négative.");
        System.out.println("Proposition représentée : le chef croit que MediBot7 n'est pas disponible.");
        System.out.println("Limite : le réseau représente la modalité, mais ne calcule pas la vérité modale.");
        System.out.println();

        System.out.println("6. Traduction vers la logique du premier ordre");
        for (ArcSemantique arc : arcsPourTraduction(graphe)) {
            System.out.println("- " + traducteur.traduireArc(graphe, arc));
        }
        System.out.println();

        String cheminAffiche = "reseaux_semantiques/reseau_semantique.dot";
        Path cheminDot = Path.of("reseaux_semantiques", "reseau_semantique.dot");
        exporteur.exporter(graphe, cheminDot);
        System.out.println("7. Export Graphviz DOT");
        System.out.println("Fichier généré : " + cheminAffiche);
        System.out.println("Interprétation : le fichier DOT permet de visualiser le réseau, sans nécessiter Graphviz pour compiler le projet.");
    }

    public static GrapheSemantique construireReseauTaxonomique() {
        GrapheSemantique graphe = new GrapheSemantique();
        ajouterNoeudsTaxonomiques(graphe);
        ajouterArcsTaxonomiques(graphe);
        return graphe;
    }

    public static GrapheSemantique construireReseauProtocoles() {
        GrapheSemantique graphe = new GrapheSemantique();
        ajouterNoeudsProtocoles(graphe);
        ajouterArcsProtocoles(graphe);
        return graphe;
    }

    public static GrapheSemantique construireReseauActions() {
        GrapheSemantique graphe = new GrapheSemantique();
        ajouterNoeudsActions(graphe);
        ajouterArcsActions(graphe);
        return graphe;
    }

    public static GrapheSemantique construireReseauContextes() {
        GrapheSemantique graphe = new GrapheSemantique();
        ajouterNoeudsActions(graphe);
        ajouterArcsActions(graphe);
        ajouterNoeudsContextes(graphe);
        ajouterArcsContextes(graphe);
        return graphe;
    }

    public static GrapheSemantique construireReseauComplet() {
        GrapheSemantique graphe = new GrapheSemantique();
        ajouterNoeudsTaxonomiques(graphe);
        ajouterArcsTaxonomiques(graphe);
        ajouterNoeudsProtocoles(graphe);
        ajouterArcsProtocoles(graphe);
        ajouterNoeudsActions(graphe);
        ajouterArcsActions(graphe);
        ajouterNoeudsContextes(graphe);
        ajouterArcsContextes(graphe);
        return graphe;
    }

    private static void ajouterNoeudsTaxonomiques(GrapheSemantique graphe) {
        graphe.ajouterNoeud("RessourceHospitaliere", "RessourceHospitaliere", TypeNoeud.CONCEPT);
        graphe.ajouterNoeud("AppareilMedical", "AppareilMedical", TypeNoeud.CONCEPT);
        graphe.ajouterNoeud("RobotMedical", "RobotMedical", TypeNoeud.CONCEPT);
        graphe.ajouterNoeud("RobotLivraison", "RobotLivraison", TypeNoeud.CONCEPT);
        graphe.ajouterNoeud("RobotEnPanne", "RobotEnPanne", TypeNoeud.CONCEPT);
        graphe.ajouterNoeud("Tracable", "Tracable", TypeNoeud.CONCEPT);
        graphe.ajouterNoeud("Batterie", "Batterie", TypeNoeud.CONCEPT);
        graphe.ajouterNoeud("Capteurs", "Capteurs", TypeNoeud.CONCEPT);
        graphe.ajouterNoeud("CompartimentSterile", "CompartimentSterile", TypeNoeud.CONCEPT);
        graphe.ajouterNoeud("Roues", "Roues", TypeNoeud.CONCEPT);
        graphe.ajouterNoeud("DistribuerMedicaments", "DistribuerMedicaments", TypeNoeud.CONCEPT);
        graphe.ajouterNoeud("MediBot7", "MediBot7", TypeNoeud.INDIVIDU);
    }

    private static void ajouterArcsTaxonomiques(GrapheSemantique graphe) {
        graphe.ajouterArc("AppareilMedical", "RessourceHospitaliere", TypeArc.EST_UN);
        graphe.ajouterArc("RobotMedical", "AppareilMedical", TypeArc.EST_UN);
        graphe.ajouterArc("RobotLivraison", "RobotMedical", TypeArc.EST_UN);
        graphe.ajouterArc("RobotEnPanne", "RobotLivraison", TypeArc.EST_UN);
        graphe.ajouterArc("MediBot7", "RobotLivraison", TypeArc.INSTANCE_DE);
        graphe.ajouterArc("RessourceHospitaliere", "Tracable", TypeArc.PROPRIETE, true, 1);
        graphe.ajouterArc("AppareilMedical", "Batterie", TypeArc.A_POUR_PARTIE, true, 1);
        graphe.ajouterArc("RobotMedical", "Capteurs", TypeArc.A_POUR_PARTIE, true, 1);
        graphe.ajouterArc("RobotLivraison", "CompartimentSterile", TypeArc.A_POUR_PARTIE, true, 1);
        graphe.ajouterArc("RobotLivraison", "Roues", TypeArc.A_POUR_PARTIE, true, 1);
        graphe.ajouterArc("RobotLivraison", "DistribuerMedicaments", TypeArc.PROPRIETE, false, 0);
        graphe.ajouterArc("RobotEnPanne", "DistribuerMedicaments", TypeArc.PROPRIETE_NEGATIVE, true, 2);
        graphe.ajouterArc("RobotEnPanne", "RobotLivraison", TypeArc.EXCEPTION_A, true, 2);
    }

    private static void ajouterNoeudsProtocoles(GrapheSemantique graphe) {
        graphe.ajouterNoeud("OrganisationHopital", "Organisation de l'hôpital", TypeNoeud.CONCEPT);
        graphe.ajouterNoeud("ServicesMedicaux", "Services médicaux", TypeNoeud.CONCEPT);
        graphe.ajouterNoeud("ServicesTechniques", "Services techniques", TypeNoeud.CONCEPT);
        graphe.ajouterNoeud("ServiceUrgences", "Service des urgences", TypeNoeud.CONCEPT);
        graphe.ajouterNoeud("ServiceCardiologie", "Service de cardiologie", TypeNoeud.CONCEPT);
        graphe.ajouterNoeud("ServiceRadiologie", "Service de radiologie", TypeNoeud.CONCEPT);
        graphe.ajouterNoeud("ServicePediatrie", "Service de pédiatrie", TypeNoeud.CONCEPT);
        graphe.ajouterNoeud("CelluleTelemedecine", "Cellule de télémédecine", TypeNoeud.CONCEPT);
        graphe.ajouterNoeud("CelluleMaintenance", "Cellule de maintenance", TypeNoeud.CONCEPT);
        graphe.ajouterNoeud("TeleconsultationSecuree", "TeleconsultationSecuree", TypeNoeud.PROPOSITION);
        graphe.ajouterNoeud("ProtocoleImagerie", "ProtocoleImagerie", TypeNoeud.PROPOSITION);
        graphe.ajouterNoeud("ProtocoleMaintenance", "ProtocoleMaintenance", TypeNoeud.PROPOSITION);
    }

    private static void ajouterArcsProtocoles(GrapheSemantique graphe) {
        graphe.ajouterArc("ServicesMedicaux", "OrganisationHopital", TypeArc.EST_UN);
        graphe.ajouterArc("ServicesTechniques", "OrganisationHopital", TypeArc.EST_UN);
        graphe.ajouterArc("ServiceUrgences", "ServicesMedicaux", TypeArc.EST_UN);
        graphe.ajouterArc("ServiceCardiologie", "ServicesMedicaux", TypeArc.EST_UN);
        graphe.ajouterArc("ServiceRadiologie", "ServicesMedicaux", TypeArc.EST_UN);
        graphe.ajouterArc("ServicePediatrie", "ServicesMedicaux", TypeArc.EST_UN);
        graphe.ajouterArc("CelluleTelemedecine", "ServicesTechniques", TypeArc.EST_UN);
        graphe.ajouterArc("CelluleMaintenance", "ServicesTechniques", TypeArc.EST_UN);
        graphe.ajouterArc("ServiceCardiologie", "TeleconsultationSecuree", TypeArc.CONTIENT);
        graphe.ajouterArc("CelluleTelemedecine", "TeleconsultationSecuree", TypeArc.CONTIENT);
        graphe.ajouterArc("ServiceRadiologie", "ProtocoleImagerie", TypeArc.CONTIENT);
        graphe.ajouterArc("CelluleMaintenance", "ProtocoleMaintenance", TypeArc.CONTIENT);
    }

    private static void ajouterNoeudsActions(GrapheSemantique graphe) {
        graphe.ajouterNoeud("Livrer-1", "Livrer-1", TypeNoeud.EVENEMENT);
        graphe.ajouterNoeud("Livrer-2", "Livrer-2", TypeNoeud.EVENEMENT);
        graphe.ajouterNoeud("MediBot7", "MediBot7", TypeNoeud.INDIVIDU);
        graphe.ajouterNoeud("Ines", "Ines", TypeNoeud.INDIVIDU);
        graphe.ajouterNoeud("DrSamir", "DrSamir", TypeNoeud.INDIVIDU);
        graphe.ajouterNoeud("PatientNora", "PatientNora", TypeNoeud.INDIVIDU);
        graphe.ajouterNoeud("KitAnalyse", "KitAnalyse", TypeNoeud.INDIVIDU);
        graphe.ajouterNoeud("DossierRadio", "DossierRadio", TypeNoeud.INDIVIDU);
    }

    private static void ajouterArcsActions(GrapheSemantique graphe) {
        graphe.ajouterArc("Livrer-1", "MediBot7", TypeArc.AGENT);
        graphe.ajouterArc("Livrer-1", "KitAnalyse", TypeArc.OBJET);
        graphe.ajouterArc("Livrer-1", "PatientNora", TypeArc.DESTINATAIRE);
        graphe.ajouterArc("Livrer-2", "Ines", TypeArc.AGENT);
        graphe.ajouterArc("Livrer-2", "DossierRadio", TypeArc.OBJET);
        graphe.ajouterArc("Livrer-2", "DrSamir", TypeArc.DESTINATAIRE);
    }

    private static void ajouterNoeudsContextes(GrapheSemantique graphe) {
        graphe.ajouterNoeud("Croyance_ChefService", "Croyance_ChefService", TypeNoeud.CONTEXTE);
        graphe.ajouterNoeud("Negation_1", "Negation_1", TypeNoeud.CONTEXTE);
        graphe.ajouterNoeud("Disponible", "Disponible", TypeNoeud.CONCEPT);
        graphe.ajouterNoeud("ChefService", "ChefService", TypeNoeud.INDIVIDU);
        graphe.ajouterNoeud("Croire-1", "Croire-1", TypeNoeud.EVENEMENT);
        graphe.ajouterNoeud("Negation-1", "Negation-1", TypeNoeud.PROPOSITION);
        graphe.ajouterContexte(new ContexteSemantique("Croyance_ChefService", "Croyance_ChefService")
                .ajouterNoeudLocal("Croire-1")
                .ajouterNoeudLocal("ChefService")
                .ajouterNoeudLocal("Negation-1"));
        graphe.ajouterContexte(new ContexteSemantique("Negation_1", "Negation_1", "Croyance_ChefService")
                .ajouterNoeudLocal("Negation-1")
                .ajouterNoeudLocal("MediBot7")
                .ajouterNoeudLocal("Disponible"));
    }

    private static void ajouterArcsContextes(GrapheSemantique graphe) {
        graphe.ajouterArc("Croire-1", "ChefService", TypeArc.AGENT);
        graphe.ajouterArc("Croire-1", "Negation-1", TypeArc.OBJET);
        graphe.ajouterArc("Negation-1", "MediBot7", TypeArc.ARGUMENT);
        graphe.ajouterArc("Croire-1", "Croyance_ChefService", TypeArc.DANS_CONTEXTE);
        graphe.ajouterArc("Negation-1", "Negation_1", TypeArc.DANS_CONTEXTE);
        graphe.ajouterArc("MediBot7", "Disponible", TypeArc.EST_UN).ajouterAttribut("contexte", "Negation_1");
    }

    private static List<ArcSemantique> arcsPourTraduction(GrapheSemantique graphe) {
        return List.of(
                trouverArc(graphe, "MediBot7", "RobotLivraison", TypeArc.INSTANCE_DE),
                trouverArc(graphe, "RobotLivraison", "RobotMedical", TypeArc.EST_UN),
                trouverArc(graphe, "RessourceHospitaliere", "Tracable", TypeArc.PROPRIETE),
                trouverArc(graphe, "Livrer-1", "MediBot7", TypeArc.AGENT),
                trouverArc(graphe, "Livrer-1", "KitAnalyse", TypeArc.OBJET),
                trouverArc(graphe, "Livrer-1", "PatientNora", TypeArc.DESTINATAIRE)
        );
    }

    private static ArcSemantique trouverArc(GrapheSemantique graphe, String source, String cible, TypeArc type) {
        return graphe.arcs().stream()
                .filter(arc -> arc.source().equals(source) && arc.cible().equals(cible) && arc.type() == type)
                .findFirst()
                .orElseThrow();
    }

    private static String booleen(boolean valeur) {
        return valeur ? "vrai" : "faux";
    }
}
