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
                // Operadores aritméticos
                /*case MAS:
                case MENOS:
                case MULT:
                case DIV:
                    SolverAritmetico solver = new SolverAritmetico(n);
                    Object res = solver.resolver(tabla);
                    break;*/

                case VAR:
                    // Crear una variable. Usar tabla de simbolos
                    if (n.getHijos().size()>1) {
                        Nodo izq = n.getHijos().get(0);
                        Nodo der = n.getHijos().get(1);
                        if (tabla.existeIdentificador(izq.getValue().lexema)) {
                            throw new RuntimeException("Variable ya definida");
                        } else {
                            tabla.asignar(izq.getValue().lexema, der.getValue().literal);
                        }
                    }else{
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
                            System.out.println(res);
                            break;
                    
                        default:
                            if (tabla.existeIdentificador(izq1.getValue().lexema)) {
                                System.out.println(tabla.obtener(izq1.getValue().lexema));
                            } else {
                                System.out.println(izq1.getValue().literal);
                            }
                            break;
                    }
                    break;
                case SI:
                    Nodo izq2 = n.getHijos().get(0);
                    SolverLogico solver1 = new SolverLogico(izq2);
                    Object res1 = solver1.resolver(tabla);
                    if ((Boolean)res1) {
                        if (n.getHijos().size()>2) {
                            for (int i = 0; i < n.getHijos().size(); i++) {
                                Nodo der1 = n.getHijos().get(i);
                                switch (der1.getValue().tipo) {
                                    case IMPRIMIR:
                                        Nodo izq3 = der1.getHijos().get(0);
                                        if (tabla.existeIdentificador(izq3.getValue().lexema)) {
                                            System.out.println(tabla.obtener(izq3.getValue().lexema));
                                        } else {
                                            System.out.println(izq3.getValue().literal);
                                        }
                                        break;
                                    default:
                                        break;
                                }
                            }
                        }else{
                            Nodo der1 = n.getHijos().get(1);
                            switch (der1.getValue().tipo) {
                                case IMPRIMIR:
                                    Nodo izq3 = der1.getHijos().get(0);
                                    if (tabla.existeIdentificador(izq3.getValue().lexema)) {
                                        System.out.println(tabla.obtener(izq3.getValue().lexema));
                                    } else {
                                        System.out.println(izq3.getValue().literal);
                                    }
                                    break;
                                default:
                                    break;
                            }
                        }
                    }else{
                        Nodo else1 = n.getHijos().get(2);
                        Nodo izqelse = else1.getHijos().get(0);
                        switch (izqelse.getValue().tipo) {
                            case IMPRIMIR:
                                Nodo izq3 = izqelse.getHijos().get(0);
                                if (tabla.existeIdentificador(izq3.getValue().lexema)) {
                                    System.out.println(tabla.obtener(izq3.getValue().lexema));
                                } else {
                                    System.out.println(izq3.getValue().literal);
                                }
                                break;
                            default:
                                break;
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    }
}
