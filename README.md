# weibo-emotion-analyzer

微博情感分析项目，2015年《自然语言处理高级专题》课程作业

# how to run

1.将项目import到eclipse中，选择Main.java -> run as -> java application， 或者用命令行也行，该步会生成特征文件

2.切换到scripts目录，运行以下命令：
    ./gendata.sh && run.sh 1>log.txt 2>&1 &

在scripts/log.txt中可以复现本实验的全部结果。

# 如何增加特征

新建一个class，实现FeatureExtractorInterface接口，并在FeatureExtractor类中setup调用registerExtractor进行注册即可。

# 模型

先用bag-of-words feature训练一个gradient boosting tree模型作为baseline

再尝试用CNN + Hierarchical Softmax改进，后面这个相对复杂，效果不一定好，需要不断尝试。

# 实验结果

## 观点分类

|     | precision | recall | F-measure |
|-----|-----------|--------|-----------|
| 0   | 0.90      | 0.81   | 0.86      |
| 1   | 0.71      | 0.84   | 0.77      |
| avg | 0.83      | 0.82   | 0.83      |

## 情绪分类average precision

|              | weibo-level | sentence-level |
|--------------|-------------|----------------|
| with None    | 0.91        | 0.955          |
| without None | 0.786       | 0.797          |


# 依赖的开源项目

[XGBoost](https://github.com/dmlc/xgboost)

[theano](https://github.com/Theano/Theano)

[Stanford CoreNLP](https://github.com/stanfordnlp/CoreNLP)

[Scikit Learn](https://github.com/scikit-learn/scikit-learn)

# 相同数据集上的tutorial

NLPCC14上有几个ppt：

[Emotion Classification of Chinese Microblog Text via Fusion of BoW and eVector Feature Representations](http://tcci.ccf.org.cn/conference/2014/ppts/nlpcc/ppt192.pdf)

基本思想：用了bag of words特征，然后自己搞了个公式，算出每个词在情绪上的分布，送给普通的分类器。

[A Novel Calibrated Label Ranking Based Method for Multiple Emotions Detection in Chinese Microblogs](http://tcci.ccf.org.cn/conference/2014/ppts/nlpcc/ppt200.pdf)

基本思想：这个是排名第一的系统，对emotion label去做ranking

[ Emotion Analysis in Chinese Weibo Texts(Tsinghua University)](http://tcci.ccf.org.cn/conference/2014/ppts/nlpcc/ppttmp02.pdf)

基本思想： 利用那些诸如😃的表情符号去做分类。

# contributors

[王亮](intfloat@pku.edu.cn)

[王异秀](labyrinth@pku.edu.cn)
