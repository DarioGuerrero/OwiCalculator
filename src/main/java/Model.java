/**
 * @author Michlu
 * @sience 2016-11-16
 */
public class Model {

    /**
     * Oblicza dwie liczby
     * @param number1 pierwsza przekzana liczba
     * @param number2 druga przekzana liczba
     * @param operator operator matematyczny
     * @return zwraca wynik rownania
     */
    public double calculate(double number1, double number2, String operator){
        switch (operator){
            case "+":
                return number1 + number2;
            case "-":
                return number1 - number2;
            case "*":
                if(number2 ==0) return 0;
                return number1 * number2;
            case "/":
                if(number2 ==0) return 0;
                return number1 / number2;
            case "%":
                return number1 % number2;
        }

        System.out.println("Nieznany operator - " + operator);
        return 0;
    }
    public double calculate(double number1, String operator) throws CalculationError {
        switch (operator){
            case "+/-":
                return -number1;
            case "sqrt":
                if(number1<0) throw (new CalculationError("Nie mozna wyciagac pierwiastka kwadratowego z liczb ujemnych"));
                return Math.sqrt(number1);
        }

        System.out.println("Nieznany operator - " + operator);
        return 0;
    }

}
class CalculationError extends Exception {
    public CalculationError() {
        super();
    }
    public CalculationError(String s) {
        super(s);
    }
}
