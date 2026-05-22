package fr.master.representationconnaissances.reseauxsemantiques.modele;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class NoeudSemantique {
    private final String id;
    private final String etiquette;
    private final TypeNoeud type;
    private final Map<String, String> attributs;

    public NoeudSemantique(String id, String etiquette, TypeNoeud type) {
        this.id = Objects.requireNonNull(id);
        this.etiquette = Objects.requireNonNull(etiquette);
        this.type = Objects.requireNonNull(type);
        this.attributs = new LinkedHashMap<>();
    }

    public String id() {
        return id;
    }

    public String etiquette() {
        return etiquette;
    }

    public TypeNoeud type() {
        return type;
    }

    public Map<String, String> attributs() {
        return Map.copyOf(attributs);
    }

    public NoeudSemantique ajouterAttribut(String cle, String valeur) {
        attributs.put(cle, valeur);
        return this;
    }

    @Override
    public boolean equals(Object objet) {
        if (this == objet) {
            return true;
        }
        if (!(objet instanceof NoeudSemantique autre)) {
            return false;
        }
        return id.equals(autre.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return etiquette;
    }
}
