package poker.version_graphics;

import javafx.application.Preloader;
import javafx.application.Preloader.StateChangeNotification;
import javafx.application.Preloader.StateChangeNotification.Type;
import javafx.scene.Scene;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class loadingScreen extends Preloader {
	private Stage loadingStage;
	private String loadingScreen; //Put in image
	
	public void HandleStageChangeNotification(StateChangeNotification stateNot) {
		if (stateNot.getType() == Type.BEFORE_START) {
			try {
				Thread.sleep(0);
			} 
			catch (InterruptedException e) {
				e.printStackTrace();
			}
			loadingStage.hide();
		}
	}
	
	@Override
	public void start (Stage myStage) throws Exception {
		this.loadingStage = myStage;
		
		ProgressIndicator progInd = new ProgressIndicator();
		progInd.setPrefSize(60, 60);
		
		VBox loading = new VBox(20, progInd);
		loading.setMaxHeight(Region.USE_PREF_SIZE);
		loading.setMaxWidth(Region.USE_PREF_SIZE);
		
		BorderPane bp1 = new BorderPane();
		bp1.setCenter(loading);
		bp1.setStyle("-fx-background-color: #FFFFFF");
		
		Scene scene = new Scene (bp1, 800, 800);
		myStage.setTitle("Mini Poker Project");
		myStage.setScene(scene);
		myStage.show();
		
	}

}