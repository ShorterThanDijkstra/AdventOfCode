import java.io.*;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class Day4Part1 {
    public static void main(String[] args) {
        String file = "src/input_day4.txt";
        int lineNum = 1;
        try (LineNumberReader reader = new LineNumberReader(new FileReader(file))) {
            int sum = 0;
            String line = reader.readLine();
            while (line != null) {
                int points = lexLine(line);
                sum += points;
                line = reader.readLine();
                System.out.printf("line: %d, points: %d, total: %d\n", lineNum, points, sum);
                lineNum += 1;
            }

            System.out.println(sum);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static int lexLine(String line) {
        int winningStart = 0;
        int len = line.length();
        while (winningStart < len && line.charAt(winningStart) != ':') {
            winningStart += 1;
        }
        while (winningStart < len && !Character.isDigit(line.charAt(winningStart))) {
            winningStart += 1;
        }
        int havingStart = winningStart;
        while (havingStart < len && line.charAt(havingStart) != '|') {
            havingStart += 1;
        }
        String winningStr = line.substring(winningStart, havingStart - 1);
        Set<Integer> winningNums = lexNums(winningStr);
        while (havingStart < len && !Character.isDigit(line.charAt(havingStart))) {
            havingStart += 1;
        }
        String havingStr = line.substring(havingStart);
        Set<Integer> havingNums = lexNums(havingStr);

        havingNums.retainAll(winningNums);
        int points = 0;
        if (!havingNums.isEmpty()) {
            points = 1 << havingNums.size() - 1;
        }
        return points;
    }

    private static Set<Integer> lexNums(String substring) {
        String[] split = substring.split("\\s+", -1);
        Set<Integer> res = Arrays.stream(split)
                .map((it) -> it.chars()
                        .reduce(0, (left, right) -> left * 10 + right - '0'))
                .collect(Collectors.toSet());
        assert split.length == res.size();
        return res;
    }

}

