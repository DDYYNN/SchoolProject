package Chess;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.border.*;
//======================================================Don't modify below===============================================================//
enum PieceType {king, queen, bishop, knight, rook, pawn, none}
enum PlayerColor {black, white, none}
	
public class ChessBoard {
	private final JPanel gui = new JPanel(new BorderLayout(3, 3));
	private JPanel chessBoard;
	private JButton[][] chessBoardSquares = new JButton[8][8];
	private Piece[][] chessBoardStatus = new Piece[8][8];
	private ImageIcon[] pieceImage_b = new ImageIcon[7];
	private ImageIcon[] pieceImage_w = new ImageIcon[7];
	private JLabel message = new JLabel("Enter Reset to Start");


	
	ChessBoard(){
		initPieceImages();
		initBoardStatus();
		initializeGui();
	}
	
	public final void initBoardStatus(){
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++) chessBoardStatus[j][i] = new Piece();
		}
	}
	
	public final void initPieceImages(){
		pieceImage_b[0] = new ImageIcon(new ImageIcon("./img/king_b.png").getImage().getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH));
		pieceImage_b[1] = new ImageIcon(new ImageIcon("./img/queen_b.png").getImage().getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH));
		pieceImage_b[2] = new ImageIcon(new ImageIcon("./img/bishop_b.png").getImage().getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH));
		pieceImage_b[3] = new ImageIcon(new ImageIcon("./img/knight_b.png").getImage().getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH));
		pieceImage_b[4] = new ImageIcon(new ImageIcon("./img/rook_b.png").getImage().getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH));
		pieceImage_b[5] = new ImageIcon(new ImageIcon("./img/pawn_b.png").getImage().getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH));
		pieceImage_b[6] = new ImageIcon(new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB));
		
		pieceImage_w[0] = new ImageIcon(new ImageIcon("./img/king_w.png").getImage().getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH));
		pieceImage_w[1] = new ImageIcon(new ImageIcon("./img/queen_w.png").getImage().getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH));
		pieceImage_w[2] = new ImageIcon(new ImageIcon("./img/bishop_w.png").getImage().getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH));
		pieceImage_w[3] = new ImageIcon(new ImageIcon("./img/knight_w.png").getImage().getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH));
		pieceImage_w[4] = new ImageIcon(new ImageIcon("./img/rook_w.png").getImage().getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH));
		pieceImage_w[5] = new ImageIcon(new ImageIcon("./img/pawn_w.png").getImage().getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH));
		pieceImage_w[6] = new ImageIcon(new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB));
	}
	
	public ImageIcon getImageIcon(Piece piece){
		if(piece.color.equals(PlayerColor.black)){
			if(piece.type.equals(PieceType.king)) return pieceImage_b[0];
			else if(piece.type.equals(PieceType.queen)) return pieceImage_b[1];
			else if(piece.type.equals(PieceType.bishop)) return pieceImage_b[2];
			else if(piece.type.equals(PieceType.knight)) return pieceImage_b[3];
			else if(piece.type.equals(PieceType.rook)) return pieceImage_b[4];
			else if(piece.type.equals(PieceType.pawn)) return pieceImage_b[5];
			else return pieceImage_b[6];
		}
		else if(piece.color.equals(PlayerColor.white)){
			if(piece.type.equals(PieceType.king)) return pieceImage_w[0];
			else if(piece.type.equals(PieceType.queen)) return pieceImage_w[1];
			else if(piece.type.equals(PieceType.bishop)) return pieceImage_w[2];
			else if(piece.type.equals(PieceType.knight)) return pieceImage_w[3];
			else if(piece.type.equals(PieceType.rook)) return pieceImage_w[4];
			else if(piece.type.equals(PieceType.pawn)) return pieceImage_w[5];
			else return pieceImage_w[6];
		}
		else return pieceImage_w[6];
	}

	public final void initializeGui(){
		gui.setBorder(new EmptyBorder(5, 5, 5, 5));
	    JToolBar tools = new JToolBar();
	    tools.setFloatable(false);
	    gui.add(tools, BorderLayout.PAGE_START);
	    JButton startButton = new JButton("Reset");
	    startButton.addActionListener(new ActionListener(){
	    	public void actionPerformed(ActionEvent e){
	    		initiateBoard();
	    	}
	    });
	    
	    tools.add(startButton);
	    tools.addSeparator();
	    tools.add(message);

	    chessBoard = new JPanel(new GridLayout(0, 8));
	    chessBoard.setBorder(new LineBorder(Color.BLACK));
	    gui.add(chessBoard);
	    ImageIcon defaultIcon = new ImageIcon(new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB));
	    Insets buttonMargin = new Insets(0,0,0,0);
	    for(int i=0; i<chessBoardSquares.length; i++) {
	        for (int j = 0; j < chessBoardSquares[i].length; j++) {
	        	JButton b = new JButton();
	        	b.addActionListener(new ButtonListener(i, j));
	            b.setMargin(buttonMargin);
	            b.setIcon(defaultIcon);
	            if((j % 2 == 1 && i % 2 == 1)|| (j % 2 == 0 && i % 2 == 0)) b.setBackground(Color.WHITE);
	            else b.setBackground(Color.gray);
	            b.setOpaque(true);
	            b.setBorderPainted(false);
	            chessBoardSquares[j][i] = b;
	        }
	    }

	    for (int i=0; i < 8; i++) {
	        for (int j=0; j < 8; j++) chessBoard.add(chessBoardSquares[j][i]);
	        
	    }
	}

	public final JComponent getGui() {
	    return gui;
	}
	
	public static void main(String[] args) {
	    Runnable r = new Runnable() {
	        @Override
	        public void run() {
	        	ChessBoard cb = new ChessBoard();
                JFrame f = new JFrame("Chess");
                f.add(cb.getGui());
                f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                f.setLocationByPlatform(true);
                f.setResizable(false);
                f.pack();
                f.setMinimumSize(f.getSize());
                f.setVisible(true);
            }
        };
        SwingUtilities.invokeLater(r);
	}
		
			//================================Utilize these functions========================================//	
	
	class Piece{
		PlayerColor color;
		PieceType type;
		
		Piece(){
			color = PlayerColor.none;
			type = PieceType.none;
		}
		Piece(PlayerColor color, PieceType type){
			this.color = color;
			this.type = type;
		}
	}
	
	public void setIcon(int x, int y, Piece piece){
		chessBoardSquares[y][x].setIcon(getImageIcon(piece));
		chessBoardStatus[y][x] = piece;
	}
	
	public Piece getIcon(int x, int y){
		return chessBoardStatus[y][x];
	}
	
	public void markPosition(int x, int y){
		chessBoardSquares[y][x].setBackground(Color.pink);
	}
	
	public void unmarkPosition(int x, int y){
		if((y % 2 == 1 && x % 2 == 1)|| (y % 2 == 0 && x % 2 == 0)) chessBoardSquares[y][x].setBackground(Color.WHITE);
		else chessBoardSquares[y][x].setBackground(Color.gray);
	}
	
	public void setStatus(String inpt){
		message.setText(inpt);
	}
	
	public void initiateBoard(){
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++) setIcon(i, j, new Piece());
		}
		setIcon(0, 0, new Piece(PlayerColor.black, PieceType.rook));
		setIcon(0, 1, new Piece(PlayerColor.black, PieceType.knight));
		setIcon(0, 2, new Piece(PlayerColor.black, PieceType.bishop));
		setIcon(0, 3, new Piece(PlayerColor.black, PieceType.queen));
		setIcon(0, 4, new Piece(PlayerColor.black, PieceType.king));
		setIcon(0, 5, new Piece(PlayerColor.black, PieceType.bishop));
		setIcon(0, 6, new Piece(PlayerColor.black, PieceType.knight));
		setIcon(0, 7, new Piece(PlayerColor.black, PieceType.rook));
		for(int i=0;i<8;i++){
			setIcon(1, i, new Piece(PlayerColor.black, PieceType.pawn));
			setIcon(6, i, new Piece(PlayerColor.white, PieceType.pawn));
		}
		setIcon(7, 0, new Piece(PlayerColor.white, PieceType.rook));
		setIcon(7, 1, new Piece(PlayerColor.white, PieceType.knight));
		setIcon(7, 2, new Piece(PlayerColor.white, PieceType.bishop));
		setIcon(7, 3, new Piece(PlayerColor.white, PieceType.queen));
		setIcon(7, 4, new Piece(PlayerColor.white, PieceType.king));
		setIcon(7, 5, new Piece(PlayerColor.white, PieceType.bishop));
		setIcon(7, 6, new Piece(PlayerColor.white, PieceType.knight));
		setIcon(7, 7, new Piece(PlayerColor.white, PieceType.rook));
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++) unmarkPosition(i, j);
		}
		onInitiateBoard();
	}
//======================================================Don't modify above==============================================================//	
	
//======================================================Implement below=================================================================//		
	class pi{
		int x,y;
		pi(int a,int b){
			x=a; y=b;
		}
	}
	pi posIcon(Piece p){
		for (int i=0;i<8;i++){
			for (int j=0;j<8;j++){
				if (getIcon(i,j).equals(p)){
					return new pi(i,j);
				}
			}
		}
		return new pi(8,8);
	}
	boolean Cango(Piece p,int x,int y){
		pi pos = posIcon(p);
		if (pos.x==x && pos.y==y) return false;
		if (p.type.equals(PieceType.pawn)){
			int dx;
			if (p.color.equals(PlayerColor.black)) dx=1;
			else dx=-1;

			// jump
			if ((p.color.equals(PlayerColor.black) && pos.x==1) || (p.color.equals(PlayerColor.white) && pos.x==6)){
				if ((pos.x+2*dx==x && pos.y==y) && getIcon(pos.x+dx,y).type.equals(PieceType.none) && getIcon(pos.x+2*dx,y).type.equals(PieceType.none))
					return true;
			}
			// go
			if ((pos.x+dx==x && pos.y==y) && getIcon(pos.x+dx,pos.y).type.equals(PieceType.none)) return true;
			// attack
			if ((pos.x+dx==x && pos.y+1==y) && !getIcon(x,y).type.equals(PieceType.none) && !getIcon(x,y).color.equals(p.color)) return true;
			if ((pos.x+dx==x && pos.y-1==y) && !getIcon(x,y).type.equals(PieceType.none) && !getIcon(x,y).color.equals(p.color)) return true;
		}
		if (p.type.equals(PieceType.knight)){
			int dx[] = {1,2,2,1,-1,-2,-2,-1}, dy[] = {-2,-1,1,2,2,1,-1,-2};
			for (int i=0;i<8;i++){
				if (pos.x+dx[i]!=x || pos.y+dy[i]!=y) continue;
				// go
				if (getIcon(x,y).type.equals(PieceType.none)) return true;
				// attack
				if (!getIcon(x,y).color.equals(p.color)) return true;
			}
		}
		if (p.type.equals(PieceType.bishop)){
			if (Math.abs(x-pos.x)==Math.abs(y-pos.y)){
				int dx = (x-pos.x)/Math.abs(x-pos.x), dy = (y-pos.y)/Math.abs(y-pos.y),i;
				for (i=1;i<Math.abs(x-pos.x);i++){
					if (!getIcon(pos.x+i*dx,pos.y+i*dy).type.equals(PieceType.none)) break;
				}
				if (i==Math.abs(x-pos.x)){
					// go
					if (getIcon(x,y).type.equals(PieceType.none)) return true;
					// attack
					if (!getIcon(x,y).type.equals(PieceType.none) && !getIcon(x,y).color.equals(p.color)) return true;
				}
			}
		}
		if (p.type.equals(PieceType.rook)){
			if (pos.x==x || pos.y==y){
				int dx=0,dy=0,mm,i;
				if (x!=pos.x){
					dx = (x-pos.x)/Math.abs(x-pos.x);
					mm = Math.abs(x-pos.x);
				}
				else{
					dy = (y-pos.y)/Math.abs(y-pos.y);
					mm = Math.abs(y-pos.y);
				}
				for (i=1;i<mm;i++){
					if (!getIcon(pos.x+i*dx,pos.y+i*dy).type.equals(PieceType.none)) break;
				}
				if (i==mm){
					// go
					if (getIcon(x,y).type.equals(PieceType.none)) return true;
					// attack
					if (!getIcon(x,y).type.equals(PieceType.none) && !getIcon(x,y).color.equals(p.color)) return true;
				}
			}
		}
		if (p.type.equals(PieceType.queen)){
			if (Math.abs(x-pos.x)==Math.abs(y-pos.y)){
				int dx = (x-pos.x)/Math.abs(x-pos.x), dy = (y-pos.y)/Math.abs(y-pos.y),i;
				for (i=1;i<Math.abs(x-pos.x);i++){
					if (!getIcon(pos.x+i*dx,pos.y+i*dy).type.equals(PieceType.none)) break;
				}
				if (i==Math.abs(x-pos.x)){
					// go
					if (getIcon(x,y).type.equals(PieceType.none)) return true;
					// attack
					if (!getIcon(x,y).type.equals(PieceType.none) && !getIcon(x,y).color.equals(p.color)) return true;
				}
			}
			if (pos.x==x || pos.y==y){
				int dx=0,dy=0,mm,i;
				if (x!=pos.x){
					dx = (x-pos.x)/Math.abs(x-pos.x);
					mm = Math.abs(x-pos.x);
				}
				else{
					dy = (y-pos.y)/Math.abs(y-pos.y);
					mm = Math.abs(y-pos.y);
				}
				for (i=1;i<mm;i++){
					if (!getIcon(pos.x+i*dx,pos.y+i*dy).type.equals(PieceType.none)) break;
				}
				if (i==mm){
					// go
					if (getIcon(x,y).type.equals(PieceType.none)) return true;
					// attack
					if (!getIcon(x,y).type.equals(PieceType.none) && !getIcon(x,y).color.equals(p.color)) return true;
				}
			}		
		}
		if (p.type.equals(PieceType.king)){
			int dx[]={-1,-1,-1,0,1,1,1,0}, dy[]={-1,0,1,1,1,0,-1,-1};
			for (int i=0;i<8;i++){
				if (pos.x+dx[i]!=x || pos.y+dy[i]!=y) continue;
				// go
				if (getIcon(x,y).type.equals(PieceType.none)) return true;
				// attack
				if (!getIcon(x,y).color.equals(p.color)) return true;
			}
		}
		return false;
	}
	
	
	public int click,turn,exit;
	public pi now;
	
	boolean check(PlayerColor c){
		pi pos=new pi(0,0);
		for (int i=0;i<8;i++) for (int j=0;j<8;j++) if (getIcon(i,j).color.equals(c) && getIcon(i,j).type.equals(PieceType.king)){
			pos.x=i; pos.y=j;
		}
		for (int i=0;i<8;i++) for (int j=0;j<8;j++){
			if (!getIcon(i,j).color.equals(c) && !getIcon(i,j).type.equals(PieceType.none)){
				if (Cango(getIcon(i,j),pos.x,pos.y)) return true;
			}
		}
		return false;
	}
	boolean checkmate(PlayerColor c){
		for (int i=0;i<8;i++) for (int j=0;j<8;j++) if (getIcon(i,j).color.equals(c) && !getIcon(i,j).type.equals(PieceType.none)){
			for (int i2=0;i2<8;i2++) for (int j2=0;j2<8;j2++){
				if (Cango(getIcon(i,j),i2,j2)){
					Piece p1 = getIcon(i,j);
					Piece p2 = getIcon(i2,j2);
					setIcon(i2,j2,p1); setIcon(i,j,new Piece(PlayerColor.none,PieceType.none));
					boolean ret=check(c);					
					setIcon(i2,j2,p2); setIcon(i,j,p1);
					if (!ret) return false;
				}
			}
		}
		return true;
	}
	class ButtonListener implements ActionListener{
		int x;
		int y;
		ButtonListener(int x, int y){
			this.x = x;
			this.y = y;
		}
		public void actionPerformed(ActionEvent e) {	// Only modify here
			if (exit==1) return;
			
			if (click==0){
				if (!getIcon(x,y).type.equals(PieceType.none) && ((turn==0 && getIcon(x,y).color.equals(PlayerColor.black)) || (turn==1 && getIcon(x,y).color.equals(PlayerColor.white)))){
					now = new pi(x,y);
					click=1;
					for (int i=0;i<8;i++) for (int j=0;j<8;j++) if (Cango(getIcon(x,y),i,j)) markPosition(i,j);
				}
			}
			else{
				click=0;
				for (int i=0;i<8;i++) for (int j=0;j<8;j++) unmarkPosition(i,j);
				if (Cango(getIcon(now.x,now.y),x,y)){
					if (getIcon(x,y).type.equals(PieceType.king)) exit=1;
					setIcon(x,y,getIcon(now.x,now.y));
					setIcon(now.x,now.y,new Piece(PlayerColor.none,PieceType.none));
					turn=(turn+1)%2;
				}
			}
			if (turn==0){
				String str; str="BLACK's TURN";
				boolean cb=check(PlayerColor.black);;
				if (cb){
					if (checkmate(PlayerColor.black)){
						exit=1;
						str+="/CHECKMATE";
					}
					else str+="/CHECK";
				}
				setStatus(str);
			}
			else{
				String str; str="WHITE's TURN";
				boolean cw=check(PlayerColor.white);
				if (cw){
					if (checkmate(PlayerColor.white)){
						exit=1;
						str+="/CHECKMATE";
					}
					else str+="/CHECK";
				}
				setStatus(str);
			}
		}
	}
	
	void onInitiateBoard(){
		click=0;
		turn=0;
		exit=0;
		setStatus("BLACK's TURN");
	}
}