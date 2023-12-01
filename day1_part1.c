#include <stdio.h>
int main()
{
    FILE *fp = fopen("input_day1.txt", "r");
    int sum = 0;
    int first = 0;
    int last = 0;
    char c = (char)fgetc(fp);
    while (c != EOF)
    {
        if (c == '\n')
        {
            sum += first * 10 + last;
            first = 0;
            last = 0;
        }
        if (c <= '9' && c >= '0')
        {
            if (first == 0)
            {
                first = c - '0';
            }

            last = c - '0';
        }

        c = (char)fgetc(fp);
    }
    sum += first * 10 + last;
    printf("%d\n", sum);
    fclose(fp);
    return 0;
}