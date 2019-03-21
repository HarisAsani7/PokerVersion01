package poker.version_graphics.view;

import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import poker.version_graphics.model.Card;

public class CardLabel extends Label {
	public CardLabel() {
		super();
		this.getStyleClass().add("card");
	}

	public void setCard(Card card) {
		if (card != null) {
			String fileName = cardToFileName(card);
			Image image = new Image(this.getClass().getClassLoader().getResourceAsStream("poker/images/" + fileName));
			ImageView imv = new ImageView(image);
			imv.fitWidthProperty().bind(this.widthProperty());
			imv.fitHeightProperty().bind(this.heightProperty());
			imv.setPreserveRatio(true);
			this.setGraphic(imv);
			RotateTransition rotation = new RotateTransition(Duration.millis(1000), imv);
			rotation.setCycleCount(1);
			imv.setRotationAxis(Rotate.Y_AXIS);
			rotation.setByAngle(360);
			rotation.setInterpolator(Interpolator.LINEAR);
			imv.setTranslateZ(imv.getBoundsInLocal().getWidth() / 2);
			rotation.play();
		} else {
//			this.setGraphic(null);
			Image image = new Image(this.getClass().getClassLoader().getResourceAsStream("poker/images/balr-logo.png"));
			ImageView imv = new ImageView(image);
			imv.fitWidthProperty().bind(this.widthProperty());
			imv.fitHeightProperty().bind(this.heightProperty());
			imv.setPreserveRatio(true);
			this.setGraphic(imv);
			
		}
	}
	

	private String cardToFileName(Card card) {
		String rank = card.getRank().toString();
		String suit = card.getSuit().toString();
		return rank + "_of_" + suit + ".png";
	}

}
