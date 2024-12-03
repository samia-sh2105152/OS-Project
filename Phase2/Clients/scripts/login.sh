#!/bin/bash
serverip=$1
try=0
username1="Client1"
pass1="Client1"
username2="Client2"
pass2="Client2"
while [ $try -lt 3 ]
do
read -p "Enter your user name: " username
read -p "Enter your password : " password
if [[ $username = $username1 && $password = $pass1 ]] || [[ $username = $username2 && $password = $pass2 ]]; then 

echo "Username and password correct"
ssh $username@$serverip
exit
else 
echo "incorrect username and password"
echo "$(date '+%d/%m/%Y %H:%M:%S') invalid login for user $username" >> invalid_attempts.log

fi
try=$((try+1))
done

if [ $try -eq 3 ]; then
echo "Unotherised user!"
echo "Sending log file to server @ ip $serverip"
sftp $username@$serverip<< EOF
put invalid_attempts.log 
exit
EOF
echo "logging out after 30 seconds"
sleep 30
sudo reboot
fi
