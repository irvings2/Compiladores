package src.mx.ipn.escom.compiladores;

import java.util.List;

public class Parser {
    private final List<Token> tokens;
    private final Token finCadena = new Token(TipoToken.EOF, "", null, 0);
    private int i = 0;
    private boolean hayErrores = false;
    private Token preanalisis;

    public Parser(List<Token> tokens){
        this.tokens = tokens;
    }

    public void parse(){
        i = 0;
        preanalisis = tokens.get(i);
        
        if(!hayErrores && !preanalisis.equals(finCadena)){
            System.out.println("Error");
        }
        else if(!hayErrores && preanalisis.equals(finCadena)){
            System.out.println("Cadena válida");
        }
    }

    void coincidir(Token t){
        if(hayErrores) return;

        if(preanalisis.tipo == t.tipo){
            i++;
            preanalisis = tokens.get(i);
        }
        else{
            hayErrores = true;
            System.out.println("Error");

        }
    }
}
