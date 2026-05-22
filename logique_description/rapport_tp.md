# Rapport TP 5 : Logique de description

## Objectif

Ce TP montre l'utilisation d'une ontologie OWL avec une TBox, une ABox et une inférence d'appartenance de classe. Les toolbox utilisées sont OWL API, HermiT et Protégé.

## Ontologie choisie

L'ontologie représente une petite famille. Elle n'est pas extraite des énoncés de TD fournis.

## TBox

- `Femme ≡ Personne ⊓ Feminin`
- `Homme ≡ Personne ⊓ ¬Femme`
- `Mere ≡ Femme ⊓ ∃aEnfant.Personne`
- `Pere ≡ Homme ⊓ ∃aEnfant.Personne`
- `Parent ≡ Pere ⊔ Mere`
- `GrandMere ≡ Mere ⊓ ∃aEnfant.Parent`
- `MereSansFille ⊑ Mere`
- `MereSansFille ≡ Mere ⊓ ∀aEnfant.¬Femme`

La définition de `Parent` utilise l'union `⊔`. Elle exprime qu'un père ou une mère est un parent, ce qui correspond à l'interprétation utilisée pour inférer `GrandMere(Mary)`.

## ABox

- `MereSansFille(Mary)`
- `Pere(Peter)`
- `aEnfant(Mary, Peter)`
- `aEnfant(Mary, Paul)`
- `aEnfant(Peter, Harry)`

## Requête

`Ontologie ⊨ GrandMere(Mary) ?`

## Résultat

Le raisonneur HermiT infère que Mary est une `GrandMere`.

## Toolbox utilisées

OWL API charge le fichier `famille.owl`. HermiT vérifie l'inférence `GrandMere(Mary)` automatiquement dans la classe Java. Protégé peut ouvrir la copie `logique_description/famille.owl` pour visualiser la TBox, l'ABox et les types inférés.

## Commande Java

```bash
mvn -pl logique_description -am exec:java
```

## Vérification dans Protégé

1. Ouvrir `logique_description/famille.owl`.
2. Choisir HermiT comme raisonneur.
3. Lancer la classification.
4. Sélectionner l'individu `Mary`.
5. Vérifier que `GrandMere` apparaît dans les types inférés.

Analyse finale : la conclusion `GrandMere(Mary)` illustre une inférence de type, obtenue à partir de la TBox et de l'ABox plutôt que déclarée directement.
