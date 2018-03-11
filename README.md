# appLogiqueFloue

Après la découverte théorique et le survol des domaines d'application, j'ai décidé d'appliquer un système de logique floue à un cas concret qui pourrait être extrapolé dans un de ces domaines d'application : j'ai opté pour l'implémentation d'un système de calcul du coefficient de freinage d'un robot motorisée, de type MBot. Implémentation d'actualité dans le domaine des voitures "intelligentes".

Grâce au projet présenté dans l'ouvrage de Virginie Mathivet, cf L'Intelligence Artificielle pour les développeurs, dont j'ai préservé la partie d'algorithmie spécifique à la logique floue!, j'ai modifié les règles et valeurs pour passer d'un système appliqué à un store "autonome" se modulant selon la température intérieure et l'ensoleillement, à un système calculant le coefficient de freinage d'un engin motorisé, ici un robot de quelques centimètres, dont les variables étaient la vitesse et la distance à l'obstacle.

Sur ce dépôt se trouvent les packages du dossier src/ :
. application : la partie JavaFX
. pojos : la classe Résultat
. service : l'interface et l'implémentation pour l'utilisations des résultats
. utils : classes issues de l'ouvrage L'Intelligence Artificielle pour les développeurs
