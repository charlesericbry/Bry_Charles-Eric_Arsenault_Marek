# IFT1025-TP2-server

Même si les commits ne viennent que d'une personne, nous avons codé en équipe sur code with me de intellij. Marek a eu un problème avec ses licenses et n'a donc pas pu contribuer lui-même dans le projet. Il a dû travailler et m'envoyer ce qu'il a fait. 
-Charles-Éric Bry

Malheureusement, nous n'avons pas réussi à faire en sorte que le code soit exécutable par n'importe quel utilisateur qui n'a pas javaFx, même si le projet est construit à partir de maven et que nous avons suivis toutes les étapes de construction, à savoir créer un fichier jar avec des modules et dépendances et ajouter les librairies. Nous avons téléchargé la librairie, l'avons rajoutée manuellement, changé les VM options, tout essayé pendant des jours en cherchant sur Youtube une explication pour nos problèmes, mais ni youtube ni le forum Piazza n'ont pu régler le problème. Selon les instructions du TP, il faut des jars éxécutables avec la commande java -jar, qui est présente lorsque vous allez spécifier le path dans la commande ci-dessous. 

java --module-path /path/to/JavaFX/lib --add-modules
 javafx.controls,javafx.fxml -jar client_fx.jar 

