package src.mx.ipn.escom.compiladores;

import java.util.ArrayList;
import java.util.List;

public class Arbol {
    private final Nodo raiz;

    public Arbol(Nodo raiz){
        this.raiz = raiz;
    }

    public void recorrer(TablaSimbolos tabla){
        for(Nodo n : raiz.getHijos()){
            Token t = n.getValue();
            switch (t.tipo){
                // Operadores aritméticos
                case MAS:
                case MENOS:
                case MULT:
                case DIV:
                    SolverAritmetico solver = new SolverAritmetico(n);
                    Object res = solver.resolver(tabla);
                    System.out.println(res);
                break;

                case VAR:
                    // Crear una variable. Usar tabla de simbolos
                    Nodo izq = n.getHijos().get(0);
                    Nodo der = n.getHijos().get(1);
                    if (tabla.existeIdentificador(izq.getValue().lexema)) {
                        throw new RuntimeException("Variable ya definida");
                    }
                    else{
                        tabla.asignar(izq.getValue().lexema,der.getValue().literal);
                    }
                    break;
                case IMPRIMIR:
                    Nodo izq1 = n.getHijos().get(0);
                    if (tabla.existeIdentificador(izq1.getValue().lexema)) {
                        System.out.println(tabla.obtener(izq1.getValue().lexema));
                    }
                    else{
                        System.out.println(izq1.getValue().literal);
                    }
                    break;
                case SI:
                    break;

            }
        }
    }
}

