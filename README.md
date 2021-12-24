spring-boot:run :



Etape 13 :

1) Avec quelle partie du code avons-nous paramétré l’url d’appel /greeting ?
	---> @RequestParam(name="nameGET", required=false, defaultValue="World")
	
2) Avec quelle partie du code avons-nous choisi le fichier HTML à afficher ?
	---> @GetMapping("/greeting")
	
3) Comment envoyons-nous le nom à qui nous disons bonjour avec le second lien ?
	---> Dans l'URL, on saisi la donnée à envoyer pour le paramètre nameGET comme ceci :
		 http://localhost:9090/greeting?nameGET=ENSIM
   



Etape 17 :

Relancez-votre application, retournez sur la console de H2 : http://localhost:8080/
h2-console. Avez-vous remarqu ́e une différence ? Ajoutez la réponse dans le README
	
	---> La table ADRESS a été ajoutée avec les colonnes id, content et creation ainsi qu'une séquence Hibernate
URL = jdbc:h2:mem:testdb

Etape 18 :
	---> L'ORM fait la correspondance entre l'objet et la base de donnée, crée les requêtes SQL, crée les base de données avec les annotations...

Etape 20 :

ID | Content 							  | Creation
---+--------------------------------------+---------------------------
1  | 57 boulevard demorieux 			  | 2021-11-02 18:08:32.988342
---+--------------------------------------+---------------------------
2  | 51 allee du gamay, 34080 montpellier | 2021-11-02 18:08:33.007209



Etape 23 :



Etape 30 :

1) Ajouter les dépendance de bootstrap et webjar dans pom.xml (Version-Agnostic Dependencies)
2) Ajouter le script et le css de bootstrap dans les pages html