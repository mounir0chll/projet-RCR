package fr.master.representationconnaissances.modale;

import java.util.Objects;

public final class Monde {

    private final String nom;

    public Monde(String nom) {
        this.nom = Objects.requireNonNull(nom);
    }

    public String nom() {
        return nom;
    }

    @Override
    public boolean equals(Object objet) {
        if (this == objet) {
            return true;
        }
        if (!(objet instanceof Monde monde)) {
            return false;
        }
        return nom.equals(monde.nom);
    }

    @Override
    public int hashCode() {
        return nom.hashCode();
    }

    @Override
    public String toString() {
        return nom;
    }
}
