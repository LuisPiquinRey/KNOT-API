package com.luispiquinrey.MicroservicesUsers.Configuration;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.lang.Nullable;

public class ShardRoutingDataSource extends AbstractRoutingDataSource{
    private static final ThreadLocal<String> currentShard=new ThreadLocal<>();

    public static void setCurrentShard(String shardId){
        currentShard.set(shardId);
    }
    public static String getCurrentShard(){
        return currentShard.get();
    }
    public static void clearCurrentShard(){
        currentShard.remove();
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return getCurrentShard();
    }
}
