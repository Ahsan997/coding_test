package com.smallworld.service;

import com.smallworld.model.BeneficiaryInformation;
import com.smallworld.model.IssueInformation;
import com.smallworld.model.SenderInformation;
import com.smallworld.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for TransactionDataFetcher class
 * @author Muhammad Ahsan Khan
 */
public class TransactionDataFetcherTests {

    private TransactionDataFetcher dataFetcher;

    @BeforeEach
    public void setUp() {
        dataFetcher = new TransactionDataFetcher();
    }

    @Test
    public void testSetTransactionsFromJSON() throws IOException {
        String filePath = "../coding_test/transactions.json";

        // Actual method call
        dataFetcher.setTransactionsFromJSON(filePath);

        // Checking the list is empty or not
        assertNotNull(dataFetcher.transactions);
    }

    @Test
    public void testGetTotalTransactionAmount() {
        // Set the mock list
        dataFetcher.transactions = stubListOfTransactions();

        // Calculate the expected total amount
        double expectedTotalAmount = dataFetcher.transactions.get(0).getAmount()
                + dataFetcher.transactions.get(1).getAmount()
                +dataFetcher.transactions.get(2).getAmount();

        // Actual method call
        double actualTotalAmount = dataFetcher.getTotalTransactionAmount();

        // Matching expected with the actual result
        assertEquals(expectedTotalAmount, actualTotalAmount);
    }
    @Test
    public void testGetTotalTransactionAmountSentBy() {
        // Set the mock list
        dataFetcher.transactions = stubListOfTransactions();

        // Calculate the expected total amount for senderFullName "Test sender1"
        double expectedTotalAmountForTestSender1 = dataFetcher.transactions.get(0).getAmount() +
                dataFetcher.transactions.get(1).getAmount();
        // Calculate the expected total amount for senderFullName "Test sender2"
        double expectedTotalAmountForTestSender2 = dataFetcher.transactions.get(2).getAmount();

        // Actual method call
        double actualTotalAmountForTestSender1 = dataFetcher.getTotalTransactionAmountSentBy("Test sender1");
        double actualTotalAmountForTestSender2 = dataFetcher.getTotalTransactionAmountSentBy("Test sender2");

        // Matching expected with the actual result
        assertEquals(expectedTotalAmountForTestSender1, actualTotalAmountForTestSender1);

        // Matching expected with the actual result
        assertEquals(expectedTotalAmountForTestSender2, actualTotalAmountForTestSender2);
}

    @Test
    public void testGetMaxTransactionAmount() {
        // Set the mock list
        dataFetcher.transactions = stubListOfTransactions();

        // Calculate the expected maximum amount among the transactions
        double expectedMaxAmount = Math.max(dataFetcher.transactions.get(0).getAmount(),
                Math.max(dataFetcher.transactions.get(1).getAmount(), dataFetcher.transactions.get(2).getAmount()));

        // Actual method call
        double actualMaxAmount = dataFetcher.getMaxTransactionAmount();

        // Matching expected with the actual result
        assertEquals(expectedMaxAmount, actualMaxAmount);
    }

    @Test
    public void testCountUniqueClients() {
        // Set the mock list
        dataFetcher.transactions = stubListOfTransactions();

        // Calculate the expected count of unique clients
        long expectedUniqueClientsCount = 2; // Set the expected count based on your mock transactions

        // Actual method call
        long actualUniqueClientsCount = dataFetcher.countUniqueClients();

        // Matching expected with the actual result
        assertEquals(expectedUniqueClientsCount, actualUniqueClientsCount);
    }

    @Test
    public void testHasOpenComplianceIssues() {
        // Set the mock list
        dataFetcher.transactions = stubListOfTransactions();

        // Assuming the clientFullName has open compliance issues in the mock data
        String clientWithOpenComplianceIssues = "Test sender2";
        String clientWithoutOpenComplianceIssues = "Test sender1";

        // Actual method calls
        boolean hasOpenComplianceIssuesForClientWithIssues = dataFetcher.hasOpenComplianceIssues(clientWithOpenComplianceIssues);
        boolean hasOpenComplianceIssuesForClientWithoutIssues = dataFetcher.hasOpenComplianceIssues(clientWithoutOpenComplianceIssues);

        // Matching expected with the actual result
        assertTrue(hasOpenComplianceIssuesForClientWithIssues);

        // Matching expected with the actual result
        assertFalse(hasOpenComplianceIssuesForClientWithoutIssues);
    }

    @Test
    public void testGetTransactionsByBeneficiaryName() {
        // Set the mock list
        dataFetcher.transactions = stubListOfTransactions();

        // Actual method call
        Map<String, List<Transaction>> transactionsByBeneficiaryName = dataFetcher.getTransactionsByBeneficiaryName();

        // Extracting keys from the map to check either data is returned based on beneficiary names or not
        Set<String> beneficiaryNames = transactionsByBeneficiaryName.keySet();

        // Checking whether the map is empty or not
        assertNotNull(transactionsByBeneficiaryName);

        // Checking that the expected key is present in set (beneficiary Names)
        assertTrue(beneficiaryNames.contains("Test beneficiary1"));
    }

    @Test
    public void testGetUnsolvedIssueIds() {
        // Set the mock list
        dataFetcher.transactions = stubListOfTransactions();

        // Actual method call
        Set<Integer> unsolvedIssueIds = dataFetcher.getUnsolvedIssueIds();

        // Checking whether the set is empty or not
        assertNotNull(unsolvedIssueIds);

        // Checking that the expected id is present in set unsolvedIssueIds
        assertTrue(unsolvedIssueIds.contains(Integer.parseInt("2")));
    }


    @Test
    public void testGetAllSolvedIssueMessages() {
        // Set the mock list
        dataFetcher.transactions = stubListOfTransactions();

        // Actual method call
        List<String> solvedIssueMessages = dataFetcher.getAllSolvedIssueMessages();

        // Checking whether the list is empty or not
        assertNotNull(solvedIssueMessages);

        // Checking that the expected message is present in list solvedIssueMessages
        assertTrue(solvedIssueMessages.contains(solvedIssueMessages.get(0)));
    }

    @Test
    public void testGetTop3TransactionsByAmount() {
        // Set the mock list
        dataFetcher.transactions = stubListOfTransactions();

        // Actual method call
        List<Transaction> top3Transactions = dataFetcher.getTop3TransactionsByAmount();

        // Checking whether the list is empty or not
        assertNotNull(top3Transactions);

        // Check if the size is 3
        assertEquals(3, top3Transactions.size());

        // Checking the first transaction's amount is as expected
        assertEquals(1000, top3Transactions.get(0).getAmount());
    }

    @Test
    public void testGetTopSender() {
        // Set the mock list
        dataFetcher.transactions = stubListOfTransactions();

        //Actual method call
        Optional<Map<String, Double>> topSender = dataFetcher.getTopSender();

        // Check if the Optional contains a value
        assertTrue(topSender.isPresent());

        // Check if the top sender name is as expected
        assertTrue(topSender.get().containsKey("Test sender1"));

        // Check if the sent amount is as expected
        assertTrue(topSender.get().containsValue(2000.0));
    }

    /**
     * Returns stub list of transactions for unit test cases
     * @return stub list of transactions
     */
    private List<Transaction> stubListOfTransactions(){
        List<Transaction> listOfTransactions = new ArrayList<>();

        SenderInformation senderInformation = new SenderInformation();
        senderInformation.setSenderAge(2);
        senderInformation.setSenderFullName("Test sender2");

        BeneficiaryInformation beneficiaryInformation = new BeneficiaryInformation();
        beneficiaryInformation.setBeneficiaryAge(2);
        beneficiaryInformation.setBeneficiaryFullName("Test beneficiary2");

        IssueInformation issueInformation = new IssueInformation();
        issueInformation.setIssueId("2");
        issueInformation.setIssueMessage("Test unsolved issue message");
        issueInformation.setIssueSolved(false);

        Transaction transaction = new Transaction();
        transaction.setMtn(2);
        transaction.setAmount(100.0);
        transaction.setSenderInformation(senderInformation);
        transaction.setBeneficiaryInformation(beneficiaryInformation);
        transaction.setIssueInformation(issueInformation);

        listOfTransactions.add(stubTransaction());
        listOfTransactions.add(stubTransaction());
        listOfTransactions.add(transaction);

        return listOfTransactions;
    }

    /**
     * Returns stub transaction for unit test cases
     * @return stub transaction
     */
    private Transaction stubTransaction() {
        SenderInformation senderInformation = new SenderInformation();
        senderInformation.setSenderAge(1);
        senderInformation.setSenderFullName("Test sender1");

        BeneficiaryInformation beneficiaryInformation = new BeneficiaryInformation();
        beneficiaryInformation.setBeneficiaryAge(1);
        beneficiaryInformation.setBeneficiaryFullName("Test beneficiary1");

        IssueInformation issueInformation = new IssueInformation();
        issueInformation.setIssueId("1");
        issueInformation.setIssueMessage("Test solved issue message");
        issueInformation.setIssueSolved(true);

        Transaction transaction = new Transaction();
        transaction.setMtn(1);
        transaction.setAmount(1000.0);
        transaction.setSenderInformation(senderInformation);
        transaction.setBeneficiaryInformation(beneficiaryInformation);
        transaction.setIssueInformation(issueInformation);

        return transaction;
    }
}
