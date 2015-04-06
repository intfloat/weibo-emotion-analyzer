# weibo-emotion-analyzer

微博情感分析项目，2015年《自然语言处理高级专题》课程作业

# how to run

将项目import到eclipse中，选择Main.java -> run as -> java application， 或者用命令行也行    

# 如何增加特征

新建一个class，实现FeatureExtractorInterface接口，并在FeatureExtractor类中setup调用registerExtractor进行注册即可。

# 模型

先用bag-of-words feature训练一个gradient boosting tree模型作为baseline

再尝试用CNN + Hierarchical Softmax改进，后面这个相对复杂，效果不一定好，需要不断尝试。

# contributors

[王亮](weibo.com/intfloat)

[王异秀]()
