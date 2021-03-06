package org.hongxi.whatsmars.mq.rocketmq.spring;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

public class ProducerFactoryBean implements FactoryBean<DefaultMQProducer>,InitializingBean,DisposableBean {

    private DefaultMQProducer producer;

    private String instanceName; // 不同集群不同值

    private String producerGroup;

    private String namesrvAddr;

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }

    public void setProducerGroup(String producerGroup) {
        this.producerGroup = producerGroup;
    }

    public void setNamesrvAddr(String namesrvAddr) {
        this.namesrvAddr = namesrvAddr;
    }

    @Override
    public DefaultMQProducer getObject() throws Exception {
        return producer;
    }

    @Override
    public Class<?> getObjectType() {
        return DefaultMQProducer.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        producer = new DefaultMQProducer(producerGroup);
        producer.setInstanceName(instanceName);
        producer.setNamesrvAddr(namesrvAddr);
        producer.start();
    }

    @Override
    public void destroy() throws Exception {
        producer.shutdown();
    }
}
