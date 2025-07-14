package druyaned.alg.yandex.contest2025.attempt01;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.UncheckedIOException;
import static java.lang.Character.isWhitespace;
import java.nio.file.Files;
import java.nio.file.Path;

public class Task01 {
    
    public static void main(String[] args) {
        try {
            solve(new Sio(System.in, System.out));
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }
    
    private static void solve(Sio sio) throws IOException {
        int n = sio.readInt();
        int m = sio.readInt();
        int x = sio.readInt();
        int y = sio.readInt();
        int target = (x * y + 1) / 2;
        int count = 0;
        char[][] house = new char[n * x][m * y];
        for (int i = 0; i < n * x; i++) {
            char[] floor = sio.readLine().toCharArray();
            System.arraycopy(floor, 0, house[i], 0, m * y);
        }
        for (int i = 0; i < n * x; i += x) {
            for (int j = 0; j < m * y; j += y) {
                int apartmentCount = 0;
                for (int r = i; r < i + x; r++) {
                    for (int c = j; c < j + y; c++) {
                        if (house[r][c] == 'X')
                            apartmentCount++;
                    }
                }
                if (apartmentCount >= target)
                    count++;
            }
        }
        sio.writeln(Integer.toString(count));
        sio.writerFlush();
    }
    
    private static class Sio {
        public static final int BUFFER_SIZE = 1 << 23;
        private final char[] buffer = new char[BUFFER_SIZE];
        private final BufferedReader reader;
        private final BufferedWriter writer;
        public Sio(BufferedReader reader, BufferedWriter writer) {
            this.reader = reader;
            this.writer = writer;
        }
        public Sio(InputStream in, PrintStream out) {
            reader = new BufferedReader(new InputStreamReader(System.in));
            writer = new BufferedWriter(new OutputStreamWriter(System.out));
        }
        public Sio(String fileIn, String fileOut) throws IOException {
            Path fin = Path.of(fileIn);
            Path fout = Path.of(fileOut);
            reader = Files.newBufferedReader(fin);
            writer = Files.newBufferedWriter(fout);
        }
        public int readInt() throws IOException {
            int bufferLength = putNextTokenToBuffer();
            return Integer.parseInt(new String(buffer, 0, bufferLength));
        }
        public long readLong() throws IOException {
            int bufferLength = putNextTokenToBuffer();
            return Long.parseLong(new String(buffer, 0, bufferLength));
        }
        public double readDouble() throws IOException {
            int bufferLength = putNextTokenToBuffer();
            return Double.parseDouble(new String(buffer, 0, bufferLength));
        }
        public String readToken() throws IOException {
            int bufferLength = putNextTokenToBuffer();
            return (bufferLength != 0) ? new String(buffer, 0, bufferLength) : null;
        }
        public String readLine() throws IOException {
            int bufferLength = putNextLineToBuffer();
            return (bufferLength != 0) ? new String(buffer, 0, bufferLength) : "";
        }
        public String[] readLineTokens() throws IOException {
            int bufferLength = putNextLineToBuffer();
            // find begin and end
            int begin = 0;
            while (begin < bufferLength && isWhitespace(buffer[begin])) {
                begin++;
            }
            int end = bufferLength;
            while (end > 0 && isWhitespace(buffer[end - 1])) {
                end--;
            }
            // count tokens
            int tokenCount = 0;
            for (int i = begin; i < end;) {
                while (i < end && !isWhitespace(buffer[i])) {
                    i++;
                }
                tokenCount++;
                while (i < end && isWhitespace(buffer[i])) {
                    i++;
                }
            }
            // make tokens
            String[] tokens = new String[tokenCount];
            for (int i = begin, k = 0; i < end; k++) {
                int offset = i;
                while (i < end && !isWhitespace(buffer[i])) {
                    i++;
                }
                tokens[k] = new String(buffer, offset, i - offset);
                while (i < end && isWhitespace(buffer[i])) {
                    i++;
                }
            }
            return tokens;
        }
        public void write(char ch) throws IOException {
            writer.write(ch);
        }
        public void write(String s) throws IOException {
            writer.write(s);
        }
        public void write(Object obj) throws IOException {
            writer.write(obj.toString());
        }
        public void writeln() throws IOException {
            writer.write('\n');
        }
        public void writeln(String s) throws IOException {
            writer.write(s + '\n');
        }
        public void writeln(Object obj) throws IOException {
            writer.write(obj.toString() + '\n');
        }
        public void writef(String format, Object... args) throws IOException {
            writer.write(String.format(format, args));
        }
        public void writerFlush() throws IOException {
            writer.flush();
        }
        // following after the token whitespace is also read
        private int putNextTokenToBuffer() throws IOException {
            int ch;
            // skip whitespaces
            while ((ch = reader.read()) != -1 && isWhitespace(ch)) {}
            if (ch == -1) {
                return 0;
            }
            // putting the token
            buffer[0] = (ch == '\u2013') ? '-' : (char)ch;
            int bufferLength = 1;
            while ((ch = reader.read()) != -1 && !isWhitespace(ch)) {
                buffer[bufferLength++] = (char)ch;
            }
            return bufferLength;
        }
        // following after the line '\n' is also read
        private int putNextLineToBuffer() throws IOException {
            int ch;
            int bufferLength = 0;
            while ((ch = reader.read()) != -1 && ch != '\n') {
                buffer[bufferLength++] = (char)ch;
            }
            return bufferLength;
        }
    }
    
}
