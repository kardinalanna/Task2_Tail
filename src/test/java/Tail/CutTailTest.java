package Tail;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;

class CutTailTest {


    private boolean assertArrayEquals(ArrayList<String> expected, String[] actual) {
        if (expected.size() == actual.length) {
            for (int i = 0; i < actual.length; i++) {
                if (!expected.get(i).equals(actual[i])) throw new AssertionError();
            }
        } else throw new AssertionError();
        return true;
    }

    private void assertFileEquals(File expected, File actual) throws IOException {
        try (BufferedReader reader1 = new BufferedReader(new FileReader(expected));
             BufferedReader reader2 = new BufferedReader(new FileReader(actual));) {
            while (true) {
                String s1 = reader1.readLine();
                String s2 = reader2.readLine();
                if (s1 == null || s2 == null) return;
                if (!s1.equals(s2)) throw new AssertionError();
            }
        }
    }

    private File writeFile(String name, String plot) throws IOException {
        File file = new File(name);
        FileWriter writer = new FileWriter(file);
        writer.write(plot);
        writer.flush();
        return file;
    }

    @Test
    void cut() throws IOException {
        CutTail object = new CutTail(new File[]{new File("text.txt")}, new File("out.txt"), 5, 0);
        ArrayList<String> a = new ArrayList<String>();
        File thisFile = writeFile("test.txt", " Но если он всегда смотрит в лицо смерти, он не будет привязан к вещам и не проявит неуемности и жадности, станет, как я говорил прежде, прекрасным человеком. Что касается размышления о смерти, то Ёсида Кэнко в \"Цурэдзурэ-гуса\"\n" +
                " говорит, что монах Синкай имел обыкновение сидеть днями напролет, размышляя о своем конце; несомненно, это очень удобный Для отшельника, но не для воина.\n" +
                "      Ведь тогда он должен был бы пренебречь своим военным долгом и отказаться от пути верности и сыновней почтительности. Самурай же, наоборот, должен постоянно быть занят и общественным, и личным. Но когда бы у него не\n" +
                " появлялось немного времени для себя, чтобы побыть в безмолвии, он не должен забывать возвращаться к вопросу о смерти и размышлять о ней. Разве не сказано, что Кусуноки Масасигэ увещевал своего сына Масацуру всегда помнить о смерти?\n" +
                " Все это предназначено для обучения юного самурая.\n");
        assertFileEquals(thisFile, object.returnFile(true));

        CutTail object1 = new CutTail(new File[]{new File("text.txt"), new File("tyt.txt")}, new File("out.txt"), 5, 0);
        assertFileEquals(writeFile("test.txt", "text.txt\n" + " Но если он всегда смотрит в лицо смерти, он не будет привязан к вещам и не проявит неуемности и жадности, станет, как я говорил прежде, прекрасным человеком. Что касается размышления о смерти, то Ёсида Кэнко в \"Цурэдзурэ-гуса\"\n" +
                " говорит, что монах Синкай имел обыкновение сидеть днями напролет, размышляя о своем конце; несомненно, это очень удобный Для отшельника, но не для воина.\n" +
                "      Ведь тогда он должен был бы пренебречь своим военным долгом и отказаться от пути верности и сыновней почтительности. Самурай же, наоборот, должен постоянно быть занят и общественным, и личным. Но когда бы у него не\n" +
                " появлялось немного времени для себя, чтобы побыть в безмолвии, он не должен забывать возвращаться к вопросу о смерти и размышлять о ней. Разве не сказано, что Кусуноки Масасигэ увещевал своего сына Масацуру всегда помнить о смерти?\n" +
                " Все это предназначено для обучения юного самурая.\n" + "tyt.txt\n" + "На этом остановилось дело на мосту ночью. Поутру, в гостинице у Московской железной дороги, обнаружилось, что дурак не подурачился, а застрелился. Но \n" +
                "остался в результате истории элемент, с которым были согласны и побежденные, именно, что если и не пошалил, а застрелился, то все-таки дурак. Этот \n" +
                "удовлетворительный для всех результат особенно прочен был именно потому, что восторжествовали консерваторы: в самом деле, если бы только пошалил \n" +
                "выстрелом на мосту, то ведь, в сущности, было бы еще сомнительно, дурак ли, или только озорник. Но застрелился на мосту — кто же стреляется на мосту? \n" +
                "как же это на мосту? зачем на мосту? глупо на мосту! — и потому, несомненно, дурак.\n"), object1.returnFile(true));

        CutTail object2 = new CutTail(new File[]{new File("tyt.txt")}, new File("out.txt"), 0, 33);
        assertFileEquals(writeFile("test.txt", "у! — и потому, несомненно, дурак."), object2.returnFile(true));
    }
}
