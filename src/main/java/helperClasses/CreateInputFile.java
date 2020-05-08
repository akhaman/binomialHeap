package helperClasses;

import java.io.FileWriter;
import java.io.IOException;

/**
 * КЛАСС ДЛЯ СОЗДАНИЯ ПРОИЗВОЛЬНЫХ ЗНАЧЕНИЙ
 * randomHeapSize() - задает размер кучи (от 100 до 10100)
 * randomValue() - произвольное значение для каждого элемента (от -500 до 500)
 * Результаты сохраняются в файл "input.txt"
 */

public class CreateInputFile {
    static final int COLLECTION_SIZE = 100;

    private static int randomHeapSize() {
        int value = 100 + (int)(Math.random()*10000);
        return value;
    }

    private static int randomValue() {
        int value =-500 + (int)(Math.random()*1000);
        return value;
    }

    public static void main(String[] args) throws IOException {
        FileWriter fw = new FileWriter("input.txt");

        for (int i = 0; i < COLLECTION_SIZE; i++) {
            int setSize = randomHeapSize();
            for (int j = 0; j < setSize; j++) {
                fw.write(randomValue() + " ");
            }
            fw.write("\n");
        }
        fw.close();
    }
}
