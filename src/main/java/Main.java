import backend.lectortxt.LectorTxt;
import frontend.Biblioteca;

import java.io.IOException;

public class Main {

    public static void main (String [ ] args) {
//        Biblioteca b = new Biblioteca();
        LectorTxt lt = new LectorTxt();
        try {
            lt.leer("/home/alejandnro/Desktop/test.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
