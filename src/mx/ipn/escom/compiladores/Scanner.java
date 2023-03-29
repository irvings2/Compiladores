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
 
       //Operadores Relacionales

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
                   if (c == '<') {
                       estado = 1;
                   } else if (c == '=') {
                       estado = 5;
                   } else if (c == '>') {
                       estado = 6;
                   } else if (c == '!') {
                       estado = 3;
                   }
                   break;
               case 1:
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
               case 3:
                   if (c == '=') {
                       temp = source.substring(i - 1, i + 1);
                       tokens.add(new Token(TipoToken.DIFERENTEQUE, temp, null, linea));
                       temp = "";
                       estado = 0;
                   }
                   break;
               case 5:
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
                   }
                   break;
               case 6:
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
                   }
                   break;
           }
           i++;
       }

       //Parentesis

       estado = 0;
       i = 0;
       c = ' ';
       temp = "";

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
                   if (c == '(') {
                       estado = 1;
                       temp = Character.toString(c);
                       tokens.add(new Token(TipoToken.PARENIZQ, temp, null, linea));
                       temp = "";
                   }
                   break;
               case 1:
                   if (c == '(') {
                       estado = 1;
                       temp = Character.toString(c);
                       tokens.add(new Token(TipoToken.PARENIZQ, temp, null, linea));
                       temp = "";
                   } else if (c == ')') {
                       estado = 2;
                       temp = Character.toString(c);
                       tokens.add(new Token(TipoToken.PARENDER, temp, null, linea));
                       temp = "";
                   }
                   break;
               case 2:
                   if (c == ')') {
                       estado = 2;
                       temp = Character.toString(c);
                       tokens.add(new Token(TipoToken.PARENDER, temp, null, linea));
                       temp = "";
                   } else if (c == '(') {
                       estado = 1;
                       temp = Character.toString(c);
                       tokens.add(new Token(TipoToken.PARENIZQ, temp, null, linea));
                       temp = "";
                   }
                   break;
           }
           i++;
       }

        //Llaves

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
                    if (c == '{') {
                        estado = 1;
                        temp = Character.toString(c);
                        tokens.add(new Token(TipoToken.LLAVEIZQ, temp, null, linea));
                        temp = "";
                    }
                    break;
                case 1:
                    if (c == '{') {
                        estado = 1;
                        temp = Character.toString(c);
                        tokens.add(new Token(TipoToken.LLAVEIZQ, temp, null, linea));
                        temp = "";
                    } else if (c == '}') {
                        estado = 2;
                        temp = Character.toString(c);
                        tokens.add(new Token(TipoToken.LLAVEDER, temp, null, linea));
                        temp = "";
                    }
                    break;
                case 2:
                    if (c == '}') {
                        estado = 2;
                        temp = Character.toString(c);
                        tokens.add(new Token(TipoToken.LLAVEDER, temp, null, linea));
                        temp = "";
                    } else if (c == '{') {
                        estado = 1;
                        temp = Character.toString(c);
                        tokens.add(new Token(TipoToken.LLAVEIZQ, temp, null, linea));
                        temp = "";
                    }
                    break;
            }
            i++;
        }

        //Operadores Aritmeticos

        estado = 0;
        i = 0;
        c = ' ';
        temp = "";

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
                    if (c == '+') {
                        estado = 1;
                        temp = Character.toString(c);
                    } else if (c == '-') {
                        estado = 2;
                        temp = Character.toString(c);
                    } else if (c == '*') {
                        estado = 3;
                        temp = Character.toString(c);
                        tokens.add(new Token(TipoToken.MULT, temp, null, linea));
                        temp = "";
                        estado = 0;
                    } else if (c == '/') {
                        estado = 4;
                        temp = Character.toString(c);
                        tokens.add(new Token(TipoToken.DIV, temp, null, linea));
                        temp = "";
                        estado = 0;
                    }
                    break;
                case 1:
                    if (c == '+') {
                        estado = 5;
                        temp = temp + c;
                        tokens.add(new Token(TipoToken.INCREMENTO, temp, null, linea));
                        temp = "";
                        estado = 0;
                    } else {
                        estado = 0;
                        tokens.add(new Token(TipoToken.MAS, temp, null, linea));
                        temp = "";
                    }
                    break;
                case 2:
                    if (c == '-') {
                        estado = 6;
                        temp = temp + c;
                        tokens.add(new Token(TipoToken.DECREMENTO, temp, null, linea));
                        temp = "";
                        estado = 0;
                    } else {
                        estado = 0;
                        tokens.add(new Token(TipoToken.MENOS, temp, null, linea));
                        temp = "";
                    }
                    break;
            }
            i++;
        }

        //Numeros

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

        //Cadenas

        estado = 0;
        i = 0;
        c = ' ';
        temp = "";

        while (source.length() != i) {
            c = source.charAt(i);
            switch (estado) {
                case 0:
                    if (c == '"') {
                        estado = 1;
                    }
                    break;
                case 1:
                    if (c != '"') {
                        estado = 1;
                        temp = temp + c;
                    } else {
                        estado = 0;
                        tokens.add(new Token(TipoToken.CADENA, temp, null, linea));
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