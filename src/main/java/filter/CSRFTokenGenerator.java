package filter;
import java.util.UUID;

public class CSRFTokenGenerator {
    public static String generateCSRFToken() {
        return UUID.randomUUID().toString();
    }
}