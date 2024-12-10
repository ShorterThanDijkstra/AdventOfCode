module Day4_1 where

data Matrix = Matrix [[Char]] Int Int

parseLines :: [String] -> Matrix
parseLines lines =
  let l = length lines
      w = length $ head lines
   in Matrix lines l w

count :: Matrix -> [(Int, Int)] -> Int
count _ [] = 0
count m (coord : rest) =
  left m coord
    + leftTop m coord
    + top m coord
    + rightTop m coord
    + right m coord
    + rightBottom m coord
    + bottom m coord
    + bottomLeft m coord
    + count m rest

-- <-
left :: Matrix -> (Int, Int) -> Int
left m@(Matrix {}) (r, c)
  | isChar m (r, c) 'X'
      && isChar m (r, c - 1) 'M'
      && isChar m (r, c - 2) 'A'
      && isChar m (r, c - 3) 'S' =
      1
  | otherwise = 0

--
--    ^
-- <--|
leftTop :: Matrix -> (Int, Int) -> Int
leftTop m@(Matrix {}) (r, c)
  | isChar m (r, c) 'X'
      && isChar m (r - 1, c - 1) 'M'
      && isChar m (r - 2, c - 2) 'A'
      && isChar m (r - 3, c - 3) 'S' =
      1
  | otherwise = 0
-- ^
-- |

top :: Matrix -> (Int, Int) -> Int
top m@(Matrix {}) (r, c)
  | isChar m (r, c) 'X'
      && isChar m (r - 1, c) 'M'
      && isChar m (r - 2, c) 'A'
      && isChar m (r - 3, c) 'S' =
      1
  | otherwise = 0
-- ^
-- |-->

rightTop :: Matrix -> (Int, Int) -> Int
rightTop m@(Matrix {}) (r, c)
  | isChar m (r, c) 'X'
      && isChar m (r - 1, c + 1) 'M'
      && isChar m (r - 2, c + 2) 'A'
      && isChar m (r - 3, c + 3) 'S' =
      1
  | otherwise = 0

-- ->
right :: Matrix -> (Int, Int) -> Int
right m@(Matrix {}) (r, c)
  | isChar m (r, c) 'X'
      && isChar m (r, c + 1) 'M'
      && isChar m (r, c + 2) 'A'
      && isChar m (r, c + 3) 'S' =
      1
  | otherwise = 0

-- -->

-- |
-- v
rightBottom :: Matrix -> (Int, Int) -> Int
rightBottom m@(Matrix {}) (r, c)
  | isChar m (r, c) 'X'
      && isChar m (r + 1, c + 1) 'M'
      && isChar m (r + 2, c + 2) 'A'
      && isChar m (r + 3, c + 3) 'S' =
      1
  | otherwise = 0

-- |
-- v
bottom :: Matrix -> (Int, Int) -> Int
bottom m@(Matrix {}) (r, c)
  | isChar m (r, c) 'X'
      && isChar m (r + 1, c) 'M'
      && isChar m (r + 2, c) 'A'
      && isChar m (r + 3, c) 'S' =
      1
  | otherwise = 0

-- <--
--   |
--   v
bottomLeft :: Matrix -> (Int, Int) -> Int
bottomLeft m@(Matrix {}) (r, c)
  | isChar m (r, c) 'X'
      && isChar m (r + 1, c - 1) 'M'
      && isChar m (r + 2, c - 2) 'A'
      && isChar m (r + 3, c - 3) 'S' =
      1
  | otherwise = 0

isChar :: Matrix -> (Int, Int) -> Char -> Bool
isChar (Matrix rows l w) (r, c) ch = r >= 0 && r < l && c >= 0 && c < w && (rows !! r) !! c == ch

xmas :: Matrix -> Int
xmas m@(Matrix _ l w) =
  let coords = [(r, c) | r <- [0 .. l - 1], c <- [0 .. w - 1]]
   in count m coords

part1 :: IO ()
part1 = do
  ls <- lines <$> readFile "input_day4.txt"
  print $ (xmas . parseLines) ls
