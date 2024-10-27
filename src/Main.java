import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter a boolean equation: ");
        String equation = scanner.nextLine();
        ArrayList<String> rpnEquation = (ArrayList<String>) toRPN(equation);
        System.out.println(booleanCalculator(rpnEquation));

        }

    public static String operand(String p, String operand, String q) {
        boolean boolP, boolQ;
        boolean result;
        boolP = Objects.equals(p, "1");
        boolQ = Objects.equals(q, "1");

        result = switch (operand) {
            case "+" -> boolP || boolQ;
            case "." -> boolP && boolQ;
            case "->" -> boolP && !boolQ;
            case "<->" -> boolP == boolQ;
            default -> false;
        };
        if (result){
            return "1";
        } else {
            return "0";
        }
    }

    public static String booleanCalculator(List<String> rpnArray) {
        String[] operands = {"+", ".", "->", "<->"};
        while (rpnArray.size() > 1) {
            for (int i = 0; i < rpnArray.size(); i++) {
                String item = rpnArray.get(i);

                if (Arrays.asList(operands).contains(item)) {
                    String result = operand(rpnArray.get(i - 2), item, rpnArray.get(i - 1));
                    rpnArray.set(i, result);
                    rpnArray.remove(i - 1);
                    rpnArray.remove(i - 2);
                    break;
                }
            }
        }
        return rpnArray.getFirst();
    }

    public static List<String> toRPN(String inputString) {
        Map<String, Integer> precedence = new HashMap<>();
        precedence.put("~", 4);
        precedence.put(".", 3);
        precedence.put("+", 3);
        precedence.put("->", 2);
        precedence.put("<->", 1);
        Map<String, String> associativity = new HashMap<>();
        associativity.put("~", "R");
        associativity.put(".", "L");
        associativity.put("+", "L");
        associativity.put("->", "R");
        associativity.put("<->", "R");

        ArrayList<String> polishList = new ArrayList<>();
        ArrayList<String> operators = new ArrayList<>();

        String[] chars = inputString.split(" ");
        for (String c: chars) {
           if (Objects.equals(c, "1") || Objects.equals(c, "0")) {
               polishList.add(c);
           } else if (precedence.containsKey(c)) {
               while(!operators.isEmpty() && !Objects.equals(operators.getLast(), "(") && ((Objects.equals(associativity.get(c), "L") && precedence.get(c) <= precedence.get(operators.getLast())) || (Objects.equals(associativity.get(c), "R") && precedence.get(c) < precedence.get(operators.getLast())))) {
                   polishList.add(operators.getLast());
                   operators.removeLast();
               }
               operators.add(c);

           } else if (c.equals("(")) {
               operators.add(c);
           } else if (c.equals(")")) {
               while(!operators.isEmpty() && !Objects.equals(operators.getLast(), "(")) {
                   polishList.add(operators.getLast());
                   operators.removeLast();
               }
               operators.removeLast();
           }
        }
        while (!operators.isEmpty()) {
            polishList.add(operators.getLast());
            operators.removeLast();
        } return polishList;
    }
}