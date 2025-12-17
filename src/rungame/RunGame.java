package rungame;

import runsimulations.RunGameSimulations;
import runsimulations.ScenarioRunner;

public class RunGame {
    public static void main(String[] args) {
//        new RunGameSimulations().runAllGameSimulations();

        ScenarioRunner runner = new ScenarioRunner();
        runner.runScenarioOne();
        runner.runScenarioTwo();

        System.out.println("All Done");
    }
}
