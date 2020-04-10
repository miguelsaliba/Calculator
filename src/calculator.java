import java.text.DecimalFormat;
import java.util.Arrays;

public class calculator {
    public static void main(String[] args) {
        simplify("10^(2+0)");
    }

    private static String[] removeElement(String[] theArray, int firstIndex, int lastIndex){
        // if the index is not in the array or the array has no elements it returns the array
        if (theArray == null || firstIndex < 0 || lastIndex < firstIndex || lastIndex >= theArray.length) {
            return theArray;
        }

        String[] newArray = new String[theArray.length-(lastIndex-firstIndex)-1];

        for (int i = 0, k = 0; i < theArray.length; i++) {
            if (i <= lastIndex && i >= firstIndex) {
                continue;
            }
            newArray[k] = theArray[i];
            k++;
        }
        return newArray;
    }

    public static String simplify(String message) {

        DecimalFormat format = new DecimalFormat("#.#");
        format.setDecimalSeparatorAlwaysShown(false);

        StringBuilder builder = new StringBuilder(message);

        //String[] temp = message.split("(?<=[()+\\-*/^])|(?=[()+\\-*/^])");
        System.out.println(builder);

        int index1 = -1;
        for (int i = 0; i < builder.length(); i++) {
            if (builder.charAt(i) == '(') {
                index1 = i;
            } else if (builder.charAt(i) == ')') {
                if (index1 == -1) {
                    return "error: incorrect bracket placement";
                }
                StringBuilder insideParentheses;
                if (index1+1 == i){
                    insideParentheses = new StringBuilder(builder.substring(index1, i));
                } else {
                    insideParentheses = new StringBuilder(builder.substring(index1+1, i));
                }
                String num = simplify(insideParentheses.toString());
                if (num.equals("error: dividing by zero")){
                    return "error: dividing by zero";
                }
                builder.replace(index1, i+1, num);
                i = 0;
                System.out.println(builder);
            }
        }

        int index = builder.indexOf("^");
        while (index != -1) {
            int before = operatorBefore(builder, index);
            int after = operatorAfter(builder, index);

            builder.replace(before, after, format.format( Math.pow( Double.parseDouble(builder.substring(before, index)),  Double.parseDouble(builder.substring(index+1, after)) )));
            System.out.println(builder);
            index = builder.indexOf("^");
        }

        index = Math.max(builder.indexOf("*"), builder.indexOf("/"));
        while (index != -1) {
            if (!solve(builder, index, format)){
                return "error: dividing by zero";
            }
            System.out.println(builder);
            index = Math.max(builder.indexOf("*"), builder.indexOf("/"));
        }

        index = Math.max(builder.indexOf("+"), builder.indexOf("-"));
        while (index != -1) {
            solve(builder, index, format);
            System.out.println(builder);
            index = Math.max(builder.indexOf("+"), builder.indexOf("-"));
        }

        return builder.toString();
    }

    public static boolean solve(StringBuilder builder, int index, DecimalFormat format){
        int before = operatorBefore(builder, index);
        int after = operatorAfter(builder, index);

        double num1 = Double.parseDouble(builder.substring(before, index));
        double num2 = Double.parseDouble(builder.substring(index + 1, after));

        switch (builder.charAt(index)) {
            case '^':
                builder.replace(before, after, format.format(Math.pow(num1, num2)));
                break;
            case '*':
                builder.replace(before, after, format.format(num1 * num2));
                break;
            case '/':
                if (num2 == 0){
                    return false;
                }
                builder.replace(before, after, format.format(num1 / num2));
                break;
            case '+':
                builder.replace(before, after, format.format(num1 + num2));
                break;
            case '-':
                builder.replace(before, after, format.format(num1 - num2));
                break;
            default:
                System.out.println("idk how u got here tbh");

        }
        return true;
    }

    public static int operatorBefore(StringBuilder str, int index) {
        for (int i = index-1; i > 0; i--){
            if (!Character.isDigit(str.charAt(i)) && str.charAt(i) != '.'){
                return i+1;
            }
        }
        return 0;
    }

    public static int operatorAfter(StringBuilder str, int index) {
        for (int i = index+1; i < str.length(); i++){
            if (!Character.isDigit(str.charAt(i)) && str.charAt(i) != '.'){
                return i-1;
            }
        }
        return str.length();
    }
}
