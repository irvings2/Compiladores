package src.mx.ipn.escom.compiladores;

import java.util.List;

public class Parser {
    private final List<Token> tokens;
    private final Token super1 = new Token(TipoToken.SUPER*, "", null, 0);
    private final Token cadena = new Token(TipoToken.CADENA, "", null, 0);
    private final Token numero = new Token(TipoToken.NUMERO, "", null, 0);
    private final Token this1 = new Token(TipoToken.ESTE, "", null, 0);
    private final Token nulo = new Token(TipoToken.NULO, "", null, 0);
    private final Token falso = new Token(TipoToken.FALSO, "", null, 0);
    private final Token verdadero = new Token(TipoToken.VERDADERO, "", null, 0);
    private final Token punto = new Token(TipoToken.PUNTO, "", null, 0);
    private final Token mult = new Token(TipoToken.MULT, "", null, 0);
    private final Token div = new Token(TipoToken.DIV, "", null, 0);
    private final Token mas = new Token(TipoToken.MAS, "", null, 0);
    private final Token menos = new Token(TipoToken.MENOS, "", null, 0);
    private final Token menorigual = new Token(TipoToken.MENOROIGUALQUE, "", null, 0);
    private final Token menorque = new Token(TipoToken.MENORQUE, "", null, 0);
    private final Token mayorigual = new Token(TipoToken.MAYOROIGUALQUE, "", null, 0);
    private final Token mayorque = new Token(TipoToken.MAYORQUE, "", null, 0);
    private final Token iguala = new Token(TipoToken.IGUALA, "", null, 0);
    private final Token diferenteque = new Token(TipoToken.DIFERENTEQUE, "", null, 0);
    private final Token and1 = new Token(TipoToken.Y, "", null, 0);
    private final Token or1 = new Token(TipoToken.O, "", null, 0);
    private final Token while1 = new Token(TipoToken.MIENTRAS, "", null, 0);
    private final Token return1 = new Token(TipoToken.RETORNAR, "", null, 0);
    private final Token print1 = new Token(TipoToken.IMPRIMIR, "", null, 0);
    private final Token else1 = new Token(TipoToken.ADEMAS, "", null, 0);
    private final Token if1 = new Token(TipoToken.SI, "", null, 0);
    private final Token for1 = new Token(TipoToken.PARA, "", null, 0);
    private final Token asignacion = new Token(TipoToken.ASIGNACION, "", null, 0);
    private final Token var1 = new Token(TipoToken.VAR, "", null, 0);
    private final Token fun1 = new Token(TipoToken.FUN, "", null, 0);
    private final Token identificador = new Token(TipoToken.IDENTIFICADOR, "", null, 0);
    private final Token class1 = new Token(TipoToken.CLASE, "", null, 0);
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
