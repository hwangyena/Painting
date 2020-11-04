package term_one;

import java.awt.BorderLayout;

import java.awt.Graphics;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;

import javax.swing.JFrame;

import javax.swing.JPanel;

import javax.swing.JRadioButton;

public class Test extends JFrame implements ActionListener

{

    ButtonGroup  bg = new ButtonGroup();

    JRadioButton rb1 = new JRadioButton("Rectangle");

    JRadioButton rb2 = new JRadioButton("circle");

    JRadioButton rb3 = new JRadioButton("line");

    JPanel top = new JPanel();

    

    int status = 0;//어ㄸ너 도형을 그리는 중 인지

    

    public Test()

    {

        bg.add(rb1);

        bg.add(rb2);

        bg.add(rb3);//그룹화(한개만 눌러지게)

        

        top.add(rb1);

        top.add(rb2);

        top.add(rb3);//패널에 라디오 버튼 넣기        

        

        add(top, BorderLayout.NORTH);//화면에 라디오 버튼이 들어있는 패널 넣기

        

        setSize(500,500);

        setVisible(true);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);       

        

        rb1.addActionListener(this);

        rb2.addActionListener(this);

        rb3.addActionListener(this);//버튼을 누루면 public void actionPerformed(ActionEvent e)실행

    }




    @Override

    public void actionPerformed(ActionEvent e)

    {

        if(e.getSource() == rb1)

        {

            status = 1;            

        }else if(e.getSource() == rb2)

        {

            status = 2;

        }else if(e.getSource() == rb3)

        {

            status = 3;

        }

        

        repaint();//화면 다시 그리기

    }




    @Override

    public void paint(Graphics g)

    {

        super.paint(g);

        switch(status)

        {

            case 1:

                g.drawRect(150, 150, 200, 200);

                break;

                case 2:

                    g.drawOval(150, 150, 200, 200);

                break;

                case 3:

                    g.drawLine(150, 150, 350, 350);

                break;

        }

        

        

    }

    

    




    public static void main(String[] args)

    {

        new Test();

    }

}