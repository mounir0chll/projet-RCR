package fr.master.representationconnaissances.modale;

public interface FormuleModale {

    boolean estVraie(ModeleKripke modele, Monde monde);

    String afficher();
}
