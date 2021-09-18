package com.primayasa.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {
    TextView workingsTV;
    TextView resultTV;

    String workings = "";
    String[] mathExpressionList;
    LinkedList<Double> operands = new LinkedList<>();
    LinkedList<String> operators = new LinkedList<>();
    Double result;
    String final_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initTextViews();
    }

    private void initTextViews(){
        workingsTV = findViewById(R.id.workingsTextView);
        resultTV = findViewById(R.id.resultTextView);
    }

    private void setWorkings(String givenValue){
        workings = workings + givenValue;
        workingsTV.setText(workings);
    }

    public void clearOnClick(View view) {
        workingsTV.setText("");
        workings = "";
        resultTV.setText("");
    }

    public void equalsOnClick(View view) {
        result = null;
        final_result = "=";
        mathExpressionList = workings.split("(?<=[\\d.])(?=[^\\d.])"
                + "|(?<=[^\\d.])(?=[\\d.])");

        if(checkMathExpression()) {
            for (String mathExpression : mathExpressionList) {
                if (mathExpression.equals("")) {
                    continue;
                } else if (isOperator(mathExpression)) {
                    operators.add(mathExpression);
                } else {
                    operands.add(Double.parseDouble(mathExpression));
                }
            }

            calculateMultiplication(countMultiplication());
            result = operands.poll();
            calculateAddition();

            if(result!=null){
                if(result % 1 == 0){
                    final_result = final_result + result.intValue();
                }else{
                    final_result = final_result + String.format("%,.4f", result);
                }
                resultTV.setText(final_result);

            }else{
                Toast.makeText(this, "Invalid Input", Toast.LENGTH_SHORT).show();
            }

        }else{
            Toast.makeText(this, "Operator Invalid", Toast.LENGTH_SHORT).show();
        }
    }

    private void calculateAddition(){
        while (!operators.isEmpty()) {
            switch (operators.poll()) {
                case "+":
                    result += operands.poll();
                    break;
                case "-":
                    result -= operands.poll();
                    break;
            }
        }
    }

    private int countMultiplication(){
        int counter = 0;
        for (int i = 0; i < operators.size(); i++) {
            switch (operators.get(i)) {
                case "x":
                    counter++;
                    break;
                case "/":
                    counter++;
                    break;
                case "%":
                    counter++;
                    break;
            }
        }
        return counter;
    }

    private void calculateMultiplication(int counter){
        while(counter>0){
            for (int i = 0; i < operators.size(); i++) {
                switch (operators.get(i)) {
                    case "x":
                        operands.set(i, operands.get(i) * operands.get(i + 1));
                        operands.remove(i + 1);
                        operators.remove(i);
                        counter--;
                        break;
                    case "/":
                        operands.set(i, operands.get(i) / operands.get(i + 1));
                        operands.remove(i + 1);
                        operators.remove(i);
                        counter--;
                        break;
                    case "%":
                        operands.set(i, operands.get(i) % operands.get(i + 1));
                        operands.remove(i + 1);
                        counter--;
                        break;
                }
            }
        }
    }

    private boolean checkMathExpression(){
        for (int i=0; i<mathExpressionList.length; i++) {
            if(isOperator(mathExpressionList[i])){
                if(i==0){
                    if (mathExpressionList[0].equals("-")) {
                        mathExpressionList[1] = "-" + mathExpressionList[1];
                        mathExpressionList[0] = "";
                    }else if(mathExpressionList[0].equals("+")) {
                        mathExpressionList[0] = "";
                    }else{
                        return false;
                    }
                }else if(i==mathExpressionList.length-1){
                    return false;
                }
            }else{
                if(isNumeric(mathExpressionList[i])){
                    continue;
                }else{
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isOperator(String element){
        switch (element) {
            case "x":
                return true;
            case "/":
                return true;
            case "%":
                return true;
            case "+":
                return true;
            case "-":
                return true;
            default:
                return false;
        }
    }

    public boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    public void delOnClick(View view) {
        StringBuffer sb= new StringBuffer(workings);
        workings = "";
        sb.deleteCharAt(sb.length()-1);
        workings = String.valueOf(sb);
        workingsTV.setText(workings);
    }

    public void divideOnClick(View view) {
        setWorkings("/");
    }

    public void modOnClick(View view) {
        setWorkings("%");
    }

    public void sevenOnClick(View view) {
        setWorkings("7");
    }

    public void eightOnClick(View view) {
        setWorkings("8");
    }

    public void nineOnClick(View view) {
        setWorkings("9");
    }

    public void multipleOnClick(View view) {
        setWorkings("x");
    }

    public void fourOnClick(View view) {
        setWorkings("4");
    }

    public void fiveOnClick(View view) {
        setWorkings("5");
    }

    public void sixOnClick(View view) {
        setWorkings("6");
    }

    public void plusOnClick(View view) {
        setWorkings("+");
    }

    public void oneOnClick(View view) {
        setWorkings("1");
    }

    public void twoOnClick(View view) {
        setWorkings("2");
    }

    public void threeOnClick(View view) {
        setWorkings("3");
    }

    public void minusOnClick(View view) {
        setWorkings("-");
    }

    public void zeroOnClick(View view) {
        setWorkings("0");
    }

    public void pointOnClick(View view) {
        setWorkings(".");
    }
}