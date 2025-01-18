import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

// La classe SerialisationReflector est responsable de la sérialisation d'un objet en JSON.
public class SerialisationReflector {
    // Listes pour stocker les références aux champs de types différents
    private List<FieldRef> simpleFields = new ArrayList();
    private List<FieldRef> objectFields = new ArrayList();
    private List<FieldRef> collectionFields = new ArrayList();

    // constructeur
    public SerialisationReflector(Object instance) {
        // Parcours de tous les champs de l'objet à l'aide de Reflection
        for(Field field : instance.getClass().getDeclaredFields()) {
            // Si le champ n'est pas annoté avec @Ignored, on l'inclut dans la sérialisation
            if (!field.isAnnotationPresent(Ignored.class)) {
                if (!field.getType().isPrimitive() && field.getType() != String.class) {
                    if (Collection.class.isAssignableFrom(field.getType())) {
                        this.collectionFields.add(new CollectionFieldRef(field));
                    } else {
                        this.objectFields.add(new ObjectFieldRef(field));
                    }
                } else {
                    this.simpleFields.add(new SimpleFieldRef(field));
                }
            }
        }

    }

    // Méthode de sérialisation qui génère une chaîne JSON à partir de l'objet instance
    public String toJson(Object instance) {
        StringBuilder json = new StringBuilder("{");
        // Sérialisation des champs simples
        for(FieldRef ref : this.simpleFields) {
            json.append(ref.toJson(instance)).append(",");
        }
       // Sérialisation des champs d'objet imbriquées
        for(FieldRef ref : this.objectFields) {
            json.append(ref.toJson(instance)).append(",");
        }
        // Sérialisation des champs de collections
        for(FieldRef ref : this.collectionFields) {
            json.append(ref.toJson(instance)).append(",");
        }
        // Suppression de la dernière virgule si nécessaire
        if (json.length() > 1) {
            json.deleteCharAt(json.length() - 1);
        }
        // Fermeture de l'objet JSON
        json.append("}");
        return json.toString();
    }
}
