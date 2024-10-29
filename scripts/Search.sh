#!/bin/bash
echo "Files large than 1M "> bigfile.txt
date "+%d/%m/%Y" >> bigfile.txt
find / -type f -size +1M &>> bigfile.txt
cat bigfile.txt | wc -l >> bigfile.txt

if [ -s bigfile.txt ]; then
echo " contents of the bigfile are attached with this mail" | mail -s "bigfile content " -a $(cat bigfile.txt) QUID@qu.edu.qa 

fi 


