module Day2 where 

parseLines :: [String] -> [[Int]]
parseLines lines = map (\line -> read <$> words line) lines

diff1_3 :: Int -> Int -> Int 
diff1_3 a b = abs (a - b) <= 3 && abs (a - b) >= 1

safe :: [Int] -> Bool
safe is = foldr 

part1 :: IO ()
part1 = do
  lines <- lines <$> readFile "input_day2.txt"
  print (parseLines lines)