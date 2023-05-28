package src.mx.ipn.escom.compiladores;

import java.util.List;

public class Parser {
    private final List<Token> tokens;
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

    public Parser(List<Token> tokens){
        this.tokens = tokens;
    }

    public void parse(){
        i = 0;
        preanalisis = tokens.get(i);
        program();
        
        if(!hayErrores && !preanalisis.equals(finCadena)){
            System.out.println("Error");
        }
        else if(!hayErrores && preanalisis.equals(finCadena)){
            System.out.println("Cadena válida");
        }
    }

    void program()
    {
        declaration();
    }

    void declaration()
    {
        if (preanalisis.equals(class1)) {
            class_decl();
            declaration();   
        }
        if (preanalisis.equals(fun1)) {
            fun_decl();
            declaration();   
        }
        if (preanalisis.equals(var1)) {
            var_decl();
            declaration();   
        }
        if (preanalisis.equals(for1) || preanalisis.equals(if1) || preanalisis.equals(print1) || preanalisis.equals(return1) || preanalisis.equals(while1)) {
            statement();
            declaration();   
        }
    }

    void class_decl()
    {
        if (preanalisis.equals(class1)) {
            coincidir(class1);
            coincidir(identificador);
            class_inher();
            coincidir(llaveizq);
            functions();
            coincidir(llaveder);
        }
        else{
            hayErrores = true;
            System.out.println("Error");
        }
    }

    void class_inher()
    {
        if (preanalisis.equals(menorque)) {
            coincidir(menorque);
            coincidir(identificador);
        }
    }

    void fun_decl()
    {
        if (preanalisis.equals(fun1)) {
            coincidir(fun1);
            function();
        }
        else{
            hayErrores = true;
            System.out.println("Error");
        }
    }

    void var_decl()
    {
        if (hayErrores) {
            
        }
        else{
            hayErrores = true;
            System.out.println("Error");
        }
    }

    void var_init()
    {

    }

    void statement()
    {
        if (preanalisis.equals(if1)) {
            if_stmt();
        }
        if (preanalisis.equals(print1)) {
            print_stmt();
        }
        if (preanalisis.equals(llaveizq)) {
            block();
        }
        else{
            hayErrores = true;
            System.out.println("Error");
        }
    }

    void expr_stmt()
    {

    }

    void for_stmt()
    {

    }

    void for_stmt_1()
    {

    }

    void for_stmt_2()
    {

    }

    void for_stmt_3()
    {

    }

    void if_stmt()
    {
        if (preanalisis.equals(if1)) {
            coincidir(if1);
            coincidir(parenizq);
            expression();
            coincidir(parender);
            statement();
            else_statement();   
        }
        else{
            hayErrores = true;
            System.out.println("Error");
        }
    }

    void else_statement()
    {
        if (preanalisis.equals(else1)) {
            coincidir(else1);
            statement();
        }
    }

    void print_stmt()
    {
        coincidir(print1);
        expression();
        coincidir(puntoycoma);
    }

    void return_stmt()
    {

    }

    void return_exp_opc()
    {

    }

    void while_stmt()
    {

    }

    void block()
    {
        coincidir(llaveizq);
        block_del();
        coincidir(llaveder);
    }

    void block_del()
    {
        if (preanalisis.equals(for1) || preanalisis.equals(if1) || preanalisis.equals(print1) || preanalisis.equals(return1) || preanalisis.equals(while1) || preanalisis.equals(class1)
        || preanalisis.equals(fun1) || preanalisis.equals(var1)) {
            declaration();
            block_del();
        }
    }

    void expression()
    {
        assignment();
    }

    void assignment()
    {
        if (preanalisis.equals(verdadero) || preanalisis.equals(falso) || preanalisis.equals(nulo) || preanalisis.equals(this1) || preanalisis.equals(numero) || preanalisis.equals(cadena) 
        || preanalisis.equals(identificador) || preanalisis.equals(parender) || preanalisis.equals(super1)) {
            call_opc();
            coincidir(identificador);
            coincidir(asignacion);
            assignment();   
        }
    }

    void logic_or()
    {

    }

    void logic_or_2()
    {

    }

    void logic_and()
    {

    }

    void logic_and_2()
    {

    }

    void equality()
    {

    }

    void equality_2()
    {

    }

    void comparison()
    {

    }

    void comparison_2()
    {

    }

    void term()
    {

    }

    void term_2()
    {

    }

    void factor()
    {

    }

    void factor_2()
    {

    }

    void unary()
    {

    }

    void call()
    {
        if (preanalisis.equals(verdadero) || preanalisis.equals(falso) || preanalisis.equals(nulo) || preanalisis.equals(this1) || preanalisis.equals(numero) || preanalisis.equals(cadena) 
        || preanalisis.equals(identificador) || preanalisis.equals(parender) || preanalisis.equals(super1)) {
            call_2();   
        }
    }

    void call_2()
    {
        if (preanalisis.equals(parenizq)) {
            coincidir(parenizq);
            arguments_opc();
            coincidir(parender);
            call_2();
        }
        else if (preanalisis.equals(punto)) {
            coincidir(punto);
            coincidir(identificador);
            call_2();
        }
    }

    void call_opc()
    {
        if (preanalisis.equals(verdadero) || preanalisis.equals(falso) || preanalisis.equals(nulo) || preanalisis.equals(this1) || preanalisis.equals(numero) || preanalisis.equals(cadena) 
        || preanalisis.equals(identificador) || preanalisis.equals(parender) || preanalisis.equals(super1)) {
            call();
            coincidir(punto);   
        }
    }

    void primary()
    {

    }

    void function()
    {
        if (preanalisis.equals(identificador)) {
            coincidir(identificador);
            coincidir(parenizq);
            parameters_opc();
            coincidir(parender);
            block();
        }
        else{
            hayErrores = true;
            System.out.println("Error");
        }
    }

    void functions()
    {
        if (preanalisis.equals(identificador)) {
            function();
            functions();
        }
    }

    void parameters_opc()
    {
        if (preanalisis.equals(identificador)) {
            parameters();
        }
    }

    void parameters()
    {
        if (preanalisis.equals(identificador)) {
            coincidir(identificador);
            parameters_2();
        }
        else{
            hayErrores = true;
            System.out.println("Error");
        }
    }

    void parameters_2()
    {
        if (preanalisis.equals(coma)) {
            coincidir(coma);
            coincidir(identificador);
            parameters_2();
        }
    }

    void arguments_opc()
    {

    }

    void arguments()
    {

    }

    void arguments_2()
    {

    }

    void coincidir(Token t){
        if(hayErrores) return;

        if(preanalisis.tipo == t.tipo){
            i++;
            preanalisis = tokens.get(i);
        }
        else{
            hayErrores = true;
            System.out.println("Error");

        }
    }
}
