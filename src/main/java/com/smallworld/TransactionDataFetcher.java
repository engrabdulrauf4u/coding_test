package com.smallworld;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.smallworld.data.Transaction;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TransactionDataFetcher {



    private List<Transaction> transactions;

    public TransactionDataFetcher(){
        // Load transactions from JSON file
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            transactions = Arrays.asList(objectMapper.readValue(new File("transactions.json"), Transaction[].class));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * get Unique Transactions by Mtn and
     * Returns the sum of the amounts of all transactions
     */
    public double getTotalTransactionAmount() {
        if (transactions == null || transactions.isEmpty()) {
            return 0.0;
        }

        return transactions.stream()
                .collect(Collectors.toMap(Transaction::getMtn, transaction -> transaction, (t1, t2) -> t1)).values()
                .stream().mapToDouble(Transaction::getAmount).sum();
    }

    /**
     * Returns the sum of the amounts of all transactions sent by the specified client with Unique MTN
     */
    public double getTotalTransactionAmountSentBy(String senderFullName) {
        if (transactions == null || transactions.isEmpty()) {
            return 0.0;
        }

        return transactions.stream()
                .filter(t -> t.getSenderFullName().equals(senderFullName))
                .collect(Collectors.toMap(Transaction::getMtn, transaction -> transaction, (t1, t2) -> t1)).values().stream()
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    /**
     * Returns the highest transaction amount
     */
    public double getMaxTransactionAmount() {
        if (transactions == null || transactions.isEmpty()) {
            return 0.0;
        }
        return transactions.stream().mapToDouble(Transaction::getAmount).max().orElse(0.0);
    }

    /**
     * Counts the number of unique clients that sent or received a transaction
     */
    public long countUniqueClients() {

        if (transactions == null || transactions.isEmpty()) {
            return 0;
        }

        Set<String> uniqueClients = transactions.stream()
                .flatMap(t -> Stream.of(t.getSenderFullName(), t.getBeneficiaryFullName()))
                .collect(Collectors.toSet());
        return uniqueClients.size();
    }

    /**
     * Returns whether a client (sender or beneficiary) has at least one transaction with a compliance
     * issue that has not been solved
     */
    public boolean hasOpenComplianceIssues(String clientFullName) {
        if (transactions == null || transactions.isEmpty()) {
            return false;
        }
        return transactions.stream()
                .anyMatch(t -> (t.getSenderFullName().equals(clientFullName) || t.getBeneficiaryFullName().equals(clientFullName))
                        && !t.isIssueSolved());
    }

    /**
     * Returns all transactions indexed by beneficiary name
     */
    public Map<String, List<Transaction>> getTransactionsByBeneficiaryName() {
        if (transactions == null || transactions.isEmpty()) {
            return Collections.emptyMap();
        }
        return transactions.stream().
                collect(Collectors.toMap(Transaction::getMtn, transaction -> transaction, (t1, t2) -> t1)).values().stream().
                collect(Collectors.groupingBy(Transaction::getBeneficiaryFullName));
    }

    /**
     * Returns the identifiers of all open compliance issues
     */
    public Set<Integer> getUnsolvedIssueIds() {
        if (transactions == null || transactions.isEmpty()) {
            return Collections.emptySet();
        }
        return transactions.stream()
                .filter(t -> t.getIssueId() != null && !t.isIssueSolved())
                .map(Transaction::getIssueId)
                .collect(Collectors.toSet());
    }

    /**
     * Returns a list of all solved issue messages
     */
    public List<String> getAllSolvedIssueMessages() {
        if (transactions == null || transactions.isEmpty()) {
            return Collections.emptyList();
        }
        return transactions.stream()
                .filter(Transaction::isIssueSolved)
                .map(Transaction::getIssueMessage)
                .collect(Collectors.toList());
    }

    /**
     * Returns the 3 transactions with the highest amount sorted by amount descending
     */
    public List<Transaction> getTop3TransactionsByAmount() {
        if (transactions == null || transactions.isEmpty()) {
            return Collections.emptyList();
        }
        return transactions.stream()
                .collect(Collectors.toMap(Transaction::getMtn, transaction -> transaction, (t1, t2) -> t1)).values().stream()
                .sorted(Comparator.comparingDouble(Transaction::getAmount).reversed())
                .limit(3)
                .collect(Collectors.toList());
    }

    /**
     * Returns the senderFullName of the sender with the most total sent amount
     */
    public Optional<String> getTopSender() {
        if (transactions == null || transactions.isEmpty()) {
            return Optional.empty();
        }
        Map<String, Double> sentAmountBySender = transactions.stream()
                .collect(Collectors.toMap(Transaction::getMtn, transaction -> transaction, (t1, t2) -> t1)).values().stream()
                .collect(Collectors.groupingBy(Transaction::getSenderFullName, Collectors.summingDouble(Transaction::getAmount)));

        return Optional.ofNullable(sentAmountBySender.entrySet().stream()
                .max(Comparator.comparingDouble(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .orElse(null));
    }
    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

}
