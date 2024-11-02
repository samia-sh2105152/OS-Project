#!/bin/bash

pstree >> process_info.log
# check the colum num 8 for letter Z zombie or letter X dead
ps aux | awk '{ if ($8=="Z" || $8=="X" ) print $0}' >> process_info.log

#CPU usage and Memory usage related to processes. 
top -b -n1 >> process_info.log


ps aux | sort -rnk 4 | head -5 >> process_info.log

while true
do 
sleep 3600
scp $USER@192.168.0.1 process_info.log

done
