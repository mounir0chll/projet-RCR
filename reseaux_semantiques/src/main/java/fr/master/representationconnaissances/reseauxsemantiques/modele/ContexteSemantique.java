package fr.master.representationconnaissances.reseauxsemantiques.modele;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

public class ContexteSemantique {
    private final String id;
    private final String etiquette;
    private final String parentId;
    private final Set<String> noeudsLocaux;

    public ContexteSemantique(String id, String etiquette) {
        this(id, etiquette, null);
    }

    public ContexteSemantique(String id, String etiquette, String parentId) {
        this.id = id;
        this.etiquette = etiquette;
        this.parentId = parentId;
        this.noeudsLocaux = new LinkedHashSet<>();
    }

    public String id() {
        return id;
    }

    public String etiquette() {
        return etiquette;
    }

    public Optional<String> parentId() {
        return Optional.ofNullable(parentId);
    }

    public Set<String> noeudsLocaux() {
        return Set.copyOf(noeudsLocaux);
    }

    public ContexteSemantique ajouterNoeudLocal(String noeudId) {
        noeudsLocaux.add(noeudId);
        return this;
    }
}
