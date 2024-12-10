import java.io.FileReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;

public class Day3 {
    private final String text;
    private int idx;

    public Day3(String fileName) {
        List<String> lines = new ArrayList<>();
        try (LineNumberReader reader = new LineNumberReader(new FileReader(fileName))) {
            String line = reader.readLine();
            while (null != line) {
                lines.add(line);
                line = reader.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        text = String.join("", lines).replaceAll("\n", "").replaceAll("\r", "").replaceAll(" ", "");
    }

    private int addInsts(List<Inst> insts) {
        int res = 0;
        for (Inst inst : insts) {
            res += inst.eval();
        }
        return res;
    }

    public int part1() {
        return addInsts(parse1());
    }

    public int part2() {
        return addInsts(parse2());
    }

    private List<Inst> parse2() {
        idx = 0;
        String prefix = "mul";
        String disable = "don't()";
        String enable = "do()";

        List<Inst> res = new ArrayList<>();
        while (idx < text.length()) {
            if (text.startsWith(prefix, idx)) {
                idx += prefix.length();
                if (!character('(')) {
                    continue;
                }
                int a = number();
                if (!character(',')) {
                    continue;
                }
                int b = number();
                if (!character(')')) {
                    continue;
                }
                res.add(new Mul(a, b));
            } else if (text.startsWith(disable, idx)) {
                idx += disable.length();
                until(enable);
            } else {
                idx += 1;
            }
        }
        return res;
    }

    private void until(String str) {
        while (idx < text.length() && !text.startsWith(str, idx)) {
            idx += 1;
        }
        if (text.startsWith(str, idx)) {
            idx += str.length();
        }
    }

    private List<Inst> parse1() {
        idx = 0;
        String prefix = "mul";
        List<Inst> res = new ArrayList<>();
        while (idx < text.length()) {
            if (text.startsWith(prefix, idx)) {
                idx += prefix.length();
                if (!character('(')) {
                    continue;
                }
                int a = number();
                if (!character(',')) {
                    continue;
                }
                int b = number();
                if (!character(')')) {
                    continue;
                }
                res.add(new Mul(a, b));
            } else {
                idx += 1;
            }
        }
        return res;
    }

    private boolean character(char c) {
        if (text.charAt(idx) == c) {
            idx += 1;
            return true;
        }
        return false;
    }

    private int number() {
        boolean neg = false;
        int res = 0;
        if (idx < text.length() && text.charAt(idx) == '-') {
            idx += 1;
            neg = true;
        }
        if (idx < text.length() && text.charAt(idx) == '+') {
            idx += 1;
        }
        while (idx < text.length() && Character.isDigit(text.charAt(idx))) {
            res = res * 10 + text.charAt(idx) - '0';
            idx += 1;
        }
        if (neg) {
            res = -res;
        }
        return res;
    }

    public static void main(String[] args) {
        Day3 day3 = new Day3("input_day3.txt");
        System.out.println(day3.part1());
        System.out.println(day3.part2());
    }
}
sealed interface Inst permits Mul {
    int eval();
}

record Mul(int a, int b) implements Inst {

    @Override
    public int eval() {
        return a * b;
    }
}
