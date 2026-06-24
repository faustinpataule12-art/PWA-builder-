# NPS.PWA Studio — Compilateur APK (GitHub Actions)

Ce dépôt contient le nécessaire pour transformer une PWA en vrai fichier `.apk`,
en utilisant GitHub Actions comme moteur de compilation (Bubblewrap + Gradle).

## Structure

```
.
├── .github/workflows/build.yml   # Workflow qui compile l'APK (déclenché à distance)
├── scripts/generate-manifest.js  # Génère twa-manifest.json depuis les paramètres reçus
├── NPS-PWA-Studio.html           # Frontend (à ouvrir depuis un navigateur mobile)
└── package.json
```

## Mise en route (à faire une seule fois)

1. **Créer le dépôt** sur GitHub (public recommandé : minutes Actions illimitées).
2. **Pousser ces fichiers** sur la branche `main`.
3. **Vérifier que la branche par défaut est bien `main`** (le workflow `dispatches` cible cette branche dans `NPS-PWA-Studio.html`, fonction `runConversion` — variable `ref:'main'`). Si votre branche par défaut s'appelle autrement, changez cette ligne dans le HTML.
4. **Créer un Personal Access Token** GitHub avec les scopes `repo` et `workflow` (Settings → Developer settings → Personal access tokens).
5. **Ouvrir `NPS-PWA-Studio.html`** dans un navigateur, aller dans la page de conversion, renseigner owner / repo / token dans la carte "🐙 Connexion GitHub", cliquer "Enregistrer".

## Utilisation

1. Renseigner l'URL HTTPS publique de la PWA, le nom, le package ID, les permissions, l'icône/splash (optionnels côté affichage — voir limitation ci-dessous).
2. Cliquer "Générer le package Android".
3. Le site déclenche le workflow GitHub Actions, suit son statut en direct (polling), et affiche le lien du run une fois terminé.
4. Télécharger l'APK depuis la page du run GitHub → section **Artifacts**.

## Limitations connues (honnêtes, pas de surprise)

- **Permissions caméra/micro/stockage** : Bubblewrap génère une TWA (Trusted Web Activity), qui est un wrapper léger autour de Chrome. Les permissions matérielles (caméra, micro, fichiers) ne sont pas automatiquement câblées comme dans une appli native — elles nécessiteraient une couche WebView personnalisée plus poussée que ce que Bubblewrap fournit nativement. Le workflow injecte les déclarations dans les inputs, mais le comportement runtime réel de la caméra/micro dans une TWA reste limité par design Android/Chrome.
- **Signature de l'APK** : le workflow génère une clé de signature "debug" à la volée, pour un usage personnel/test. Pour publier sur le Play Store, il faudra une vraie clé de release gérée séparément (ne jamais committer de vraie clé de signature dans le repo).
- **Temps de build** : 3 à 10 minutes selon la charge des runners GitHub.
- **Quota gratuit** : illimité sur dépôt public ; ~2000 min/mois sur dépôt privé.
