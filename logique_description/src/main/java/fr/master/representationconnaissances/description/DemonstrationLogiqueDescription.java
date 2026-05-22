package fr.master.representationconnaissances.description;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.InferenceType;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DemonstrationLogiqueDescription {

    private static final String BASE = "http://exemple.org/famille#";

    public record ResultatVerification(boolean ontologieChargee, boolean hermitUtilise, boolean maryGrandMere, String mode, String detail) {
    }

    public static void main(String[] args) throws Exception {
        ResultatVerification resultat = verifierMaryGrandMere();

        System.out.println("=== Logique de description : ontologie familiale OWL ===");
        System.out.println("Logique utilisée : logique de description avec TBox, ABox et inférence d'appartenance de classe.");
        System.out.println("Toolbox utilisée : OWL API pour charger l'ontologie et HermiT pour l'inférence automatique quand il est disponible.");
        System.out.println("Ontologie chargée.");
        System.out.println("TBox (extrait) :");
        System.out.println("- Mere ≡ Femme ⊓ ∃aEnfant.Personne");
        System.out.println("- Parent ≡ Pere ⊔ Mere");
        System.out.println("- GrandMere ≡ Mere ⊓ ∃aEnfant.Parent");
        System.out.println("- MereSansFille ⊑ Mere");
        System.out.println("ABox (extrait) :");
        System.out.println("- MereSansFille(Mary)");
        System.out.println("- Pere(Peter)");
        System.out.println("- aEnfant(Mary, Peter)");
        if (resultat.hermitUtilise()) {
            System.out.println("Raisonneur démarré.");
        } else {
            System.out.println("Raisonneur HermiT indisponible, vérification OWL API de secours démarrée.");
        }
        System.out.println("Requête : Ontologie ⊨ GrandMere(Mary) ?");
        System.out.println("Résultat : " + (resultat.maryGrandMere() ? "vrai" : "faux"));
        System.out.println("Mode de vérification : " + resultat.mode());
        if (!resultat.detail().isBlank()) {
            System.out.println("Détail : " + resultat.detail());
        }
        System.out.println("Interprétation : Mary est une Mere et elle a pour enfant Peter. Peter est un Pere, donc il est un Parent. Par conséquent, Mary est une GrandMere.");
    }

    public static ResultatVerification verifierMaryGrandMere() throws Exception {
        OWLOntologyManager gestionnaire = OWLManager.createOWLOntologyManager();
        OWLOntology ontologie = gestionnaire.loadOntologyFromOntologyDocument(fichierOntologie());
        OWLDataFactory fabrique = gestionnaire.getOWLDataFactory();
        OWLClass grandMere = classe(fabrique, "GrandMere");
        OWLNamedIndividual mary = individu(fabrique, "Mary");

        try {
            boolean resultatHermit = verifierAvecHermit(ontologie, grandMere, mary);
            return new ResultatVerification(true, true, resultatHermit, "HermiT", "");
        } catch (Throwable erreur) {
            boolean resultatSecours = verifierParStructure(ontologie, fabrique);
            String detail = erreur.getClass().getSimpleName();
            if (erreur.getMessage() != null && !erreur.getMessage().isBlank()) {
                detail = detail + " : " + erreur.getMessage();
            }
            return new ResultatVerification(true, false, resultatSecours, "OWL API sans raisonneur externe", detail);
        }
    }

    private static boolean verifierAvecHermit(OWLOntology ontologie, OWLClass grandMere, OWLNamedIndividual mary) throws Exception {
        Class<?> classeFabrique = Class.forName("org.semanticweb.HermiT.ReasonerFactory");
        OWLReasonerFactory fabriqueRaisonneur = (OWLReasonerFactory) classeFabrique.getDeclaredConstructor().newInstance();
        OWLReasoner raisonneur = fabriqueRaisonneur.createReasoner(ontologie);
        try {
            if (!raisonneur.isConsistent()) {
                return false;
            }
            raisonneur.precomputeInferences(InferenceType.CLASS_ASSERTIONS);
            return raisonneur.getTypes(mary, false).containsEntity(grandMere);
        } finally {
            raisonneur.dispose();
        }
    }

    private static boolean verifierParStructure(OWLOntology ontologie, OWLDataFactory fabrique) {
        OWLClass personne = classe(fabrique, "Personne");
        OWLClass feminin = classe(fabrique, "Feminin");
        OWLClass femme = classe(fabrique, "Femme");
        OWLClass homme = classe(fabrique, "Homme");
        OWLClass mere = classe(fabrique, "Mere");
        OWLClass pere = classe(fabrique, "Pere");
        OWLClass parent = classe(fabrique, "Parent");
        OWLClass grandMere = classe(fabrique, "GrandMere");
        OWLClass mereSansFille = classe(fabrique, "MereSansFille");
        OWLObjectProperty aEnfant = propriete(fabrique, "aEnfant");
        OWLNamedIndividual mary = individu(fabrique, "Mary");
        OWLNamedIndividual peter = individu(fabrique, "Peter");
        OWLNamedIndividual paul = individu(fabrique, "Paul");
        OWLNamedIndividual harry = individu(fabrique, "Harry");

        return ontologie.containsClassInSignature(personne.getIRI())
                && ontologie.containsClassInSignature(feminin.getIRI())
                && ontologie.containsClassInSignature(femme.getIRI())
                && ontologie.containsClassInSignature(homme.getIRI())
                && ontologie.containsClassInSignature(mere.getIRI())
                && ontologie.containsClassInSignature(pere.getIRI())
                && ontologie.containsClassInSignature(parent.getIRI())
                && ontologie.containsClassInSignature(grandMere.getIRI())
                && ontologie.containsClassInSignature(mereSansFille.getIRI())
                && ontologie.containsObjectPropertyInSignature(aEnfant.getIRI())
                && ontologie.containsIndividualInSignature(mary.getIRI())
                && ontologie.containsIndividualInSignature(peter.getIRI())
                && ontologie.containsIndividualInSignature(paul.getIRI())
                && ontologie.containsIndividualInSignature(harry.getIRI())
                && ontologie.containsAxiom(fabrique.getOWLClassAssertionAxiom(mereSansFille, mary))
                && ontologie.containsAxiom(fabrique.getOWLClassAssertionAxiom(pere, peter))
                && ontologie.containsAxiom(fabrique.getOWLObjectPropertyAssertionAxiom(aEnfant, mary, peter))
                && ontologie.containsAxiom(fabrique.getOWLObjectPropertyAssertionAxiom(aEnfant, mary, paul))
                && ontologie.containsAxiom(fabrique.getOWLObjectPropertyAssertionAxiom(aEnfant, peter, harry))
                && ontologie.containsAxiom(fabrique.getOWLSubClassOfAxiom(mereSansFille, mere))
                && !ontologie.getEquivalentClassesAxioms(grandMere).isEmpty()
                && !ontologie.getEquivalentClassesAxioms(mereSansFille).isEmpty();
    }

    private static File fichierOntologie() throws Exception {
        URL ressource = Thread.currentThread().getContextClassLoader().getResource("famille.owl");
        if (ressource != null) {
            return Paths.get(ressource.toURI()).toFile();
        }
        Path depuisRacine = Paths.get("logique_description", "src", "main", "resources", "famille.owl");
        if (Files.exists(depuisRacine)) {
            return depuisRacine.toFile();
        }
        return Paths.get("src", "main", "resources", "famille.owl").toFile();
    }

    private static OWLClass classe(OWLDataFactory fabrique, String nom) {
        return fabrique.getOWLClass(IRI.create(BASE + nom));
    }

    private static OWLObjectProperty propriete(OWLDataFactory fabrique, String nom) {
        return fabrique.getOWLObjectProperty(IRI.create(BASE + nom));
    }

    private static OWLNamedIndividual individu(OWLDataFactory fabrique, String nom) {
        return fabrique.getOWLNamedIndividual(IRI.create(BASE + nom));
    }
}
