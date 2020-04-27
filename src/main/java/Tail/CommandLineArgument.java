package Tail;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.File;
import java.io.IOException;

public class CommandLineArgument {
    @Argument(metaVar = "inputName", usage = "input File Name")
    private
    File[] inName;
    @Option(name = "-o", metaVar = "OutName", usage = "output File Name")
    private
    File outName;
    @Option(name = "-c", metaVar = "countS", forbids = {"-n"}, usage = "cut symbols")
    private
    int countOfSymbols;
    @Option(name = "-n", metaVar = "countL", forbids = {"-c"}, usage = "cut liens")
    private
    int countOfLiens;

    public CommandLineArgument() {
    }

    public static void main(String[] args) {
        new CommandLineArgument().separate(args);
    }

    public void separate(String[] args) {
        CmdLineParser parser = new CmdLineParser(this);
        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            System.out.println("java -jar tail.jar Input_File_Name -c/-n Count_Of_Symbols/Lines -o Out_File_Name");
            parser.printUsage(System.err);
            return;
        }
        try {
            if (countOfLiens < 0 || countOfSymbols < 0) { //в случае отрицательных згпачений -n или -с
                System.out.println("Количество отрезаемых строк или символов не может быть отрицательным");
                return;
            }
            boolean outExist = false;
            if (outName != null) outExist = true;
            if (countOfLiens == 0 && countOfSymbols == 0) countOfLiens = 10;
            Cutter tail = new Cutter(inName, outName, countOfLiens, countOfSymbols);
            if (tail.returnFile(outExist) != null) System.out.println(tail.returnFile(outExist));
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
