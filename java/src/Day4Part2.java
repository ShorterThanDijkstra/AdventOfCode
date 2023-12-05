import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Day4Part2 {
    public static void main(String[] args) {
        String file = "src/input_day4.txt";
        int lineNum = 1;
        Map<Integer, Integer> instances = new HashMap<>();
        try (LineNumberReader reader = new LineNumberReader(new FileReader(file))) {
            String line = reader.readLine();
            while (line != null) {
                lexLine(line, instances, lineNum);
                line = reader.readLine();
                lineNum += 1;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        int sum = instances.values().stream().reduce(0, Integer::sum);
        System.out.println(sum);
    }

    private static void lexLine(String line, Map<Integer, Integer> instances, int lineNum) {
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
        int points = havingNums.size();

        int copied = instances.getOrDefault(lineNum, 0);
        int currInstances = copied + 1;
        instances.put(lineNum, currInstances);
        for (int i = lineNum + 1; i <= lineNum + points; i++) {
            Integer oldVal = instances.getOrDefault(i, 0);
            instances.put(i, oldVal + currInstances);
        }

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

