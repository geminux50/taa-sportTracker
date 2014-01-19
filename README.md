taa-sportTracker
================

Infos sur le projet :
- Intégration de log4j
- Génération d'un token par utilisateur pour authentifier les requetes
- Coté Tomcat, utilisation d'un RequestFilter pour valider le Header d'authentification (token)à chaque requêtes
- Coté Angular, utilisation d'un interceptor pour ajouter le Header d'authentification (token)à toutes les requêtes
- La configuration de la persistance écrase la bdd à chaque run du projet
- Utilisation du sessionStorage à la place des cookies pour le stockage de données locales coté client.

Structure :
- Le dossier src/ contient la parite java (Model + JPA + Jersey)
- Le dosser yo/ contient la partie Yeoman/Angularjs

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
- Dans apps.js (Angularjs) passer la variable 'globalDebug' à 'true' pour activer le debug des requêtes coté client web
