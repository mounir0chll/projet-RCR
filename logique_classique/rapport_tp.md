# Rapport TP 1 : Logiques classiques, SAT et inférence

## Objectif

Ce TP montre comment une base de connaissances propositionnelle peut être traduite en CNF, testée avec un solveur SAT, puis utilisée pour simuler une inférence par raisonnement par l'absurde. L'exemple est aussi vérifié avec TweetyProject Propositional Logic.

## Exemple choisi

L'exemple est volontairement différent des exemples du TP et du cours. Il décrit un laboratoire équipé d'un système d'alarme.

- S'il y a de la fumée, l'alarme se déclenche.
- Si l'alarme se déclenche, l'évacuation est obligatoire.
- De la fumée est observée.
- Requête : faut-il évacuer ?

## Formalisation

Variables :

- `1 = fumee`
- `2 = alarme`
- `3 = evacuation`

Base :

- `fumee → alarme`, donc `¬fumee ∨ alarme`
- `alarme → evacuation`, donc `¬alarme ∨ evacuation`
- `fumee`

Pour tester `evacuation`, on ajoute `¬evacuation`. Si la base devient insatisfiable, alors la base initiale infère `evacuation`.
Avec cette correspondance DIMACS, la clause `-1 2 0` représente `¬fumee ∨ alarme`, donc l'implication `fumee → alarme`. De même, `-2 3 0` représente `alarme → evacuation`.

## Fichiers produits

- `cnf/alarme_satisfiable.cnf`
- `cnf/alarme_inference_evacuation.cnf`
- `cnf/contradiction_simple.cnf`

## Toolbox utilisées

- TweetyProject Propositional Logic pour construire une base propositionnelle et interroger la formule `evacuation`.
- UBCSAT pour exploiter directement les fichiers `.cnf`.
- Maven pour compiler et tester automatiquement le module.

UBCSAT sert ici à exécuter les fichiers DIMACS produits pour le TP. La preuve d'insatisfiabilité est confirmée par le raisonneur Java exhaustif et par TweetyProject.

## Commandes UBCSAT

Placer `ubcsat.exe` dans `logique_classique/cnf`, puis exécuter :

```bat
ubcsat.exe -alg saps -i alarme_satisfiable.cnf -solve
ubcsat.exe -alg saps -i alarme_inference_evacuation.cnf -solve
ubcsat.exe -alg saps -i contradiction_simple.cnf -solve
```

## Commande Java

```bash
mvn -pl logique_classique -am exec:java
```

## Résultats

- La base seule est satisfiable.
- La base `{fumee, ¬fumee}` est incohérente.
- La base avec `¬evacuation` est insatisfiable.
- Donc `evacuation` est déduit de la base.
- TweetyProject confirme aussi la déduction de `evacuation` et la contradiction `{fumee, ¬fumee}`.

## Interprétation

La logique classique convient ici parce que les règles sont strictes. Une fois que `fumee` est connue, la chaîne d'implications impose `alarme`, puis `evacuation`.

Analyse finale : l'exemple vérifie une conséquence monotone, car aucune règle d'exception ne peut retirer la conclusion `evacuation`.
