package pl.edu.pjwstk.jps.interpreter;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import edu.pjwstk.jps.ast.IExpression;
import edu.pjwstk.jps.ast.auxname.IAsExpression;
import edu.pjwstk.jps.ast.auxname.IGroupAsExpression;
import edu.pjwstk.jps.ast.binary.*;
import edu.pjwstk.jps.ast.terminal.*;
import edu.pjwstk.jps.ast.unary.*;
import edu.pjwstk.jps.datastore.ISBAStore;
import edu.pjwstk.jps.interpreter.envs.IInterpreter;
import edu.pjwstk.jps.result.*;
import pl.edu.pjwstk.jps.interpreter.envs.ENVS;
import pl.edu.pjwstk.jps.qres.interpreter.QResStack;
import pl.edu.pjwstk.jps.result.*;
import pl.edu.pjwstk.jps.result.util.CartesianProduct;

import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import static pl.edu.pjwstk.jps.interpreter.InterpreterUtils.deRefrence;
import static pl.edu.pjwstk.jps.interpreter.InterpreterUtils.toIterable;
import static pl.edu.pjwstk.jps.interpreter.InterpreterUtils.toSingleResult;
import static pl.edu.pjwstk.jps.interpreter.InterpreterUtils.equals;

/**
 * jeszcze w życiu takiego potwora nie zrobiłem....
 */
public class Interpreter implements IInterpreter {
	private final QResStack qres;
	private final ENVS envs;
	private final ISBAStore store;
	
	public Interpreter(ISBAStore store) {
		this(store, new QResStack(), new ENVS());

		init();
	}

	protected Interpreter(ISBAStore store, QResStack stack, ENVS envs) {
		this.store = store;
		this.envs = envs;
		this.qres = stack;
	}

	protected void init() {
		envs.init(store.getEntryOID(), store);;
	}

	/**
	 * przypisanie nazwy pomocniczej elementom kolekcji.
	 * Ewaluacja:
	 * 	1. Wykonaj podzapytanie.
	 * 	2. Podnies rezultat z QRES.
	 * 	3. Kazdy element otrzymanej kolekcji zastap binderem o nazwie podanej jako parametr operatora
	 * 		i wartosci bedacej zastepowanym rezultatem.
	 * 	4. Umiesc wynikowa kolekcje na QRES.
	 * @param expr
	 */
	@Override	//DONE
	public void visitAsExpression(IAsExpression expr) {
		IAbstractQueryResult res = new ExpressionExecutor(expr.getInnerExpression()).getFirst();
		IBagResult result = new BagResult();

		for(ISingleResult singleResult : toIterable(res)) {
			result.getElements().add(new BinderResult(expr.getAuxiliaryName(), singleResult));
		}

		qres.push(result);
	}

	private class ExpressionExecutor {
		public final IExpression [] expressions;
		private final IAbstractQueryResult [] queryResults;

		public ExpressionExecutor(IExpression ... expressions) {
			this.expressions = expressions;
			queryResults = new IAbstractQueryResult[expressions.length];

			accept();		//nie jest to za ładne ale co tam
		}

		private ExpressionExecutor accept() {
			for(IExpression ex : expressions) {
				ex.accept(Interpreter.this);
			}

			for(int i = expressions.length - 1; i >= 0; i--) {
				queryResults[i] = qres.pop();
			}
//			for(int i = 0 ; i < expressions.length; i++) {
//				queryResults[i] = qres.pop();
//			}

			return this;
		}

		public IAbstractQueryResult [] getQueryResults() {
			return queryResults;
		}

		public IAbstractQueryResult getFirst() {
			return get(0);
		}

		public IAbstractQueryResult get(int index) {
			return queryResults[index];
		}
	}

	/**
	 * Przypisanie nazwy pomocniczej calemu rezultatowi.
	 * Ewaluacja:
	 * 	1. Wykonaj podzapytanie.
	 * 	2. Podnies jego rezultat z QRES.
	 *	3. Utworz binder o nazwie podanej jako parametr operatora oraz wartosci otrzymanej w poprzednim kroku.
	 *	4. Poloz go na QRES.
	 */
	@Override	//DONE
	public void visitGroupAsExpression(IGroupAsExpression expr) {
		IAbstractQueryResult res = new ExpressionExecutor(expr.getInnerExpression()).getFirst();

		IBinderResult binderResult = new BinderResult(expr.getAuxiliaryName(), res);
		qres.push(binderResult);
	}

	/**
	 * kwantyfikator ogolny (niealgebraiczny).
	 * Ewaluacja:
	 * 	podobnie jak w . (kropka).
	 *	1. Zainicjalizuj pusty bag (bedziemy sie do niego odnosic jako eres).
	 *	2. Wykonaj lewe podzapytanie.
	 *	3. Podnies jego rezultat ze stosu QRES.
	 *	4. Dla kazdego elementu e z rezultatu otrzymanego w poprzednim kroku wykonaj:
	 *		4.1. Otworz nowa sekcja na ENVS.
	 *		4.2. Wykonaj operacje nested(e).
	 *		4.3. Wykonaj prawe podzapytanie.
	 *		4.4. Podnies jego rezultat z QRES.
	 * 		4.5. Jesli rezultat prawego wyrazenia nie jest pojedyncza wartoscia logiczna, podnies blad czasu wykonania.
	 *		4.6. Jesli rezultat prawego wyrazenia jest false, umiesc wartosc false na QRES i przerwij ewaluacje operatora.
	 * @param expr
	 */
	@Override	//DONE
	public void visitAllExpression(IForAllExpression expr) {
		IAbstractQueryResult left = new ExpressionExecutor(expr.getLeftExpression()).getFirst();//2,3

		for (ISingleResult element : toIterable(left)) {					//4
			envs.nested(element, store);										//4.1, 4.2

			IAbstractQueryResult rightRes = new ExpressionExecutor(expr.getRightExpression()).getFirst();	//4.3, 4.4

			IAbstractQueryResult single = toSingleResult(rightRes);
			if(single instanceof IBooleanResult) {
				IBooleanResult booleanResult = (IBooleanResult) single;
				if(!booleanResult.getValue().booleanValue()) {
					qres.push(new BooleanResult(false));
					return;	//FIXME brzydal										//4.6
				}
			} else {
				throw new IllegalStateException("Result must be boolean");		////4.5
			}
			envs.pop();
		}

		qres.push(new BooleanResult(true));
	}

	/**
	 * tradycyjne binarne operatory arytmetyczne, logiczne i porownania.
	 * Ewaluacja:
	 * 	1. Wykonaj oba podzapytania.
	 * 	2. Podnies dwa elementy z QRES.
	 *	3. Sprawdz czy sa to pojedyncze wartosci. Jesli nie, podnies blad czasu wykonania.
	 *	4. Jesli ktorykolwiek z rezultatow jest referencja, wykonaj dereferencje.
	 *	5. Sprawdz typy danych obu razultatow. Jesli operator nie moze byc zastosowany do takich wartosci, podnies blad czasu wykonania.
	 *	6. Wykonaj operacje zwiazana z operatorem.
	 *	7. Umiesc rezultat na QRES.
	 * @param expr
	 */
	@Override	//DONE
	public void visitAndExpression(IAndExpression expr) {
		ExpressionExecutor executor = new ExpressionExecutor(expr.getLeftExpression(), expr.getRightExpression());

		IAbstractQueryResult leftRes = executor.get(0);
		IAbstractQueryResult rightRes = executor.get(1);

		leftRes = toSingleResult(leftRes);		//3
		leftRes = deRefrence(leftRes, store);	//4
		rightRes = toSingleResult(rightRes);	//3
		rightRes = deRefrence(rightRes, store);//4

		if(leftRes instanceof IBooleanResult && rightRes instanceof IBooleanResult) {
			IBooleanResult leftBoolean = (IBooleanResult) leftRes;
			IBooleanResult rightBoolean = (IBooleanResult) rightRes;

			qres.push(new BooleanResult(leftBoolean.getValue().booleanValue() && rightBoolean.getValue().booleanValue()));

			//TODO można dorobić pornównanie z pojedyńczą wartością liczbową (1-true, 0-false)
		} else {
			throw new IllegalStateException("Invlaid results types, left = ["+leftRes.getClass() +"] right = ["+rightRes.getClass() + "]");
		}
	}

	/**
	 * kwantyfikator szczegolowy (niealgebraiczny).
	 * Ewaluacja:
	 * podobnie jak w . (kropka).
	 *	1. Zainicjalizuj pusty bag (bedziemy sie do niego odnosic jako eres).
	 *	2. Wykonaj lewe podzapytanie.
	 *	3. Podnies jego rezultat ze stosu QRES.
	 *	4. Dla kazdego elementu e z rezultatu otrzymanego w poprzednim kroku wykonaj:
	 *		4.1. Otworz nowa sekcja na ENVS.
	 *		4.2. Wykonaj operacje nested(e).
	 *		4.3. Wykonaj prawe podzapytanie.
	 *		4.4. Podnies jego rezultat z QRES.
	 * 		4.5. Jesli rezultatem prawego wyrazenia nie jest pojedyncza wartosc logiczna, podnires blad czasu wykonania.
	 * 		4.6. Jesli rezultatem prawego wyrazenia jest true, umiesc true na QRES i przerwij ewaluacje operatora.
	 * @param expr
	 */
	@Override	//DONE
	public void visitAnyExpression(IForAnyExpression expr) {
		IAbstractQueryResult left = new ExpressionExecutor(expr.getLeftExpression()).getFirst();				//2,3

		for (ISingleResult element : toIterable(left)) {					//4
			envs.nested(element, store);										//4.1, 4.2

			IAbstractQueryResult rightRes = new ExpressionExecutor(expr.getRightExpression()).getFirst();	//4.3, 4.4

			IAbstractQueryResult single = toSingleResult(rightRes);
			if(single instanceof IBooleanResult) {
				IBooleanResult booleanResult = (IBooleanResult) single;
				if(booleanResult.getValue().booleanValue()) {
					qres.push(new BooleanResult(true));
					return;	//FIXME brzydal										//4.6
				}
			} else {
				throw new IllegalStateException("Result must be boolean");		////4.5
			}
			envs.pop();
		}

		qres.push(new BooleanResult(false));
	}

	@Override	//DONE??
	public void visitCloseByExpression(ICloseByExpression expr) {
		IBagResult visitResult = new BagResult();

		expr.getLeftExpression().accept(this);
		IAbstractQueryResult leftQueryResult = qres.pop();
		Iterables.addAll(visitResult.getElements(), toIterable(leftQueryResult));

		List<IAbstractQueryResult> tmpList = Lists.newArrayList();
		tmpList.addAll(visitResult.getElements());
		ListIterator<IAbstractQueryResult> tmpIt = tmpList.listIterator();

		while(tmpIt.hasNext()) {
			IAbstractQueryResult element = tmpIt.next();
			element = deRefrence(element, store);
			envs.nested(element, store);

			expr.getRightExpression().accept(this);
			IAbstractQueryResult right = qres.pop();

			Iterable<ISingleResult> rightBag = toIterable(right);
			Iterables.addAll(visitResult.getElements(), rightBag);

			for(ISingleResult singleResult : rightBag) {
				tmpIt.add(singleResult);
			}

			envs.pop();
		}

		qres.push(visitResult);
	}

	/**
	 * 1. Zainicjalizuj pusty bag (bedziemy odnosic sie do niego jako eres).
	 * 2. Wykonaj oba podwyrazenia.
	 * 3 Podnies jeden element z QRES (bedziemy odnosic sie do niego jako e2res).
	 * 4. Podnies jeden element z QRES (bedziemy odnosic sie do niego jako e1res).
	 * 5. Dla kazdego elementu e1 z e1res wykonaj:
	 *  5.1 Dla kazdego elementu e2 z e2res wykonaj:
	 *   5.1.1 utworz struct { e1, e2 }. Jesli e1 i/lub e2 jest struktura, wez jej pola.
	 *   5.1.2 Dodaj strukture do eres.
	 * 6. Umiesc eres na QRES.
	 */
	@Override	//DONE
	public void visitCommaExpression(ICommaExpression expr) {
		ExpressionExecutor executor = new ExpressionExecutor(expr.getLeftExpression(), expr.getRightExpression());

		IAbstractQueryResult left = executor.get(0);
		IAbstractQueryResult right = executor.get(1);

		qres.push(new CartesianProduct(left, right).getResult());
	}

	/**
	 * tradycyjne binarne operatory arytmetyczne, logiczne i porownania.
	 * Ewaluacja:
	 * 	1.Wykonaj oba podzapytania.
	 * 	2. Podnies dwa elementy z QRES.
	 *	3. Sprawdz czy sa to pojedyncze wartosci. Jesli nie, podnies blad czasu wykonania.
	 *	4. Jesli ktorykolwiek z rezultatow jest referencja, wykonaj dereferencje.
	 *	5. Sprawdz typy danych obu razultatow. Jesli operator nie moze byc zastosowany do takich wartosci, podnies blad czasu wykonania.
	 *	6. Wykonaj operacje zwiazana z operatorem.
	 *	7. Umiesc rezultat na QRES.
	 * @param expr
	 */
	@Override	//DONE
	public void visitDivideExpression(IDivideExpression expr) {
		ExpressionExecutor executor = new ExpressionExecutor(expr.getLeftExpression(), expr.getRightExpression());

		IAbstractQueryResult left = executor.get(0);
		IAbstractQueryResult right = executor.get(1);

		qres.push(new NumberCalculator(store, left, right) {
			@Override
			protected double calculate(double firstNumber, double secondNumber) {
				return firstNumber / secondNumber;
			}
		}.calculate());
	}

	/**
	 * projekcja/nawigacja (niealgebraiczny).
	 * Ewaluacja:
	 * 	1. Zainicjalizuj pusty bag (bedziemy sie do niego odnosic jako eres).
	 * 	2. Wykonaj lewe podzapytanie.
	 * 	3. Podnies jego rezultat ze stosu QRES.
	 * 	4. Dla kazdego elementu e z rezultatu otrzymanego w poprzednim kroku wykonaj:
	 * 		4.1. Otworz nowa sekcja na ENVS.
	 * 		4.2. Wykonaj operacje nested(e).
	 * 		4.3. Wykonaj prawe podzapytanie.
	 * 		4.4. Podnies jego rezultat z QRES.
	 * 		4.5. Dodaj go do eres.
	 * 	5. Poloz eres na QRES.
	 * @param expr
	 */
	@Override	//DONE
	public void visitDotExpression(IDotExpression expr) {
		BagResult dotRes = new BagResult();								//1

		IAbstractQueryResult left = eval(expr.getLeftExpression());		//2,3
		for (ISingleResult element : toIterable(left)) {			//4
			envs.nested(element, store);								//4.1, 4.2
			expr.getRightExpression().accept(this);
			IAbstractQueryResult rightRes = eval(expr.getRightExpression());	//4.3, 4.4
			dotRes.add(rightRes);										//4.5
			envs.pop();
		}
		
		qres.push(dotRes);												//5
	}

	/**
	 * tradycyjne binarne operatory arytmetyczne, logiczne i porownania.
	 * Ewaluacja:
	 * 	1.Wykonaj oba podzapytania.
	 * 	2. Podnies dwa elementy z QRES.
	 *	3. Sprawdz czy sa to pojedyncze wartosci. Jesli nie, podnies blad czasu wykonania.
	 *	4. Jesli ktorykolwiek z rezultatow jest referencja, wykonaj dereferencje.
	 *	5. Sprawdz typy danych obu razultatow. Jesli operator nie moze byc zastosowany do takich wartosci, podnies blad
	 *		czasu wykonania.
	 *	6. Wykonaj operacje zwiazana z operatorem.
	 *	7. Umiesc rezultat na QRES.
	 */
	@Override	//DONE
	public void visitEqualsExpression(IEqualsExpression expr) {
		qres.push(new BooleanResult(InterpreterUtils.equals(expr, this, qres, store)));
	}

	/**
	 * tradycyjne binarne operatory arytmetyczne, logiczne i porownania.
	 * Ewaluacja:
	 * 	1.Wykonaj oba podzapytania.
	 * 	2. Podnies dwa elementy z QRES.
	 *	3. Sprawdz czy sa to pojedyncze wartosci. Jesli nie, podnies blad czasu wykonania.
	 *	4. Jesli ktorykolwiek z rezultatow jest referencja, wykonaj dereferencje.
	 *	5. Sprawdz typy danych obu razultatow. Jesli operator nie moze byc zastosowany do takich wartosci, podnies blad
	 *		czasu wykonania.
	 *	6. Wykonaj operacje zwiazana z operatorem.
	 *	7. Umiesc rezultat na QRES.
	 */
	@Override	//DONE
	public void visitNotEqualsExpression(INotEqualsExpression expr) {
		qres.push(new BooleanResult(!InterpreterUtils.equals(expr, this, qres, store)));
	}

	/**
	 * tradycyjne binarne operatory arytmetyczne, logiczne i porownania.
	 * Ewaluacja:
	 * 	1.Wykonaj oba podzapytania.
	 * 	2. Podnies dwa elementy z QRES.
	 * 	3. Sprawdz czy sa to pojedyncze wartosci. Jesli nie, podnies blad czasu wykonania.
	 *	4. Jesli ktorykolwiek z rezultatow jest referencja, wykonaj dereferencje.
	 *	5. Sprawdz typy danych obu razultatow. Jesli operator nie moze byc zastosowany do
	 *		takich wartosci, podnies blad czasu wykonania.
	 *	6. Wykonaj operacje zwiazana z operatorem.
	 *	7. Umiesc rezultat na QRES.
	 */
	@Override	//DONE
	public void visitGreaterThanExpression(IGreaterThanExpression expr) {
		ExpressionExecutor executor = new ExpressionExecutor(expr.getLeftExpression(), expr.getRightExpression());

		IAbstractQueryResult left = executor.get(0);
		IAbstractQueryResult right = executor.get(1);

		qres.push(new NumberBooleanCalculator(store, left, right) {
			@Override
			protected boolean calculate(double firstNumber, double secondNumber) {
				return firstNumber > secondNumber;
			}
		}.calculate());
	}

	/**
	 * tradycyjne binarne operatory arytmetyczne, logiczne i porownania.
	 * Ewaluacja:
	 * 	1.Wykonaj oba podzapytania.
	 * 	2. Podnies dwa elementy z QRES.
	 * 	3. Sprawdz czy sa to pojedyncze wartosci. Jesli nie, podnies blad czasu wykonania.
	 *	4. Jesli ktorykolwiek z rezultatow jest referencja, wykonaj dereferencje.
	 *	5. Sprawdz typy danych obu razultatow. Jesli operator nie moze byc zastosowany do
	 *		takich wartosci, podnies blad czasu wykonania.
	 *	6. Wykonaj operacje zwiazana z operatorem.
	 *	7. Umiesc rezultat na QRES.
	 */
	@Override	//DONE
	public void visitGreaterOrEqualThanExpression(IGreaterOrEqualThanExpression expr) {
		ExpressionExecutor executor = new ExpressionExecutor(expr.getLeftExpression(), expr.getRightExpression());

		IAbstractQueryResult left = executor.get(0);
		IAbstractQueryResult right = executor.get(1);

		qres.push(new NumberBooleanCalculator(store, left, right) {
			@Override
			protected boolean calculate(double firstNumber, double secondNumber) {
				return firstNumber >= secondNumber;
			}
		}.calculate());
	}

	/**
	 * Ewaluacja:
	 * 	1. Zainicjalizuj pusty bag (bedziemy odnosic sie do niego jaki eres).
	 * 	2. Wykonaj oba podwyrazenia.
	 * 	3. Podnies dwa elementy z QRES.
	 * 	4. Dodaj elementy które się zagadzaja
	 * 	5. Umiesc eres na QRES.
	 * @param expr
	 */
	@Override	//DONE??
	public void visitInExpression(IInExpression expr) {
		IBagResult result = new BagResult();		//1

		ExpressionExecutor executor = new ExpressionExecutor(expr.getLeftExpression(), expr.getRightExpression());		//2

		IAbstractQueryResult left = executor.get(0);		//3
		IAbstractQueryResult right = executor.get(1);		//3

		for(ISingleResult res : toIterable(left)) {
			for(ISingleResult inRes : toIterable(right)) {
				if(res.equals(inRes)) {
					result.getElements().add(res);
					break;
				}
			}
		}

		qres.push(result);							//5
	}

	/**
	 * czesc wspolna zbiorow.
	 * Ewaluacja:
	 * 	1. Zainicjalizuj pusty bag (bedziemy odnosic sie do niego jaki eres).
	 * 	2. Wykonaj oba podwyrazenia.
	 * 	3. Podnies dwa elementy z QRES.
	 *	4. wykonauj sume zbiorow
	 *	5. Umiesc eres na QRES.
	 * @param expr
	 */
	@Override	//DONE
	public void visitIntersectExpression(IIntersectExpression expr) {
		IBagResult result = new BagResult();		//1

		ExpressionExecutor executor = new ExpressionExecutor(expr.getLeftExpression(), expr.getRightExpression());	//2

		IAbstractQueryResult left = executor.get(0);		//3
		IAbstractQueryResult right = executor.get(1);		//3

		left = deRefrence(left, store);
		right = deRefrence(right, store);

		for(ISingleResult res1 : toIterable(left)) {			//4
			for(ISingleResult res2 : toIterable(right)) {
				if(res1.equals(res2)) {
					result.getElements().add(res1);
				}
			}
		}

		qres.push(result);							//5
	}

	/**
	 * zlaczenie zalezne (niealgebraiczny).
	 * Ewaluacja:
	 * 	1. Zainicjalizuj pusty bag (bedziemy sie do niego odnosic jako eres).
	 * 	2. Wykonaj lewe podzapytanie.
	 * 	3. Podnies jego rezultat ze stosu QRES.
	 * 	4. Dla kazdego elementu e z rezultatu otrzymanego w poprzednim kroku wykonaj:
	 * 		4.1. Otworz nowa sekcja na ENVS.
	 * 		4.2. Wykonaj operacje nested(e).
	 * 		4.3. Wykonaj prawe podzapytanie.
	 * 		4.4. Podnies jego rezultat z QRES.
	 * 		4.5. Zamiast dodawania rezultatu do eres, wykonaj
	 * 			iloczyn kartezjanski e z rezultatem wykonania prawego podzapytania.
	 * 		4.6. Dodaj otrzymana strukture do eres.
	 * 	5. Poloz eres na QRES.
	 * @param expr
	 */
	@Override	//DONE
	public void visitJoinExpression(IJoinExpression expr) {
		BagResult dotRes = new BagResult();								//1

		IAbstractQueryResult left = new ExpressionExecutor(expr.getLeftExpression()).getFirst();		//2,3
		for (ISingleResult element : toIterable(left)) {						//4
			envs.nested(element, store); 										//4.1, 4.2

			IAbstractQueryResult rightRes = new ExpressionExecutor(expr.getRightExpression()).getFirst();	//4.3, 4.4
			IBagResult cartesian = new CartesianProduct(element, rightRes).getResult();	//4.5
			dotRes.add(cartesian);										//4.6
			envs.pop();
		}

		qres.push(dotRes);												//5
	}

	/**
	 * tradycyjne binarne operatory arytmetyczne, logiczne i porownania.
	 * Ewaluacja:
	 * 	1.Wykonaj oba podzapytania.
	 * 	2. Podnies dwa elementy z QRES.
	 * 	3. Sprawdz czy sa to pojedyncze wartosci. Jesli nie, podnies blad czasu wykonania.
	 *	4. Jesli ktorykolwiek z rezultatow jest referencja, wykonaj dereferencje.
	 *	5. Sprawdz typy danych obu razultatow. Jesli operator nie moze byc zastosowany do
	 *		takich wartosci, podnies blad czasu wykonania.
	 *	6. Wykonaj operacje zwiazana z operatorem.
	 *	7. Umiesc rezultat na QRES.
	 */
	@Override	//DONE
	public void visitLessOrEqualThanExpression(ILessOrEqualThanExpression expr) {
		ExpressionExecutor executor = new ExpressionExecutor(expr.getLeftExpression(), expr.getRightExpression());

		IAbstractQueryResult left = executor.get(0);
		IAbstractQueryResult right = executor.get(1);

		qres.push(new NumberBooleanCalculator(store, left, right) {
			@Override
			protected boolean calculate(double firstNumber, double secondNumber) {
				return firstNumber <= secondNumber;
			}
		}.calculate());
	}

	/**
	 * tradycyjne binarne operatory arytmetyczne, logiczne i porownania.
	 * Ewaluacja:
	 * 	1.Wykonaj oba podzapytania.
	 * 	2. Podnies dwa elementy z QRES.
	 * 	3. Sprawdz czy sa to pojedyncze wartosci. Jesli nie, podnies blad czasu wykonania.
	 *	4. Jesli ktorykolwiek z rezultatow jest referencja, wykonaj dereferencje.
	 *	5. Sprawdz typy danych obu razultatow. Jesli operator nie moze byc zastosowany do
	 *		takich wartosci, podnies blad czasu wykonania.
	 *	6. Wykonaj operacje zwiazana z operatorem.
	 *	7. Umiesc rezultat na QRES.
	 */
	@Override	//DONE
	public void visitLessThanExpression(ILessThanExpression expr) {
		ExpressionExecutor executor = new ExpressionExecutor(expr.getLeftExpression(), expr.getRightExpression());

		IAbstractQueryResult left = executor.get(0);
		IAbstractQueryResult right = executor.get(1);

		qres.push(new NumberBooleanCalculator(store, left, right) {
			@Override
			protected boolean calculate(double firstNumber, double secondNumber) {
				return firstNumber < secondNumber;
			}
		}.calculate());
	}

	/**
	 * tradycyjne binarne operatory arytmetyczne, logiczne i porownania.
	 * Ewaluacja:
	 * 	1.Wykonaj oba podzapytania.
	 * 	2. Podnies dwa elementy z QRES.
	 *	3. Sprawdz czy sa to pojedyncze wartosci. Jesli nie, podnies blad czasu wykonania.
	 *	4. Jesli ktorykolwiek z rezultatow jest referencja, wykonaj dereferencje.
	 *	5. Sprawdz typy danych obu razultatow. Jesli operator nie moze byc zastosowany do takich wartosci, podnies blad czasu wykonania.
	 *	6. Wykonaj operacje zwiazana z operatorem.
	 *	7. Umiesc rezultat na QRES.
	 * @param expr
	 */
	@Override	//DONE
	public void visitMinusExpression(IMinusExpression expr) {
		ExpressionExecutor executor = new ExpressionExecutor(expr.getLeftExpression(), expr.getRightExpression());

		IAbstractQueryResult left = executor.get(0);
		IAbstractQueryResult right = executor.get(1);

		qres.push(new NumberCalculator(store, left, right) {
			@Override
			protected double calculate(double firstNumber, double secondNumber) {
				return firstNumber - secondNumber;
			}
		}.calculate());
	}

	/**
	 * roznica zbiorow.
	 * Ewaluacja:
	 * 	1. Zainicjalizuj pusty bag (bedziemy odnosic sie do niego jaki eres).
	 * 	2. Wykonaj oba podwyrazenia.
	 * 	3. Podnies dwa elementy z QRES.
	 *	4. wykonauj roznice zbiorow
	 *	5. Umiesc eres na QRES.
	 * @param expr
	 */
	@Override	//DONE
	public void visitMinusSetExpression(IMinusSetExpression expr) {
		IBagResult result = new BagResult();		//1

		ExpressionExecutor executor = new ExpressionExecutor(expr.getLeftExpression(), expr.getRightExpression());	//2

		IAbstractQueryResult left = executor.get(0);	//3
		IAbstractQueryResult right = executor.get(1);	//3

		left = deRefrence(left, store);
		right = deRefrence(right, store);

		for(ISingleResult res1 : toIterable(left)) {			//4
			boolean canAdd = true;
			for(ISingleResult res2 : toIterable(right)) {
				if(res1.equals(res2)) {
					canAdd = false;
					break;
				}
			}

			if(canAdd) {
				result.getElements().add(res1);
			}
		}

		qres.push(result);							//5
	}

	/**
	 * tradycyjne binarne operatory arytmetyczne, logiczne i porownania.
	 * Ewaluacja:
	 * 	1.Wykonaj oba podzapytania.
	 * 	2. Podnies dwa elementy z QRES.
	 *	3. Sprawdz czy sa to pojedyncze wartosci. Jesli nie, podnies blad czasu wykonania.
	 *	4. Jesli ktorykolwiek z rezultatow jest referencja, wykonaj dereferencje.
	 *	5. Sprawdz typy danych obu razultatow. Jesli operator nie moze byc zastosowany do takich wartosci, podnies blad czasu wykonania.
	 *	6. Wykonaj operacje zwiazana z operatorem.
	 *	7. Umiesc rezultat na QRES.
	 * @param expr
	 */
	@Override	//DONE
	public void visitModuloExpression(IModuloExpression expr) {
		ExpressionExecutor executor = new ExpressionExecutor(expr.getLeftExpression(), expr.getRightExpression());

		IAbstractQueryResult left = executor.get(0);
		IAbstractQueryResult right = executor.get(1);

		qres.push(new NumberCalculator(store, left, right) {
			@Override
			protected double calculate(double firstNumber, double secondNumber) {
				return firstNumber % secondNumber;
			}
		}.calculate());
	}

	/**
	 * tradycyjne binarne operatory arytmetyczne, logiczne i porownania.
	 * Ewaluacja:
	 * 	1.Wykonaj oba podzapytania.
	 * 	2. Podnies dwa elementy z QRES.
	 *	3. Sprawdz czy sa to pojedyncze wartosci. Jesli nie, podnies blad czasu wykonania.
	 *	4. Jesli ktorykolwiek z rezultatow jest referencja, wykonaj dereferencje.
	 *	5. Sprawdz typy danych obu razultatow. Jesli operator nie moze byc zastosowany do takich wartosci, podnies blad czasu wykonania.
	 *	6. Wykonaj operacje zwiazana z operatorem.
	 *	7. Umiesc rezultat na QRES.
	 * @param expr
	 */
	@Override	//DONE
	public void visitMultiplyExpression(IMultiplyExpression expr) {
		ExpressionExecutor executor = new ExpressionExecutor(expr.getLeftExpression(), expr.getRightExpression());

		IAbstractQueryResult left = executor.get(0);
		IAbstractQueryResult right = executor.get(1);

		qres.push(new NumberCalculator(store, left, right) {
			@Override
			protected double calculate(double firstNumber, double secondNumber) {
				return firstNumber * secondNumber;
			}
		}.calculate());
	}

	/**
	 * sortowanie (niealgebraiczny).
	 * Ewaluacja:
	 * 	1. Wykonaj operacje join.
	 * 	2. Podnies wynik z QRES.
	 * 	3. Posortuj otrzymane struktury wg drugiego pola kazdej z tych struktur, pozniej trzeciego, czwartego, etc.
	 * 	3. Usun wszystkie z wyjatkiem pierwszego pola otrzymanych struktur.
	 * 	4. Odloz kolekcje struktur na QRES.
	 * @param expr
	 */
	@Override	//TODO
	public void visitOrderByExpression(IOrderByExpression expr) {
//		expr.getLeftExpression().accept(this);
//		IBagResult leftBag = toBag(qres.pop());
//
//		expr.getRightExpression().accept(this);
//		IBagResult rightBag = toBag(qres.pop());
//
//		List<String> sortOrders = Lists.newArrayList();
//		for(ISingleResult orderBy : rightBag.getElements()) {
//			if(orderBy instanceof StringResult) {
//				sortOrders.add(((StringResult) orderBy).getValue());
//			} else {
//				throw new IllegalStateException("invalid second argument of order by was: " + orderBy.getClass());
//			}
//		}
//
		//i tu dalej cuuddaaa
		throw new UnsupportedOperationException("Not implemented yet");
	}

	@Override	//DONE
	public void visitOrExpression(IOrExpression expr) {
		ExpressionExecutor executor = new ExpressionExecutor(expr.getLeftExpression(), expr.getRightExpression());

		IAbstractQueryResult left = executor.get(0);
		IAbstractQueryResult right = executor.get(1);

		left = toSingleResult(left);
		left = deRefrence(left, store);

		right = toSingleResult(right);
		right = deRefrence(right, store);

		if(left instanceof IBooleanResult && right instanceof IBooleanResult) {
			qres.push(new BooleanResult(((IBooleanResult) left).getValue().booleanValue() || ((IBooleanResult) right).getValue().booleanValue()));
		} else {
			throw new IllegalStateException("Or expression can not be applied to: " + left.getClass() + " and " + right.getClass());
		}
	}

	/**
	 * tradycyjne binarne operatory arytmetyczne, logiczne i porownania.
	 * Ewaluacja:
	 * 	1.Wykonaj oba podzapytania.
	 * 	2. Podnies dwa elementy z QRES.
	 *	3. Sprawdz czy sa to pojedyncze wartosci. Jesli nie, podnies blad czasu wykonania.
	 *	4. Jesli ktorykolwiek z rezultatow jest referencja, wykonaj dereferencje.
	 *	5. Sprawdz typy danych obu razultatow. Jesli operator nie moze byc zastosowany do takich wartosci, podnies blad czasu wykonania.
	 *	6. Wykonaj operacje zwiazana z operatorem.
	 *	7. Umiesc rezultat na QRES.
	 * @param expr
	 */
	@Override	//DONE
	public void visitPlusExpression(IPlusExpression expr) {
		expr.getLeftExpression().accept(this);
		expr.getRightExpression().accept(this);

		IAbstractQueryResult right = qres.pop();
		IAbstractQueryResult left = qres.pop();

		qres.push(new NumberCalculator(store, left, right) {
			@Override
			protected double calculate(double firstNumber, double secondNumber) {
				return firstNumber + secondNumber;
			}
		}.calculate());
	}

	/**
	 * suma zbiorow.
	 * Ewaluacja:
	 * 	1. Zainicjalizuj pusty bag (bedziemy odnosic sie do niego jaki eres).
	 * 	2. Wykonaj oba podwyrazenia.
	 * 	3. Podnies dwa elementy z QRES.
	 *	4. Dodaj wszystkie elementy z obu rezultatow do eres.
	 *	5. Umiesc eres na QRES.
	 * @param expr
	 */
	@Override	//DONE
	public void visitUnionExpression(IUnionExpression expr) {
		IBagResult result = new BagResult();		//1

		ExpressionExecutor executor = new ExpressionExecutor(expr.getLeftExpression(), expr.getRightExpression());

		IAbstractQueryResult left = executor.get(0);
		IAbstractQueryResult right = executor.get(1);

		Iterables.addAll(result.getElements(), toIterable(left));	//4
		Iterables.addAll(result.getElements(), toIterable(right));	//4

		qres.push(result);							//5
	}

	/**
	 * selekcja (niealgebraiczny).
	 * Ewaluacja:
	 * 	1. Zainicjalizuj pusty bag (bedziemy sie do niego odnosic jako eres).
	 * 	2. Wykonaj lewe podzapytanie.
	 * 	3. Podnies jego rezultat ze stosu QRES.
	 * 	4. Dla kazdego elementu e z rezultatu otrzymanego w poprzednim kroku wykonaj:
	 * 		4.1. Otworz nowa sekcja na ENVS.
	 * 		4.2. Wykonaj operacje nested(e).
	 * 		4.3. Wykonaj prawe podzapytanie.
	 * 		4.4. Podnies jego rezultat z QRES.
	 * 		4.5. Jesli wynik ewaluacji prawego wyrazenia nie jest pojedyncza wartoscia logiczna, podnies blad czasu wykonania.
	 *		4.6. Jesli rezultat prawego podzapytania jest true, dodaj e do eres.
	 * 	5. Poloz eres na QRES.
	 * @param expr
	 */
	@Override	//DONE
	public void visitWhereExpression(IWhereExpression expr) {
		expr.getLeftExpression().accept(this);
		IAbstractQueryResult left = qres.pop();

		BagResult whereRes = new BagResult();
		for (ISingleResult element : toIterable(left)) {
			envs.nested(element, store);
			expr.getRightExpression().accept(this);
			IAbstractQueryResult rightRes = qres.pop();
			rightRes = toSingleResult(rightRes);
			rightRes = deRefrence(rightRes, store);
			if(rightRes instanceof IBooleanResult) {
				boolean val = ((IBooleanResult)rightRes).getValue();
				if(val) {
					whereRes.add(element);
				}
			}
			envs.pop();
		}
		
		qres.push(whereRes);		
	}

	/**
	 * Tablica prawdy dla alternatywy wykluczającej:
	 * 	X	Y	RESULT
	 *	0	0	0
	 *	0	1	1
	 *	1	0	1
	 *	1	1	0
	 *
	 * @param expr
	 */
	@Override	//DONE
	public void visitXORExpression(IXORExpression expr) {
		ExpressionExecutor executor = new ExpressionExecutor(expr.getLeftExpression(), expr.getRightExpression());

		IAbstractQueryResult left = executor.get(0);
		IAbstractQueryResult right = executor.get(1);

		left = toSingleResult(left);
		left = deRefrence(left, store);

		right = toSingleResult(right);
		right = deRefrence(right, store);

		if(left instanceof IBooleanResult && right instanceof IBooleanResult) {
			boolean first = ((IBooleanResult) left).getValue().booleanValue();
			boolean second = ((IBooleanResult) right).getValue().booleanValue();
			qres.push(new BooleanResult(first != second));
		} else {
			throw new IllegalStateException("XOR expression can not be applied to: " + left.getClass() + " and " + right.getClass());
		}
	}

	@Override	//DONE
	public void visitBooleanTerminal(IBooleanTerminal expr) {
		Boolean value = expr.getValue();
		qres.push(new BooleanResult(value));
	}

	@Override	//DONE
	public void visitDoubleTerminal(IDoubleTerminal expr) {
		Double value = expr.getValue();
		qres.push(new DoubleResult(value));
	}

	@Override	//DONE
	public void visitIntegerTerminal(IIntegerTerminal expr) {
		Integer value = expr.getValue();
		qres.push(new IntegerResult(value));
	}

	@Override	//DONE
	public void visitNameTerminal(INameTerminal expr) {
		IBagResult bind = envs.bind(expr.getName());
		qres.push(bind);
	}

	@Override	//DONE
	public void visitStringTerminal(IStringTerminal expr) {
		String value = expr.getValue();
		qres.push(new StringResult(value));
	}

	/**
	 * konstruktory bagow i sekwencji.
	 * Ewaluacja:
	 *	1. Zainiacjalizuj pusty bag (bedziemy odnosic sie do niego jako eres).
	 *	2. Wykonaj podzapytanie.
	 *	3. Podnies wynik z QRES.
	 *	4. Potraktuj go jako strukture. Kazde pole tej struktury dodaj do eres.
	 *	5. Umiesc eres na QRES.
	 */
	@Override	//DONE??
	public void visitBagExpression(IBagExpression expr) {
		IBagResult result = new BagResult();

		IAbstractQueryResult res = new ExpressionExecutor(expr.getInnerExpression()).getFirst();
		Iterables.addAll(result.getElements(), toIterable(res));

		qres.push(result);
	}

	/**
	 * suma elementow w kolekcji.
	 * Ewaluacja:
	 * 	1. Zainicjalizuj wartosc, ktora bedzie sluzyc do przechowywania sumy (bedziemy odnosic sie do niej jako eres).
	 * 	2. Wykonaj podzapytanie.
	 * 	3. Podnies jeden element z QRES.
	 * 	4. Dla kazdego elementu e wykonaj:
	 *  	4.1 zwiększ licznik elementów
	 * 	5. Umiesc eres na QRES.
	 */
	@Override	//DONE
	public void visitCountExpression(ICountExpression expr) {
		IAbstractQueryResult res = new ExpressionExecutor(expr.getInnerExpression()).getFirst();

		qres.push(new NumberAggregation(store, res) {
			private double count = 0.0;

			@Override
			protected void aggregateSingle(double number) {
				count++;
			}

			@Override
			protected double getResult() {
				return count;
			}

			@Override
			protected boolean performDereference() {
				return false;
			}
		}.aggregate());
	}

	@Override	//TODO
	public void visitExistsExpression(IExistsExpression expr) {
		//TODO
	}

	@Override	//DONE
	public void visitMaxExpression(IMaxExpression expr) {
		IAbstractQueryResult res = new ExpressionExecutor(expr.getInnerExpression()).getFirst();

		qres.push(new NumberAggregation(store, res) {
			private double max = Double.MIN_VALUE;

			@Override
			protected void aggregateSingle(double number) {
				if(number > max) {
					max = number;
				}
			}

			@Override
			protected double getResult() {
				return max;
			}
		}.aggregate());
	}

	@Override	//DONE
	public void visitMinExpression(IMinExpression expr) {
		IAbstractQueryResult res = new ExpressionExecutor(expr.getInnerExpression()).getFirst();

		qres.push(new NumberAggregation(store, res) {
			private double min = Double.MAX_VALUE;

			@Override
			protected void aggregateSingle(double number) {
				if(number < min) {
					min = number;
				}
			}

			@Override
			protected double getResult() {
				return min;
			}
		}.aggregate());
	}

	@Override	//DONE
	public void visitNotExpression(INotExpression expr) {
		IAbstractQueryResult res = new ExpressionExecutor(expr.getInnerExpression()).getFirst();
		res = toSingleResult(res);
		res = deRefrence(res, store);

		if(res instanceof IBooleanResult) {
			qres.push(new BooleanResult(!((IBooleanResult) res).getValue().booleanValue()));
		} else {
			throw new IllegalStateException("Can not apply not expression to: " + res.getClass());
		}
	}

	/**
	 * konstruktory bagow i sekwencji.
	 * Ewaluacja:
	 *	1. Zainiacjalizuj pusty bag (bedziemy odnosic sie do niego jako eres).
	 *	2. Wykonaj podzapytanie.
	 *	3. Podnies wynik z QRES.
	 *	4. Potraktuj go jako strukture. Kazde pole tej struktury dodaj do eres.
	 *	5. Umiesc eres na QRES.
	 */
	@Override	//DONE
	public void visitStructExpression(IStructExpression expr) {
		IBagResult result = new BagResult();			//1

		IAbstractQueryResult res = eval(expr);			//2,3

		//jeden pies czy dodam do bag'a czy do sekwencji. skoro na koniec i tak londuje w bagu...
		Iterables.addAll(result.getElements(), toIterable(res));	//4

		qres.push(result);
	}

	/**
	 * suma elementow kolekcji.
	 * Ewaluacja:
	 * 	1. Zainicjalizuj wartosc, ktora bedzie sluzyc do przechowywania sumy (bedziemy odnosic sie do niej jako eres).
	 * 	2. Wykonaj podzapytanie.
	 * 	3. Podnies jeden element z QRES.
	 * 	4. Dla kazdego elementu e wykonaj:
	 *  	4.1 Jesli rezultat jest referencja, wykonaj dereferencje.
	 *  	4.2 Jesli rezultat (po ewentualnej dereferencji) nie jest liczba, podnies blad czasu wykonania.
	 *  	4.3 Dodaj liczbe do eres.
	 * 	5. Umiesc eres na QRES.
	 */
	@Override	//DONE
	public void visitSumExpression(ISumExpression expr) {
		IAbstractQueryResult res = new ExpressionExecutor(expr.getInnerExpression()).getFirst();

		qres.push(new NumberAggregation(store, res) {
			private double sum = 0.0;

			@Override
			protected void aggregateSingle(double number) {
				sum += number;
			}

			@Override
			protected double getResult() {
				return sum;
			}
		}.aggregate());
	}

	@Override	//DONE
	public void visitUniqueExpression(IUniqueExpression expr) {
		IBagResult result = new BagResult();		//1

		IAbstractQueryResult res = new ExpressionExecutor(expr.getInnerExpression()).getFirst();

		res = deRefrence(res, store);

		Set<ISingleResult> set = Sets.newHashSet();
		for(ISingleResult element : toIterable(res)) {
			set.add(element);
		}

		for(ISingleResult element : set) {
			result.getElements().add(element);
		}
		qres.push(result);							//5
	}

	@Override	//DONE
	public void visitAvgExpression(IAvgExpression expr) {
		IAbstractQueryResult res = eval(expr.getInnerExpression());

		qres.push(new NumberAggregation(store, res) {
			private double sum = 0.0;
			private double count = 0.0;

			@Override
			protected void aggregateSingle(double number) {
				sum += number;
				count++;
			}

			@Override
			protected double getResult() {
				return sum/count;
			}
		}.aggregate());
	}

	@Override	//DONE
	public IAbstractQueryResult eval(IExpression queryTreeRoot) {
		queryTreeRoot.accept(this);
		return qres.pop();
	}
}
