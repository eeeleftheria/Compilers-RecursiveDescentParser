import java.io.InputStream;
import java.io.IOException;


class Evaluator{
    private final InputStream in;
    private int lookahead;

    public Evaluator(InputStream in) throws IOException{
        this.in = in;
        lookahead = in.read();
    }

    private void consume(int symbol) throws IOException, ParseError{
        if(lookahead == symbol)
            lookahead = in.read();
        else
            throw new ParseError();
    }

    public boolean Eval() throws IOException, ParseError{
        boolean value = Exp();

        if(lookahead != -1 && lookahead != '\n'){
            throw new ParseError();
        }

        return value;
    }

    private boolean Exp() throws IOException, ParseError{
        // match exp -> term expTail 

        if(Term() && ExpTail()){
            return true;
        }
        return false;
    }

    private boolean ExpTail() throws IOException, ParseError{
        // match expTail -> / exp
        if(lookahead == '/'){
            consume(lookahead);
            if(Exp()){
                return true;
            } 
        }

        // expTail -> empty
        return true;
    }

    private boolean Term() throws IOException, ParseError{
        //match term -> factor termTail
        if(Factor() && TermTail()){
            return true;
        }
        return false;
    }

    private boolean TermTail() throws IOException, ParseError{
        // match termTail-> ** factor termTail
        if(lookahead == '*'){
            consume(lookahead);
            if(lookahead == '*'){
                consume(lookahead);

                if(Factor() && TermTail()){
                    return true;
                }
                return false;
            }
        }

        // termTail -> empty
        return true;
    }

    private boolean Factor() throws IOException, ParseError{
        // match factor -> str
        // match factor -> (exp)

        if(lookahead == '('){
            consume(lookahead);

            if(Exp()){

                if(lookahead == ')'){
                    consume(lookahead);
                    return true;
                }

                // expected ')'
                else{
                    throw new ParseError();
                }

            }
            return false;
        }
        else if(Str()){
            return true;
        }

        return false;
    }

    private boolean Str() throws IOException, ParseError{
        // match str -> char strTail

        if(Char() && StrTail()){
            return true;
        }
        return false;
    }

    private boolean StrTail() throws IOException, ParseError{
        // match strTail-> str

        if(Str()){
            return true;
        }
        return true;
    }

    private boolean Char() throws IOException, ParseError{
        // match char -> a-z
        // match char -> A-Z 

        if((lookahead >= 'a' && lookahead <= 'z') ||
            (lookahead >= 'A' && lookahead <= 'Z')){
                
                consume(lookahead);
                return true;
        }
        return false;
    }

}