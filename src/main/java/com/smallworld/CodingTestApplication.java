package com.smallworld;

import com.smallworld.data.Transaction;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Map;
import java.util.Set;

@SpringBootApplication
public class CodingTestApplication {

	public static void main(String[] args) {

		SpringApplication.run(CodingTestApplication.class, args);

		TransactionDataFetcher transactionDataFetcher = new TransactionDataFetcher();
		// Example usage of the methods
		System.out.println("Total Transaction Amount: " + transactionDataFetcher.getTotalTransactionAmount());
		System.out.println("Total Transaction Amount Send by Grace Burgess :" + transactionDataFetcher.getTotalTransactionAmountSentBy("Grace Burgess"));
		System.out.println("Maximum Transaction Amount: " + transactionDataFetcher.getMaxTransactionAmount());
		System.out.println("Count Unique Clients: " +transactionDataFetcher.countUniqueClients());
		System.out.println("Has Unsolved Compliance Issue for Client: " + transactionDataFetcher.hasOpenComplianceIssues("Aberama Gold"));
		System.out.println("-----------------Transactions By Beneficiary Name: ----------------");
		Map<String, List<Transaction>> mapByBenificiary = transactionDataFetcher.getTransactionsByBeneficiaryName();
		Set<String> BenificiarySet = mapByBenificiary.keySet();
		for (String benificiary:BenificiarySet ) {
			System.out.println(benificiary);
			List<Transaction> transactionList = mapByBenificiary.get(benificiary);
			for (Transaction transaction:transactionList ) {
				System.out.println(transaction.toString());
			}
		}
		System.out.println("----------------------UnResolved Issue Ids: --------------------" + transactionDataFetcher.getUnsolvedIssueIds());
		System.out.println("Solved Issue Messages: " + transactionDataFetcher.getAllSolvedIssueMessages());
		System.out.println("Top 3 Transactions by Amount: " + transactionDataFetcher.getTop3TransactionsByAmount());
		System.out.println("Top Sender: " +transactionDataFetcher.getTopSender());

	}

}
