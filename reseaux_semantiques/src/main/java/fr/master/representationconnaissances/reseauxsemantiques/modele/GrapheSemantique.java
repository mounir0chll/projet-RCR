package fr.master.representationconnaissances.reseauxsemantiques.modele;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class GrapheSemantique {
    private final Map<String, NoeudSemantique> noeuds;
    private final List<ArcSemantique> arcs;
    private final Map<String, ContexteSemantique> contextes;
    private final AtomicInteger compteurArc;

    public GrapheSemantique() {
        this.noeuds = new LinkedHashMap<>();
        this.arcs = new ArrayList<>();
        this.contextes = new LinkedHashMap<>();
        this.compteurArc = new AtomicInteger(1);
    }

    public NoeudSemantique ajouterNoeud(String id, String etiquette, TypeNoeud type) {
        return noeuds.computeIfAbsent(id, cle -> new NoeudSemantique(id, etiquette, type));
    }

    public NoeudSemantique ajouterNoeud(NoeudSemantique noeud) {
        noeuds.putIfAbsent(noeud.id(), noeud);
        return noeuds.get(noeud.id());
    }

    public ArcSemantique ajouterArc(String source, String cible, TypeArc type) {
        return ajouterArc(source, cible, type, true, 0);
    }

    public ArcSemantique ajouterArc(String source, String cible, TypeArc type, boolean strict, int priorite) {
        ArcSemantique arc = new ArcSemantique("arc-" + compteurArc.getAndIncrement(), source, cible, type, strict, priorite);
        return ajouterArc(arc);
    }

    public ArcSemantique ajouterArc(ArcSemantique arc) {
        if (!noeuds.containsKey(arc.source())) {
            throw new IllegalArgumentException("Noeud source inconnu : " + arc.source());
        }
        if (!noeuds.containsKey(arc.cible())) {
            throw new IllegalArgumentException("Noeud cible inconnu : " + arc.cible());
        }
        arcs.add(arc);
        return arc;
    }

    public ContexteSemantique ajouterContexte(ContexteSemantique contexte) {
        contextes.put(contexte.id(), contexte);
        return contexte;
    }

    public Optional<NoeudSemantique> trouverNoeud(String id) {
        return Optional.ofNullable(noeuds.get(id));
    }

    public Collection<NoeudSemantique> noeuds() {
        return List.copyOf(noeuds.values());
    }

    public List<ArcSemantique> arcs() {
        return List.copyOf(arcs);
    }

    public Collection<ContexteSemantique> contextes() {
        return List.copyOf(contextes.values());
    }

    public List<NoeudSemantique> successeurs(String id) {
        return arcsSortants(id).stream()
                .map(ArcSemantique::cible)
                .map(noeuds::get)
                .toList();
    }

    public List<NoeudSemantique> predecesseurs(String id) {
        return arcsEntrants(id).stream()
                .map(ArcSemantique::source)
                .map(noeuds::get)
                .toList();
    }

    public List<ArcSemantique> arcsSortants(String id) {
        return arcs.stream()
                .filter(arc -> arc.source().equals(id))
                .toList();
    }

    public List<ArcSemantique> arcsEntrants(String id) {
        return arcs.stream()
                .filter(arc -> arc.cible().equals(id))
                .toList();
    }

    public boolean existeArcType(String source, String cible, TypeArc type) {
        return arcs.stream()
                .anyMatch(arc -> arc.source().equals(source) && arc.cible().equals(cible) && arc.type() == type);
    }

    public String etiquette(String id) {
        return trouverNoeud(id).map(NoeudSemantique::etiquette).orElse(id);
    }

    public String decrireEvenement(String idEvenement) {
        String agent = cibleUnique(idEvenement, TypeArc.AGENT).map(this::etiquette).orElse("un agent inconnu");
        String objet = cibleUnique(idEvenement, TypeArc.OBJET).map(this::etiquette).orElse("un objet inconnu");
        String destinataire = cibleUnique(idEvenement, TypeArc.DESTINATAIRE).map(this::etiquette).orElse("un destinataire inconnu");
        String nomEvenement = etiquette(idEvenement).toLowerCase();
        String verbe = nomEvenement.startsWith("donner") ? "donne" : nomEvenement.startsWith("livrer") ? "livre" : "agit sur";
        return etiquette(idEvenement) + " : " + agent + " " + verbe + " " + objet + " à " + destinataire + ".";
    }

    public Optional<String> cibleUnique(String source, TypeArc type) {
        return arcsSortants(source).stream()
                .filter(arc -> arc.type() == type)
                .map(ArcSemantique::cible)
                .findFirst();
    }
}
