package fr.master.representationconnaissances.modale;

public class Necessaire implements FormuleModale {

    private final FormuleModale formule;

    public Necessaire(FormuleModale formule) {
        this.formule = formule;
    }

    @Override
    public boolean estVraie(ModeleKripke modele, Monde monde) {
        return modele.necessaire(formule, monde);
    }

    @Override
    public String afficher() {
        return "□" + formule.afficher();
    }
}
