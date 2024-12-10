import java.io.FileReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;

sealed interface Inst permits Mul {
}

record Mul(int a, int b) implements Inst {
}

public class Day3 {
    private final List<String> lines;
    private int idx;

    public Day3() {
        lines = new ArrayList<>();
        try (LineNumberReader reader = new LineNumberReader(new FileReader("input_day3.txt"))) {
            String line = reader.readLine();
            while (null != line) {
                lines.add(line);
                line = reader.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Inst> parse() {
        idx = 0;
        String text = String.join("", lines);
        text = text.replaceAll("\n", "").replaceAll("\r", "").replaceAll(" ", "");
        int i = 0;
        while (i < text.length()) {
            if (text.startsWith("mul(", i)) {
                int a = number(text);
            }
        }
    }

    private int number(String text) {
        int res = 0;
        int i = idx;
        while (i < text.length() && Character.isDigit(text.charAt(i))) {
            res = res * 10 + text.charAt(i) - '0';
            i += 1;
        }
        idx = i;
        return res;
    }

    public static void main(String[] args) {
        Day3 day3 = new Day3();
        day3.parse();
    }
}
