package alif.com.mainproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.nio.charset.Charset;

@SpringBootApplication
public class MainProjectApplication {

    public static void main(String[] args) {

        // Lotin alifbosidagi matn
        String lotinMatn = "Hello, World!";

// Kirill alifbosiga o'tkazish
        byte[] kiritBytes = lotinMatn.getBytes(Charset.forName("Cp1251"));
        String kirilMatn = new String(kiritBytes, Charset.forName("Cp1251"));

        System.out.println("Kirill: " + kirilMatn);

        SpringApplication.run(MainProjectApplication.class, args);
    }

}
