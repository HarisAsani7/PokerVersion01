package poker.version_graphics.view;

import javafx.scene.control.Label;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import poker.version_graphics.model.DeckOfCards;

public class ControlArea extends HBox{
    private DeckLabel lblDeck = new DeckLabel();
    private Region spacer = new Region(); // Empty spacer
    Button btnShuffle = new Button("Shuffle");
    Button btnDeal = new Button("Deal");
    Label text = new Label("Remaining Cards: ");
    
    public ControlArea() {
    	super(); // Always call super-constructor first !!
    	
    	this.getChildren().addAll(text, lblDeck, spacer, btnShuffle, btnDeal);
    	text.setId("textCards");
    	lblDeck.setId("cardDeck");
    	btnDeal.setId("Deal");
    	btnShuffle.setId("Shuffle");

        HBox.setHgrow(spacer, Priority.ALWAYS); // Use region to absorb resizing
        this.setId("controlArea"); // Unique ID in the CSS
    }
    
    public void linkDeck(DeckOfCards deck) {
    	lblDeck.setDeck(deck);
    }      
}
