package com.saldo;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;

public class TopRankDomainsTest {

    @Test
    public void testCornerCases() {
        List<String> emails = Arrays.asList("", "andrij.andrijenko", "@");
        Map<String, Integer> expectedDomainCounts = new HashMap<>();

        Map<String, Integer> topDomainsMap = TopRankDomains.getNTopDomains(emails, 10);

        assertThat(topDomainsMap)
            .containsExactlyInAnyOrderEntriesOf(expectedDomainCounts);
    }

    @Test
    public void testTop1() {
        List<String> emails = Arrays.asList(
            "max@saldoaaps.com", "max@saldoaaps.com",
            "max@1saldoaaps.com", "max@1saldoaaps.com",
            "max@2saldoaaps.com", "max@2saldoaaps.com", "max@2saldoaaps.com");

        Map<String, Integer> expectedDomainCounts = new HashMap<>();
        expectedDomainCounts.put("2saldoaaps.com", 3);

        Map<String, Integer> topDomainsMap = TopRankDomains.getNTopDomains(emails, 1);

        assertThat(topDomainsMap)
            .containsExactlyInAnyOrderEntriesOf(expectedDomainCounts);
    }

    @Test
    public void testTop0() {
        List<String> emails = Arrays.asList(
            "max@saldoaaps.com", "max@saldoaaps.com",
            "max@1saldoaaps.com", "max@1saldoaaps.com",
            "max@2saldoaaps.com", "max@2saldoaaps.com", "max@2saldoaaps.com");

        Map<String, Integer> expectedDomainCounts = new HashMap<>();

        Map<String, Integer> topDomainsMap = TopRankDomains.getNTopDomains(emails, 0);

        assertThat(topDomainsMap)
            .containsExactlyInAnyOrderEntriesOf(expectedDomainCounts);
    }

    @Test
    public void testTop10() {
        List<String> emails = Arrays.asList(
            "max@saldoaaps.com", "max@saldoaaps.com",
            "max@1saldoaaps.com", "max@1saldoaaps.com",
            "max@2saldoaaps.com", "max@2saldoaaps.com", "max@2saldoaaps.com",
            "max@3saldoaaps.com", "max@3saldoaaps.com",
            "max@4saldoaaps.com", "max@4saldoaaps.com", "max@4saldoaaps.com", "max@4saldoaaps.com",
            "max@5saldoaaps.com", "max@5saldoaaps.com",
            "max@6gmail.com", "max@6gmail.com",
            "max@7gmail.com", "max@7gmail.com",
            "max@8gmail.com", "max@8gmail.com",
            "max@9gmail.com", "max@9gmail.com",
            "max@yahoo.com");

        Map<String, Integer> expectedDomainCounts = new HashMap<>();
        expectedDomainCounts.put("4saldoaaps.com", 4);
        expectedDomainCounts.put("2saldoaaps.com", 3);
        expectedDomainCounts.put("1saldoaaps.com", 2);
        expectedDomainCounts.put("3saldoaaps.com", 2);
        expectedDomainCounts.put("saldoaaps.com", 2);
        expectedDomainCounts.put("5saldoaaps.com", 2);
        expectedDomainCounts.put("6gmail.com", 2);
        expectedDomainCounts.put("7gmail.com", 2);
        expectedDomainCounts.put("8gmail.com", 2);
        expectedDomainCounts.put("9gmail.com", 2);

        Map<String, Integer> topDomainsMap = TopRankDomains.getNTopDomains(emails, 10);

        assertThat(topDomainsMap)
            .containsExactlyInAnyOrderEntriesOf(expectedDomainCounts);
    }

}
