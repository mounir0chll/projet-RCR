# Logique classique

Ce module correspond au TP sur les logiques classiques. Il contient une partie SAT/CNF compatible avec UBCSAT, une vérification avec TweetyProject Propositional Logic et une partie Java qui rend explicite l'inférence logique.

## Exemple original utilisé

Le domaine choisi est un système d'alarme de laboratoire, afin de ne pas reprendre les exemples du TP ou du cours.

- S'il y a de la fumée, alors l'alarme se déclenche.
- Si l'alarme se déclenche, alors l'évacuation est obligatoire.
- Il y a de la fumée.

La requête est : `evacuation`.

## Traduction propositionnelle

- `fumee → alarme`
- `alarme → evacuation`
- `fumee`

La base est satisfiable. Pour tester l'inférence, on ajoute `¬evacuation`. La base devient alors insatisfiable, donc `evacuation` est déduit par raisonnement par l'absurde.
Cette vérification distingue bien deux choses : la cohérence de la base initiale et la validité de la requête. Une base peut être satisfiable tout en imposant une conclusion précise.

## Toolbox utilisées

- TweetyProject Propositional Logic, dépendance Maven `org.tweetyproject.logics:pl`, pour représenter la base propositionnelle et vérifier l'inférence.
- UBCSAT, outil externe installé localement, pour tester les fichiers CNF.
- Un mini-raisonneur par énumération de valuations est conservé pour expliquer clairement le raisonnement par l'absurde.

## Fichiers CNF pour UBCSAT

Les fichiers sont dans `logique_classique/cnf`.

Correspondance DIMACS :

- `1 = fumee`
- `2 = alarme`
- `3 = evacuation`

- `alarme_satisfiable.cnf` : base de connaissances seule.
- `alarme_inference_evacuation.cnf` : base avec `¬evacuation`.
- `contradiction_simple.cnf` : base incohérente `{fumee, ¬fumee}`.

Les fichiers sont compatibles avec UBCSAT. L'insatisfiabilité utilisée pour la preuve par l'absurde est validée dans le projet par l'énumération exhaustive du raisonneur Java et par TweetyProject.

Pour utiliser UBCSAT, copier `ubcsat.exe` dans `logique_classique/cnf`, ouvrir un CMD dans ce dossier, puis lancer :

```bat
ubcsat.exe -alg saps -i alarme_satisfiable.cnf -solve
ubcsat.exe -alg saps -i alarme_inference_evacuation.cnf -solve
ubcsat.exe -alg saps -i contradiction_simple.cnf -solve
```

Le script `lancer_ubcsat.bat` contient ces commandes.

## Partie premier ordre

L'exemple original choisi est l'accès à une salle technique.

- `TechnicienCertifie(Nora)`
- `∀x(TechnicienCertifie(x) → AutoriseIntervention(x))`
- Requête : `AutoriseIntervention(Nora)`

La conclusion attendue est `vrai`.
La règle universelle est simplement instanciée avec `Nora`, puis appliquée au fait déjà connu.

## Commande Java

```bash
mvn -pl logique_classique -am exec:java
```
