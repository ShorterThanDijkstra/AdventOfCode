#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <string.h>
#include <assert.h>

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

char *lex_num(unsigned long *num, char *line)
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
    if (strlen(line) < strlen(pattern))
    {
        return false;
    }
    for (int i = 0; i < strlen(pattern); i++)
    {
        if (*(line + i) != *(pattern + i))
        {
            return false;
        }
    }
    return true;
}
char *lex_cube_set(char *line, unsigned long *blue, unsigned long *red, unsigned long *green)
{
    while (!(*line == ';' || *line == '\n'))
    {
        unsigned long num = 0;
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

unsigned long power_of_fewest(char *line)
{
    line = lex_start(line);
    unsigned long blue = 0;
    unsigned long red = 0;
    unsigned long green = 0;
    unsigned long blue_max = 0;
    unsigned long red_max = 0;
    unsigned long green_max = 0;
    while (*line != '\n')
    {
        line = lex_cube_set(line, &blue, &red, &green);
        red_max = red > red_max ? red : red_max;
        green_max = green > green_max ? green : green_max;
        blue_max = blue > blue_max ? blue : blue_max;

        blue = 0;
        red = 0;
        green = 0;
    }

    return red_max * green_max * blue_max;
}

int main()
{
    FILE *fp = fopen("input_day2.txt", "r");
    char *line = (char *)malloc(BUFF_SIZE * sizeof(char));
    unsigned long sum = 0;
    while (fgets(line, BUFF_SIZE, fp) != NULL)
    {
        sum += power_of_fewest(line);
    }
    printf("%ld\n", sum);
    fclose(fp);
    return 0;
}