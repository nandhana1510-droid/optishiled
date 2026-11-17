package threads;

import model.PerformanceResult;

import java.util.ArrayList;
import java.util.List;

public class ResultCollector {

    private List<PerformanceResult> results;

    public ResultCollector() {
        results = new ArrayList<>();
    }

    public synchronized void addResult(
            PerformanceResult result) {

        results.add(result);

        System.out.println(
                "Result safely added: "
                        + result.getAlgorithmName()
        );
    }

    public synchronized List<PerformanceResult>
    getResults() {

        return new ArrayList<>(results);
    }
}