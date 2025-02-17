package lab24.ankit.group01.a2.Security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PassEncrypt {

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public static String hashPassword(String password) {
        return encoder.encode(password);
    }

    public static boolean checkPassword(String rawPassword, String hashedPassword) {
        return encoder.matches(rawPassword, hashedPassword);
    }
}
