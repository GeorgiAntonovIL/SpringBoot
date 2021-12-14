package com.Service;

import com.Entity.Address;
import com.Entity.Transaction;
import com.Entity.User;
import com.Entity.UserDTO;
import com.Repository.AddressRepository;
import com.Repository.TransactionRepository;
import com.Repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final Gson gson;
    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final TransactionRepository transactionRepository;

    public UserServiceImpl(Gson gson, ObjectMapper objectMapper, UserRepository userRepository,
                           AddressRepository addressRepository, TransactionRepository transactionRepository) {
        this.gson = gson;
        this.objectMapper = objectMapper;
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public String createUser(UserDTO userDTO) {
        if (checkIfUserExists(userDTO.getFirstName())) {
            return "User already exists.";
        }

        User user = new User(userDTO.getFirstName(), userDTO.getLastName());
        Address jsonAddress = getAddressForUser(user);
        addressRepository.save(jsonAddress);
        user.getAddresses().add(jsonAddress);

        String resultJson = "";

        try {
            resultJson = objectMapper.writeValueAsString(user);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        userRepository.save(user);

        return resultJson;
    }


    @Override
    public String getUser(String firstName, int transactionsCount) {
        if (!checkIfUserExists(firstName)) {
            return "User does not exist!";
        }

        User user = userRepository.getUserByFirstName(firstName).orElse(null);

        List<Transaction> addressList = user.getTransactions().stream().limit(transactionsCount)
                .collect(Collectors.toList());

        user.setTransactions(addressList);

        String resultJson = "";
        try {
            resultJson = objectMapper.writeValueAsString(user);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return resultJson;
    }

    @Override
    public String createAddressForUser(String firstName) {
        if (!checkIfUserExists(firstName)) {
            return "User does not exist!";
        }

        User user = userRepository.getUserByFirstName(firstName).orElse(null);

        Address addressForUser = getAddressForUser(user);
        addressRepository.save(addressForUser);

        user.getAddresses().add(addressForUser);
        userRepository.save(user);

        String resultJson = "";
        try {
            resultJson = objectMapper.writeValueAsString(user);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return resultJson;
    }

    @Override
    public String createTransaction(String firstName) {
        if (!checkIfUserExists(firstName)) {
            return "User does not exist!";
        }

        User user = userRepository.getUserByFirstName(firstName).orElse(null);

        Transaction transaction = getTransactionForUser(user);

        transactionRepository.save(transaction);
        user.getTransactions().add(transaction);
        userRepository.save(user);


        String resultJson = "";
        try {
            resultJson = objectMapper.writeValueAsString(user);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return resultJson;
    }

    @Override
    public String updateUser(String firstName, String newFirstName, String newLastName) {
        if (!checkIfUserExists(firstName)) {
            return "User does not exist!";
        }

        User user = userRepository.getUserByFirstName(firstName).orElse(null);
        user.setFirstName(newFirstName);
        user.setLastName(newLastName);
        userRepository.save(user);

        String resultJson = "";
        try {
            resultJson = objectMapper.writeValueAsString(user);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return resultJson;
    }

    @Override
    public String deleteUser(String firstName) {
        if (!checkIfUserExists(firstName)) {
            return "User does not exist!";
        }

        User user = userRepository.getUserByFirstName(firstName).orElse(null);

        List<Address> addresses = user.getAddresses();
        addressRepository.deleteAll(addresses);

        List<Transaction> transactions = user.getTransactions();
        transactionRepository.deleteAll(transactions);

        userRepository.delete(user);

        return "User was deleted.";
    }


    private Address getAddressForUser(User user) {
        String addressServiceUrl = String.format("http://localhost:8082/address/create?userId=%s", user.getId());

        RestTemplate template = new RestTemplate();
        String result = template.postForObject(addressServiceUrl, null, String.class);

        return gson.fromJson(result, Address.class);
    }

    private Transaction getTransactionForUser(User user) {
        String transactionServiceUrl = String.format("http://localhost:8083/transaction/create?userId=%s", user.getId());

        RestTemplate template = new RestTemplate();
        String result = template.postForObject(transactionServiceUrl, null, String.class);

        return gson.fromJson(result, Transaction.class);
    }


    private boolean checkIfUserExists(String firstName) {
        return userRepository.getUserByFirstName(firstName).isPresent();
    }
}