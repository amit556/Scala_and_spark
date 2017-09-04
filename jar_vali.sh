#!/bin/bash


if[ -e path/environment.sh ]
then
echo "File exists"
source environment.sh
echo $Name
echo $loc
else 
echo "file doesn't exists
exit 1
fi

if [ -e path/test.jar ] 
then
echo "Jar Exists and will execute shortly"
else 
echo "Jar doesn't exists"
exit 1
fi

java -jar sample.jar $1 $2 
out=$?

if [ $out -e 0 ] 
then
echo "Jar Executed successfully"
else
echo "Jar execution failed"
exit 1	
fi