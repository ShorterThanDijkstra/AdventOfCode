import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;

public class Day3Part1 {
    private static int lexLine(String pre, String curr, String next) {
        int sum = 0;
        int start = 0;
        int end = 0;
        int len = curr.length();
        while (start < len) {
            int num = 0;
            while (start < len && !Character.isDigit(curr.charAt(start))) {
                start += 1;
                end += 1;
            }
            while (end < len && Character.isDigit(curr.charAt(end))) {
                num = num * 10 + curr.charAt(end) - '0';
                end += 1;
            }
            boolean rightAdj = end < len - 1 && curr.charAt(end) != '.';
            boolean leftAdj = start > 0 && curr.charAt(start - 1) != '.';
            boolean topAdj = topOrBottomAdj(pre, start - 1, end + 1);
            boolean bottomAdj = topOrBottomAdj(next, start - 1, end + 1);
            if (rightAdj || leftAdj || topAdj || bottomAdj) {
                sum += num;
            }
            start = end;
        }
        return sum;
    }

    private static boolean topOrBottomAdj(String line, int start, int end) {
        if (line == null) {
            return false;
        }
        for (int i = start; i < end; i++) {
            if (i > 0 && i < line.length() && line.charAt(i) != '.' && !Character.isDigit(line.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) throws IOException {
        try (LineNumberReader fileReader = new LineNumberReader(new FileReader("src/input_day3.txt"))) {
            String pre = null;
            String curr = fileReader.readLine();
            int sum = 0;
            while (curr != null) {
                String next = fileReader.readLine();
                sum += lexLine(pre, curr, next);
                pre = curr;
                curr = next;
            }
            System.out.println(sum);
        }
    }
}
