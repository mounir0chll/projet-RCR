# Logique modale

Ce module correspond au TP sur la logique modale. Il utilise TweetyProject Modal Logic pour valider les formules modales et un modèle de Kripke interne, sans interface graphique, pour représenter explicitement les mondes possibles et la relation d'accessibilité du domaine.

## Exemple original utilisé

Le domaine choisi est un centre de secours qui hésite entre deux plans.

- Dans `planA`, l'énergie de secours est disponible.
- Dans `planB`, le réseau radio est disponible.
- Depuis l'état initial `s0`, les deux plans sont possibles.
- Aucun plan directement accessible depuis `s0` ne garantit les deux propriétés en même temps.

Les requêtes sont évaluées dans le monde initial `s0`. Le modèle correspond à une sémantique modale de base de type K : aucune propriété supplémentaire de la relation d'accessibilité n'est imposée.

## Modèle

- `W = {s0, planA, planB, controle}`
- `s0 → planA`
- `s0 → planB`
- `planA → controle`
- `planB → controle`
- `energieSecours` est vrai dans `planA`
- `reseauRadio` est vrai dans `planB`

## Requêtes

Les requêtes sont évaluées dans `s0`.

- `◊energieSecours`
- `◊reseauRadio`
- `◊energieSecours ∧ ◊reseauRadio`
- `◊(energieSecours ∧ reseauRadio)`

Les trois premières formules sont vraies car elles demandent seulement l'existence de mondes accessibles appropriés. La dernière est fausse car elle demande un même monde accessible où les deux propositions sont vraies simultanément.

## Toolbox utilisée

La dépendance Maven `org.tweetyproject.logics:ml` est utilisée pour analyser les formules modales. Le modèle de Kripke interne est conservé parce que le TP doit afficher précisément les mondes `s0`, `planA`, `planB`, `controle`, la relation `R` et la valuation `V`.

## Commande

```bash
mvn -pl logique_modale -am exec:java
```

## Interprétation

La possibilité séparée de deux propriétés ne garantit pas leur possibilité conjointe. Il existe un plan où l'énergie de secours est disponible, et un autre plan où le réseau radio est disponible, mais aucun plan accessible directement ne réunit les deux.
