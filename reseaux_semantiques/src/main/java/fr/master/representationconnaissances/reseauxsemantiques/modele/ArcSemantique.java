package fr.master.representationconnaissances.reseauxsemantiques.modele;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class ArcSemantique {
    private final String id;
    private final String source;
    private final String cible;
    private final TypeArc type;
    private final boolean strict;
    private final int priorite;
    private final Map<String, String> attributs;

    public ArcSemantique(String id, String source, String cible, TypeArc type, boolean strict, int priorite) {
        this.id = Objects.requireNonNull(id);
        this.source = Objects.requireNonNull(source);
        this.cible = Objects.requireNonNull(cible);
        this.type = Objects.requireNonNull(type);
        this.strict = strict;
        this.priorite = priorite;
        this.attributs = new LinkedHashMap<>();
    }

    public String id() {
        return id;
    }

    public String source() {
        return source;
    }

    public String cible() {
        return cible;
    }

    public TypeArc type() {
        return type;
    }

    public boolean strict() {
        return strict;
    }

    public int priorite() {
        return priorite;
    }

    public Map<String, String> attributs() {
        return Map.copyOf(attributs);
    }

    public ArcSemantique ajouterAttribut(String cle, String valeur) {
        attributs.put(cle, valeur);
        return this;
    }

    @Override
    public String toString() {
        return source + " -" + type + "-> " + cible;
    }
}
