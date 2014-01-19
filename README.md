taa-sportTracker
================

Infos sur le projet :
- Intégration de log4j
- Génération d'un token par utilisateur pour authentifier les requetes
- La configuration de la persisitance est écrase la bdd à chaque run du projet

Structure :
- Le dosser yo/ contient la partie angularjs

Prérequis :
- Eclipse JEE with maven
- Un serveur HSQLDB

Procédure :
- Récupérer le projet dans eclipse en tant que projet maven
- Démarrer HSQLDB Server
- lancer le projet avec les Goals maven suivants : 'clean compile jetty:run'
- Lancer un navigateur sur 'http://localhost:8080'
- Créer 2 comptes utilisateurs
- Se connecter avec le 1er compte
- Ajouter le second compte comme ami du premier
- Se déconnecter
- Effectuer l'opération inverse
- Essayer de supprimer les comptes utilisateurs

Tips :
- Dans apps.js (Angularjs) passer la variable 'gloabDebug' à 'true' pour activer le debug des requêtes coté client web
