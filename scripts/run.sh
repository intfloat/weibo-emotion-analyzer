#!/bin/bash

# sentence opinion classification
echo "Sentence opinion binary classification:"
python model.py train_opinion.txt test_opinion.txt 0

echo "Weibo level emotion classification:"
python model.py train_weibo.txt test_weibo.txt 1

echo "Sentence level emotion classification:"
python model.py train_sentence.txt test_sentence.txt 1
