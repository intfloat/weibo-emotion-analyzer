# -*- coding: utf-8 -*-
import argparse
import numpy as np
import cPickle
from collections import defaultdict
import sys, re
import pandas as pd

def build_data_cv(data_folder):
    """
    Loads data
    """
    revs = []    
    vocab = defaultdict(float)
    # idx = 0 corresponds to training data,
    # idx = 1 corresponds to test data
    for idx, path in enumerate(data_folder):
        with open(path, 'rb') as f:
            for line in f:            
                rev = line.strip()
                words = rev.split() # first field is label, then follow original text
                words, label = words[1:], int(words[0])
                for word in words:
                    vocab[word] += 1
                datum  = {"y" : label,
                          "text" : rev,
                          "num_words" : len(words),
                          "split" : idx}
                revs.append(datum)    
    return revs, vocab
    
def get_W(word_vecs, k = 50):
    """
    Get word matrix. W[i] is the vector for word indexed by i
    """
    vocab_size = len(word_vecs)
    word_idx_map = dict()
    W = np.zeros(shape=(vocab_size+1, k))            
    W[0] = np.zeros(k)
    i = 1
    for word in word_vecs:
        W[i] = word_vecs[word]
        word_idx_map[word] = i
        i += 1
    return W, word_idx_map

def load_bin_vec(fname, vocab):
    """
    Loads 50x1 word vecs
    """
    word_vecs = {}
    with open(fname, "rb") as f:
        header = f.readline()
        vocab_size, layer1_size = map(int, header.split())
        binary_len = np.dtype('float32').itemsize * layer1_size
        for line in xrange(vocab_size):
            word = []
            while True:
                ch = f.read(1)
                if ch == ' ':
                    word = ''.join(word)
                    break
                if ch != '\n':
                    word.append(ch)   
            if word in vocab:
               word_vecs[word] = np.fromstring(f.read(binary_len), dtype='float32')  
            else:
                f.read(binary_len)
    return word_vecs

def add_unknown_words(word_vecs, vocab, min_df=1, k=50):
    """
    For words that occur in at least min_df documents, create a separate word vector.    
    0.25 is chosen so the unknown vectors have (approximately) same variance as pre-trained ones
    """
    for word in vocab:
        if word not in word_vecs and vocab[word] >= min_df:
            word_vecs[word] = np.random.uniform(-0.25,0.25,k)  

if __name__=="__main__":
    """
    command: python process_data.py small_train.txt small_test.txt vectors.bin
    """
    parser = argparse.ArgumentParser()
    parser.add_argument('train', help = 'training data file')
    parser.add_argument('test', help = 'test data file')
    parser.add_argument('w2v', help = 'word2vec file')
    options = parser.parse_args()
    print options
    w2v_file = options.w2v
    data_folder = [options.train, options.test]    
    print "loading data...",
    revs, vocab = build_data_cv(data_folder)
    max_l = np.max(pd.DataFrame(revs)["num_words"])
    print "data loaded!"
    print "number of sentences: " + str(len(revs))
    print "vocab size: " + str(len(vocab))
    print "max sentence length: " + str(max_l)
    print "loading word2vec vectors...",
    w2v = load_bin_vec(w2v_file, vocab)
    print "word2vec loaded!"
    print "num words already in word2vec: " + str(len(w2v))
    add_unknown_words(w2v, vocab)
    W, word_idx_map = get_W(w2v)
    rand_vecs = {}
    add_unknown_words(rand_vecs, vocab)
    W2, _ = get_W(rand_vecs)
    cPickle.dump([revs, W, W2, word_idx_map, vocab], open("mr.p", "wb"))
    print "dataset created!"
    
