import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;

public class Day5Part2 {
    private static final int MAP_SIZE = 7;

    static class Pair {
        public final long start;
        public final long range;

        Pair(long start, long range) {
            this.start = start;
            this.range = range;
        }
    }

    static class Record {
        public final long dest;
        public final long src;
        public final long range;

        public Record(long dest, long src, long range) {
            this.dest = dest;
            this.src = src;
            this.range = range;
        }
    }

    static class CategoryMap {
        public final List<Record> records = new ArrayList<>();

        List<Pair> convert(Pair source) {
            List<Pair> res = new ArrayList<>();
            for (Record record : records) {

            }
            return res;
        }
    }

    static List<Pair> parseSeeds(String line) {
        List<Long> nums = parseNums(line);
        assert nums.size() % 2 == 0;
        int i = 0;
        List<Pair> res = new ArrayList<>();
        while (i < nums.size()) {
            res.add(new Pair(nums.get(i), nums.get(i + 1)));
            i += 2;
        }
        return res;
    }

    public static void main(String[] args) {
//        String file = "E:\\IdeaProjects\\WordParser\\src\\main\\kotlin\\small_test.txt";
        String file = "src/input_day5.txt";
        try (LineNumberReader reader = new LineNumberReader(new FileReader(file))) {
            String line = reader.readLine();
            List<Pair> seeds = parseSeeds(line);
            reader.readLine();
            List<CategoryMap> categoryMaps = new ArrayList<>(MAP_SIZE);
            for (int i = 0; i < MAP_SIZE; i++) {
                reader.readLine();
                CategoryMap map = parseCategoryMap(reader);
                categoryMaps.add(map);
            }
//            long minLocation = Long.MAX_VALUE;
//            for (long seed : seeds) {
//                long tmp = seed;
//                for (int i = 0; i < MAP_SIZE; i++) {
//                    tmp = categoryMaps.get(i).convert(tmp);
//                }
//                if (tmp < minLocation) {
//                    minLocation = tmp;
//                }
//            }
//            System.out.println(minLocation);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static CategoryMap parseCategoryMap(LineNumberReader reader) throws IOException {
        String line = reader.readLine();
        CategoryMap result = new CategoryMap();
        while (line != null && !line.trim().isEmpty()) {
            List<Long> nums = parseNums(line);
            if (nums.size() != 3) {
                throw new RuntimeException(line);
            }
            result.records.add(new Record(nums.get(0), nums.get(1), nums.get(2)));
            line = reader.readLine();
        }
        return result;
    }

    private static List<Long> parseNums(String line) {
        int start = 0;
        while (start < line.length() && !Character.isDigit(line.charAt(start))) {
            start += 1;
        }
        String[] split = line.substring(start).split("\\s+");
        List<Long> seeds = new ArrayList<>(split.length);
        for (String s : split) {
            long seed = 0;
            for (int i = 0; i < s.length(); i++) {
                seed = seed * 10 + s.charAt(i) - '0';
            }
            seeds.add(seed);
        }
        return seeds;
    }
}

