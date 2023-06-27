package src.mx.ipn.escom.compiladores;

public class SolverAritmetico {

    private final Nodo nodo;

    public SolverAritmetico(Nodo nodo) {
        this.nodo = nodo;
    }

    public Object resolver(TablaSimbolos tabla) {
        return resolver(nodo, tabla);
    }

    private Object resolver(Nodo n, TablaSimbolos tabla) {
        // No tiene hijos, es un operando
        if (n.getHijos() == null) {
            if (n.getValue().tipo == TipoToken.NUMERO || n.getValue().tipo == TipoToken.CADENA) {
                return n.getValue().literal;
            } else if (n.getValue().tipo == TipoToken.IDENTIFICADOR) {
                // Ver la tabla de símbolos
                return tabla.obtener(n.getValue().lexema);
            }
        }

        // Por simplicidad se asume que la lista de hijos del nodo tiene dos elementos
        Nodo izq = n.getHijos().get(0);
        Nodo der = n.getHijos().get(1);

        Object resultadoIzquierdo = resolver(izq, tabla);
        Object resultadoDerecho = resolver(der, tabla);

        if (resultadoIzquierdo instanceof Double && resultadoDerecho instanceof Double) {
            switch (n.getValue().tipo) {
                case MAS:
                    return ((Double) resultadoIzquierdo + (Double) resultadoDerecho);
                case MENOS:
                    return ((Double) resultadoIzquierdo - (Double) resultadoDerecho);
                case MULT:
                    return ((Double) resultadoIzquierdo * (Double) resultadoDerecho);
                case DIV:
                    return ((Double) resultadoIzquierdo / (Double) resultadoDerecho);
                default:
                    break;
            }
        } else if (resultadoIzquierdo instanceof String && resultadoDerecho instanceof String) {
            if (n.getValue().tipo == TipoToken.MAS) {
                // Ejecutar la concatenación
                return ((String) resultadoIzquierdo + (String) resultadoDerecho);
            }
        } else {
            // Error por diferencia de tipos
            System.exit(0);
        }

        return null;
    }
}
