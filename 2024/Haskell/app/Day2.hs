module Day2 where

import Debug.Trace (trace)

parseLines :: [String] -> [[Int]]
parseLines = map (fmap read . words)

diff1_3 :: Int -> Int -> Bool
diff1_3 a b = (abs (a - b) <= 3) && (abs (a - b) >= 1)

safe1 :: [Int] -> Bool
safe1 is = go (<) is || go (>) is
  where
    go _ [] = True
    go _ [_] = True
    go comp (i1 : i2 : rest) = diff1_3 i1 i2 && i1 `comp` i2 && go comp (i2 : rest)

safe2 :: [Int] -> Bool
safe2 is = go (<) is 0 || go (>) is 0
  where
    go :: (Int -> Int -> Bool) -> [Int] -> Int -> Bool
    go _ [] _ = True
    go _ [_] _ = True
    go comp (i1 : i2 : rest) idx
      | diff1_3 i1 i2 && i1 `comp` i2 = go comp (i2 : rest) (idx + 1)
      | otherwise =
          let is1 = take idx is ++ drop (idx + 1) is
              is2 = take (idx + 1) is ++ drop (idx + 2) is
           in safe1 is1 || safe1 is2

safeCounts :: ([Int] -> Bool) -> [[Int]] -> Int
safeCounts safe = foldr (\is count -> if safe is then count + 1 else count) 0

part1 :: IO ()
part1 = do
  ls <- lines <$> readFile "input_day2.txt"
  print $ (safeCounts safe1 . parseLines) ls

part2 :: IO ()
part2 = do
  ls <- lines <$> readFile "input_day2.txt"
  print $ (safeCounts safe2 . parseLines) ls


