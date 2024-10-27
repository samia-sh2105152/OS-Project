#!/bin/bash
serverip='192.168.1.1'
try=0
while [ $try -lt 3 ]
do
read -p "Enter your user name: " username
read -p "Enter your password : " password

sshpass -p "$password" ssh -o BatchMode=no -o ConnectTimeout=4 $username@$serverip exit

if [ $? -eq 0 ]; then 
echo "connected succesfully"
exit
else 
echo "$(date '+%d/%m/%Y %H:%M:%S') invalid login for user $username" >> invalid_attempts.log

fi
try=$((try+1))
done

if [ $try -eq 3 ]; then
echo "Unotherised user!"

sftp $username@$serverip<< EOF
put invalid_attempts.log 
exit
EOF
echo "logging out after 30 seconds"
sleep 30
logout
