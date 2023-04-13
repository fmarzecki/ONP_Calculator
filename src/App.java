public class App {
    static String operators[] = {"(", "+-)", "*/%", "^"};
    static String equation = "34+4*2/(1-5)^2";

    public static int getPriority(String s) {
        for (int i = 0; i < operators.length; i++) {
            if (operators[i].contains(s)) {
                return i;
            }
        }
        return -1;
    }

    public static String convertToOnp(String equation) {
        String output = "";
        String stack[] = new String[10];
        String number = ""; 
        int stackSize = -1;

        for (int i = 0; i < equation.length(); i++) {
            String element = Character.toString(equation.charAt(i));

            // Gdy element jest liczba
            if (getPriority(element) == -1) {
                number += element;
                
            }
            // Gdy element nie jest liczba
            else {
                output += number  + " ";
                number = "";
                // Gdy element to nawias otwierajacy, dodaj go na stos
                if (element.equals("(")) {
                    stackSize++;
                    stack[stackSize] = element;
                }
                // Gdy element to nawias zamykajacy, zdejmuj ze stosu i dodawaj na wyjscie
                // dopoki
                else if (element.equals(")")) {
                   while(!stack[stackSize].equals("(")) {
                        output += stack[stackSize];
                        stackSize--;
                   }
                   stackSize--;
                }
                else  {
                    // Jesli jest to pierwszy element na stosie
                    if (stackSize == -1) {
                        stackSize++;
                        stack[stackSize] = element;
                        continue;
                    }

                    // jesli badany operator ma mniejszy priorytet od operatora na szczycie stosu
                    // zdejmujemy ze stosu i dodajemy na wyjscie operatory o priorytecie mniejszym
                    // badz rownym lub dopoki stos nie bedzie pusty
                    // nastepnie dodajey badany operator
                    if (getPriority(element) <= getPriority(stack[stackSize])) {
                        while (getPriority(element) <= getPriority(stack[stackSize]) && stackSize > 0) {
                            output += stack[stackSize];
                            stackSize--;
                        }
                        stackSize++;
                        stack[stackSize] = element;
                    }
                    // Jesli badany operator ma wiekszy priorytet, po prostu go dodajemy
                    else {
                        stackSize++;
                        stack[stackSize] = element;
                    }
                }
            }
        }

        // Gdy wyrazenie sie zakonczy dodaj reszte stosu na wyjscie
        for (int i = stackSize; i >= 0; i--) {
            output += stack[i]  + " ";
        }

        return output;
    }

    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");

        System.out.println("Wynik = " + convertToOnp(equation));

    }
}
