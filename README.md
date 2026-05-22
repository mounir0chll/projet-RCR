# Rep-Connais

Projet Java/Maven pour un travail de Master 1 en représentation des connaissances et raisonnement.

Le dépôt contient cinq TP indépendants. La consigne minimale demande au moins deux TP et un rapport global ; ici, cinq TP sont fournis pour couvrir davantage de familles de logiques et de représentations. Le rapport final est `rapport_rep_connais.pdf` : il présente surtout l'exécution des TP, les bases de connaissances, les requêtes, les sorties console et les résultats vérifiés.
Les exemples utilisés dans les démonstrations sont volontairement différents des exemples des TD/TP et du cours. Ils sont inspirés de petits domaines réels définis pour le projet : alarme de laboratoire, centre de secours, drones de maintenance, ontologie familiale et réseau sémantique de modes de représentation.
Chaque module correspond à une famille logique différente étudiée dans le cours.

## Toolbox exploitées

Le but des TP est de représenter des connaissances dans des domaines définis, puis de les exploiter avec des outils de raisonnement.

- TweetyProject est utilisé dans les modules de logique propositionnelle, logique modale et logique des défauts. La documentation fournie mentionne l'ancien groupId Maven `net.sf.tweety.logics`; le projet utilise le groupId actuel `org.tweetyproject.logics`, qui correspond à la version moderne du même projet.
- UBCSAT est utilisé comme outil externe compatible avec les fichiers CNF du TP 1. La preuve d'insatisfiabilité est validée par le raisonneur Java exhaustif et par TweetyProject.
- OWL API et HermiT sont utilisés pour charger l'ontologie OWL et vérifier l'inférence de logique de description.
- Le module `reseaux_semantiques` utilise une structure Java en mémoire et produit un export Graphviz DOT. Graphviz peut servir à visualiser le fichier `.dot`, mais il n'est pas nécessaire pour compiler le projet.

Des mini-raisonneurs internes restent présents pour garder les démonstrations lisibles au niveau Master 1 et pour fixer explicitement certains modèles, notamment le modèle de Kripke et les priorités d'exceptions.

Chaque résultat est validé de deux manières : par la sortie console de la démonstration et par un test JUnit ciblé sur l'inférence annoncée.

## Objectif du projet

L'objectif est de montrer comment différentes logiques permettent de représenter des connaissances et de produire des conclusions :

- la logique classique pour les vérités strictes et le raisonnement monotone ;
- la logique modale pour les mondes possibles, la possibilité et la nécessité ;
- la logique des défauts pour les règles générales avec exceptions ;
- la logique de description pour les ontologies, la TBox, l'ABox et l'inférence d'appartenance ;
- les réseaux sémantiques pour représenter graphiquement des concepts, individus, actions, contextes, héritages et exceptions.

## Organisation du code Java

Le code est divisé en cinq modules Maven, un module par famille logique ou méthode de représentation. Cette organisation répond directement à la consigne : chaque TP peut être compris, exécuté et testé indépendamment.

### 1. Module `logique_classique`

Rôle du module : représenter une base propositionnelle stricte, tester SAT/CNF, vérifier une inférence par l'absurde et montrer un petit exemple du premier ordre.

| Classe Java | Rôle bref |
|---|---|
| `DemonstrationLogiqueClassique` | Point d'entrée du module ; lance les démonstrations propositionnelle et premier ordre. |
| `DemonstrationLogiquePropositionnelle` | Affiche la base `fumee → alarme → evacuation`, la requête et le résultat. |
| `RaisonneurPropositionnel` | Énumère les valuations pour tester la satisfiabilité et l'inférence par l'absurde. |
| `VerificationTweetyPropositionnelle` | Vérifie l'inférence avec TweetyProject. |
| `DemonstrationLogiquePremierOrdre` | Affiche l'exemple `∀x(TechnicienCertifie(x) → AutoriseIntervention(x))`. |
| `RaisonneurPremierOrdre` | Applique une règle universelle simple sur les faits connus. |
| Tests JUnit | Vérifient que `evacuation` et `AutoriseIntervention(Nora)` sont bien déduits. |

### 2. Module `logique_modale`

Rôle du module : construire un modèle de Kripke et évaluer des formules de possibilité dans le monde initial `s0`.

| Classe Java | Rôle bref |
|---|---|
| `DemonstrationLogiqueModale` | Point d'entrée du module ; construit le modèle et affiche les requêtes. |
| `Monde` | Représente un monde possible. |
| `ModeleKripke` | Stocke les mondes, la relation d'accessibilité et les valuations. |
| `FormuleModale` | Interface commune des formules modales. |
| `Proposition`, `Non`, `Et` | Représentent les formules de base. |
| `Possible`, `Necessaire` | Implémentent les opérateurs modaux `◊` et `□`. |
| `ValidationTweetyModale` | Vérifie la syntaxe des formules avec TweetyProject. |
| Tests JUnit | Vérifient que les quatre requêtes modales donnent les résultats attendus. |

### 3. Module `logique_defauts`

Rôle du module : montrer un raisonnement non monotone avec une règle générale et une exception.

| Classe Java | Rôle bref |
|---|---|
| `DemonstrationLogiqueDefauts` | Point d'entrée du module ; affiche les deux scénarios et les extensions obtenues. |
| `RegleDefaut` | Représente une règle normale de la forme `A : B / B`. |
| `TheorieDefauts` | Contient les faits stricts, règles strictes et règles de défaut. |
| `RaisonneurDefauts` | Calcule une extension en vérifiant la cohérence entre une conclusion et sa négation. |
| `ValidationTweetyDefauts` | Charge une petite théorie de défauts avec TweetyProject. |
| Tests JUnit | Vérifient que le drone est opérationnel dans le cas général puis non opérationnel dans le cas endommagé. |

### 4. Module `logique_description`

Rôle du module : charger une ontologie OWL, exploiter une TBox/ABox et vérifier l'inférence `GrandMere(Mary)`.

| Classe Java | Rôle bref |
|---|---|
| `DemonstrationLogiqueDescription` | Charge `famille.owl`, lance HermiT si disponible et vérifie `GrandMere(Mary)`. |
| `LogiqueDescriptionTest` | Vérifie que l'ontologie est chargeable et que l'inférence attendue reste vraie. |
| `famille.owl` | Contient les classes, individus et axiomes de l'ontologie familiale. |

### 5. Module `reseaux_semantiques`

Rôle du module : représenter un réseau sémantique sous forme de graphe orienté étiqueté, puis exploiter ce graphe par héritage, blocage d'exception, propagation de marqueurs, description d'événements, traduction logique et export DOT.

| Classe Java | Rôle bref |
|---|---|
| `DemonstrationReseauxSemantiques` | Point d'entrée du module ; construit les graphes et affiche toutes les démonstrations. |
| `TypeNoeud`, `TypeArc` | Définissent les catégories de noeuds et de relations. |
| `NoeudSemantique`, `ArcSemantique` | Représentent les éléments du graphe orienté étiqueté. |
| `ContexteSemantique` | Représente une partition ou un contexte local. |
| `GrapheSemantique` | Stocke les noeuds, arcs, contextes et fournit les recherches de voisinage. |
| `RaisonneurHeritage` | Remonte les arcs `INSTANCE_DE` et `EST_UN` pour récupérer concepts, propriétés et parties héritées. |
| `GestionnaireExceptions` | Bloque une propriété héritée lorsqu'une propriété négative stricte locale existe. |
| `PropagateurMarqueurs` | Répond à la question sur l'axiome A7 par propagation de marqueurs. |
| `TraducteurVersLogique` | Produit une traduction textuelle vers la logique du premier ordre. |
| `ExporteurDot` | Génère le fichier `reseau_semantique.dot`. |
| Tests JUnit | Vérifient l'héritage, les exceptions, la propagation de marqueurs et l'export DOT. |

## Pourquoi les chemins Java sont longs

Un chemin comme :

```text
logique_X/src/test/java/fr/master/representationconnaissances/X
```

est normal dans un projet Maven/Java :

- `logique_X` désigne le module Maven du TP concerné ;
- `src/main/java` contient le code exécuté ;
- `src/test/java` contient les tests JUnit ;
- `fr/master/representationconnaissances` correspond au package Java `fr.master.representationconnaissances` ;
- le dernier dossier `X` sépare les sous-packages : `classique`, `modale`, `defauts`, `description`, `reseauxsemantiques`.

Cette structure paraît longue, mais elle suit la convention Maven et Java. Elle permet à Maven de compiler correctement les classes, de trouver les tests et de séparer les cinq parties. Les dossiers `target` sont seulement des fichiers générés automatiquement par Maven.

## TP réalisés

### TP 1 : Logiques classiques et SAT

Module : `logique_classique`

Exemple original : système d'alarme de laboratoire.

- `fumee → alarme`
- `alarme → evacuation`
- `fumee`
- Requête : `evacuation`

Le module contient aussi des fichiers `.cnf` dans `logique_classique/cnf` pour une exécution avec UBCSAT. L'inférence propositionnelle est également vérifiée avec TweetyProject.
Le point clé est que la base seule reste satisfiable, alors que la base augmentée avec `¬evacuation` devient insatisfiable.
La correspondance DIMACS utilisée est `1 = fumee`, `2 = alarme`, `3 = evacuation`.

Note technique interne : `logique_classique/rapport_tp.md`

### TP 3 : Logique modale

Module : `logique_modale`

Exemple original : centre de secours avec deux plans possibles.

Le modèle montre que `◊energieSecours` et `◊reseauRadio` peuvent être vrais séparément, alors que `◊(energieSecours ∧ reseauRadio)` est faux. Les formules modales sont validées avec TweetyProject Modal Logic.
La différence vient du fait que les deux propriétés sont vraies dans deux mondes accessibles différents.
Toutes les requêtes sont évaluées dans le monde initial `s0`, avec une sémantique modale de base de type K.

Note technique interne : `logique_modale/rapport_tp.md`

### TP 4 : Logique des défauts

Module : `logique_defauts`

Exemple original : drones de maintenance.

Un drone est normalement opérationnel. Si le drone est endommagé, l'exception bloque cette conclusion et permet d'obtenir `NonOperationnel(droneAlpha)`. Une théorie de défauts est chargée et validée avec TweetyProject Reiter's Default Logic.
Le raisonneur empêche d'ajouter simultanément une conclusion et sa négation dans la même extension.

Note technique interne : `logique_defauts/rapport_tp.md`

### TP 5 : Logique de description

Module : `logique_description`

Exemple : ontologie familiale OWL avec TBox et ABox.

Le raisonneur HermiT infère que `Mary` est une `GrandMere`.
Cette conclusion n'est pas déclarée directement : elle est obtenue à partir des axiomes de la TBox et des faits de l'ABox.

Note technique interne : `logique_description/rapport_tp.md`

### TP 6 : Réseaux sémantiques

Module : `reseaux_semantiques`

Exemple original : réseau de concepts, individus, actions, contextes, services et protocoles dans un centre hospitalier intelligent.

Le module montre que `MediBot7` hérite des concepts `RobotMedical`, `AppareilMedical` et `RessourceHospitaliere`, ainsi que des propriétés et parties héritables. Il montre aussi que `RobotEnPanne` bloque l'héritage de `DistribuerMedicaments`, car il possède localement une propriété négative stricte.

La propagation de marqueurs répond à la question :

```text
Quels services du centre hospitalier utilisent le protocole TeleconsultationSecuree ?
```

Résultat : `Service de cardiologie` et `Cellule de télémédecine`.

Le module représente aussi deux événements `Livrer-1` et `Livrer-2`, un contexte de croyance, une traduction vers la logique du premier ordre et un export `reseau_semantique.dot`.

Note technique interne : `reseaux_semantiques/README.md`

## Commandes Maven

Tester tout le projet :

```bash
mvn test
```

Compiler tout le projet :

```bash
mvn clean package
```

Lancer le menu interactif sous Windows :

```bat
lancer_tout.bat
```

Lancer le menu interactif sous Linux ou Mac :

```bash
./lancer_tout.sh
```

Le menu propose les choix suivants :

```text
1 - Logique classique
2 - Logique modale
3 - Logique des defauts
4 - Logique de description
5 - Reseaux semantiques
6 - Toutes les logiques
0 - Quitter
```

Après l'exécution d'une logique, le script affiche le résultat puis revient au même menu. Le choix `0` est le seul choix qui ferme le menu.

Les scripts `lancer_tout.bat` et `lancer_tout.sh` servent à faciliter la démonstration. Ils appellent les mêmes commandes Maven que ci-dessous, avec `-pl` pour choisir le module et `-am` pour construire aussi les dépendances nécessaires. Le choix `6` lance les cinq modules à la suite.

Lancer un module précis :

```bash
mvn -pl logique_classique -am exec:java
mvn -pl logique_modale -am exec:java
mvn -pl logique_defauts -am exec:java
mvn -pl logique_description -am exec:java
mvn -pl reseaux_semantiques -am exec:java
```

## Commandes UBCSAT pour le TP 1

Copier `ubcsat.exe` dans `logique_classique/cnf`, puis ouvrir un CMD dans ce dossier :

```bat
ubcsat.exe -alg saps -i alarme_satisfiable.cnf -solve
ubcsat.exe -alg saps -i alarme_inference_evacuation.cnf -solve
ubcsat.exe -alg saps -i contradiction_simple.cnf -solve
```

Ou lancer :

```bat
lancer_ubcsat.bat
```

## Résumé des résultats attendus

- Logique classique : `evacuation` est déduit de la base propositionnelle et `AutoriseIntervention(Nora)` est déduit en premier ordre.
- Logique modale : `◊energieSecours`, `◊reseauRadio` et `◊energieSecours ∧ ◊reseauRadio` sont vrais en `s0`, mais `◊(energieSecours ∧ reseauRadio)` est faux.
- Logique des défauts : `droneAlpha` est opérationnel quand il est seulement connu comme drone de maintenance, puis il devient non opérationnel quand il est connu comme endommagé.
- Logique de description : Mary est reconnue comme `GrandMere`.
- Réseaux sémantiques : `MediBot7` hérite de concepts, propriétés et parties ; `RobotEnPanne` bloque `DistribuerMedicaments` ; la propagation retourne `Service de cardiologie` et `Cellule de télémédecine` ; le fichier DOT est généré.

## Lien avec la consigne du TP

- Le projet est en Java/Maven multi-module.
- Chaque logique possède son propre dossier, son code, son README et sa sortie attendue.
- Les exemples sont originaux et ne reprennent pas directement les exercices de TD.
- Les toolboxes sont exploitées : TweetyProject, UBCSAT, OWL API et HermiT.
- Les tests JUnit vérifient automatiquement les inférences principales.
- Les scripts `lancer_tout` permettent de démontrer rapidement toutes les parties.
- Le rapport global `rapport_rep_connais.pdf` présente l'exécution des TP réalisés, et non une suite de rapports séparés.
- Le module `reseaux_semantiques` est intégré comme cinquième module Maven au même niveau que les quatre modules logiques.

## Démonstration orale en 10 minutes

1. Présenter la structure Maven multi-module.
2. Lancer `mvn test` pour montrer que les résultats importants et les validations de toolbox sont vérifiés automatiquement.
3. Lancer `mvn -pl logique_classique -am exec:java`, puis montrer les fichiers `.cnf` du dossier `logique_classique/cnf`.
4. Lancer `mvn -pl logique_modale -am exec:java` et expliquer la différence entre possibilités séparées et possibilité conjointe.
5. Lancer `mvn -pl logique_defauts -am exec:java` et expliquer la non-monotonie.
6. Lancer `mvn -pl logique_description -am exec:java` et expliquer la TBox, l'ABox et l'inférence `GrandMere(Mary)`.
7. Lancer `mvn -pl reseaux_semantiques -am exec:java` et expliquer le graphe, l'héritage, l'exception, la propagation de marqueurs et l'export DOT.
8. Ouvrir `logique_description/famille.owl` dans Protégé si une démonstration visuelle de l'ontologie est souhaitée.
"# decentralized-app" 
