package org.linlinjava.litemall.core.eth;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 3.4.0.
 */
public class User extends Contract {
    private static final String BINARY = "608060405234801561001057600080fd5b50620f42406002819055506108528061002a6000396000f3fe608060405260043610610088576000357c010000000000000000000000000000000000000000000000000000000090048063112dd7bb1461008d57806320b3b1bf146100c85780633a7d280c146100f35780633ffbd47f146101d357806398e1b41014610332578063a4f772e51461035d578063d0bc1a8814610398578063f43ad89e146103d3575b600080fd5b34801561009957600080fd5b506100c6600480360360208110156100b057600080fd5b81019080803590602001909291905050506104b3565b005b3480156100d457600080fd5b506100dd6104bd565b6040518082815260200191505060405180910390f35b3480156100ff57600080fd5b506101b96004803603602081101561011657600080fd5b810190808035906020019064010000000081111561013357600080fd5b82018360208201111561014557600080fd5b8035906020019184600183028401116401000000008311171561016757600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f8201169050808301925050505050505091929192905050506104c7565b604051808215151515815260200191505060405180910390f35b3480156101df57600080fd5b50610330600480360360408110156101f657600080fd5b810190808035906020019064010000000081111561021357600080fd5b82018360208201111561022557600080fd5b8035906020019184600183028401116401000000008311171561024757600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f820116905080830192505050505050509192919290803590602001906401000000008111156102aa57600080fd5b8201836020820111156102bc57600080fd5b803590602001918460018302840111640100000000831117156102de57600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f820116905080830192505050505050509192919290505050610560565b005b34801561033e57600080fd5b50610347610615565b6040518082815260200191505060405180910390f35b34801561036957600080fd5b506103966004803603602081101561038057600080fd5b810190808035906020019092919050505061061f565b005b3480156103a457600080fd5b506103d1600480360360208110156103bb57600080fd5b8101908080359060200190929190505050610632565b005b3480156103df57600080fd5b50610499600480360360208110156103f657600080fd5b810190808035906020019064010000000081111561041357600080fd5b82018360208201111561042557600080fd5b8035906020019184600183028401116401000000008311171561044757600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f820116905080830192505050505050509192919290505050610645565b604051808215151515815260200191505060405180910390f35b8060038190555050565b6000600354905090565b6000816040516020018080602001828103825283818151815260200191508051906020019080838360005b8381101561050d5780820151818401526020810190506104f2565b50505050905090810190601f16801561053a5780820380516001836020036101000a031916815260200191505b509250505060405160208183030381529060405280519060200120600154149050919050565b8160009080519060200190610576929190610781565b50806040516020018080602001828103825283818151815260200191508051906020019080838360005b838110156105bb5780820151818401526020810190506105a0565b50505050905090810190601f1680156105e85780820380516001836020036101000a031916815260200191505b50925050506040516020818303038152906040528051906020012060018190555060006003819055505050565b6000600254905090565b8060026000828254039250508190555050565b8060026000828254019250508190555050565b6000816040516020018080602001828103825283818151815260200191508051906020019080838360005b8381101561068b578082015181840152602081019050610670565b50505050905090810190601f1680156106b85780820380516001836020036101000a031916815260200191505b50925050506040516020818303038152906040528051906020012060015414156106e5576000905061077c565b816040516020018080602001828103825283818151815260200191508051906020019080838360005b8381101561072957808201518184015260208101905061070e565b50505050905090810190601f1680156107565780820380516001836020036101000a031916815260200191505b509250505060405160208183030381529060405280519060200120600181905550600190505b919050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106107c257805160ff19168380011785556107f0565b828001600101855582156107f0579182015b828111156107ef5782518255916020019190600101906107d4565b5b5090506107fd9190610801565b5090565b61082391905b8082111561081f576000816000905550600101610807565b5090565b9056fea165627a7a72305820a3d868acde9f3c18b8adf21140554c48c0b5c2ccdd5f3858b8d2bdff54670e9a0029";

    public static final String FUNC_SETLEVEL = "setlevel";

    public static final String FUNC_GETLEVEL = "getlevel";

    public static final String FUNC_LOGIN = "login";

    public static final String FUNC_REGISTER = "register";

    public static final String FUNC_GETMONEY = "getMoney";

    public static final String FUNC_SUBMONEY = "subMoney";

    public static final String FUNC_ADDMONEY = "addMoney";

    public static final String FUNC_CHANGEPW = "changePw";

    protected User(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected User(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected User(String contractAddress, Web3j web3j, Credentials credentials,
			ContractGasProvider contractGasProvider) {
		super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
	}
    
    protected User(String contractAddress, Web3j web3j, TransactionManager transactionManager,
			ContractGasProvider contractGasProvider) {
		super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
	}

    
    
    public RemoteCall<TransactionReceipt> setlevel(int theLe) {
        final Function function = new Function(
                FUNC_SETLEVEL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(theLe)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<Uint256> getlevel() {
        final Function function = new Function(
                FUNC_GETLEVEL, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}) );
        return executeRemoteCallSingleValueReturn(function);
    }

  
    
    public RemoteCall<Bool> login(String pw) {
        final Function function = new Function(
                FUNC_LOGIN, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(pw)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {
				}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteCall<TransactionReceipt> register(String theName, String pw) {
        final Function function = new Function(
                FUNC_REGISTER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(theName), 
                new org.web3j.abi.datatypes.Utf8String(pw)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<Uint256> getMoney() {
        final Function function = new Function(
                FUNC_GETMONEY, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}) );
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteCall<TransactionReceipt> subMoney(int value) {
        final Function function = new Function(
                FUNC_SUBMONEY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(value)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> addMoney(int value) {
        final Function function = new Function(
                FUNC_ADDMONEY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(value)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> changePw(String pw) {
        final Function function = new Function(
                FUNC_CHANGEPW, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(pw)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {
				}));
        return executeRemoteCallTransaction(function);
    }

    public static RemoteCall<User> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(User.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<User> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(User.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static User load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new User(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static User load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new User(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }
}
