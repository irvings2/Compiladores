package src.mx.ipn.escom.compiladores;

public class Token {

    final TipoToken tipo;
    final String lexema;
    final Object literal;
    final int linea;

    public Token(TipoToken tipo, String lexema, Object literal, int linea) {
        this.tipo = tipo;
        this.lexema = lexema;
        this.literal = literal;
        this.linea = linea;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Token)) {
            return false;
        }

        if (this.tipo == ((Token) o).tipo) {
            return true;
        }

        return false;
    }

    public String toString() {
        return tipo + " " + lexema + " " + literal;
    }

    // Métodos auxiliares
    public boolean esOperando(){
        switch (this.tipo){
            case IDENTIFICADOR:
            case NUMERO:
                return true;
            default:
                return false;
        }
    }

    public boolean esOperador(){
        switch (this.tipo){
            case MAS:
            case MENOS:
            case MULT:
            case DIV:
            case IGUALA:
            case MAYORQUE:
            case MAYOROIGUALQUE:
            case MENORQUE:
            case MENOROIGUALQUE:
                return true;
            default:
                return false;
        }
    }

    public boolean esPalabraReservada(){
        switch (this.tipo){
            case VAR:
            case IMPRIMIR:
            case FALSO:
            case FUN:
            case NULO:
            case RETORNAR:
            case SUPER:
            case ESTE:
            case VERDADERO:
            case CLASE:
                return true;
            default:
                return false;
        }
    }

    public boolean esEstructuraDeControl(){
        switch (this.tipo){
            case ADEMAS:
            case MIENTRAS:
            case PARA:
            case SI:
                return true;
            default:
                return false;
        }
    }

    public boolean precedenciaMayorIgual(Token t){
        return this.obtenerPrecedencia() >= t.obtenerPrecedencia();
    }

    private int obtenerPrecedencia(){
        switch (this.tipo){
            case MULT:
            case DIV:
                return 3;
            case MAS:
            case MENOS:
                return 2;
            case IGUALA:
                return 1;
            case MAYORQUE:
            case MAYOROIGUALQUE:
            case MENORQUE:
            case MENOROIGUALQUE:
                return 1;
        }

        return 0;
    }

    public int aridad(){
        switch (this.tipo) {
            case MULT:
            case DIV:
            case MAS:
            case MENOS:
            case IGUALA:
            case MAYORQUE:
            case MAYOROIGUALQUE:
            case MENORQUE:
            case MENOROIGUALQUE:
                return 2;
        }
        return 0;
    }
}
