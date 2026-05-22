# Logique des défauts

Ce module correspond au TP sur la logique des défauts. Il montre un raisonnement non monotone, c'est-à-dire un raisonnement où une conclusion par défaut peut être bloquée par une information plus spécifique. TweetyProject Reiter's Default Logic est utilisé pour charger et valider une théorie de défauts.

## Exemple original utilisé

Le domaine choisi est la supervision de drones de maintenance.

Scénario 1 :

- `DroneMaintenance(droneAlpha)`
- En général, un drone de maintenance est opérationnel.
- Conclusion : `Operationnel(droneAlpha)`.
- Extension : `{DroneMaintenance(droneAlpha), Operationnel(droneAlpha)}`.

Scénario 2 :

- `DroneEndommage(droneAlpha)`
- Tout drone endommagé est un drone de maintenance.
- En général, un drone de maintenance est opérationnel.
- En général, un drone endommagé n'est pas opérationnel.
- Conclusion : `NonOperationnel(droneAlpha)`, et le défaut général est bloqué.
- Extension : `{DroneEndommage(droneAlpha), DroneMaintenance(droneAlpha), NonOperationnel(droneAlpha)}`.

## Forme utilisée

Le mini-raisonneur utilise des défauts normaux :

```text
A : B / B
```

Il vérifie la cohérence entre une conclusion et sa négation avant de l'ajouter à l'extension.
Dans le scénario endommagé, l'exception `NonOperationnel(droneAlpha)` est ajoutée avant la conclusion générale contraire. Le défaut général est donc bloqué par incohérence.

## Toolbox utilisée

La dépendance Maven `org.tweetyproject.logics:rdl` est utilisée pour analyser une théorie de défauts de Reiter et vérifier que la représentation syntaxique est valide. Le mini-raisonneur interne conserve une priorité explicite entre défaut général et exception, ce qui rend la démonstration plus directe pour l'oral.

## Commande

```bash
mvn -pl logique_defauts -am exec:java
```

## Interprétation

La logique des défauts est adaptée aux règles générales avec exceptions. Elle évite de rendre la base incohérente lorsqu'un cas particulier contredit une règle générale.
