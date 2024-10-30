{\rtf1\ansi\ansicpg1252\cocoartf2818
\cocoatextscaling0\cocoaplatform0{\fonttbl\f0\fswiss\fcharset0 Helvetica;}
{\colortbl;\red255\green255\blue255;}
{\*\expandedcolortbl;;}
\paperw11900\paperh16840\margl1440\margr1440\vieww11520\viewh8400\viewkind0
\pard\tx720\tx1440\tx2160\tx2880\tx3600\tx4320\tx5040\tx5760\tx6480\tx7200\tx7920\tx8640\pardirnatural\partightenfactor0

\f0\fs24 \cf0 #!/bin/bash\
\
output_disk="disk_info.log"\
output_cpu_mem="mem_cpu_info.log"\
> "$output_disk"\
> "$output_cpu_mem"\
\
#Disk\
echo "Disk informations" >> "$output_disk"\
echo "----------------------------------" >> "$output_disk"\
echo >> $output_disk\
\
echo "Disk space:" >> "$output_disk"\
df -H >> "$output_disk"\
echo "==================================" >> "$output_disk"\
echo >> $output_disk\
\
echo "Disk usage for HOME:" >> "$output_disk"\
du -sh "$HOME" >> "$output_disk"\
echo "==================================" >> "$output_disk"\
echo >> $output_disk\
\
echo "Disk usage for Subdirectories:" >> "$output_disk"\
du -hs "$HOME"/* >> "$output_disk"\
echo >> $output_disk\
\
#Memory CPU\
echo "Memory and CPU informations" >> "$output_cpu_mem"\
echo "----------------------------------" >> "$output_cpu_mem"\
echo >> "$output_cpu_mem"\
\
echo "Memory Usage (Percentage):" >> "$output_cpu_mem"\
free | grep Mem | awk '\{print $3/$2 * 100.0, "%"\}' >> "$output_cpu_mem"\
echo "==================================" >> "$output_cpu_mem"\
echo >> "$output_cpu_mem"\
\
echo "Memory Free (Percentage):" >> "$output_cpu_mem"\
free | grep Mem | awk '\{print $4/$2 * 100.0, "%"\}' >> "$output_cpu_mem"\
echo "==================================" >> "$output_cpu_mem"\
echo >> "$output_cpu_mem"\
\
echo "CPU Model:" >> "$output_cpu_mem"\
lscpu | grep -i 'architecture\\|vendor' >> "$output_cpu_mem"\
echo "==================================" >> "$output_cpu_mem"\
echo >> "$output_cpu_mem"\
\
echo "CPU Cores:" >> "$output_cpu_mem"\
nproc >> "$output_cpu_mem"\
echo "==================================" >> "$output_cpu_mem"\
echo >> "$output_cpu_mem"\
\
#Displaying output from files\
echo "Showing output of disk_info.log..."\
echo\
cat "$output_disk"\
echo \
\
echo "Showing output of mem_cpu_info.log...."\
echo\
cat "$output_cpu_mem"\
echo\
}