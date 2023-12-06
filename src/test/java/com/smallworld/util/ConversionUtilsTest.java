package com.smallworld.util;

import com.smallworld.model.Transaction;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Test class for ConversionUtils class
 * @author Muhammad Ahsan Khan
 */
public class ConversionUtilsTest {

    @Test
    public void testConvertMapToObject() {
        // Call the method to convert the list of maps to a list of Transaction objects
        List<Transaction> convertedListOfTransactions = ConversionUtils.convertMapToObject(stubListOfMapOfTransactions());

        // Assertions based on the expected behavior of the ConversionUtils class
        assertNotNull(convertedListOfTransactions);
    }

    @Test
    public void testConvertMapToObjectThrowExceptionForIncompleteTransaction() {
        //Create stub list for incomplete transactions
        List<Map<String, Object>> incompleteTransactionList = stubListOfMapOfIncompleteTransaction();

        //Get the exception from the actual method
        UnsupportedOperationException unsupportedOperationException = assertThrows(UnsupportedOperationException.class, () -> ConversionUtils.convertMapToObject(incompleteTransactionList));

        //Check if the exception object is present
        assertNotNull(unsupportedOperationException);

        //Verify the exception message
        assertEquals("Incomplete Transaction!",unsupportedOperationException.getMessage());

    }

    @Test
    public void testConvertMapToObjectThrowExceptionForEmptyTransaction() {
        //Create stub list for empty transactions
        List<Map<String, Object>> emptyTransactionList = stubListOfMapOfEmptyTransaction();

        //Get the exception from the actual method
        UnsupportedOperationException unsupportedOperationException = assertThrows(UnsupportedOperationException.class, () -> ConversionUtils.convertMapToObject(emptyTransactionList));

        //Check if the exception object is present
        assertNotNull(unsupportedOperationException);

        //Verify the exception message
        assertEquals("Transaction is Empty!",unsupportedOperationException.getMessage());

    }

    /**
     * Return stub list of map of transactions for happy scenario
     * @return return list of map of transactions
     */
    private List<Map<String, Object>> stubListOfMapOfTransactions(){
        List<Map<String, Object>> listOfMap = new ArrayList<>();
        Map<String, Object> transaction1 = new HashMap<>();
        transaction1.put("mtn",1L);
        transaction1.put("amount",1000.0);
        transaction1.put("senderFullName","Test sender1");
        transaction1.put("senderAge",1);
        transaction1.put("beneficiaryFullName","Test beneficiary1");
        transaction1.put("beneficiaryAge",1);
        transaction1.put("issueId", "1");
        transaction1.put("issueSolved", true);
        transaction1.put("issueMessage", "Test solved issue message");

        Map<String, Object> transaction2 = new HashMap<>();
        transaction2.put("mtn",2L);
        transaction2.put("amount",100.0);
        transaction2.put("senderFullName","Test sender2");
        transaction2.put("senderAge",2);
        transaction2.put("beneficiaryFullName","Test beneficiary2");
        transaction2.put("beneficiaryAge",2);
        transaction2.put("issueId", "2");
        transaction2.put("issueSolved", false);
        transaction2.put("issueMessage", "Test unsolved issue message");

        listOfMap.add(transaction1);
        listOfMap.add(transaction2);
        return listOfMap;
    }

    /**
     * Returns list of map of incomplete transactions for exception test case
     * @return return list of map of incomplete transactions
     */
    private List<Map<String, Object>> stubListOfMapOfIncompleteTransaction(){
        List<Map<String, Object>> listOfMap = new ArrayList<>();
        Map<String, Object> transaction = new HashMap<>();
        transaction.put("mtn",1L);
        listOfMap.add(transaction);
        return listOfMap;
    }

    /**
     * Return list of map of empty transactions for exception test case
     * @return return list of map of empty transactions
     */
    private List<Map<String, Object>> stubListOfMapOfEmptyTransaction(){
        List<Map<String, Object>> listOfMap = new ArrayList<>();
        Map<String, Object> transaction = new HashMap<>();
        listOfMap.add(transaction);
        return listOfMap;
    }


}