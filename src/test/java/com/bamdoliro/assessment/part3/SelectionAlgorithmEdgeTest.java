package com.bamdoliro.assessment.part3;

import com.bamdoliro.assessment.problems.Candidate;
import com.bamdoliro.assessment.problems.SelectionAlgorithm;
import com.bamdoliro.assessment.problems.SelectionResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/** 파트 3-1. 직접 추가한 테스트 */
class SelectionAlgorithmEdgeTest {

    private static Candidate busan(String number, double score) {
        return new Candidate(number, score, true);
    }

    private static Candidate other(String number, double score) {
        return new Candidate(number, score, false);
    }

    private static List<String> numbers(List<Candidate> candidates) {
        return candidates.stream().map(Candidate::applicantNumber).toList();
    }

    @Test
    @DisplayName("타지역 한도가 0이면 타지역은 떨어지고 정원이 남아도 다 안채워지라")
    void zeroOtherRegionLimit() {
        List<Candidate> all = List.of(other("G0001", 99), busan("G0002", 50));

        SelectionResult result = SelectionAlgorithm.select(all, 2, 0);

        assertEquals(List.of("G0002"), numbers(result.passed()));
        assertEquals(List.of("G0001"), numbers(result.failed()));
    }

    @Test
    @DisplayName("점수가 경계점수와 같아도 정원이 차기 전에 나온 타지역은 한도 때문에 불합격한다..")
    void tieBeforeQuotaFailsByRegionLimit() {
        List<Candidate> all = List.of(other("G0001", 90), busan("G0002", 90), busan("G0003", 80));

        SelectionResult result = SelectionAlgorithm.select(all, 1, 0);

        assertEquals(List.of("G0002"), numbers(result.passed()));
        assertEquals(List.of("G0001", "G0003"), numbers(result.failed()));
    }
}