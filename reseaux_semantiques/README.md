# Réseaux sémantiques

Ce module ajoute les réseaux sémantiques comme cinquième partie du projet Rep-Connais. Il représente les connaissances sous forme d'un graphe orienté étiqueté : les noeuds représentent des concepts, individus, événements, propositions ou contextes, et les arcs représentent des relations typées.

L'exemple utilisé est original et inspiré d'un domaine réel : la gestion d'un centre hospitalier intelligent. Il ne reprend pas les exemples du cours ni ceux des TD.

Le module reste volontairement léger : il n'utilise pas Neo4j, RDF, OWL ou JGraphT comme coeur de représentation. Le graphe est stocké en mémoire avec des collections Java simples.

## Objectif du module

L'objectif est de montrer comment un réseau sémantique permet de représenter et d'exploiter des connaissances graphiques :

- taxonomie et relations hiérarchiques ;
- héritage de propriétés et de parties ;
- exception locale qui bloque un héritage par défaut ;
- propagation de marqueurs ;
- représentation d'actions par réification ;
- contextes et partitions ;
- traduction textuelle vers la logique du premier ordre ;
- export Graphviz DOT.

## Définition utilisée

Un réseau sémantique est un graphe orienté étiqueté. Dans ce module :

- un noeud possède un identifiant, une étiquette, un type et des attributs ;
- un arc possède une source, une cible, un type, un indicateur strict/non strict, une priorité et des attributs ;
- un contexte regroupe des noeuds locaux pour représenter une partition ou une portée de croyance.

## Noeuds et arcs

Les types de noeuds utilisés sont :

- `CONCEPT` ;
- `INDIVIDU` ;
- `EVENEMENT` ;
- `CONTEXTE` ;
- `PROPOSITION` ;
- `VARIABLE`.

Les types d'arcs incluent notamment :

- `EST_UN` pour la hiérarchie conceptuelle ;
- `INSTANCE_DE` pour relier un individu à un concept ;
- `A_POUR_PARTIE` pour représenter les parties héritables ;
- `PROPRIETE` et `PROPRIETE_NEGATIVE` pour les propriétés ;
- `EXCEPTION_A` pour représenter une exception ;
- `AGENT`, `OBJET`, `DESTINATAIRE` pour les rôles d'action ;
- `CONTIENT` pour la propagation de marqueurs ;
- `DANS_CONTEXTE` pour les partitions.

## Héritage

Le raisonneur d'héritage remonte les arcs `INSTANCE_DE` puis `EST_UN`. Dans l'exemple hospitalier :

```text
MediBot7 INSTANCE_DE RobotLivraison
RobotLivraison EST_UN RobotMedical
RobotMedical EST_UN AppareilMedical
AppareilMedical EST_UN RessourceHospitaliere
```

On conclut donc que `MediBot7` est un `RobotMedical`, un `AppareilMedical` et une `RessourceHospitaliere`.

Les propriétés et les parties portées par les ancêtres sont aussi récupérées :

```text
RessourceHospitaliere PROPRIETE Tracable
AppareilMedical A_POUR_PARTIE Batterie
RobotMedical A_POUR_PARTIE Capteurs
RobotLivraison A_POUR_PARTIE CompartimentSterile
RobotLivraison A_POUR_PARTIE Roues
RobotLivraison PROPRIETE DistribuerMedicaments
```

`MediBot7` hérite donc de `Tracable`, `Batterie`, `Capteurs`, `CompartimentSterile`, `Roues` et `DistribuerMedicaments`.

## Blocage d'exception

Le module montre un blocage local d'inférence :

```text
RobotLivraison PROPRIETE DistribuerMedicaments
RobotEnPanne EST_UN RobotLivraison
RobotEnPanne PROPRIETE_NEGATIVE DistribuerMedicaments
RobotEnPanne EXCEPTION_A RobotLivraison
```

La propriété `DistribuerMedicaments` portée par `RobotLivraison` est non stricte. La propriété négative stricte portée localement par `RobotEnPanne` bloque cet héritage. Le module ne calcule pas une logique des défauts complète ; il illustre seulement le blocage d'une propriété héritée dans un graphe sémantique.

## Propagation de marqueurs

La propagation de marqueurs est appliquée à une question originale :

```text
Quels services du centre hospitalier utilisent le protocole TeleconsultationSecuree ?
```

Le raisonnement suit trois étapes :

1. marquer le noeud `OrganisationHopital` ;
2. propager ce marqueur dans le sens inverse des arcs `EST_UN` ;
3. marquer le noeud `TeleconsultationSecuree` ;
4. chercher les noeuds marqués qui possèdent un arc `CONTIENT` vers ce protocole.

Les réponses attendues sont :

```text
Service de cardiologie
Cellule de télémédecine
```

## Actions réifiées

Les actions sont représentées comme des noeuds `EVENEMENT`.

Exemple :

```text
Livrer-1 AGENT MediBot7
Livrer-1 OBJET KitAnalyse
Livrer-1 DESTINATAIRE PatientNora
```

Cette réification évite l'ambiguïté des relations non binaires : l'action `Livrer-1` porte plusieurs rôles, au lieu de forcer la relation à être simplement binaire.

## Contextes et partitions

Le module représente aussi des contextes simples :

```text
Croyance_ChefService
Negation_1
```

L'exemple modélise :

```text
Le chef de service croit que MediBot7 n'est pas disponible.
```

Cette représentation montre visuellement la modalité et la négation, mais elle ne calcule pas la vérité modale. Pour un raisonnement modal formel, il faut utiliser le module `logique_modale`.

## Traduction vers la logique du premier ordre

Le traducteur produit une lecture textuelle simple :

```text
MediBot7 INSTANCE_DE RobotLivraison       -> RobotLivraison(MediBot7)
RobotLivraison EST_UN RobotMedical        -> is_a(RobotLivraison,RobotMedical)
Livrer-1 AGENT MediBot7                   -> Agent(Livrer_1,MediBot7)
```

Cette traduction aide à comparer une représentation graphique avec une représentation logique.

## Export DOT

Le module génère le fichier :

```text
reseaux_semantiques/reseau_semantique.dot
```

Le fichier utilise `digraph` et peut être visualisé avec Graphviz. Graphviz n'est pas nécessaire pour compiler ou tester le projet : le module génère seulement le fichier `.dot`.

## Commandes

Depuis la racine du projet :

```bash
mvn -pl reseaux_semantiques -am exec:java
```

Tests du module :

```bash
mvn -pl reseaux_semantiques test
```

## Limites du module

- pas de raisonnement modal complet ;
- pas de raisonnement non monotone général ;
- pas de base graphe externe ;
- pas d'ontologie OWL interne ;
- les réseaux partitionnés sont représentés mais pas entièrement raisonnés ;
- la propagation de marqueurs est volontairement limitée à la requête démontrée.
