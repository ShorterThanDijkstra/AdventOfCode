module Day4_2 where

data Matrix = Matrix [[Char]] Int Int

parseLines :: [String] -> Matrix
parseLines lines =
  let l = length lines
      w = length $ head lines
   in Matrix lines l w

count :: Matrix -> [(Int, Int)] -> Int
count _ [] = 0
count m ((r, c) : rest)
  | isChar' (r, c) 'A'
      && ( (isChar' (r - 1, c - 1) 'M' && isChar' (r + 1, c + 1) 'S')
             || (isChar' (r - 1, c - 1) 'S' && isChar' (r + 1, c + 1) 'M')
         )
      && ( (isChar' (r - 1, c + 1) 'M' && isChar' (r + 1, c - 1) 'S')
             || (isChar' (r - 1, c + 1) 'S' && isChar' (r + 1, c - 1) 'M')
         ) =
      1 + count m rest
  | otherwise = count m rest
  where
    isChar' = isChar m

isChar :: Matrix -> (Int, Int) -> Char -> Bool
isChar (Matrix rows l w) (r, c) ch = r >= 0 && r < l && c >= 0 && c < w && (rows !! r) !! c == ch

xMas :: Matrix -> Int
xMas m@(Matrix _ l w) =
  let coords = [(r, c) | r <- [0 .. l - 1], c <- [0 .. w - 1]]
   in count m coords

part2 :: IO ()
part2 = do
  ls <- lines <$> readFile "input_day4.txt"
  print $ (xMas . parseLines) ls
