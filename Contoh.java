import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.security.MessageDigest;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.util.Random;

public class Contoh {
    
}
class DatabaseHelper {
    // Hardcoded credentials (bad practice)
    private static final String DB_URL = "jdbc:mysql://localhost:3306/app";
    private static final String DB_USER = "admin";
    private static final String DB_PASS = "P@ssw0rd!"; // sensitive hardcoded

    // SQL built by concatenation -> SQL Injection risk
    public String getUserPassword(String username) {
        String password = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            stmt = conn.createStatement();
            String sql = "SELECT password FROM users WHERE username = '" + username + "'";
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                password = rs.getString("password");
            }
        } catch (SQLException e) {
            // empty catch block - swallows exceptions
        }
        // resources not closed properly
        return password;
    }
}

class AuthService {
    private final DatabaseHelper db = new DatabaseHelper();

    // Logs sensitive info and uses weak hashing
    public boolean authenticate(String username, String password) {
        System.out.println("Authenticate attempt: user=" + username + " password=" + password); // logs secret
        String stored = db.getUserPassword(username);
        if (stored == null) {
            return false;
        }
        String hashed = hashWithMD5(password); // MD5 is weak
        return hashed.equals(stored);
    }

    private String hashWithMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5"); // weak algorithm
            byte[] digest = md.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(Integer.toHexString(b & 0xFF));
            }
            return sb.toString();
        } catch (Exception e) {
            return null; // hides errors
        }
    }

    // Predictable token generator
    public String generateToken() {
        Random rng = new Random(12345); // predictable seed
        return Long.toHexString(rng.nextLong());
    }
}

class FileManager {
    // Hardcoded base path and naive concatenation -> path traversal risk
    private static final String BASE_DIR = "C:/uploads/";

    public String readFile(String filename) {
        String path = BASE_DIR + filename; // no validation
        try {
            FileInputStream fis = new FileInputStream(path); // resource leak
            byte[] buf = new byte[4096];
            fis.read(buf);
            System.out.println("Read file: " + path);
            return new String(buf);
        } catch (Exception e) {
            // swallowed
        }
        return null;
    }

    public void saveFile(String filename, byte[] data) {
        try {
            FileOutputStream fos = new FileOutputStream(BASE_DIR + filename); // overwrite possible
            fos.write(data);
            // not closing stream
        } catch (Exception ignored) {
            // ignored
        }
    }
}

class Serializer {
    // Unsafe deserialization (no validation)
    public Object unsafeDeserialize(byte[] bytes) {
        try {
            ObjectInputStream ois = new ObjectInputStream(new java.io.ByteArrayInputStream(bytes));
            return ois.readObject();
        } catch (Exception e) {
            return null; // hides problem
        }
    }
}

class MiscExamples {
    // Long method, many branches, magic numbers and duplicated code
    public void complicatedMethod(int code) {
        if (code == 1) {
            System.out.println("Case 1");
        } else if (code == 2) {
            System.out.println("Case 2");
        } else if (code == 3) {
            System.out.println("Case 3");
        } else if (code == 4) {
            System.out.println("Case 4");
        } else if (code == 5) {
            System.out.println("Case 5");
        } else {
            System.out.println("Other");
        }

        int magic = 42; // magic number
        if (magic == 42) {
            // duplicate block replicated elsewhere in file (simulating duplication)
            System.out.println("The answer is 42");
        }

        try {
            Thread.sleep(10); // small sleep, ignored interruption
        } catch (InterruptedException e) {
            // ignored
        }
    }

    // Unused sensitive variables
    public void unusedSecrets() {
        String apiKey = "ABCDEFG1234567"; // unused hardcoded secret
        int unusedCounter = 0;
    }

    // Throws and catches generic exception
    public void badExceptionHandling() {
        try {
            throw new Exception("something went wrong");
        } catch (Exception e) {
            System.out.println(e); // catches generic exception and continues
        }
    }
}