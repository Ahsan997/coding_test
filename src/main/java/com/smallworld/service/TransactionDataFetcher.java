package com.smallworld.service;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smallworld.model.Transaction;
import com.smallworld.util.ConversionUtils;

public class TransactionDataFetcher {
    /**
     * This list will hold the parsed JSON data in form of classes
     */
    public List<Transaction> transactions;

    /**
     * Method to read JSON data from a file and set the 'transactions' list.
     *
     * @param filePath path of transactions.json file
     * @throws IOException
     */
    public void setTransactionsFromJSON(String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        transactions = ConversionUtils.convertMapToObject(objectMapper.readValue(new File(filePath), new TypeReference<>(){}));
    }

    /**
     * Returns the sum of the amounts of all transactions.
     *
     * @return total transaction amount of successful transactions.
     */
    public double getTotalTransactionAmount() {
        //All required exceptions will be thrown from conversionUtils,
        // if any field is unavailable or if transactions are empty.
        double totalAmount = 0.0;
        for(Transaction transaction : transactions) {
            //this check will add only transactions which have not any issue that is not resolved yet
            //in order to get total amount of all the transaction inclusive of the failed ones then remove this check
//            if(transaction.getIssueInformation().isIssueSolved()){
                        totalAmount += transaction.getAmount();
//            }
        }
        return totalAmount;
    }

    /**
     * Returns the sum of the amounts of all transactions sent by the specified client.
     *
     * @param senderFullName transaction sender's full name.
     * @return total transaction amount for successful transactions.
     */
    public double getTotalTransactionAmountSentBy(String senderFullName) {
        double totalAmount = 0.0;
        for(Transaction transaction : transactions){
                if(transaction.getSenderInformation().getSenderFullName().equals(senderFullName)){
                            totalAmount += transaction.getAmount();
                    }
        }
        //Rounding off the result to two decimal places before returning
        return Math.round(totalAmount * 100.0) / 100.0;
    }

    /**
     * Returns the highest transaction amount.
     *
     * @return max transaction amount.
     */
    public double getMaxTransactionAmount() {
        double totalAmount = 0.0;

        for(Transaction transaction : transactions){
            if( transaction.getAmount() > totalAmount){
                totalAmount = transaction.getAmount();
            }
        }
        return totalAmount;
    }

    /**
     * Counts the number of unique clients that sent or received a transaction.
     *
     * @return count of unique clients.
     */
    public long countUniqueClients() {
        List<String> clientsList = new ArrayList<>();
        Set<String> uniqueClients = new HashSet<>();

        //Extracting all the senders and receivers full name
        for(Transaction transaction : transactions){
            clientsList.add(transaction.getSenderInformation().getSenderFullName());
            clientsList.add(transaction.getBeneficiaryInformation().getBeneficiaryFullName());
        }
        boolean isUnique = false;
        //Making a Set of all the unique clients that are involved in only one transaction
        for(int i = 0; i < clientsList.size(); i++){
            String client = clientsList.get(i);
            for(int j = 0; j < clientsList.size(); j++){
                if(i != j){
                    // If client is present on another index of client list then don't add it in a set
                    if(!client.equals(clientsList.get(j))){
                        isUnique = true;
                    }else{
                        isUnique = false;
                        break;
                    }
                }
            }
            //If client is unique then it will be added in unique client set
            if(isUnique){
                uniqueClients.add(client);
            }
        }
        return uniqueClients.size();
    }

    /**
     * Returns whether a client (sender or beneficiary) has at least one transaction with a compliance
     * issue that has not been solved.
     *
     * @param clientFullName client (sender's or receiver's) full name.
     * @return true if user has any unsolved compliance issue otherwise false.
     */
    public boolean hasOpenComplianceIssues(String clientFullName) {
        String senderFullName;
        String beneficiaryFullName;
        for(Transaction transaction : transactions){
            senderFullName = transaction.getSenderInformation().getSenderFullName();//setting values
            beneficiaryFullName = transaction.getBeneficiaryInformation().getBeneficiaryFullName();//setting values
            if(senderFullName.equals(clientFullName) || beneficiaryFullName.equals(clientFullName)){
                //If issue id is not null and issue is not resolved then return true
                if((transaction.getIssueInformation().getIssueId() != null) && !transaction.getIssueInformation().isIssueSolved()){
                       return true;
                   }
                }
        }
        return false;
    }

    /**
     * Returns all transactions indexed by beneficiary name.
     *
     * @return Map of transactions by beneficiary name with its relevant transactions.
     */
    public Map<String, List<Transaction>> getTransactionsByBeneficiaryName() {
        Map<String, List<Transaction>> transactionByBeneficiaryName = new HashMap<>();
        String benificiaryName;
        List<Transaction> transactionsByName = new ArrayList<>();

        //Extracting beneficiary names to filter
        for(Transaction transaction : transactions){
            benificiaryName = transaction.getBeneficiaryInformation().getBeneficiaryFullName();

            //Filtering all transactions by beneficiary name
            for(Transaction trans : transactions){
                if(trans.getBeneficiaryInformation().getBeneficiaryFullName().equals(benificiaryName)){
                    transactionsByName.add(trans);
                }
            }
            transactionByBeneficiaryName.put(benificiaryName,transactionsByName);
            transactionsByName = new ArrayList<>();
        }
        //Returning map of beneficiary name along with all of its relevant transactions
        return transactionByBeneficiaryName;
    }

    /**
     * Returns the identifiers of all open compliance issues
     */
    public Set<Integer> getUnsolvedIssueIds() {
        Set<Integer> unsolvedIssueIds = new HashSet<>();
        for(Transaction transaction : transactions){

                //check if the issue is unresolved or not
                if(!transaction.getIssueInformation().isIssueSolved()){
                    unsolvedIssueIds.add(Integer.parseInt(transaction.getIssueInformation().getIssueId()));
                }
        }
        return unsolvedIssueIds;
    }

    /**
     * Returns a list of all solved issue messages
     *
     * @return Returns list of all solved issue messages.
     */
    public List<String> getAllSolvedIssueMessages() {
        List<String> solvedIssueIds = new ArrayList<>();
        for(Transaction transaction : transactions){

            //check if the issue is resolved and check if the issue message is not null
            if(transaction.getIssueInformation().isIssueSolved() && transaction.getIssueInformation().getIssueMessage() != null){
                solvedIssueIds.add(transaction.getIssueInformation().getIssueMessage());
            }
        }
        return solvedIssueIds;
    }

    /**
     * Returns the 3 transactions with highest amount sorted by amount descending
     *
     * @return Returns list of top 3 transactions having highest total transaction amount.
     */
    public List<Transaction> getTop3TransactionsByAmount() {
        List<Transaction> tempListOftransactions = new ArrayList<>(transactions);
        List<Transaction> top3Transactions = new ArrayList<>();
        Transaction tempObj;
        double firstMatch;
        double secondMatch;

       //Using custom logic to sort the list in descending order
       for (int i = 0; i < tempListOftransactions.size(); i++){
            for(int j = 0; j < tempListOftransactions.size(); j++){
                //avoid checking element with itself
                if(i != j) {
                    firstMatch = tempListOftransactions.get(i).getAmount();
                    secondMatch = tempListOftransactions.get(j).getAmount();

                    //sorting the list in descending order
                    if(firstMatch > secondMatch){
                        tempObj = tempListOftransactions.get(i);
                        tempListOftransactions.set(i,tempListOftransactions.get(j));
                        tempListOftransactions.set(j,tempObj);
                    }
                }
            }
        }

        //getting only the top 3 transactions including the failed ones
        for (int i = 0; i < 3; i++) {

            //if the transaction to be added is exactly the same as already available in the list then it'll not be added
            if(!top3Transactions.contains(tempListOftransactions.get(i))) {
                top3Transactions.add(tempListOftransactions.get(i));
            }
        }
        return top3Transactions;
    }

    /**
     * Returns the sender with the most total sent amount
     *
     * @return Returns Optional Object of Top highest sender among all the transactions.
     */
    public Optional<Map<String, Double>> getTopSender() {
        Set<String> senderNames;
        String senderName = "";
        double highest = 0.0;

        //getting unique sender names in set using lambda expression
        senderNames = transactions.stream()
                .map(t -> t.getSenderInformation().getSenderFullName())
                .collect(Collectors.toSet());

        //finding the highest total amount of sender by calling getTotalTransactionAmountSentBy(sender) method
        for(String sender : senderNames) {
                double amount = getTotalTransactionAmountSentBy(sender);
                if(amount > highest){
                    highest = amount;
                    senderName = sender;
                }
        }
        return Optional.of(Map.of(senderName, highest));
    }

}
