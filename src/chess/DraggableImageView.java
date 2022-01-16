// package chess;

// import javafx.scene.image.Image;
// import javafx.scene.image.ImageView;
// import javafx.scene.input.MouseEvent;

// public class DraggableImageView {
//     private ImageView imageView;
//     private GUIBoard guiBoard;
//     private Square square;
    
//     public DraggableImageView(GUIBoard guiBoard, Square square, Image image) {
//         imageView = new ImageView(image);
//         this.guiBoard = guiBoard;
//         this.square = square;
//         imageView.setOnDragDetected(e -> onDragStart(e))
//     }

//     private void onDragStart(MouseEvent e) {
//         square.clearMoveHints();
//         if (guiBoard.getBoard().getBoard()[square][rank] == null) {
//             return;
//         }

//         // then we are clicking a piece to move it
//         Piece piece = guiBoard.getBoard().getBoard()[file][rank];
//         if ((piece.getColor() == Color.WHITE && Board.getPly() % 2 == 1) || 
//             (piece.getColor() == Color.BLACK && Board.getPly() % 2 == 0)) {
//                 return; // we are clicking an enemy piece
//         }
//         ImageView imageView = (ImageView)stackPane.getChildren().get(0);
//         Dragboard db = imageView.startDragAndDrop(TransferMode.ANY);
//         ClipboardContent content = new ClipboardContent();
//         content.putImage(piece.getImage());
//         db.setContent(content);
//         e.consume();

//     }


// }
