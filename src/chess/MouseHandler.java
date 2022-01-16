package chess;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class MouseHandler implements EventHandler<MouseEvent> {
    private EventHandler<MouseEvent> onClickedEventHandler;
    private EventHandler<MouseEvent> onDraggedEventHandler;
    // private EventHandler<MouseEvent> onPressedEventHandler;

    private boolean dragging = false;

    public MouseHandler(EventHandler<MouseEvent> clicked, EventHandler<MouseEvent> dragged) {
        onClickedEventHandler = clicked;
        onDraggedEventHandler = dragged;
        // onPressedEventHandler = pressed;
    }

    @Override
    public void handle(MouseEvent event) {
        if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
            dragging = false;
        }
        else if (event.getEventType() == MouseEvent.DRAG_DETECTED) {
            dragging = true;
        }
        else if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
            //maybe filter on dragging (== true)
            onDraggedEventHandler.handle(event);
        }
        else if (event.getEventType() == MouseEvent.MOUSE_CLICKED) {
            // if (!dragging) {
                onClickedEventHandler.handle(event);
            // }
        }

    }
    
}
