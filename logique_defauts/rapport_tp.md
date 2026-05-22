# Rapport TP 4 : Logique des défauts

## Objectif

Ce TP montre comment représenter des règles générales avec exceptions grâce à la logique des défauts. Une théorie de défauts est chargée avec TweetyProject Reiter's Default Logic, puis l'exemple pédagogique est exploité avec un mini-raisonneur limité aux défauts normaux.

## Exemple choisi

L'exemple est original et ne reprend pas les exemples du TD. Il concerne des drones de maintenance.

## Théorie 1 : drone normal

Base stricte :

- `DroneMaintenance(droneAlpha)`

Défaut :

- `DroneMaintenance(x) : Operationnel(x) / Operationnel(x)`

Extension obtenue :

- `DroneMaintenance(droneAlpha)`
- `Operationnel(droneAlpha)`

## Toolbox utilisée

Le module utilise la dépendance Maven `org.tweetyproject.logics:rdl`. TweetyProject sert à charger et valider une théorie de défauts au format Reiter. Le raisonneur interne sert ensuite à afficher clairement le blocage par exception entre `Operationnel(droneAlpha)` et `NonOperationnel(droneAlpha)`.

## Théorie 2 : drone endommagé

Base stricte :

- `DroneEndommage(droneAlpha)`
- `DroneEndommage(x) → DroneMaintenance(x)`

Défauts :

- `DroneMaintenance(x) : Operationnel(x) / Operationnel(x)`
- `DroneEndommage(x) : NonOperationnel(x) / NonOperationnel(x)`

Extension obtenue :

- `DroneEndommage(droneAlpha)`
- `DroneMaintenance(droneAlpha)`
- `NonOperationnel(droneAlpha)`

Le défaut général `Operationnel(droneAlpha)` est bloqué par l'exception.
Le blocage vient du contrôle de cohérence : ajouter `Operationnel(droneAlpha)` rendrait l'extension incompatible avec `NonOperationnel(droneAlpha)`.

## Commande

```bash
mvn -pl logique_defauts -am exec:java
```

## Interprétation

Ce TP illustre la non-monotonie : lorsque l'information `DroneEndommage(droneAlpha)` est ajoutée, la conclusion par défaut `Operationnel(droneAlpha)` disparaît.

Analyse finale : le résultat montre que la cohérence de l'extension contrôle l'application des règles générales.
