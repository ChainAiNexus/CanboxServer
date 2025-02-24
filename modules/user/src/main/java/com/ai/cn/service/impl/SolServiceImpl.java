package com.ai.cn.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ai.cn.service.SolService;
import com.nft.cn.util.AesUtil;
import com.nft.cn.util.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.bitcoinj.core.Base58;
import org.p2p.solanaj.core.Account;
import org.p2p.solanaj.core.PublicKey;
import org.p2p.solanaj.core.Transaction;
import org.p2p.solanaj.programs.SystemProgram;
import org.p2p.solanaj.rpc.RpcClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

/**
 * @author kires
 * @date 2024/9/11
 */
@Slf4j
@Service
public class SolServiceImpl implements SolService {

    @Value("${solRpcUrl}")
    private String solRpcUrl;

    @Override
    public BigInteger getBlockNum() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("jsonrpc", "2.0");
        jsonObject.put("id", "getblock.io");
        jsonObject.put("method", "getBlockHeight");
        jsonObject.put("params", null);
        String string = HttpClientUtil.doPostJson(solRpcUrl, jsonObject.toString());
        return JSONObject.parseObject(string).getBigInteger("result");
    }

    @Override
    public BigInteger getLastSlotNum() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("jsonrpc", "2.0");
        jsonObject.put("id", "1");
        jsonObject.put("method", "getEpochInfo");
        jsonObject.put("params", null);
        String string = HttpClientUtil.doPostJson(solRpcUrl, jsonObject.toString());

        JSONObject jsonObject1 = JSONObject.parseObject(string);
        BigInteger bigInteger = JSONObject.parseObject(jsonObject1.getString("result")).getBigInteger("absoluteSlot");

        return bigInteger;
    }

    @Override
    public JSONObject getBlocksByNum(BigInteger blockNum) {
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(blockNum);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("encoding","jsonParsed");
        jsonObject.put("maxSupportedTransactionVersion",0);
        jsonArray.add(jsonObject);
        //
        JSONObject json = new JSONObject();
        json.put("jsonrpc", "2.0");
        json.put("id", "getblock.io");
        json.put("method", "getBlock");
        json.put("params", jsonArray);

        String string = HttpClientUtil.doPostJson(solRpcUrl, json.toString());
        //log.info("          :{}",string);
        return JSONObject.parseObject(string);
    }

    @Override
    public JSONObject getTransaction(String signature) {
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(signature);

        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.put("encoding","jsonParsed");
        jsonObject2.put("maxSupportedTransactionVersion",0);
        jsonArray.add(jsonObject2);


        JSONObject jsonObject = new JSONObject();
        jsonObject.put("jsonrpc", "2.0");
        jsonObject.put("id", "getblock.io");
        jsonObject.put("method", "getTransaction");
        jsonObject.put("params", jsonArray);
        String string = HttpClientUtil.doPostJson(solRpcUrl, jsonObject.toString());
        return JSONObject.parseObject(string);
    }

    @Override
    public JSONObject getSignaturesForAddress(String address,String until) {
        JSONObject json = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        jsonArray.add(address);

        if (until != null) {
            JSONObject jsonObject2 = new JSONObject();
            jsonObject2.put("until",until);
            //jsonObject2.put("before",until);
            //jsonObject2.put("limit",100);
            jsonArray.add(jsonObject2);
        }

        json.put("jsonrpc", "2.0");
        json.put("id", "getblock.io");
        json.put("method", "getSignaturesForAddress");
        json.put("params", jsonArray);
        String string = HttpClientUtil.doPostJson(solRpcUrl, json.toString());
        return JSONObject.parseObject(string);
    }

    @Override
    public BigDecimal getBalance(String address) {
        JSONObject json = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(address);


        JSONObject jsonObject = new JSONObject();
        jsonObject.put("encoding","jsonParsed");
        jsonObject.put("maxSupportedTransactionVersion",0);
        jsonArray.add(jsonObject);
        //

        json.put("jsonrpc", "2.0");
        json.put("id", "getblock.io");
        json.put("method", "getBalance");
        json.put("params", jsonArray);

        String string = HttpClientUtil.doPostJson(solRpcUrl, json.toString());

        JSONObject jsonResponse = JSONObject.parseObject(string);
        JSONObject resultObject = JSONObject.parseObject(jsonResponse.getString("result"));

        BigDecimal bigDecimal = resultObject.getBigDecimal("value");

        return  bigDecimal.divide(new BigDecimal("1000000000"),6, RoundingMode.DOWN).setScale(6, RoundingMode.DOWN);
    }

    @Override
    public BigDecimal getTokenBalance(String address, String tokenAddress,String url) {
        // JSON RPC

        JSONObject json = new JSONObject();
        JSONArray jsonArray = new JSONArray();


        JSONObject jsonObject333 = new JSONObject();
        jsonObject333.put("mint",tokenAddress);
        JSONObject jsonObject2222 = new JSONObject();
        jsonObject2222.put("encoding","jsonParsed");
        jsonArray.add(address);


        jsonArray.add(jsonObject333);
        jsonArray.add(jsonObject2222);
        //

        json.put("jsonrpc", "2.0");
        json.put("id", "getblock.io");
        json.put("method", "getTokenAccountsByOwner");
        json.put("params", jsonArray);

        //
        String string = HttpClientUtil.doPostJson(url, json.toString());

        if (string == null){
            return BigDecimal.ZERO;
        }

        JSONObject jsonObject1 = JSONObject.parseObject(string);

        JSONArray jsonArray1 = jsonObject1.getJSONObject("result").getJSONArray("value");

        if (jsonArray1.isEmpty()){
            return BigDecimal.ZERO;
        }

        Object object = jsonArray1.get(0);

        JSONObject jsonObject = JSONObject.parseObject(object.toString()).getJSONObject("account");

        JSONObject jsonObject2 = jsonObject.getJSONObject("data").getJSONObject("parsed").getJSONObject("info").getJSONObject("tokenAmount");

        return jsonObject2.getBigDecimal("uiAmount");
    }



    public static BigDecimal getTokenBalancePl(String address, String tokenAddress,String url) {
        // JSON RPC

        JSONObject json = new JSONObject();
        JSONArray jsonArray = new JSONArray();


        JSONObject jsonObject333 = new JSONObject();
        jsonObject333.put("mint",tokenAddress);
        JSONObject jsonObject2222 = new JSONObject();
        jsonObject2222.put("encoding","jsonParsed");
        jsonArray.add(address);


        jsonArray.add(jsonObject333);
        jsonArray.add(jsonObject2222);
        //

        json.put("jsonrpc", "2.0");
        json.put("id", "getblock.io");
        json.put("method", "getTokenAccountsByOwner");
        json.put("params", jsonArray);

        //
        String string = HttpClientUtil.doPostJson(url, json.toString());

        if (string == null){
            return BigDecimal.ZERO;
        }

        JSONObject jsonObject1 = JSONObject.parseObject(string);

        JSONArray jsonArray1 = jsonObject1.getJSONObject("result").getJSONArray("value");

        if (jsonArray1.isEmpty()){
            return BigDecimal.ZERO;
        }

        Object object = jsonArray1.get(0);

        JSONObject jsonObject = JSONObject.parseObject(object.toString()).getJSONObject("account");

        JSONObject jsonObject2 = jsonObject.getJSONObject("data").getJSONObject("parsed").getJSONObject("info").getJSONObject("tokenAmount");

        return jsonObject2.getBigDecimal("uiAmount");
    }

    @Override
    public String transfer(String fromAddress, String toAddress, long amount,String secretKey) throws Exception {
        RpcClient client = new RpcClient(solRpcUrl);
        //
        PublicKey fromPublicKey = new PublicKey(fromAddress);

        //
        PublicKey toPublickKey = new PublicKey(toAddress);

        //
        //0.009
        long lamports = amount;

        Account signer = new Account(Base58
                .decode(AesUtil.decrypt(secretKey)));

        Transaction transaction = new Transaction();
        transaction.addInstruction(SystemProgram.transfer(fromPublicKey, toPublickKey, lamports));

        //
        return client.getApi().sendTransaction(transaction, signer);
    }

    @Override
    public String getAccountInfo(String tokenAddress, String mintAddress) {
        JSONObject json = new JSONObject();
        json.put("jsonrpc", "2.0");
        json.put("id", "getblock.io");
        json.put("method", "getAccountInfo");
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(tokenAddress);

        JSONObject jsonObject2222 = new JSONObject();
        jsonObject2222.put("encoding","jsonParsed");
        jsonArray.add(jsonObject2222);

        json.put("params", jsonArray);

        //
        String string = HttpClientUtil.doPostJson(solRpcUrl, json.toString());

        JSONObject result = JSONObject.parseObject(string).getJSONObject("result");

        JSONObject dataJson = result.getJSONObject("value").getJSONObject("data");

        JSONObject infoJson = dataJson.getJSONObject("parsed").getJSONObject("info");

        if (!infoJson.getString("mint").equalsIgnoreCase(mintAddress)) {
            return null;
        }

        return infoJson.getString("owner");
    }
}
