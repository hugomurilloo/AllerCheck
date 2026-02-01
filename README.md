# AllerCheck - Documentació de l'Activitat 07

**NOM:** Hugo Murillo

## 1. Descripció General del Projecte

AllerCheck és una aplicació mòbil, dissenyada per ajudar usuaris amb restriccions alimentàries a trobar restaurants segurs. 

## 2. Anàlisi segons la Rúbrica de l'Activitat 07

### **2.1. Pantalla Inicial-Logotip**

S'ha implementat la **Splash Screen API** (`androidx.core:core-splashscreen:1.0.1`), que és l'enfocament modern i recomanat per Google i tu :).

-   **Personalització del Tema:** S'ha creat un tema específic `Theme.AllerCheck.SplashScreen` que defineix:
    -   `windowSplashScreenBackground`: El color de fons de la pantalla.
    -   `windowSplashScreenAnimatedIcon`: L'element central de la pantalla, que en aquest cas és el fitxer `@drawable/baseline_check_24`. Aquesta icona es mostra amb un efecte d'animació en iniciar l'app.
    -   `postSplashScreenTheme`: El tema que s'aplicarà un cop finalitzi la Splash Screen, assegurant una transició neta cap a `activity_onboarding`.
-   **Activitat de Llançament:** El `AndroidManifest.xml` estableix `activity_onboarding` com l'activitat de llançament (`LAUNCHER`), aplicant-li el tema personalitzat de la Splash Screen.

**El resultat és una pantalla de càrrega professional i alineada amb la marca de l'aplicació, complint tots els requisits.**

### **2.2. Menú/s de Navegació**

L'aplicació implementa un sistema de navegació principal mitjançant un `BottomNavigationView` personalitzat, una de les opcions més estàndard i reconeixibles en el disseny d'apps modernes.

-   **Menú Personalitzat:** El menú es defineix a `res/menu/bottom_nav_menu.xml`, incloent les tres seccions principals de l'app: Inici (`navigation_home`), Preferits (`navigation_favorites`) i Perfil (`navigation_profile`).
-   **Implementació:** Aquest menú està integrat a les activitats clau (`activity_principal`, `activity_favorite`, etc.) i la seva lògica de navegació es gestiona amb un `OnItemSelectedListener`, que llança l'`Intent` corresponent per canviar d'activitat.
-   **Disseny Visual:** El `BottomNavigationView` té un estil personalitzat, amb un fons de color verd corporatiu (`@color/verde`) (que realment es balu perque vaig veure fa poc el comentari de l'anterior activitat) i icones blanques, mantenint la coherència visual.

S'ha optat per una arquitectura multi-activitat en lloc de fragments.

### **2.3. Llistes Dinàmiques**

#### **Part Visual**

L'aplicació utilitza `RecyclerView` de manera extensiva, demostrant el domini d'aquesta eina essencial.

-   **Dues Llistes Dinàmiques:**
    1.  **Llista Principal de Restaurants** (`activity_principal.xml`): Mostra tots els restaurants disponibles (o els filtrats).
    2.  **Llista de Preferits** (`activity_favorite.xml`): Mostra només els restaurants que l'usuari ha marcat com a favorits.
-   **Dissenys d'Ítem Personalitzats:** Cada `RecyclerView` utilitza un layout d'ítem propi per adaptar-se al context:
    -   `item_restaurant.xml`: Disseny per a la llista general.
    -   `item_favorite.xml`: Disseny similar però amb controls addicionals per eliminar de la llista de preferits.
-   **Comportament en la Selecció:** Tots els elements de les llistes són seleccionables. En fer clic, es navega a la pantalla `activity_detail`, passant les dades del restaurant seleccionat per mostrar la seva informació completa.

#### **Part Programació**

S'ha implementat un sistema de filtratge multi-criteri que constitueix una de les funcionalitats centrals de l'aplicació.

-   **Estructura del Filtre:**
    -   A `activity_config_restrictions`, l'usuari pot seleccionar fins a 8 criteris de filtre diferents (Gluten, Lactosa, Vegà, etc.) mitjançant `CheckBoxes`. Com vas dir al comentari de l'anterior activitat. 
    -   En prémer "Guardar", l'estat de cada `CheckBox` es desa a `SharedPreferences`, garantint la persistència de les preferències de l'usuari entre sessions.
-   **Lògica d'Aplicació del Filtre:**
    -   A `activity_principal`, abans de crear l'adaptador, es llegeixen tots els valors booleans des de `SharedPreferences`.
    -   S'utilitza la funció `filter` de Kotlin sobre la llista completa de restaurants. Dins del lambda del filtre, es comprova que un restaurant compleixi **tots** els criteris actius (operador `&&`).
    -   El `RecyclerView` es construeix finalment només amb la llista ja filtrada.
-   **Classes `Adapter` i `ViewHolder`:** S'han creat `RestaurantAdapter` i `FavoriteRestaurantAdapter`, ambdós seguint el patró estàndard. El `ViewHolder` s'encarrega d'inflar la vista i fer el "binding" de les dades (nom, puntuació, etc.) als `TextViews` corresponents. Els esdeveniments de clic es gestionen mitjançant funcions lambda passades al constructor de l'adaptador, una pràctica neta i moderna.


-   **Estructura Lògica:** La informació està organitzada seguint la mateixa estructura de la rúbrica de l'activitat, facilitant la correcció i l'avaluació.
-   **Justificació Tècnica:** Es descriuen les decisions clau de disseny (p. ex., `BottomNavigationView`, ús d'`SharedPreferences`, lògica de filtre amb `&&`).
-   **Ús d'Assistència per IA:** Per poder desenvolupar i provar les funcionalitats de llistat i filtrat de manera eficient, **es va generar una llista de dades de restaurants de mostra amb l'assistència d'una Intel·ligència Artificial**. Crear manualment una quantitat tan gran i variada de dades (amb 16 propietats per restaurant) hauria estat un procés extremadament lent i repetitiu. Aquestes dades de mostra estan integrades directament al codi font com a llistes (`listOf(Restaurant(...))`) i són la base sobre la qual opera la lògica de l'aplicació.

En resum, la documentació no només descriu "què" s'ha fet, sinó també "com" i "per què", demostrant una comprensió completa dels conceptes treballats durant l'activitat.
