{\rtf1\ansi\ansicpg1252\cocoartf2818
\cocoatextscaling0\cocoaplatform0{\fonttbl\f0\fswiss\fcharset0 Helvetica;}
{\colortbl;\red255\green255\blue255;}
{\*\expandedcolortbl;;}
\paperw11900\paperh16840\margl1440\margr1440\vieww11520\viewh8400\viewkind0
\pard\tx720\tx1440\tx2160\tx2880\tx3600\tx4320\tx5040\tx5760\tx6480\tx7200\tx7920\tx8640\pardirnatural\partightenfactor0

\f0\fs24 \cf0 #!/bin/bash\
clear\
echo\
output_network="network.log"\
> "$output_network"  \
\
server_ip="192.168.64.3"\
client1_ip="192.168.64.2"\
# client2_ip="192.168.64.2"\
\
echo "Server IP: $server_ip" >> "$output_network"\
echo "Client1 IP: $client1_ip" >> "$output_network"\
echo >> "$output_network"\
\
echo "Pinging from server to clients: " >> "$output_network"\
echo "----------------------------------" >> "$output_network"\
echo  >> "$output_network"\
\
for i in \{1..3\}\
do\
    echo "Attempt $i:" >> "$output_network"\
    echo "====================================" >> "$output_network"\
    echo "Pinging from server (VM1) to client1 (VM2)..." >> "$output_network"\
    ping -c 1 -W 2 "$client1_ip" >> "$output_network" 2>&1\
  	if [ $? -ne 0 ]; then\
        echo "Ping to client1[$client1_ip] failed. Running traceroute and diagnostic checks..." >> "$output_network"\
        ./traceroute.sh >> "$output_network" 2>&1\
        date "+%Y-%m-%d %H:%M:%S - Connectivity with client1 [$client1_ip] failed, running diagnostics." >> "$output_network"\
    else\
    	echo >> "$output_network"\
        date "+%Y-%m-%d %H:%M:%S - Connectivity with client1 [$client1_ip] is ok." >> "$output_network"\
    fi\
    echo >> "$output_network"\
    # echo "Pinging from server (VM1) to client2 (VM3)..." >> "$output_network"\
    # ping -c 1 -W 2 "$client2_ip" >> "$output_network" 2>&1\
    # if [ $? -ne 0 ]; then\
    #     echo "Ping to client2[$client2_ip] failed. Running traceroute and diagnostic checks..." >> "$output_network"\
    #     ./traceroute.sh >> "$output_network" 2>&1ssh username@192.168.64.2\
\
    #     date "+%Y-%m-%d %H:%M:%S - Connectivity with client2 [$client2_ip] failed, running diagnostics." >> "$output_network"\
    # else\
    # 	echo >> "$output_network"\
    #   date "+%Y-%m-%d %H:%M:%S - Connectivity with client2 [$client2_ip] is ok." >> "$output_network"\
    #fi\
\
    # # echo >> "$output_network"\
\
    echo "-------------------------------------------------" >> "$output_network"\
    echo >> "$output_network"\
   \
done\
echo >> "$output_network"\
\
echo "Pinging from clients to server: " >> "$output_network"\
echo "----------------------------------" >> "$output_network"\
echo  >> "$output_network"\
\
for i in \{1..3\}\
do\
    echo "Attempt $i:" >> "$output_network"\
    echo "====================================" >> "$output_network"\
\
    echo "Pinging from client1 (VM2) to server (VM1)..." >> "$output_network"\
    ssh -i ~/.ssh/id_rsa -o StrictHostKeyChecking=no samia@$client1_ip "ping -c 1 -W 2 $server_ip" >> "$output_network" 2>&1\
    ssh_exit_status=$?\
\
    if [ $ssh_exit_status -ne 0 ]; then\
        echo "Ping to server [$server_ip] failed. Running traceroute and diagnostic checks..." >> "$output_network"\
        ssh -i ~/.ssh/id_rsa -o StrictHostKeyChecking=no samia@$client1_ip "./traceroute.sh" >> "$output_network" 2>&1\
        date "+%Y-%m-%d %H:%M:%S - Connectivity with server [$server_ip] failed, running diagnostics." >> "$output_network"\
    else\
    	echo >> "$output_network"\
        date "+%Y-%m-%d %H:%M:%S - Connectivity with server [$server_ip] is ok." >> "$output_network"\
    fi\
    echo >> "$output_network"\
\
    # echo "Pinging from client2 (VM3) to server (VM1)..." >> "$output_network"\
\
    # ssh -i ~/.ssh/id_rsa -o StrictHostKeyChecking=no samia@$client1_ip "ping -c 1 -W 2 $server_ip" >> "$output_network" 2>&1\
    # ssh_exit_status=$?\
\
    # if [ $ssh_exit_status -ne 0 ]; then\
    #     echo "Ping to server [$server_ip] failed. Running traceroute and diagnostic checks..." >> "$output_network"\
    #     ssh -i ~/.ssh/id_rsa -o StrictHostKeyChecking=no samia@$client1_ip "./traceroute.sh" >> "$output_network" 2>&1\
    #     date "+%Y-%m-%d %H:%M:%S - Connectivity with server [$server_ip] failed, running diagnostics." >> "$output_network"\
    # else\
    #     echo >> "$output_network"\
    #     date "+%Y-%m-%d %H:%M:%S - Connectivity with server [$server_ip] is ok." >> "$output_network"\
    # fi\
 \
    echo >> "$output_network"\
    echo "-------------------------------------------------" >> "$output_network"\
    echo >> "$output_network"\
   \
done\
echo >> "$output_network"\
\
echo "Showing results from network.log file......."\
cat network.log\
}