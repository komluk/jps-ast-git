package pl.edu.pjwstk.jps.parser;

import java_cup.runtime.Symbol;
import static pl.edu.pjwstk.jps.parser.Symbols.*;

%%
%{
	private Symbol createToken(int id) {
		return new Symbol(id, yyline, yycolumn);
	}
	private Symbol createToken(int id, Object o) {
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
DOUBLE = [0-9]+\.[0-9]+
STRING = [\"][^\"]*[\"]
CHAR = [\'][^\"][\']
LineTerminator = \r|\n|\r\n
WHITESPACE = {LineTerminator} | [ \t]
%%

<YYINITIAL> {
	"+"						{ return createToken(PLUS); }
	"-"						{ return createToken(MINUS); }
	"*"						{ return createToken(MULTIPLY); }
	"/"						{ return createToken(DIVIDE); }
	"%"                     { return createToken(MODULO); }

	"not"                   { return createToken(NOT); }
	"!"                     { return createToken(NOT); }
	"and"                   { return createToken(AND); }
	"&&"                    { return createToken(AND); }
	"&"                     { return createToken(AND); }
	"or"                    { return createToken(OR); }
	"||"                    { return createToken(OR); }
	"|"                     { return createToken(OR); }
	"xor"                   { return createToken(XOR); }

	"eq"                    { return createToken(EQ); }
	"="                     { return createToken(EQ); }
	"=="                    { return createToken(EQ); }

	"<"                     { return createToken(LESS_THAN); }
	"<="                    { return createToken(LESS_EQ_THAN); }
	">"                     { return createToken(MORE_THAN); }
	">="                    { return createToken(MORE_EQ_THAN); }

	","                     { return createToken(COMMA); }
	"sum"                   { return createToken(SUM); }
	"count"                 { return createToken(COUNT); }
	"avg"                   { return createToken(AVG); }
	"min"                   { return createToken(MIN); }
	"max"                   { return createToken(MAX); }

	"!="                    { return createToken(NOT_EQ); }

	"("						{ return createToken(LEFT_ROUND_BRACKET); }
	")"						{ return createToken(RIGHT_ROUND_BRACKET); }


	{WHITESPACE} { }
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
﻿   {IDENTIFIER} {
	    return createToken(IDENTIFIER, yytext());
﻿   }
﻿  {STRING} {
		return createToken(STRING_LITERAL, yytext().substring(1,yytext().length()-1));
	}
}
