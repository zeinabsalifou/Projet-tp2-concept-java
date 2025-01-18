import java.lang.reflect.Field;

//Classe pour gérer la sérialisation et désérialisation des champs de type simple.
public class SimpleFieldRef extends FieldRef {
    //Constructeur
     public SimpleFieldRef(Field field) {
         super(field);
     }

    // Méthode de sérialisation : elle transforme la valeur du champ simple en une chaîne JSON
     @Override
     protected String toJson(Object instance) {
         Object value = this.getValue(instance);
         return "\"" + this.getName() + "\":\"" + String.valueOf(value) + "\"";
     }

    // Méthode de désérialisation : elle extrait la valeur du JSON et l'assigne au champ de l'objet

     @Override
     public void fromJson(String json, Object instance) {
         // Ici, tu dois extraire la valeur du JSON pour ce champ simple
         try {
             // Crée la chaîne de recherche du champ dans le JSON
             String fieldName = "\"" + this.getName() + "\":\"";
             // Recherche les indices de début et de fin de la valeur du champ dans le JSON
             int startIndex = json.indexOf(fieldName) + fieldName.length();
             int endIndex = json.indexOf("\"", startIndex);

             // Si les indices sont trouvés, on extrait la valeur
             if (startIndex > -1 && endIndex > -1) {
                 String value = json.substring(startIndex, endIndex);
                 //Convertit la valeur extraite dans le type approprié et l'assigne au champ
                 Object parsedValue = convertToType(value, this.getField().getType());
                 this.setValue(instance, parsedValue);
             } else {

                 throw new JsonProcessingException(" champs manquant ou mal formés: " + this.getName(), null);
             }
         } catch (Exception e) {
             // Si une erreur survient lors de la désérialisation, tu lances l'exception personnalisée
             throw new JsonProcessingException("Error deserializing field: " + this.getName(), e);
         }
     }

     // Méthode pour convertir la chaîne JSON en un type spécifique
     private Object convertToType(String value, Class<?> type) {
         if (type == Integer.class || type == Integer.TYPE) {
             return Integer.parseInt(value);
         } else if (type == Double.class || type == Double.TYPE) {
             return Double.parseDouble(value);
         } else if (type == Boolean.class || type == Boolean.TYPE) {
             return Boolean.parseBoolean(value);
         } else {
             return value; // Si c'est un String ou un autre type, on renvoie la valeur telle quelle
         }
     }
 }