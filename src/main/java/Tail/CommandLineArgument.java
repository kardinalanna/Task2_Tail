package Tail;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CommandLineArgument {
    @Argument(required = false, metaVar = "inputName", usage = "input File Name")
    String[] inName;
    @Option(name = "-o", metaVar = "OutName", required = false, usage = "output File Name")
    String outName;
    @Option(name = "-c", metaVar = "countS", required = false, usage = "cut symbols")
    int countOfSymbols;
    @Option(name = "-n", metaVar = "countL", required = false, usage = "cut liens")
    int countOfLiens;


    public void separate(String[] args) {
        CmdLineParser parser = new CmdLineParser(this);
        try {
            parser.parseArgument(args);

            CutTail tail = new CutTail(inName, outName); // здесь генерируем выходные файлы используя массив с отрезанной частью из метода сut
            String[] array = tail.cut(countOfLiens, countOfSymbols);
            if (array.length == 0) return;
            for (Integer i = 0; i < inName.length; i++) {
                if (outName != null) {
                    FileWriter writer = new FileWriter(new File("C://javaFile//" + outName + i + ".txt"));
                    writer.write(array[i]);
                    writer.flush();
                    if (inName.length > 1) System.out.println(inName[i]);
                    System.out.println(new File("C://javaFile//" + outName.replaceAll(".txt", i.toString()) + ".txt"));
                } else {
                    if (inName.length > 1) System.out.println(inName[i]);
                    System.out.println(array[i]);
                }

            }
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            System.out.println("java -jar tail.jar Input_File_Name -c/-n Count_Of_Symbols/Lines -o Out_File_Name");
            parser.printUsage(System.err);
            return;
        }
        catch (IOException e){
            return;
        }
    }
}
