package com.smallworld;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

@SpringBootApplication
public class CodingTestApplication {



	public static void main(String[] args) throws IOException, ParseException {

		SpringApplication.run(CodingTestApplication.class, args);

	   List<Transactions>	transactionsList = getTransactionListFromJsonFile();

		TransactionDataFetcher dataFetcher=new TransactionDataFetcher();


		/*******************************************/
		Double totalTranAmount = dataFetcher.getTotalTransactionAmount(transactionsList);
		System.out.println("/*******************************************/");
		System.out.println("Total Transaction amount "+totalTranAmount);

		/*******************************************/
		Map<String,Double>  amountsBySender = dataFetcher.getTotalTransactionAmountSentBy(transactionsList);
		System.out.println("/*******************************************/");
		for (Map.Entry<String,Double> entry : amountsBySender.entrySet())
			System.out.println("Total Transaction Sent By  " + entry.getKey() +
					", Value = " + entry.getValue());

		/*******************************************/

		System.out.println("/*******************************************/");
		System.out.println("Maximum Transaction amount "+dataFetcher.getMaxTransactionAmount(transactionsList));

		/**
		 * Counts the number of unique clients that sent or received a transaction
		 */

		System.out.println("/*******************************************/");

		System.out.println(" Count of Maximum Number of unique Clients "+dataFetcher.countUniqueClients(transactionsList));

		/**
		 * Returns whether a client (sender or beneficiary) has at least one transaction with a compliance
		 * issue that has not been solved
		 */

		Set<String> listHavingComplaints = dataFetcher.hasOpenComplianceIssues(transactionsList);
		System.out.println("/*******************************************/");
		System.out.println("List of Client Having Complaints unresolved ");
		for (String client:listHavingComplaints) {

			System.out.println(client);
		}

		/*******************************************/

		Map<String, List<Transactions>> listByBenificiary = dataFetcher.getTransactionsByBeneficiaryName(transactionsList);

		System.out.println("/*******************************************/");
		for (Map.Entry<String,List<Transactions>> entry : listByBenificiary.entrySet()) {
			System.out.println("Transaction Recieved by benificiary " + entry.getKey());
			for (Transactions transaction:entry.getValue()) {
				System.out.println("************** " + transaction.getAmount());
			}
		}

		/****************************************/
		System.out.println("/*******************************************/");

		List<Transactions> Top3TransactionsByAmount = dataFetcher.getTop3TransactionsByAmount(transactionsList);

		/****************************************/
		System.out.println("/*******************************************/");
		String topSender = dataFetcher.getTopSender(dataFetcher.getTotalTransactionAmountSentBy(transactionsList));
		System.out.println("");
		System.out.println("************** " + topSender);

	}

	private static List<Transactions> getTransactionListFromJsonFile() throws IOException, ParseException {

		List<Transactions> transactionsList=new ArrayList<Transactions>();

		JSONParser jsonParser=new JSONParser();
		FileReader reader=new FileReader(".\\transactions.json");


		Object object = jsonParser.parse(reader);

		JSONArray jsonObjectArray = (JSONArray) object;

		Iterator<String> unitsIterator = jsonObjectArray.iterator();
		while(unitsIterator.hasNext()){
			Object uJson = unitsIterator.next();
			JSONObject uj = (JSONObject) uJson;

			Transactions transaction=new Transactions();
			transaction.setMtn((Long) uj.get("mtn"));
			transaction.setAmount((Double) uj.get("amount"));
			transaction.setSenderFullName((String) uj.get("senderFullName"));
			transaction.setSenderAge((Long) uj.get("senderAge"));
			transaction.setBeneficiaryAge((Long) uj.get("beneficiaryAge"));
			transaction.setBeneficiaryFullName((String) uj.get("beneficiaryFullName"));
			transaction.setIssueId((Long) uj.get("issueId"));
			transaction.setIssueSolved((Boolean) uj.get("issueSolved"));
			transaction.setIssueMessage((String) uj.get("issueMessage"));

			transactionsList.add(transaction);

		}
		return transactionsList;
	}

}
