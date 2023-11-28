package com.smallworld;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CodingTestApplicationTests {

	TransactionDataFetcher transactionDataFetcher = new TransactionDataFetcher();
	@Test
	void getTotalTransactionAmount() {
		assertTrue(transactionDataFetcher.getTotalTransactionAmount()>Double.MIN_VALUE);
	}
	@Test
	void TestGetTotalTransactionAmountSentByTest() {
		assertFalse(transactionDataFetcher.getTotalTransactionAmountSentBy("Test")>Double.parseDouble("0.0"));
	}

	@Test
	void TestGetTotalTransactionAmountSentByTomShelby() {
		assertTrue(transactionDataFetcher.getTotalTransactionAmountSentBy("Tom Shelby")>Double.parseDouble("0.0"));
	}
	@Test
	void TestGetMaxTransactionAmount() {
		assertTrue(transactionDataFetcher.getMaxTransactionAmount()>Double.parseDouble("0.0"));
	}
	@Test
	void TestCountUniqueClients() {
		assertTrue(transactionDataFetcher.countUniqueClients()>0);
	}
	@Test
	void TestHasOpenComplianceIssues() {
		assertTrue(transactionDataFetcher.hasOpenComplianceIssues("Tom Shelby"));
	}
	@Test
	void TestGetTransactionsByBeneficiaryName(){
		assertNotNull(transactionDataFetcher.getTransactionsByBeneficiaryName());
	}
	@Test
	void TestGetUnsolvedIssueIds(){
		assertNotNull(transactionDataFetcher.getUnsolvedIssueIds());
	}
	@Test
	void TestGetAllSolvedIssueMessages(){
        assertFalse(transactionDataFetcher.getAllSolvedIssueMessages().isEmpty());
	}
	@Test
	void TestGetTop3TransactionsByAmount(){
		assertFalse(transactionDataFetcher.getTop3TransactionsByAmount().isEmpty());
	}
	@Test
	void TestGetTopSender(){
		assertFalse(transactionDataFetcher.getTopSender().isEmpty());
	}


}
