module Day1 where

import Data.List (sort)
import Data.Map (lookup, insert, empty)

parseLines :: [String] -> ([Int], [Int])
parseLines = foldr (\s (l, r) -> let is = map read (words s) in (head is : l, last is : r)) ([], []) 

distance :: ([Int], [Int]) -> Int
distance (l, r) = foldr (\(a, b) d -> abs (a - b) + d) 0 $ zip (sort l) (sort r)

part1 :: IO ()
part1 = do
  lines <- lines <$> readFile "input_day1.txt"
  print (distance $ parseLines lines)

similarity :: ([Int], [Int]) -> Int
similarity (l, r) = let m = foldr (\i m -> case Data.Map.lookup i m of 
                                             Nothing -> insert i 1 m 
                                             Just count -> insert i (count + 1) m)
                                  empty
                                  r 
                    in foldr (\i res -> case Data.Map.lookup i m of
                                          Nothing -> res 
                                          Just count -> i * count + res)
                             0
                             l
part2 :: IO ()
part2 = do
  lines <- lines <$> readFile "input_day1.txt"
  print (similarity $ parseLines lines)