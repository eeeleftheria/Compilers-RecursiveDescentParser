import java.io.InputStream;
import java.io.IOException;


class Evaluator{
    private final InputStream in;
    private int lookahead;

    public Evaluator(InputStream in) throws IOException{
        this.in = in;

        // reads next char from input and returns its int value
        lookahead = in.read();
    }

    private void consume(int symbol) throws IOException, ParseError{
        if(lookahead == symbol){
            lookahead = in.read();
        }
        else
            throw new ParseError();
    }

    public String Eval() throws IOException, ParseError{
        String value = Exp();

        if(lookahead != -1 && lookahead != '\n'){
            
            System.out.println("#3");
            throw new ParseError();
        }

        return value;
    }

    // evaluates s1**s2
    private String EvalDoubleStar(String s1, String s2) throws IOException, ParseError{

        String res = s1 + s2 + s2;

        return res;
    }

    // evaluates s1/s2
    private String EvalExcl(String s1, String s2) throws IOException, ParseError{

        // if it is indeed a suffix of s1, remove it
        if(s1.endsWith(s2)){
            // we want to keep only the initial string without the suffix
            String res = s1.substring(0, s1.length() - s2.length());
            return res;
        }

        return s1;
    }



    private String Exp() throws IOException, ParseError{
       
        // match exp -> term expTail 

        // follow rule #1
        if(lookahead >= 'a' && lookahead <= 'z' ||
            lookahead >= 'A' && lookahead <= 'Z'  ||
            lookahead == '('
        ){

            String t = Term();
            String e = ExpTail();

            // if the expTail is not empty we have /exp case
            if(!e.isEmpty()){
                return EvalExcl(t, e);
            }
            else {
                return t;
            }
          
        }

        // parse error for any other input
        System.out.println("#10000");
        throw new ParseError();
    }

    private String ExpTail() throws IOException, ParseError{
        // match expTail -> / exp
        
        if(lookahead == '/'){
            // '/' is met in actual rule so consume it
            consume(lookahead);
            
            String s = Exp();
            return s;
        }

        // match expTail -> empty
        else if(lookahead == ')' || lookahead == -1 || lookahead == '\n'){
            return "";
        }

        System.out.println("#2");
        throw new ParseError();
    }

    private String Term() throws IOException, ParseError{
        //match term -> factor termTail

        if(lookahead >= 'a' && lookahead <= 'z' ||
            lookahead >= 'A' && lookahead <= 'Z' ||
            lookahead == '('){
            
                String f = Factor();
                String t = TermTail(f);

                return t;
        }
        
        System.out.println("#4");
        throw new ParseError();
    }

    private String TermTail(String left) throws IOException, ParseError{
        // match termTail-> ** factor termTail
        
        // follow rule #5
        if(lookahead == '*'){
            consume(lookahead);
            
            if(lookahead == '*'){
                consume(lookahead);

                String f = Factor();
                // since ** is left associative we should calculate
                // it before going on with the deeper operations
                String newLeft = EvalDoubleStar(left, f);

                // the result of the ** is given as input for the 
                // next recursion, since in case we meet another **
                // we should have: newLeft**X
                String t = TermTail(newLeft);

                return t;
            }

            // expected input is '*'
            else{
                System.out.println("#5");
                throw new ParseError();
            }
        }

        // match termTail -> empty
        // follow rule #6
        else if(lookahead == '/' || lookahead == ')' || lookahead == -1 || lookahead == '\n'){
            
            // if there is no ** the string remains as it is
            return left;
        }

        System.out.println("#6");
        throw new ParseError();
    }

    private String Factor() throws IOException, ParseError{
        
        // match factor -> str
        if((lookahead >= 'A' && lookahead <= 'Z') ||
        (lookahead >= 'a' && lookahead <= 'z')){
            
            return Str();
        }
        
        // match factor -> (exp)
        else if(lookahead == '('){
            consume(lookahead);

            String s = Exp();

            if(lookahead == ')'){
                consume(lookahead);

                return s;
            }

            // expected ')'
            else{
                System.out.println("#7");
                throw new ParseError();
            }

        }

        System.out.println("#8");
        throw new ParseError();
    }

    private String Str() throws IOException, ParseError{
      
        // match str -> char strTail
        if((lookahead >= 'a' && lookahead <= 'z') ||
            (lookahead >= 'A' && lookahead <= 'Z')){
            
            return Char() + StrTail();
        }

        System.out.println("#9");
        throw new ParseError();
    }

    private String StrTail() throws IOException, ParseError{
       
        // match strTail-> str
        if(lookahead >= 'a' && lookahead <= 'z' ||
            (lookahead >= 'A' && lookahead <= 'Z')){           

            return Str();
        }

        // follow rule #11: empty
        else if(lookahead == '*'){

            if(lookahead == '*'){

                return "";
            }
            else{
            
                System.out.println("#10");
                throw new ParseError();
            }
        }

        // follow rule #11: empty
        else if(lookahead == '/' || lookahead == ')' || lookahead == -1 || lookahead == '\n'){
            
            return "";
        }        

        System.out.println("#11");
        throw new ParseError();
    }

    private char Char() throws IOException, ParseError{
        // match char -> a-z
        // match char -> A-Z 

        if((lookahead >= 'a' && lookahead <= 'z') || (lookahead >= 'A' && lookahead <= 'Z')){       
            int old = lookahead;    
            consume(lookahead);
            return (char)old;
        }

        System.out.println("#12");
        throw new ParseError();
    }

}