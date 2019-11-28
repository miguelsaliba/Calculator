import java.util.Arrays;

public class calculator {
    public static void main(String[] args) {
        System.out.println(simplify("2*3*12+7-2/2"));
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

    private static String simplify(String message) {

        String[] temp = message.split("(?<=[()+\\-*/^])|(?=[()+\\-*/^])");
        System.out.println(Arrays.toString(temp));

        for (int i = 0; i < temp.length; i++) {
            if (temp[i].equals("*")) {
                temp[i+1] = String.valueOf( Double.parseDouble(temp[i-1]) *  Double.parseDouble(temp[i+1]) );
                temp = removeElement(temp, i-1, i);
                i -= 2;
                System.out.println(Arrays.toString(temp));

            } else if (temp[i].equals("/")){
                if (temp[i + 1].equals("0")) {
                    temp = new String[]{"ok boomer"};
                    break;
                }
                temp[i+1] = String.valueOf( Double.parseDouble(temp[i-1]) /  Double.parseDouble(temp[i+1]) );
                temp = removeElement(temp, i-1, i);
                i -= 2;
                System.out.println(Arrays.toString(temp));

            }
        }
        for (int i = 0; i < temp.length; i++) {
            if (temp[i].equals("+")) {
                temp[i+1] = String.valueOf( Double.parseDouble(temp[i-1]) +  Double.parseDouble(temp[i+1]) );
                temp = removeElement(temp, i-1, i);
                i -= 2;
                System.out.println(Arrays.toString(temp));


            } else if (temp[i].equals("-")){
                temp[i+1] = String.valueOf( Double.parseDouble(temp[i-1]) -  Double.parseDouble(temp[i+1]) );
                temp = removeElement(temp, i-1, i);
                i -= 2;
                System.out.println(Arrays.toString(temp));

            }
        }

        return message + " = " + temp[0];

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
