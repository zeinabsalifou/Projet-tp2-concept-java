import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DeSerialisationReflector {
    // Listes pour stocker les références des différents types de champs (simples, objets, collections)
    private List<FieldRef> simpleFields = new ArrayList();
    private List<FieldRef> objectFields = new ArrayList();
    private List<FieldRef> collectionFields = new ArrayList();
    // Constructeur qui initialise les listes de champs à partir d'une instance donnée
    public DeSerialisationReflector(Object instance) {
        Field[] fields = instance.getClass().getDeclaredFields();

        // Parcours de chaque champ pour déterminer son type et l'ajouter à la bonne liste
        for(Field field : fields) {
            // ici on rend le champs est accessible meme si il est privée
            field.setAccessible(true);
            // Si le champ est de type Collection, on le traite comme une collection
            if (Collection.class.isAssignableFrom(field.getType())) {
                this.collectionFields.add(new CollectionFieldRef(field));
            }
            //Si le champ est un objet non-primordial (non String et non type primitif)
            else if (!field.getType().isPrimitive() && !field.getType().equals(String.class)) {
                this.objectFields.add(new ObjectFieldRef(field));
            }
            // si non il est simple
            else {
                this.simpleFields.add(new SimpleFieldRef(field));
            }
        }

    }

    // Méthode pour désérialiser un JSON dans une instance d'objet
    public Object fromJson(String json, Object instance) {
        for(FieldRef ref : this.simpleFields) {
            ref.fromJson(json, instance);
        }

        for(FieldRef ref : this.objectFields) {
            ref.fromJson(json, instance);
        }

        for(FieldRef ref : this.collectionFields) {
            ref.fromJson(json, instance);
        }

        return instance;
    }

    // Méthode pour sérialiser une instance d'objet en JSON
    public String toJson(Object instance) {
        StringBuilder json = new StringBuilder("{");

        // Sérialisation des champs simples
        for(FieldRef ref : this.simpleFields) {
            json.append(ref.toJson(instance)).append(",");
        }

        // Sérialisation des objets imbriqués
        for(FieldRef ref : this.objectFields) {
            json.append(ref.toJson(instance)).append(",");
        }

        // Sérialisation des collections
        for(FieldRef ref : this.collectionFields) {
            json.append(ref.toJson(instance)).append(",");
        }

        // Si la chaîne JSON n'est pas vide (c'est-à-dire qu'on a ajouté des éléments), supprimer la dernière virgule
        if (json.length() > 1) {
            json.deleteCharAt(json.length() - 1);
        }
        // Fermer le JSON et retourner la chaîne résultante
        json.append("}");
        return json.toString();
    }
}