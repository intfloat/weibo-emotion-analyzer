'''
Author: intfloat@pku.edu.cn, labyrinth@pku.edu.cn
'''
import argparse
from sklearn.ensemble import GradientBoostingClassifier as GBDT
from sklearn.feature_extraction import DictVectorizer
from sklearn.metrics import classification_report

def load_data(path):
    reader = open(path, 'r')
    data = []
    for line in reader:
        line = line.strip().split()
        y = int(line[0])
        x = {}
        for field in line[1:]:
            idx, val = field.split(':')
            idx, val = idx, float(val)
            x[idx] = val
        data.append([x, y])
    X, Y = [e[0] for e in data], [e[1] for e in data]
    return X, Y

def evaluate(y_gold, y_pred):
    '''
    Only works for Average Precision(AP) ranking score evaluation,
    in y_gold & y_pred, consecutive 2 labels should belong to the same instance.
    '''
    assert(len(y_gold) % 2 == 0)    
    y_gold = [(y_gold[i], y_gold[i + 1]) for i in range(len(y_gold)) if i % 2 == 0]    
    assert(len(y_gold) == len(y_pred))
    ap = 0.0
    for tp, pred in zip(y_gold, y_pred):
        val = 0.0
        for i in xrange(2):
            pos = pred.index(tp[i])
            assert(pos >= 0)
            cnt = len([1 for e in pred[:pos + 1] if e in tp])
            val += 1.0 * cnt / (pos + 1)
        ap += val / 2.0
    ap /= len(y_pred)
    print 'Average Precision:', ap
    return ap

if __name__ == '__main__':
    parser = argparse.ArgumentParser()
    parser.add_argument('train', help = 'training data file')
    parser.add_argument('test', help = 'test data file')
    parser.add_argument('multilabel', help = 'whether this is a multilabel classification problem')
    options = parser.parse_args()
    print options
    multilabel = int(options.multilabel)
    
    x_train, y_train = load_data(options.train)    
    x_test, y_test = load_data(options.test)
    print 'Number of labels:', max(y_train) + 1
    print 'Number of training examples:', len(x_train)
    print 'Number of test exampples:', len(x_test) 

    vectorizer = DictVectorizer()
    x_train = vectorizer.fit_transform(x_train).toarray()
    print 'Dimension of feature vector:', len(x_train[0])
    clf = GBDT()
    print 'Start to train...'
    clf.fit(x_train, y_train)
    print 'Finish training, start to predict...'

    x_test = vectorizer.transform(x_test).toarray()   

    if multilabel == 0:
        y_pred = clf.predict(x_test)
        print classification_report(y_test, y_pred)
    else:
        assert(len(x_test) % 2 == 0)
        # get probabilistic values for ranking
        y_pred, pos = [], 0
        while pos < len(x_test):
            instance = x_test[pos]
            prob = clf.predict_proba(instance)[0]            
            tmp = [(i, prob[i]) for i in xrange(len(prob))]
            # sort in probability descending order
            tmp.sort(key = lambda (idx, p): p, reverse = True)
            cur = []
            for idx, p in tmp:
                cur.append(idx)
            y_pred.append(cur)
            pos += 2  # skip duplicate instance
        evaluate(y_test, y_pred)

    print 'Done.'

