package com.smallworld;

import com.smallworld.model.Transaction;
import com.smallworld.service.TransactionDataFetcher;

import java.io.IOException;
import java.util.*;

public class SmallWorldApplication {
    public static void main(String[] args) {
        TransactionDataFetcher transactionDataFetcher = new TransactionDataFetcher();

        try {
            String filePath = "../coding_test/transactions.json";
            // Assuming setTransactionsFromJSONFile method is implemented to load data from the JSON file
            transactionDataFetcher.setTransactionsFromJSON(filePath);

            // Test each method by calling them and printing results
            double totalAmount = transactionDataFetcher.getTotalTransactionAmount();
            System.out.println("Total Transaction Amount: " + totalAmount);

            String senderFullName = "Tom Shelby";
            totalAmount = transactionDataFetcher.getTotalTransactionAmountSentBy("Tom Shelby");
            System.out.println("Total Transaction Amount For "+ senderFullName + " : " + totalAmount);

            double maxAmount = transactionDataFetcher.getMaxTransactionAmount();
            System.out.println("Max Transaction Amount is : " + maxAmount);

            long uniqueClientsCount = transactionDataFetcher.countUniqueClients();
            System.out.println("Unique clients count that sent or receive transaction is : "+uniqueClientsCount);

            String clientName = "Aunt Polly";
            if(transactionDataFetcher.hasOpenComplianceIssues(clientName)){
                System.out.println("Yes! "+clientName+" has at least 1 unsolved compliance issue!");
            }else{
                System.out.println("No! "+clientName+" has not any unsolved compliance issue!");
            }

           Map<String, List<Transaction>> listOfTransactionsByBeneficiaryName = transactionDataFetcher.getTransactionsByBeneficiaryName();
           Collection<List<Transaction>> listOfTransactions = listOfTransactionsByBeneficiaryName.values();
            String beneficiaryName = "";
           for(List<Transaction> list : listOfTransactions){
               for(Transaction transaction : list){
                   if(!beneficiaryName.equals(transaction.getBeneficiaryInformation().getBeneficiaryFullName())){
                       System.out.println(transaction.getBeneficiaryInformation().getBeneficiaryFullName());
                       System.out.println(transaction);
                   }else{
                       System.out.println(transaction);
                   }
                    beneficiaryName = transaction.getBeneficiaryInformation().getBeneficiaryFullName();
               }
           }

            System.out.println("Unsolved Issue Ids : "+transactionDataFetcher.getUnsolvedIssueIds());

            List<String> listOfSolvedMessages = transactionDataFetcher.getAllSolvedIssueMessages();
            for(String solvedMessage : listOfSolvedMessages){
                System.out.println("Solved Issue Message # 1 : "+solvedMessage);
            }

            List<Transaction> top3Transactions = new ArrayList<>(transactionDataFetcher.getTop3TransactionsByAmount());
            for (Transaction transaction : top3Transactions) {
                System.out.println(transaction);
            }

            System.out.println(transactionDataFetcher.getTopSender().toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
