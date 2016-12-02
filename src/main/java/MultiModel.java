import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import static javafx.scene.input.KeyCode.*;

/**
 * @author Michlu
 * @sience 2016-11-30
 */
public class MultiModel {

    private String output;

    private double number1 = 0;
    private String operator = "";
    private boolean start = true;
    private final int MAX_OUTPUT_LENGTH = 19;

    private Model model = new Model();

    public String getOutput() {
        return output;
    }

    // Zbiory przyjmowanych przyciskow
    KeyCode[] numpadKey = {NUMPAD0, NUMPAD1, NUMPAD2, NUMPAD3, NUMPAD4, NUMPAD5, NUMPAD6, NUMPAD7, NUMPAD8, NUMPAD9, DECIMAL,
            DIGIT0, DIGIT1, DIGIT2, DIGIT3, DIGIT4, DIGIT5, DIGIT6, DIGIT7, DIGIT8, DIGIT9, PERIOD};
    KeyCode[] operatorKey = {DIVIDE, MULTIPLY, SUBTRACT, ADD, MINUS, SLASH};
    /**
     * Ustawia text w widoku aplikacji
     * @param value przyjmuje String interfejsu klawiatury numerycznej
     */
    public void addText(String value){
        if(start){
            output = "";
            start = false;
        }

        // Aby nie przekorczyc dlugosci okna wyswietlania
        if(!(output.length() == MAX_OUTPUT_LENGTH)){

            // Jezeli pierwszym znakiem jest kropka (.) dodaj 0 przed znakiem
            if(output.length() == 0 && value.equals("."))
                output= "0";
            // Jezeli w lancuchu znakow jest kropka nie pozwol na dodanie kolejnej
            if(value.equals(".")){
                int dot = 1;
                for(int i=0;i<output.length();++i){
                    if(output.charAt(i)=='.') dot += 1;
                    if(dot==2) return;
                }
            }
            // Jezeli pierwsza liczba to zero nie dodawaj kolejnego zera
            if(output.length()==1 && output.charAt(0)=='0' && value.equals("0")) return;
            // Jezeli pierwsza liczba to zero a drugi znak nie jest kropka skasuj zero
            if(output.length()==1 && output.charAt(0)=='0' && !value.equals(".")){
                output = output.substring(0, 0);
            }

            output = output + value;
        }
        else{
            return;
        }
    }


    /**
     * Ustawia operator, oraz ustawia wyniki dzialan matematycznych
     * @param value przyjmuje String interfejsu operatorow matematycznych
     */
    public void addOperator(String value) {
        // Kasowanie wyniku/wyswietlacza
        if(value.equals("C")){
            cleanOutput();
        }
        // Jezeli brak liczb, nic nie rob przy kliknieciu operoatorow
        if(output.length()==0 ){
            return;
        }

        // Jezeli operatorem nie jest znak =
        if(!"=".equals(value)){

//            if(number1>0 && !operator.isEmpty() && output.getText()==null){
//                System.out.println("Return");
//                return;
//            }
            // Funkcje operujace na jednej liczbie
            if(value.equals("sqrt") || value.equals("+/-") || value.equals("%")){
                try {
                    output = String.valueOf(model.calculate(Double.parseDouble(output), value));
                } catch (CalculationError e) {
                    e.printStackTrace();
                    output = "ERR";
                }
            }

            else {
                operator = value;
                // Jezeli jest juz pierwsza liczba i podana druga, przyciskajac operator matematyczny - oblicza i podaje wynik, przypisuje go do liczby1
                if(number1>0){
                    try {
                        output = String.valueOf(model.calculate(number1, Double.parseDouble(output), operator));
                    } catch (CalculationError calculationError) {
                        calculationError.printStackTrace();
                    }
                    number1 = Double.parseDouble(output);
                    start = true;
                }
                // jezeli to pierwsza liczba, przypisuje ja d number1
                else{
                    number1 = Double.parseDouble(output);
                }
                start = true;
//                output.setText("");
            }
        }
        // Jezeli operatorem jest znak =
        else {
            if(operator.isEmpty()) return;
            prepareOutput(); //wynik

        }
    }

    public void cleanOutput() {
        output = "";
        operator = "";
        number1 = 0;
        start = true;
    }

    public void prepareOutput() {
        // Pobiera pierwsza liczbe i przesyla druga wraz z operatorem
        // String do sprawdzenia ostatnich cyfer
        String checkString = null;
        try {
            checkString = String.valueOf(model.calculate(number1, Double.parseDouble(output), operator));
        } catch (CalculationError calculationError) {
            calculationError.printStackTrace();
        }

        // Jezeli na koncu wyniku jest (.0) skasuj
        if(checkString.charAt(checkString.length()-1)=='0' && checkString.charAt(checkString.length()-2)=='.'){
            output = checkString.substring(0, checkString.length()-2);
        }
        else{
            output = checkString;
        }
        number1 = 0;
        operator = "";
        start = true;
    }
}
