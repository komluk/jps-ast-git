package pl.edu.pjwstk.jps.parser;

import java_cup.runtime.*;
import java.io.IOException;

import java_cup.runtime.Symbol;
import static pl.edu.pjwstk.jps.parser.Symbols.*;
import pl.edu.pjwstk.jps.parser.Symbols;

%%
%{
	private Symbol createToken(int id) {
		return new Symbol(id, yyline, yycolumn);
	}
	private Symbol createToken(int id, Object o) {
		//System.out.println(id + " | " + o.toString());
		return new Symbol(id, yyline, yycolumn, o);
	}
%}

%public
%class Lexer
%cup
%line
%column
%char
%eofval{
	return createToken(EOF);
%eofval}

INTEGER = [0-9]+
BOOLEAN = true|false
IDENTIFIER = [_a-zA-Z][0-9a-zA-Z]*
STRING = [\"][^\"]*[\"]
DOUBLE = [0-9]+\.[0-9]+
LineTerminator = \r|\n|\r\n
WHITESPACE = {LineTerminator} | [ \t]
%%

<YYINITIAL> {
	"+"						{ return createToken(PLUS); }
	"-"						{ return createToken(MINUS); }
	"*"						{ return createToken(MULTIPLY); }
	"/"						{ return createToken(DIVIDE); }
	"%"                     { return createToken(MODULO); }

	"not"|"NOT"|"!"         { return createToken(NOT); }
	"and"|"AND"|"&"|"&&"    { return createToken(AND); }
	"or"|"OR"|"\|\|"|"\|"      { return createToken(OR); }
	"xor"                   { return createToken(XOR); }

	"eq"|"EQ"|"="|"=="      { return createToken(EQ); }

	"<"                     { return createToken(LESS_THAN); }
	"<="                    { return createToken(LESS_EQ_THAN); }
	">"                     { return createToken(MORE_THAN); }
	">="                    { return createToken(MORE_EQ_THAN); }

	","                     { return createToken(COMMA); }
	"sum"|"SUM"             { return createToken(SUM); }
	"count"|"COUNT"         { return createToken(COUNT); }
	"avg"|"AVG"             { return createToken(AVG); }
	"min"|"MIN"             { return createToken(MIN); }
	"max"|"MAX"             { return createToken(MAX); }
	"unique"|"UNIQUE"       { return createToken(UNIQUE); }
	"exists"|"EXISTS"       { return createToken(EXISTS); }
	"minus"|"MINUS"         { return createToken(MINUS_SET); }
	"intersect"|"INTERSECT" { return createToken(INTERSECT); }

	"."                     { return createToken(DOT); }

	"as"|"AS"				{ return createToken(AS); }
	"groupas"|"GROUPAS"|"group as"|"GROUP AS"   { return createToken(GROUP_AS); }

	"union"|"UNION"         { return createToken(UNION); }
	"in"|"IN"         { return createToken(IN); }
	"any"|"ANY"         { return createToken(FOR_ANY); }
	"all"|"ALL"         { return createToken(FOR_ALL); }
	"where"|"WHERE"         { return createToken(WHERE); }
	"join"|"JOIN"         { return createToken(JOIN); }

	"bag"|"BAG"             { return createToken(BAG); }
	"STRUCT"|"struct"       { return createToken(STRUCT); }
	"!="                    { return createToken(NOT_EQ); }

	"("						{ return createToken(LEFT_ROUND_BRACKET); }
	")"						{ return createToken(RIGHT_ROUND_BRACKET); }



    {WHITESPACE} { }
   	{STRING} {return createToken(STRING_LITERAL, yytext().substring(1,yytext().length()-1)) ; }
	{INTEGER} {
		int val;
		try {
			val = Integer.parseInt(yytext());
		}
		catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		return createToken(INTEGER_LITERAL, new Integer(val));
	}
	{DOUBLE} {
		double val;
		try {
			val = Double.parseDouble(yytext());
		}
		catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		return createToken(DOUBLE_LITERAL, new Double(val));
	}
	{BOOLEAN} {
		boolean val;
		try {
			val = Boolean.parseBoolean(yytext());
		}
		catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		return createToken(BOOLEAN_LITERAL, new Boolean(val));
	}
	{IDENTIFIER} {
		return createToken(IDENTIFIER, yytext());
	}
}
