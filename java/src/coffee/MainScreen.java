package coffee;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class MainScreen extends Frame implements ActionListener{
	
	int coin=0; //현재투입금액
	int totCoin=0; //총결제금액
	
	//커피 금액 수량 커피명
	int coffeePrice[] = {2001,2002,2003,2004,2005,2006};
	int coffeeCnt[] = {1,2,3,4,5,6};
	String coffeeName[] = {"커페라떼","에스프레소","콜드블루","아메리카노","바닐라라떼","카페모카"};
	
	//선택한 커피 순번
	int selCoffee = 0; //0~5이 차례대로커피 선택임 배열로 저장해놔서 
	
	boolean makeCheck=true;
	
	

	private Dimension dimen, dimen1;
	private int xpos, ypos;
	Font font20 = new Font("SansSerif", Font.PLAIN, 20);
	Font font30 = new Font("SansSerif", Font.BOLD, 30);
	Font font50 = new Font("SansSerif", Font.BOLD, 50);
	
	private Image img1;
	private Image img2;
	private Image img3;
	private Image img4;
	private Image img5;
	private Image img6;	
	private Image imgSel;//내가선택한커피
	
	Label lbTitle = new Label("커피 - 자판기");
	Label lbCoffee1Cnt = new Label("수량 "+coffeeCnt[0]+"개",Label.CENTER);
	Label lbCoffee2Cnt = new Label("수량 "+coffeeCnt[1]+"개",Label.CENTER);
	Label lbCoffee3Cnt = new Label("수량 "+coffeeCnt[2]+"개",Label.CENTER);
	Label lbCoffee4Cnt = new Label("수량 "+coffeeCnt[3]+"개",Label.CENTER);
	Label lbCoffee5Cnt = new Label("수량 "+coffeeCnt[4]+"개",Label.CENTER);
	Label lbCoffee6Cnt = new Label("수량 "+coffeeCnt[5]+"개",Label.CENTER);
	Label lbCoffeeSelCnt = new Label("0원",Label.CENTER);
	Label lbCoffee1Price = new Label(coffeePrice[0]+"원",Label.CENTER);
	Label lbCoffee2Price = new Label(coffeePrice[1]+"원",Label.CENTER);
	Label lbCoffee3Price = new Label(coffeePrice[2]+"원",Label.CENTER);
	Label lbCoffee4Price = new Label(coffeePrice[3]+"원",Label.CENTER);
	Label lbCoffee5Price = new Label(coffeePrice[4]+"원",Label.CENTER);
	Label lbCoffee6Price = new Label(coffeePrice[5]+"원",Label.CENTER);
	Label lbCoffeeSelPrice = new Label("커피 - 자판기");
	Label lbSelTitle = new Label("선택대기중..");
	Label lbJan1 = new Label("현재잔액  : ");
	Label lbJan2 = new Label(coin+"원",Label.RIGHT);
	Label lbCoin1 = new Label("상품금액  : ");
	Label lbCoin2 = new Label("0원",Label.RIGHT);
	
	Label lbCoin = new Label("< 투입금액 >");
	Label lbSel = new Label("< 선택한 상품 >");
	Label lbSelPrice = new Label("가격 :    0원");

	Button btnCofee1 = new Button(coffeeName[0]);
	Button btnCofee2 = new Button(coffeeName[1]);
	Button btnCofee3 = new Button(coffeeName[2]);
	Button btnCofee4 = new Button(coffeeName[3]);
	Button btnCofee5 = new Button(coffeeName[4]);
	Button btnCofee6 = new Button(coffeeName[5]);
	Button btn1000 = new Button("1000원");
	Button btn5000 = new Button("5000원");
	Button btn10000 = new Button("10000원");
	Button btnBuy = new Button("구매하기");
	
	
	
	MainScreen()
	{
		super("커피 - 자판기");
		this.setSize(1200,1050);
		this.center();
		this.init();
		this.startEvent();
		this.setVisible(true);
	}
	void init() {	
		//카페라떼
		img1 = Toolkit.getDefaultToolkit().getImage("img/angel_coffee.png");
		//에스프레소
		img2 = Toolkit.getDefaultToolkit().getImage("img/twosome_coffee.png");
		//콜드블루
		img3 = Toolkit.getDefaultToolkit().getImage("img/pascucci_coffee.png");
		//아메리카노
		img4 = Toolkit.getDefaultToolkit().getImage("img/kfc_coffee.png");
		//바닐라라떼
		img5 = Toolkit.getDefaultToolkit().getImage("img/dunkin_coffee.png");
		//카페모카
		img6 = Toolkit.getDefaultToolkit().getImage("img/isaac_coffee.png");
		//카페모카
		imgSel = Toolkit.getDefaultToolkit().getImage("img/default.png");
		
		
		this.setLayout(null);
		this.add(lbTitle);
		this.add(lbCoffee1Cnt);this.add(lbCoffee1Price);this.add(btnCofee1);
		this.add(lbCoffee2Cnt);this.add(lbCoffee2Price);this.add(btnCofee2);
		this.add(lbCoffee3Cnt);this.add(lbCoffee3Price);this.add(btnCofee3);
		this.add(lbCoffee4Cnt);this.add(lbCoffee4Price);this.add(btnCofee4);
		this.add(lbCoffee5Cnt);this.add(lbCoffee5Price);this.add(btnCofee5);
		this.add(lbCoffee6Cnt);this.add(lbCoffee6Price);this.add(btnCofee6);
		this.add(lbCoffeeSelCnt);this.add(lbCoffeeSelPrice);
		this.add(btn1000);this.add(btn5000);this.add(btn10000);
		this.add(btnBuy);
		this.add(lbCoin);
		this.add(lbCoin1);
		this.add(lbCoin2);
		this.add(lbSelTitle);
		this.add(lbJan1);
		this.add(lbJan2);
		this.add(lbSel);
		this.add(lbSelPrice);
		
		
		
		
		lbTitle.setBounds(400, 40, 600, 50);lbTitle.setFont(font50);
		//상품1		
		lbCoffee1Cnt.setBounds(100, 300, 100, 30);lbCoffee1Cnt.setFont(font20);
		lbCoffee1Price.setBounds(100, 330, 100, 30);lbCoffee1Price.setFont(font20);
		btnCofee1.setBounds(50, 360, 200, 50);btnCofee1.setFont(font30);

		//상품2		
		lbCoffee2Cnt.setBounds(400, 300, 100, 30);lbCoffee2Cnt.setFont(font20);
		lbCoffee2Price.setBounds(400, 330, 100, 30);lbCoffee2Price.setFont(font20);
		btnCofee2.setBounds(350, 360, 200, 50);btnCofee2.setFont(font30);

		//상품3		
		lbCoffee3Cnt.setBounds(700, 300, 100, 30);lbCoffee3Cnt.setFont(font20);
		lbCoffee3Price.setBounds(700, 330, 100, 30);lbCoffee3Price.setFont(font20);
		btnCofee3.setBounds(650, 360, 200, 50);btnCofee3.setFont(font30);
		
		//상품4		
		lbCoffee4Cnt.setBounds(100, 650, 100, 30);lbCoffee4Cnt.setFont(font20);
		lbCoffee4Price.setBounds(100, 680, 100, 30);lbCoffee4Price.setFont(font20);
		btnCofee4.setBounds(50, 710, 200, 50);btnCofee4.setFont(font30);

		//상품5		
		lbCoffee5Cnt.setBounds(400, 650, 100, 30);lbCoffee5Cnt.setFont(font20);
		lbCoffee5Price.setBounds(400, 680, 100, 30);lbCoffee5Price.setFont(font20);
		btnCofee5.setBounds(350, 710, 200, 50);btnCofee5.setFont(font30);

		//상품6		
		lbCoffee6Cnt.setBounds(700, 650, 100, 30);lbCoffee6Cnt.setFont(font20);
		lbCoffee6Price.setBounds(700, 680, 100, 30);lbCoffee6Price.setFont(font20);
		btnCofee6.setBounds(650, 710, 200, 50);btnCofee6.setFont(font30);
		
		lbCoin.setBounds(50, 800, 150, 50);lbCoin.setFont(font20);
		btn1000.setBounds(50, 850, 250, 50);btn1000.setFont(font30);
		btn5000.setBounds(350, 850, 250, 50);btn5000.setFont(font30);
		btn10000.setBounds(650, 850, 250, 50);btn10000.setFont(font30);
		
		lbJan1.setBounds(50, 900, 150, 50);lbJan1.setFont(font30);
		lbJan2.setBounds(200, 900, 250, 50);lbJan2.setFont(font30);
		lbCoin1.setBounds(500, 900, 150, 50);lbCoin1.setFont(font30);
		lbCoin2.setBounds(650, 900, 250, 50);lbCoin2.setFont(font30);
		

		lbSel.setBounds(950, 160, 250, 50);lbSel.setFont(font30);
		lbSelTitle.setBounds(950, 460, 250, 50);lbSelTitle.setFont(font30);
		lbSelPrice.setBounds(950, 510, 250, 50);lbSelPrice.setFont(font30);
		btnBuy.setBounds(900, 600, 250, 50);btnBuy.setFont(font30);
		

		
	}	
	public void paint(Graphics g) {	
		
			g.drawImage(img1, 50, 100, 200, 200, this);
			g.drawImage(img2, 350, 100, 200, 200, this);
			g.drawImage(img3, 650, 100, 200, 200, this);
			g.drawImage(img4, 50, 450, 200, 200, this);
			g.drawImage(img5, 350, 450, 200, 200, this);
			g.drawImage(img6, 650, 450, 200, 200, this);
			
			g.drawImage(imgSel, 950, 250, 200, 200, this);
	}
	void startEvent()
	{
		btnCofee1.addActionListener(this);
		btnCofee2.addActionListener(this);
		btnCofee3.addActionListener(this);
		btnCofee4.addActionListener(this);
		btnCofee5.addActionListener(this);
		btnCofee6.addActionListener(this);
		btn1000.addActionListener(this);
		btn5000.addActionListener(this);
		btn10000.addActionListener(this);
		btnBuy.addActionListener(this);
		
	}
	void center() {
		dimen = Toolkit.getDefaultToolkit().getScreenSize();
		dimen1 = this.getSize();
		xpos = (int) (dimen.getWidth() / 2 - dimen1.getWidth() / 2);
		ypos = (int) (dimen.getHeight() / 2 - dimen1.getHeight() / 2);
		this.setLocation(xpos, ypos);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}

	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == btnCofee1)
		{
			selCoffee=0;//1번커피선택
			imgSel = Toolkit.getDefaultToolkit().getImage("img/angel_coffee.png");
			totCoin = coffeePrice[0];
			lbCoin2.setText(totCoin+"원");	
			lbSelPrice.setText(totCoin+"원");
			lbSelTitle.setText(coffeeName[0]);
			this.repaint();
					
		}
		else if(e.getSource() == btnCofee2)
		{
			selCoffee=1;//2번커피선택
			imgSel = Toolkit.getDefaultToolkit().getImage("img/twosome_coffee.png");
			totCoin = coffeePrice[1];
			lbCoin2.setText(totCoin+"원");
			lbSelPrice.setText(totCoin+"원");
			lbSelTitle.setText(coffeeName[1]);
			this.repaint();
		}
		else if(e.getSource() == btnCofee3)
		{
			selCoffee=2;//3번커피선택
			imgSel = Toolkit.getDefaultToolkit().getImage("img/kfc_coffee.png");
			totCoin = coffeePrice[2];
			lbCoin2.setText(totCoin+"원");
			lbSelPrice.setText(totCoin+"원");
			lbSelTitle.setText(coffeeName[2]);
			this.repaint();
		}
		else if(e.getSource() == btnCofee4)
		{
			selCoffee=3;//4번커피선택
			imgSel = Toolkit.getDefaultToolkit().getImage("img/dunkin_coffee.png");
			totCoin = coffeePrice[3];
			lbSelPrice.setText(totCoin+"원");
			lbSelTitle.setText(coffeeName[3]);
			lbCoin2.setText(totCoin+"원");
			
			this.repaint();
		}
		else if(e.getSource() == btnCofee5)
		{
			selCoffee=4;//5번커피선택
			imgSel = Toolkit.getDefaultToolkit().getImage("img/isaac_coffee.png");
			totCoin = coffeePrice[4];
			lbSelPrice.setText(totCoin+"원");
			lbSelTitle.setText(coffeeName[4]);
			lbCoin2.setText(totCoin+"원");
			this.repaint();
		}
		else if(e.getSource() == btnCofee6)
		{
			selCoffee=5;//6번커피선택
			imgSel = Toolkit.getDefaultToolkit().getImage("img/isaac_coffee.png");
			totCoin = coffeePrice[5];
			lbSelPrice.setText(totCoin+"원");
			lbSelTitle.setText(coffeeName[5]);
			lbCoin2.setText(totCoin+"원");
			this.repaint();
		}
		else if(e.getSource() == btn1000)
		{
			coin+=1000;
			lbJan2.setText(coin+"원");
		}
		else if(e.getSource() == btn5000)
		{
			coin+=5000;
			lbJan2.setText(coin+"원");
		}
		else if(e.getSource() == btn10000)
		{
			coin+=10000;
			lbJan2.setText(coin+"원");
		}
		else if(e.getSource() == btnBuy)
		{
			//잔액이 최소 3000원이상 있을경우에만 진행
			if(coin < 3000)
			{
				msg("최소3000원이상 금액을투입하세요.");
				return;
			}
			//상품선택메세지 경고
			if(totCoin==0)
			{
				msg("구매할 커피를 선택해주세요.");
				return;
			}
			
			//수량체크 메서드
			countCheck(selCoffee);
			
			if(makeCheck==true)
			{			
				//선택한 커피 차감
				coin-=totCoin;
				//차감된금액표시
				lbJan2.setText(coin+"원");		
				
				
				//제조표시하기			
				try {
					lbSelTitle.setText("맛있는");
					Thread.sleep(1000);
				} catch (InterruptedException e1) {	}
				try {
					lbSelTitle.setText("커피를");
					Thread.sleep(1000);
				} catch (InterruptedException e1) {	}
				try {
					lbSelTitle.setText("제조합니다.");
					Thread.sleep(1000);
				} catch (InterruptedException e1) {	}
				
				imgSel = Toolkit.getDefaultToolkit().getImage("img/ing.gif");
				this.repaint();
			}
			else
			{
				makeCheck=true;
			}
			
			
		}	
	}
	void countCheck(int selCoffee)
	{
		if(coffeeCnt[selCoffee]==0)
		{
			msg("수량이없어서 구매가불가능합니다.");			
			makeCheck=false;
			return;
		}		
		if(selCoffee==0)
		{
			
			coffeeCnt[0]--;
			lbCoffee1Cnt.setText("수량 "+coffeeCnt[0]+"개");
			
		}
		else if(selCoffee==1)
		{
			
			coffeeCnt[1]--;
			lbCoffee2Cnt.setText("수량 "+coffeeCnt[1]+"개");
			
		}
		else if(selCoffee==2)
		{
			coffeeCnt[2]--;
			lbCoffee3Cnt.setText("수량 "+coffeeCnt[2]+"개");
			
		}
		else if(selCoffee==3)
		{
			coffeeCnt[3]--;
			lbCoffee4Cnt.setText("수량 "+coffeeCnt[3]+"개");
			
		}
		else if(selCoffee==4)
		{
			coffeeCnt[4]--;
			lbCoffee5Cnt.setText("수량 "+coffeeCnt[4]+"개");
			
		}
		else if(selCoffee==5)
		{
			coffeeCnt[5]--;
			lbCoffee6Cnt.setText("수량 "+coffeeCnt[5]+"개");
			
		}
	}
	void msg(String msg)
	{
		final Dialog dlg = new Dialog(this, "알림 메세지창", true);
		dlg.setLayout(null);
		Label lbMsg = new Label(msg);
		
		dlg.add(lbMsg);	lbMsg.setFont(font20);
		lbMsg.setBounds(100, 100, 450, 30);
		
		dlg.setSize(500, 250);
		dlg.setLocation(700, 400);
		
		dlg.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dlg.setVisible(false);
			}
		});
		dlg.setVisible(true);
	}
	
	void search(String name)
	{
		try {
			Class.forName("org.gjt.mm.mysql.Driver");
		} catch (ClassNotFoundException ee) {
			System.out.println("드라이브가 연결안됨.");
			System.exit(0);
		}
		Connection conn = null;
		String url = "jdbc:mysql://127.0.0.1:3306/dw202?"
				+ "useUnicode=true&characterEncoding=utf8";				
		String id = "root";
		String pass = "qwer";
		Statement stmt = null;
		ResultSet rs = null;
		String query = "select * from member";
		try {
			conn = DriverManager.getConnection(url, id, pass);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			
			while (rs.next()) {
				if(rs.getString(4).equals(name))
				{

				}
			}
			
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException ee) {
			System.err.println("error = " + ee.toString());
		}
	}
	void searchAll()
	{
		try {
			Class.forName("org.gjt.mm.mysql.Driver");
		} catch (ClassNotFoundException ee) {
			System.out.println("드라이브가 연결안됨.");
			System.exit(0);
		}
		Connection conn = null;
		String url = "jdbc:mysql://127.0.0.1:3306/dw202?"
				+ "useUnicode=true&characterEncoding=utf8";				
		String id = "root";
		String pass = "qwer";
		Statement stmt = null;
		ResultSet rs = null;
		String query = "select * from member";
		try {
			conn = DriverManager.getConnection(url, id, pass);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			String result="";
			while (rs.next()) {
				result += rs.getInt(1) + " / " + rs.getString(2)
				+ " / " + rs.getString(3)
				+ " / " + rs.getString(4)
				+ " / " + rs.getString(5)+"\n";			
			}
		
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException ee) {
			System.err.println("error = " + ee.toString());
		}
	}

}









