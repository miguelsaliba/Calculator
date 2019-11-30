import java.text.DecimalFormat;
import java.util.Arrays;

public class calculator {
    public static void main(String[] args) {
        simplify("2*3*((12+7)^2+4)-3/2");
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

        String[] temp = message.split("(?<=[()+\\-*/^])|(?=[()+\\-*/^])");
        System.out.println(Arrays.asList(temp).toString().substring(1).replaceFirst("]", "").replace(", ", ""));

        int index1 = -1;
        for (int i = 0; i < temp.length; i++) {
            if (temp[i].equals("(")) {
                index1 = i;
            } else if (temp[i].equals(")")) {
                if (index1 == -1) {
                    return "error: incorrect bracket placement";
                }
                String[] insideParentheses;
                if (index1+1 == i){
                    insideParentheses = Arrays.copyOfRange(temp, index1, i);
                } else {
                    insideParentheses = Arrays.copyOfRange(temp, index1 + 1, i);
                }
                String num = simplify(String.join("", Arrays.asList(insideParentheses)));
                temp[i] = num;
                temp = removeElement(temp, index1, i-1);
                i = 0;
                System.out.println(Arrays.asList(temp).toString().substring(1).replaceFirst("]", "").replace(", ", ""));
            }
        }

        for (int i = 0; i < temp.length; i++) {
            if (temp[i].equals("^")) {
                temp[i+1] = String.valueOf( format.format( Math.pow( Double.parseDouble(temp[i-1]),  Double.parseDouble(temp[i+1]) )));
                temp = removeElement(temp, i-1, i);
                i -= 2;
                System.out.println(Arrays.asList(temp).toString().substring(1).replaceFirst("]", "").replace(", ", ""));

            }
        }

        for (int i = 0; i < temp.length; i++) {
            if (temp[i].equals("*")) {
                temp[i+1] = String.valueOf( format.format( Double.parseDouble(temp[i-1]) *  Double.parseDouble(temp[i+1]) ));
                temp = removeElement(temp, i-1, i);
                i -= 2;
                System.out.println(Arrays.asList(temp).toString().substring(1).replaceFirst("]", "").replace(", ", ""));

            } else if (temp[i].equals("/")){
                if (temp[i + 1].equals("0")) {
                    return "error: dividing by zero";
                }
                temp[i+1] = String.valueOf( format.format( Double.parseDouble(temp[i-1]) /  Double.parseDouble(temp[i+1]) ));
                temp = removeElement(temp, i-1, i);
                i -= 2;
                System.out.println(Arrays.asList(temp).toString().substring(1).replaceFirst("]", "").replace(", ", ""));

            }
        }

        for (int i = 0; i < temp.length; i++) {
            if (temp[i].equals("+")) {
                temp[i+1] = String.valueOf( format.format( Double.parseDouble(temp[i-1]) +  Double.parseDouble(temp[i+1]) ));
                temp = removeElement(temp, i-1, i);
                i -= 2;
                System.out.println(Arrays.asList(temp).toString().substring(1).replaceFirst("]", "").replace(", ", ""));

            } else if (temp[i].equals("-")){
                temp[i+1] = String.valueOf( format.format( Double.parseDouble(temp[i-1]) -  Double.parseDouble(temp[i+1]) ));
                temp = removeElement(temp, i-1, i);
                i -= 2;
                System.out.println(Arrays.asList(temp).toString().substring(1).replaceFirst("]", "").replace(", ", ""));

            }
        }

        return temp[0];

        // Gets the index of the last iteration of each operator
        /*
        int lastMult = message.lastIndexOf("*");
        int lastDiv = message.lastIndexOf("/");
        int lastSub = message.lastIndexOf("-");
        int lastAdd = message.lastIndexOf("+");

        double num;

        // If the last operator is multiplication
        if (lastMult > lastDiv && lastMult > lastSub && lastMult > lastAdd){
            // num is the last number in the input string
            num = Double.parseDouble(message.substring(lastMult+1));
            // removes the last number and operator from the string
            message = message.substring(0,lastMult);

            // MAGIC
            return String.valueOf(Double.parseDouble(simplify(message)) * num);

        }
        if (lastDiv > lastSub && lastDiv > lastAdd){
            num = Double.parseDouble(message.substring(lastDiv+1));
            message = message.substring(0,lastDiv);

            return String.valueOf(Double.parseDouble(simplify(message)) / num);

        }
        if (lastSub > lastAdd){
            num = Double.parseDouble(message.substring(lastSub+1));
            message = message.substring(0,lastSub);

            return String.valueOf(Double.parseDouble(simplify(message)) - num);

        } else if (lastAdd > lastSub){
            num = Double.parseDouble(message.substring(lastAdd+1));
            message = message.substring(0,lastAdd);

            return String.valueOf(Double.parseDouble(simplify(message)) + num);

        } else {
            return message;
        }
        */
    }
}
