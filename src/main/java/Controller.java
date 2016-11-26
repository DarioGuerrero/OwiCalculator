import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

import static javafx.scene.input.KeyCode.*;


/**
 * @author Michal Nowinski
 * @sience 2016-11-16
 */
public class Controller {

    @FXML
    private Label output;

    private double number1 = 0;
    private String operator = "";
    private boolean start = true;
    private final int MAX_OUTPUT_LENGTH = 19;

    private Model model = new Model();

    // Zbiory przyjmowanych przyciskow
    KeyCode[] numpadKey = {NUMPAD0, NUMPAD1, NUMPAD2, NUMPAD3, NUMPAD4, NUMPAD5, NUMPAD6, NUMPAD7, NUMPAD8, NUMPAD9, DECIMAL,
            DIGIT0, DIGIT1, DIGIT2, DIGIT3, DIGIT4, DIGIT5, DIGIT6, DIGIT7, DIGIT8, DIGIT9, PERIOD};
    KeyCode[] operatorKey = {DIVIDE, MULTIPLY, SUBTRACT, ADD, MINUS, SLASH};

    @FXML
    public void processMenu(ActionEvent event){
        String value = ((MenuItem)event.getSource()).getText();
        if(value.equals("Copy")){
            StringSelection stringSelection = new StringSelection(output.getText());
            Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
            clpbrd.setContents(stringSelection, null);
        }
        if(value.equals("Exit")){
            Platform.exit();
            System.exit(0);
        }
    }
    /**
     * Obsluga interfejsu klawiatury numerycznej
     * @param event przechwytuje nacisniecia przyciskow
     */
    @FXML
    public void processNumpad(ActionEvent event) {
        String value = ((Button) event.getSource()).getText();
        addText(value);
    }

    /**
     * Obsluga interfejsu klawiatury operatorow matematycznych
     * @param event przechwytuje nacisniecia przyciskow
     */
    @FXML
    public void processOperator(ActionEvent event) {
        String value = ((Button)event.getSource()).getText();
        addOperator(value);
    }

    /**
     * Ustawia text w widoku aplikacji
     * @param value przyjmuje String interfejsu klawiatury numerycznej
     */
    public void addText(String value){
        if(start){
            output.setText("");
            start = false;
        }

        // Aby nie przekorczyc dlugosci okna wyswietlania
        if(!(output.getText().length() == MAX_OUTPUT_LENGTH)){

            // Jezeli pierwszym znakiem jest kropka (.) dodaj 0 przed znakiem
            if(output.getText().length() == 0 && value.equals("."))
                output.setText("0");
            // Jezeli w lancuchu znakow jest kropka nie pozwol na dodanie kolejnej
            if(value.equals(".")){
                int dot = 1;
                for(int i=0;i<output.getText().length();++i){
                    if(output.getText().charAt(i)=='.') dot += 1;
                    if(dot==2) return;
                }
            }
            // Jezeli pierwsza liczba to zero nie dodawaj kolejnego zera
            if(output.getText().length()==1 && output.getText().charAt(0)=='0' && value.equals("0")) return;
            // Jezeli pierwsza liczba to zero a drugi znak nie jest kropka skasuj zero
            if(output.getText().length()==1 && output.getText().charAt(0)=='0' && !value.equals(".")){
                    output.setText(output.getText().substring(0, 0));
            }

            output.setText(output.getText() + value);
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
        if(output.getText().length()==0 ){
            return;
        }

        // Jezeli operatorem nie jest znak =
        if(!"=".equals(value)){

            if(!operator.isEmpty()){
                return;
            }
            // Funkcje operujace na jednej liczbie
            else if(value.equals("sqrt") || value.equals("+/-") || value.equals("%")){
                operator = value;
                try {
                    output.setText(String.valueOf(model.calculate(Double.parseDouble(output.getText()), operator)));
                } catch (CalculationError e) {
                    e.printStackTrace();
                    output.setText("ERR");
                }
                operator = "";
            }

            else {
                operator = value;
                number1 = Double.parseDouble(output.getText());
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


    /**
     * Obsluga klawiatury
     * @param event przyjmuje parametr po nacisnieciu przycisku
     */
    @FXML
    public void setOnKeyPressed(KeyEvent event){

        String value = event.getText();


        // Jezeli enter albo przycisk =, daj wynik
        if(event.getCode() == ENTER || event.getCode() == EQUALS){
            if(operator.isEmpty()) return;
            prepareOutput(); //wynik
            return;
        }
        // Jezeli przycisk DEL kasuj
        if(event.getCode() == DELETE){
            cleanOutput();
            return;
        }
        // dodaj wartosc jezeli przycisk sie zgadza z tabela numeryczna
        for (KeyCode keyCode :numpadKey) {
            if(event.getCode() == keyCode){
                if(event.getCode() == DECIMAL) value = ".";
                addText(value);
                break;
            }
        }
        // dodaj opertator jezeli przysick zgadza sie z tabela operatorow
        for (KeyCode keyCode :operatorKey) {
            if(keyCode == event.getCode()){
                addOperator(value);
                break;
            }
        }
    }

    public void cleanOutput() {
        output.setText("");
        operator = "";
        number1 = 0;
        start = true;
    }

    public void prepareOutput() {
        // Pobiera pierwsza liczbe i przesyla druga wraz z operatorem
        // String do sprawdzenia ostatnich cyfer
        String checkString = String.valueOf(model.calculate(number1, Double.parseDouble(output.getText()), operator));

        // Jezeli na koncu wyniku jest (.0) skasuj
        if(checkString.charAt(checkString.length()-1)=='0' && checkString.charAt(checkString.length()-2)=='.'){
            output.setText(checkString.substring(0, checkString.length()-2));
        }
        else{
            output.setText(checkString);
        }
        operator = "";
        start = true;
    }
}
