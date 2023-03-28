package src.mx.ipn.escom.compiladores;

public enum TipoToken {
    // Crear un tipoToken por palabra reservada
    // Crear un tipoToken: identificador, una cadena y numero
    // Crear un tipoToken por cada "Signo del lenguaje" (ver clase Scanner)

    // Palabras RESERVADAS
    CLASE, ADEMAS, FALSO, PARA, FUN, SI, NULO, IMPRIMIR, RETORNAR, SUPER,
    ESTE, VERDADERO, VAR, MIENTRAS,

    //OTROS TOKENS
    IDENTIFICADOR,CADENA,NUMERO,

    //SIMBOLOS
    PARENIZQ,PARENDER,LLAVEIZQ,LLAVEDER,COMA,PUNTO,PUNTOYCOMA,MENOS,MAS,MULT,DIV,NEGACION,DIFERENTEQUE,ASIGNACION,
    Y,O,IGUALA,MENORQUE,MENOROIGUALQUE,MAYORQUE,MAYOROIGUALQUE,INCREMENTO,DECREMENTO,

    // Final de cadena
    EOF
}