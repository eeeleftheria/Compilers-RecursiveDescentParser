import java_cup.runtime.*;
import java.io.*;

class Main {
    public static void main(String[] argv) throws Exception{
        Scanner s = new Scanner(new InputStreamReader(System.in));
        Parser p = new Parser(s);
        p.parse();
    }
}
