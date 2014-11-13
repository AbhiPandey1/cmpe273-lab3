package edu.sjsu.cmpe.cache.client;

import java.util.ArrayList;
import java.util.List;

import com.google.common.hash.Hashing;

import edu.sjsu.cmpe.cache.client.DistributedCacheService;

public class Client {

    public static void main(String[] args) throws Exception {
    /*    System.out.println("Starting Cache Client...");
        CacheServiceInterface cache = new DistributedCacheService(
                "http://localhost:3000");

        cache.put(1, "foo");
        System.out.println("put(1 => foo)");

        String value = cache.get(1);
        System.out.println("get(1) => " + value);

        System.out.println("Existing Cache Client...");
*/    
    	System.out.println("Starting Cache Client...");
        char[] distributedData = {
            '0', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j'
        };
        List < DistributedCacheService > NodeRing = new ArrayList < DistributedCacheService > ();
        
        //Introducing  three new servers to implement distributed service.
        
        NodeRing.add(new DistributedCacheService("http://localhost:3000"));
        NodeRing.add(new DistributedCacheService("http://localhost:3001"));
        NodeRing.add(new DistributedCacheService("http://localhost:3002"));
        
        System.out.println("---- Inserting the Key Value Pattern for Horizontal Sharding----");
        for (int implementInsertionKey = 1; implementInsertionKey <= 10; implementInsertionKey++) {
            int generatedHashkeyForInsertion  = Hashing.consistentHash(Hashing.md5().hashString(Integer.toString(implementInsertionKey)), NodeRing.size());
            NodeRing.get(generatedHashkeyForInsertion).put(implementInsertionKey, Character.toString(distributedData[implementInsertionKey]));
            System.out.println("Key Value Pattern Pair " + implementInsertionKey + "-" + distributedData[implementInsertionKey] + " is distributed to the  server " + generatedHashkeyForInsertion);
        }
        System.out.println("---- Retriving  the Key Value Pattern for Horizontal Sharding ----");
        for (int implementRetrivalKey = 1; implementRetrivalKey <= 10; implementRetrivalKey++) {
            int generatedHashkeyForRetrival = Hashing.consistentHash(Hashing.md5().hashString(Integer.toString(implementRetrivalKey)), NodeRing.size());
            System.out.println("Key Value Pattern Pair" + implementRetrivalKey + "-" + NodeRing.get(generatedHashkeyForRetrival).get(implementRetrivalKey) + " is distributed to the  server  " + generatedHashkeyForRetrival);;
        }
        System.out.println("Exiting Cache Client...");
    }
    	
    	
    }



