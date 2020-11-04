package term_one;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;


public class Drawing extends JFrame {	
	Container contentPane;
	
	public int shapeNum = 0;		//���� ������� �������ִ� �ʵ�
	public int color;		//���� �������� �������ִ� �ʵ�
	
	int x, y, x1, y1;
	
	//�⺻���� ���� ����� Ŭ���� Drawing
	public Drawing() {
		setTitle("������ �׸���");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = getContentPane();

		//���ο� �г��� ���� -> panel
		MyPanel panel = new MyPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		
		//�޴� -> ��������(shapeNum���� ����)
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnDraw = new JMenu("DRAW");
		menuBar.add(mnDraw);

		JRadioButton Pen = new JRadioButton("PEN");
		Pen.addActionListener(new ActionListener() {
			//PEN�� ������ ���(0)
			public void actionPerformed(ActionEvent arg0) {
				shapeNum = 0;
			}
		});
		mnDraw.add(Pen);
		
		JRadioButton Line = new JRadioButton("LINE");
		Line.addMouseListener(new MouseAdapter() {
			
		});
		Line.addActionListener(new ActionListener() {
			//LINE�� ������ ���(1)
			public void actionPerformed(ActionEvent arg0) {
				shapeNum = 1;
				
			}
		});
		mnDraw.add(Line);
		
		JRadioButton Oval = new JRadioButton("OVAL");
		Oval.addActionListener(new ActionListener() {
			//OVAL�� ������ ���(2)
			public void actionPerformed(ActionEvent arg0) {
				shapeNum = 2;
			}
		});
		mnDraw.add(Oval);
		
		JRadioButton Rect = new JRadioButton("RECT");
		Rect.addActionListener(new ActionListener() {
			//RECT�� ������ ���(3)
			public void actionPerformed(ActionEvent arg0) {
				shapeNum = 3;
			}
		});
		mnDraw.add(Rect);
		
		
		//�� �߿� �ϳ��� ���õǵ��� �Ѵ�
		ButtonGroup shape = new ButtonGroup();
		shape.add(Pen);
		shape.add(Line);
		shape.add(Oval);
		shape.add(Rect);
		

		super.setSize(500, 500);
		super.setResizable(false);
		super.setVisible(true);

			
	}
	
	//�׸��� �׸��� �г��� ����
	//�������� vStart�� ������ vEnd ���Ϳ� ���� ����
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

		
		//���콺 ��ư�� ������ ��
		public void mousePressed(MouseEvent e) {
			startPoint = e.getPoint();
		}
		
		//���콺 ��ư�� ������ ���� ��
		public void mouseDragged(MouseEvent e) {
			endPoint = e.getPoint();

			vStart.add(startPoint);
			vEnd.add(endPoint);
			repaint();

		}
		
		//���콺 ��ư�� �� ��
		public void mouseReleased(MouseEvent e) {
			endPoint = e.getPoint();

			vStart.add(startPoint);
			vEnd.add(endPoint);			//vEnd�� ���� ����

			//������ ���� �ٽ� ���� & ���� ���� ȭ�鿡 ���
			repaint();
			
		}
		
		public void paint(Graphics g) {
	
			//����Ʈ ���� ����	
			g.setColor(Color.BLUE);
			
			//������ �׷ȴ� ���� �ٽ� �׷���
			for(int i=0;i<vEnd.size(); ++i) {
				Point st = vStart.elementAt(i);
				Point ed = vEnd.elementAt(i);
				
				switch(shapeNum) {
					case 0:	g.drawLine((int)st.getX(), (int)st.getY(), (int)ed.getX(), (int)ed.getY());
						break;
					case 1: g.drawLine((int)st.getX(), (int)st.getY(), (int)ed.getX(), (int)ed.getY());
						break;
						
					case 2: //������ �Ʒ�
						if(((int)st.getX() < (int)ed.getX()) && ((int)st.getY() < (int)ed.getY()))
							g.drawRect((int)st.getX(), (int)st.getY(), (int)ed.getX()-(int)st.getX(), (int)ed.getY()-(int)st.getY());
							//������ ��
						else if (((int)st.getX() < (int)ed.getX()) && ((int)st.getY() > (int)ed.getY())) 
							g.drawRect((int)st.getX(), (int)ed.getY(), (int)ed.getX()-(int)st.getX(), Math.abs((int)ed.getY()-(int)st.getY()));
							//���� ��
						else if (((int)st.getX() > (int)ed.getX()) && ((int)st.getY() > (int)ed.getY())) 
							g.drawRect((int)ed.getX(), (int)ed.getY(), (int)st.getX()-(int)ed.getX(), (int)st.getY()-(int)ed.getY());
							//���� �Ʒ�						
						else
							g.drawRect((int)ed.getX(), (int)st.getY(), (int)st.getX()-(int)ed.getX(), (int)ed.getY()-(int)st.getY());						
						break;
						
					case 3: //������ �Ʒ�
						if(((int)st.getX() < (int)ed.getX()) && ((int)st.getY() < (int)ed.getY()))
							g.drawOval((int)st.getX(), (int)st.getY(), (int)ed.getX()-(int)st.getX(), (int)ed.getY()-(int)st.getY());
						//������ ��
						else if (((int)st.getX() < (int)ed.getX()) && ((int)st.getY() > (int)ed.getY())) 
							g.drawOval((int)st.getX(), (int)ed.getY(), (int)ed.getX()-(int)st.getX(), Math.abs((int)ed.getY()-(int)st.getY()));
						//���� ��
						else if (((int)st.getX() > (int)ed.getX()) && ((int)st.getY() > (int)ed.getY())) 
							g.drawOval((int)ed.getX(), (int)ed.getY(), (int)st.getX()-(int)ed.getX(), (int)st.getY()-(int)ed.getY());
						//���� �Ʒ�						
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





