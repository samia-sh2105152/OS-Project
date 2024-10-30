#!/bin/bash
find $HOME -type f -perm 0777 > perm_change.log
files=$(cat perm_change.log) 
for file in $files
do 
chmod 700 $file
done
