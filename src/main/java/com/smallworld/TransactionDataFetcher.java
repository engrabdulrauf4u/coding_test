package com.smallworld;

import org.apache.logging.log4j.ThreadContext;

import java.util.*;

import static java.util.Comparator.comparingLong;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

public class TransactionDataFetcher {

    /**
     * Returns the sum of the amounts of all transactions
     * @param transactionsList
     */
    public double getTotalTransactionAmount(List<Transactions> transactionsList) {


        List<Transactions> transactionsByDistinctId = transactionsList.stream().collect(
                collectingAndThen(toCollection(() ->
                        new TreeSet<>(comparingLong(Transactions::getMtn))), ArrayList::new));


        Double totalTransactionAmount = Double.parseDouble("0.00");
        for(Transactions transactions:transactionsByDistinctId){
            totalTransactionAmount=totalTransactionAmount+transactions.getAmount();
        }

        return  totalTransactionAmount;
    }

    /**
     * Returns the sum of the amounts of all transactions sent by the specified client
     */
    public Map<String,Double> getTotalTransactionAmountSentBy(List<Transactions> transactionsList) {

        List<Transactions> transactionsByDistinctId = transactionsList.stream().collect(
                collectingAndThen(toCollection(() ->
                        new TreeSet<>(comparingLong(Transactions::getMtn))), ArrayList::new));

        Map<String,Double> transactionAmountsBySenderName=new HashMap();

        for(Transactions transactions:transactionsByDistinctId){
             if(transactionAmountsBySenderName.get(transactions.getSenderFullName())!=null){ //already present in map
                   Double oldamount = transactionAmountsBySenderName.get(transactions.getSenderFullName());
                   oldamount = oldamount+transactions.getAmount();

                   transactionAmountsBySenderName.put(transactions.getSenderFullName(),oldamount);
            }
            else{
               transactionAmountsBySenderName.put(transactions.getSenderFullName(),transactions.getAmount());

            }
        }

        return transactionAmountsBySenderName;

    }

    /**
     * Returns the highest transaction amount
     */
    public double getMaxTransactionAmount(List<Transactions> transactions) {

        Double maxValue = Double.valueOf(0.00);
        for(Transactions transaction:transactions){

            int retval = Double.compare(transaction.getAmount(), maxValue);

            if(retval>0){
                maxValue = transaction.getAmount();
            }
        }
        return Double.valueOf(maxValue);

    }

    /**
     * Counts the number of unique clients that sent or received a transaction
     */
    public long countUniqueClients(List<Transactions> transactionsList) {

        Set<String> uniqueClients = new HashSet<>();

        List<Transactions> transactionsByDistinctId = transactionsList.stream().collect(
                collectingAndThen(toCollection(() ->
                        new TreeSet<>(comparingLong(Transactions::getMtn))), ArrayList::new));

        uniqueClients = getUniqueClients(transactionsByDistinctId);

        return uniqueClients.size();
    }

    public Set<String> getUniqueClients(List<Transactions> transactionsByDistinctId){
        Set<String> uniqueClients = new HashSet<>();

        for(Transactions transaction:transactionsByDistinctId){
            uniqueClients.add(transaction.getSenderFullName());
            uniqueClients.add(transaction.getBeneficiaryFullName());
        }
        return uniqueClients;
    }
    /**
     * Returns whether a client (sender or beneficiary) has at least one transaction with a compliance
     * issue that has not been solved
     */
    public Set<String> hasOpenComplianceIssues(List<Transactions> transactionsList) {

        Set<String> listhavingComplains = new HashSet<>();

        for(Transactions transaction:transactionsList){
            if(transaction.getIssueId()!=null && transaction.isIssueSolved()==false){
                listhavingComplains.add(transaction.getSenderFullName());
                listhavingComplains.add(transaction.getBeneficiaryFullName());
          }
        }

        return listhavingComplains;
    }

    /**
     * Returns all transactions indexed by beneficiary name
     */
    public Map<String, List<Transactions>> getTransactionsByBeneficiaryName( List<Transactions> transactions) {

        List<Transactions> transactionsByDistictId = transactions.stream().collect(
                collectingAndThen(toCollection(() ->
                        new TreeSet<>(comparingLong(Transactions::getMtn))), ArrayList::new));
        Map<String, List<Transactions>> transactionsByBenificiaryName = new HashMap<>();

        for(Transactions transactions1:transactionsByDistictId){

            if(transactions1.isIssueSolved()) {
                List<Transactions> list = new ArrayList<>();
                if (transactionsByBenificiaryName.get(transactions1.getBeneficiaryFullName()) != null) {
                    list = transactionsByBenificiaryName.get(transactions1.getBeneficiaryFullName());
                    list.add(transactions1);
                } else {
                    list.add(transactions1);
                }
                transactionsByBenificiaryName.put(transactions1.getBeneficiaryFullName(), list);
            }
        }

        return transactionsByBenificiaryName;
    }

    /**
     * Returns the identifiers of all open compliance issues
     */
    public Set<Long> getUnsolvedIssueIds(List<Transactions> transactions) {
        Set<Long> openIssuesIdentifiers=new HashSet<>();

        for (Transactions transaction:transactions
             ) {
                if(transaction.isIssueSolved()==false){
                    openIssuesIdentifiers.add(transaction.getMtn());
                }
        }

        return openIssuesIdentifiers;
    }

    /**
     * Returns a list of all solved issue messages
     */
    public List<String> getAllSolvedIssueMessages(List<Transactions> transactions) {

        List<String> solvedIssueMessages=new ArrayList<>();

        for (Transactions transaction:transactions) {
            if(transaction.isIssueSolved() && transaction.getIssueMessage()!=null){
                solvedIssueMessages.add(transaction.getIssueMessage());
            }
        }

        return solvedIssueMessages;
    }

    /**
     * Returns the 3 transactions with highest amount sorted by amount descending
     */
    public List<Transactions> getTop3TransactionsByAmount(List<Transactions> transactions) {

        Transactions temp = null;    //Temporary variable to store the element

        List<Transactions> transactionsByDistictId = transactions.stream().collect(
                collectingAndThen(toCollection(() ->
                        new TreeSet<>(comparingLong(Transactions::getMtn))), ArrayList::new));

        for (int i = 0; i < transactionsByDistictId.size(); i++)   //Holds each Array element
        {

            for (int j = i+1; j < transactionsByDistictId.size(); j++)    //compares with remaining Array elements
            {
                if(transactionsByDistictId.get(i).getAmount() < transactionsByDistictId.get(j).getAmount()) //Compare and swap
                {
                    temp = transactionsByDistictId.get(i);
                    transactionsByDistictId.set(i,transactionsByDistictId.get(j));
                    transactionsByDistictId.set(j,temp);

                }
            }


        }

        System.out.println();

        //Displaying elements of array after sorting
        System.out.println("Elements of array sorted in descending order: ");
        for (int i = 0; i < transactionsByDistictId.subList(0,3).size(); i++)
        {
            System.out.print(transactionsByDistictId.get(i).getAmount() + " ");
        }
        return transactionsByDistictId.subList(0,3);
    }

    /**
     * Returns the sender with the most total sent amount
     */
    public String getTopSender(Map<String,Double> transactions) {

        String returnS=new String();

        Double maxValue = Double.valueOf(0.00);
        String sender = null;

        for (Map.Entry<String,Double> entry : transactions.entrySet())
        {

            int retval = Double.compare(entry.getValue(), maxValue);

            if(retval>0){
                maxValue = entry.getValue();
                sender = entry.getKey();
            }


        }
        returnS = "The Top Sender is "+sender+" With Maximum Amount "+maxValue;


        return returnS;
    }
}
