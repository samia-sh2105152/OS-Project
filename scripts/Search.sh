#!/bin/bash
echo "Files large than 1M "> bigfile.txt
date "+%d/%m/%Y" >> bigfile.txt
find / -type f -size +1M &>> bigfile.txt
cat bigfile.txt | wc -l >> bigfile.txt

