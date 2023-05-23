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
        palabrasReservadas.put("class", TipoToken.CLASE);
        palabrasReservadas.put("else", TipoToken.ADEMAS);
        palabrasReservadas.put("false", TipoToken.FALSO);
        palabrasReservadas.put("for", TipoToken.PARA);
        palabrasReservadas.put("fun", TipoToken.FUN); // definir funciones
        palabrasReservadas.put("if", TipoToken.SI);
        palabrasReservadas.put("null", TipoToken.NULO);
        palabrasReservadas.put("printf", TipoToken.IMPRIMIR);
        palabrasReservadas.put("return", TipoToken.RETORNAR);
        palabrasReservadas.put("super", TipoToken.SUPER);
        palabrasReservadas.put("this", TipoToken.ESTE);
        palabrasReservadas.put("true", TipoToken.VERDADERO);
        palabrasReservadas.put("var", TipoToken.VAR); // definir variables
        palabrasReservadas.put("while", TipoToken.MIENTRAS);
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

         int estado = 0, i = 0;
         char c;
         String temp = "";
         while (source.length() != i) {
             c = source.charAt(i);
             switch (estado) {
                 case 0:
                     if (Character.isLetter(c)) {
                         estado = 1;
                         temp = temp + c;
                     }
                    if (c == '"') {
                        estado = 2;
                    }
                    if (c == '<') {
                       estado = 3;
                    } else if (c == '=') {
                       estado = 4;
                    } else if (c == '>') {
                       estado = 5;
                    } else if (c == '!') {
                       estado = 6;
                    }
                    if (c == '(') {
                       temp = Character.toString(c);
                       tokens.add(new Token(TipoToken.PARENIZQ, temp, null, linea));
                       temp = "";
                       estado = 0;
                    }
                    if (c == ')') {
                       temp = Character.toString(c);
                       tokens.add(new Token(TipoToken.PARENIZQ, temp, null, linea));
                       temp = "";
                       estado = 0;
                    }
                    if (c == '{') {
                       temp = Character.toString(c);
                       tokens.add(new Token(TipoToken.LLAVEIZQ, temp, null, linea));
                       temp = "";
                       estado = 0;
                    }
                    if (c == '}') {
                       temp = Character.toString(c);
                       tokens.add(new Token(TipoToken.LLAVEDER, temp, null, linea));
                       temp = "";
                       estado = 0;
                    }
                    if (c == '+') {
                        estado = 7;
                        temp = Character.toString(c);
                    } else if (c == '-') {
                        estado = 8;
                        temp = Character.toString(c);
                    } else if (c == '*') {
                        temp = Character.toString(c);
                        tokens.add(new Token(TipoToken.MULT, temp, null, linea));
                        temp = "";
                        estado = 0;
                    } else if (c == '/') {
                        temp = Character.toString(c);
                        tokens.add(new Token(TipoToken.DIV, temp, null, linea));
                        temp = "";
                        estado = 0;
                    }
                    break;
                 case 1:
                     if (Character.isLetter(c) || Character.isDigit(c)) {
                         estado = 1;
                         temp = temp + c;
                     }
                     else {
                         if (palabrasReservadas.containsKey(temp)) {
                            tokens.add(new Token(palabrasReservadas.get(temp), temp, null, linea));
                         } else {
                            tokens.add(new Token(TipoToken.IDENTIFICADOR, temp, null, linea));
                         }
                         estado = 0;
                         temp = "";
                         i--;
                     }
                     break;
                case 2:
                     if (c != '"') {
                        estado = 2;
                        temp = temp + c;
                    } else {
                        estado = 0;
                        tokens.add(new Token(TipoToken.CADENA, temp, null, linea));
                        temp = "";
                    }
                    break;
                case 3:
                    if (c == '=') {
                       temp = source.substring(i - 1, i + 1);
                       tokens.add(new Token(TipoToken.MENOROIGUALQUE, temp, null, linea));
                       temp = "";
                       estado = 0;
                   } else {
                       temp = source.substring(i - 1, i);
                       tokens.add(new Token(TipoToken.MENORQUE, temp, null, linea));
                       temp = "";
                       estado = 0;
                   }
                   break;
                case 4:
                    if (c == '=') {
                       temp = source.substring(i - 1, i + 1);
                       tokens.add(new Token(TipoToken.IGUALA, temp, null, linea));
                       temp = "";
                       estado = 0;
                    } else {
                       temp = source.substring(i - 1, i);
                       tokens.add(new Token(TipoToken.ASIGNACION, temp, null, linea));
                       temp = "";
                       estado = 0;
                       i--;
                   }
                    break;
                case 5:
                   if (c == '=') {
                       temp = source.substring(i - 1, i + 1);
                       tokens.add(new Token(TipoToken.MAYOROIGUALQUE, temp, null, linea));
                       temp = "";
                       estado = 0;
                   } else {
                       temp = source.substring(i - 1, i);
                       tokens.add(new Token(TipoToken.MAYORQUE, temp, null, linea));
                       temp = "";
                       estado = 0;
                       i--;
                   }
                   break;
                case 6:
                   if (c == '=') {
                       temp = source.substring(i - 1, i + 1);
                       tokens.add(new Token(TipoToken.DIFERENTEQUE, temp, null, linea));
                       temp = "";
                       estado = 0;
                   }
                   break;
                case 7:
                    if (c == '+') {
                        temp = temp + c;
                        tokens.add(new Token(TipoToken.INCREMENTO, temp, null, linea));
                        temp = "";
                        estado = 0;
                    } else {
                        tokens.add(new Token(TipoToken.MAS, temp, null, linea));
                        temp = "";
                        estado = 0;
                    }
                    break;
                case 8:
                    if (c == '-') {
                        temp = temp + c;
                        tokens.add(new Token(TipoToken.DECREMENTO, temp, null, linea));
                        temp = "";
                        estado = 0;
                    } else {
                        tokens.add(new Token(TipoToken.MENOS, temp, null, linea));
                        temp = "";
                        estado = 0;
                    }
                    break;
                 default:
                     break;
             }
             i++;
         }

        /*Numeros

        estado = 0;
        i = 0;
        c = ' ';
        temp = "";
        char caux=' ';

        while (source.length() != i) {
            c = source.charAt(i);
            if (c == '"') {
                while (source.length() != i) {
                    i++;
                    c = source.charAt(i);
                    if (c == '"') {
                        break;
                    }
                }
            }
            if (i!=0) {
                caux=source.charAt(i-1);   
            }
            if (Character.isLetter(caux)) {
                while (source.length() != i) {
                    c = source.charAt(i);
                    if (!Character.isDigit(c)){
                        break;
                    }
                    i++;
                }
            }
            switch (estado) {
                case 0:
                    if (Character.isDigit(c)) {
                        estado = 1;
                        temp = temp + c;
                    }
                    break;
                case 1:
                    if (Character.isDigit(c)) {
                        estado = 1;
                        temp = temp + c;
                    } else if (c == '.') {
                        estado = 2;
                        temp = temp + c;
                    } else if (c == 'E') {
                        estado = 4;
                        temp = temp + c;
                    } else {
                        estado = 0;
                        tokens.add(new Token(TipoToken.NUMERO, temp, null, linea));
                        temp = "";
                    }
                    break;
                case 2:
                    if (Character.isDigit(c)) {
                        estado = 3;
                        temp = temp + c;
                    }
                    break;
                case 3:
                    if (Character.isDigit(c)) {
                        estado = 3;
                        temp = temp + c;
                    } else if (c == 'E') {
                        estado = 4;
                        temp = temp + c;
                    } else {
                        estado = 0;
                        tokens.add(new Token(TipoToken.NUMERO, temp, null, linea));
                        temp = "";
                    }
                    break;
                case 4:
                    if (c == '+' || c == '-') {
                        estado = 5;
                        temp = temp + c;
                    } else if (Character.isDigit(c)) {
                        estado = 6;
                        temp = temp + c;
                    }
                    break;
                case 5:
                    if (Character.isDigit(c)) {
                        estado = 6;
                        temp = temp + c;
                    }
                    break;
                case 6:
                    if (Character.isDigit(c)) {
                        estado = 6;
                        temp = temp + c;
                    } else {
                        estado = 0;
                        tokens.add(new Token(TipoToken.NUMERO, temp, null, linea));
                        temp = "";
                    }
                    break;
            }
            i++;
        }

        //Punto, coma, punto y coma

        estado = 0;
        i = 0;
        c = ' ';
        temp = "";

        while (source.length() != i) {
            c = source.charAt(i);
            if (c == '"') {
                while (source.length() != i) {
                    i++;
                    c = source.charAt(i);
                    if (c == '"') {
                        break;
                    }
                }
            }
            switch (estado) {
                case 0:
                    if (c == '.') {
                        estado = 1;
                        temp = Character.toString(c);
                    } else if (c == ',') {
                        estado = 2;
                        temp = Character.toString(c);
                    } else if (c == ';') {
                        estado = 0;
                        temp = Character.toString(c);
                        tokens.add(new Token(TipoToken.PUNTOYCOMA, temp, null, linea));
                    }
                    break;
                case 1:
                    if (c != '.') {
                        estado = 0;
                        tokens.add(new Token(TipoToken.PUNTO, temp, null, linea));
                        temp = "";
                    }
                    break;
                case 2:
                    if (c != ',') {
                        tokens.add(new Token(TipoToken.COMA, temp, null, linea));
                        temp = "";
                        estado = 0;
                    }
                    break;
            }
            i++;
        }*/

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