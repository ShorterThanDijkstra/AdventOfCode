#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>

#define BUFF_SIZE 1024

const char *NUMS[9] = {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};

void set_first_last(int *first, int *last, int value)
{
    if (*first == 0)
    {
        *first = value;
    }
    *last = value;
}

int parse_line(char *line)
{
    int first = 0;
    int last = 0;
    int len = strlen(line);
    int i = 0;
    while (line[i] != '\0')
    {
        // printf("%c\n", line[i]);
        if (line[i] <= '9' && line[i] >= '0')
        {
            set_first_last(&first, &last, line[i] - '0');
            i += 1;
            continue;
        }

        for (int j = 0; j < 9; j++)
        {
            const char *num = NUMS[j];

            int len_k = strlen(num);
            if (i + len_k > len)
            {
                continue;
            }
            int k = 0;
            while (k < len_k && line[i + k] == num[k])
            {
                k += 1;
            }
            if (k == len_k)
            {
                set_first_last(&first, &last, j + 1);
                break;
            }
        }
        i += 1;
    }
    printf("line: %s, first: %d, last: %d\n", line, first, last);
    return first * 10 + last;
}
int main()
{
    FILE *fp = fopen("input_day1.txt", "r");
    char *line = (char *)malloc(BUFF_SIZE * sizeof(char));
    int sum = 0;
    while (fgets(line, BUFF_SIZE, fp) != NULL)
    {
        sum += parse_line(line);
    }
    printf("%d\n", sum);
    fclose(fp);
    // parse_line("twone\n");
    return 0;
}