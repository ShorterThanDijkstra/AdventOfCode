import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;

public class Day3Part2 {
    private static boolean isGear(String pre, String curr, String next, int i) {
        if (curr.charAt(i) != '*') {
            return false;
        }
        int count = 0;
        if (pre != null) {
            if (Character.isDigit(pre.charAt(i))) {
                count += 1;
            } else {
                if (i > 0 && Character.isDigit(pre.charAt(i - 1))) {
                    count += 1;
                }
                if (i + 1 < pre.length() && Character.isDigit(pre.charAt(i + 1))) {
                    count += 1;
                }
            }
        }
        if (next != null) {
            if (Character.isDigit(next.charAt(i))) {
                count += 1;
            } else {
                if (i > 0 && Character.isDigit(next.charAt(i - 1))) {
                    count += 1;
                }
                if (i + 1 < next.length() && Character.isDigit(next.charAt(i + 1))) {
                    count += 1;
                }
            }
        }
        if (i > 0 && Character.isDigit(curr.charAt(i - 1))) {
            count += 1;
        }
        if (i + 1 < curr.length() && Character.isDigit(curr.charAt(i + 1))) {
            count += 1;
        }
        return count == 2;
    }

    private static int lexLine(String pre, String curr, String next, int lineNum) {
        int sum = 0;
        for (int i = 0; i < curr.length(); i++) {
            if (i + 1 == 66 && lineNum == 139) {
                System.out.println();
            }
            if (isGear(pre, curr, next, i)) {

                int ratio = gearRatio(pre, curr, next, i);
                sum += ratio;
                System.out.printf("line: %d, colum:%d, ration:%d\n", lineNum, i + 1, ratio);
            }
        }
        return sum;
    }

    private static int lexNum(String line, int start) {
        int num = 0;
        for (int i = start; i < line.length(); i++) {
            if (!Character.isDigit(line.charAt(i))) {
                break;
            }
            num = num * 10 + line.charAt(i) - '0';
        }
        return num;
    }

    private static int lexGearRatioFromLeft(String line, int i) {
        if (Character.isDigit(line.charAt(i))) {
            int start = i;
            while (start >= 0 && Character.isDigit(line.charAt(start))) {
                start -= 1;
            }
            return lexNum(line, start + 1);
        } else {
            int ratio = 1;
            if (i > 0 && Character.isDigit(line.charAt(i - 1))) {
                int start = i - 1;
                while (start >= 0 && Character.isDigit(line.charAt(start))) {
                    start -= 1;
                }
                ratio *= lexNum(line, start + 1);
            }
            if (i + 1 < line.length() && Character.isDigit(line.charAt(i + 1))) {
                ratio *= lexNum(line, i + 1);
            }
            return ratio;
        }
    }

    private static int gearRatio(String pre, String curr, String next, int i) {

        int ratio = 1;
        if (pre != null) {
            ratio *= lexGearRatioFromLeft(pre, i);
        }
        if (next != null) {
            ratio *= lexGearRatioFromLeft(next, i);
        }
        if (i > 0 && Character.isDigit(curr.charAt(i - 1))) {
            int start = i - 1;
            while (start >= 0 && Character.isDigit(curr.charAt(start))) {
                start -= 1;
            }
            ratio *= lexNum(curr, start+1);
        }
        if (i + 1 < curr.length() && Character.isDigit(curr.charAt(i + 1))) {
            ratio *= lexNum(curr, i + 1);
        }
        return ratio;
    }

    public static void main(String[] args) throws IOException {
        try (LineNumberReader fileReader = new LineNumberReader(new FileReader("src/input_day3.txt"))) {
            String pre = null;
            String curr = fileReader.readLine();
            int sum = 0;
            int lineNum = 1;
            while (curr != null) {
                String next = fileReader.readLine();
                sum += lexLine(pre, curr, next, lineNum);
                pre = curr;
                curr = next;
                lineNum += 1;
            }
            System.out.println(sum);
        }
    }
}
