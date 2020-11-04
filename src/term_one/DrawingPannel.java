package term_one;

import java.awt.*;
import java.awt.event.*; 
import java.io.*;
import java.util.*; //저장
import javax.swing.*;

class Draw implements Serializable {

	private int x, y, x1, y1;
	private int dist;
	public int getDist() {return dist;}
	public void setDist(int dist) {this.dist = dist;}
	public int getX() {return x;}
	public void setX(int x) {this.x = x;}
	public int getY() {return y;}
	public void setY(int y) {this.y = y;}
	public int getX1() {return x1;}
	public void setX1(int x1) {this.x1 = x1;}
	public int getY1() {return y1;}
	public void setY1(int y1) {this.y1 = y1;}

	//color값
	private Color color;
	public void setC(Color c) {this.color = c;}
	public Color getC() {return color;}
}

class PicturePanel extends Frame implements ItemListener, MouseListener, MouseMotionListener{

	//화면구성부
	private MenuBar mb = new MenuBar(); //메뉴바
	private Menu draw = new Menu("DRAW"); //메뉴 DRAW

	public Color colorselect;		//현재 선택된 색상정보
	
	
	//체크박스 서브메뉴
	private CheckboxMenuItem pen = new CheckboxMenuItem("PEN", true);  // 기본체크
	private CheckboxMenuItem line = new CheckboxMenuItem("LINE");
	private CheckboxMenuItem oval = new CheckboxMenuItem("OVAL");
	private CheckboxMenuItem rect = new CheckboxMenuItem("RECT");
		
	private int x, y, x1, y1; //마우스를 눌렀을때와 뗐을때 각 좌표값
	private Vector vc = new Vector();
	
	//화면분할
	private Panel p = new Panel();
	private BorderLayout bl = new BorderLayout();
	private FlowLayout fl = new FlowLayout(FlowLayout.RIGHT);
	
	//panel안에 색상표를 만들어주는 메소드
	public PicturePanel(String title) {
	
		super(title);
		
		//로고만드는 메소드
		Toolkit tk = Toolkit.getDefaultToolkit();
		Image img2 = tk.getImage("nex.gif");
		this.setIconImage(img2);
		
		
		this.init(); //화면구성용 메소드
		this.start(); //이벤트용 메소드
		
		//window의 크기 위치조정
		super.setSize(400,400);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frm = super.getSize();
		int xpos = (int) (screen.getWidth() / 2 - frm.getWidth() / 2);
		int ypos = (int) (screen.getHeight() / 2 - frm.getHeight() / 2);
		super.setLocation(xpos, ypos);
		super.setResizable(false);
		super.setVisible(true);

		
		//색상표 패널 크기 설정
		p.setBounds(0,50,400,90);		
		
		Label colorText;					//'색' 텍스트를 추가해주는 라벨 

		p.setBackground(Color.LIGHT_GRAY);	//바탕이 되는 패널 p 색상 설정
		p.setLayout(null); 			//버튼 크기 설정을 위함
		
		//텍스트 라벨 색상, 위치 설정
		colorText = new Label("색", Label.CENTER);	
		colorText.setBounds(20, 34, 350, 85);		
		colorText.setForeground(Color.BLACK);
		
		//RadioButton을 묶어주는 그룹
		ButtonGroup group = new ButtonGroup();
		
		//두가지 중에 선택(버튼 1,2)
		JRadioButton firColor = new JRadioButton("1", true);
		JRadioButton secColor = new JRadioButton("2");

		//초기색 설정값
		firColor.setBackground(Color.BLACK);
		secColor.setBackground(Color.WHITE);
		
		//둘중에 하나만 선택(버튼 그룹 객체)
		group.add(firColor);
		group.add(secColor);
		
		//외곽선 출력
		firColor.setBorderPainted(true);
		secColor.setBorderPainted(true);
		
		//버튼 위치 설정
		firColor.setBounds(25, 9, 40, 53);
		secColor.setBounds(70, 9, 40, 53);
		
		//패널에 라디오 버튼 넣기
		p.add(firColor);
		p.add(secColor);

		
		//색상표 Button 생성
		JButton[] color = new JButton[30];
		
		int m = 116;		//일정한 간격을 벌려주는 m값(가로)
		int n = 6;			//일정한 간격을 벌려주는 n값(세로)		
		
		//일정한 간격대로 버튼을 만들어줌
		for(int j=0; j<3; j++) {
			for(int i=0; i<10; i++) {		//한 줄에 10칸
				color[j*10+i] = new JButton();
				color[j*10+i].setBounds(m, n, 18, 18);
				m+=20;
				
				p.add(color[j*10+i]);
			}
			n += 20;	//세로위치 +20
			m=116;		//처음 가로위치 부터
		}
		
		//색상표 색 지정
		color[0].setBackground(Color.BLACK);
		color[1].setBackground(Color.DARK_GRAY);
		color[2].setBackground(Color.GRAY);
		color[3].setBackground(Color.RED);
		color[4].setBackground(Color.ORANGE);
		color[5].setBackground(Color.YELLOW);
		color[6].setBackground(Color.GREEN);
		color[7].setBackground(Color.BLUE);
		color[8].setBackground(Color.CYAN);
		color[9].setBackground(Color.MAGENTA);
		color[10].setBackground(Color.WHITE);
		color[11].setBackground(Color.LIGHT_GRAY);
		color[12].setBackground(Color.PINK);
		
		//남는 색상은 흰색으로 지정
		for(int i=13; i<30;i++)
			color[i].setBackground(Color.WHITE);		
		
		
		//색 편집 Button(JColorChooser) 생성 및 버튼색상, 위치 지정
		JButton whatColor = new JButton("편집");
		whatColor.setBackground(Color.WHITE);
		whatColor.setBounds(325, 9, 60, 50);
		
		//색 편집 버튼 클릭시 사용자 정의 색 선택 창 
		whatColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				colorselect = JColorChooser.showDialog(null, "사용자 정의 색 선택", Color.BLACK);

				//RadioButton 배경 색상 변경

				//첫번째 RadioButton인 경우
				if(firColor.isSelected()) {
					firColor.setBackground(colorselect);
				}
				
				//두번째 RadioButton인 경우
				else {
					secColor.setBackground(colorselect);
				}
			}
		});
		
		//패널에 색 편집 버튼 추가
		p.add(whatColor);
				
		
		//color 선택하는 경우 Action 실행 -> 이때 colorselect값이 바뀐다
		for(int i=0;i<30;i++) {
			//색상선택시 버튼의 색상도 변경
			color[i].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JButton colorChoice = (JButton)e.getSource();	//선택된버튼 : colorChoice
					
					colorselect = colorChoice.getBackground();		//색상변경

					
					//RadioButton 배경 색상 변경

					//첫번째 RadioButton인 경우
					if(firColor.isSelected()) {
						firColor.setBackground(colorselect);
					}
					
					//두번째 RadioButton인 경우
					else {
						secColor.setBackground(colorselect);
					}
				}
			});
		}
		
		//firColor을 선택하는경우 -> 그 배경색으로 colorselect를 변경해준다
		firColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				colorselect = firColor.getBackground();		//색상변경
			}
		});
		
		//secColor을 선택하는 경우 -> 그 배경색으로 colorselect를 변경해준다
		secColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				colorselect = secColor.getBackground();		//색상변경
			}
		});
		
		p.add(colorText);					//텍스트 라벨 패널에 넣어줌
		
		add(p);				//panel에 넣어줌

	}
	
	//레이아웃 화면구성
	public void init() {
		draw.add(pen);   //pen
		draw.add(line);  //line
		draw.add(oval);  //oval
		draw.add(rect);  //rect
		mb.add(draw);    // draw
		this.setMenuBar(mb); // 메뉴바
	}
	
	//이벤트 구성
	public void start() {
	// window의 X버튼을 누르면 window를 종료하라
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
	
		});
	
		//선택된 메뉴만 체크되도록 하는 이벤트
		pen.addItemListener(this);
		line.addItemListener(this);
		oval.addItemListener(this);
		rect.addItemListener(this);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);//마우스가 움직이는 동안 그려지는 모양을 위한 이벤트
	}
	
	//그림을 위한 메소드
	public void paint(Graphics g) {
		
		//그림그리기
		for(int i = 0; i < vc.size(); ++i) {
			Draw d = (Draw)vc.elementAt(i);
			
			g.setColor(d.getC());		//color 선택
				
			
			if(d.getDist() == 1) {	//선
				g.drawLine(d.getX(), d.getY(), d.getX1(), d.getY1());
			}
			else if(d.getDist() == 2) {	//OVAL
				
				//오른쪽 아래
				if((d.getX() < d.getX1()) && d.getY() < d.getY1())
					g.drawOval(d.getX(), d.getY(), d.getX1()-d.getX(), d.getY1()-d.getY());
				
				//오른쪽 위
				else if ((d.getX() < d.getX1()) && (d.getY() > d.getY1())) 
					g.drawOval(d.getX(), d.getY1(), d.getX1()-d.getX(), Math.abs(d.getY1()-d.getY()));
				//왼쪽 위
				else if ((d.getX() > d.getX1()) && (d.getY() > d.getY1())) 
					g.drawOval(d.getX1(), d.getY1(), d.getX()-d.getX1(), d.getY()-d.getY1());
				//왼쪽 아래						
				else
					g.drawOval(d.getX1(), d.getY(), d.getX()-d.getX1(), d.getY1()-d.getY());						
				
			}
			else if(d.getDist() == 3) {	//RECT
				
				//오른쪽 아래
				if((d.getX() < d.getX1()) && d.getY() < d.getY1())
					g.drawRect(d.getX(), d.getY(), d.getX1()-d.getX(), d.getY1()-d.getY());
				
				//오른쪽 위
				else if ((d.getX() < d.getX1()) && (d.getY() > d.getY1())) 
					g.drawRect(d.getX(), d.getY1(), d.getX1()-d.getX(), Math.abs(d.getY1()-d.getY()));
				//왼쪽 위
				else if ((d.getX() > d.getX1()) && (d.getY() > d.getY1())) 
					g.drawRect(d.getX1(), d.getY1(), d.getX()-d.getX1(), d.getY()-d.getY1());
				//왼쪽 아래						
				else
					g.drawRect(d.getX1(), d.getY(), d.getX()-d.getX1(), d.getY1()-d.getY());

			}
		}
	
	// 마우스를 눌렀다 떼었을때 
		if(line.getState() == true) {  //라인을 체크하면
			g.drawLine(x, y, x1, y1);  //x,y좌표에서 x1,y1좌표에 라인을 그려라
		}
		else if(oval.getState() == true){  //oval을 체크하면

			//오른쪽 아래
			if((x < x1) && y < y1)
				g.drawOval(x, y, x1-x, y1-y);
			
			//오른쪽 위
			else if ((x < x1) && (y > y1)) 
				g.drawOval(x, y1, x1-x, Math.abs(y1-y));
			//왼쪽 위
			else if ((x > x1) && (y > y1)) 
				g.drawOval(x1, y1, x-x1, y-y1);
			//왼쪽 아래						
			else
				g.drawOval(x1, y, x-x1, y1-y);
					
		}
		else if(rect.getState() == true) {    //rect를 체크하면
			
			//오른쪽 아래
			if((x < x1) && y < y1)
				g.drawRect(x, y, x1-x, y1-y);
			
			//오른쪽 위
			else if ((x < x1) && (y > y1)) 
				g.drawRect(x, y1, x1-x, Math.abs(y1-y));
			//왼쪽 위
			else if ((x > x1) && (y > y1)) 
				g.drawRect(x1, y1, x-x1, y-y1);
			//왼쪽 아래						
			else
				g.drawRect(x1, y, x-x1, y1-y);
			
		}
	}
	
	//선택된 메뉴만 체크되도록 하는 이벤트
	public void itemStateChanged(ItemEvent e){
		pen.setState(false);
		line.setState(false);
		oval.setState(false);
		rect.setState(false);
		CheckboxMenuItem cb = (CheckboxMenuItem)e.getSource();
		   cb.setState(true);
	}
	
	//마우스 사용을 위한 메소드
	public void mouseClicked(MouseEvent e) {}
	public void mousePressed(MouseEvent e) { //눌렀을때
		x = e.getX();       //x의 좌표값을 얻어내어
		y = e.getY();       //y의 좌표값을 얻어내어
	}
	
	public void mouseReleased(MouseEvent e) { //떼었을때
		x1 = e.getX();   //x1의 좌표값
		y1 = e.getY();   //y1의 좌표값
		this.repaint();  //그림을 다시 그린다
	
		if(pen.getState() != true) {  //pen이 true가 아닐때에만 아래를 실행하라
			int dist = 0;
			if(line.getState() == true) dist =1; // line가 체크되면 1값을 대입
			else if(oval.getState() == true) dist = 2; // ovaldl 체크되면 2값을 대입
			else if(rect.getState()== true) dist = 3; // rect가 체크되면 3값을 대입
			Draw d = new Draw();  //d 객체생성
			d.setDist(dist);  //dist 값 대입
	
		 //각각의 값 대입
			d.setX(x);  
			d.setY(y);
			d.setX1(x1);
			d.setY1(y1);
			d.setC(colorselect);		//선택한 색상 값 저장
			vc.add(d); //vc에 값을 저장하라
		}
	}
	
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	
	//마우스가 움직이는 동안 그려지는 모양 보이기
	public void mouseDragged(MouseEvent e) {
		x1 = e.getX();
		y1 = e.getY();
		this.repaint(); //움직이는 동안 보여지기
		// pen 그려지는 이벤트
		if(pen.getState()) {  
		 Draw d = new Draw();  
		 d.setDist(1);
		 d.setX(x);
		 d.setY(y);
		 d.setX1(x1);
		 d.setY1(y1);
		 d.setC(colorselect);		//선택한 선택 값 저장
		 vc.add(d);
		 x = x1;
		 y = y1;
		}
	}

	public void mouseMoved(MouseEvent e) {}

}//class end



public class DrawingPannel {
	public static void main(String[] args){
		new PicturePanel("예나의 그림판"); // 메뉴글자
	}
}