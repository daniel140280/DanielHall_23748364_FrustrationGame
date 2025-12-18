package rungame;

import runsimulations.RunGameSimulations;
import runsimulations.ScenarioRunner;

public class RunGame {
    public static void main(String[] args) {
//        new RunGameSimulations().runAllGameSimulations();

        ScenarioRunner runner = new ScenarioRunner();
        runner.runScenarioOneA();
        runner.runScenarioOneB();
        runner.runScenarioTwo();
        runner.runScenarioThree();
        runner.runScenarioFour();
        runner.runScenarioFive();
        runner.runScenarioSix();
        runner.runScenarioSeven();
        runner.runScenarioEight();

        System.out.println("All Done");
    }
}
