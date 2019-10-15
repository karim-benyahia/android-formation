# Création d'une application Android
Le but de cet exercice est de lister un ensemble de données, récupèré depuis un service exposé gratuitement, vous pourrez choisir votre service à cette adresse : [https://github.com/public-apis/public-apis](https://github.com/public-apis/public-apis).

Pour ce faire, nous allons créer une activité, et l'enrichir dynamiquement des données reçu du service choisi.

## Création du projet
Créez un nouveau projet.
Vous choisirez une activité vide (Empty Activity).
Vous nommerez ce projet comme vous le souhaitez, et choisirez le langage Java.

## Ma première "Activity"

Définition d'une activité [ici](https://developer.android.com/reference/android/app/Activity)

Deux fichiers utils à l'Activity ont été créés.
1. MainActivity.java dans les sources Java

> Celui ci reprèsente le Controler. C'est dans ce module que vous implémenterez les règles de cycle de vie de votre composant.   
1. activity_main.xml dans le dossier layout
>Ce fichier correspond à la View. c'est un fichier xml dans lequel vous renseignerez les éléments gaphiques qui composeront votre page/écran/composant. 


Commençons par éditer ce fichier, vous trouverez [ici](https://developer.android.com/reference/android/widget/package-summary?hl=fr), une liste exhaustif des widgets existants qui vous permettrons de créer votre première interface.

Amusez vous avec différents composants afin de découvrir ce qu'Android vos offre nativement.


Retourner ensuite dans le fichier MainActivity.java

```java

    private TextView mTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ...
        
        this.mTextView = findViewById(R.id.mon_text_view);
        this.mTextView.setText("le texte que je veux afficher")
        
        ...
    }
```

### Appel de l'API choisi

Pour ce faire nous utiliserons une librairie nommée [volley](https://developer.android.com/training/volley),
je vous invite à lire la doc afin de faire votre première requête.
Vous pourrez ensuite utilisé les données reçu afin de les visualizer sur votre application Android.


### le RecyclerView

Vous avez récupérer une liste de données il vous faudrait donc les afficher sous forme de liste.
Ce composant vous le permet.
Je vous invite donc à faire une recherche afin de maitriser au mieux ce composant et afficher vos informations sous forme de liste scrollable.

Indice : regardez aussi du coté des CardView
