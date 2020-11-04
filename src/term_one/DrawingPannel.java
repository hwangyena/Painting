package term_one;

import java.awt.*;
import java.awt.event.*; 
import java.io.*;
import java.util.*; //����
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

	//color��
	private Color color;
	public void setC(Color c) {this.color = c;}
	public Color getC() {return color;}
}

class PicturePanel extends Frame implements ItemListener, MouseListener, MouseMotionListener{

	//ȭ�鱸����
	private MenuBar mb = new MenuBar(); //�޴���
	private Menu draw = new Menu("DRAW"); //�޴� DRAW

	public Color colorselect;		//���� ���õ� ��������
	
	
	//üũ�ڽ� ����޴�
	private CheckboxMenuItem pen = new CheckboxMenuItem("PEN", true);  // �⺻üũ
	private CheckboxMenuItem line = new CheckboxMenuItem("LINE");
	private CheckboxMenuItem oval = new CheckboxMenuItem("OVAL");
	private CheckboxMenuItem rect = new CheckboxMenuItem("RECT");
		
	private int x, y, x1, y1; //���콺�� ���������� ������ �� ��ǥ��
	private Vector vc = new Vector();
	
	//ȭ�����
	private Panel p = new Panel();
	private BorderLayout bl = new BorderLayout();
	private FlowLayout fl = new FlowLayout(FlowLayout.RIGHT);
	
	//panel�ȿ� ����ǥ�� ������ִ� �޼ҵ�
	public PicturePanel(String title) {
	
		super(title);
		
		//�ΰ���� �޼ҵ�
		Toolkit tk = Toolkit.getDefaultToolkit();
		Image img2 = tk.getImage("nex.gif");
		this.setIconImage(img2);
		
		
		this.init(); //ȭ�鱸���� �޼ҵ�
		this.start(); //�̺�Ʈ�� �޼ҵ�
		
		//window�� ũ�� ��ġ����
		super.setSize(400,400);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frm = super.getSize();
		int xpos = (int) (screen.getWidth() / 2 - frm.getWidth() / 2);
		int ypos = (int) (screen.getHeight() / 2 - frm.getHeight() / 2);
		super.setLocation(xpos, ypos);
		super.setResizable(false);
		super.setVisible(true);

		
		//����ǥ �г� ũ�� ����
		p.setBounds(0,50,400,90);		
		
		Label colorText;					//'��' �ؽ�Ʈ�� �߰����ִ� �� 

		p.setBackground(Color.LIGHT_GRAY);	//������ �Ǵ� �г� p ���� ����
		p.setLayout(null); 			//��ư ũ�� ������ ����
		
		//�ؽ�Ʈ �� ����, ��ġ ����
		colorText = new Label("��", Label.CENTER);	
		colorText.setBounds(20, 34, 350, 85);		
		colorText.setForeground(Color.BLACK);
		
		//RadioButton�� �����ִ� �׷�
		ButtonGroup group = new ButtonGroup();
		
		//�ΰ��� �߿� ����(��ư 1,2)
		JRadioButton firColor = new JRadioButton("1", true);
		JRadioButton secColor = new JRadioButton("2");

		//�ʱ�� ������
		firColor.setBackground(Color.BLACK);
		secColor.setBackground(Color.WHITE);
		
		//���߿� �ϳ��� ����(��ư �׷� ��ü)
		group.add(firColor);
		group.add(secColor);
		
		//�ܰ��� ���
		firColor.setBorderPainted(true);
		secColor.setBorderPainted(true);
		
		//��ư ��ġ ����
		firColor.setBounds(25, 9, 40, 53);
		secColor.setBounds(70, 9, 40, 53);
		
		//�гο� ���� ��ư �ֱ�
		p.add(firColor);
		p.add(secColor);

		
		//����ǥ Button ����
		JButton[] color = new JButton[30];
		
		int m = 116;		//������ ������ �����ִ� m��(����)
		int n = 6;			//������ ������ �����ִ� n��(����)		
		
		//������ ���ݴ�� ��ư�� �������
		for(int j=0; j<3; j++) {
			for(int i=0; i<10; i++) {		//�� �ٿ� 10ĭ
				color[j*10+i] = new JButton();
				color[j*10+i].setBounds(m, n, 18, 18);
				m+=20;
				
				p.add(color[j*10+i]);
			}
			n += 20;	//������ġ +20
			m=116;		//ó�� ������ġ ����
		}
		
		//����ǥ �� ����
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
		
		//���� ������ ������� ����
		for(int i=13; i<30;i++)
			color[i].setBackground(Color.WHITE);		
		
		
		//�� ���� Button(JColorChooser) ���� �� ��ư����, ��ġ ����
		JButton whatColor = new JButton("����");
		whatColor.setBackground(Color.WHITE);
		whatColor.setBounds(325, 9, 60, 50);
		
		//�� ���� ��ư Ŭ���� ����� ���� �� ���� â 
		whatColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				colorselect = JColorChooser.showDialog(null, "����� ���� �� ����", Color.BLACK);

				//RadioButton ��� ���� ����

				//ù��° RadioButton�� ���
				if(firColor.isSelected()) {
					firColor.setBackground(colorselect);
				}
				
				//�ι�° RadioButton�� ���
				else {
					secColor.setBackground(colorselect);
				}
			}
		});
		
		//�гο� �� ���� ��ư �߰�
		p.add(whatColor);
				
		
		//color �����ϴ� ��� Action ���� -> �̶� colorselect���� �ٲ��
		for(int i=0;i<30;i++) {
			//�����ý� ��ư�� ���� ����
			color[i].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JButton colorChoice = (JButton)e.getSource();	//���õȹ�ư : colorChoice
					
					colorselect = colorChoice.getBackground();		//���󺯰�

					
					//RadioButton ��� ���� ����

					//ù��° RadioButton�� ���
					if(firColor.isSelected()) {
						firColor.setBackground(colorselect);
					}
					
					//�ι�° RadioButton�� ���
					else {
						secColor.setBackground(colorselect);
					}
				}
			});
		}
		
		//firColor�� �����ϴ°�� -> �� �������� colorselect�� �������ش�
		firColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				colorselect = firColor.getBackground();		//���󺯰�
			}
		});
		
		//secColor�� �����ϴ� ��� -> �� �������� colorselect�� �������ش�
		secColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				colorselect = secColor.getBackground();		//���󺯰�
			}
		});
		
		p.add(colorText);					//�ؽ�Ʈ �� �гο� �־���
		
		add(p);				//panel�� �־���

	}
	
	//���̾ƿ� ȭ�鱸��
	public void init() {
		draw.add(pen);   //pen
		draw.add(line);  //line
		draw.add(oval);  //oval
		draw.add(rect);  //rect
		mb.add(draw);    // draw
		this.setMenuBar(mb); // �޴���
	}
	
	//�̺�Ʈ ����
	public void start() {
	// window�� X��ư�� ������ window�� �����϶�
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
	
		});
	
		//���õ� �޴��� üũ�ǵ��� �ϴ� �̺�Ʈ
		pen.addItemListener(this);
		line.addItemListener(this);
		oval.addItemListener(this);
		rect.addItemListener(this);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);//���콺�� �����̴� ���� �׷����� ����� ���� �̺�Ʈ
	}
	
	//�׸��� ���� �޼ҵ�
	public void paint(Graphics g) {
		
		//�׸��׸���
		for(int i = 0; i < vc.size(); ++i) {
			Draw d = (Draw)vc.elementAt(i);
			
			g.setColor(d.getC());		//color ����
				
			
			if(d.getDist() == 1) {	//��
				g.drawLine(d.getX(), d.getY(), d.getX1(), d.getY1());
			}
			else if(d.getDist() == 2) {	//OVAL
				
				//������ �Ʒ�
				if((d.getX() < d.getX1()) && d.getY() < d.getY1())
					g.drawOval(d.getX(), d.getY(), d.getX1()-d.getX(), d.getY1()-d.getY());
				
				//������ ��
				else if ((d.getX() < d.getX1()) && (d.getY() > d.getY1())) 
					g.drawOval(d.getX(), d.getY1(), d.getX1()-d.getX(), Math.abs(d.getY1()-d.getY()));
				//���� ��
				else if ((d.getX() > d.getX1()) && (d.getY() > d.getY1())) 
					g.drawOval(d.getX1(), d.getY1(), d.getX()-d.getX1(), d.getY()-d.getY1());
				//���� �Ʒ�						
				else
					g.drawOval(d.getX1(), d.getY(), d.getX()-d.getX1(), d.getY1()-d.getY());						
				
			}
			else if(d.getDist() == 3) {	//RECT
				
				//������ �Ʒ�
				if((d.getX() < d.getX1()) && d.getY() < d.getY1())
					g.drawRect(d.getX(), d.getY(), d.getX1()-d.getX(), d.getY1()-d.getY());
				
				//������ ��
				else if ((d.getX() < d.getX1()) && (d.getY() > d.getY1())) 
					g.drawRect(d.getX(), d.getY1(), d.getX1()-d.getX(), Math.abs(d.getY1()-d.getY()));
				//���� ��
				else if ((d.getX() > d.getX1()) && (d.getY() > d.getY1())) 
					g.drawRect(d.getX1(), d.getY1(), d.getX()-d.getX1(), d.getY()-d.getY1());
				//���� �Ʒ�						
				else
					g.drawRect(d.getX1(), d.getY(), d.getX()-d.getX1(), d.getY1()-d.getY());

			}
		}
	
	// ���콺�� ������ �������� 
		if(line.getState() == true) {  //������ üũ�ϸ�
			g.drawLine(x, y, x1, y1);  //x,y��ǥ���� x1,y1��ǥ�� ������ �׷���
		}
		else if(oval.getState() == true){  //oval�� üũ�ϸ�

			//������ �Ʒ�
			if((x < x1) && y < y1)
				g.drawOval(x, y, x1-x, y1-y);
			
			//������ ��
			else if ((x < x1) && (y > y1)) 
				g.drawOval(x, y1, x1-x, Math.abs(y1-y));
			//���� ��
			else if ((x > x1) && (y > y1)) 
				g.drawOval(x1, y1, x-x1, y-y1);
			//���� �Ʒ�						
			else
				g.drawOval(x1, y, x-x1, y1-y);
					
		}
		else if(rect.getState() == true) {    //rect�� üũ�ϸ�
			
			//������ �Ʒ�
			if((x < x1) && y < y1)
				g.drawRect(x, y, x1-x, y1-y);
			
			//������ ��
			else if ((x < x1) && (y > y1)) 
				g.drawRect(x, y1, x1-x, Math.abs(y1-y));
			//���� ��
			else if ((x > x1) && (y > y1)) 
				g.drawRect(x1, y1, x-x1, y-y1);
			//���� �Ʒ�						
			else
				g.drawRect(x1, y, x-x1, y1-y);
			
		}
	}
	
	//���õ� �޴��� üũ�ǵ��� �ϴ� �̺�Ʈ
	public void itemStateChanged(ItemEvent e){
		pen.setState(false);
		line.setState(false);
		oval.setState(false);
		rect.setState(false);
		CheckboxMenuItem cb = (CheckboxMenuItem)e.getSource();
		   cb.setState(true);
	}
	
	//���콺 ����� ���� �޼ҵ�
	public void mouseClicked(MouseEvent e) {}
	public void mousePressed(MouseEvent e) { //��������
		x = e.getX();       //x�� ��ǥ���� ����
		y = e.getY();       //y�� ��ǥ���� ����
	}
	
	public void mouseReleased(MouseEvent e) { //��������
		x1 = e.getX();   //x1�� ��ǥ��
		y1 = e.getY();   //y1�� ��ǥ��
		this.repaint();  //�׸��� �ٽ� �׸���
	
		if(pen.getState() != true) {  //pen�� true�� �ƴҶ����� �Ʒ��� �����϶�
			int dist = 0;
			if(line.getState() == true) dist =1; // line�� üũ�Ǹ� 1���� ����
			else if(oval.getState() == true) dist = 2; // ovaldl üũ�Ǹ� 2���� ����
			else if(rect.getState()== true) dist = 3; // rect�� üũ�Ǹ� 3���� ����
			Draw d = new Draw();  //d ��ü����
			d.setDist(dist);  //dist �� ����
	
		 //������ �� ����
			d.setX(x);  
			d.setY(y);
			d.setX1(x1);
			d.setY1(y1);
			d.setC(colorselect);		//������ ���� �� ����
			vc.add(d); //vc�� ���� �����϶�
		}
	}
	
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	
	//���콺�� �����̴� ���� �׷����� ��� ���̱�
	public void mouseDragged(MouseEvent e) {
		x1 = e.getX();
		y1 = e.getY();
		this.repaint(); //�����̴� ���� ��������
		// pen �׷����� �̺�Ʈ
		if(pen.getState()) {  
		 Draw d = new Draw();  
		 d.setDist(1);
		 d.setX(x);
		 d.setY(y);
		 d.setX1(x1);
		 d.setY1(y1);
		 d.setC(colorselect);		//������ ���� �� ����
		 vc.add(d);
		 x = x1;
		 y = y1;
		}
	}

	public void mouseMoved(MouseEvent e) {}

}//class end



public class DrawingPannel {
	public static void main(String[] args){
		new PicturePanel("������ �׸���"); // �޴�����
	}
}