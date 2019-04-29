#!/bin/bash
MAIN_CLASS=com.lvwza.consistent.Application
cd ..
BASE_DIR=`pwd`
mkdir logs
LIB_HOME=../lib
BIN_PATH=../bin
CONF_PATH=$BASE_DIR/conf
cd $BASE_DIR/lib
for v_jar in `ls *.jar`
do
        BIN_PATH=$BIN_PATH:$LIB_HOME/$v_jar
done

echo $BIN_PATH

cd $CONF_PATH
nohup /home/jsb/jdk1.8.0_181/bin/java -classpath ./:$BIN_PATH $MAIN_CLASS >../logs/nohup.out 2>&1 &
tail -f ../logs/nohup.out