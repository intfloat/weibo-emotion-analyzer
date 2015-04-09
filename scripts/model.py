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
            idx, val = int(idx), float(val)
            x[idx] = val
        data.append([x, y])
    X, Y = [e[0] for e in data], [e[1] for e in data]
    return X, Y

def evaluate(y_gold, y_pred):
    '''
    Only works for Average Precision(AP) ranking score evaluation,
    in y_gold & y_pred, consecutive 2 labels should belong to the same instance.
    '''
    return 1.0

if __name__ == '__main__':
    parser = argparse.ArgumentParser()
    parser.add_argument('train', help = 'training data file')
    parser.add_argument('test', help = 'test data file')
    parser.add_argument('multilabel', help = 'whether this is a multilabel classification problem')
    options = parser.parse_args()
    multilabel = int(options.multilabel)
    
    x_train, y_train = load_data(options.train)    
    x_test, y_test = load_data(options.test)
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
    y_pred = clf.predict(x_test)
    print 'Finish predicting, start to evaluate...'

    if multilabel == 0:
        print classification_report(y_test, y_pred)
    else:
        evaluate(y_test, y_pred)

    print 'Done.'

