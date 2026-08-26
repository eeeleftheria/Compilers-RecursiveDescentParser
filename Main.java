import java.io.IOException;

class Main {
    public static void main(String[] args) {
        while(true){
            try{
                System.out.println((new Evaluator(System.in)).Eval());
            } 
            catch (IOException | ParseError e) {
                System.err.println(e.getMessage());
            }
        }
    }
}
