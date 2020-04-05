package Tail;

import org.junit.jupiter.api.Test;

import java.io.IOException;

class CommandLineArgumentTest {
CommandLineArgument u = new CommandLineArgument();
    CutTail it = new CutTail(new String[]{"tyt.txt"},"out.txt" );
@Test
void separate(String[] args) throws IOException {
    String[] t = it.cut(10, 0);
     u.separate(t);
    }
}
