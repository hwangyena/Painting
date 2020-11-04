package term_one;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;


public class Drawing extends JFrame {	
	Container contentPane;
	
	public int shapeNum = 0;		//무슨 모양인지 결정해주는 필드
	public int color;		//무슨 색상인지 결정해주는 필드
	
	int x, y, x1, y1;
	
	//기본적인 판을 만드는 클래스 Drawing
	public Drawing() {
		setTitle("예나의 그림판");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = getContentPane();

		//새로운 패널을 만듦 -> panel
		MyPanel panel = new MyPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		
		//메뉴 -> 도형선택(shapeNum으로 구분)
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnDraw = new JMenu("DRAW");
		menuBar.add(mnDraw);

		JRadioButton Pen = new JRadioButton("PEN");
		Pen.addActionListener(new ActionListener() {
			//PEN을 선택한 경우(0)
			public void actionPerformed(ActionEvent arg0) {
				shapeNum = 0;
			}
		});
		mnDraw.add(Pen);
		
		JRadioButton Line = new JRadioButton("LINE");
		Line.addMouseListener(new MouseAdapter() {
			
		});
		Line.addActionListener(new ActionListener() {
			//LINE을 선택한 경우(1)
			public void actionPerformed(ActionEvent arg0) {
				shapeNum = 1;
				
			}
		});
		mnDraw.add(Line);
		
		JRadioButton Oval = new JRadioButton("OVAL");
		Oval.addActionListener(new ActionListener() {
			//OVAL을 선택한 경우(2)
			public void actionPerformed(ActionEvent arg0) {
				shapeNum = 2;
			}
		});
		mnDraw.add(Oval);
		
		JRadioButton Rect = new JRadioButton("RECT");
		Rect.addActionListener(new ActionListener() {
			//RECT을 선택한 경우(3)
			public void actionPerformed(ActionEvent arg0) {
				shapeNum = 3;
			}
		});
		mnDraw.add(Rect);
		
		
		//넷 중에 하나만 선택되도록 한다
		ButtonGroup shape = new ButtonGroup();
		shape.add(Pen);
		shape.add(Line);
		shape.add(Oval);
		shape.add(Rect);
		

		super.setSize(500, 500);
		super.setResizable(false);
		super.setVisible(true);

			
	}
	
	//그림을 그리는 패널을 구현
	//시작점은 vStart에 끝점은 vEnd 벡터에 각각 저장
	class MyPanel extends JPanel implements MouseListener{
		
		Vector<Point> vStart = new Vector<Point>();
		Vector<Point> vEnd = new Vector<Point>();
		
		Point startPoint;
		Point endPoint;
		
		public MyPanel() {
			addMouseListener(this);
		}

		@Override
		public void mouseClicked(MouseEvent e) {
		}
		public void mouseEntered(MouseEvent e) {
		}
		public void mouseExited(MouseEvent e) {
		}

		
		//마우스 버튼이 눌러질 때
		public void mousePressed(MouseEvent e) {
			startPoint = e.getPoint();
		}
		
		//마우스 버튼을 누르고 있을 때
		public void mouseDragged(MouseEvent e) {
			endPoint = e.getPoint();

			vStart.add(startPoint);
			vEnd.add(endPoint);
			repaint();

		}
		
		//마우스 버튼을 뗄 때
		public void mouseReleased(MouseEvent e) {
			endPoint = e.getPoint();

			vStart.add(startPoint);
			vEnd.add(endPoint);			//vEnd에 끝점 저장

			//페인팅 과정 다시 실행 & 변경 사항 화면에 출력
			repaint();
			
		}
		
		public void paint(Graphics g) {
	
			//페인트 색깔 선택	
			g.setColor(Color.BLUE);
			
			//이전에 그렸던 내용 다시 그려줌
			for(int i=0;i<vEnd.size(); ++i) {
				Point st = vStart.elementAt(i);
				Point ed = vEnd.elementAt(i);
				
				switch(shapeNum) {
					case 0:	g.drawLine((int)st.getX(), (int)st.getY(), (int)ed.getX(), (int)ed.getY());
						break;
					case 1: g.drawLine((int)st.getX(), (int)st.getY(), (int)ed.getX(), (int)ed.getY());
						break;
						
					case 2: //오른쪽 아래
						if(((int)st.getX() < (int)ed.getX()) && ((int)st.getY() < (int)ed.getY()))
							g.drawRect((int)st.getX(), (int)st.getY(), (int)ed.getX()-(int)st.getX(), (int)ed.getY()-(int)st.getY());
							//오른쪽 위
						else if (((int)st.getX() < (int)ed.getX()) && ((int)st.getY() > (int)ed.getY())) 
							g.drawRect((int)st.getX(), (int)ed.getY(), (int)ed.getX()-(int)st.getX(), Math.abs((int)ed.getY()-(int)st.getY()));
							//왼쪽 위
						else if (((int)st.getX() > (int)ed.getX()) && ((int)st.getY() > (int)ed.getY())) 
							g.drawRect((int)ed.getX(), (int)ed.getY(), (int)st.getX()-(int)ed.getX(), (int)st.getY()-(int)ed.getY());
							//왼쪽 아래						
						else
							g.drawRect((int)ed.getX(), (int)st.getY(), (int)st.getX()-(int)ed.getX(), (int)ed.getY()-(int)st.getY());						
						break;
						
					case 3: //오른쪽 아래
						if(((int)st.getX() < (int)ed.getX()) && ((int)st.getY() < (int)ed.getY()))
							g.drawOval((int)st.getX(), (int)st.getY(), (int)ed.getX()-(int)st.getX(), (int)ed.getY()-(int)st.getY());
						//오른쪽 위
						else if (((int)st.getX() < (int)ed.getX()) && ((int)st.getY() > (int)ed.getY())) 
							g.drawOval((int)st.getX(), (int)ed.getY(), (int)ed.getX()-(int)st.getX(), Math.abs((int)ed.getY()-(int)st.getY()));
						//왼쪽 위
						else if (((int)st.getX() > (int)ed.getX()) && ((int)st.getY() > (int)ed.getY())) 
							g.drawOval((int)ed.getX(), (int)ed.getY(), (int)st.getX()-(int)ed.getX(), (int)st.getY()-(int)ed.getY());
						//왼쪽 아래						
						else
							g.drawOval((int)ed.getX(), (int)st.getY(), (int)st.getX()-(int)ed.getX(), (int)ed.getY()-(int)st.getY());						
						break;
					default: System.out.println("error");
					 	break;
				}				
		//		setVisible(false);

			}
		}
	}
	
	public static void main(String[] args) {
		new Drawing();
	}
}





