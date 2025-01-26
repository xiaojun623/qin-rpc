package com.qin.qinrpc.loadbalancer;

import cn.hutool.core.collection.CollUtil;
import com.google.common.hash.Hashing;
import com.qin.qinrpc.model.ServiceMetaInfo;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 一致性哈希负载均衡器
 */
public class MyConsistentHashLoadBalancer implements LoadBalancer {

    /**
     * 一致性 Hash 环，存放虚拟节点
     */
    private final TreeMap<Integer, ServiceMetaInfo> hashRing = new TreeMap<>();

    /**
     * 虚拟节点数
     */
    private static final int VIRTUAL_NODES = 10;

    @Override
    public ServiceMetaInfo select(Map<String, Object> requestParams, List<ServiceMetaInfo> serviceMetaInfoList) {
        if (CollUtil.isEmpty(serviceMetaInfoList)) {
            return null;
        }

        int size = serviceMetaInfoList.size();
        if (size == 1) {
            return serviceMetaInfoList.get(0);
        }
        // 构造哈希环
        for (ServiceMetaInfo info : serviceMetaInfoList) {
            for (int i = 0; i < VIRTUAL_NODES; i++) {
                int hash = getHash(info.getServiceAddress() + "#" + i);
                hashRing.put(hash, info);
            }
        }

        // 获取请求参数的哈希值
        int hash = getHash(requestParams);
        // 选择最接近且大于等于调用请求 hash 值的虚拟节点
        Map.Entry<Integer, ServiceMetaInfo> entry = hashRing.ceilingEntry(hash);
        if (entry == null) {
            // 如果没有大于等于调用请求 hash 值的虚拟节点，则返回环首部的节点
            entry = hashRing.firstEntry();
        }

        return entry.getValue();
    }

    /**
     * 先获取对象的哈希值，然后使用Guava库提供的SHA256哈希算法对编码值进行哈希计算，返回整型
     * @param object
     * @return
     */
    private int getHash(Object object) {
        return Hashing.sha256().hashInt(object.hashCode()).asInt();
    }
}
