package Tail;

import java.io.*;
import java.util.Scanner;

public class CutTail {
     String[]inputName;
     String outputName;

    CutTail(String[] inName, String outName) {
        this.inputName = inName;
        this.outputName = outName;

    }
    private String[] enterFromConsole() {  //если поленились указать имя как параметр, считываем с консольного ввода
            int i = 0;
            Scanner scan = new Scanner(System.in);
            System.out.println("Ведите имя входного файла");
            while (scan.hasNextLine()) {
                inputName[i] = scan.nextLine();
                i++;
            }
        return inputName;
    }

    private void readFile() throws FileNotFoundException {
        for (String o: inputName // проверяем, существуют ли такие файлы
             ) {
            try {
                FileReader c = new FileReader(o);
            }
            catch (FileNotFoundException e) {
               System.err.println(e.getMessage());
                return;
            }
        }
    }



public String[] cut(int countOfLines, int countOfSymbols) throws IOException { // основной метод, котороый возвращает массив отрезанных строк/символов + проверяет корректность входных данных
        if (inputName == null){
            inputName = enterFromConsole();
        }
        readFile();

        if (countOfLines != 0 && countOfSymbols != 0) {
            System.out.println("Одновременно введены параметры '-n' и '-c'");
            throw new IOException();
        }
        String[] allOutFile = new String[inputName.length]; //массив выходных данных
        if (countOfLines != 0) {                        // если нужно отрзать послендние n строк
                for (int y = 0; y < inputName.length; y++) {
                    Scanner scan = new Scanner( new FileReader(inputName[y]));
                    StringBuilder miniOutFile = new StringBuilder();
                    int nun = 0;
                    while (scan.hasNextLine()) {
                        scan.nextLine();//считаем кол-во строк
                        nun++;
                    }
                    BufferedReader read = new BufferedReader( new FileReader(inputName[y]));
                    int cNun = nun - countOfLines;
                    for (int t = 0; t < nun; t++ ){
                        String thisLine = read.readLine();
                        if (t >= cNun){
                           miniOutFile.append(thisLine).append("\n"); //начиная с определенной строки кладем все остальные в StringBuilder
                            }
                        }
                    allOutFile[y] = miniOutFile.toString();
                    }
                } else
    if (countOfSymbols != 0) {
        for (int y = 0; y < inputName.length; y++) {
            FileReader read = new FileReader(inputName[y]);
            Scanner scan = new Scanner(read);
            BufferedReader read2 = new BufferedReader(new FileReader(inputName[y]));
            StringBuilder miniOutFile = new StringBuilder(); //одному входному файлу соответствует один StringBuffer
            int sizeOfAllSymbols = 0;
            int nun = 0;
            while (scan.hasNextLine()) {
                sizeOfAllSymbols += scan.nextLine().length(); //посчитали количество символов в файле
                nun++;
            }
            // отрезаем нужное кол-во символов, накапливая их в StringBuilder, потом кладем их в ячейку масссива
            // ( итого: массив входнах файлов соответствует массиву, в котором лежит содержимое будущих выходных файлов)
            int nowLengths = 0;
            int thisLineLengths = 0;
            String thisLine = "";
            boolean flag = true;   // если нам нужно отрезать часть строки, а потом еще несколько строк, обрабатываем отрезания кусочка, а остальные строкм просто добавляем
            for(int i = 0; i<nun; i++){
                thisLine = read2.readLine();
                thisLineLengths = thisLine.length();
                nowLengths += thisLineLengths;
                int dif = sizeOfAllSymbols - countOfSymbols;
                if (nowLengths >= dif){
                    if (flag) {
                        miniOutFile.append(thisLine.substring(thisLineLengths - (nowLengths - dif)));
                        flag = false;
                    }
                     else {
                        miniOutFile.append(thisLine).append("\n");;
                    }
                }
            }
            allOutFile[y] = miniOutFile.toString();
        }
    } else
    {
        String[] lastTen = cut(10,0);   // если флаги не указаны выводим на консоль 10 последних строк
        allOutFile = new String[]{};
        for (String y : lastTen
             ) {
            System.out.println(y);
        }
    }
        return allOutFile;
            }

        }

