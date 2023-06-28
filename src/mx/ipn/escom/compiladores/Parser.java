package src.mx.ipn.escom.compiladores;

import java.util.List;

public class Parser {
    private final List<Token> tokens;
    private final Token negacion = new Token(TipoToken.NEGACION, "", null, 0);
    private final Token parender = new Token(TipoToken.PARENDER, "", null, 0);
    private final Token parenizq = new Token(TipoToken.PARENIZQ, "", null, 0);
    private final Token llaveder = new Token(TipoToken.LLAVEDER, "", null, 0);
    private final Token llaveizq = new Token(TipoToken.LLAVEIZQ, "", null, 0);
    private final Token puntoycoma = new Token(TipoToken.PUNTOYCOMA, "", null, 0);
    private final Token coma = new Token(TipoToken.COMA, "", null, 0);
    private final Token super1 = new Token(TipoToken.SUPER, "", null, 0);
    private final Token cadena = new Token(TipoToken.CADENA, "", null, 0);
    private final Token numero = new Token(TipoToken.NUMERO, "", null, 0);
    private final Token this1 = new Token(TipoToken.ESTE, "", null, 0);
    private final Token nulo = new Token(TipoToken.NULO, "", null, 0);
    private final Token falso = new Token(TipoToken.FALSO, "", null, 0);
    private final Token verdadero = new Token(TipoToken.VERDADERO, "", null, 0);
    private final Token punto = new Token(TipoToken.PUNTO, "", null, 0);
    private final Token mult = new Token(TipoToken.MULT, "", null, 0);
    private final Token div = new Token(TipoToken.DIV, "", null, 0);
    private final Token mas = new Token(TipoToken.MAS, "", null, 0);
    private final Token menos = new Token(TipoToken.MENOS, "", null, 0);
    private final Token menorigual = new Token(TipoToken.MENOROIGUALQUE, "", null, 0);
    private final Token menorque = new Token(TipoToken.MENORQUE, "", null, 0);
    private final Token mayorigual = new Token(TipoToken.MAYOROIGUALQUE, "", null, 0);
    private final Token mayorque = new Token(TipoToken.MAYORQUE, "", null, 0);
    private final Token iguala = new Token(TipoToken.IGUALA, "", null, 0);
    private final Token diferenteque = new Token(TipoToken.DIFERENTEQUE, "", null, 0);
    private final Token and1 = new Token(TipoToken.Y, "", null, 0);
    private final Token or1 = new Token(TipoToken.O, "", null, 0);
    private final Token while1 = new Token(TipoToken.MIENTRAS, "", null, 0);
    private final Token return1 = new Token(TipoToken.RETORNAR, "", null, 0);
    private final Token print1 = new Token(TipoToken.IMPRIMIR, "", null, 0);
    private final Token else1 = new Token(TipoToken.ADEMAS, "", null, 0);
    private final Token if1 = new Token(TipoToken.SI, "", null, 0);
    private final Token for1 = new Token(TipoToken.PARA, "", null, 0);
    private final Token asignacion = new Token(TipoToken.ASIGNACION, "", null, 0);
    private final Token var1 = new Token(TipoToken.VAR, "", null, 0);
    private final Token fun1 = new Token(TipoToken.FUN, "", null, 0);
    private final Token identificador = new Token(TipoToken.IDENTIFICADOR, "", null, 0);
    private final Token class1 = new Token(TipoToken.CLASE, "", null, 0);
    private final Token finCadena = new Token(TipoToken.EOF, "", null, 0);
    private int i = 0;
    private boolean hayErrores = false;
    private Token preanalisis;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    public void parse() {
        i = 0;
        preanalisis = tokens.get(i);
        program();

        if (!hayErrores && !preanalisis.equals(finCadena)) {
            System.out.println("Error");
        } /*
           * else if (!hayErrores && preanalisis.equals(finCadena)) {
           * System.out.println("Cadena válida");
           * }
           */
    }

    void program() {
        declaration();
    }

    void declaration() {
        if (preanalisis.equals(class1)) {
            class_decl();
            declaration();
        } else if (preanalisis.equals(fun1)) {
            fun_decl();
            declaration();
        } else if (preanalisis.equals(var1)) {
            var_decl();
            declaration();
        } else if (preanalisis.equals(for1) || preanalisis.equals(if1) || preanalisis.equals(print1)
                || preanalisis.equals(return1) || preanalisis.equals(while1) || preanalisis.equals(negacion)
                || preanalisis.equals(menos) || preanalisis.equals(verdadero)
                || preanalisis.equals(falso) || preanalisis.equals(identificador)
                || preanalisis.equals(nulo) || preanalisis.equals(this1) || preanalisis.equals(numero)
                || preanalisis.equals(cadena) || preanalisis.equals(parenizq) || preanalisis.equals(super1)
                || preanalisis.equals(llaveizq)) {
            statement();
            declaration();
        }
    }

    void class_decl() {
        if (preanalisis.equals(class1)) {
            coincidir(class1);
            coincidir(identificador);
            class_inher();
            coincidir(llaveizq);
            functions();
            coincidir(llaveder);
        } else {
            hayErrores = true;
            System.out.println("Error, se esperaba la palabra reservada class");
        }
    }

    void class_inher() {
        if (preanalisis.equals(menorque)) {
            coincidir(menorque);
            coincidir(identificador);
        }
    }

    void fun_decl() {
        if (preanalisis.equals(fun1)) {
            coincidir(fun1);
            function();
        } else {
            hayErrores = true;
            System.out.println("Error, se esperaba la palabra reservada fun");
        }
    }

    void var_decl() {
        if (preanalisis.equals(var1)) {
            coincidir(var1);
            coincidir(identificador);
            var_init();
            coincidir(puntoycoma);
        } else {
            hayErrores = true;
            System.out.println("Error,se esperaba la palabra reservada var");
        }
    }

    void var_init() {
        if (preanalisis.equals(asignacion)) {
            coincidir(asignacion);
            expression();
        }
    }

    void statement() {
        if (preanalisis.equals(negacion) || preanalisis.equals(menos) || preanalisis.equals(verdadero)
                || preanalisis.equals(falso) || preanalisis.equals(identificador)
                || preanalisis.equals(nulo) || preanalisis.equals(this1) || preanalisis.equals(numero)
                || preanalisis.equals(cadena) || preanalisis.equals(parenizq) || preanalisis.equals(super1)) {
            expr_stmt();
        } else if (preanalisis.equals(for1)) {
            for_stmt();
        } else if (preanalisis.equals(if1)) {
            if_stmt();
        } else if (preanalisis.equals(print1)) {
            print_stmt();
        } else if (preanalisis.equals(return1)) {
            return_stmt();
        } else if (preanalisis.equals(while1)) {
            while_stmt();
        } else if (preanalisis.equals(llaveizq)) {
            block();
        } else {
            hayErrores = true;
            System.out.println("Error");
        }
    }

    void expr_stmt() {
        if (preanalisis.equals(negacion) || preanalisis.equals(menos) || preanalisis.equals(verdadero)
                || preanalisis.equals(falso) || preanalisis.equals(identificador)
                || preanalisis.equals(nulo) || preanalisis.equals(this1) || preanalisis.equals(numero)
                || preanalisis.equals(cadena) || preanalisis.equals(parenizq) || preanalisis.equals(super1)) {
            expression();
            coincidir(puntoycoma);
        } else {
            hayErrores = true;
            System.out.println("Error, se esperaba una expresion de estado");
        }
    }

    void for_stmt() {
        if (preanalisis.equals(for1)) {
            coincidir(for1);
            coincidir(parenizq);
            for_stmt_1();
            for_stmt_2();
            for_stmt_3();
            coincidir(parender);
            statement();
        } else {
            hayErrores = true;
            System.out.println("Error, se esperaba la palabra reservada for");
        }
    }

    void for_stmt_1() {
        if (preanalisis.equals(var1)) {
            var_decl();
        } else if (preanalisis.equals(negacion) || preanalisis.equals(menos) || preanalisis.equals(verdadero)
                || preanalisis.equals(falso) || preanalisis.equals(identificador)
                || preanalisis.equals(nulo) || preanalisis.equals(this1) || preanalisis.equals(numero)
                || preanalisis.equals(cadena) || preanalisis.equals(parenizq) || preanalisis.equals(super1)) {
            expr_stmt();
        } else if (preanalisis.equals(puntoycoma)) {
            coincidir(puntoycoma);
        } else {
            hayErrores = true;
            System.out.println("Error, se esperaba la palabra reservada var o un declaracion de estado o punto y coma");
        }
    }

    void for_stmt_2() {
        if (preanalisis.equals(negacion) || preanalisis.equals(menos) || preanalisis.equals(verdadero)
                || preanalisis.equals(falso) || preanalisis.equals(identificador)
                || preanalisis.equals(nulo) || preanalisis.equals(this1) || preanalisis.equals(numero)
                || preanalisis.equals(cadena) || preanalisis.equals(parenizq) || preanalisis.equals(super1)) {
            expression();
            coincidir(puntoycoma);
        } else if (preanalisis.equals(puntoycoma)) {
            coincidir(puntoycoma);
        } else {
            hayErrores = true;
            System.out.println("Error, se esperaba una declaracion de estado o punto y coma");
        }
    }

    void for_stmt_3() {
        if (preanalisis.equals(negacion) || preanalisis.equals(menos) || preanalisis.equals(verdadero)
                || preanalisis.equals(falso) || preanalisis.equals(identificador)
                || preanalisis.equals(nulo) || preanalisis.equals(this1) || preanalisis.equals(numero)
                || preanalisis.equals(cadena) || preanalisis.equals(parenizq) || preanalisis.equals(super1)) {
            expression();
        }
    }

    void if_stmt() {
        if (preanalisis.equals(if1)) {
            coincidir(if1);
            coincidir(parenizq);
            expression();
            coincidir(parender);
            statement();
            else_statement();
        } else {
            hayErrores = true;
            System.out.println("Error, se esperaba la palabra reservada if");
        }
    }

    void else_statement() {
        if (preanalisis.equals(else1)) {
            coincidir(else1);
            statement();
        }
    }

    void print_stmt() {
        if (preanalisis.equals(print1)) {
            coincidir(print1);
            expression();
            coincidir(puntoycoma);
        } else {
            hayErrores = true;
            System.out.println("Error, se esperaba la palabra reservada print");
        }
    }

    void return_stmt() {
        if (preanalisis.equals(return1)) {
            coincidir(return1);
            return_exp_opc();
            coincidir(puntoycoma);
        } else {
            hayErrores = true;
            System.out.println("Error, se esperaba la palabra reservada return");
        }
    }

    void return_exp_opc() {
        if (preanalisis.equals(negacion) || preanalisis.equals(menos) || preanalisis.equals(verdadero)
                || preanalisis.equals(falso) || preanalisis.equals(identificador)
                || preanalisis.equals(nulo) || preanalisis.equals(this1) || preanalisis.equals(numero)
                || preanalisis.equals(cadena) || preanalisis.equals(parenizq) || preanalisis.equals(super1)) {
            expression();
        }
    }

    void while_stmt() {
        if (preanalisis.equals(while1)) {
            coincidir(while1);
            coincidir(parenizq);
            expression();
            coincidir(parender);
            statement();
        } else {
            hayErrores = true;
            System.out.println("Error, se esperaba la palabra reservada while");
        }
    }

    void block() {
        if (preanalisis.equals(llaveizq)) {
            coincidir(llaveizq);
            block_del();
            coincidir(llaveder);
        } else {
            hayErrores = true;
            System.out.println("Error, se esperaba uso de las llaves");
        }
    }

    void block_del() {
        if (preanalisis.equals(for1) || preanalisis.equals(if1) || preanalisis.equals(print1)
                || preanalisis.equals(return1) || preanalisis.equals(while1) || preanalisis.equals(class1)
                || preanalisis.equals(fun1) || preanalisis.equals(var1) || preanalisis.equals(negacion)
                || preanalisis.equals(menos) || preanalisis.equals(verdadero)
                || preanalisis.equals(falso)
                || preanalisis.equals(nulo) || preanalisis.equals(this1) || preanalisis.equals(numero)
                || preanalisis.equals(cadena)
                || preanalisis.equals(identificador) || preanalisis.equals(parenizq) || preanalisis.equals(super1)
                || preanalisis.equals(llaveizq)) {
            declaration();
            block_del();
        }
    }

    void expression() {
        if (preanalisis.equals(negacion) || preanalisis.equals(menos) || preanalisis.equals(verdadero)
                || preanalisis.equals(falso)
                || preanalisis.equals(nulo) || preanalisis.equals(this1) || preanalisis.equals(numero)
                || preanalisis.equals(cadena)
                || preanalisis.equals(identificador) || preanalisis.equals(parenizq) || preanalisis.equals(super1)) {
            assignment();
        } else {
            hayErrores = true;
            System.out.println("Error, se esperaba una declaracion de estado, un identificador o llaves");
        }
    }

    void assignment() {
        if (preanalisis.equals(negacion) || preanalisis.equals(menos) || preanalisis.equals(verdadero)
                || preanalisis.equals(falso)
                || preanalisis.equals(nulo) || preanalisis.equals(this1) || preanalisis.equals(numero)
                || preanalisis.equals(cadena)
                || preanalisis.equals(identificador) || preanalisis.equals(parenizq) || preanalisis.equals(super1)) {
            logic_or();
            assignment_opc();
        } else {
            hayErrores = true;
            System.out.println("Error");
        }
    }

    void assignment_opc() {
        if (preanalisis.equals(asignacion)) {
            coincidir(asignacion);
            expression();
        }
    }

    void logic_or() {
        if (preanalisis.equals(negacion) || preanalisis.equals(menos) || preanalisis.equals(verdadero)
                || preanalisis.equals(falso)
                || preanalisis.equals(nulo) || preanalisis.equals(this1) || preanalisis.equals(numero)
                || preanalisis.equals(cadena)
                || preanalisis.equals(identificador) || preanalisis.equals(parenizq) || preanalisis.equals(super1)) {
            logic_and();
            logic_or_2();
        } else {
            hayErrores = true;
            System.out.println("Error, se esperaba una expresion de estado");
        }
    }

    void logic_or_2() {
        if (preanalisis.equals(or1)) {
            coincidir(or1);
            logic_and();
            logic_or_2();
        }
    }

    void logic_and() {
        if (preanalisis.equals(negacion) || preanalisis.equals(menos) || preanalisis.equals(verdadero)
                || preanalisis.equals(falso)
                || preanalisis.equals(nulo) || preanalisis.equals(this1) || preanalisis.equals(numero)
                || preanalisis.equals(cadena)
                || preanalisis.equals(identificador) || preanalisis.equals(parenizq) || preanalisis.equals(super1)) {
            equality();
            logic_and_2();
        } else {
            hayErrores = true;
            System.out.println("Error, se esperaba una expresion de estado");
        }
    }

    void logic_and_2() {
        if (preanalisis.equals(and1)) {
            coincidir(and1);
            equality();
            logic_and_2();
        }
    }

    void equality() {
        if (preanalisis.equals(negacion) || preanalisis.equals(menos) || preanalisis.equals(verdadero)
                || preanalisis.equals(falso)
                || preanalisis.equals(nulo) || preanalisis.equals(this1) || preanalisis.equals(numero)
                || preanalisis.equals(cadena)
                || preanalisis.equals(identificador) || preanalisis.equals(parenizq) || preanalisis.equals(super1)) {
            comparison();
            equality_2();
        } else {
            hayErrores = true;
            System.out.println("Error, se esperaba una expresion de estado");
        }
    }

    void equality_2() {
        if (preanalisis.equals(diferenteque)) {
            coincidir(diferenteque);
            comparison();
            equality_2();
        } else if (preanalisis.equals(iguala)) {
            coincidir(iguala);
            comparison();
            equality_2();
        }
    }

    void comparison() {
        if (preanalisis.equals(negacion) || preanalisis.equals(menos) || preanalisis.equals(verdadero)
                || preanalisis.equals(falso)
                || preanalisis.equals(nulo) || preanalisis.equals(this1) || preanalisis.equals(numero)
                || preanalisis.equals(cadena)
                || preanalisis.equals(identificador) || preanalisis.equals(parenizq) || preanalisis.equals(super1)) {
            term();
            comparison_2();
        } else {
            hayErrores = true;
            System.out.println("Error, se esperaba una expresion de estado");
        }
    }

    void comparison_2() {
        if (preanalisis.equals(mayorque)) {
            coincidir(mayorque);
            term();
            comparison_2();
        } else if (preanalisis.equals(mayorigual)) {
            coincidir(mayorigual);
            term();
            comparison_2();
        } else if (preanalisis.equals(menorque)) {
            coincidir(menorque);
            term();
            comparison_2();
        } else if (preanalisis.equals(menorigual)) {
            coincidir(menorigual);
            term();
            comparison_2();
        }
    }

    void term() {
        if (preanalisis.equals(negacion) || preanalisis.equals(menos) || preanalisis.equals(verdadero)
                || preanalisis.equals(falso)
                || preanalisis.equals(nulo) || preanalisis.equals(this1) || preanalisis.equals(numero)
                || preanalisis.equals(cadena)
                || preanalisis.equals(identificador) || preanalisis.equals(parenizq) || preanalisis.equals(super1)) {
            factor();
            term_2();
        } else {
            hayErrores = true;
            System.out.println("Error, se esperaba una expresion de estado");
        }
    }

    void term_2() {
        if (preanalisis.equals(menos)) {
            coincidir(menos);
            factor();
            term_2();
        } else if (preanalisis.equals(mas)) {
            coincidir(mas);
            factor();
            term_2();
        }
    }

    void factor() {
        if (preanalisis.equals(negacion) || preanalisis.equals(menos) || preanalisis.equals(verdadero)
                || preanalisis.equals(falso)
                || preanalisis.equals(nulo) || preanalisis.equals(this1) || preanalisis.equals(numero)
                || preanalisis.equals(cadena)
                || preanalisis.equals(identificador) || preanalisis.equals(parenizq) || preanalisis.equals(super1)) {
            unary();
            factor_2();
        } else {
            hayErrores = true;
            System.out.println("Error, se esperaba una expresion de estado");
        }
    }

    void factor_2() {
        if (preanalisis.equals(div)) {
            coincidir(div);
            unary();
            factor_2();
        } else if (preanalisis.equals(mult)) {
            coincidir(mult);
            unary();
            factor_2();
        }
    }

    void unary() {
        if (preanalisis.equals(negacion)) {
            coincidir(negacion);
            unary();
        } else if (preanalisis.equals(menos)) {
            coincidir(menos);
            unary();
        } else if (preanalisis.equals(verdadero) || preanalisis.equals(falso) || preanalisis.equals(nulo)
                || preanalisis.equals(this1) || preanalisis.equals(numero) || preanalisis.equals(cadena)
                || preanalisis.equals(identificador) || preanalisis.equals(parenizq) || preanalisis.equals(super1)) {
            call();
        } else {
            hayErrores = true;
            System.out.println("Error, se esperaba una expresion de estado");
        }
    }

    void call() {
        if (preanalisis.equals(verdadero) || preanalisis.equals(falso) || preanalisis.equals(nulo)
                || preanalisis.equals(this1) || preanalisis.equals(numero) || preanalisis.equals(cadena)
                || preanalisis.equals(identificador) || preanalisis.equals(parenizq) || preanalisis.equals(super1)) {
            primary();
            call_2();
        } else {
            hayErrores = true;
            System.out.println("Error, se esperaba una expresion de estado");
        }
    }

    void call_2() {
        if (preanalisis.equals(parenizq)) {
            coincidir(parenizq);
            arguments_opc();
            coincidir(parender);
            call_2();
        } else if (preanalisis.equals(punto)) {
            coincidir(punto);
            coincidir(identificador);
            call_2();
        }
    }

    void primary() {
        if (preanalisis.equals(verdadero)) {
            coincidir(verdadero);
        } else if (preanalisis.equals(falso)) {
            coincidir(falso);
        } else if (preanalisis.equals(nulo)) {
            coincidir(nulo);
        } else if (preanalisis.equals(this1)) {
            coincidir(this1);
        } else if (preanalisis.equals(numero)) {
            coincidir(numero);
        } else if (preanalisis.equals(cadena)) {
            coincidir(cadena);
        } else if (preanalisis.equals(identificador)) {
            coincidir(identificador);
        } else if (preanalisis.equals(parenizq)) {
            coincidir(parenizq);
            expression();
            coincidir(parender);
        } else if (preanalisis.equals(super1)) {
            coincidir(super1);
            coincidir(punto);
            coincidir(identificador);
        } else {
            hayErrores = true;
            System.out.println("Error, se esperaba una expresion de estado");
        }
    }

    void function() {
        if (preanalisis.equals(identificador)) {
            coincidir(identificador);
            coincidir(parenizq);
            parameters_opc();
            coincidir(parender);
            block();
        } else {
            hayErrores = true;
            System.out.println("Error, se esperaba un identificador");
        }
    }

    void functions() {
        if (preanalisis.equals(identificador)) {
            function();
            functions();
        }
    }

    void parameters_opc() {
        if (preanalisis.equals(identificador)) {
            parameters();
        }
    }

    void parameters() {
        if (preanalisis.equals(identificador)) {
            coincidir(identificador);
            parameters_2();
        } else {
            hayErrores = true;
            System.out.println("Error, se esperaba un identificador");
        }
    }

    void parameters_2() {
        if (preanalisis.equals(coma)) {
            coincidir(coma);
            coincidir(identificador);
            parameters_2();
        }
    }

    void arguments_opc() {
        if (preanalisis.equals(negacion) || preanalisis.equals(menos) || preanalisis.equals(verdadero)
                || preanalisis.equals(falso)
                || preanalisis.equals(nulo) || preanalisis.equals(this1) || preanalisis.equals(numero)
                || preanalisis.equals(cadena)
                || preanalisis.equals(identificador) || preanalisis.equals(parenizq) || preanalisis.equals(super1)) {
            arguments();
        }
    }

    void arguments() {
        if (preanalisis.equals(negacion) || preanalisis.equals(menos) || preanalisis.equals(verdadero)
                || preanalisis.equals(falso)
                || preanalisis.equals(nulo) || preanalisis.equals(this1) || preanalisis.equals(numero)
                || preanalisis.equals(cadena)
                || preanalisis.equals(identificador) || preanalisis.equals(parenizq) || preanalisis.equals(super1)) {
            expression();
            arguments_2();
        } else {
            hayErrores = true;
            System.out.println("Error, se esperaba una expresion de estado");
        }
    }

    void arguments_2() {
        if (preanalisis.equals(coma)) {
            coincidir(coma);
            expression();
            arguments_2();
        }
    }

    void coincidir(Token t) {
        if (hayErrores) {
            System.exit(65);
        } else if (preanalisis.tipo == t.tipo) {
            i++;
            preanalisis = tokens.get(i);
        } else {
            hayErrores = true;
            System.out.println("Error");

        }
    }
}
