# Logique de description

Ce module modélise une petite ontologie familiale avec une TBox, une ABox et une requête d'appartenance de classe. Il exploite OWL API pour charger le fichier OWL et HermiT pour l'inférence automatique quand le raisonneur est disponible.

## TBox

- `Femme ≡ Personne ⊓ Feminin`
- `Homme ≡ Personne ⊓ ¬Femme`
- `Mere ≡ Femme ⊓ ∃aEnfant.Personne`
- `Pere ≡ Homme ⊓ ∃aEnfant.Personne`
- `Parent ≡ Pere ⊔ Mere`
- `GrandMere ≡ Mere ⊓ ∃aEnfant.Parent`
- `MereSansFille ⊑ Mere`
- `MereSansFille ≡ Mere ⊓ ∀aEnfant.¬Femme`

La classe `Parent` est bien définie par une union : un parent est une mère ou un père. Cette définition permet de conclure que `Pere(Peter)` implique `Parent(Peter)`.

## ABox

- `MereSansFille(Mary)`
- `Pere(Peter)`
- `aEnfant(Mary, Peter)`
- `aEnfant(Mary, Paul)`
- `aEnfant(Peter, Harry)`

## Inférence attendue

La requête est : `Ontologie ⊨ GrandMere(Mary) ?`

La réponse attendue est `vrai`. Cette appartenance n'est pas ajoutée comme fait explicite. Mary est une `Mere`, elle a pour enfant Peter, et Peter est un `Pere`. Comme `Parent ≡ Pere ⊔ Mere`, Peter est un `Parent`. Donc Mary appartient à la classe `GrandMere`.

## Toolbox utilisées

- OWL API pour charger et inspecter `famille.owl`.
- HermiT pour inférer automatiquement l'appartenance `GrandMere(Mary)`.
- Protégé pour une vérification visuelle et manuelle de l'ontologie.

## Commande

```bash
mvn -pl logique_description -am exec:java
```

## Vérification dans Protégé avec HermiT

1. Ouvrir le fichier `logique_description/famille.owl` dans Protégé.
2. Choisir le raisonneur HermiT dans le menu des raisonneurs.
3. Lancer la classification.
4. Sélectionner l'individu `Mary`.
5. Vérifier que `GrandMere` apparaît dans les types inférés.

La classe Java tente d'utiliser HermiT automatiquement. Si HermiT pose un problème de compatibilité dans l'environnement Maven, le programme utilise une vérification de secours avec OWL API : il charge `famille.owl` et vérifie la présence des classes, individus et axiomes nécessaires. Cette solution garde le projet compilable tout en laissant l'ontologie compatible avec Protégé et HermiT.
