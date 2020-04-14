package Tail;

import java.io.*;
import java.util.Scanner;

public class CutTail {
    private File[] inputName;
    private File outputName;
    private int countOfSymbols;
    private int countOfLines;

    CutTail(File[] inName, File outName, int countOfLines, int countOfSymbols) {
        this.inputName = inName;
        this.outputName = outName;
        this.countOfSymbols = countOfSymbols;
        this.countOfLines = countOfLines;
    }

    private void enterFromConsole() throws IOException {
        inputName = new File[]{new File("in.txt")}; //если inputName не был задан, то считываем стоки/строчки из комондной строки
        System.out.println("Ведите данные. Чтобы заканчить ввод, введите пустую строку.");
        try (Scanner scan = new Scanner(System.in);
             FileWriter writer = new FileWriter(inputName[0])) {
            while (true) {
                String string = scan.nextLine();
                if (string.equals("")) break;
                writer.write(string + System.lineSeparator());
            }
        }
    }

    private String[] cut(int countOfLines, int countOfSymbols) throws IOException { // основной метод, котороый возвращает массив отрезанных строк/символов + проверяет корректность входных данных
        if (inputName == null) enterFromConsole();
        String[] allOutFile = new String[inputName.length];
        for (int y = 0; y < inputName.length; y++) {
            StringBuilder miniOutFile = new StringBuilder();
            try (Scanner scan = new Scanner(new FileReader(inputName[y]));
                 BufferedReader read = new BufferedReader(new FileReader(inputName[y]))) {
                int nun = 0;
                int sizeOfAllSymbols = 0;
                while (scan.hasNextLine()) {
                    sizeOfAllSymbols += scan.nextLine().length(); //посчитали количество символов в файле
                    nun++;                                        //посчитали коливество строк в файле
                }
                if (countOfLines != 0) {
                    int cNun = nun - countOfLines;
                    for (int t = 0; t < nun; t++) {
                        String thisLine = read.readLine();
                        if (t >= cNun) {
                            miniOutFile.append(thisLine).append(System.lineSeparator()); //начиная с определенной строки кладем все остальные в StringBuilder
                        }
                    }
                    allOutFile[y] = miniOutFile.toString();
                } else if (countOfSymbols != 0) {
                    int nowLengths = 0;       // отрезаем нужное кол-во символов, накапливая их в StringBuilder, потом кладем их в ячейку масссива
                    int thisLineLengths;  // ( итого: массив входнах файлов соответствует массиву, в котором лежит содержимое будущих выходных файлов)
                    String thisLine;
                    boolean flag = true;      // если нам нужно отрезать часть строки, а потом еще несколько строк, обрабатываем отрезания кусочка, а остальные строкм просто добавляем
                    for (int i = 0; i < nun; i++) {
                        thisLine = read.readLine();
                        thisLineLengths = thisLine.length();
                        nowLengths += thisLineLengths;
                        int dif = sizeOfAllSymbols - countOfSymbols;
                        if (nowLengths >= dif) {
                            if (flag) {
                                miniOutFile.append(thisLine.substring(thisLineLengths - (nowLengths - dif))).append(System.lineSeparator());
                                flag = false;
                            } else {
                                miniOutFile.append(thisLine).append(System.lineSeparator());
                            }
                        }
                    }
                }
                allOutFile[y] = miniOutFile.toString();
            }
        }
        return allOutFile;
    }


    public File returnFile(boolean outExist) throws IOException {   //выводи одни файл, содержащий отркзанные части
        String[] array = cut(countOfLines, countOfSymbols);
        try {
            return write(array);
        } catch (NullPointerException e) {
            for (int i = 0; i < array.length; i++) {
                if (array.length > 1) System.out.println(inputName[i].getName() + System.lineSeparator() + array[i]);
                else System.out.println(array[i]);
            }
        }
        return outputName;
    }

    private File write(String[] array) throws IOException {
        FileWriter writer = new FileWriter(outputName);
        for (int i = 0; i < array.length; i++) {
            if (array.length > 1) {
                writer.write(inputName[i].getName() + System.lineSeparator() + array[i]);
            } else writer.write(array[i]);
        }
        writer.flush();
        return outputName;
    }


}