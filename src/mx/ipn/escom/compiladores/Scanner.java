package src.mx.ipn.escom.compiladores;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Scanner {

    private final String source;

    private final List<Token> tokens = new ArrayList<>();

    private int linea = 1;

    private static final Map<String, TipoToken> palabrasReservadas;
    static {
        palabrasReservadas = new HashMap<>();
        palabrasReservadas.put("clase", TipoToken.CLASE);
        palabrasReservadas.put("ademas", TipoToken.ADEMAS);
        palabrasReservadas.put("falso", TipoToken.FALSO);
        palabrasReservadas.put("para", TipoToken.PARA);
        palabrasReservadas.put("fun", TipoToken.FUN); // definir funciones
        palabrasReservadas.put("si", TipoToken.SI);
        palabrasReservadas.put("nulo", TipoToken.NULO);
        palabrasReservadas.put("imprimir", TipoToken.IMPRIMIR);
        palabrasReservadas.put("retornar", TipoToken.RETORNAR);
        palabrasReservadas.put("super", TipoToken.SUPER);
        palabrasReservadas.put("este", TipoToken.ESTE);
        palabrasReservadas.put("verdadero", TipoToken.VERDADERO);
        palabrasReservadas.put("var", TipoToken.VAR); // definir variables
        palabrasReservadas.put("mientras", TipoToken.MIENTRAS);
    }

    Scanner(String source) {
        this.source = source;
    }

    List<Token> scanTokens() {
        // Aquí va el corazón del scanner.

        /*
         * Analizar el texto de entrada para extraer todos los tokens
         * y al final agregar el token de fin de archivo
         */

         //Identificadores y Palabras Reservadas
         int estado = 0, i = 0;
         char c;
         String temp = "";
         while (source.length() != i) {
             c = source.charAt(i);
             if (c=='"') {
                 while (source.length() != i) {
                     i++;
                     c = source.charAt(i);
                     if (c=='"') {
                         break;
                     }
                 }
             }
             switch (estado) {
                 case 0:
                     if (Character.isLetter(c)) {
                         estado = 1;
                         temp = temp + c;
                     }
                     break;
                 case 1:
                     if (Character.isLetter(c) || Character.isDigit(c)) {
                         estado = 1;
                         temp = temp + c;
                     } else {
                         switch (temp) {
                             case "if":
                                 tokens.add(new Token(TipoToken.SI, temp, null, linea));
                                 break;
                             case "else":
                                 tokens.add(new Token(TipoToken.ADEMAS, temp, null, linea));
                                 break;
                             case "while":
                                 tokens.add(new Token(TipoToken.MIENTRAS, temp, null, linea));
                                 break;
                             case "for":
                                 tokens.add(new Token(TipoToken.PARA, temp, null, linea));
                                 break;
                             case "return":
                                 tokens.add(new Token(TipoToken.RETORNAR, temp, null, linea));
                                 break;
                             case "class":
                                 tokens.add(new Token(TipoToken.CLASE, temp, null, linea));
                                 break;
                             case "print":
                                 tokens.add(new Token(TipoToken.IMPRIMIR, temp, null, linea));
                                 break;
                             case "this":
                                 tokens.add(new Token(TipoToken.ESTE, temp, null, linea));
                                 break;
                             case "false":
                                 tokens.add(new Token(TipoToken.FALSO, temp, null, linea));
                                 break;
                             case "true":
                                 tokens.add(new Token(TipoToken.VERDADERO, temp, null, linea));
                                 break;
                             case "null":
                                 tokens.add(new Token(TipoToken.NULO, temp, null, linea));
                                 break;
                             case "fun":
                                 tokens.add(new Token(TipoToken.FUN, temp, null, linea));
                                 break;
                             case "super":
                                 tokens.add(new Token(TipoToken.SUPER, temp, null, linea));
                                 break;
                             case "var":
                                 tokens.add(new Token(TipoToken.VAR, temp, null, linea));
                                 break;
                             default:
                                 tokens.add(new Token(TipoToken.IDENTIFICADOR, temp, null, linea));
                                 break;
                         }
                         estado = 0;
                         temp = "";
                     }
                     break;
                 default:
                     break;
             }
             i++;
         }
 

        tokens.add(new Token(TipoToken.EOF, "", null, linea));

        return tokens;
    }
}

/*
 * Signos o símbolos del lenguaje:
 * (
 * )
 * {
 * }
 * ,
 * .
 * ;
 * -
 * +
 *
 * /
 * !
 * !=
 * =
 * ==
 * <
 * <=
 * >
 * >=
 * // -> comentarios (no se genera token)
 * /* ... * / -> comentarios (no se genera token)
 * Identificador,
 * Cadena
 * Numero
 * Cada palabra reservada tiene su nombre de token
 * 
 */