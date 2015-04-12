'''
Author: intfloat@pku.edu.cn, labyrinth@pku.edu.cn
'''
import argparse
from sys import stdout
from copy import deepcopy
from sklearn import svm
from sklearn.ensemble import RandomForestClassifier as RF
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

def average_precision(ranking, reference):
    """
    Compute average precision for ranking problem
    """
    assert(len(ranking) == len(reference))
    val = 0.0
    for label in reference:        
        if label in ranking:
            pos = ranking.index(label)
            cnt = len([1 for e in ranking[:pos + 1] if e in reference])
            val += 1.0 * cnt / (pos + 1)
    return val / len(reference)

def evaluate(y_gold, y_pred, cnt_none = True):
    '''
    Only works for Average Precision(AP) ranking score evaluation,
    in y_gold & y_pred, consecutive 2 labels should belong to the same instance.
    '''
    assert(len(y_gold) % 2 == 0)
    y_gold = [[y_gold[i], y_gold[i + 1]] for i in range(len(y_gold)) if i % 2 == 0]
    if not cnt_none:  # delete None label in prediction
        for i in xrange(len(y_pred)):
            y_pred[i].remove(0)
        for i in xrange(len(y_gold)):
            while 0 in y_gold[i]: y_gold[i].remove(0)    
    assert(len(y_gold) == len(y_pred))

    ap = []
    for gold, pred in zip(y_gold, y_pred):
        val, pred = 0.0, pred[:len(gold)]
        if len(gold) > 0:
            ap.append(average_precision(pred, gold))
    
    precision = sum(ap) / len(ap)
    return precision

classifiers = {'svm' : svm.SVC(kernel = 'linear', probability = True), 
               'rf' : RF(n_estimators = 200, n_jobs = 5), 
               'gbdt' : GBDT()}

if __name__ == '__main__':
    parser = argparse.ArgumentParser()
    parser.add_argument('train', help = 'training data file')
    parser.add_argument('test', help = 'test data file')
    parser.add_argument('multilabel', help = 'whether this is a multilabel classification problem')
    parser.add_argument('model', help = 'specify the classifier to use')
    options = parser.parse_args()
    print options
    if not options.model in classifiers:
        print 'Invalid model:', options.model
        print 'Available models:'
        for clf in classifiers: print clf,
        print 
        exit(1)
    stdout.flush()
    multilabel = int(options.multilabel)
    
    x_train, y_train = load_data(options.train)    
    x_test, y_test = load_data(options.test)
    print 'Number of labels:', max(y_train) + 1
    print 'Number of training examples:', len(x_train)
    print 'Number of test exampples:', len(x_test)
    stdout.flush()

    vectorizer = DictVectorizer()
    x_train = vectorizer.fit_transform(x_train)
    # print 'Dimension of feature vector:', len(x_train[0])
    clf = classifiers[options.model]
    if isinstance(clf, GBDT): x_train = x_train.toarray()
    print 'Start to train...'
    stdout.flush()
    clf.fit(x_train, y_train)
    print 'Finish training, start to predict...'

    x_test = vectorizer.transform(x_test)
    if isinstance(clf, GBDT): x_test = x_test.toarray()

    if multilabel == 0:
        y_pred = clf.predict(x_test)
        print classification_report(y_test, y_pred)
    else:
        # assert(len(x_test) % 2 == 0)
        # get probabilistic values for ranking
        y_pred, prob_val = [], clf.predict_proba(x_test)
        assert(len(prob_val) == len(y_test))
        # ignore duplicate values
        prob_val = [prob_val[i] for i in xrange(len(prob_val)) if i % 2 == 0]
        for prob in prob_val:
            tmp = [(i, prob[i]) for i in xrange(len(prob))]
            # sort in probability descending order
            tmp.sort(key = lambda (idx, p): p, reverse = True)
            cur = []
            for idx, p in tmp:
                cur.append(idx)
            y_pred.append(cur)            
        print 'Average precision with none:', evaluate(y_test, deepcopy(y_pred), cnt_none = True)
        print 'Average precision without none:', evaluate(y_test, deepcopy(y_pred), cnt_none = False)

    print 'Done.'
    stdout.flush()

