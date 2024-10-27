#!/bin/bash
try=0
while [ $try -lt 3 ]
do
read -p "Enter your user name: " username
read -p "Enter your password : " password

sshpass -p "$password" ssh -o BatchMode=no -o ConnectTimeout=4 $username@192.168.1.1.1 exit

if [ $? -eq 0 ]; then 
echo "connected succesfully"
exit
else 
echo "$(date '+%d/%m/%Y %H:%M:%S') invalid login for user $username" >> invalid_attempts.log

fi
try=$((try+1))
done
