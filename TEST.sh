#!/bin/bash
amountTests=19
amountPassedTests=0
declare -A tests
tests[0]="app.jar"
tests[1]="app.jar -h"
tests[2]="app.jar -login vasya -pass 123"
tests[3]="app.jar -pass 123 -login vasya"
tests[4]="app.jar -login !vasya! -pass 123"
tests[5]="app.jar -login asd -pass dsa"
tests[6]="app.jar -login admin -pass 123"
tests[7]="app.jar -login vasya -pass 123 -role READ -res A"
tests[8]="app.jar -login vasya -pass 123 -role DELETE -res A"
tests[9]="app.jar -login q -pass ytrewq -role EXECUTE -res A"
tests[10]="app.jar -login admin -pass admin -role READ -res A.B"
tests[11]="app.jar -login admin -pass admin -role WRITE -res A.B.C"
tests[12]="app.jar -login vasya -pass 1234 -role DELETE -res A.B.C"
tests[13]="app.jar -login vasya -pass 123 -role WRITE -res A.B"
tests[14]="app.jar -login vasya -pass 123 -role READ -res A.B -ds 2020-10-1 -de 2020-10-2 -vol 10"
tests[15]="app.jar -login vasya -pass 123 -role READ -res A.B -ds 2020.10.1 -de 2020.10.2 -vol 10"
tests[16]="app.jar -login vasya -pass 123 -role READ -res A.B -ds 2020-10-1 -de 2020-10-60 -vol 10"
tests[17]="app.jar -login vasya -pass 123 -role READ -res A.B -ds 2020-10-1 -de 2020-10-2 -vol hgh"
tests[18]="app.jar -login vasya -pass 123 -role WRITE -res A -ds 2020-03-12 -de 2020-03-13 -vol 10"
expectedExitCodes=(1 1 0 0 2 3 4 0 5 6 0 0 4 6 0 7 7 7 6)
for ((i=0; i < "$amountTests"; i++))
do
  test=tests[$i]
  expectedExitCode=${expectedExitCodes[$i]}
  ./RUN.sh "$test"
  exitCode="$?"
  if [ "$exitCode" == "$expectedExitCode" ]
   then
      echo "Test $i passed: exit code - $?."
      let amountPassedTests++
    else
      echo "Test $i failed: received - $exitCode expected - $expectedExitCode."
  fi
done
echo "Result: $amountPassedTests passed tests"
if [ "$amountPassedTests" == "$amountTests" ]
  then
    return 0
  else
    return 1
fi
exec $SHELL