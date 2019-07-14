import java.util.Scanner;

public class RLE {
    public static void main(String args[]) {
        Scanner scnr = new Scanner(System.in);
        String optionString;//if user type in not int, covert it to int
        int x;//index of optionString
        int optionNum;
        boolean gameOn = true;
        boolean isNum = true;
        String inputString;
        String tempDecString = "";//temporary string that concats characters in encodedString array
        int i = 0;//index of row of encodedString array
        int j = 0;//index of column of encodedString array
        int k = 0;//index of decodedString array
        char[][] encodedString = new char[i][j];
        char[] decodedString = new char[k];

        while (gameOn) {
            System.out.println("What would you like to do?");
            System.out.println("1.Input string to be encoded");
            System.out.println("2.View encoded string");
            System.out.println("3.View decoded string");
            System.out.println("4.Exit program");
            System.out.print("Option: ");
            optionString = scnr.next();

            for (x= 0; x < optionString.length(); ++x) {
                if (!Character.isDigit(optionString.charAt(x))){
                    isNum = false;
                }
            }

            if (!isNum) {
                System.out.println("\nError! Invalid input. Please enter an integer from 1-4");
                System.out.print("\n");
                isNum = true;//rest isNum to true
            }

            else {
                optionNum = Integer.parseInt(optionString);

                switch (optionNum) {
                    case 1:
                        System.out.print("\nEnter message: ");
                        inputString = scnr.next();
                        encodedString = encodeRLE(inputString);
                        break;

                    case 2:
                        System.out.print("\nThe encoded data is: ");

                        for (i = 0; i < encodedString.length; ++i) {
                            for (j = 0; j < encodedString[i].length; ++j) {
                                System.out.print(encodedString[i][j]);
                            }
                        }

                        System.out.print("\n");
                        System.out.print("\n");

                        break;

                    case 3:
                        System.out.print("\nThe decoded data is: ");

                        for (i = 0; i < encodedString.length; ++i) {
                            for (j = 0; j < encodedString[i].length; ++j) {
                                //concat string in encodedString array so that it can be sent to decodeRLE method as its parameter is string
                                tempDecString += encodedString[i][j];
                            }
                        }

                        decodedString = decodeRLE(tempDecString);

                        for (k = 0; k < decodedString.length; ++k) {
                            System.out.print(decodedString[k]);
                        }

                        tempDecString = "";//rest temDecString

                        System.out.print("\n");
                        System.out.print("\n");

                        break;

                    case 4:
                        gameOn = false;
                        System.out.println("\nProgram terminated");
                        break;

                    default:
                        System.out.println("\nError! Invalid input. Please enter an integer from 1-4");
                        System.out.print("\n");
                }
            }
        }
    }

    //method1
    public static int numOfDigits(int num) {
        int resultNum = String.valueOf(num).length();//convert integer to string to calculate number of digits

        return resultNum;

    }

    //Method2
    public static char[] toCharArray(int charCount, char strchar) {
        if (charCount == 1) {
            char[] countAndChar = new char[1];
            countAndChar[0] = strchar;
            return countAndChar;
        }
        else if (charCount <= 0) {
            return null;
        }
        else if ((charCount <= 9) && (charCount > 1)) {
            char countInChar;
            countInChar = (char) (charCount + 48);
            char[] countAndChar = new char[2];
            countAndChar[0] = countInChar;
            countAndChar[1] = strchar;

            return countAndChar;
        }

        else {
            int quotient = charCount / 10;

            while (quotient > 9) {
                quotient = quotient / 10;//divided by ten until quotient becomes less than ten, quotient will be the first element of countAndChar array
                // Ex.125a quotient represents 1
            }

            int quotient2 = charCount / 10;//set another quotient to get all remainders as quotients are necessary to find all remainders
            int remainder = charCount % 10;//remainders represent rest of count
            // Ex.125a 2, 5 are equal to remainders obtained by dividing count by ten until timesToDivide becomes zero

            int timesToDivide = String.valueOf(charCount).length() - 1;//times count has to be divided by ten to find remainders are its length -1
            char[] remainderArray = new char[timesToDivide];//temporary array containing remainders

            remainderArray[timesToDivide - 1] = (char) (remainder + '0');//during division, remainders appear in reverse order,
            //Ex.125a obtained 5 first and then 2, so assign them from last index to first
            --timesToDivide;

            while (timesToDivide > 0) {
                //loop until all remainders are obtained, in each loop, quotient2 and remainder are updated and remainder is assigned to remainderArray
                remainder = quotient2 % 10;
                quotient2 = quotient2 / 10;

                remainderArray[timesToDivide - 1] = (char) (remainder + '0');
                --timesToDivide;
            }

            timesToDivide = String.valueOf(charCount).length() - 1;//rest timesToDivide to assign size of countAndChar array
            char[] countAndChar = new char[timesToDivide + 2];
            //size of countAndChar array is number of remainder(which is equal to timesToDivide) + (quotient + alphabet))

            countAndChar[0] = (char) (quotient + '0');//assign quotient to first element
            int i = 1;

            for (i = 1; i < countAndChar.length - 1; ++i) {
                countAndChar[i] = remainderArray[i - 1];//assign each element in remainderArray to countAndChar array
            }

            countAndChar[countAndChar.length - 1] = strchar;//last element of countAndChar Array is alphabet

            return  countAndChar;
        }
    }

    //method3
    public static int findEncodeLength(String inputString) {
        int i;
        int uniqueNum = 1;//number of unique alphabet

        if (inputString == null) {
            return 0;
        }
        else {
            for (i = 0; i < inputString.length() - 1; ++i) {
                if (inputString.charAt(i + 1) != inputString.charAt(i)) {
                    uniqueNum += 1;
                }
            }
        }

        return uniqueNum;

    }

    //method4
    public static int findDecodeLength(String rleString) {
        int a = 0;//starting index of string
        int b;//ending index of string

        int decodeLength = 0;//sum of coefficients of each letter, which is equal to size of decode array

        while (a < rleString.length()) {
            if (Character.isDigit(rleString.charAt(a))) {
                b = a;
                while (Character.isDigit(rleString.charAt(b))) {
                    ++b;
                }
                decodeLength += Integer.parseInt(rleString.substring(a, b));
                a = b + 1;
            }

            //when coefficient of letter is one, one is not shown
            else {
                decodeLength += 1;
                ++a;
            }
        }

        return decodeLength;

    }

    //method5
    public static char[] decodeRLE(String rleString) {
        int decodeSize;//size of decoddeArray

        decodeSize = findDecodeLength(rleString);

        char[] decodeArray = new char[decodeSize];

        int i = 0;//index of decodeArray
        int j = 0;//index of rleString
        int k;//index indicating letter after digit Ex.3a k is index of a
        int l;//integer to let the program loop "number of letter" times. Ex 5a: loop 5 times

        while (j < rleString.length()) {
            if (Character.isDigit(rleString.charAt(j))) {
                k = j;
                while (Character.isDigit(rleString.charAt(k))) {
                    ++k;
                }
                l = 0;
                while (l < Integer.parseInt(rleString.substring(j, k))) {
                    decodeArray[i] = rleString.charAt(k);
                    ++i;
                    ++l;
                }
                ++k;
                j = k;
            }
            else {
                decodeArray[i] = rleString.charAt(j);
                ++i;
                ++j;
            }
        }

        return decodeArray;

    }

    //method6
    public static char[][] encodeRLE(String inputString) {
        int m;//number of row
        int i;//index of row

        int a = 0;//index of inputString
        int b;//index of tempArray and index of column

        int numLetter = 1;
        int arraySize;//number of repeated letter, sum of this and the letter is the size

        m = findEncodeLength(inputString);

        char[][] encodeArray = new char[m][];

        for(i = 0; i < m; ++i) {
            if (a < inputString.length() - 1) {
                while (inputString.charAt(a + 1) == inputString.charAt(a)) {
                    ++numLetter;

                    if ( a < inputString.length() - 2) {
                        ++a;
                    }
                    else {
                        break;
                    }
                }
            }

            arraySize = numOfDigits(numLetter);//call numOfDigits to find size for letter count

            char[] tempArray = new char[arraySize + 1];//temporary array to receive array from toCharArray
            tempArray = toCharArray(numLetter, inputString.charAt(a));

            if (numLetter == 1) {
                encodeArray[i] = new char[1];//only need spot for letter when coefficient of letter is 1
            }
            else {
                encodeArray[i] = new char[arraySize + 1];//add 1 because array has to have space for letter
            }

            ++a;//add 1 to a to so that loop will start from next index of inputString
            numLetter = 1;//rest number of letter to 1

            for (b = 0; b < tempArray.length; ++b) {
                encodeArray[i][b] = tempArray[b];
            }
        }

        return encodeArray;

    }
}
