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

//그림그린 점 위치
class DrawPoint implements Serializable {

	private int x, y, x1, y1;
	private int dist;
	public int getDist() {return dist;}
	public void setDist(int dist) {this.dist = dist;}
	
	private int paint_set;		//페인트 선택 했는지
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

	//color값_왼쪽
	private Color color;
	public void setC(Color c) {this.color = c;}
	public Color getC() {return color;}

	//선굵기
	private int lineWeight;		
	public int getLineWeight() {return lineWeight;}//lineWeight 값 가져오기 위해
	public void setLineWeight(int lineWeight) {this.lineWeight=lineWeight;}//lineWeight 값 설정하기 위해

	//텍스트
	private String f_font;		 //글자 모양 저장
	private int f_shape;			 //글자 특징 저장
	private int f_size;	 		 //글자 크기 저장
	private int f_under;			 //밑줄이 선택된 상태로 그려진 텍스트인지
	private String str;      //문자열 저장

	public String getF1() {return f_font;}//f1 값 가져오기 위해
	public void setF1(String f1) {this.f_font = f1;}//f1 값 설정하기 위해
	public int getF2() {return f_shape;}//f2 값 가져오기 위해
	public void setF2(int f2) {this.f_shape = f2;}//f2 값 설정하기 위해
	public int getF3() {return f_size;}//f3 값 가져오기 위해
	public void setF3(int f3) {this.f_size = f3;}//f3 값 설정하기 위해
	public int getUL() {return f_under;}//ul 값 가져오기 위해
	public void setUL(int ul) {this.f_under = ul;}//ul 값 설정하기 위해
	public String getStr() {return str;}//str 값 가져오기 위해
	public void setStr(String str) {this.str = str;}//str 설정하기 위해

}

public class Drawing extends JFrame {
	Container contentPane;
	
	private int x, y, x1, y1; //마우스를 눌렀을때와 뗐을때 각 좌표값
	private Vector vc = new Vector();
	
	//도구패널과 도형패널 구분자
	public int tool_Selected = 0;
	public int shape_Selected = 0;
		
	
	//현재 선택된 도구 및 도형 정보
	public int what_Select=0;
	
	//그림 그리는 패널
	PaintPanel drawPanel = new PaintPanel();
	
	//패널 높이&넓이
	public int panel_Width = drawPanel.getWidth();
	public int panel_Height = drawPanel.getHeight();
	
	//현재 선택된 색상 정보
	public Color colorselect_left;	//왼쪽
	public Color colorselect_right;	//오른쪽
	
	public Color colorselect;

	//새로운 색상 버튼 추가
	public int new_btn=20;

	//메뉴 열기파일로 출력될 레이블 생성
	JLabel imageLabel = new JLabel();

	//파일 경로 및 필터
	private String savePath = new String(); //저장파일 경로
	private String openFilePath = new String(); //열기파일 경로
	private String filt;      //파일 필터

	//지금까지 그린게 저장되었는지
	private int saveStatus = 0;

	//색 채우기 
	public int if_any_shape=0;		//페인트 선택 시 다른 도형 선택되면 1, 아니면 0
	public int if_paint = 0;		//페인트 선택 시 1, 아닐 시 0
	//선굵기
	public int lineWeight = 2;		
	
	//폰트 리스트
	private String fL[];

	//선택한 폰트
	public String font_select;
	//선택한 크기
	public String font_size;
	public int font_size_int;	//int형

	//폰트 설정
	public int what_font = 0;
	public int under_line = 0;	//밑줄
	
	//확대 횟수
	public int count_plus = 0;
	
	//전체화면
	Window window;
	JPanel windowFullScreen;
	
	//탭팬 생성
	JTabbedPane pane = new JTabbedPane();
	
	//ctrl키 활성화
	public int ctrl = 0;		
	
	//프린트 이미지 저장
	public Image printImage; 
	
	public Drawing() {
		setTitle("예나의 그림판");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


		contentPane = getContentPane();
				
		// window의 X버튼을 누르면 window를 종료하라
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
	
		});

		pane.addTab("홈", homePanel());
		pane.addTab("보기", editPanel());
		
		//그림그리는 패널 생성		
		drawPanel.setBounds(0,110,800,500);
		drawPanel.setBackground(Color.WHITE);
		drawPanel.add(imageLabel);

		//컨탠트팬에 추가
		contentPane.add(drawPanel);	//그림그리는 패널
		contentPane.add(pane);		//탭팬		
	
		//메뉴 생성
		createMenu();

		//메뉴 키 활성화
		contentPane.addKeyListener(new MenuKey());
		pane.addKeyListener(new MenuKey());
		drawPanel.addKeyListener(new MenuKey());
		
		setSize(800, 600);
		setVisible(true);
	}
	
	//***********************************************************
	//메뉴 키 단축버튼
	class MenuKey extends KeyAdapter{
		public void keyPressed(KeyEvent e) {				
			//ctrl키
			if(e.getKeyCode() == KeyEvent.VK_CONTROL) 
				ctrl = 1;
					
			//ctrl+s -> 저장
			if(e.getKeyCode() == KeyEvent.VK_S && ctrl == 1) {
				MenuActionListener me = new MenuActionListener();
				me.fileSave(0);//파일 저장
						
				saveStatus = 0;//저장 완료
			}
					
			//ctrl+n -> 새로만들기
			else if(e.getKeyCode() == KeyEvent.VK_N && ctrl == 1) {
				MenuActionListener me = new MenuActionListener();
				if(saveStatus == 1) {//전에 그려놓았던 panel저장할지
					int i = me.save_Ask();
					if(i == 2){
						vc.removeAllElements();
						vc.clear();
					}
					else if(i==0 && saveStatus==0){// 아무것도 그려져 있지 않으면 바로 종료
						vc.removeAllElements();
						vc.clear();
					}
				}
			}
			
			//ctrl+o -> 열기
			else if(e.getKeyCode() == KeyEvent.VK_O && ctrl == 1) {
				MenuActionListener me = new MenuActionListener();
				JFileChooser chooser = new JFileChooser();
				
				FileNameExtensionFilter filter = new FileNameExtensionFilter(
						"JPG & GIF Images",	//파일 이름 난에 출력될 문자열
						"jpg", "gif");		//파일 필터로 사용되는 확장자
					
				chooser.setFileFilter(filter); 	//파일 다이얼로그에 파일 필터 설정
					
				int ret = chooser.showOpenDialog(null);	//파일 다이얼로그 출력
					
				//선택 안한 경우
				if (ret != JFileChooser.APPROVE_OPTION) {
					JOptionPane.showMessageDialog(null, "파일을 선택하지 않았습니다", "경고", JOptionPane.WARNING_MESSAGE);
					return;
				}

				if(saveStatus == 0) {	//아무 그림도 안그린 경우
					String filePath = chooser.getSelectedFile().getPath();	//파일 경로명 리턴
					imageLabel.setIcon(new ImageIcon(filePath));//이미지 출력
				}
				else {	//그려져 있는 경우
						vc.removeAllElements();//벡터요소 전체 삭제
								
						if(me.save_Ask() == 2) {	//파일 저장 안할 경우
							//사용자가 파일을 선택하고 "열기"를 누른 경우
							drawPanel.setForeground(Color.WHITE);
							String filePath = chooser.getSelectedFile().getPath();	//파일 경로명 리턴
							imageLabel.setIcon(new ImageIcon(filePath));//이미지 출력
						}
								
						else {		//파일 저장 하는 경우
							me.fileSave(0);
							drawPanel.setBackground(Color.WHITE);	//배경색 유지
							String filePath = chooser.getSelectedFile().getPath();	//파일 경로명 리턴
							imageLabel.setIcon(new ImageIcon(filePath));//이미지 출력
							repaint();
						}
					}
					saveStatus = 1;
				}
					
				//ctrl+x -> 종료
			else if(e.getKeyCode() == KeyEvent.VK_X && ctrl == 1) {
					MenuActionListener me = new MenuActionListener();
					if(saveStatus == 1) {//전에 그려놓았던 panel저장할지
						int i = me.save_Ask();
						if(i == 2)
							System.exit(0);
						else if(i==0 && saveStatus==0)// 아무것도 그려져 있지 않으면 바로 종료
							System.exit(0);
					}
					else//취소 선택
						System.exit(0);
			}
			
			//ctrl+p -> 인쇄
			else if(e.getKeyCode() == KeyEvent.VK_P && ctrl == 1) {
				File temp=  new File("temp.jpg"); //프린트 할 이미지 형성을 위한 임시 이미지 파일
		        
				BufferedImage image = new BufferedImage(drawPanel.getWidth(),
						drawPanel.getHeight(),BufferedImage.TYPE_USHORT_565_RGB);
				
		         try{
		             ImageIO.write(image, "png", temp); 
		          }catch(Exception ee){
		             ee.printStackTrace();
		          }
		         
		       //저장한 이미지를 이미지 아이콘객체에 저장
				ImageIcon temp2= new ImageIcon("temp.jpg");      
				printImage= temp2.getImage();               //저장한 이미지 불러오기
				Paper p= new Paper();
				PageFormat format = new PageFormat();         //새 PageFormat 인스턴스가 만들어지고 기본 크기와 방향으로 설정
				format.setPaper(p);  //페이지포맷 페이지영역설정을 인자로
				PrinterJob pj = PrinterJob.getPrinterJob();     //프린트를 컨트롤해주는 객체
				pj.setPrintable(new MyPrintable(), format);     //클래스를 호출하여 작업을 설정하고 선택적으로 사용자와 인쇄 대화 상자를 호출 한 다음 작업 페이지를 인쇄
				try{
					pj.print();                           //프린트
				}catch(PrinterException pe){
					System.out.println("Printingfailed : "+pe.getMessage());
				}
			}
			
				drawPanel.repaint();
			}
			public void keyReleased(KeyEvent e) {
				//컨트롤을 떼면 -> ctrl=0
				if(e.getKeyCode() == KeyEvent.VK_CONTROL) {
					ctrl = 0;
				}
			}
		}

	//***********************************************
	//그림 그리는 패널 클래스
	class PaintPanel extends JPanel {
		public int right=0;
		public int left=0;
		
		//그림 그리는 메소드
		public void paint(Graphics gg) {		//선 굵기를 위해 Graphics2D로 바꾸기!!!!!
			super.paint(gg);
			Graphics2D g = (Graphics2D)gg;
			
			
			//그림그리기
			for(int i = 0; i < vc.size(); ++i) {
				DrawPoint d = (DrawPoint)vc.elementAt(i);
				
				g.setColor(d.getC());		//color 선택				
				g.setStroke(new BasicStroke(d.getLineWeight()));//객체에서 라인 두께 가져오기
				
				if(d.getDist() == 1) {	//선
					g.drawLine(d.getX(), d.getY(), d.getX1(), d.getY1());
				}
				
				else if(d.getDist() == 9) { //텍스트
					Font font = new Font(d.getF1(),d.getF2(),d.getF3());
					if(d.getUL()==1) {//텍스트의 밑줄긋기가 체크되어있으면
						Map attributes = font.getAttributes();
						attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
						g.setFont(font.deriveFont(attributes));
					}
					else
						g.setFont(font);
					g.drawString(d.getStr(), d.getX(), d.getY());
				}
				
				//채우기 미 선택시
				else if(d.getPaint() == 0) {
					if(d.getDist() == 2) {	//OVAL
						
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
					else if(d.getDist() == 3) { //Rect
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
					else if(d.getDist() == 4) {	//RoundRECT
						//오른쪽 아래
						if((d.getX() < d.getX1()) && d.getY() < d.getY1())
							g.drawRoundRect(d.getX(), d.getY(), d.getX1()-d.getX(), d.getY1()-d.getY(), 40, 60);
						
						//오른쪽 위
						else if ((d.getX() < d.getX1()) && (d.getY() > d.getY1())) 
							g.drawRoundRect(d.getX(), d.getY1(), d.getX1()-d.getX(), Math.abs(d.getY1()-d.getY()), 40, 60);
						//왼쪽 위
						else if ((d.getX() > d.getX1()) && (d.getY() > d.getY1())) 
							g.drawRoundRect(d.getX1(), d.getY1(), d.getX()-d.getX1(), d.getY()-d.getY1(),40,60);
						//왼쪽 아래						
						else
							g.drawRoundRect(d.getX1(), d.getY(), d.getX()-d.getX1(), d.getY1()-d.getY(),40,60);
					}
					else if(d.getDist() == 5) {	//이등변 삼각형
						int [] x = {d.getX(),(d.getX()+d.getX1())/2,d.getX1()};
						int [] y = {d.getY()>d.getY1()?d.getY():d.getY1(),
									   d.getY()>d.getY1()?d.getY1():d.getY(),
								       d.getY()>d.getY1()?d.getY():d.getY1()};
						g.drawPolygon(x,y,3);
					}
					else if(d.getDist() == 6) { //직각 삼각형
						int [] x = {d.getX()>d.getX1()?d.getX1():d.getX(),
								   d.getX()>d.getX1()?d.getX1():d.getX(),
							       d.getX()>d.getX1()?d.getX():d.getX1()};
						int [] y = {d.getY()>d.getY1()?d.getY1():d.getY(),
							   	   d.getY()>d.getY1()?d.getY():d.getY1(),
								   d.getY()>d.getY1()?d.getY():d.getY1()};
						g.drawPolygon(x,y,3);
					}
					else if(d.getDist() == 7) { //오각형
						int [] x = {(d.getX1()-d.getX())*2/9+d.getX(),(d.getX1()-d.getX())*7/9+d.getX(),d.getX1(),(d.getX1()+d.getX())/2,d.getX()};
						int [] y = {d.getY()>d.getY1()?d.getY():d.getY1(),d.getY()>d.getY1()?d.getY():d.getY1(),
								d.getY()>d.getY1()?(d.getY()-d.getY1())/3+d.getY1():(d.getY1()-d.getY())/3+d.getY(),
				    		           d.getY()>d.getY1()?d.getY1():d.getY(),d.getY()>d.getY1()?
				    		        		   (d.getY()-d.getY1())/3+d.getY1():(d.getY1()-d.getY())/3+d.getY()};
						g.drawPolygon(x,y,5);
					}
				}
				//채우기 선택 시
				else if(d.getPaint() == 1) {	
					if(d.getDist() == 2) {	//OVAL
						
						//오른쪽 아래
						if((d.getX() < d.getX1()) && d.getY() < d.getY1())
							g.fillOval(d.getX(), d.getY(), d.getX1()-d.getX(), d.getY1()-d.getY());
						
						//오른쪽 위
						else if ((d.getX() < d.getX1()) && (d.getY() > d.getY1())) 
							g.fillOval(d.getX(), d.getY1(), d.getX1()-d.getX(), Math.abs(d.getY1()-d.getY()));
						//왼쪽 위
						else if ((d.getX() > d.getX1()) && (d.getY() > d.getY1())) 
							g.fillOval(d.getX1(), d.getY1(), d.getX()-d.getX1(), d.getY()-d.getY1());
						//왼쪽 아래						
						else
							g.fillOval(d.getX1(), d.getY(), d.getX()-d.getX1(), d.getY1()-d.getY());						
						
					}
					else if(d.getDist() == 3) { //Rect
						//오른쪽 아래
						if((d.getX() < d.getX1()) && d.getY() < d.getY1())
							g.fillRect(d.getX(), d.getY(), d.getX1()-d.getX(), d.getY1()-d.getY());
						
						//오른쪽 위
						else if ((d.getX() < d.getX1()) && (d.getY() > d.getY1())) 
							g.fillRect(d.getX(), d.getY1(), d.getX1()-d.getX(), Math.abs(d.getY1()-d.getY()));
						//왼쪽 위
						else if ((d.getX() > d.getX1()) && (d.getY() > d.getY1())) 
							g.fillRect(d.getX1(), d.getY1(), d.getX()-d.getX1(), d.getY()-d.getY1());
						//왼쪽 아래						
						else
							g.fillRect(d.getX1(), d.getY(), d.getX()-d.getX1(), d.getY1()-d.getY());
	
					}
					else if(d.getDist() == 4) {	//RoundRECT
						//오른쪽 아래
						if((d.getX() < d.getX1()) && d.getY() < d.getY1())
							g.fillRoundRect(d.getX(), d.getY(), d.getX1()-d.getX(), d.getY1()-d.getY(), 40, 60);
						
						//오른쪽 위
						else if ((d.getX() < d.getX1()) && (d.getY() > d.getY1())) 
							g.fillRoundRect(d.getX(), d.getY1(), d.getX1()-d.getX(), Math.abs(d.getY1()-d.getY()), 40, 60);
						//왼쪽 위
						else if ((d.getX() > d.getX1()) && (d.getY() > d.getY1())) 
							g.fillRoundRect(d.getX1(), d.getY1(), d.getX()-d.getX1(), d.getY()-d.getY1(),40,60);
						//왼쪽 아래						
						else
							g.fillRoundRect(d.getX1(), d.getY(), d.getX()-d.getX1(), d.getY1()-d.getY(),40,60);
					}
					else if(d.getDist() == 5) {	//이등변 삼각형
						int [] x = {d.getX(),(d.getX()+d.getX1())/2,d.getX1()};
						int [] y = {d.getY()>d.getY1()?d.getY():d.getY1(),
									   d.getY()>d.getY1()?d.getY1():d.getY(),
								       d.getY()>d.getY1()?d.getY():d.getY1()};
						g.fillPolygon(x,y,3);
					}
					else if(d.getDist() == 6) { //직각 삼각형
						int [] x = {d.getX()>d.getX1()?d.getX1():d.getX(),
								   d.getX()>d.getX1()?d.getX1():d.getX(),
							       d.getX()>d.getX1()?d.getX():d.getX1()};
						int [] y = {d.getY()>d.getY1()?d.getY1():d.getY(),
							   	   d.getY()>d.getY1()?d.getY():d.getY1(),
								   d.getY()>d.getY1()?d.getY():d.getY1()};
						g.fillPolygon(x,y,3);
					}
					else if(d.getDist() == 7) { //오각형
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
		
			// 마우스를 눌렀다 떼었을때 
			if(what_Select == 1) {  //라인을 체크하면
				g.drawLine(x, y, x1, y1);  //x,y좌표에서 x1,y1좌표에 라인을 그려라
			}

			//채우기 미 선택시
			if(if_any_shape == 0) {
				if(what_Select == 2){  //oval을 체크하면
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
				else if(what_Select == 3) {    //rect를 체크하면
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
				else if(what_Select == 4) {	//roundrect를 체크하면
					//오른쪽 아래
					if((x < x1) && y < y1)
						g.drawRoundRect(x, y, x1-x, y1-y, 40, 60);
								
					//오른쪽 위
					else if ((x < x1) && (y > y1)) 
						g.drawRoundRect(x, y1, x1-x, Math.abs(y1-y), 40, 60);
					
					//왼쪽 위
					else if ((x > x1) && (y > y1)) 
						g.drawRoundRect(x1, y1, x-x1, y-y1, 40, 60);
					//왼쪽 아래						
					else
						g.drawRoundRect(x1, y, x-x1, y1-y, 40, 60);
		
				}
				else if(what_Select == 5) { //이등변 삼각형을 체크하면
					g.drawPolygon(new int[] {x,(x+x1)/2,x1},
						 new int[] {y>y1?y:y1,y>y1?y1:y,y>y1?y:y1}, 3);
	
				}
				else if(what_Select == 6) { //직각 삼각형을 체크하면
					g.drawPolygon(new int[] {x>x1?x1:x,x>x1?x1:x,x>x1?x:x1},
							  new int[] {y>y1?y1:y,y>y1?y:y1,y>y1?y:y1}, 3);
		
				}
				else if(what_Select == 7) {	//오각형을 체크하면
					g.drawPolygon(new int[] {(x1-x)*2/9+x,(x1-x)*7/9+x,x1,(x1+x)/2,x},
						      new int[] {y>y1?y:y1,y>y1?y:y1,y>y1?(y-y1)/3+y1:(y1-y)/3+y,
						    		     y>y1?y1:y,y>y1?(y-y1)/3+y1:(y1-y)/3+y},5);
				}
			}
			
			//색 채우기 선택 시
			else if(if_any_shape == 1) {
				if(what_Select == 2){  //oval을 체크하면
					//오른쪽 아래
					if((x < x1) && y < y1)
						g.fillOval(x, y, x1-x, y1-y);
								
					//오른쪽 위
					else if ((x < x1) && (y > y1)) 
						g.fillOval(x, y1, x1-x, Math.abs(y1-y));
					
					//왼쪽 위
					else if ((x > x1) && (y > y1)) 
						g.fillOval(x1, y1, x-x1, y-y1);
					//왼쪽 아래						
					else
						g.fillOval(x1, y, x-x1, y1-y);
				}
				else if(what_Select == 3) {    //rect를 체크하면
					//오른쪽 아래
					if((x < x1) && y < y1)
						g.fillRect(x, y, x1-x, y1-y);
								
					//오른쪽 위
					else if ((x < x1) && (y > y1)) 
						g.fillRect(x, y1, x1-x, Math.abs(y1-y));
					
					//왼쪽 위
					else if ((x > x1) && (y > y1)) 
						g.fillRect(x1, y1, x-x1, y-y1);
					//왼쪽 아래						
					else
						g.fillRect(x1, y, x-x1, y1-y);
				}
				else if(what_Select == 4) {	//roundrect를 체크하면
					//오른쪽 아래
					if((x < x1) && y < y1)
						g.fillRoundRect(x, y, x1-x, y1-y, 40, 60);
								
					//오른쪽 위
					else if ((x < x1) && (y > y1)) 
						g.fillRoundRect(x, y1, x1-x, Math.abs(y1-y), 40, 60);
					
					//왼쪽 위
					else if ((x > x1) && (y > y1)) 
						g.fillRoundRect(x1, y1, x-x1, y-y1, 40, 60);
					//왼쪽 아래						
					else
						g.fillRoundRect(x1, y, x-x1, y1-y, 40, 60);
		
				}
				else if(what_Select == 5) { //이등변 삼각형을 체크하면
					g.fillPolygon(new int[] {x,(x+x1)/2,x1},
						 new int[] {y>y1?y:y1,y>y1?y1:y,y>y1?y:y1}, 3);
	
				}
				else if(what_Select == 6) { //직각 삼각형을 체크하면
					g.fillPolygon(new int[] {x>x1?x1:x,x>x1?x1:x,x>x1?x:x1},
							  new int[] {y>y1?y1:y,y>y1?y:y1,y>y1?y:y1}, 3);
		
				}
				else if(what_Select == 7) {	//오각형을 체크하면
					g.fillPolygon(new int[] {(x1-x)*2/9+x,(x1-x)*7/9+x,x1,(x1+x)/2,x},
						      new int[] {y>y1?y:y1,y>y1?y:y1,y>y1?(y-y1)/3+y1:(y1-y)/3+y,
						    		     y>y1?y1:y,y>y1?(y-y1)/3+y1:(y1-y)/3+y},5);
				}
			}		
		}
		
		public PaintPanel() {
			//마우스 사용 시
			addMouseListener(new MouseAdapter() {

				//마우스 눌렀을 때
				public void mousePressed(MouseEvent e) {				
					x = e.getX();       //x의 좌표값
					y = e.getY();       //y의 좌표값

					DrawPoint d = new DrawPoint();	//객체 생성
					
					saveStatus = 1;
					if(e.getButton()==MouseEvent.BUTTON3) {	//오른쪽 마우스
						right = 1;
						left = 0;
					}else {				//왼쪽 마우스	
						left = 1;
						right = 0;
					}
					
					//텍스트 선택 시
					if(what_Select == 9) {
						 String str = JOptionPane.showInputDialog("입력");
						 if(str!=null) {
							
							 //각각의 값 대입
							 d.setX(x);
							 d.setY(y);
							 d.setStr(str);
							 d.setDist(9);
							 d.setC(colorselect_left);
							 d.setF1(font_select);//콤보박스에서 선택한 글씨체 가져오기
							 switch(what_font) {//선택한 라디오버튼에 따른 글자 스타일 설정
								 case 0 : d.setF2(Font.PLAIN);
								 	break;
								 case 1 : d.setF2(Font.BOLD);
								 	break;
								 case 2 : d.setF2(Font.ITALIC);
								 	break;
								 case 3 : d.setF2(Font.BOLD | Font.ITALIC);
								 	break;
							 }
							 
							 //밑줄 선택시
							 if(under_line == 1) {
								 d.setUL(1);
							 }
							 
							 //텍스트필드에서 글자크기 가져오기(확대된 상태에서 입력 시 더 크게)
							 d.setF3(font_size_int);
							 vc.add(d); //vc에 값을 저장하라
							 repaint();
						 }
					}
				}
					
				//마우스 떼었을 때
				public void mouseReleased(MouseEvent e) { 
					x1 = e.getX();   //x1의 좌표값
					y1 = e.getY();   //y1의 좌표값
					repaint();  //그림을 다시 그린다	
					
					//pen이 true가 아닐때에만 아래를 실행하라
					if(what_Select != 0) {  
						
						int dist = 0;
						if(what_Select == 1) dist =1; 			// line가 체크되면 1값을 대입
						else if(what_Select == 2) dist = 2; 	// oval이 체크되면 2값을 대입
						else if(what_Select == 3) dist = 3; 	// rect가 체크되면 3값을 대입
						else if(what_Select == 4) dist = 4;		// roundrect
						else if(what_Select == 5) dist = 5;		// 이등변삼각형
						else if(what_Select == 6) dist = 6;		// 직각삼각형
						else if(what_Select == 7) dist = 7;		// 오각형
						else if(what_Select == 9) dist = 9;		// 텍스트
						else if(what_Select == 10)dist = 10;	// 지우개
						
						DrawPoint dt = new DrawPoint(); 		 	//d 객체생성
						dt.setDist(dist); 					 	//dist 값 대입
						
					 //각각의 값 대입
						dt.setX(x);  
						dt.setY(y);
						dt.setX1(x1);
						dt.setY1(y1);
						
						//선택한 색상 값 저장
						if(e.getButton()==MouseEvent.BUTTON3) {	//오른쪽 마우스
							dt.setC(colorselect_right);
						}else {					//왼쪽 마우스	
							dt.setC(colorselect_left);
						}
						
						dt.setLineWeight(lineWeight);	//굵기 저장
						
						//색채우기 선택 시
						if(if_paint == 1) {
							dt.setPaint(1);	//색 채우기 선택
							//도형 미선택시 -> 배경을 해당 색상으로 변경
							if(what_Select == 0) {
								if_any_shape = 0;
							}
							//도형 선택시 -> 함수로 들어가 채워진 도형 그려줌
							else {
								if_any_shape = 1;
							}
						}
						
						else {
							dt.setPaint(0);		//색 채우기 미선택
						}
						
						vc.add(dt); //vc에 값을 저장하라
					}
					
					
				}
				
				
			});
			
			addMouseMotionListener(new MouseAdapter() {
				
				//마우스가 움직이는 동안 그려지는 모양 보이기
				public void mouseDragged(MouseEvent e) {
					x1 = e.getX();
					y1 = e.getY();
					repaint(); //움직이는 동안 보여지기
					
					// pen 그려지는 이벤트
					if(what_Select == 0) {  
						DrawPoint d = new DrawPoint();  
						d.setDist(1);
						d.setLineWeight(lineWeight); 
						d.setX(x);
						d.setY(y);
						d.setX1(x1);
						d.setY1(y1);
						
						//선택한 색상 값 저장
						if(right == 1) {	//오른쪽 마우스
							d.setC(colorselect_right);
						}else {					//왼쪽 마우스
							d.setC(colorselect_left);
						}
						
						//채우기 선택 및 연필 선택 -> 배경 다 지우고 색깔 바꾸기
						if(if_paint == 1) {
							vc.removeAllElements();	//벡터 전체요소 삭제
							drawPanel.setBackground(d.getC());
						}
						
						vc.add(d);
						x = x1;
						y = y1;

					}
					
					//지우개 그려지는 이벤트
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
	
	
	//메뉴 만들기
	void createMenu() {
		JMenuBar mb = new JMenuBar();
		JMenu file = new JMenu("파일");
		this.setJMenuBar(mb);
		

		mb.add(file);
		
		JMenuItem new_file = new JMenuItem("새로만들기(N)");
		new_file.addActionListener(new MenuActionListener());
		file.add(new_file);
		
		JMenuItem open_file = new JMenuItem("열기(O)");
		open_file.addActionListener(new MenuActionListener());
		file.add(open_file);
		
		JMenuItem save_file = new JMenuItem("저장(S)");
		save_file.addActionListener(new MenuActionListener());
		file.add(save_file);
		
		JMenuItem saveother_file = new JMenuItem("다른 이름으로 저장");
		saveother_file.addActionListener(new MenuActionListener());
		file.add(saveother_file);
		
		file.addSeparator();	//구분선

		JMenuItem print_file = new JMenuItem("인쇄(P)");
		print_file.addActionListener(new MenuActionListener());
		file.add(print_file);
		
		JMenuItem end_file = new JMenuItem("끝내기(X)");
		end_file.addActionListener(new MenuActionListener());
		file.add(end_file);
		
	}
	
	//메뉴 아이템 Action리스너
	class MenuActionListener implements ActionListener{
		JFileChooser chooser = new JFileChooser();
		
		MenuActionListener(){	//파일 다이얼로그 생성
			chooser = new JFileChooser();
		}
		
		//액션 리스너
		public void actionPerformed(ActionEvent e) {
			String cmd = e.getActionCommand();	//메뉴아이템의 문자열 리턴
			
			//새로만들기
			if(cmd.equals("새로만들기(N)")) {
				if(saveStatus == 0) {
					vc.removeAllElements();
					vc.clear();
					repaint();	
				}
				else {
					if(save_Ask() == 2) {	//파일 저장 안할 경우
						vc.removeAllElements();
						vc.clear();
						repaint();
					}
					else {	//파일 저장 하는 경우
						fileSave(0);
						vc.removeAllElements();
						vc.clear();
						repaint();					
					}

				}
			}
			
			//열기
			else if(cmd.equals("열기(O)")) {	
				FileNameExtensionFilter filter = new FileNameExtensionFilter(
					"JPG & GIF Images",	//파일 이름 난에 출력될 문자열
					"jpg", "gif");		//파일 필터로 사용되는 확장자
				
				chooser.setFileFilter(filter); 	//파일 다이얼로그에 파일 필터 설정
				
				int ret = chooser.showOpenDialog(null);	//파일 다이얼로그 출력
				
				//선택 안한 경우
				if (ret != JFileChooser.APPROVE_OPTION) {
					JOptionPane.showMessageDialog(null, "파일을 선택하지 않았습니다", "경고", JOptionPane.WARNING_MESSAGE);
					return;
				}

				if(saveStatus == 0) {	//아무 그림도 안그린 경우
					String filePath = chooser.getSelectedFile().getPath();	//파일 경로명 리턴
					imageLabel.setIcon(new ImageIcon(filePath));//이미지 출력
				}
				else {	//그려져 있는 경우
					vc.removeAllElements();//벡터요소 전체 삭제
					
					if(save_Ask() == 2) {	//파일 저장 안할 경우
						//사용자가 파일을 선택하고 "열기"를 누른 경우
						drawPanel.setForeground(Color.WHITE);
						String filePath = chooser.getSelectedFile().getPath();	//파일 경로명 리턴
						imageLabel.setIcon(new ImageIcon(filePath));//이미지 출력
					}
					
					else {		//파일 저장 하는 경우
						this.fileSave(0);
						drawPanel.setBackground(Color.WHITE);	//배경색 유지
						String filePath = chooser.getSelectedFile().getPath();	//파일 경로명 리턴
						imageLabel.setIcon(new ImageIcon(filePath));//이미지 출력
						repaint();
					}
				}
				saveStatus = 1;
			}
			
			//저장
			else if(cmd.equals("저장(S)")) {
				this.fileSave(0);
			}
			
			//다른 이름으로 저장
			else if(cmd.equals("다른 이름으로 저장")) {
				this.fileSave(1);
			}
			
			//인쇄
			else if(cmd.equals("인쇄(P)")) {
				File temp=  new File("temp.jpg"); //프린트 할 이미지 형성을 위한 임시 이미지 파일
			        
				BufferedImage image = new BufferedImage(drawPanel.getWidth(),
						drawPanel.getHeight(),BufferedImage.TYPE_USHORT_565_RGB);
				
		         try{
		             ImageIO.write(image, "png", temp); 
		          }catch(Exception ee){
		             ee.printStackTrace();
		          }
		         
		       //저장한 이미지를 이미지 아이콘객체에 저장
				ImageIcon temp2= new ImageIcon("temp.jpg");      
				printImage= temp2.getImage();               //저장한 이미지 불러오기
				
				Paper p= new Paper();
				PageFormat format = new PageFormat();         //새 PageFormat 인스턴스가 만들어지고 기본 크기와 방향으로 설정
				format.setPaper(p);  //페이지포맷 페이지영역설정을 인자로
				PrinterJob pj = PrinterJob.getPrinterJob();     //프린트를 컨트롤해주는 객체
				pj.setPrintable(new MyPrintable(), format);     //클래스를 호출하여 작업을 설정하고 선택적으로 사용자와 인쇄 대화 상자를 호출 한 다음 작업 페이지를 인쇄
				try{
					pj.print();                           //프린트
				}catch(PrinterException pe){
					System.out.println("Printingfailed : "+pe.getMessage());
				}
			}
			
			//끝내기
			else if(cmd.equals("끝내기(X)")) {
				if(saveStatus == 0) {
					System.exit(0);
				}
				else {
					if(save_Ask() == 2) {	//파일 저장 안할 경우
						System.exit(0);
					}
					else {
						this.fileSave(1);
						System.exit(0);
					}
					
				}
			}
		}

		//파일 저장하는 메소드(save 0 - 그냥저장 / 1 - 다른이름으로 저장
		void fileSave(int save) {
			//이미지 버퍼 생성
			BufferedImage image = new BufferedImage(drawPanel.getWidth(),
					drawPanel.getHeight(),BufferedImage.TYPE_USHORT_565_RGB);
	
			//버퍼이미지에 drawPanel 저장
			drawPanel.paint(image.getGraphics());
			
			//파일 경로가 비거나 다른이름으로 저장할 때
			if(savePath.length()==0 || save == 1)
				this.fileSave_Fin();
			
			//파일 저장
			if(savePath.length()!=0 && filt != null) {
				try {
					ImageIO.write(image, filt, new File(savePath+"."+filt));
					saveStatus = 0;	//저장 완료
				}catch(IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		
		//다른 이름으로 저장시
		void fileSave_Fin() {
			FileNameExtensionFilter filter[] = {
				 new FileNameExtensionFilter("jpg","jpg"),
				 new FileNameExtensionFilter("gif","gif"),
				 new FileNameExtensionFilter("png","png"),
				 new FileNameExtensionFilter("bmp","bmp")};
			
			chooser.resetChoosableFileFilters();//필터 초기화
			for(int i=0;i<4;i++)
				chooser.addChoosableFileFilter(filter[i]);//파일 필터 설정

			int ret = chooser.showSaveDialog(null);//파일 다이얼 로그 열기

			if(ret!=JFileChooser.APPROVE_OPTION) //선택 안할 시
				return;

			savePath = new String(chooser.getSelectedFile().getPath());
			filt = chooser.getFileFilter().getDescription();//파일 다이얼로그에서 가져온 필터
	
			
			File temp = new File(savePath+"."+filt);
			if(temp.exists()) {//이미 존재하는 파일인경우
				int r = JOptionPane.showConfirmDialog(null,temp.getName()+
						"이(가) 이미 존재합니다. 바꾸시겠습니까?","다른 이름으로 저장 확인",
						JOptionPane.YES_NO_OPTION);
				if(r==JOptionPane.NO_OPTION)
					this.fileSave_Fin();
			}
		}
		//저장할 건지 묻는 메소드
		int save_Ask() {
			//확인 다이얼로그 생성
			int result = JOptionPane.showConfirmDialog(null, "변경 내용을 저장하시겠습니까?", "예나그림판", 
					JOptionPane.YES_NO_OPTION);
			
			//예 선택시
			if(result == JOptionPane.YES_OPTION) {
				fileSave(0);	//파일 저장
				return 0;
			}
			
			//닫기 선택시
			else if(result == JOptionPane.CLOSED_OPTION) {
				return 1;
			}
			
			//아니요 선택시
			else	return 2;
		}

	}
	
		
	//홈 패널
	public JPanel homePanel() {			
		JPanel mainPanel = new JPanel();
		mainPanel.setBackground(new Color(245, 245, 245));
		
		mainPanel.setLayout(null);
		
		//*********************
		//도구 패널
		JPanel tool = new JPanel();
		tool.setBounds(0, 5, 78, 70);
		tool.setBackground(new Color(245, 245, 245));
		tool.setOpaque(false);
		
		//도구에 들어가는 버튼
		ImageIcon pencil = new ImageIcon("Image/pencil.jpg");
		ImageIcon pai = new ImageIcon("Image/paint.jpg");
		ImageIcon te = new ImageIcon("Image/text.jpg");
		ImageIcon era = new ImageIcon("Image/eraser.jpg");
		
		
		//이미지 크기 변경
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
		
		
		//버튼 그룹에 넣기
		ButtonGroup gup = new ButtonGroup();
		
		JRadioButton ra_pencil = new JRadioButton(pen);
		JRadioButton ra_paint = new JRadioButton(paint);
		JRadioButton ra_text = new JRadioButton(text);
		JRadioButton ra_eraser = new JRadioButton(eraser);
			
		ra_pencil.setBorderPainted(true);
		ra_paint.setBorderPainted(true);
		ra_text.setBorderPainted(true);
		ra_eraser.setBorderPainted(true);
	
		//그룹에 추가(페인트 제외)
		gup.add(ra_pencil);
		gup.add(ra_text);
		gup.add(ra_eraser);
		ra_pencil.setSelected(true); //초기 선택 설정
		
		//패널에 라디오 버튼 추가
		tool.add(ra_pencil);
		tool.add(ra_paint);
		tool.add(ra_text);
		tool.add(ra_eraser);
		
		//도구 이벤트 리스너
		ra_pencil.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				what_Select = 0;
			}
		});
		
		ra_paint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(ra_paint.isSelected()) {	//페인트 선택
					if_paint = 1;
					if(what_Select == 0) {
						if_any_shape = 0;		//도형 미선택시(배경 채우기)
					}
					else {

						if_any_shape = 1;		//도형 선택시(도형 채우기)
					}
				}
				else {	//페인트 선택 해제
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
		
		//구분선 패널
		JPanel linePanel = new JPanel();
		linePanel.setBackground(new Color(211, 211, 211));
		linePanel.setBounds(75, 2, 1, 78);
		
		
		//*********************
		
		//도형 패널
		JPanel shapePanel = new JPanel();
		shapePanel.setBounds(75, 5, 130, 70);
		shapePanel.setOpaque(false);
		
		//도형에 들어가는 버튼
		ImageIcon li = new ImageIcon("Image/line.jpg");
		ImageIcon cir = new ImageIcon("Image/circle.jpg");
		ImageIcon rec = new ImageIcon("Image/rect.jpg");
		ImageIcon rrec = new ImageIcon("Image/roundrect.jpg");
		ImageIcon tri = new ImageIcon("Image/triangle.jpg");
		ImageIcon poly = new ImageIcon("Image/polygon.jpg");
		ImageIcon fiv = new ImageIcon("Image/fiveangle.jpg");
		
		//이미지 크기 변경
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
				
		//그룹에 추가
		gup.add(ra_line);
		gup.add(ra_cir);
		gup.add(ra_rec);
		gup.add(ra_rrec);
		gup.add(ra_tri);
		gup.add(ra_poly);
		gup.add(ra_fiv);
		
		//구분선 추가
		ra_line.setBorderPainted(true);
		ra_cir.setBorderPainted(true);
		ra_rec.setBorderPainted(true);
		ra_rrec.setBorderPainted(true);
		ra_tri.setBorderPainted(true);
		ra_poly.setBorderPainted(true);
		ra_fiv.setBorderPainted(true);
		
		//패널에 라디오 버튼 추가
		shapePanel.add(ra_line);
		shapePanel.add(ra_cir);
		shapePanel.add(ra_rec);
		shapePanel.add(ra_rrec);
		shapePanel.add(ra_tri);
		shapePanel.add(ra_poly);
		shapePanel.add(ra_fiv);

		
		//이벤트 리스너
		
		//선택된 도형에 따라 shapeSelect 결정
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

		//구분선 패널
		JPanel linePanel_two = new JPanel();
		linePanel_two.setBackground(new Color(211, 211, 211));
		linePanel_two.setBounds(200, 2, 1, 78);
		
		//*********************
		//텍스트 패널
		
		//글씨체를 가져오는 콤보박스 생성
		GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Font[] farr = e.getAllFonts();//폰트 리스트 폰트 객체로 저장
		fL = new String[farr.length];
		for(int i=0;i<farr.length;i++)//사용 가능한 모든 폰트 리스트 문자열로 저장
			fL[i] = new String(farr[i].getFontName());
 
		//글씨체 콤보박스
		JComboBox fontlist = new JComboBox(fL);

		//폰트 사이즈
		String [] size = {"8", "9", "10","11","12","14","16","18",
							"20","22","24","26","28","30"};
		
		//사이즈 콤보박스
		JComboBox fontSize = new JComboBox(size);
		
		fontlist.setBounds(210, 10, 125, 20);
		fontSize.setBounds(213, 35, 43, 20);
		
		//버튼 이미지 크기 조정
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
		
		//버튼 생성(굵게, 기울이기, 밑줄)
		JCheckBox boltB = new JCheckBox(bolt);
		JCheckBox slideB = new JCheckBox(slide);
		JCheckBox underB = new JCheckBox(under);
		
		//버튼 외곽선
		boltB.setBorderPainted(true);
		slideB.setBorderPainted(true);
		underB.setBorderPainted(true);
		
		//버튼 위치 
		boltB.setBounds(260, 35, 20, 20);
		slideB.setBounds(285, 35, 20, 20);
		underB.setBounds(310, 35, 20, 20);
		
		//콤보박스 선택시
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
		
		//볼드체 선택시
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
		
		//슬라이드 선택시
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
		
		//밑줄 선택시
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
		//구분선 패널
		JPanel linePanel_three = new JPanel();
		linePanel_three.setBackground(new Color(211, 211, 211));
		linePanel_three.setBounds(342, 2, 1, 78);
		
		//*********************
		//선 굵기를 설정해주는 패널
				
		//이미지 크기 조정
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
		
		//라디오 버튼 생성
		JRadioButton line1 = new JRadioButton(line_fir);
		JRadioButton line2 = new JRadioButton(line_sec);
		JRadioButton line3 = new JRadioButton(line_thr);
			
		//그룹에 추가
		size_gup.add(line1);
		size_gup.add(line2);
		size_gup.add(line3);
		
		line1.setSelected(true); //초기 선택 설정
		
		//구분선 추가
		line1.setBorderPainted(true);
		line2.setBorderPainted(true);
		line3.setBorderPainted(true);
		
		//안보이게 설정(선 크기 선택 버튼 선택 전)
		line1.setVisible(false);
		line2.setVisible(false);
		line3.setVisible(false);
		
		//크기 선택 버튼 위치
		line1.setBounds(350, 3, 60, 25);
		line2.setBounds(350, 27, 60, 25);
		line3.setBounds(350, 52, 60, 25);
				
		//선 크기 선택 버튼
		JButton line_Size = new JButton(line_menu);
			
		line_Size.setBounds(357, 10, 45, 57);
		//크기 선택 버튼 클릭시
		line_Size.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {		
				//크기 선택가능
				line1.setVisible(true);
				line2.setVisible(true);
				line3.setVisible(true);
				line_Size.setVisible(false);	//크기 선택버튼 위치에 올라오기 때문에 false
				
				//세 크기중 하나 선택시 원상태 복구
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

		//구분선 패널
		JPanel linePanel_four = new JPanel();
		linePanel_four.setBackground(new Color(211, 211, 211));
		linePanel_four.setBounds(414, 2, 1, 78);
		
		//*********************	
		
		//색상 선택 패널
		ImageIcon color = new ImageIcon("Image/color.jpg");
		JRadioButton fir_btn = new JRadioButton(color);
		fir_btn.setForeground(new Color(0, 0, 205));
		fir_btn.setBorderPainted(true);
		fir_btn.setOpaque(false);
		
		
		JRadioButton sec_btn = new JRadioButton(color);
		sec_btn.setFont(new Font("210 맨발의청춘 L", Font.PLAIN, 12));
		sec_btn.setBorderPainted(true);
		sec_btn.setOpaque(false);
		
		fir_btn.setBounds(420, 5, 40, 60);
		sec_btn.setBounds(465, 5, 40, 60);

		ButtonGroup g_colorChoice = new ButtonGroup();
		g_colorChoice.add(fir_btn);
		g_colorChoice.add(sec_btn);
		
		fir_btn.setSelected(true);	//처음에 첫번째 버튼 선택
		
		//색상 선택시 색변경 패널
		JPanel fir_pan = new JPanel();
		JPanel sec_pan = new JPanel();
		
		fir_pan.setBounds(425, 10, 30, 30);
		fir_pan.setBackground(Color.BLACK);
		
		sec_pan.setBounds(470, 10, 30, 30);
		sec_pan.setBackground(Color.WHITE);
	
		//색1, 색2 표시 레이블
		JLabel fir_label = new JLabel("색 1");
		fir_label.setFont(new Font("다음_SemiBold", Font.PLAIN, 12));
		fir_label.setBounds(430, 45, 30, 13);
		
		JLabel sec_label = new JLabel("\uC0C9 2");
		sec_label.setFont(new Font("다음_SemiBold", Font.PLAIN, 12));
		sec_label.setBounds(475, 45, 30, 13);
		
		//*********************
		
		//색 버튼 패널
		JPanel colorChart_Panel = new JPanel();
		colorChart_Panel.setBounds(515, 0, 210, 70);
		colorChart_Panel.setLayout(null);
		colorChart_Panel.setOpaque(false);
		
		//색상표 Button 생성
		JButton[] color_chart = new JButton[30];
				
		int m = 5;		//일정한 간격을 벌려주는 m값(가로)
		int n = 7;			//일정한 간격을 벌려주는 n값(세로)		
				
		//일정한 간격대로 버튼을 만들어줌
		for(int j=0; j<3; j++) {
			for(int i=0; i<10; i++) {		//한 줄에 10칸
				color_chart[j*10+i] = new JButton();
				color_chart[j*10+i].setBounds(m, n, 17, 17);
				m+=20;
						
				colorChart_Panel.add(color_chart[j*10+i]);
			}
			n += 20;	//세로위치 +10
			m=5;		//처음 가로위치 부터
		}
		
		//색상표 색 지정
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

		//남는 색상 지정
		for(int i=20; i<30;i++)
			color_chart[i].setBackground(new Color(245, 245, 245));		
	
		//************************
		//색 편집 버튼
		ImageIcon colorEd = new ImageIcon("Image/coloredit.jpg");
		Image image_colored = colorEd.getImage();
		Image image_colorEd = image_colored.getScaledInstance(45, 70, java.awt.Image.SCALE_SMOOTH);
		ImageIcon colorEdit = new ImageIcon(image_colorEd);
		
		JButton coloredit = new JButton(colorEdit);
		coloredit.setSize(45, 70);
		coloredit.setLocation(729,5);

		//색 편집 버튼 클릭시 사용자 정의 색 선택 창 
		coloredit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				colorselect = JColorChooser.showDialog(null, "사용자 정의 색 선택", Color.BLACK);
				
				//RadioButton 배경 색상 변경
				//첫번째 RadioButton인 경우
				if(fir_btn.isSelected()) {
					colorselect_left= colorselect;
					fir_pan.setBackground(colorselect);
					//버튼에 추가된 배경 색상 넣어주기
					color_chart[new_btn++].setBackground(colorselect);
				}
				
				//두번째 RadioButton인 경우
				else {
					colorselect_right = colorselect;
					sec_pan.setBackground(colorselect);
					//버튼에 추가된 배경 색상 넣어주기
					color_chart[new_btn++].setBackground(colorselect);
				}
				
			
			}
		});
		
		//************************
		//색 버튼 클릭시 펜 색상 변경
		for(int i=0; i<30; i++) {
			//색상선택시 버튼의 색상도 변경
			color_chart[i].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JButton colorChoice = (JButton)e.getSource();	//선택된버튼 : colorChoice
					colorselect = colorChoice.getBackground();		//색상변경
					
					//RadioButton 배경 색상 변경

					//첫번째 RadioButton인 경우
					if(fir_btn.isSelected()) {
						colorselect_left = colorselect;
						fir_pan.setBackground(colorselect_left);
					}
					
					//두번째 RadioButton인 경우
					else {
						colorselect_right = colorselect;
						sec_pan.setBackground(colorselect_right);
					}
				}
			});
		}
		
		//색 패널 클릭시-> 패널 배경색으로 colorselect 변경
		fir_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				colorselect_left = fir_pan.getBackground();		//색상변경
			}
		});
		
		//secColor을 선택하는 경우 -> 그 패널 배경색으로 colorselect를 변경해준다
		sec_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				colorselect_right = sec_pan.getBackground();		//색상변경
			}
		});
		
		//***********************
		
		colorselect_left = fir_pan.getBackground();
		colorselect_right = sec_pan.getBackground();
		
		//레이블 추가
		JLabel tool_name = new JLabel("도구");
		JLabel shape_name = new JLabel("도형");
		JLabel text_name = new JLabel("글꼴");
		JLabel color_name = new JLabel("색");
		
		tool_name.setFont(new Font("다음_SemiBold", Font.PLAIN, 10));
		tool_name.setBounds(30, 67, 30, 10);
		
		shape_name.setFont(new Font("다음_SemiBold", Font.PLAIN, 10));
		shape_name.setBounds(130, 67, 30, 10);
		
		text_name.setFont(new Font("다음_SemiBold", Font.PLAIN, 10));
		text_name.setBounds(258, 67, 30, 10);
		
		color_name.setFont(new Font("다음_SemiBold", Font.PLAIN, 10));
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

	
	//탭팬 - 보기 패널
	public JPanel editPanel(){
		JPanel mainPanel = new JPanel();
		mainPanel.setBackground(new Color(245, 245, 245));
		mainPanel.setLayout(null);
		
		//**********************************
		//확대&축소 패널
		ImageIcon up = new ImageIcon("Image/size_up.jpg");
		ImageIcon down = new ImageIcon("Image/size_down.jpg");
		
		//이미지 크기 변경
		Image one_up = up.getImage();
		Image two_up = one_up.getScaledInstance(50,70,java.awt.Image.SCALE_SMOOTH);
		ImageIcon size_up = new ImageIcon(two_up);
		Image one_dw = down.getImage();
		Image two_dw = one_dw.getScaledInstance(50,70,java.awt.Image.SCALE_SMOOTH);
		ImageIcon size_down = new ImageIcon(two_dw);
		
		JButton big = new JButton(size_up);
		JButton small = new JButton(size_down);
		
		//버튼 위치 조정
		big.setBounds(3,3,50,70);
		small.setBounds(56, 3, 50, 70);
		
		//zoom in 버튼 클릭시
		big.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//vc요소 확대
				for(int i=0;i<vc.size();i++) {
					DrawPoint d_plus = (DrawPoint)vc.elementAt(i);

					//2배로 확대
					d_plus.setX((int)(d_plus.getX()*2));
					d_plus.setX1((int)(d_plus.getX1()*2));
					d_plus.setY((int)(d_plus.getY()*2));
					d_plus.setY1((int)(d_plus.getY1()*2));
					d_plus.setLineWeight((int)(d_plus.getLineWeight()*2));
					d_plus.setF3(d_plus.getF3()*2);
					
				}
				lineWeight*=2;//선 굵기 조정
				count_plus++;//확대 횟수 ++;
			}
		});

		small.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//vc요소 축소
				for(int i=0;i<vc.size();i++) {
					DrawPoint d_minus = (DrawPoint)vc.elementAt(i);

					//2배로 축소
					d_minus.setX((int)(d_minus.getX()/2));
					d_minus.setX1((int)(d_minus.getX1()/2));
					d_minus.setY((int)(d_minus.getY()/2));
					d_minus.setY1((int)(d_minus.getY1()/2));
					d_minus.setLineWeight((int)(d_minus.getLineWeight()/2));
					d_minus.setF3(d_minus.getF3()/2);
					
				}
				lineWeight/=2;//선 굵기 조정
				count_plus--;//확대 횟수 
			}
		});
		//**********************************
		
		//구분선 패널
		JPanel linePanel = new JPanel();
		linePanel.setBackground(new Color(211, 211, 211));
		linePanel.setBounds(113, 2, 1, 78);
	
		//***********************************
		//전체화면
		ImageIcon all = new ImageIcon("Image/window.jpg");
		
		//이미지 크기 변경
		Image one_all = all.getImage();
		Image two_all = one_all.getScaledInstance(50,70,java.awt.Image.SCALE_SMOOTH);
		ImageIcon win = new ImageIcon(two_all);

		JButton window = new JButton(win);
		
		window.setBounds(120,3,50,70);
		
		//전체화면 버튼 클릭 시
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
				//window 기본 설정
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
			
			window.add(windowFullScreen);	//패널 윈도우에 올려주기
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
	
	//프린트 해주는 클래스
	class MyPrintable implements Printable{
		public int print(Graphics g1, PageFormat pf, int pageIndex){
			g1.translate((int)(pf.getImageableX()), (int)(pf.getImageableY()));// 그래픽 원점을 프린트할 이미지의 좌표로 변환합니다.
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
