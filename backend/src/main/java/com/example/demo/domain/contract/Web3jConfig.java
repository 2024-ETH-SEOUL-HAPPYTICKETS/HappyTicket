package com.example.demo.domain.contract;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.StaticGasProvider;

import java.math.BigInteger;

@Configuration
@Slf4j
public class Web3jConfig {
    private String RPCURL = "https://devnet.neonevm.org";
//    private String INFURA_API_URL = "https://sepolia.infura.io/v3/790bcc6e0ad54306907fc3ec40048ba5";
    private String PRIVATE_KEY = "---privite key---";
    private String CONTRACT_TICKET = "0xD48875feb9E6105313007Afd7fe61bF23F5AF116";
    private String CONTRACT_MERCHANDISE = "0xb49D89F49856cf613634BF96b532C3F687138916";

    @Bean
    public Web3j web3j(){
        Web3j web3j = Web3j.build(new HttpService(RPCURL));
        // 노드와의 연결 확인
        try {
            web3j.web3ClientVersion().send().getWeb3ClientVersion();
            log.info("Connected to the node successfully");
        } catch (Exception e) {
            log.error("Failed to connect to the node: {}", e.getMessage());
        }
        return web3j;
    }
    @Bean
    public Credentials credentials() {
        return Credentials.create(PRIVATE_KEY);
    }

    @Bean
    public Ticket ticketContract(Web3j web3j , Credentials credentials) {
        ContractGasProvider contractGasProvider = new StaticGasProvider(BigInteger.valueOf(2_000_000_000L), BigInteger.valueOf(4_300_000));
        return Ticket.load(CONTRACT_TICKET, web3j, credentials, contractGasProvider);
    }

    @Bean
    public Merchandise merchandiseContract(Web3j web3j , Credentials credentials) {
        ContractGasProvider contractGasProvider = new StaticGasProvider(BigInteger.valueOf(2_000_000_000L), BigInteger.valueOf(4_300_000));
        return Merchandise.load(CONTRACT_MERCHANDISE, web3j, credentials, contractGasProvider);
    }
}
