#!/bin/bash

# used to generate training & test file for python script
# to run this script, just type: 
# ./gendata.sh

TRAIN_DATA=../data/train_class.txt
TEST_DATA=../data/test_class.txt
# generate opinion classification data
cat $TRAIN_DATA | grep SOID | cut -d' ' -f2- | uniq > train_opinion.txt
cat $TEST_DATA | grep SOID | cut -d' ' -f2-  > test_opinion.txt

# generate weibo level emotion classification data
cat $TRAIN_DATA | grep WPID | cut -d' ' -f2-  | uniq > train_weibo.txt
cat $TEST_DATA | grep WPID | cut -d' ' -f2-  > test_weibo.txt

# generate sentence level emotion classification data
cat $TRAIN_DATA | grep SPID | cut -d' ' -f2-  | uniq > train_sentence.txt
cat $TEST_DATA | grep SPID | cut -d' ' -f2-  > test_sentence.txt
