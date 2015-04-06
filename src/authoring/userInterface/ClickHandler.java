package authoring.userInterface;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.Function;

import javafx.event.Event;
import javafx.event.EventHandler;

class ClickHandler implements EventHandler<Event> {
	
	private Object reactor;
	private Object target;
	private Object[] args;

	ClickHandler(Method reactor, Object target, Object... args){
		this.reactor = reactor;
		this.target = target;
		this.args = args;
	}
	
	ClickHandler(Function reactor){
		this.reactor = reactor;
	}
	
	@Override
	public void handle(Event event) {
		if(reactor instanceof Method){
			try {
				((Method) reactor).invoke(target, args);
			} catch (IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		else if (reactor instanceof Function) {
			((Function) reactor).apply(target);
		}
	}


}
