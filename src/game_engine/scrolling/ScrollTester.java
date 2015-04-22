package game_engine.scrolling;

import game_engine.behaviors.IAction;



public class ScrollTester {

    @IActionAnnotation(description = "1", numParams = 0)
    private IAction one = (params) -> {};

    @IActionAnnotation(description = "2", numParams = 3)
    private IAction two = (params) -> {};

}
