#!/bin/bash
//------------To check whether file1 exists----------------
if [ -e path/file1 ] 
then
echo "File1 Exists and  we can proceed"
else 
echo "File1 doesn't exists, unable to proceed"
exit 1
fi
//------------To check whether file2 exists----------------
if [ -e path/file2 ] 
then
echo "File2 Exists and  we can proceed"
else 
echo "file2 doesn't exists, unable to proceed"
exit 1
fi
------------------total counts of attributes in each files -------------
echo  - n "Total number of attributes in Attribute.txt = "
wc -l Attribute.txt
echo  - n "Total number of attributes in file1 = "
wc -l File1
//-----------To find difference between two files-------
ad=grep -Fxvf   file1 Atribute.txt|wc -l
grep -iFxvf  file1 Atribute.txt>Miss_attr.txt
grep -Fxvf File1 Atribute.txt >b.txt
grep -Fxvf File1 a.txt b.txt>case_mm_attr.txt
grep -f File1  file1 Atribute.txt|uniq -dc >Dup_attr.txt
if [ $ad == 0 ]
echo "Passed"
else 
echo "Failed"
echo -n "Missing Attributes are : "
cat Miss_attr.txt
echo -n "Attrbutes with case mismatch are: "
cat case_mm_attr.txt
exit 1
fi
echo -n "Data with multiple occurance"
cat Dup_attr.txt