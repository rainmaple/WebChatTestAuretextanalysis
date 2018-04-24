### WebChatTestAuretextanalysis
####引言

这是一种通过web应用来实现关键词提取的小应用，我试用了国内Microsoft AZure产品中AI和机器学习模块的text analysis的接口，感觉性能不错，但是json的解析需要花费心思。但是感觉在解析方面积累了小小的经验。
下面是效果图
应用采用聊天的方式进行交互。

![这里写图片描述](https://img-blog.csdn.net/2018042418450170?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3JhaW5tYXBsZTIwMTg2/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)

#### 演示部分
<br>
![这里写图片描述](https://img-blog.csdn.net/20180424184532997?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3JhaW5tYXBsZTIwMTg2/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)

前台的构建采用了jQuery-chat的构建方式，然后使用ajax进行传值，无须刷新页面下面给出前台传值的代码


后台还是使用servlet进行传值并且来调用接口
主要是接口的调用部分

#### text analysis api 的调用方式

这是一个 [api官方文档入口](https://azure.microsoft.com/zh-cn/try/cognitive-services/?api=text-analytics) 官方是一元免费试用三十天，还是很人性化的


#### 接口的调用使用java实现

后台的解析结果
![这里写图片描述](https://img-blog.csdn.net/2018042420340425?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3JhaW5tYXBsZTIwMTg2/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)
