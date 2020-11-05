#!/bin/bash
./BUILD.sh
amountTests=19
amountPassedTests=0
declare -A tests
tests[0]=""
tests[1]="-h"
tests[2]="-login vasya -pass 123"
tests[3]="-pass 123 -login vasya"
tests[4]="-login !vasya! -pass 123"
tests[5]="-login asd -pass dsa"
tests[6]="-login admin -pass 123"
tests[7]="-login vasya -pass 123 -role READ -res A"
tests[8]="-login vasya -pass 123 -role DELETE -res A"
tests[9]="-login q -pass ytrewq -role EXECUTE -res A"
tests[10]="-login admin -pass admin -role READ -res A.B"
tests[11]="-login admin -pass admin -role WRITE -res A.B.C"
tests[12]="-login vasya -pass 1234 -role DELETE -res A.B.C"
tests[13]="-login vasya -pass 123 -role WRITE -res A.B"
tests[14]="-login vasya -pass 123 -role READ -res A.B -ds 2020-10-11 -de 2020-10-12 -vol 10"
tests[15]="-login vasya -pass 123 -role READ -res A.B -ds 2020.10.11 -de 2020.10.12 -vol 10"
tests[16]="-login vasya -pass 123 -role READ -res A.B -ds 2020-10-1 -de 2020-10-60 -vol 10"
tests[17]="-login vasya -pass 123 -role READ -res A.B -ds 2020-10-11 -de 2020-10-12 -vol hgh"
tests[18]="-login vasya -pass 123 -role WRITE -res A -ds 2020-03-12 -de 2020-03-13 -vol 10"
expectedExitCodes=(1 1 0 0 2 3 4 0 5 6 0 0 4 6 0 7 7 7 6)
for ((i = 0; i < "$amountTests"; i++)); do
  test=${tests[$i]}
  expectedExitCode=${expectedExitCodes[$i]}
  ./RUN.sh ""${test}""
  exitCode="$?"
  if [ "$exitCode" == "$expectedExitCode" ]; then
    echo "Test $i passed: exit code - $exitCode."
    let amountPassedTests++
  else
    echo "Test $i failed: received - $exitCode expected - $expectedExitCode."
  fi
done
echo "Result: $amountPassedTests passed tests"
if [ "$amountPassedTests" == "$amountTests" ]; then
  exit 0
else
  exit 1
fi
exec $SHELL