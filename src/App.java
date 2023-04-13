
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

        for (int i = 0; i < equation.length(); i++) {
            element = Character.toString(equation.charAt(i));
                   
            // Gdy wyrazenie sie zakonczy dodaj reszte stosu na wyjscie
            if (element.equals("=")) {
                output += number;
                if (!output.endsWith(" ")) {
                    output += " ";
                }
                for (int j = stackSize; j >= 0; j--) {
                    output += stack[j]  + " ";
                }
                break;
            }

            // Gdy element jest liczba
            if (getPriority(element) == -1) {

                // Dodawaj element dopoki nie natrafisz na operator
                // Aby wziac pod uwage liczby wielocyfrowe
                number += element;
            }

            // Gdy element nie jest liczba
            else {
                output += number;
                number = "";

                if (!output.endsWith(" ")) {
                    output += " ";
                }

                // Gdy element to nawias otwierajacy, dodaj go na stos
                if (element.equals("(")) {
                    stackSize++;
                    stack[stackSize] = element;
                }

                // Gdy element to nawias zamykajacy, zdejmuj ze stosu i dodawaj na wyjscie
                // dopoki nie natrafisz na nawias otwierajacy
                else if (element.equals(")")) {
                   while(!stack[stackSize].equals("(")) {
                        output += stack[stackSize];
                        if (!output.endsWith(" ")) {
                            output += " ";
                        }
                        stackSize--;
                   }
                   stackSize--;
                }

                // Gdy element to jeden z operatorow
                else  {
                    // Jesli jest to pierwszy element na stosie
                    if (stackSize == -1) {
                        stackSize++;
                        stack[stackSize] = element;
                        continue;
                    }

                    /* 
                        jesli badany operator ma mniejszy priorytet od operatora na szczycie stosu
                        zdejmujemy ze stosu i dodajemy na wyjscie operatory o priorytecie mniejszym
                        badz rownym lub dopoki stos nie bedzie pusty, nastepnie dodajey badany operator
                    */  
                    if (getPriority(element) <= getPriority(stack[stackSize])) {
                        while (stackSize >= 0 && getPriority(element) <= getPriority(stack[stackSize]) ) {
                            output += stack[stackSize] + " ";
                            stackSize--;
                        }
                        stackSize++;
                        stack[stackSize] = element;
                    }
                    // Jesli badany operator ma wiekszy priorytet, dodajemy go na stos
                    else {
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
