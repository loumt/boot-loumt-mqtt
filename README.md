
* SpringBoot + MQTT

* [参考](https://www.jianshu.com/p/8b0291e8ee02)


1. Qos(Quality of Server)

```
 QoS = 0
    对于qos1而言，对于client而言，有且仅发一次publish包
    对于broker而言，有且仅发一次publish
    简而言之，就是仅发一次包，是否收到完全不管，适合那些不是很重要的数据。

    1. Publisher ---msg---> Broker ---msg----> Subscriber
    2. Publisher Delete Msg
    
```

```
 QoS = 1
    对于qos0而言，这个交互就是多了一次ack的作用
    但是会有个问题，尽管我们可以通过确认来保证一定收到客户端或服务器的message
    但是我们却不能保证message仅有一次
    也就是当client没收到service的puback或者service没有收到client的puback
    那么就会一直发送publisher

    1. Publisher ---Store(msg)---msg---> Broker
    2. Broker ---Store(msg)---msg---> Subscriber
    3. Broker ---PuBack---> Publisher.Delete(msg)
    4. Subscriber ---PuBack---> Broker.Delete(msg)
    5. 若其中一个PuBack不存在,一直发送msg
    
    QoS1是只要sever接收到message就会publish给他的subscriber，
    网络不好的时候sender是会重复发送相同的message的，server也就会重复publish相同的message给他的。
```

```
 Qos = 2
    对于qos1而言，qos2可以实现仅仅接受一次message，其主要原理(对于publisher而言)，
    publisher和broker进行了缓存，其中publisher缓存了message和msgID
    而broker缓存了msgID，两方都做记录所以可以保证消息不重复
    但是由于记录是需要删除的，这个删除流程同样多了一倍

    1.Publisher ---Store(msg)---msg---> Broker
    2.Broker ---Store(msg)---PubRec(with msgId)---> Publisher (Borker存储并通知Publisher收到消息)
    3.Publisher ---PubRel---> Broker ---> msg ---> Subscriber (Publisher告知Borker可以下一步了)
    4.Broker ---PubCom---Publisher.Delete(msg) (Broker通知Publisher完成传输,Publisher删除存储)
    5.Subscriber ---Store(msg)--- PubRec----> Borker (Subscriber通知Borker已经接受到消息了)
    6.Borker --- PubRel ---> Subscriber.Notify(msg) (告诉Subscriber你可以做下一步了,Subscriber响应消息)
    7.Subscriber ---PubCom ----> Borker.Delete(msg),Subscriber.Delete(msg) (Subscriber告诉Broker我完成响应了,Borker和Subscriber都删除消息)

    如何保证消息只发一次:
        我们以第一种action为例（注意这里第一种action存储的是message，第二种action存储的是message ID）：
        1 若client没收到来自sever的pubcomp：那么client将重发pubrel,
          意思是sever将收到两次pubrel；但是sever并不会将消息发送两次,
          因为在第一次将消息发送给订阅者之后，server将删除这条消息的内容（delete message），
          而消息的内容是在publish中传输的，而不是在pubrel中传输的；
        2 若client没收到来自server的pubrec，那么client将重发publish,
          意思是sever将收到两次publish，但是这里sever只是存储了信息，
          而且后面在pubrec中会有消息的ID，而client可以根据两次ID是否相同来判断
          server收到了几次同样的消息。
        
        综上所述，这就保证了server不会将消息重发。
        
    QoS2的server端处理逻辑有点特别：
        如果sender没收到server的PUBRECV， sender 是会重发
        但是对于上一条message，server有两种处理方式：
          1, message存在本地，先不publish给subscriber；
          2，存下message ID，把message publish 给他的subscriber。
        对于第一种处理方式，如果sender继续重发，且被收到（ID相同），
            那在server端只算一条message，继续等sender发PUBREL，
            这样server就保证只publish了一条message，而不是多条。 
        对于第二种处理方式，如果sender继续重发，且被收到，sever会检查它的message ID，
            如果重发过来的message ID是之前存过的，server就不会publish给他的subscriber，
            因为之前已经publish了。直接删除。
   
```