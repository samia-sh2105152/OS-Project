#!/bin/bash

pstree
# check the colum num 8 for letter Z zombie or letter X dead
ps aux | awk '{ if ($8=="Z" || $8=="X" ) print $0}' 



