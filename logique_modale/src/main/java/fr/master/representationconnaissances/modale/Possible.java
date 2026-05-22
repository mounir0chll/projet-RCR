package fr.master.representationconnaissances.modale;

public class Possible implements FormuleModale {

    private final FormuleModale formule;

    public Possible(FormuleModale formule) {
        this.formule = formule;
    }

    @Override
    public boolean estVraie(ModeleKripke modele, Monde monde) {
        return modele.possible(formule, monde);
    }

    @Override
    public String afficher() {
        return "◊" + formule.afficher();
    }
}
