import javafx.application.Application;
import javafx.stage.Stage;

/**
 * VoogaSaladMain is responsible only for launching the application.
 * 
 * @author Brian Lavallee
 * @since 30 March 2015
 */
public class VoogaSaladMain extends Application
{
    private static final int HEIGHT = 1000;
    private static final int WIDTH = 1800;
    
    public void start(Stage stage) throws Exception
    {
	stage.setTitle("VoogaSalad");
	stage.setHeight(HEIGHT);
	stage.setWidth(WIDTH);
	stage.setResizable(false);
	
	MainMenu menu = new MainMenu(WIDTH, HEIGHT);
	stage.setScene(menu.initialize());
	
	stage.show();
    }
    
    public static void main(String[] args)
    {
	launch(args);
    }
}