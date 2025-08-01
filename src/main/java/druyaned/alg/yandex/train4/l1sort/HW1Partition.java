package druyaned.alg.yandex.train4.l1sort;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HW1Partition {
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    private static final char[] buf = new char[15];
    
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
        int lessCount = 0;
        int[] arr = new int[n];
        for (int i = 0; i < n; ++i) {
            arr[i] = readInt(reader);
        }
        int k = readInt(reader);
        for (int i = 0; i < n; ++i) {
            if (arr[i] < k) {
                ++lessCount;
            }
        }
        writer.write(lessCount + "\n" + (n - lessCount) + "\n");
        System.out.print(lessCount + "\n" + (n - lessCount) + "\n"); // TODO: debug
    }
    
}
