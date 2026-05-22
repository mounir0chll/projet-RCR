package fr.master.representationconnaissances.reseauxsemantiques.export;

import fr.master.representationconnaissances.reseauxsemantiques.modele.ArcSemantique;
import fr.master.representationconnaissances.reseauxsemantiques.modele.ContexteSemantique;
import fr.master.representationconnaissances.reseauxsemantiques.modele.GrapheSemantique;
import fr.master.representationconnaissances.reseauxsemantiques.modele.NoeudSemantique;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class ExporteurDot {
    public String genererDot(GrapheSemantique graphe) {
        StringBuilder builder = new StringBuilder();
        builder.append("digraph ReseauSemantique {\n");
        builder.append("  rankdir=LR;\n");
        builder.append("  node [shape=box, style=\"rounded,filled\", fillcolor=\"#F8FAFC\", color=\"#64748B\"];\n");
        for (NoeudSemantique noeud : graphe.noeuds()) {
            builder.append("  ").append(quote(noeud.id()))
                    .append(" [label=").append(quote(noeud.etiquette()))
                    .append("];\n");
        }
        for (ContexteSemantique contexte : graphe.contextes()) {
            builder.append("  subgraph ").append("cluster_").append(nettoyer(contexte.id())).append(" {\n");
            builder.append("    label=").append(quote(contexte.etiquette())).append(";\n");
            builder.append("    color=\"#CBD5E1\";\n");
            for (String noeudId : contexte.noeudsLocaux()) {
                builder.append("    ").append(quote(noeudId)).append(";\n");
            }
            builder.append("  }\n");
        }
        for (ArcSemantique arc : graphe.arcs()) {
            builder.append("  ").append(quote(arc.source()))
                    .append(" -> ").append(quote(arc.cible()))
                    .append(" [label=").append(quote(arc.type().name()))
                    .append("];\n");
        }
        builder.append("}\n");
        return builder.toString();
    }

    public void exporter(GrapheSemantique graphe, Path chemin) throws IOException {
        Files.writeString(chemin, genererDot(graphe), StandardCharsets.UTF_8);
    }

    private String quote(String valeur) {
        return "\"" + valeur.replace("\\", "\\\\").replace("\"", "\\\"") + "\"";
    }

    private String nettoyer(String valeur) {
        return valeur.replaceAll("[^A-Za-z0-9_]", "_");
    }
}
