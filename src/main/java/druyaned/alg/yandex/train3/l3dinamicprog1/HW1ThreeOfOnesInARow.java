package druyaned.alg.yandex.train3.l3dinamicprog1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HW1ThreeOfOnesInARow {
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    private static final char[] buf = new char[16];
    
    private static int readInt(Reader reader) throws IOException {
        int c;
        while ((c = reader.read()) != -1 && c != '+' && c != '-' && !('0' <= c && c <= '9')) {}
        if (c == -1) {
            throw new NumberFormatException("empty input");
        }
        buf[0] = (char)c;
        int l = 1;
        while ((c = reader.read()) != -1 && '0' <= c && c <= '9') {
            buf[l++] = (char)c;
        }
        return Integer.parseInt(new String(buf, 0, l));
    }
    
    public static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        int n = readInt(reader);
        int[] dp = new int[n + 3];
        dp[1] = 2;
        dp[2] = 4;
        dp[3] = 7;
        for (int i = 4; i <= n; ++i) {
            dp[i] = dp[i-1] + dp[i-2] + dp[i-3];
        }
        writer.write(dp[n] + "\n");
    }
    
}
/*
В dp[n] буду хранить правильное количество.
Как я могу попасть в dp[i]? Если я к уже правильным
последовательностям добавляю 0, 01 и 011.
..... 0 dp[i-1]
.... 01 dp[i-2]
... 011 dp[i-3]

1
0

11
10
01
00

111 -
110
101
100
011
010
001
000
*/
