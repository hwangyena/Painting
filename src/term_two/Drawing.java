package term_two;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Map;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.filechooser.FileNameExtensionFilter;

//�׸��׸� �� ��ġ
class DrawPoint implements Serializable {

	private int x, y, x1, y1;
	private int dist;
	public int getDist() {return dist;}
	public void setDist(int dist) {this.dist = dist;}
	
	private int paint_set;		//����Ʈ ���� �ߴ���
	public int getPaint()	{return paint_set;}
	public void setPaint(int pa) {this.paint_set = pa;}
	
	
	public int getX() {return x;}
	public void setX(int x) {this.x = x;}
	public int getY() {return y;}
	public void setY(int y) {this.y = y;}
	public int getX1() {return x1;}
	public void setX1(int x1) {this.x1 = x1;}
	public int getY1() {return y1;}
	public void setY1(int y1) {this.y1 = y1;}

	//color��_����
	private Color color;
	public void setC(Color c) {this.color = c;}
	public Color getC() {return color;}

	//������
	private int lineWeight;		
	public int getLineWeight() {return lineWeight;}//lineWeight �� �������� ����
	public void setLineWeight(int lineWeight) {this.lineWeight=lineWeight;}//lineWeight �� �����ϱ� ����

	//�ؽ�Ʈ
	private String f_font;		 //���� ��� ����
	private int f_shape;			 //���� Ư¡ ����
	private int f_size;	 		 //���� ũ�� ����
	private int f_under;			 //������ ���õ� ���·� �׷��� �ؽ�Ʈ����
	private String str;      //���ڿ� ����

	public String getF1() {return f_font;}//f1 �� �������� ����
	public void setF1(String f1) {this.f_font = f1;}//f1 �� �����ϱ� ����
	public int getF2() {return f_shape;}//f2 �� �������� ����
	public void setF2(int f2) {this.f_shape = f2;}//f2 �� �����ϱ� ����
	public int getF3() {return f_size;}//f3 �� �������� ����
	public void setF3(int f3) {this.f_size = f3;}//f3 �� �����ϱ� ����
	public int getUL() {return f_under;}//ul �� �������� ����
	public void setUL(int ul) {this.f_under = ul;}//ul �� �����ϱ� ����
	public String getStr() {return str;}//str �� �������� ����
	public void setStr(String str) {this.str = str;}//str �����ϱ� ����

}

public class Drawing extends JFrame {
	Container contentPane;
	
	private int x, y, x1, y1; //���콺�� ���������� ������ �� ��ǥ��
	private Vector vc = new Vector();
	
	//�����гΰ� �����г� ������
	public int tool_Selected = 0;
	public int shape_Selected = 0;
		
	
	//���� ���õ� ���� �� ���� ����
	public int what_Select=0;
	
	//�׸� �׸��� �г�
	PaintPanel drawPanel = new PaintPanel();
	
	//�г� ����&����
	public int panel_Width = drawPanel.getWidth();
	public int panel_Height = drawPanel.getHeight();
	
	//���� ���õ� ���� ����
	public Color colorselect_left;	//����
	public Color colorselect_right;	//������
	
	public Color colorselect;

	//���ο� ���� ��ư �߰�
	public int new_btn=20;

	//�޴� �������Ϸ� ��µ� ���̺� ����
	JLabel imageLabel = new JLabel();

	//���� ��� �� ����
	private String savePath = new String(); //�������� ���
	private String openFilePath = new String(); //�������� ���
	private String filt;      //���� ����

	//���ݱ��� �׸��� ����Ǿ�����
	private int saveStatus = 0;

	//�� ä��� 
	public int if_any_shape=0;		//����Ʈ ���� �� �ٸ� ���� ���õǸ� 1, �ƴϸ� 0
	public int if_paint = 0;		//����Ʈ ���� �� 1, �ƴ� �� 0
	//������
	public int lineWeight = 2;		
	
	//��Ʈ ����Ʈ
	private String fL[];

	//������ ��Ʈ
	public String font_select;
	//������ ũ��
	public String font_size;
	public int font_size_int;	//int��

	//��Ʈ ����
	public int what_font = 0;
	public int under_line = 0;	//����
	
	//Ȯ�� Ƚ��
	public int count_plus = 0;
	
	//��üȭ��
	Window window;
	JPanel windowFullScreen;
	
	//���� ����
	JTabbedPane pane = new JTabbedPane();
	
	//ctrlŰ Ȱ��ȭ
	public int ctrl = 0;		
	
	//����Ʈ �̹��� ����
	public Image printImage; 
	
	public Drawing() {
		setTitle("������ �׸���");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


		contentPane = getContentPane();
				
		// window�� X��ư�� ������ window�� �����϶�
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
	
		});

		pane.addTab("Ȩ", homePanel());
		pane.addTab("����", editPanel());
		
		//�׸��׸��� �г� ����		
		drawPanel.setBounds(0,110,800,500);
		drawPanel.setBackground(Color.WHITE);
		drawPanel.add(imageLabel);

		//����Ʈ�ҿ� �߰�
		contentPane.add(drawPanel);	//�׸��׸��� �г�
		contentPane.add(pane);		//����		
	
		//�޴� ����
		createMenu();

		//�޴� Ű Ȱ��ȭ
		contentPane.addKeyListener(new MenuKey());
		pane.addKeyListener(new MenuKey());
		drawPanel.addKeyListener(new MenuKey());
		
		setSize(800, 600);
		setVisible(true);
	}
	
	//***********************************************************
	//�޴� Ű �����ư
	class MenuKey extends KeyAdapter{
		public void keyPressed(KeyEvent e) {				
			//ctrlŰ
			if(e.getKeyCode() == KeyEvent.VK_CONTROL) 
				ctrl = 1;
					
			//ctrl+s -> ����
			if(e.getKeyCode() == KeyEvent.VK_S && ctrl == 1) {
				MenuActionListener me = new MenuActionListener();
				me.fileSave(0);//���� ����
						
				saveStatus = 0;//���� �Ϸ�
			}
					
			//ctrl+n -> ���θ����
			else if(e.getKeyCode() == KeyEvent.VK_N && ctrl == 1) {
				MenuActionListener me = new MenuActionListener();
				if(saveStatus == 1) {//���� �׷����Ҵ� panel��������
					int i = me.save_Ask();
					if(i == 2){
						vc.removeAllElements();
						vc.clear();
					}
					else if(i==0 && saveStatus==0){// �ƹ��͵� �׷��� ���� ������ �ٷ� ����
						vc.removeAllElements();
						vc.clear();
					}
				}
			}
			
			//ctrl+o -> ����
			else if(e.getKeyCode() == KeyEvent.VK_O && ctrl == 1) {
				MenuActionListener me = new MenuActionListener();
				JFileChooser chooser = new JFileChooser();
				
				FileNameExtensionFilter filter = new FileNameExtensionFilter(
						"JPG & GIF Images",	//���� �̸� ���� ��µ� ���ڿ�
						"jpg", "gif");		//���� ���ͷ� ���Ǵ� Ȯ����
					
				chooser.setFileFilter(filter); 	//���� ���̾�α׿� ���� ���� ����
					
				int ret = chooser.showOpenDialog(null);	//���� ���̾�α� ���
					
				//���� ���� ���
				if (ret != JFileChooser.APPROVE_OPTION) {
					JOptionPane.showMessageDialog(null, "������ �������� �ʾҽ��ϴ�", "���", JOptionPane.WARNING_MESSAGE);
					return;
				}

				if(saveStatus == 0) {	//�ƹ� �׸��� �ȱ׸� ���
					String filePath = chooser.getSelectedFile().getPath();	//���� ��θ� ����
					imageLabel.setIcon(new ImageIcon(filePath));//�̹��� ���
				}
				else {	//�׷��� �ִ� ���
						vc.removeAllElements();//���Ϳ�� ��ü ����
								
						if(me.save_Ask() == 2) {	//���� ���� ���� ���
							//����ڰ� ������ �����ϰ� "����"�� ���� ���
							drawPanel.setForeground(Color.WHITE);
							String filePath = chooser.getSelectedFile().getPath();	//���� ��θ� ����
							imageLabel.setIcon(new ImageIcon(filePath));//�̹��� ���
						}
								
						else {		//���� ���� �ϴ� ���
							me.fileSave(0);
							drawPanel.setBackground(Color.WHITE);	//���� ����
							String filePath = chooser.getSelectedFile().getPath();	//���� ��θ� ����
							imageLabel.setIcon(new ImageIcon(filePath));//�̹��� ���
							repaint();
						}
					}
					saveStatus = 1;
				}
					
				//ctrl+x -> ����
			else if(e.getKeyCode() == KeyEvent.VK_X && ctrl == 1) {
					MenuActionListener me = new MenuActionListener();
					if(saveStatus == 1) {//���� �׷����Ҵ� panel��������
						int i = me.save_Ask();
						if(i == 2)
							System.exit(0);
						else if(i==0 && saveStatus==0)// �ƹ��͵� �׷��� ���� ������ �ٷ� ����
							System.exit(0);
					}
					else//��� ����
						System.exit(0);
			}
			
			//ctrl+p -> �μ�
			else if(e.getKeyCode() == KeyEvent.VK_P && ctrl == 1) {
				File temp=  new File("temp.jpg"); //����Ʈ �� �̹��� ������ ���� �ӽ� �̹��� ����
		        
				BufferedImage image = new BufferedImage(drawPanel.getWidth(),
						drawPanel.getHeight(),BufferedImage.TYPE_USHORT_565_RGB);
				
		         try{
		             ImageIO.write(image, "png", temp); 
		          }catch(Exception ee){
		             ee.printStackTrace();
		          }
		         
		       //������ �̹����� �̹��� �����ܰ�ü�� ����
				ImageIcon temp2= new ImageIcon("temp.jpg");      
				printImage= temp2.getImage();               //������ �̹��� �ҷ�����
				Paper p= new Paper();
				PageFormat format = new PageFormat();         //�� PageFormat �ν��Ͻ��� ��������� �⺻ ũ��� �������� ����
				format.setPaper(p);  //���������� ���������������� ���ڷ�
				PrinterJob pj = PrinterJob.getPrinterJob();     //����Ʈ�� ��Ʈ�����ִ� ��ü
				pj.setPrintable(new MyPrintable(), format);     //Ŭ������ ȣ���Ͽ� �۾��� �����ϰ� ���������� ����ڿ� �μ� ��ȭ ���ڸ� ȣ�� �� ���� �۾� �������� �μ�
				try{
					pj.print();                           //����Ʈ
				}catch(PrinterException pe){
					System.out.println("Printingfailed : "+pe.getMessage());
				}
			}
			
				drawPanel.repaint();
			}
			public void keyReleased(KeyEvent e) {
				//��Ʈ���� ���� -> ctrl=0
				if(e.getKeyCode() == KeyEvent.VK_CONTROL) {
					ctrl = 0;
				}
			}
		}

	//***********************************************
	//�׸� �׸��� �г� Ŭ����
	class PaintPanel extends JPanel {
		public int right=0;
		public int left=0;
		
		//�׸� �׸��� �޼ҵ�
		public void paint(Graphics gg) {		//�� ���⸦ ���� Graphics2D�� �ٲٱ�!!!!!
			super.paint(gg);
			Graphics2D g = (Graphics2D)gg;
			
			
			//�׸��׸���
			for(int i = 0; i < vc.size(); ++i) {
				DrawPoint d = (DrawPoint)vc.elementAt(i);
				
				g.setColor(d.getC());		//color ����				
				g.setStroke(new BasicStroke(d.getLineWeight()));//��ü���� ���� �β� ��������
				
				if(d.getDist() == 1) {	//��
					g.drawLine(d.getX(), d.getY(), d.getX1(), d.getY1());
				}
				
				else if(d.getDist() == 9) { //�ؽ�Ʈ
					Font font = new Font(d.getF1(),d.getF2(),d.getF3());
					if(d.getUL()==1) {//�ؽ�Ʈ�� ���ٱ߱Ⱑ üũ�Ǿ�������
						Map attributes = font.getAttributes();
						attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
						g.setFont(font.deriveFont(attributes));
					}
					else
						g.setFont(font);
					g.drawString(d.getStr(), d.getX(), d.getY());
				}
				
				//ä��� �� ���ý�
				else if(d.getPaint() == 0) {
					if(d.getDist() == 2) {	//OVAL
						
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
					else if(d.getDist() == 3) { //Rect
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
					else if(d.getDist() == 4) {	//RoundRECT
						//������ �Ʒ�
						if((d.getX() < d.getX1()) && d.getY() < d.getY1())
							g.drawRoundRect(d.getX(), d.getY(), d.getX1()-d.getX(), d.getY1()-d.getY(), 40, 60);
						
						//������ ��
						else if ((d.getX() < d.getX1()) && (d.getY() > d.getY1())) 
							g.drawRoundRect(d.getX(), d.getY1(), d.getX1()-d.getX(), Math.abs(d.getY1()-d.getY()), 40, 60);
						//���� ��
						else if ((d.getX() > d.getX1()) && (d.getY() > d.getY1())) 
							g.drawRoundRect(d.getX1(), d.getY1(), d.getX()-d.getX1(), d.getY()-d.getY1(),40,60);
						//���� �Ʒ�						
						else
							g.drawRoundRect(d.getX1(), d.getY(), d.getX()-d.getX1(), d.getY1()-d.getY(),40,60);
					}
					else if(d.getDist() == 5) {	//�̵ �ﰢ��
						int [] x = {d.getX(),(d.getX()+d.getX1())/2,d.getX1()};
						int [] y = {d.getY()>d.getY1()?d.getY():d.getY1(),
									   d.getY()>d.getY1()?d.getY1():d.getY(),
								       d.getY()>d.getY1()?d.getY():d.getY1()};
						g.drawPolygon(x,y,3);
					}
					else if(d.getDist() == 6) { //���� �ﰢ��
						int [] x = {d.getX()>d.getX1()?d.getX1():d.getX(),
								   d.getX()>d.getX1()?d.getX1():d.getX(),
							       d.getX()>d.getX1()?d.getX():d.getX1()};
						int [] y = {d.getY()>d.getY1()?d.getY1():d.getY(),
							   	   d.getY()>d.getY1()?d.getY():d.getY1(),
								   d.getY()>d.getY1()?d.getY():d.getY1()};
						g.drawPolygon(x,y,3);
					}
					else if(d.getDist() == 7) { //������
						int [] x = {(d.getX1()-d.getX())*2/9+d.getX(),(d.getX1()-d.getX())*7/9+d.getX(),d.getX1(),(d.getX1()+d.getX())/2,d.getX()};
						int [] y = {d.getY()>d.getY1()?d.getY():d.getY1(),d.getY()>d.getY1()?d.getY():d.getY1(),
								d.getY()>d.getY1()?(d.getY()-d.getY1())/3+d.getY1():(d.getY1()-d.getY())/3+d.getY(),
				    		           d.getY()>d.getY1()?d.getY1():d.getY(),d.getY()>d.getY1()?
				    		        		   (d.getY()-d.getY1())/3+d.getY1():(d.getY1()-d.getY())/3+d.getY()};
						g.drawPolygon(x,y,5);
					}
				}
				//ä��� ���� ��
				else if(d.getPaint() == 1) {	
					if(d.getDist() == 2) {	//OVAL
						
						//������ �Ʒ�
						if((d.getX() < d.getX1()) && d.getY() < d.getY1())
							g.fillOval(d.getX(), d.getY(), d.getX1()-d.getX(), d.getY1()-d.getY());
						
						//������ ��
						else if ((d.getX() < d.getX1()) && (d.getY() > d.getY1())) 
							g.fillOval(d.getX(), d.getY1(), d.getX1()-d.getX(), Math.abs(d.getY1()-d.getY()));
						//���� ��
						else if ((d.getX() > d.getX1()) && (d.getY() > d.getY1())) 
							g.fillOval(d.getX1(), d.getY1(), d.getX()-d.getX1(), d.getY()-d.getY1());
						//���� �Ʒ�						
						else
							g.fillOval(d.getX1(), d.getY(), d.getX()-d.getX1(), d.getY1()-d.getY());						
						
					}
					else if(d.getDist() == 3) { //Rect
						//������ �Ʒ�
						if((d.getX() < d.getX1()) && d.getY() < d.getY1())
							g.fillRect(d.getX(), d.getY(), d.getX1()-d.getX(), d.getY1()-d.getY());
						
						//������ ��
						else if ((d.getX() < d.getX1()) && (d.getY() > d.getY1())) 
							g.fillRect(d.getX(), d.getY1(), d.getX1()-d.getX(), Math.abs(d.getY1()-d.getY()));
						//���� ��
						else if ((d.getX() > d.getX1()) && (d.getY() > d.getY1())) 
							g.fillRect(d.getX1(), d.getY1(), d.getX()-d.getX1(), d.getY()-d.getY1());
						//���� �Ʒ�						
						else
							g.fillRect(d.getX1(), d.getY(), d.getX()-d.getX1(), d.getY1()-d.getY());
	
					}
					else if(d.getDist() == 4) {	//RoundRECT
						//������ �Ʒ�
						if((d.getX() < d.getX1()) && d.getY() < d.getY1())
							g.fillRoundRect(d.getX(), d.getY(), d.getX1()-d.getX(), d.getY1()-d.getY(), 40, 60);
						
						//������ ��
						else if ((d.getX() < d.getX1()) && (d.getY() > d.getY1())) 
							g.fillRoundRect(d.getX(), d.getY1(), d.getX1()-d.getX(), Math.abs(d.getY1()-d.getY()), 40, 60);
						//���� ��
						else if ((d.getX() > d.getX1()) && (d.getY() > d.getY1())) 
							g.fillRoundRect(d.getX1(), d.getY1(), d.getX()-d.getX1(), d.getY()-d.getY1(),40,60);
						//���� �Ʒ�						
						else
							g.fillRoundRect(d.getX1(), d.getY(), d.getX()-d.getX1(), d.getY1()-d.getY(),40,60);
					}
					else if(d.getDist() == 5) {	//�̵ �ﰢ��
						int [] x = {d.getX(),(d.getX()+d.getX1())/2,d.getX1()};
						int [] y = {d.getY()>d.getY1()?d.getY():d.getY1(),
									   d.getY()>d.getY1()?d.getY1():d.getY(),
								       d.getY()>d.getY1()?d.getY():d.getY1()};
						g.fillPolygon(x,y,3);
					}
					else if(d.getDist() == 6) { //���� �ﰢ��
						int [] x = {d.getX()>d.getX1()?d.getX1():d.getX(),
								   d.getX()>d.getX1()?d.getX1():d.getX(),
							       d.getX()>d.getX1()?d.getX():d.getX1()};
						int [] y = {d.getY()>d.getY1()?d.getY1():d.getY(),
							   	   d.getY()>d.getY1()?d.getY():d.getY1(),
								   d.getY()>d.getY1()?d.getY():d.getY1()};
						g.fillPolygon(x,y,3);
					}
					else if(d.getDist() == 7) { //������
						int [] x = {(d.getX1()-d.getX())*2/9+d.getX(),(d.getX1()-d.getX())*7/9+d.getX(),d.getX1(),
								(d.getX1()+d.getX())/2,d.getX()};
						int [] y = {d.getY()>d.getY1()?d.getY():d.getY1(),d.getY()>d.getY1()?d.getY():
							d.getY1(),d.getY()>d.getY1()?(d.getY()-d.getY1())/3+d.getY1():(d.getY1()-d.getY())/3+d.getY(),
				    		           d.getY()>d.getY1()?d.getY1():d.getY(),d.getY()>d.getY1()?(d.getY()-d.getY1())/3+d.getY1():
				    		        	   (d.getY1()-d.getY())/3+d.getY()};
						g.fillPolygon(x,y,5);
					}
				}
				

			}
		
			// ���콺�� ������ �������� 
			if(what_Select == 1) {  //������ üũ�ϸ�
				g.drawLine(x, y, x1, y1);  //x,y��ǥ���� x1,y1��ǥ�� ������ �׷���
			}

			//ä��� �� ���ý�
			if(if_any_shape == 0) {
				if(what_Select == 2){  //oval�� üũ�ϸ�
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
				else if(what_Select == 3) {    //rect�� üũ�ϸ�
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
				else if(what_Select == 4) {	//roundrect�� üũ�ϸ�
					//������ �Ʒ�
					if((x < x1) && y < y1)
						g.drawRoundRect(x, y, x1-x, y1-y, 40, 60);
								
					//������ ��
					else if ((x < x1) && (y > y1)) 
						g.drawRoundRect(x, y1, x1-x, Math.abs(y1-y), 40, 60);
					
					//���� ��
					else if ((x > x1) && (y > y1)) 
						g.drawRoundRect(x1, y1, x-x1, y-y1, 40, 60);
					//���� �Ʒ�						
					else
						g.drawRoundRect(x1, y, x-x1, y1-y, 40, 60);
		
				}
				else if(what_Select == 5) { //�̵ �ﰢ���� üũ�ϸ�
					g.drawPolygon(new int[] {x,(x+x1)/2,x1},
						 new int[] {y>y1?y:y1,y>y1?y1:y,y>y1?y:y1}, 3);
	
				}
				else if(what_Select == 6) { //���� �ﰢ���� üũ�ϸ�
					g.drawPolygon(new int[] {x>x1?x1:x,x>x1?x1:x,x>x1?x:x1},
							  new int[] {y>y1?y1:y,y>y1?y:y1,y>y1?y:y1}, 3);
		
				}
				else if(what_Select == 7) {	//�������� üũ�ϸ�
					g.drawPolygon(new int[] {(x1-x)*2/9+x,(x1-x)*7/9+x,x1,(x1+x)/2,x},
						      new int[] {y>y1?y:y1,y>y1?y:y1,y>y1?(y-y1)/3+y1:(y1-y)/3+y,
						    		     y>y1?y1:y,y>y1?(y-y1)/3+y1:(y1-y)/3+y},5);
				}
			}
			
			//�� ä��� ���� ��
			else if(if_any_shape == 1) {
				if(what_Select == 2){  //oval�� üũ�ϸ�
					//������ �Ʒ�
					if((x < x1) && y < y1)
						g.fillOval(x, y, x1-x, y1-y);
								
					//������ ��
					else if ((x < x1) && (y > y1)) 
						g.fillOval(x, y1, x1-x, Math.abs(y1-y));
					
					//���� ��
					else if ((x > x1) && (y > y1)) 
						g.fillOval(x1, y1, x-x1, y-y1);
					//���� �Ʒ�						
					else
						g.fillOval(x1, y, x-x1, y1-y);
				}
				else if(what_Select == 3) {    //rect�� üũ�ϸ�
					//������ �Ʒ�
					if((x < x1) && y < y1)
						g.fillRect(x, y, x1-x, y1-y);
								
					//������ ��
					else if ((x < x1) && (y > y1)) 
						g.fillRect(x, y1, x1-x, Math.abs(y1-y));
					
					//���� ��
					else if ((x > x1) && (y > y1)) 
						g.fillRect(x1, y1, x-x1, y-y1);
					//���� �Ʒ�						
					else
						g.fillRect(x1, y, x-x1, y1-y);
				}
				else if(what_Select == 4) {	//roundrect�� üũ�ϸ�
					//������ �Ʒ�
					if((x < x1) && y < y1)
						g.fillRoundRect(x, y, x1-x, y1-y, 40, 60);
								
					//������ ��
					else if ((x < x1) && (y > y1)) 
						g.fillRoundRect(x, y1, x1-x, Math.abs(y1-y), 40, 60);
					
					//���� ��
					else if ((x > x1) && (y > y1)) 
						g.fillRoundRect(x1, y1, x-x1, y-y1, 40, 60);
					//���� �Ʒ�						
					else
						g.fillRoundRect(x1, y, x-x1, y1-y, 40, 60);
		
				}
				else if(what_Select == 5) { //�̵ �ﰢ���� üũ�ϸ�
					g.fillPolygon(new int[] {x,(x+x1)/2,x1},
						 new int[] {y>y1?y:y1,y>y1?y1:y,y>y1?y:y1}, 3);
	
				}
				else if(what_Select == 6) { //���� �ﰢ���� üũ�ϸ�
					g.fillPolygon(new int[] {x>x1?x1:x,x>x1?x1:x,x>x1?x:x1},
							  new int[] {y>y1?y1:y,y>y1?y:y1,y>y1?y:y1}, 3);
		
				}
				else if(what_Select == 7) {	//�������� üũ�ϸ�
					g.fillPolygon(new int[] {(x1-x)*2/9+x,(x1-x)*7/9+x,x1,(x1+x)/2,x},
						      new int[] {y>y1?y:y1,y>y1?y:y1,y>y1?(y-y1)/3+y1:(y1-y)/3+y,
						    		     y>y1?y1:y,y>y1?(y-y1)/3+y1:(y1-y)/3+y},5);
				}
			}		
		}
		
		public PaintPanel() {
			//���콺 ��� ��
			addMouseListener(new MouseAdapter() {

				//���콺 ������ ��
				public void mousePressed(MouseEvent e) {				
					x = e.getX();       //x�� ��ǥ��
					y = e.getY();       //y�� ��ǥ��

					DrawPoint d = new DrawPoint();	//��ü ����
					
					saveStatus = 1;
					if(e.getButton()==MouseEvent.BUTTON3) {	//������ ���콺
						right = 1;
						left = 0;
					}else {				//���� ���콺	
						left = 1;
						right = 0;
					}
					
					//�ؽ�Ʈ ���� ��
					if(what_Select == 9) {
						 String str = JOptionPane.showInputDialog("�Է�");
						 if(str!=null) {
							
							 //������ �� ����
							 d.setX(x);
							 d.setY(y);
							 d.setStr(str);
							 d.setDist(9);
							 d.setC(colorselect_left);
							 d.setF1(font_select);//�޺��ڽ����� ������ �۾�ü ��������
							 switch(what_font) {//������ ������ư�� ���� ���� ��Ÿ�� ����
								 case 0 : d.setF2(Font.PLAIN);
								 	break;
								 case 1 : d.setF2(Font.BOLD);
								 	break;
								 case 2 : d.setF2(Font.ITALIC);
								 	break;
								 case 3 : d.setF2(Font.BOLD | Font.ITALIC);
								 	break;
							 }
							 
							 //���� ���ý�
							 if(under_line == 1) {
								 d.setUL(1);
							 }
							 
							 //�ؽ�Ʈ�ʵ忡�� ����ũ�� ��������(Ȯ��� ���¿��� �Է� �� �� ũ��)
							 d.setF3(font_size_int);
							 vc.add(d); //vc�� ���� �����϶�
							 repaint();
						 }
					}
				}
					
				//���콺 ������ ��
				public void mouseReleased(MouseEvent e) { 
					x1 = e.getX();   //x1�� ��ǥ��
					y1 = e.getY();   //y1�� ��ǥ��
					repaint();  //�׸��� �ٽ� �׸���	
					
					//pen�� true�� �ƴҶ����� �Ʒ��� �����϶�
					if(what_Select != 0) {  
						
						int dist = 0;
						if(what_Select == 1) dist =1; 			// line�� üũ�Ǹ� 1���� ����
						else if(what_Select == 2) dist = 2; 	// oval�� üũ�Ǹ� 2���� ����
						else if(what_Select == 3) dist = 3; 	// rect�� üũ�Ǹ� 3���� ����
						else if(what_Select == 4) dist = 4;		// roundrect
						else if(what_Select == 5) dist = 5;		// �̵�ﰢ��
						else if(what_Select == 6) dist = 6;		// �����ﰢ��
						else if(what_Select == 7) dist = 7;		// ������
						else if(what_Select == 9) dist = 9;		// �ؽ�Ʈ
						else if(what_Select == 10)dist = 10;	// ���찳
						
						DrawPoint dt = new DrawPoint(); 		 	//d ��ü����
						dt.setDist(dist); 					 	//dist �� ����
						
					 //������ �� ����
						dt.setX(x);  
						dt.setY(y);
						dt.setX1(x1);
						dt.setY1(y1);
						
						//������ ���� �� ����
						if(e.getButton()==MouseEvent.BUTTON3) {	//������ ���콺
							dt.setC(colorselect_right);
						}else {					//���� ���콺	
							dt.setC(colorselect_left);
						}
						
						dt.setLineWeight(lineWeight);	//���� ����
						
						//��ä��� ���� ��
						if(if_paint == 1) {
							dt.setPaint(1);	//�� ä��� ����
							//���� �̼��ý� -> ����� �ش� �������� ����
							if(what_Select == 0) {
								if_any_shape = 0;
							}
							//���� ���ý� -> �Լ��� �� ä���� ���� �׷���
							else {
								if_any_shape = 1;
							}
						}
						
						else {
							dt.setPaint(0);		//�� ä��� �̼���
						}
						
						vc.add(dt); //vc�� ���� �����϶�
					}
					
					
				}
				
				
			});
			
			addMouseMotionListener(new MouseAdapter() {
				
				//���콺�� �����̴� ���� �׷����� ��� ���̱�
				public void mouseDragged(MouseEvent e) {
					x1 = e.getX();
					y1 = e.getY();
					repaint(); //�����̴� ���� ��������
					
					// pen �׷����� �̺�Ʈ
					if(what_Select == 0) {  
						DrawPoint d = new DrawPoint();  
						d.setDist(1);
						d.setLineWeight(lineWeight); 
						d.setX(x);
						d.setY(y);
						d.setX1(x1);
						d.setY1(y1);
						
						//������ ���� �� ����
						if(right == 1) {	//������ ���콺
							d.setC(colorselect_right);
						}else {					//���� ���콺
							d.setC(colorselect_left);
						}
						
						//ä��� ���� �� ���� ���� -> ��� �� ����� ���� �ٲٱ�
						if(if_paint == 1) {
							vc.removeAllElements();	//���� ��ü��� ����
							drawPanel.setBackground(d.getC());
						}
						
						vc.add(d);
						x = x1;
						y = y1;

					}
					
					//���찳 �׷����� �̺�Ʈ
					else if(what_Select == 10) {
						DrawPoint d = new DrawPoint();  
						d.setLineWeight(lineWeight);
						d.setDist(1);
						d.setX(x);
						d.setY(y);
						d.setX1(x1);
						d.setY1(y1);
						d.setC(colorselect_right);
						vc.add(d);
						x = x1;
						y = y1;
					}
				}
			});
		}
	}

	//***********************************************************
	
	
	//�޴� �����
	void createMenu() {
		JMenuBar mb = new JMenuBar();
		JMenu file = new JMenu("����");
		this.setJMenuBar(mb);
		

		mb.add(file);
		
		JMenuItem new_file = new JMenuItem("���θ����(N)");
		new_file.addActionListener(new MenuActionListener());
		file.add(new_file);
		
		JMenuItem open_file = new JMenuItem("����(O)");
		open_file.addActionListener(new MenuActionListener());
		file.add(open_file);
		
		JMenuItem save_file = new JMenuItem("����(S)");
		save_file.addActionListener(new MenuActionListener());
		file.add(save_file);
		
		JMenuItem saveother_file = new JMenuItem("�ٸ� �̸����� ����");
		saveother_file.addActionListener(new MenuActionListener());
		file.add(saveother_file);
		
		file.addSeparator();	//���м�

		JMenuItem print_file = new JMenuItem("�μ�(P)");
		print_file.addActionListener(new MenuActionListener());
		file.add(print_file);
		
		JMenuItem end_file = new JMenuItem("������(X)");
		end_file.addActionListener(new MenuActionListener());
		file.add(end_file);
		
	}
	
	//�޴� ������ Action������
	class MenuActionListener implements ActionListener{
		JFileChooser chooser = new JFileChooser();
		
		MenuActionListener(){	//���� ���̾�α� ����
			chooser = new JFileChooser();
		}
		
		//�׼� ������
		public void actionPerformed(ActionEvent e) {
			String cmd = e.getActionCommand();	//�޴��������� ���ڿ� ����
			
			//���θ����
			if(cmd.equals("���θ����(N)")) {
				if(saveStatus == 0) {
					vc.removeAllElements();
					vc.clear();
					repaint();	
				}
				else {
					if(save_Ask() == 2) {	//���� ���� ���� ���
						vc.removeAllElements();
						vc.clear();
						repaint();
					}
					else {	//���� ���� �ϴ� ���
						fileSave(0);
						vc.removeAllElements();
						vc.clear();
						repaint();					
					}

				}
			}
			
			//����
			else if(cmd.equals("����(O)")) {	
				FileNameExtensionFilter filter = new FileNameExtensionFilter(
					"JPG & GIF Images",	//���� �̸� ���� ��µ� ���ڿ�
					"jpg", "gif");		//���� ���ͷ� ���Ǵ� Ȯ����
				
				chooser.setFileFilter(filter); 	//���� ���̾�α׿� ���� ���� ����
				
				int ret = chooser.showOpenDialog(null);	//���� ���̾�α� ���
				
				//���� ���� ���
				if (ret != JFileChooser.APPROVE_OPTION) {
					JOptionPane.showMessageDialog(null, "������ �������� �ʾҽ��ϴ�", "���", JOptionPane.WARNING_MESSAGE);
					return;
				}

				if(saveStatus == 0) {	//�ƹ� �׸��� �ȱ׸� ���
					String filePath = chooser.getSelectedFile().getPath();	//���� ��θ� ����
					imageLabel.setIcon(new ImageIcon(filePath));//�̹��� ���
				}
				else {	//�׷��� �ִ� ���
					vc.removeAllElements();//���Ϳ�� ��ü ����
					
					if(save_Ask() == 2) {	//���� ���� ���� ���
						//����ڰ� ������ �����ϰ� "����"�� ���� ���
						drawPanel.setForeground(Color.WHITE);
						String filePath = chooser.getSelectedFile().getPath();	//���� ��θ� ����
						imageLabel.setIcon(new ImageIcon(filePath));//�̹��� ���
					}
					
					else {		//���� ���� �ϴ� ���
						this.fileSave(0);
						drawPanel.setBackground(Color.WHITE);	//���� ����
						String filePath = chooser.getSelectedFile().getPath();	//���� ��θ� ����
						imageLabel.setIcon(new ImageIcon(filePath));//�̹��� ���
						repaint();
					}
				}
				saveStatus = 1;
			}
			
			//����
			else if(cmd.equals("����(S)")) {
				this.fileSave(0);
			}
			
			//�ٸ� �̸����� ����
			else if(cmd.equals("�ٸ� �̸����� ����")) {
				this.fileSave(1);
			}
			
			//�μ�
			else if(cmd.equals("�μ�(P)")) {
				File temp=  new File("temp.jpg"); //����Ʈ �� �̹��� ������ ���� �ӽ� �̹��� ����
			        
				BufferedImage image = new BufferedImage(drawPanel.getWidth(),
						drawPanel.getHeight(),BufferedImage.TYPE_USHORT_565_RGB);
				
		         try{
		             ImageIO.write(image, "png", temp); 
		          }catch(Exception ee){
		             ee.printStackTrace();
		          }
		         
		       //������ �̹����� �̹��� �����ܰ�ü�� ����
				ImageIcon temp2= new ImageIcon("temp.jpg");      
				printImage= temp2.getImage();               //������ �̹��� �ҷ�����
				
				Paper p= new Paper();
				PageFormat format = new PageFormat();         //�� PageFormat �ν��Ͻ��� ��������� �⺻ ũ��� �������� ����
				format.setPaper(p);  //���������� ���������������� ���ڷ�
				PrinterJob pj = PrinterJob.getPrinterJob();     //����Ʈ�� ��Ʈ�����ִ� ��ü
				pj.setPrintable(new MyPrintable(), format);     //Ŭ������ ȣ���Ͽ� �۾��� �����ϰ� ���������� ����ڿ� �μ� ��ȭ ���ڸ� ȣ�� �� ���� �۾� �������� �μ�
				try{
					pj.print();                           //����Ʈ
				}catch(PrinterException pe){
					System.out.println("Printingfailed : "+pe.getMessage());
				}
			}
			
			//������
			else if(cmd.equals("������(X)")) {
				if(saveStatus == 0) {
					System.exit(0);
				}
				else {
					if(save_Ask() == 2) {	//���� ���� ���� ���
						System.exit(0);
					}
					else {
						this.fileSave(1);
						System.exit(0);
					}
					
				}
			}
		}

		//���� �����ϴ� �޼ҵ�(save 0 - �׳����� / 1 - �ٸ��̸����� ����
		void fileSave(int save) {
			//�̹��� ���� ����
			BufferedImage image = new BufferedImage(drawPanel.getWidth(),
					drawPanel.getHeight(),BufferedImage.TYPE_USHORT_565_RGB);
	
			//�����̹����� drawPanel ����
			drawPanel.paint(image.getGraphics());
			
			//���� ��ΰ� ��ų� �ٸ��̸����� ������ ��
			if(savePath.length()==0 || save == 1)
				this.fileSave_Fin();
			
			//���� ����
			if(savePath.length()!=0 && filt != null) {
				try {
					ImageIO.write(image, filt, new File(savePath+"."+filt));
					saveStatus = 0;	//���� �Ϸ�
				}catch(IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		
		//�ٸ� �̸����� �����
		void fileSave_Fin() {
			FileNameExtensionFilter filter[] = {
				 new FileNameExtensionFilter("jpg","jpg"),
				 new FileNameExtensionFilter("gif","gif"),
				 new FileNameExtensionFilter("png","png"),
				 new FileNameExtensionFilter("bmp","bmp")};
			
			chooser.resetChoosableFileFilters();//���� �ʱ�ȭ
			for(int i=0;i<4;i++)
				chooser.addChoosableFileFilter(filter[i]);//���� ���� ����

			int ret = chooser.showSaveDialog(null);//���� ���̾� �α� ����

			if(ret!=JFileChooser.APPROVE_OPTION) //���� ���� ��
				return;

			savePath = new String(chooser.getSelectedFile().getPath());
			filt = chooser.getFileFilter().getDescription();//���� ���̾�α׿��� ������ ����
	
			
			File temp = new File(savePath+"."+filt);
			if(temp.exists()) {//�̹� �����ϴ� �����ΰ��
				int r = JOptionPane.showConfirmDialog(null,temp.getName()+
						"��(��) �̹� �����մϴ�. �ٲٽðڽ��ϱ�?","�ٸ� �̸����� ���� Ȯ��",
						JOptionPane.YES_NO_OPTION);
				if(r==JOptionPane.NO_OPTION)
					this.fileSave_Fin();
			}
		}
		//������ ���� ���� �޼ҵ�
		int save_Ask() {
			//Ȯ�� ���̾�α� ����
			int result = JOptionPane.showConfirmDialog(null, "���� ������ �����Ͻðڽ��ϱ�?", "�����׸���", 
					JOptionPane.YES_NO_OPTION);
			
			//�� ���ý�
			if(result == JOptionPane.YES_OPTION) {
				fileSave(0);	//���� ����
				return 0;
			}
			
			//�ݱ� ���ý�
			else if(result == JOptionPane.CLOSED_OPTION) {
				return 1;
			}
			
			//�ƴϿ� ���ý�
			else	return 2;
		}

	}
	
		
	//Ȩ �г�
	public JPanel homePanel() {			
		JPanel mainPanel = new JPanel();
		mainPanel.setBackground(new Color(245, 245, 245));
		
		mainPanel.setLayout(null);
		
		//*********************
		//���� �г�
		JPanel tool = new JPanel();
		tool.setBounds(0, 5, 78, 70);
		tool.setBackground(new Color(245, 245, 245));
		tool.setOpaque(false);
		
		//������ ���� ��ư
		ImageIcon pencil = new ImageIcon("Image/pencil.jpg");
		ImageIcon pai = new ImageIcon("Image/paint.jpg");
		ImageIcon te = new ImageIcon("Image/text.jpg");
		ImageIcon era = new ImageIcon("Image/eraser.jpg");
		
		
		//�̹��� ũ�� ����
		Image one_pen = pencil.getImage();
		Image two_pen = one_pen.getScaledInstance(15,15,java.awt.Image.SCALE_SMOOTH);
		ImageIcon pen = new ImageIcon(two_pen);
		
		Image one_pai = pai.getImage();
		Image two_pai = one_pai.getScaledInstance(15,15,java.awt.Image.SCALE_SMOOTH);
		ImageIcon paint = new ImageIcon(two_pai);
		
		Image one_te = te.getImage();
		Image two_te = one_te.getScaledInstance(15,15,java.awt.Image.SCALE_SMOOTH);
		ImageIcon text = new ImageIcon(two_te);
		
		Image one_era = era.getImage();
		Image two_era = one_era.getScaledInstance(15,15,java.awt.Image.SCALE_SMOOTH);
		ImageIcon eraser = new ImageIcon(two_era);
		
		
		//��ư �׷쿡 �ֱ�
		ButtonGroup gup = new ButtonGroup();
		
		JRadioButton ra_pencil = new JRadioButton(pen);
		JRadioButton ra_paint = new JRadioButton(paint);
		JRadioButton ra_text = new JRadioButton(text);
		JRadioButton ra_eraser = new JRadioButton(eraser);
			
		ra_pencil.setBorderPainted(true);
		ra_paint.setBorderPainted(true);
		ra_text.setBorderPainted(true);
		ra_eraser.setBorderPainted(true);
	
		//�׷쿡 �߰�(����Ʈ ����)
		gup.add(ra_pencil);
		gup.add(ra_text);
		gup.add(ra_eraser);
		ra_pencil.setSelected(true); //�ʱ� ���� ����
		
		//�гο� ���� ��ư �߰�
		tool.add(ra_pencil);
		tool.add(ra_paint);
		tool.add(ra_text);
		tool.add(ra_eraser);
		
		//���� �̺�Ʈ ������
		ra_pencil.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				what_Select = 0;
			}
		});
		
		ra_paint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(ra_paint.isSelected()) {	//����Ʈ ����
					if_paint = 1;
					if(what_Select == 0) {
						if_any_shape = 0;		//���� �̼��ý�(��� ä���)
					}
					else {

						if_any_shape = 1;		//���� ���ý�(���� ä���)
					}
				}
				else {	//����Ʈ ���� ����
					if_paint = 0;
				}
			}
		});
		
		ra_text.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				what_Select = 9;
			}
		});
		
		ra_eraser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				what_Select = 10;
			}
		});
		
		//*********************
		
		//���м� �г�
		JPanel linePanel = new JPanel();
		linePanel.setBackground(new Color(211, 211, 211));
		linePanel.setBounds(75, 2, 1, 78);
		
		
		//*********************
		
		//���� �г�
		JPanel shapePanel = new JPanel();
		shapePanel.setBounds(75, 5, 130, 70);
		shapePanel.setOpaque(false);
		
		//������ ���� ��ư
		ImageIcon li = new ImageIcon("Image/line.jpg");
		ImageIcon cir = new ImageIcon("Image/circle.jpg");
		ImageIcon rec = new ImageIcon("Image/rect.jpg");
		ImageIcon rrec = new ImageIcon("Image/roundrect.jpg");
		ImageIcon tri = new ImageIcon("Image/triangle.jpg");
		ImageIcon poly = new ImageIcon("Image/polygon.jpg");
		ImageIcon fiv = new ImageIcon("Image/fiveangle.jpg");
		
		//�̹��� ũ�� ����
		Image one_li = li.getImage();
		Image two_li = one_li.getScaledInstance(15,15,java.awt.Image.SCALE_SMOOTH);
		ImageIcon line = new ImageIcon(two_li);
		
		Image one_cir = cir.getImage();
		Image two_cir = one_cir.getScaledInstance(15,15,java.awt.Image.SCALE_SMOOTH);
		ImageIcon circle = new ImageIcon(two_cir);
		
		Image one_rec = rec.getImage();
		Image two_rec = one_rec.getScaledInstance(15,15,java.awt.Image.SCALE_SMOOTH);
		ImageIcon rect = new ImageIcon(two_rec);
		
		Image one_rrec = rrec.getImage();
		Image two_rrec = one_rrec.getScaledInstance(15,15,java.awt.Image.SCALE_SMOOTH);
		ImageIcon roundrect = new ImageIcon(two_rrec);
		
		Image one_tri = tri.getImage();
		Image two_tri = one_tri.getScaledInstance(15,15,java.awt.Image.SCALE_SMOOTH);
		ImageIcon triangle = new ImageIcon(two_tri);
		
		Image one_poly = poly.getImage();
		Image two_poly = one_poly.getScaledInstance(15,15,java.awt.Image.SCALE_SMOOTH);
		ImageIcon polygon = new ImageIcon(two_poly);
		
		Image one_fiv = fiv.getImage();
		Image two_fiv = one_fiv.getScaledInstance(15,15,java.awt.Image.SCALE_SMOOTH);
		ImageIcon fiveangle = new ImageIcon(two_fiv);
		
		JRadioButton ra_line = new JRadioButton(line);
		JRadioButton ra_cir = new JRadioButton(circle);
		JRadioButton ra_rec = new JRadioButton(rect);
		JRadioButton ra_rrec = new JRadioButton(roundrect);
		JRadioButton ra_tri = new JRadioButton(triangle);
		JRadioButton ra_poly = new JRadioButton(polygon);
		JRadioButton ra_fiv = new JRadioButton(fiveangle);
				
		//�׷쿡 �߰�
		gup.add(ra_line);
		gup.add(ra_cir);
		gup.add(ra_rec);
		gup.add(ra_rrec);
		gup.add(ra_tri);
		gup.add(ra_poly);
		gup.add(ra_fiv);
		
		//���м� �߰�
		ra_line.setBorderPainted(true);
		ra_cir.setBorderPainted(true);
		ra_rec.setBorderPainted(true);
		ra_rrec.setBorderPainted(true);
		ra_tri.setBorderPainted(true);
		ra_poly.setBorderPainted(true);
		ra_fiv.setBorderPainted(true);
		
		//�гο� ���� ��ư �߰�
		shapePanel.add(ra_line);
		shapePanel.add(ra_cir);
		shapePanel.add(ra_rec);
		shapePanel.add(ra_rrec);
		shapePanel.add(ra_tri);
		shapePanel.add(ra_poly);
		shapePanel.add(ra_fiv);

		
		//�̺�Ʈ ������
		
		//���õ� ������ ���� shapeSelect ����
		ra_line.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				what_Select = 1;
			}
		});
		
		ra_cir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				what_Select = 2;
			}
		});
		
		ra_rec.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				what_Select = 3;
			}
		});
		
		ra_rrec.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				what_Select = 4;
			}
		});
		
		ra_tri.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				what_Select = 5;
			}
		});
		
		ra_poly.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				what_Select = 6;
			}
		});
		
		ra_fiv.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				what_Select = 7;
			}
		});
		
		//*********************

		//���м� �г�
		JPanel linePanel_two = new JPanel();
		linePanel_two.setBackground(new Color(211, 211, 211));
		linePanel_two.setBounds(200, 2, 1, 78);
		
		//*********************
		//�ؽ�Ʈ �г�
		
		//�۾�ü�� �������� �޺��ڽ� ����
		GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Font[] farr = e.getAllFonts();//��Ʈ ����Ʈ ��Ʈ ��ü�� ����
		fL = new String[farr.length];
		for(int i=0;i<farr.length;i++)//��� ������ ��� ��Ʈ ����Ʈ ���ڿ��� ����
			fL[i] = new String(farr[i].getFontName());
 
		//�۾�ü �޺��ڽ�
		JComboBox fontlist = new JComboBox(fL);

		//��Ʈ ������
		String [] size = {"8", "9", "10","11","12","14","16","18",
							"20","22","24","26","28","30"};
		
		//������ �޺��ڽ�
		JComboBox fontSize = new JComboBox(size);
		
		fontlist.setBounds(210, 10, 125, 20);
		fontSize.setBounds(213, 35, 43, 20);
		
		//��ư �̹��� ũ�� ����
		ImageIcon bolt_btn = new ImageIcon("Image/bolt.jpg");
		Image bolt_one = bolt_btn.getImage();
		Image bolt_two = bolt_one.getScaledInstance(14, 18, java.awt.Image.SCALE_SMOOTH);
		ImageIcon bolt = new ImageIcon(bolt_two);

		ImageIcon slide_btn = new ImageIcon("Image/slide.jpg");
		Image slide_one = slide_btn.getImage();
		Image slide_two = slide_one.getScaledInstance(14, 19, java.awt.Image.SCALE_SMOOTH);
		ImageIcon slide = new ImageIcon(slide_two);
		
		ImageIcon under_btn = new ImageIcon("Image/under.jpg");
		Image under_one = under_btn.getImage();
		Image under_two = under_one.getScaledInstance(15, 19, java.awt.Image.SCALE_SMOOTH);
		ImageIcon under = new ImageIcon(under_two);
		
		//��ư ����(����, ����̱�, ����)
		JCheckBox boltB = new JCheckBox(bolt);
		JCheckBox slideB = new JCheckBox(slide);
		JCheckBox underB = new JCheckBox(under);
		
		//��ư �ܰ���
		boltB.setBorderPainted(true);
		slideB.setBorderPainted(true);
		underB.setBorderPainted(true);
		
		//��ư ��ġ 
		boltB.setBounds(260, 35, 20, 20);
		slideB.setBounds(285, 35, 20, 20);
		underB.setBounds(310, 35, 20, 20);
		
		//�޺��ڽ� ���ý�
		fontlist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				font_select = fL[fontlist.getSelectedIndex()];
			}
		});
		
		fontSize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				font_size = size[fontSize.getSelectedIndex()];
				font_size_int = Integer.parseInt(font_size);
			}
		});
		
		//����ü ���ý�
		boltB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(slideB.isSelected() && boltB.isSelected()) {
					what_font = 3;
				}
				else if(boltB.isSelected()){
					what_font = 1;
				}
				else {
					what_font = 0;
				}
			}
		});
		
		//�����̵� ���ý�
		slideB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(boltB.isSelected() && slideB.isSelected()) {
					what_font = 3;
				}
				else if(slideB.isSelected()){
					what_font = 2;
				}
				else {
					what_font = 0;
				}
			}
		});
		
		//���� ���ý�
		underB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(underB.isSelected()) {
					under_line = 1;
				}
				else {
					under_line = 0;
				}
			}
		});
		
		//*********************
		//���м� �г�
		JPanel linePanel_three = new JPanel();
		linePanel_three.setBackground(new Color(211, 211, 211));
		linePanel_three.setBounds(342, 2, 1, 78);
		
		//*********************
		//�� ���⸦ �������ִ� �г�
				
		//�̹��� ũ�� ����
		ImageIcon li_menu = new ImageIcon("Image/line_size.jpg");
		Image one_menu = li_menu.getImage();
		Image two_menu = one_menu.getScaledInstance(44, 57, java.awt.Image.SCALE_SMOOTH);
		ImageIcon line_menu = new ImageIcon(two_menu);
				
		ImageIcon li_fir = new ImageIcon("Image/line1.jpg");
		Image one_fir = li_fir.getImage();
		Image two_fir = one_fir.getScaledInstance(63, 25, java.awt.Image.SCALE_SMOOTH);
		ImageIcon line_fir = new ImageIcon(two_fir);
				
		ImageIcon li_sec = new ImageIcon("Image/line2.jpg");
		Image one_sec = li_sec.getImage();
		Image two_sec = one_sec.getScaledInstance(63, 25, java.awt.Image.SCALE_SMOOTH);
		ImageIcon line_sec = new ImageIcon(two_sec);
				
		ImageIcon li_thr = new ImageIcon("Image/line3.jpg");
		Image one_thr = li_thr.getImage();
		Image two_thr = one_thr.getScaledInstance(63, 25, java.awt.Image.SCALE_SMOOTH);
		ImageIcon line_thr = new ImageIcon(two_thr);
				
		ButtonGroup size_gup = new ButtonGroup();
		
		//���� ��ư ����
		JRadioButton line1 = new JRadioButton(line_fir);
		JRadioButton line2 = new JRadioButton(line_sec);
		JRadioButton line3 = new JRadioButton(line_thr);
			
		//�׷쿡 �߰�
		size_gup.add(line1);
		size_gup.add(line2);
		size_gup.add(line3);
		
		line1.setSelected(true); //�ʱ� ���� ����
		
		//���м� �߰�
		line1.setBorderPainted(true);
		line2.setBorderPainted(true);
		line3.setBorderPainted(true);
		
		//�Ⱥ��̰� ����(�� ũ�� ���� ��ư ���� ��)
		line1.setVisible(false);
		line2.setVisible(false);
		line3.setVisible(false);
		
		//ũ�� ���� ��ư ��ġ
		line1.setBounds(350, 3, 60, 25);
		line2.setBounds(350, 27, 60, 25);
		line3.setBounds(350, 52, 60, 25);
				
		//�� ũ�� ���� ��ư
		JButton line_Size = new JButton(line_menu);
			
		line_Size.setBounds(357, 10, 45, 57);
		//ũ�� ���� ��ư Ŭ����
		line_Size.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {		
				//ũ�� ���ð���
				line1.setVisible(true);
				line2.setVisible(true);
				line3.setVisible(true);
				line_Size.setVisible(false);	//ũ�� ���ù�ư ��ġ�� �ö���� ������ false
				
				//�� ũ���� �ϳ� ���ý� ������ ����
				line1.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						line1.setVisible(false);
						line2.setVisible(false);
						line3.setVisible(false);
						line_Size.setVisible(true);
						
						lineWeight = 2;
					}
				});
				
				line2.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						line1.setVisible(false);
						line2.setVisible(false);
						line3.setVisible(false);
						line_Size.setVisible(true);
						
						lineWeight = 4;
					}
				});
				
				line3.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						line1.setVisible(false);
						line2.setVisible(false);
						line3.setVisible(false);
						line_Size.setVisible(true);
						
						lineWeight = 6;
					}
				});
			}
		});
		
		//*********************

		//���м� �г�
		JPanel linePanel_four = new JPanel();
		linePanel_four.setBackground(new Color(211, 211, 211));
		linePanel_four.setBounds(414, 2, 1, 78);
		
		//*********************	
		
		//���� ���� �г�
		ImageIcon color = new ImageIcon("Image/color.jpg");
		JRadioButton fir_btn = new JRadioButton(color);
		fir_btn.setForeground(new Color(0, 0, 205));
		fir_btn.setBorderPainted(true);
		fir_btn.setOpaque(false);
		
		
		JRadioButton sec_btn = new JRadioButton(color);
		sec_btn.setFont(new Font("210 �ǹ���û�� L", Font.PLAIN, 12));
		sec_btn.setBorderPainted(true);
		sec_btn.setOpaque(false);
		
		fir_btn.setBounds(420, 5, 40, 60);
		sec_btn.setBounds(465, 5, 40, 60);

		ButtonGroup g_colorChoice = new ButtonGroup();
		g_colorChoice.add(fir_btn);
		g_colorChoice.add(sec_btn);
		
		fir_btn.setSelected(true);	//ó���� ù��° ��ư ����
		
		//���� ���ý� ������ �г�
		JPanel fir_pan = new JPanel();
		JPanel sec_pan = new JPanel();
		
		fir_pan.setBounds(425, 10, 30, 30);
		fir_pan.setBackground(Color.BLACK);
		
		sec_pan.setBounds(470, 10, 30, 30);
		sec_pan.setBackground(Color.WHITE);
	
		//��1, ��2 ǥ�� ���̺�
		JLabel fir_label = new JLabel("�� 1");
		fir_label.setFont(new Font("����_SemiBold", Font.PLAIN, 12));
		fir_label.setBounds(430, 45, 30, 13);
		
		JLabel sec_label = new JLabel("\uC0C9 2");
		sec_label.setFont(new Font("����_SemiBold", Font.PLAIN, 12));
		sec_label.setBounds(475, 45, 30, 13);
		
		//*********************
		
		//�� ��ư �г�
		JPanel colorChart_Panel = new JPanel();
		colorChart_Panel.setBounds(515, 0, 210, 70);
		colorChart_Panel.setLayout(null);
		colorChart_Panel.setOpaque(false);
		
		//����ǥ Button ����
		JButton[] color_chart = new JButton[30];
				
		int m = 5;		//������ ������ �����ִ� m��(����)
		int n = 7;			//������ ������ �����ִ� n��(����)		
				
		//������ ���ݴ�� ��ư�� �������
		for(int j=0; j<3; j++) {
			for(int i=0; i<10; i++) {		//�� �ٿ� 10ĭ
				color_chart[j*10+i] = new JButton();
				color_chart[j*10+i].setBounds(m, n, 17, 17);
				m+=20;
						
				colorChart_Panel.add(color_chart[j*10+i]);
			}
			n += 20;	//������ġ +10
			m=5;		//ó�� ������ġ ����
		}
		
		//����ǥ �� ����
		color_chart[0].setBackground(Color.BLACK);
		color_chart[1].setBackground(Color.DARK_GRAY);
		color_chart[2].setBackground(new Color(139, 0, 0));
		color_chart[3].setBackground(Color.RED);
		color_chart[4].setBackground(Color.ORANGE);
		color_chart[5].setBackground(Color.YELLOW);
		color_chart[6].setBackground(Color.GREEN);
		color_chart[7].setBackground(Color.BLUE);
		color_chart[8].setBackground(new Color(0, 0, 139));
		color_chart[9].setBackground(new Color(138, 43, 226));
		color_chart[10].setBackground(Color.WHITE);
		color_chart[11].setBackground(Color.LIGHT_GRAY);
		color_chart[12].setBackground(new Color(160, 82, 45));
		color_chart[13].setBackground(Color.PINK);
		color_chart[14].setBackground(new Color(244, 164, 96));
		color_chart[15].setBackground(new Color(250, 235, 215));
		color_chart[16].setBackground(new Color(173, 255, 47));
		color_chart[17].setBackground(new Color(175, 238, 238));
		color_chart[18].setBackground(new Color(70, 130, 180));
		color_chart[19].setBackground(new Color(216, 191, 216));

		//���� ���� ����
		for(int i=20; i<30;i++)
			color_chart[i].setBackground(new Color(245, 245, 245));		
	
		//************************
		//�� ���� ��ư
		ImageIcon colorEd = new ImageIcon("Image/coloredit.jpg");
		Image image_colored = colorEd.getImage();
		Image image_colorEd = image_colored.getScaledInstance(45, 70, java.awt.Image.SCALE_SMOOTH);
		ImageIcon colorEdit = new ImageIcon(image_colorEd);
		
		JButton coloredit = new JButton(colorEdit);
		coloredit.setSize(45, 70);
		coloredit.setLocation(729,5);

		//�� ���� ��ư Ŭ���� ����� ���� �� ���� â 
		coloredit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				colorselect = JColorChooser.showDialog(null, "����� ���� �� ����", Color.BLACK);
				
				//RadioButton ��� ���� ����
				//ù��° RadioButton�� ���
				if(fir_btn.isSelected()) {
					colorselect_left= colorselect;
					fir_pan.setBackground(colorselect);
					//��ư�� �߰��� ��� ���� �־��ֱ�
					color_chart[new_btn++].setBackground(colorselect);
				}
				
				//�ι�° RadioButton�� ���
				else {
					colorselect_right = colorselect;
					sec_pan.setBackground(colorselect);
					//��ư�� �߰��� ��� ���� �־��ֱ�
					color_chart[new_btn++].setBackground(colorselect);
				}
				
			
			}
		});
		
		//************************
		//�� ��ư Ŭ���� �� ���� ����
		for(int i=0; i<30; i++) {
			//�����ý� ��ư�� ���� ����
			color_chart[i].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JButton colorChoice = (JButton)e.getSource();	//���õȹ�ư : colorChoice
					colorselect = colorChoice.getBackground();		//���󺯰�
					
					//RadioButton ��� ���� ����

					//ù��° RadioButton�� ���
					if(fir_btn.isSelected()) {
						colorselect_left = colorselect;
						fir_pan.setBackground(colorselect_left);
					}
					
					//�ι�° RadioButton�� ���
					else {
						colorselect_right = colorselect;
						sec_pan.setBackground(colorselect_right);
					}
				}
			});
		}
		
		//�� �г� Ŭ����-> �г� �������� colorselect ����
		fir_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				colorselect_left = fir_pan.getBackground();		//���󺯰�
			}
		});
		
		//secColor�� �����ϴ� ��� -> �� �г� �������� colorselect�� �������ش�
		sec_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				colorselect_right = sec_pan.getBackground();		//���󺯰�
			}
		});
		
		//***********************
		
		colorselect_left = fir_pan.getBackground();
		colorselect_right = sec_pan.getBackground();
		
		//���̺� �߰�
		JLabel tool_name = new JLabel("����");
		JLabel shape_name = new JLabel("����");
		JLabel text_name = new JLabel("�۲�");
		JLabel color_name = new JLabel("��");
		
		tool_name.setFont(new Font("����_SemiBold", Font.PLAIN, 10));
		tool_name.setBounds(30, 67, 30, 10);
		
		shape_name.setFont(new Font("����_SemiBold", Font.PLAIN, 10));
		shape_name.setBounds(130, 67, 30, 10);
		
		text_name.setFont(new Font("����_SemiBold", Font.PLAIN, 10));
		text_name.setBounds(258, 67, 30, 10);
		
		color_name.setFont(new Font("����_SemiBold", Font.PLAIN, 10));
		color_name.setBounds(600, 67, 30, 10);
		
		//************************

		mainPanel.add(tool);
		mainPanel.add(shapePanel);
		mainPanel.add(linePanel);
		mainPanel.add(linePanel_two);
		mainPanel.add(linePanel_three);
		mainPanel.add(linePanel_four);
		mainPanel.add(fir_btn);
		mainPanel.add(sec_btn);
		mainPanel.add(fir_pan);
		mainPanel.add(sec_pan);
		mainPanel.add(line_Size);
		mainPanel.add(line1);
		mainPanel.add(line2);
		mainPanel.add(line3);
		mainPanel.add(fir_label);
		mainPanel.add(sec_label);
		mainPanel.add(colorChart_Panel);
		mainPanel.add(coloredit);
		mainPanel.add(tool_name);
		mainPanel.add(shape_name);
		mainPanel.add(text_name);
		mainPanel.add(color_name);
		mainPanel.add(fontlist);
		mainPanel.add(fontSize);
		mainPanel.add(slideB);
		mainPanel.add(boltB);
		mainPanel.add(underB);
		
		return mainPanel;
		
	}

	
	//���� - ���� �г�
	public JPanel editPanel(){
		JPanel mainPanel = new JPanel();
		mainPanel.setBackground(new Color(245, 245, 245));
		mainPanel.setLayout(null);
		
		//**********************************
		//Ȯ��&��� �г�
		ImageIcon up = new ImageIcon("Image/size_up.jpg");
		ImageIcon down = new ImageIcon("Image/size_down.jpg");
		
		//�̹��� ũ�� ����
		Image one_up = up.getImage();
		Image two_up = one_up.getScaledInstance(50,70,java.awt.Image.SCALE_SMOOTH);
		ImageIcon size_up = new ImageIcon(two_up);
		Image one_dw = down.getImage();
		Image two_dw = one_dw.getScaledInstance(50,70,java.awt.Image.SCALE_SMOOTH);
		ImageIcon size_down = new ImageIcon(two_dw);
		
		JButton big = new JButton(size_up);
		JButton small = new JButton(size_down);
		
		//��ư ��ġ ����
		big.setBounds(3,3,50,70);
		small.setBounds(56, 3, 50, 70);
		
		//zoom in ��ư Ŭ����
		big.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//vc��� Ȯ��
				for(int i=0;i<vc.size();i++) {
					DrawPoint d_plus = (DrawPoint)vc.elementAt(i);

					//2��� Ȯ��
					d_plus.setX((int)(d_plus.getX()*2));
					d_plus.setX1((int)(d_plus.getX1()*2));
					d_plus.setY((int)(d_plus.getY()*2));
					d_plus.setY1((int)(d_plus.getY1()*2));
					d_plus.setLineWeight((int)(d_plus.getLineWeight()*2));
					d_plus.setF3(d_plus.getF3()*2);
					
				}
				lineWeight*=2;//�� ���� ����
				count_plus++;//Ȯ�� Ƚ�� ++;
			}
		});

		small.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//vc��� ���
				for(int i=0;i<vc.size();i++) {
					DrawPoint d_minus = (DrawPoint)vc.elementAt(i);

					//2��� ���
					d_minus.setX((int)(d_minus.getX()/2));
					d_minus.setX1((int)(d_minus.getX1()/2));
					d_minus.setY((int)(d_minus.getY()/2));
					d_minus.setY1((int)(d_minus.getY1()/2));
					d_minus.setLineWeight((int)(d_minus.getLineWeight()/2));
					d_minus.setF3(d_minus.getF3()/2);
					
				}
				lineWeight/=2;//�� ���� ����
				count_plus--;//Ȯ�� Ƚ�� 
			}
		});
		//**********************************
		
		//���м� �г�
		JPanel linePanel = new JPanel();
		linePanel.setBackground(new Color(211, 211, 211));
		linePanel.setBounds(113, 2, 1, 78);
	
		//***********************************
		//��üȭ��
		ImageIcon all = new ImageIcon("Image/window.jpg");
		
		//�̹��� ũ�� ����
		Image one_all = all.getImage();
		Image two_all = one_all.getScaledInstance(50,70,java.awt.Image.SCALE_SMOOTH);
		ImageIcon win = new ImageIcon(two_all);

		JButton window = new JButton(win);
		
		window.setBounds(120,3,50,70);
		
		//��üȭ�� ��ư Ŭ�� ��
		window.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fullScreen(true);
			}
		});
		
		//***********************************
		
		mainPanel.add(big);
		mainPanel.add(small);
		mainPanel.add(linePanel);
		mainPanel.add(window);
		return mainPanel;
	}
	
	public void fullScreen(boolean visible) {
		windowFullScreen = drawPanel;

		if(visible) {
			windowFullScreen = drawPanel;
			Dimension dimScreen = Toolkit.getDefaultToolkit().getScreenSize();
	
			this.remove(windowFullScreen);

			if(window == null) {
				//window �⺻ ����
				window = new Window(this);
				window.setBackground(Color.WHITE);
				window.setBounds(0,0, dimScreen.width, dimScreen.height);
			
				windowFullScreen.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						contentPane.add(drawPanel);
						contentPane.add(pane);
						drawPanel.setBounds(0,110,800,500);

						fullScreen(false);
					}
				});
			}
			
			window.add(windowFullScreen);	//�г� �����쿡 �÷��ֱ�
			window.setVisible(true);
			window.validate();
		}
		else {
			window.remove(windowFullScreen);
			window.setVisible(false);
			
			this.add(windowFullScreen);
			windowFullScreen.validate();
			windowFullScreen.setBounds(0,110,800,500);
			setVisible(true);
		}
	}
	
	//����Ʈ ���ִ� Ŭ����
	class MyPrintable implements Printable{
		public int print(Graphics g1, PageFormat pf, int pageIndex){
			g1.translate((int)(pf.getImageableX()), (int)(pf.getImageableY()));// �׷��� ������ ����Ʈ�� �̹����� ��ǥ�� ��ȯ�մϴ�.
			if(pageIndex == 0){
				g1.drawImage(printImage, 0, 0, null);
				return Printable.PAGE_EXISTS;
			}
			return Printable.NO_SUCH_PAGE;
		}
	}
	
	public static void main(String[] args) {
		new Drawing();
	}
}
