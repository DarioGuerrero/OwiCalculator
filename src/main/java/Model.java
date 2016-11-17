/**
 * Created by Michlu on 2016-11-16.
 */
public class Model {

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
    public double calculate(double number1, String operator){
        switch (operator){
            case "+/-":
                    return -number1;
            case "sqrt":
                if(number1<0) return 0;
                return Math.sqrt(number1);
        }

        System.out.println("Nieznany operator - " + operator);
        return 0;
    }
}
