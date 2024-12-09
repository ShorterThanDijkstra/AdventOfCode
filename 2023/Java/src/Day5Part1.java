import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;

public class Day5Part1 {
//    private static String SEED_TO_SOIL = "seed-to-soil map:";
//    private static String SOIL_TO_FERTILIZER = "soil-to-fertilizer map:";
//    private static String FERTILIZER_TO_WATER = "fertilizer-to-water map:";
//    private static String WATER_TO_LIGHT = "water-to-light map:";
//    private static String LIGHT_TO_TEMPERATURE = "light-to-temperature map:";
//    private static String TEMPERATURE_TO_HUMIDITY = "temperature-to-humidity map:";
//    private static String HUMIDITY_TO_LOCATION = "humidity-to-location map:";

    private static final int MAP_SIZE = 7;

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

        long convert(long source) {
            for (Record record : records) {
                long diff = source - record.src;
                if (diff >= 0 && diff < record.range) {
                    return record.dest + diff;
                }
            }
            return source;
        }
    }

    public static void main(String[] args) {
        String file = "src/input_day5.txt";
        try (LineNumberReader reader = new LineNumberReader(new FileReader(file))) {
            String line = reader.readLine();
            List<Long> seeds = parseNums(line);
            reader.readLine();
            List<CategoryMap> categoryMaps = new ArrayList<>(MAP_SIZE);
            for (int i = 0; i < MAP_SIZE; i++) {
                reader.readLine();
                CategoryMap map = parseCategoryMap(reader);
                categoryMaps.add(map);
            }
            long minLocation = Long.MAX_VALUE;
            for (long seed : seeds) {
                long tmp = seed;
                for (int i = 0; i < MAP_SIZE; i++) {
                    tmp = categoryMaps.get(i).convert(tmp);
                }
                if (tmp < minLocation) {
                    minLocation = tmp;
                }
            }
            System.out.println(minLocation);
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

