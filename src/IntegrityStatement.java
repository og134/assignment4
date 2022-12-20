
public final class IntegrityStatement {
    public static String signature() {
        String signature = "Omer Gonen (318170842)"; // <- Fill in your name and ID here! For example: "Israel Israeli (123456789)"
        
        if (signature.length() == 0) {
            throw new UnsupportedOperationException("You didn't sign the integrity statement.");
        }
        
        return signature;
    }
}
