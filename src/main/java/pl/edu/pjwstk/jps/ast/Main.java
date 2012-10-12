package pl.edu.pjwstk.jps.ast;

import edu.pjwstk.jps.ast.IExpression;
import pl.edu.pjwstk.jps.ast.binary.CommaExpression;
import pl.edu.pjwstk.jps.ast.binary.DotExpression;
import pl.edu.pjwstk.jps.ast.binary.EqualsExpression;
import pl.edu.pjwstk.jps.ast.binary.GreaterThanExpression;
import pl.edu.pjwstk.jps.ast.binary.InExpression;
import pl.edu.pjwstk.jps.ast.binary.WhereExpression;
import pl.edu.pjwstk.jps.ast.terminal.IntegerTerminal;
import pl.edu.pjwstk.jps.ast.terminal.NameTerminal;
import pl.edu.pjwstk.jps.ast.terminal.StringTerminal;
import pl.edu.pjwstk.jps.ast.unary.BagExpression;
import pl.edu.pjwstk.jps.ast.unary.CountExpression;

public class Main {
	public static void main(String[] args) {
		System.out.println("Hello world");

		//osoba where (count(imie) > 1)
		IExpression first = new WhereExpression(
			new NameTerminal("osoba"),
			new GreaterThanExpression(
				new CountExpression(
					new NameTerminal("imię")
				),
				new IntegerTerminal(1)
			)
		);

		//firma where (lokalizacja in (bag(„Warszawa”, „Łódź”)))
		IExpression second = new WhereExpression(
			new NameTerminal("firma"),
			new InExpression(
				new NameTerminal("lokalizacja"),
				new BagExpression(
					new CommaExpression(
						new NameTerminal("Warszawa"),
						new NameTerminal("Łódz")
					)
				)
			)
		);

		//bag(1,2) in bag(1,2,3)
		IExpression third = new InExpression(
			new BagExpression(
				new CommaExpression(
					new IntegerTerminal(1),
					new IntegerTerminal(2)
				)
			),
			new BagExpression(
				new CommaExpression(
					new IntegerTerminal(1),
					new IntegerTerminal(2)
				)
			)
		);
	}
}
