# NewsApp - Application Android de News

Application Android développée en Kotlin avec Jetpack Compose.

## Architecture

### MVVM (Model-View-ViewModel)

L'application suit l'architecture **MVVM** (Model-View-ViewModel), qui est devenue le standard pour le développement Android moderne.

#### Pourquoi MVVM ?

1. **Séparation des responsabilités** : La logique métier est séparée de l'interface utilisateur, facilitant la maintenance et les tests.

2. **Cycle de vie Android** : Les ViewModels survivent aux changements de configuration (rotation d'écran), préservant l'état de l'application sans nécessiter de sauvegarde manuelle.

3. **Testabilité** : La logique métier dans les ViewModels peut être testée indépendamment de l'UI, ce qui simplifie grandement l'écriture de tests unitaires.

4. **Jetpack Compose** : MVVM s'intègre naturellement avec Compose grâce à `viewModel()` et les state holders, permettant une réactivité automatique de l'UI.

#### Structure de l'architecture

```
L’UI (Compose) affiche les données

Le ViewModel gère l’état et la logique

Le Repository s’occupe de récupérer les données

L’API fournit les actualités

```

## Choix techniques

### Jetpack Compose

**Choix** : Jetpack Compose au lieu de XML/View System traditionnel.

**Raisons** :
- **Déclaratif** : Le code UI est plus lisible et maintenable avec une approche déclarative.
- **Moins de code** : Réduction significative du code nécessaire par rapport à XML + Kotlin.
- **Performance** : Recomposition intelligente qui ne met à jour que les composants nécessaires.
- **Modernité** : C'est l'avenir du développement Android UI, Google pousse fortement cette technologie.
- **Intégration** : S'intègre parfaitement avec les autres composants Jetpack (ViewModel, Navigation, etc.).

**Alternative considérée** : XML avec ViewBinding
- **Pourquoi non choisi** : Plus verbeux, nécessite plus de fichiers, moins moderne.

### Retrofit + OkHttp

**Choix** : Retrofit pour les appels réseau.

**Raisons** :
- **Simplicité** : Interface déclarative qui réduit le code boilerplate.
- **Type-safe** : Génération automatique des appels API à partir d'interfaces, réduisant les erreurs.
- **Intégration** : Support natif pour Gson, Coroutines, etc.
- **Mature** : Bibliothèque très stable et largement utilisée dans l'écosystème Android.

**Alternative considérée** : Volley
- **Volley** : Plus ancien, moins maintenu, API moins moderne.

### Coroutines

**Choix** : Kotlin Coroutines pour la gestion asynchrone.

**Raisons** :
- **Natif Kotlin** : Solution officielle pour l'asynchrone en Kotlin.
- **Lisibilité** : Code séquentiel qui se lit comme du code synchrone, évitant le "callback hell".
- **Intégration** : Support natif dans Retrofit, ViewModel, Compose.
- **Performance** : Légères et efficaces, meilleures que les threads Java traditionnels.

**Alternative considérée** : RxJava
- **Pourquoi non choisi** : Plus complexe, moins intégré avec les composants modernes Android.

### Coil

**Choix** : Coil pour le chargement d'images.

**Raisons** :
- **Compose** : Support natif pour Jetpack Compose avec `AsyncImage`.
- **Performance** : Optimisé pour Android avec mise en cache intelligente.
- **Léger** : Plus léger que Glide ou Picasso.
- **Moderne** : Écrit en Kotlin avec Coroutines, s'intègre parfaitement avec l'écosystème moderne.

**Alternative considérée** : Glide, Picasso
- **Glide** : Plus lourd, nécessite plus de configuration pour Compose.
- **Picasso** : Moins maintenu, pas d'intégration native Compose.

### Navigation Compose

**Choix** : Navigation Compose pour la navigation.

**Raisons** :
- **Type-safe** : Navigation type-safe avec les arguments.
- **Intégration Compose** : Conçu spécifiquement pour Compose.
- **Simplicité** : API simple et déclarative.
- **État** : Gestion automatique de l'état de navigation.

**Alternative considérée** : Navigation Component XML
- **Pourquoi non choisi** : Nécessite des fragments/activités, moins adapté à une application 100% Compose.

### Material Design 3

**Choix** : Material Design 3 pour le design.

**Raisons** :
- **Moderne** : Dernière version du design system Google.
- **Compose** : Support natif dans Compose Material3.
- **Cohérence** : Assure une cohérence visuelle avec les autres applications Android modernes.
- **Accessibilité** : Meilleures pratiques d'accessibilité intégrées.

### Gson

**Choix** : Gson pour la sérialisation/désérialisation JSON.

**Raisons** :
- **Simplicité** : API simple et intuitive.
- **Intégration Retrofit** : Support natif via `GsonConverterFactory`.
- **Mature** : Bibliothèque stable et largement utilisée.

**Alternative considérée** : Kotlinx Serialization, Moshi
- **Kotlinx Serialization** : Plus moderne mais nécessite plus de configuration, moins de support dans Retrofit.
- **Moshi** : Plus performant mais API plus complexe, moins nécessaire pour ce projet.


## Gestion de la locale

L'application détecte automatiquement la langue du téléphone via `LocaleHelper` et utilise le code pays correspondant pour récupérer les actualités dans la langue appropriée via l'API NewsAPI.

**Implémentation** :
- Utilise `Locale.getDefault()` pour obtenir la locale système.
- Mappe le code pays vers les codes supportés par NewsAPI.
- Fallback sur "us" si le pays n'est pas supporté.
- NB: Pour "fr", l'api n'offre pas de résultats

## Tests

### Tests unitaires

- **NewsViewModelTest** : Teste la logique métier du ViewModel (chargement, erreurs, retry).
- **NewsRepositoryTest** : Teste la couche repository (appels API, gestion d'erreurs).
- **LocaleHelperTest** : Teste la détection de locale.
- **NewsViewModelFactoryTest** : Teste la création du ViewModel.

### Tests d'intégration

- **NewsListScreenTest** : Tests UI pour l'écran de liste.
- **ArticleTest** : Tests de sérialisation Parcelable.

**Outils utilisés** :
- JUnit 4
- Mockito-Kotlin
- Coroutines Test
- Compose UI Test

## Problèmes identifiés mais non traités

### 1. Gestion du cache

**Problème** : Les actualités sont toujours récupérées depuis l'API, même si elles ont été récemment chargées.

**Solution non implémentée** : 
- Implémenter un cache local.
- Stocker les articles avec timestamp.
- Afficher le cache en premier, puis rafraîchir en arrière-plan.

**Impact** : Consommation réseau inutile, pas d'offline support.

### 2. Pagination

**Problème** : Toutes les actualités sont chargées en une seule fois.

**Solution non implémentée** :
- Implémenter la pagination avec `Paging 3`.
- Charger les articles par pages de x.
- Scroll infini pour charger plus.

**Impact** : Performance dégradée avec beaucoup d'articles, consommation mémoire élevée.

### 3. Recherche et filtres

**Problème** : Pas de possibilité de rechercher ou filtrer les actualités.

**Solution non implémentée** :
- Barre de recherche pour filtrer par titre/mot-clé.
- Filtres par catégorie, source, date.
- Sauvegarde des préférences utilisateur.

### 4. Partage d'articles

**Problème** : Pas de fonctionnalité de partage.

**Solution non implémentée** :
- Intent de partage Android natif.
- Partage vers réseaux sociaux, email, etc.

### 5. Notifications

**Problème** : Pas de notifications pour les nouvelles actualités.

**Solution non implémentée** :
- WorkManager pour vérifier périodiquement les nouvelles actualités.
- Notifications push pour les breaking news.
- Préférences utilisateur pour activer/désactiver.

### 6. Internationalisation complète

**Problème** : L'application n'est pas entièrement traduite, seuls les articles sont dans la langue du téléphone.

**Solution non implémentée** :
- Fichiers de ressources strings.xml pour chaque langue.
- Traduction de tous les textes de l'UI.
- Support RTL pour les langues arabes/hébreu.

## Améliorations futures possibles

1. **Architecture modulaire** : Séparer en modules (core, data, ui) pour une meilleure scalabilité.

2. **Dependency Injection** : Implémenter Hilt/Dagger pour une meilleure gestion des dépendances.

3. **State Management avancé** : Utiliser StateFlow/SharedFlow pour un state management plus robuste.

4. **Analytics** : Intégrer Firebase Analytics pour suivre l'usage.

5. **Crash Reporting** : Intégrer Firebase Crashlytics pour le suivi des erreurs.

6. **CI/CD** : Mise en place d'un pipeline CI/CD avec GitHub Actions ou GitLab CI.

7. **Accessibilité** : Améliorer le support de l'accessibilité (TalkBack, etc.).

8. **Performance** : Optimisation des images, lazy loading, préfetching.

## Configuration

### Clé API

La clé API NewsAPI est configurée dans `build.gradle.kts` via `buildConfigField`. Pour la production, il faudrait :
- Utiliser des variables d'environnement.
- Stocker la clé de manière sécurisée (Android Keystore).
- Ne pas commiter la clé dans le repository.

### SDK Version

- **compileSdk** : 34
- **targetSdk** : 34
- **minSdk** : 24 (Android 7.0)

## Dépendances principales

```kotlin
// UI
androidx.compose:compose-bom:2024.02.00
androidx.navigation:navigation-compose:2.7.6

// Architecture
androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0

// Network
com.squareup.retrofit2:retrofit:2.9.0
com.squareup.okhttp3:okhttp:4.12.0

// Images
io.coil-kt:coil-compose:2.5.0

// Coroutines
org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3
```
