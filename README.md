# TP2 - Tests Unitaires avec Mockups

Ce projet contient les exercices du TP2 sur les tests unitaires avec mockups, en utilisant JUnit 5 et Mockito.

## Exercice 4 - Réponses aux questions

### Question 1
Les objets qui doivent forcément être mockés dans le test de la méthode `jouer` sont :
- **Joueur** : Car ses méthodes `mise()`, `debiter()` et `crediter()` dépendent d'un état externe (solde du joueur) et peuvent avoir des comportements variés.
- **De** (les deux dés) : Car ils sont basés sur le hasard, donc nous devons contrôler leur comportement pour tester des scénarios précis.
- **Banque** : Car elle interagit avec le système externe (la banque), et son état (solvabilité) dépend d'interactions précédentes.

Ces mockups permettent de contrôler précisément le comportement de ces objets dans nos tests sans dépendre de leur implémentation réelle ni de l'aléatoire des dés.

### Question 2
Scénarios à tester pour la méthode `jouer` :

1. **Jeu fermé** : Vérifier que la méthode lève une exception `JeuFermeException` quand le jeu est fermé.
2. **Joueur insolvable** : Vérifier que le jeu s'arrête sans lancer les dés si le joueur ne peut pas payer sa mise.
3. **Joueur perd** : Vérifier le comportement quand la somme des dés n'est pas 7, le joueur perd sa mise et le jeu s'arrête là.
4. **Joueur gagne, banque solvable** : Vérifier que le joueur reçoit deux fois sa mise et que le jeu reste ouvert.
5. **Joueur gagne, banque insolvable** : Vérifier que le joueur reçoit deux fois sa mise mais que le jeu ferme.

### Question 4
Le test du cas où le jeu est fermé est un test d'état car il vérifie que le jeu a bien le statut "fermé" et qu'il lance l'exception attendue. On vérifie l'état du jeu après avoir appelé la méthode `fermer()`.

### Question 5
Pour tester que le jeu ne touche pas aux dés lorsque le joueur est insolvable, on utilise la vérification d'interaction avec `verify(de1Mock, never()).lancer()` et `verify(de2Mock, never()).lancer()`.

C'est un test d'interaction car on vérifie les interactions entre les objets (que les méthodes des dés n'ont pas été appelées) plutôt que l'état final du système.
