package src.mx.ipn.escom.compiladores;

public class Arbol {
    private final Nodo raiz;

    public Arbol(Nodo raiz) {
        this.raiz = raiz;
    }

    public void recorrer(TablaSimbolos tabla) {
        for (Nodo n : raiz.getHijos()) {
            Token t = n.getValue();
            switch (t.tipo) {
                case VAR:
                    // Crear una variable. Usar tabla de simbolos
                    if (n.getHijos().size() > 1) {
                        Nodo izq = n.getHijos().get(0);
                        Nodo der = n.getHijos().get(1);
                        switch (der.getValue().tipo) {
                            case MAS:
                            case MENOS:
                            case MULT:
                            case DIV:
                                SolverAritmetico solver = new SolverAritmetico(der);
                                Object res = solver.resolver(tabla);
                                if (tabla.existeIdentificador(izq.getValue().lexema)) {
                                    throw new RuntimeException("Variable ya definida");
                                } else {
                                    tabla.asignar(izq.getValue().lexema, res);
                                }
                                break;

                            default:
                                if (tabla.existeIdentificador(izq.getValue().lexema)) {
                                    throw new RuntimeException("Variable ya definida");
                                } else {
                                    tabla.asignar(izq.getValue().lexema, der.getValue().literal);
                                }
                                break;
                        }
                    } else {
                        Nodo izq = n.getHijos().get(0);
                        if (tabla.existeIdentificador(izq.getValue().lexema)) {
                            throw new RuntimeException("Variable ya definida");
                        } else {
                            tabla.asignar(izq.getValue().lexema, null);
                        }
                    }
                    break;
                case IMPRIMIR:
                    Nodo izq1 = n.getHijos().get(0);
                    switch (izq1.getValue().tipo) {
                        case MAS:
                            SolverAritmetico solver = new SolverAritmetico(izq1);
                            Object res = solver.resolver(tabla);
                            System.out.print(res);
                            break;

                        default:
                            if (tabla.existeIdentificador(izq1.getValue().lexema)) {
                                System.out.print(tabla.obtener(izq1.getValue().lexema));
                            } else {
                                System.out.print(izq1.getValue().literal);
                            }
                            break;
                    }
                    break;
                case SI:
                    Nodo izq2 = n.getHijos().get(0);
                    SolverLogico solver1 = new SolverLogico(izq2);
                    Object res1 = solver1.resolver(tabla);
                    if ((Boolean) res1) {
                        if (n.getHijos().size() > 2) {
                            for (int i = 0; i < n.getHijos().size(); i++) {
                                Nodo der1 = n.getHijos().get(i);
                                switch (der1.getValue().tipo) {
                                    case IMPRIMIR:
                                        Nodo izq3 = der1.getHijos().get(0);
                                        if (tabla.existeIdentificador(izq3.getValue().lexema)) {
                                            System.out.print(tabla.obtener(izq3.getValue().lexema));
                                        } else {
                                            System.out.print(izq3.getValue().literal);
                                        }
                                        break;
                                    default:
                                        break;
                                }
                            }
                        } else {
                            Nodo der1 = n.getHijos().get(1);
                            switch (der1.getValue().tipo) {
                                case IMPRIMIR:
                                    Nodo izq3 = der1.getHijos().get(0);
                                    if (tabla.existeIdentificador(izq3.getValue().lexema)) {
                                        System.out.print(tabla.obtener(izq3.getValue().lexema));
                                    } else {
                                        System.out.print(izq3.getValue().literal);
                                    }
                                    break;
                                default:
                                    break;
                            }
                        }
                    } else {
                        if (n.getHijos().size() > 2) {
                            Nodo aux1 = n.getHijos().get(2);
                            if (aux1.getHijos().size() > 2) {
                                for (int i = 0; i < aux1.getHijos().size(); i++) {
                                    Nodo der1 = aux1.getHijos().get(i);
                                    switch (der1.getValue().tipo) {
                                        case IMPRIMIR:
                                            Nodo izq3 = der1.getHijos().get(0);
                                            if (tabla.existeIdentificador(izq3.getValue().lexema)) {
                                                System.out.print(tabla.obtener(izq3.getValue().lexema));
                                            } else {
                                                System.out.print(izq3.getValue().literal);
                                            }
                                            break;
                                        default:
                                            break;
                                    }
                                }
                            } else {
                                Nodo der1 = aux1.getHijos().get(0);
                                switch (der1.getValue().tipo) {
                                    case IMPRIMIR:
                                        Nodo izq3 = der1.getHijos().get(0);
                                        if (tabla.existeIdentificador(izq3.getValue().lexema)) {
                                            System.out.print(tabla.obtener(izq3.getValue().lexema));
                                        } else {
                                            System.out.print(izq3.getValue().literal);
                                        }
                                        break;
                                    default:
                                        break;
                                }
                            }
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    }
}
