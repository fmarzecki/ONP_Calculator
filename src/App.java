
public class App {

    static String operators[] = {"(", "+-)", "*/%", "^"};
    static String equation = "5+(1+2)*4-3=";

    final void printHi(String s) {
        System.out.println(s);
    }

    void printHello(String s) {
        System.out.println(s);
    }
    static int getPriority(String s) {
        for (int i = 0; i < operators.length; i++) {
            if (operators[i].contains(s)) {
                return i;
            }
        }
        return -1;
    }

    static String convertToOnp(String equation) {
        String stack[] = new String[10];
        String output = "";
        String number = ""; 
        String element = "";

        int stackSize = -1;

        for (int i = 0; i < equation.length()-1; i++) {
            element = Character.toString(equation.charAt(i));
                 
            if (getPriority(element) == -1) {               // Gdy element jest liczba dodawaj go dopoki nie natrafisz na operator
                number += element;
            }
            else {                                          // Gdy element nie jest liczba
                output += number;
                output += output.equals("") || output.endsWith(" ") ? "" : " ";
                number = "";
                
                if (element.equals("(")) {                  // Gdy element to nawias otwierajacy, dodaj go na stos
                    stackSize++;
                    stack[stackSize] = element;
                }
                else if (element.equals(")")) {             // Gdy element to nawias zamykajacy, zdejmuj ze stosu i dodawaj na wyjscie
                   while(!stack[stackSize].equals("(")) {   // dopoki nie natrafisz na nawias otwierajacy, usun nawias ze stosu
                        output += stack[stackSize];
                        output += output.endsWith(" ") ? "" : " ";
                        stackSize--;
                   }
                   stackSize--;
                }
                else  {                                     // Gdy element to jeden z operatorow
                    if (stackSize == -1) {                  // Jesli jest to pierwszy element na stosie
                        stackSize++;
                        stack[stackSize] = element;
                        continue;
                    }
                    if (getPriority(element) <= getPriority(stack[stackSize])) {                            //jesli badany operator ma mniejszy priorytet od operatora na szczycie stosu
                        while (stackSize >= 0 && getPriority(element) <= getPriority(stack[stackSize]) ) {  //zdejmujemy ze stosu i dodajemy na wyjscie operatory o priorytecie mniejszym
                            output += stack[stackSize] + " ";                                               // badz rownym lub dopoki stos nie bedzie pusty, nastepnie dodajey badany operator
                            stackSize--;
                        }
                        stackSize++;
                        stack[stackSize] = element;
                    }
                    else {                                  // Jesli badany operator ma wiekszy priorytet od operatora na szycie stosu, dodajemy go na stos
                        stackSize++;
                        stack[stackSize] = element;
                    }
                }
            }
        }

        // Dodaj reszte na stos
        output += number;
        output += output.endsWith(" ") ? "" : " ";

        for (int j = stackSize; j >= 0; j--) {
            output += stack[j]  + " ";
        }
        return output;
    }

    static double calculateONP(String onp) {
        String[] onpArray = onp.split(" ");
        double[] stack = new double[20];

        double result = 0;
        int stackSize = -1;

        for (int i = 0; i < onpArray.length; i++) {

            // Jesli element jest liczba poloz na stos
            if (getPriority(onpArray[i]) == -1) {
                stackSize++;
                stack[stackSize] = Double.parseDouble(onpArray[i]);
            }
            // Jesli element jest operatorem zdejmij ze stosu dwie ostatnie liczby
            else {
                result = operation(stack[stackSize-1],stack[stackSize],onpArray[i]);
                stackSize--;
                stack[stackSize] = result;
            }
        }
        return stack[0];
    }

    static void printTable(String[] tab) {
        for (int i = 0; i < tab.length; i++) {
            System.out.print(tab[i] + " ");
        }
    }

    static double operation(double a, double b, String operator) {
        switch(operator) {
            case "+":
                return a+b;
            case "-":
                return a-b;
            case "/":
                return a/b;
            case "*":
                return a*b;
            case "%":
                return a%b;
            case "^":
                return Math.pow(a, b);
            default:
                return 0;
        }
    }

    public static void main(String[] args) throws Exception {
        for (String onp : args) {
            System.out.println("ONP = " + convertToOnp(onp));
            System.out.println("WYNIK ONP = " + calculateONP(convertToOnp(onp)));
        }
    }
}  