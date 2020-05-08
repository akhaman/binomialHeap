import binomialHeap.BinomialHeap;
import helperClasses.Measurement;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * В ЭТОМ КЛАССЕ ПРОИСХОДИТ СЧИТЫВАЕНИЕ ЗНАЧИЕНИЙ ИЗ "input.txt" , ЗАПОЛНЕНИЕ СПИСКА ИЗ 100 БИНОМИАЛЬНЫХ КУЧ,
 * ВЫЧИСЛЕНИЕ ВРЕМЕНИ НА ОПЕРАЦИИ: ВСТАВКА, ПОИСК МИНИМУМА, СЛИЯНИЕ КУЧ, УДАЛЕНИЕ МИНИМУМА ДЛЯ КАЖДОЙ КУЧИ.
 * ВСЕ ВЫЧИСЛЕННЫЕ ЗНАЧЕНИЯ СОХРАНЯЮТСЯ В EXCEL-ТАБЛИЦУ "OUTPUT.XLS"
 *
 * ВХОДНЫЕ ДАННЫЕ: 100 НАБОРОВ ПО 100-10000 ЭЛЕМЕНТОВ
 *
 */

public class Main {

//    Считывание значений из файла
    private static ArrayList<ArrayList<Integer>> read(String path) throws FileNotFoundException {

        ArrayList<ArrayList<Integer>> matrix = new ArrayList();

        Scanner sc = new Scanner(new File(path));

        for (int i = 0; sc.hasNextLine(); i++) {
            Scanner s = new Scanner(sc.nextLine());

            ArrayList<Integer> row = new ArrayList();
            for (int j = 0; s.hasNextInt(); j++) {
                row.add(s.nextInt());
            }
            matrix.add(row);

            s.close();
        }

        sc.close();

        return matrix;
    }

    public static void main(String[] args) throws IOException {
        ArrayList<ArrayList<Integer>> values = read("input.txt");

        BinomialHeap<Integer>[] binomialHeapsArray = new BinomialHeap[values.size()];

        Measurement[] measurements = new Measurement[values.size()];



        int i = 0;
        for (ArrayList<Integer> line: values) { // Вставка элементов в кучи. Вычисление времени на вставку.

            binomialHeapsArray[i] = new BinomialHeap();
            measurements[i] = new Measurement();

            long start = System.nanoTime();

            for (int value: line) {

                binomialHeapsArray[i].insert(value);
            }

            long end = System.nanoTime();
            measurements[i].insertTime = end - start;

            i++;
        }



        i = 0;
        for(BinomialHeap heap: binomialHeapsArray) {    //Вычисление времени на поиск минимума
            long start = System.nanoTime();
            heap.min();
            long end = System.nanoTime();
            measurements[i].minTime = end - start;
            i++;
        }


        i = 0;
        for(BinomialHeap heap: binomialHeapsArray) {    // Вычисление времени на удаление минимального элемента
            long start = System.nanoTime();
            heap.delMin();
            long end = System.nanoTime();
            measurements[i].delMinTime = end - start;
            i++;
        }


        i = 0;
        for(BinomialHeap heap: binomialHeapsArray) {    // Вычисление времени запрос размера куч
            long start = System.nanoTime();
            measurements[i].size = heap.size();
            long end = System.nanoTime();
            measurements[i].sizeTime = end - start;
            i++;
        }


        ArrayList<ArrayList<Integer>> heapXList = read("heapX.txt");
        BinomialHeap<Integer> heapX = new BinomialHeap<>();

        for (ArrayList<Integer> line: heapXList) { // Вставка элементов в кучу heapX
            for (int value: line) {
                heapX.insert(value);
            }
        }


        BinomialHeap<Integer>[] secondBinomialHeapsArray = binomialHeapsArray;    // Вычисление времени на слияние 2-х куч
        for (i = 0; i < binomialHeapsArray.length; i++) {
            long start = System.nanoTime();
            binomialHeapsArray[i].merge(heapX);
            long end = System.nanoTime();
            measurements[i].unionTime = end - start;
        }

    /*
      Сохраниение результатов в таблицу   */

        Workbook book = new HSSFWorkbook();
        Sheet sheet = book.createSheet("Замеры 1");

        Row row = sheet.createRow(0);

        Cell number = row.createCell(0);
        number.setCellValue("Номер кучи");

        Cell currentSize = row.createCell(1);
        currentSize.setCellValue("Размер кучи");

        Cell insert = row.createCell(2);
        insert.setCellValue("Вставка элементов");

        Cell min = row.createCell(3);
        min.setCellValue("Поиск минимума");

        Cell delMin = row.createCell(4);
        delMin.setCellValue("Удаление минимума");

        Cell unionTime = row.createCell(5);
        unionTime.setCellValue("Слияние с кучей X");


        for (i = 0; i < measurements.length; i++) {
            Row line = sheet.createRow(i+1);
            
            Cell numberOfHeap = line.createCell(0);
            numberOfHeap.setCellValue(i+1);
            
            Cell heapSize = line.createCell(1);
            heapSize.setCellValue(measurements[i].size);

            Cell inserting = line.createCell(2);
            inserting.setCellValue(measurements[i].insertTime);

            Cell searchingMin = line.createCell(3);
            searchingMin.setCellValue(measurements[i].minTime);

            Cell deletingMin = line.createCell(4);
            deletingMin.setCellValue(measurements[i].delMinTime);

            Cell union= line.createCell(5);
            union.setCellValue(measurements[i].unionTime);
        }

        book.write(new FileOutputStream("Результаты.xls"));
        book.close();
    }

}
