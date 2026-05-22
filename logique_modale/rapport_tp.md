# Rapport TP 3 : Logique modale

## Objectif

Ce TP montre comment représenter des connaissances de possibilité avec un modèle de Kripke. TweetyProject Modal Logic est utilisé comme toolbox pour analyser les formules modales manipulées dans l'exemple.

## Exemple choisi

L'exemple choisi n'est pas repris du TD. Il décrit un centre de secours avec deux plans alternatifs.

- `planA` assure l'énergie de secours.
- `planB` assure le réseau radio.
- Depuis l'état initial `s0`, les deux plans sont accessibles.
- Les deux propriétés ne sont pas vraies dans un même monde accessible directement.

Toutes les requêtes sont évaluées dans le monde initial `s0`. La relation d'accessibilité n'est pas supposée réflexive, transitive ou symétrique ; on utilise donc une sémantique modale minimale de type K.

## Formalisation

Modèle `M = <W, R, V>` :

- `W = {s0, planA, planB, controle}`
- `R = {(s0, planA), (s0, planB), (planA, controle), (planB, controle)}`
- `V(energieSecours) = {planA}`
- `V(reseauRadio) = {planB}`

## Toolbox utilisée

Le module utilise la dépendance Maven `org.tweetyproject.logics:ml`. Les formules `<>(EnergieSecours)`, `<>(ReseauRadio)`, `<>(EnergieSecours) && <>(ReseauRadio)` et `<>(EnergieSecours && ReseauRadio)` sont analysées par le parseur modal de TweetyProject. L'évaluation finale est faite sur un modèle de Kripke interne pour garder le modèle fini entièrement contrôlé et affichable.

## Requêtes évaluées dans s0

- `◊energieSecours = vrai`
- `◊reseauRadio = vrai`
- `◊energieSecours ∧ ◊reseauRadio = vrai`
- `◊(energieSecours ∧ reseauRadio) = faux`

## Commande

```bash
mvn -pl logique_modale -am exec:java
```

## Interprétation

Le modèle montre une distinction importante de la logique modale : deux choses peuvent être possibles séparément sans être possibles ensemble.

Analyse finale : le résultat dépend du monde d'évaluation `s0` et de la relation d'accessibilité choisie.
