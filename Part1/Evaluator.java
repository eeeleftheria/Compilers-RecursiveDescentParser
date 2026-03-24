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

    private int Exp() throws IOException, ParseError{
        // match exp -> term expTail 
    }

    private int ExpTail() throws IOException, ParseError{
        // match expTail -> / exp

    }

    private int Term() throws IOException, ParseError{
        //match term -> factor termTail
    }

    private int TermTail() throws IOException, ParseError{
        // match termTail-> ** factor termTail
    }

    private int Factor() throws IOException, ParseError{
        // match factor -> str
        // match factor -> (exp)
    }

    private int Str() throws IOException, ParseError{
        // match str -> char strTail
    }

    private int StrTail() throws IOException, ParseError{
        // match strTail-> str
    }

    private int Char() throws IOException, ParseError{
        // match char -> a-z
        // match char -> A-Z 
    }

}