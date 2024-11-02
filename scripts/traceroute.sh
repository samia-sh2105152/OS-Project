#!/bin/bash

echo "Running traceroute..." 
echo
echo "Routing Table:"
echo "===============" 
netstat -rn 
echo "-----------------------------------------------------------------------------" 
echo

echo "Hostname:" 
echo "=========="
hostname 
echo "-------------------------------------" 
echo

echo "Testing local DNS server for google.com:" 
echo "========================================="
nslookup google.com 
echo "-------------------------------------" 
echo

echo "Tracing route to google.com:" 
echo "=============================="
traceroute google.com 
echo "---------------------------------------------------" 
echo

echo "Pinging google.com to check connectivity:" 
echo "========================================="
ping -c 4 google.com
echo "--------------------------------------------------------------------" 
echo

echo "Traceroute completed." 

