#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <string.h>
#include <assert.h>

#define MAX_RED 12
#define MAX_GREEN 13
#define MAX_BLUE 14
#define BUFF_SIZE 256

char *lex_start(char *line)
{
    while (*line != ':')
    {
        line += 1;
    }
    line += 2;
    return line;
}

char *lex_num(int *num, char *line)
{
    char c = *line;
    *num = 0;
    while (c != ' ')
    {
        assert(c <= '9' && c >= '0');
        *num = *num * 10 + c - '0';
        line += 1;
        c = *line;
    }
    line += 1;
    return line;
}

bool starts_with(char *line, char *pattern)
{
    size_t lenpattern = strlen(pattern),
           lenline = strlen(line);
    return lenline < lenpattern ? false : memcmp(pattern, line, lenpattern) == 0;
}

char *lex_cube_set(char *line, int *blue, int *red, int *green)
{
    while (!(*line == ';' || *line == '\n'))
    {
        int num = 0;
        line = lex_num(&num, line);

        if (starts_with(line, "blue"))
        {
            *blue = num;
            line += 4;
        }
        else if (starts_with(line, "red"))
        {
            *red = num;
            line += 3;
        }
        else
        {
            assert(starts_with(line, "green"));
            *green = num;
            line += 5;
        }
        if (*line == ',')
        {
            line += 2;
        }
    }
    if (*line != '\n')
    {
        assert(*line == ';');
        line += 2;
    }
    return line;
}
bool validate_game(char *line)
{
    line = lex_start(line);
    int blue = 0;
    int red = 0;
    int green = 0;
    while (*line != '\n')
    {
        line = lex_cube_set(line, &blue, &red, &green);
        if (blue > MAX_BLUE || red > MAX_RED || green > MAX_GREEN)
        {
            return false;
        }
        blue = 0;
        red = 0;
        green = 0;
    }
    return true;
}

int main()
{
    FILE *fp = fopen("input_day2.txt", "r");
    char *line = (char *)malloc(BUFF_SIZE * sizeof(char));
    int sum = 0;
    int line_num = 1;
    while (fgets(line, BUFF_SIZE, fp) != NULL)
    {
        if (validate_game(line))
        {
            sum += line_num;
        }
        line_num += 1;
    }
    printf("%d\n", sum);
    fclose(fp);
    return 0;
}