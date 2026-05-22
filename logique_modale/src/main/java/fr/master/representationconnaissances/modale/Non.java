package fr.master.representationconnaissances.modale;

public class Non implements FormuleModale {

    private final FormuleModale formule;

    public Non(FormuleModale formule) {
        this.formule = formule;
    }

    @Override
    public boolean estVraie(ModeleKripke modele, Monde monde) {
        return !formule.estVraie(modele, monde);
    }

    @Override
    public String afficher() {
        return "¬" + formule.afficher();
    }
}
