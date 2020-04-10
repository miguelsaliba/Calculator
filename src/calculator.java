import java.text.DecimalFormat;

public class calculator {
    public static void main(String[] args) {
        simplify("2*3*((12+7)^2+4)-3/2");
    }

    public static String simplify(String message) {

        DecimalFormat format = new DecimalFormat("#.#");
        format.setDecimalSeparatorAlwaysShown(false);

        StringBuilder builder = new StringBuilder(message);

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
            solve(builder, index, format);
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
        int after = operatorAfter(builder, index)+1;

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
        return str.length()-1;
    }
}
