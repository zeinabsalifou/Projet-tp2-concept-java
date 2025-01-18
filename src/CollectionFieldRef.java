import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;

public class CollectionFieldRef extends FieldRef {
    // Constructeur qui appelle le constructeur de la classe parente FieldRef
    public CollectionFieldRef(Field field) {
        super(field);
    }

    // Méthode pour sérialiser une collection en JSON
    @Override
    protected String toJson(Object instance) {
        // Récupération de la valeur de la collection à partir de l'instance
        Collection<?> collection = (Collection<?>) this.getValue(instance);
        StringBuilder json = new StringBuilder("\"" + this.getName() + "\":[");

        // Vérification si la collection n'est pas vide
        if (collection != null) {
            for (Object item : collection) {
                json.append("\"").append(item).append("\",");
            }

            // Retirer la dernière virgule si la collection n'est pas vide
            if (!collection.isEmpty()) {
                json.deleteCharAt(json.length() - 1);
            }
        }
        // Fermeture du tableau JSON et retour de la chaîne JSON
        json.append("]");
        return json.toString();
    }

    // Méthode pour désérialiser une collection à partir d'un JSON
    @Override
    public void fromJson(String json, Object instance) {
        String fieldName = "\"" + this.getName() + "\":[";
        int startIndex = json.indexOf(fieldName) + fieldName.length();
        if (startIndex >= fieldName.length()) {
            int endIndex = json.indexOf("]", startIndex);
            String arrayContent = json.substring(startIndex, endIndex);
            String[] items = arrayContent.split(",");

            try {
                // Créer la collection vide pour contenir les éléments
                Collection<Object> collection = new ArrayList<>();

                // Traitement des éléments de la collection
                for (String item : items) {
                    // Enlever les guillemets autour des éléments
                    String cleanedItem = item.replace("\"", "").trim();
                    // Convertir l'élément dans le type approprié
                    collection.add(cleanedItem); // Ici, tu pourrais faire plus de traitement selon le type des éléments
                }

                // Mettre à jour la valeur de la collection dans l'instance
                this.setValue(instance, collection);
            } catch (Exception e) {
                // Lancer une exception personnalisée si un problème se produit
                throw new JsonProcessingException("Error deserializing collection field: " + this.getName(), e);
            }
        }
    }
}
