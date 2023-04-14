
public class App {
    static String operators[] = {"(", "+-)", "*/%", "^"};
    static String equation = "12+2*(3*4+10/5)=";


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
                 
            if (element.equals("=")) {                      // Gdy wyrazenie sie zakonczy dodaj reszte stosu na wyjscie
                output += number;
                if (!output.endsWith(" ")) {
                    output += " ";
                }
                for (int j = stackSize; j >= 0; j--) {
                    output += stack[j]  + " ";
                }
                break;
            }

            if (getPriority(element) == -1) {               // Gdy element jest liczba dodawaj go dopoki nie natrafisz na operator
                number += element;
            }
            else {                                          // Gdy element nie jest liczba
                output += number;
                number = "";

                if (!output.endsWith(" ")) {
                    output += " ";
                }

                
                if (element.equals("(")) {                  // Gdy element to nawias otwierajacy, dodaj go na stos
                    stackSize++;
                    stack[stackSize] = element;
                }
                else if (element.equals(")")) {             // Gdy element to nawias zamykajacy, zdejmuj ze stosu i dodawaj na wyjscie
                   while(!stack[stackSize].equals("(")) {   // dopoki nie natrafisz na nawias otwierajacy, usun nawias ze stosu
                        output += stack[stackSize];
                        if (!output.endsWith(" ")) {
                            output += " ";
                        }
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
        return output;
    }

    static int calculateONP(String onp) {
        String[] onpArray = onp.split(" ");
        int[] stack = new int[20];

        int result = 0;
        int stackSize = -1;

        // printTable(onpArray);
        for (int i = 0; i < onpArray.length; i++) {

            // Jesli element jest liczba poloz na stos
            if (getPriority(onpArray[i]) == -1) {
                stackSize++;
                stack[stackSize] = Integer.parseInt(onpArray[i]);
            }

            // Jesli element jest operatorem zdejmij ze stosu dwie ostatnie liczby
            // i wykonaj na nich podana operacje
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

    static int operation(int a, int b, String operator) {
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
                return (int)Math.pow(a, b);
            default:
                return 0;
        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println("ONP = " + convertToOnp(equation));
        System.out.println("WYNIK ONP = " + calculateONP(convertToOnp(equation)));
    }
}
