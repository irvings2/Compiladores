package src.mx.ipn.escom.compiladores;

import java.util.ArrayList;
import java.util.List;

public class Arbol {
    private final Nodo raiz;

    public Arbol(Nodo raiz){
        this.raiz = raiz;
    }

    public void recorrer(){
        for(Nodo n : raiz.getHijos()){
            Token t = n.getValue();
            switch (t.tipo){
                // Operadores aritméticos
                case MAS:
                case MENOS:
                case MULT:
                case DIV:
                    SolverAritmetico solver = new SolverAritmetico(n);
                    Object res = solver.resolver();
                    System.out.println(res);
                break;

                case VAR:
                    // Crear una variable. Usar tabla de simbolos
                    break;
                case SI:
                    break;

            }
        }
    }

    public void imprimir(){
        for(Nodo n : raiz.getHijos()){
            Token t = n.getValue();
            System.out.println(t);
        }
    }
}

