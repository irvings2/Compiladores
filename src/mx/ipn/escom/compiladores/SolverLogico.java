package src.mx.ipn.escom.compiladores;

public class SolverLogico {

    private final Nodo nodo;

    public SolverLogico(Nodo nodo) {
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
                if (tabla.existeIdentificador(n.getValue().lexema)) {
                    return tabla.obtener(n.getValue().lexema);
                }
            }
        }

        // Por simplicidad se asume que la lista de hijos del nodo tiene dos elementos
        Nodo izq = n.getHijos().get(0);
        Nodo der = n.getHijos().get(1);

        Object resultadoIzquierdo = resolver(izq, tabla);
        Object resultadoDerecho = resolver(der, tabla);

        if (resultadoIzquierdo instanceof Double && resultadoDerecho instanceof Double) {
            switch (n.getValue().tipo) {
                case MENORQUE:
                    return ((Double) resultadoIzquierdo < (Double) resultadoDerecho);
                case MENOROIGUALQUE:
                    return ((Double) resultadoIzquierdo <= (Double) resultadoDerecho);
                case MAYORQUE:
                    return ((Double) resultadoIzquierdo > (Double) resultadoDerecho);
                case MAYOROIGUALQUE:
                    return ((Double) resultadoIzquierdo >= (Double) resultadoDerecho);
                default:
                    break;
            }
        } else if (resultadoIzquierdo instanceof Boolean && resultadoDerecho instanceof Boolean) {
            if (n.getValue().tipo == TipoToken.Y) {
                return ((Boolean) resultadoIzquierdo && (Boolean) resultadoDerecho);
            }
        } else {
            // Error por diferencia de tipos
            System.out.println("Error");
            System.exit(0);
        }

        return null;
    }
}
