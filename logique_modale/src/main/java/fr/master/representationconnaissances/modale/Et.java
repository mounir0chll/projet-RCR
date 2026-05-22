package fr.master.representationconnaissances.modale;

public class Et implements FormuleModale {

    private final FormuleModale gauche;
    private final FormuleModale droite;

    public Et(FormuleModale gauche, FormuleModale droite) {
        this.gauche = gauche;
        this.droite = droite;
    }

    @Override
    public boolean estVraie(ModeleKripke modele, Monde monde) {
        return gauche.estVraie(modele, monde) && droite.estVraie(modele, monde);
    }

    @Override
    public String afficher() {
        return "(" + gauche.afficher() + " ∧ " + droite.afficher() + ")";
    }
}
