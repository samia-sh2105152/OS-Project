#!/bin/bash

pstree
# check the colum num 8 for letter Z zombie or letter X dead
ps aux | awk '{ if ($8=="Z" || $8=="X" ) print $0}' 

#CPU usage related to processes. 
#top -i 
#mpstat -P ALL
#Memory usage of running processes. 
ps aux | sort -rnk 4 | head -5
