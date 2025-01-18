import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
// Cette annotation permet d'associer un alias à un champ ou à une méthode.
@Retention(RetentionPolicy.RUNTIME)
public @interface Alias {
    String value() default "";
}