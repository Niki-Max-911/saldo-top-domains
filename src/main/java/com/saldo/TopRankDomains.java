package com.saldo;

import static java.util.stream.Collectors.toMap;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Pattern;

public class TopRankDomains {

    private static final Pattern A_CHERRY_PATTERN = Pattern.compile("@");

    private final List<String> emails;

    private final int desiredTopPositions;

    private final String[] topDomains;

    private final Map<String, Integer> domainsToCount = new HashMap<>();

    public static Map<String, Integer> getNTopDomains(List<String> emails, int desiredTopPositions) {
        return new TopRankDomains(emails, desiredTopPositions).getTopDomains();
    }

    private TopRankDomains(List<String> emails, int desiredTopPositions) {
        this.emails = emails;
        this.desiredTopPositions = desiredTopPositions;

        topDomains = new String[desiredTopPositions];
        Arrays.fill(topDomains, "");

        domainsToCount.put("", Integer.MIN_VALUE);
    }

    private Iterable<String> transformEmailsToDomains() {
        return emails.stream()
            .filter(email -> A_CHERRY_PATTERN.matcher(email).find())
            .map(email -> A_CHERRY_PATTERN.split(email, 2)[1])
            .map(String::trim)
            .filter(domain -> !domain.isEmpty())::iterator;
    }

    private Map<String, Integer> getTopDomains() {

        Iterable<String> domains = transformEmailsToDomains();
        for (String aDomainName : domains) {

            Integer aDomainCount = domainsToCount
                .compute(aDomainName, (aKey, aValue) -> aValue == null ? 1 : aValue + 1);

            int currentDomainPosition = desiredTopPositions;
            int topRankPosition = desiredTopPositions;
            while (topRankPosition > 0) {

                int assumedTopPosition = topRankPosition - 1;
                String assumedTopRankDomain = topDomains[assumedTopPosition];
                Integer assumedTopRankCount = domainsToCount.get(assumedTopRankDomain);

                if (assumedTopRankDomain.equals(aDomainName)) {
                    currentDomainPosition = assumedTopPosition;
                }

                if (aDomainCount >= assumedTopRankCount) {
                    topRankPosition = assumedTopPosition;
                } else {
                    break;
                }
            }
            if (topRankPosition < desiredTopPositions) {
                addAsTopElement(topRankPosition, currentDomainPosition, aDomainName);
            }
        }

        return Arrays.stream(topDomains)
            .filter(s -> !s.isEmpty())
            .collect(toMap(Function.identity(), domainsToCount::get));
    }

    private void addAsTopElement(int topPosition, int previousPosition, String value) {
        if (previousPosition < desiredTopPositions) {
            System.arraycopy(topDomains, topPosition, topDomains, topPosition + 1, previousPosition - topPosition);
        }
        topDomains[topPosition] = value;
    }

}
