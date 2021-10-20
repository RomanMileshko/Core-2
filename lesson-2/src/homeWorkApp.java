public class homeWorkApp {

    public static void main(String[] args) {

        String[][] goodArray = {{"1", "2", "3", "4"},
                {"1", "2", "3", "4"},
                {"1", "2", "3", "4"},
                {"1", "2", "3", "4"}
        };
        String[][] badSizeArray = {{"1", "2", "3", "4"},
                {"1", "2", "4"},
                {"1", "2", "3", "4"},
                {"1", "2", "3", "4"}
        };

        String[][] badDigitArray = {{"1", "2", "3", "4"},
                {"1", "2", "o", "4"},
                {"1", "2", "3", "4"},
                {"1", "2", "3", "4"}
        };

        checkAndSumArray(goodArray);
        System.out.println("----------------------");
        checkAndSumArray(badSizeArray);
        System.out.println("----------------------");
        checkAndSumArray(badDigitArray);
        System.out.println("----------------------");
        System.out.println("Все..");
    }

    public static void checkAndSumArray(String[][] dataArr) {
        try {
            checkArrayException(dataArr);
            System.out.println("Размер массива верен!");
            try {
                sumArrayException(dataArr);
            } catch (MyArrayDataException e) {
                e.printStackTrace();
            }
        } catch (MyArraySizeException e) {
            e.printStackTrace();
        }

    }

    public static void checkArrayException(String[][] arr) throws MyArraySizeException {
        if (arr.length != 4) {
            throw new MyArraySizeException("Неверное количество строк");
        }
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].length != 4)
                throw new MyArraySizeException(String.format("Неверное количество элементов в %d-й строке", i + 1));
            for (int j = 0; j < arr[i].length; j++) {
                System.out.printf("%2s", arr[i][j]);
            }
            System.out.println();
        }
    }

    public static void sumArrayException(String[][] arr) throws MyArrayDataException {
        int sum = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                try {
                    sum += Integer.parseInt(arr[i][j]);
                } catch (NumberFormatException e) {
                    throw new MyArrayDataException(String.format("Неверный элемент в позиции %d : %d", j + 1, i + 1));
                }
            }
        }
        System.out.println("Сумма массива: " + sum);

    }


    static class MyArraySizeException extends Exception {
        public MyArraySizeException(String message) {
            super(message);
        }
    }

    static class MyArrayDataException extends Exception {
        public MyArrayDataException(String message) {
            super(message);
        }
    }
}