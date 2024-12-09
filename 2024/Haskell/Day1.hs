module Day1 where

import Data.List (sort)

parseLines :: [String] -> [(Int, Int)]
parseLines lines =
  let (l, r) = foldr (\s (l, r) -> let is = map read (words s) in (head is : l, last is : r)) ([], []) lines
   in zip (sort l) $ sort r

distance :: [(Int, Int)] -> Int
distance  = foldr (\(a, b) d -> abs (a - b) + d) 0

main :: IO ()
main = do
  lines <- lines <$> readFile "input_day1.txt"
  print (distance $ parseLines lines)