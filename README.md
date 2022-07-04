# Chess
This is a chess game that I made using Java. I love chess and play it often. Ever since the pandemic, I chess has held a special place in my heart, so I wanted to make a chess game myself. At the moment, it is 2-player only, but I plan to create a single player mode where you can play against an AI. This chess game follows all the rules of chess including those of checks, castling, en passant, draws, and pins. Please email me at 3069391@gmail.com or comment on this project page should you have any questions about the game, suggestions for further improvement, or have found any bugs.

## Screenshots
<img src="https://user-images.githubusercontent.com/82241006/177068154-80d75e70-f8c7-4cb7-a012-f5f8dcc3a552.png" alt="PEMDAS" width="500" />
<img src="https://user-images.githubusercontent.com/82241006/177068237-3560b7c4-38ea-40d1-aa02-465c8c15388b.png" alt="PEMDAS" width="500" />

<br>
<br>
In this following screenshot, it is black's move and he is trying to advance the pawn on D7. However, that pawn is pinned to the black king by the white bishop and therefore can't move as indicated by the lack of move hints. If the pawn on D7 could move, then this would put the black king in check and thus would not be a legal move.
<img src="https://user-images.githubusercontent.com/82241006/177068308-61f21455-2b80-497e-9c71-4692bc80a7f4.png" alt="PEMDAS" width="500" />
White can castle in this position
<img src="https://user-images.githubusercontent.com/82241006/177068517-55a4533e-151e-4477-b382-19094e1c7376.png" alt="PEMDAS" width="500" />
White can no longer castle because of the black bishop on A6.
<img src="https://user-images.githubusercontent.com/82241006/177068592-18db7083-c4f8-46e4-a39b-611da98c43e1.png" alt="PEMDAS" width="500" />
A black pawn on B2 is ready to promote to a higher level piece
<img src="https://user-images.githubusercontent.com/82241006/177068989-27538473-66f1-470f-bd12-a673e00220aa.png" alt="PEMDAS" width="500" />
<img src="https://user-images.githubusercontent.com/82241006/177069022-5386c50c-6694-4df9-bde9-cf2fe4b46435.png" alt="PEMDAS" width="500" />
Now the white king is in check. White has only three valid moves, one is blocking the check with the light-square bishop, another is blocking the check with the dark-square bishop, and the third is moving the king to D2. 
<img src="https://user-images.githubusercontent.com/82241006/177069078-21d569f4-4861-47fc-a81c-afa8949a7533.png" alt="PEMDAS" width="500" />
<img src="https://user-images.githubusercontent.com/82241006/177069407-ae9fa328-8cb6-4dcf-8e92-32a2711a8827.png" alt="PEMDAS" width="500" />
<img src="https://user-images.githubusercontent.com/82241006/177069407-ae9fa328-8cb6-4dcf-8e92-32a2711a8827.png" alt="PEMDAS" width="500" />
This project also takes into account draws by 3-fold repetition, by 50-move rule, and by insufficient material as well as stalemates.
<img src="https://user-images.githubusercontent.com/82241006/177069460-c7424108-28bd-4a87-b397-9a765ce99ce7.png" alt="PEMDAS" width="500" />

## Setup Instructions
1. First, download the source code, either by executing a `git clone https://github.com/x2dtu/chess.git` in a terminal or downloading the project as a zip through the Github page and extracting that zip.
2. This project uses Java 8. A library called JavaFX is used for the GUI and this library is available in the Java 8 standard but was removed in a later version of Java. If you wish to use a later version of Java, make sure you install JavaFX. However, it is recommended you run this with Java 8. So, if you do not have Java 8, you can install the necessary executables with the Java 8 software development kit (SDK) on the Java Oracle website.
3. To run this program, in the command line at the project directory, run  `java -cp bin chess.Main`. Enjoy!

## Controls and Features

