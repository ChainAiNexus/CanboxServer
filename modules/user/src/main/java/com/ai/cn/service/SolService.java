package com.ai.cn.service;

import com.alibaba.fastjson.JSONObject;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author kires
 * @date 2024/9/11
 */
public interface SolService {

    /**
     *
     * @return 0
     */
    BigInteger getBlockNum();

    /**
     *        slot
     * @return 0
     */
    BigInteger getLastSlotNum();

    /**
     *
     * @param blockNum
     * @return JSONObject
     */
    JSONObject getBlocksByNum(BigInteger blockNum);

    /**
     *
     * @param signature
     * @return JSONObject
     */
    JSONObject getTransaction(String signature);

    /**
     *
     * @param address
     * @param until
     * @return json
     */
    JSONObject getSignaturesForAddress(String address,String until);

    /**
     *   sol
     * @param address
     * @return 0
     */
    BigDecimal getBalance(String address);

    /**
     *
     * @param address
     * @param tokenAddress
     * @return 0
     */
    BigDecimal getTokenBalance(String address,String tokenAddress,String url);

    /**
     *
     * @param fromAddress
     * @param toAddress
     * @param amount
     * @param secretKey
     * @return hash
     * @throws Exception
     */
    String transfer(String fromAddress, String toAddress, long amount,String secretKey) throws Exception;

    /**
     *
     * @param tokenAddress
     * @param mintAddress
     * @return 0
     */
    String getAccountInfo(String tokenAddress,String mintAddress);


}
