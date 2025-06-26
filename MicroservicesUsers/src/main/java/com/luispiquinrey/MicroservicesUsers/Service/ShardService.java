package com.luispiquinrey.MicroservicesUsers.Service;

import org.springframework.stereotype.Service;

@Service
public class ShardService {
    public String determineShardKey(Long id_user){
        return "shard" + (id_user % 2 +1);
    }
}
