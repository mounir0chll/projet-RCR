package fr.master.representationconnaissances.modale;

public class Proposition implements FormuleModale {

    private final String nom;

    public Proposition(String nom) {
        this.nom = nom;
    }

    @Override
    public boolean estVraie(ModeleKripke modele, Monde monde) {
        return modele.estVraie(nom, monde);
    }

    @Override
    public String afficher() {
        return nom;
    }
}
