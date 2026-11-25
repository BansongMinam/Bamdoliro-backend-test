package com.bamdoliro.assessment.problems;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SelectionAlgorithm {

    /**
     * 파트 3-1. 규칙은 README.md 의 "파트 3-1" 절을 참고한다.
     *
     * @param candidates       지원자 목록 (정렬되어 있지 않을 수 있고, 이 메서드가 수정하면 안 된다)
     * @param quota            선발 정원
     * @param otherRegionLimit 부산 외 지역 지원자를 합격시킬 수 있는 최대 인원
     */
    public static SelectionResult select(List<Candidate> candidates, int quota, int otherRegionLimit) {
        ArrayList passed = new ArrayList();
        ArrayList failed = new ArrayList();
        if (candidates.size() == 0){
            return new SelectionResult(passed, failed);
        }
        List<Candidate> sorted = new ArrayList<>(candidates);
        sorted.sort(
                Comparator.comparingDouble(Candidate::score).reversed()
                        .thenComparing(Candidate::applicantNumber)
        );
        if (quota <= 0){
            failed.addAll(sorted);
            return new SelectionResult(passed,failed);
        }
        double wjddnjstnsqjswjatn = sorted.get(Math.min(quota, sorted.size()) - 1).score();
        for(Candidate i : sorted){
            if (quota>0) {
                if (i.busan()) {
                    passed.add(i);
                    quota--;
                } else {
                    if (otherRegionLimit > 0) {
                        passed.add(i);
                        quota--;
                        otherRegionLimit--;
                    } else {
                        failed.add(i);
                    }
                }
            }else if (i.score() == wjddnjstnsqjswjatn) {
                passed.add(i);
            }else{
                failed.add(i);
            }

            }
        return new SelectionResult(passed, failed);


    }
}
