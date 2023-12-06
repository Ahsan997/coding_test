package com.smallworld.util;

import com.smallworld.model.BeneficiaryInformation;
import com.smallworld.model.IssueInformation;
import com.smallworld.model.SenderInformation;
import com.smallworld.model.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This class has methods which converts map into object and check the validity of the data.
 * @author Muhammad Ahsan Khan
 */
public class ConversionUtils {

    /**
     * This method converts every map of transaction to its relevant object i.e., 'Transaction'
     * @param listOfMap list of map of transactions
     * @return returns list of type 'Transactions'
     */
    public static List<Transaction> convertMapToObject(List<Map<String, Object>> listOfMap){
        List<Transaction> convertedList = new ArrayList<>();
        if(isNotEmptyTransactionsList(listOfMap)){
            for (Map<String, Object> transactionElement : listOfMap) {

                SenderInformation senderInformation = new SenderInformation();
                BeneficiaryInformation beneficiaryInformation = new BeneficiaryInformation();
                IssueInformation issueInformation = new IssueInformation();
                Transaction transaction = new Transaction();

                transaction.setMtn(Long.parseLong(transactionElement.get("mtn").toString()));
                transaction.setAmount(Double.parseDouble(transactionElement.get("amount").toString()));

                senderInformation.setSenderFullName(transactionElement.get("senderFullName").toString());
                senderInformation.setSenderAge(Integer.parseInt(transactionElement.get("senderAge").toString()));

                beneficiaryInformation.setBeneficiaryFullName(transactionElement.get("beneficiaryFullName").toString());
                beneficiaryInformation.setBeneficiaryAge(Integer.parseInt(transactionElement.get("beneficiaryAge").toString()));

                if(transactionElement.get("issueId") != null){
                    issueInformation.setIssueId(transactionElement.get("issueId").toString());
                }
                issueInformation.setIssueSolved(Boolean.parseBoolean(transactionElement.get("issueSolved").toString()));
                if(transactionElement.get("issueMessage") != null){
                    issueInformation.setIssueMessage(transactionElement.get("issueMessage").toString());
                }

                transaction.setSenderInformation(senderInformation);
                transaction.setBeneficiaryInformation(beneficiaryInformation);
                transaction.setIssueInformation(issueInformation);

                convertedList.add(transaction);
            }
        }
        return convertedList;
    }

    /**
     * Returns true if transactions list is not empty and if it is empty or any transaction in the list
     * is incomplete then the exception with relevant message is will be thrown.
     * @param transactions list of map of transactions.
     * @return true if list has valid transactions.
     */
    private static boolean isNotEmptyTransactionsList(List<Map<String, Object>> transactions) {
        for(Map<String, Object> transaction : transactions){
            if(transaction.isEmpty()) {
                throw new UnsupportedOperationException("Transaction is Empty!");
            }else if(transaction.containsKey("mtn") && transaction.containsKey("amount") && transaction.containsKey("senderFullName")
                    && transaction.containsKey("senderAge") && transaction.containsKey("beneficiaryFullName") && transaction.containsKey("beneficiaryAge")
                    && transaction.containsKey("issueSolved")) {
                return true;
            }else{
                throw new UnsupportedOperationException("Incomplete Transaction!");
            }
        }
        return false;
    }
}
