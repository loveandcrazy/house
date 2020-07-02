package org.linlinjava.litemall.core.eth;


import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 3.4.0.
 */
public class LeaseContract extends Contract {
    private static final String BINARY = "608060405234801561001057600080fd5b50611bc4806100206000396000f3fe60806040526004361061007d576000357c010000000000000000000000000000000000000000000000000000000090048063439f5ac21461008257806349bb8fff146101125780638f95b8f31461069d57806398d5fdca146106d85780639af4e8a114610703578063ac9e3af814610ca6578063c828371e14610cd1575b600080fd5b34801561008e57600080fd5b50610097610d61565b6040518080602001828103825283818151815260200191508051906020019080838360005b838110156100d75780820151818401526020810190506100bc565b50505050905090810190601f1680156101045780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b34801561011e57600080fd5b5061069b600480360361014081101561013657600080fd5b810190808035906020019064010000000081111561015357600080fd5b82018360208201111561016557600080fd5b8035906020019184600183028401116401000000008311171561018757600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f820116905080830192505050505050509192919290803590602001906401000000008111156101ea57600080fd5b8201836020820111156101fc57600080fd5b8035906020019184600183028401116401000000008311171561021e57600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f8201169050808301925050505050505091929192908035906020019064010000000081111561028157600080fd5b82018360208201111561029357600080fd5b803590602001918460018302840111640100000000831117156102b557600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f8201169050808301925050505050505091929192908035906020019064010000000081111561031857600080fd5b82018360208201111561032a57600080fd5b8035906020019184600183028401116401000000008311171561034c57600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f820116905080830192505050505050509192919290803590602001906401000000008111156103af57600080fd5b8201836020820111156103c157600080fd5b803590602001918460018302840111640100000000831117156103e357600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f8201169050808301925050505050505091929192908035906020019064010000000081111561044657600080fd5b82018360208201111561045857600080fd5b8035906020019184600183028401116401000000008311171561047a57600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f820116905080830192505050505050509192919290803590602001906401000000008111156104dd57600080fd5b8201836020820111156104ef57600080fd5b8035906020019184600183028401116401000000008311171561051157600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f820116905080830192505050505050509192919290803590602001909291908035906020019064010000000081111561057e57600080fd5b82018360208201111561059057600080fd5b803590602001918460018302840111640100000000831117156105b257600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f8201169050808301925050505050505091929192908035906020019064010000000081111561061557600080fd5b82018360208201111561062757600080fd5b8035906020019184600183028401116401000000008311171561064957600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f820116905080830192505050505050509192919290505050610e03565b005b3480156106a957600080fd5b506106d6600480360360208110156106c057600080fd5b8101908080359060200190929190505050610ee5565b005b3480156106e457600080fd5b506106ed610eef565b6040518082815260200191505060405180910390f35b34801561070f57600080fd5b50610c8c600480360361014081101561072757600080fd5b810190808035906020019064010000000081111561074457600080fd5b82018360208201111561075657600080fd5b8035906020019184600183028401116401000000008311171561077857600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f820116905080830192505050505050509192919290803590602001906401000000008111156107db57600080fd5b8201836020820111156107ed57600080fd5b8035906020019184600183028401116401000000008311171561080f57600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f8201169050808301925050505050505091929192908035906020019064010000000081111561087257600080fd5b82018360208201111561088457600080fd5b803590602001918460018302840111640100000000831117156108a657600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f8201169050808301925050505050505091929192908035906020019064010000000081111561090957600080fd5b82018360208201111561091b57600080fd5b8035906020019184600183028401116401000000008311171561093d57600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f820116905080830192505050505050509192919290803590602001906401000000008111156109a057600080fd5b8201836020820111156109b257600080fd5b803590602001918460018302840111640100000000831117156109d457600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f82011690508083019250505050505050919291929080359060200190640100000000811115610a3757600080fd5b820183602082011115610a4957600080fd5b80359060200191846001830284011164010000000083111715610a6b57600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f82011690508083019250505050505050919291929080359060200190640100000000811115610ace57600080fd5b820183602082011115610ae057600080fd5b80359060200191846001830284011164010000000083111715610b0257600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f8201169050808301925050505050505091929192908035906020019092919080359060200190640100000000811115610b6f57600080fd5b820183602082011115610b8157600080fd5b80359060200191846001830284011164010000000083111715610ba357600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f82011690508083019250505050505050919291929080359060200190640100000000811115610c0657600080fd5b820183602082011115610c1857600080fd5b80359060200191846001830284011164010000000083111715610c3a57600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f820116905080830192505050505050509192919290505050610ef9565b604051808215151515815260200191505060405180910390f35b348015610cb257600080fd5b50610cbb611a47565b6040518082815260200191505060405180910390f35b348015610cdd57600080fd5b50610ce6611a51565b6040518080602001828103825283818151815260200191508051906020019080838360005b83811015610d26578082015181840152602081019050610d0b565b50505050905090810190601f168015610d535780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b606060078054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015610df95780601f10610dce57610100808354040283529160200191610df9565b820191906000526020600020905b815481529060010190602001808311610ddc57829003601f168201915b5050505050905090565b8860019080519060200190610e19929190611af3565b508960009080519060200190610e30929190611af3565b508660049080519060200190610e47929190611af3565b508760039080519060200190610e5e929190611af3565b508560059080519060200190610e75929190611af3565b508460069080519060200190610e8c929190611af3565b508360079080519060200190610ea3929190611af3565b50826008819055508160099080519060200190610ec1929190611af3565b5080600a9080519060200190610ed8929190611af3565b5050505050505050505050565b8060028190555050565b6000600854905090565b6000896040516020018080602001828103825283818151815260200191508051906020019080838360005b83811015610f3f578082015181840152602081019050610f24565b50505050905090810190601f168015610f6c5780820380516001836020036101000a031916815260200191505b509250505060405160208183030381529060405280519060200120600160405160200180806020018281038252838181546001816001161561010002031660029004815260200191508054600181600116156101000203166002900480156110155780601f10610fea57610100808354040283529160200191611015565b820191906000526020600020905b815481529060010190602001808311610ff857829003601f168201915b5050925050506040516020818303038152906040528051906020012014801561117157508a6040516020018080602001828103825283818151815260200191508051906020019080838360005b8381101561107d578082015181840152602081019050611062565b50505050905090810190601f1680156110aa5780820380516001836020036101000a031916815260200191505b509250505060405160208183030381529060405280519060200120600060405160200180806020018281038252838181546001816001161561010002031660029004815260200191508054600181600116156101000203166002900480156111535780601f1061112857610100808354040283529160200191611153565b820191906000526020600020905b81548152906001019060200180831161113657829003601f168201915b50509250505060405160208183030381529060405280519060200120145b80156112b05750876040516020018080602001828103825283818151815260200191508051906020019080838360005b838110156111bc5780820151818401526020810190506111a1565b50505050905090810190601f1680156111e95780820380516001836020036101000a031916815260200191505b509250505060405160208183030381529060405280519060200120600460405160200180806020018281038252838181546001816001161561010002031660029004815260200191508054600181600116156101000203166002900480156112925780601f1061126757610100808354040283529160200191611292565b820191906000526020600020905b81548152906001019060200180831161127557829003601f168201915b50509250505060405160208183030381529060405280519060200120145b80156113ef5750886040516020018080602001828103825283818151815260200191508051906020019080838360005b838110156112fb5780820151818401526020810190506112e0565b50505050905090810190601f1680156113285780820380516001836020036101000a031916815260200191505b509250505060405160208183030381529060405280519060200120600360405160200180806020018281038252838181546001816001161561010002031660029004815260200191508054600181600116156101000203166002900480156113d15780601f106113a6576101008083540402835291602001916113d1565b820191906000526020600020905b8154815290600101906020018083116113b457829003601f168201915b50509250505060405160208183030381529060405280519060200120145b801561152e5750866040516020018080602001828103825283818151815260200191508051906020019080838360005b8381101561143a57808201518184015260208101905061141f565b50505050905090810190601f1680156114675780820380516001836020036101000a031916815260200191505b509250505060405160208183030381529060405280519060200120600560405160200180806020018281038252838181546001816001161561010002031660029004815260200191508054600181600116156101000203166002900480156115105780601f106114e557610100808354040283529160200191611510565b820191906000526020600020905b8154815290600101906020018083116114f357829003601f168201915b50509250505060405160208183030381529060405280519060200120145b801561166d5750856040516020018080602001828103825283818151815260200191508051906020019080838360005b8381101561157957808201518184015260208101905061155e565b50505050905090810190601f1680156115a65780820380516001836020036101000a031916815260200191505b5092505050604051602081830303815290604052805190602001206006604051602001808060200182810382528381815460018160011615610100020316600290048152602001915080546001816001161561010002031660029004801561164f5780601f106116245761010080835404028352916020019161164f565b820191906000526020600020905b81548152906001019060200180831161163257829003601f168201915b50509250505060405160208183030381529060405280519060200120145b80156117ac5750846040516020018080602001828103825283818151815260200191508051906020019080838360005b838110156116b857808201518184015260208101905061169d565b50505050905090810190601f1680156116e55780820380516001836020036101000a031916815260200191505b5092505050604051602081830303815290604052805190602001206007604051602001808060200182810382528381815460018160011615610100020316600290048152602001915080546001816001161561010002031660029004801561178e5780601f106117635761010080835404028352916020019161178e565b820191906000526020600020905b81548152906001019060200180831161177157829003601f168201915b50509250505060405160208183030381529060405280519060200120145b80156117b9575083600854145b80156118f85750826040516020018080602001828103825283818151815260200191508051906020019080838360005b838110156118045780820151818401526020810190506117e9565b50505050905090810190601f1680156118315780820380516001836020036101000a031916815260200191505b509250505060405160208183030381529060405280519060200120600960405160200180806020018281038252838181546001816001161561010002031660029004815260200191508054600181600116156101000203166002900480156118da5780601f106118af576101008083540402835291602001916118da565b820191906000526020600020905b8154815290600101906020018083116118bd57829003601f168201915b50509250505060405160208183030381529060405280519060200120145b8015611a375750816040516020018080602001828103825283818151815260200191508051906020019080838360005b83811015611943578082015181840152602081019050611928565b50505050905090810190601f1680156119705780820380516001836020036101000a031916815260200191505b509250505060405160208183030381529060405280519060200120600a6040516020018080602001828103825283818154600181600116156101000203166002900481526020019150805460018160011615610100020316600290048015611a195780601f106119ee57610100808354040283529160200191611a19565b820191906000526020600020905b8154815290600101906020018083116119fc57829003601f168201915b50509250505060405160208183030381529060405280519060200120145b90509a9950505050505050505050565b6000600254905090565b606060068054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015611ae95780601f10611abe57610100808354040283529160200191611ae9565b820191906000526020600020905b815481529060010190602001808311611acc57829003601f168201915b5050505050905090565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10611b3457805160ff1916838001178555611b62565b82800160010185558215611b62579182015b82811115611b61578251825591602001919060010190611b46565b5b509050611b6f9190611b73565b5090565b611b9591905b80821115611b91576000816000905550600101611b79565b5090565b9056fea165627a7a72305820ba3ef1d21dba8d8bbb9ce1f0f7de0b6e4afe1a74e19f933aaeaa131cc845c7510029";

    public static final String FUNC_CHANGE_STATUS = "change_status";

    public static final String FUNC_GENERATECONTRACT = "generateContract";

    public static final String FUNC_GETENDTIME = "getEndTime";

    public static final String FUNC_GETPRICE = "getPrice";

    public static final String FUNC_GETSTARTTIME = "getStartTime";

    public static final String FUNC_GETSTAUTS = "getStauts";

    public static final String FUNC_PROVECONTRACT = "proveContract";

    protected LeaseContract(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected LeaseContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }
    
    
    protected LeaseContract(String contractAddress, Web3j web3j, Credentials credentials,
			ContractGasProvider contractGasProvider) {
		super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
	}
    
    protected LeaseContract(String contractAddress, Web3j web3j, TransactionManager transactionManager,
			ContractGasProvider contractGasProvider) {
		super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
	}

    
    

    public RemoteCall<TransactionReceipt> change_status(int theStatus) {
        final Function function = new Function(
                FUNC_CHANGE_STATUS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(theStatus)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> generateContract(String contractSn, int userid1, int userid2, String name1, String name2, String idcard1, String idcard2,
                                                           String price, String start_time,String end_time, String update_time, String province, String city, String county,String address_detail) {
        final Function function = new Function(
                FUNC_GENERATECONTRACT, 
                Arrays.<Type>asList(
                new org.web3j.abi.datatypes.Utf8String(contractSn),
                new org.web3j.abi.datatypes.Utf8String(String.valueOf(userid1) ),
                new org.web3j.abi.datatypes.Utf8String(String.valueOf(userid2)),
                new org.web3j.abi.datatypes.Utf8String(name1),
                new org.web3j.abi.datatypes.Utf8String(name2),
                new org.web3j.abi.datatypes.Utf8String(idcard1),
                new org.web3j.abi.datatypes.Utf8String(idcard2),
                new org.web3j.abi.datatypes.Utf8String(price),
                new org.web3j.abi.datatypes.Utf8String(start_time),
                new org.web3j.abi.datatypes.Utf8String(end_time),
                new org.web3j.abi.datatypes.Utf8String(update_time),
                new org.web3j.abi.datatypes.Utf8String(province),
                new org.web3j.abi.datatypes.Utf8String(city),
                        new org.web3j.abi.datatypes.Utf8String(county),
                new org.web3j.abi.datatypes.Utf8String(address_detail)),
        Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    
    public RemoteCall<Utf8String> getEndTime() {
        final Function function = new Function(
                FUNC_GETENDTIME, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}) 
                );
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteCall<Uint256> getPrice() {
        final Function function = new Function(
                FUNC_GETPRICE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {})
                );
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteCall<Utf8String> getStartTime() {
        final Function function = new Function(
                FUNC_GETSTARTTIME, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}) 
                );
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteCall<Uint256> getStauts() {
        final Function function = new Function(
                FUNC_GETSTAUTS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {})
                );
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteCall<Bool> proveContract(String contractSn, int userid1, int userid2, String name1, String name2, String idcard1, String idcard2,
                                          String price, String start_time,String end_time, String update_time, String province, String city, String county,String address_detail) {
        final Function function = new Function(
                FUNC_PROVECONTRACT, 
                Arrays.<Type>asList(
                        new org.web3j.abi.datatypes.Utf8String(contractSn),
                        new org.web3j.abi.datatypes.Utf8String(String.valueOf(userid1) ),
                        new org.web3j.abi.datatypes.Utf8String(String.valueOf(userid2)),
                        new org.web3j.abi.datatypes.Utf8String(name1),
                        new org.web3j.abi.datatypes.Utf8String(name2),
                        new org.web3j.abi.datatypes.Utf8String(idcard1),
                        new org.web3j.abi.datatypes.Utf8String(idcard2),
                        new org.web3j.abi.datatypes.Utf8String(price),
                        new org.web3j.abi.datatypes.Utf8String(start_time),
                        new org.web3j.abi.datatypes.Utf8String(end_time),
                        new org.web3j.abi.datatypes.Utf8String(update_time),
                        new org.web3j.abi.datatypes.Utf8String(province),
                        new org.web3j.abi.datatypes.Utf8String(city),
                        new org.web3j.abi.datatypes.Utf8String(county),
                        new org.web3j.abi.datatypes.Utf8String(address_detail)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {})
                );
        return executeRemoteCallSingleValueReturn(function);
    }

    public static RemoteCall<LeaseContract> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(LeaseContract.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<LeaseContract> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(LeaseContract.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static LeaseContract load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new LeaseContract(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static LeaseContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new LeaseContract(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }
}
