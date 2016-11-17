import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * Created by Michlu on 2016-11-16.
 */
public class Controller {

    @FXML
    private Label output;

    private double number1 = 0;
    private String operator = "";
    private boolean start = true;

    private Model model = new Model();

    @FXML
    public void processNumpad(ActionEvent event){
//        if(start){
//            output.setText("");
//            start = false;
//        }
        // wypisywanie liczb
        String value = ((Button)event.getSource()).getText();
        output.setText(output.getText() + value);

    }

    public void processOperator(ActionEvent event) {
        String value = ((Button)event.getSource()).getText();
        System.out.println(value);

        // Jezeli operatorem nie jest znak =
        if(!"=".equals(value)){
            if(!operator.isEmpty()){
                return;
            }
            // Funkcje operujace na jednej liczbie
            else if(value.equals("sqrt") || value.equals("+/-")){
                operator = value;
                output.setText(String.valueOf(model.calculate(Double.parseDouble(output.getText()), operator)));
                operator = "";
            }
            // Kasowanie wyniku/wyswietlacza
            else if(value.equals("C")){
                output.setText("");
                operator = "";
                number1 = 0;
//                start = true;
            }
            else {
                operator = value;
                number1 = Double.parseDouble(output.getText());
                output.setText("");
            }
        }
        // Jezeli operatorem jest znak =
        else {
            if(operator.isEmpty()) return;

            // Pobiera pierwsza liczbe i przesyla druga wraz z operatorem - nastepnie wyswietla wynik
            output.setText(String.valueOf(model.calculate(number1, Double.parseDouble(output.getText()), operator)));
            operator = "";
//            start = true;
        }
    }
}
