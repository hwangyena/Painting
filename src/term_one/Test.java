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

    

    int status = 0;//��� ������ �׸��� �� ����

    

    public Test()

    {

        bg.add(rb1);

        bg.add(rb2);

        bg.add(rb3);//�׷�ȭ(�Ѱ��� ��������)

        

        top.add(rb1);

        top.add(rb2);

        top.add(rb3);//�гο� ���� ��ư �ֱ�        

        

        add(top, BorderLayout.NORTH);//ȭ�鿡 ���� ��ư�� ����ִ� �г� �ֱ�

        

        setSize(500,500);

        setVisible(true);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);       

        

        rb1.addActionListener(this);

        rb2.addActionListener(this);

        rb3.addActionListener(this);//��ư�� ����� public void actionPerformed(ActionEvent e)����

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

        

        repaint();//ȭ�� �ٽ� �׸���

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