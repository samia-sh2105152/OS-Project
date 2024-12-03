#!/bin/bash
echo "start searching"
echo "Files large than 1M "> bigfile.txt
date "+%d/%m/%Y" >> bigfile.txt
find / -type f -size +1M &>> bigfile.txt
cat bigfile.txt | wc -l >> bigfile.txt

if [ -s bigfile.txt ]; then
echo " contents of the bigfile are attached with this mail $(cat bigfile.txt)" | mail -s "bigfile content " aa2002255@qu.edu.qa 

fi 

echo "end searching"
