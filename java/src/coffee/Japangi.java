package coffee;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import javax.swing.JOptionPane;
import javax.swing.border.Border;

public class Japangi extends Frame implements ActionListener {
	//상품 테이블 읽어와서 저장하는 2차원배열
	String coffee[][] = new String[6][4];
	
	private Dimension dimen, dimen1;
	private int xpos, ypos;
	private Image img1, img2, img3, img4, img5, img6, imgPick;
	
	Font fontP15 = new Font("SansSerif", Font.PLAIN, 15);
	Font fontP20 = new Font("SansSerif", Font.PLAIN, 20);
	Font fontP25 = new Font("SansSerif", Font.PLAIN, 25);
	Font fontP30 = new Font("SansSerif", Font.PLAIN, 30);
	
	Font fontB15 = new Font("SansSerif", Font.BOLD, 15);
	Font fontB20 = new Font("SansSerif", Font.BOLD, 20);
	Font fontB25 = new Font("SansSerif", Font.BOLD, 25);
	Font fontB30 = new Font("SansSerif", Font.BOLD, 30);

	Font fontB40 = new Font("SansSerif", Font.BOLD, 40);

	int price[] = {1000, 2000, 3000, 3000, 4000, 3000}; // 각 상품 가격
	int sumPrice[] = {0, 0, 0, 0, 0, 0}; // 각 상품 가격 합산용
	int count[] = {1, 1, 1, 1, 1, 1}; // 각각 상품 지정 개수
	int qtyCount[] = {0, 0, 0, 0, 0, 0}; // 각 상품 개별 선택 개수
	int countTot = 0;
	
	String coffeeName[] = {"에스프레소", "아메리카노", "카페라떼", "카푸치노", "카라멜마키아또", "카페모카"};
	
	int moneyTot = 0; // 입금잔액
	
	int pTot = 0; // 총 결제금액
	
	Label lbTitle = new Label("커피 자판기");
	
	Label lbQty1 = new Label("수량 "+this.count[0]+"잔",Label.CENTER);
	Label lbQty2 = new Label("수량 "+this.count[1]+"잔",Label.CENTER);
	Label lbQty3 = new Label("수량 "+this.count[2]+"잔",Label.CENTER);
	Label lbQty4 = new Label("수량 "+this.count[3]+"잔",Label.CENTER);
	Label lbQty5 = new Label("수량 "+this.count[4]+"잔",Label.CENTER);
	Label lbQty6 = new Label("수량 "+this.count[5]+"잔",Label.CENTER);
	
	Button btn1 = new Button(coffeeName[0]);
	Button btn2 = new Button(coffeeName[1]);
	Button btn3 = new Button(coffeeName[2]);
	Button btn4 = new Button(coffeeName[3]);
	Button btn5 = new Button(coffeeName[4]);
	Button btn6 = new Button(coffeeName[5]);
	
	Label lbQty1Price = new Label(price[0]+"원",Label.CENTER);
	Label lbQty2Price = new Label(price[1]+"원",Label.CENTER);
	Label lbQty3Price = new Label(price[2]+"원",Label.CENTER);
	Label lbQty4Price = new Label(price[3]+"원",Label.CENTER);
	Label lbQty5Price = new Label(price[4]+"원",Label.CENTER);
	Label lbQty6Price = new Label(price[5]+"원",Label.CENTER);
	
	Label lbPick = new Label("선택상품");
	Label lbName = new Label("호롤로커피");
	Label lbQtyPick = new Label("수량 : 0잔");
	Label lbPrice = new Label("금액 : 0원");
	Label lbQtyTot = new Label("총 수량 : 0잔");
	
	Label lbInMoney = new Label("투입금액");
	Button btn1k = new Button("1000원");
	Button btn5k = new Button("5000원");
	Button btn10k = new Button("10000원");
	Button btnPay = new Button("결제하기");
	Button btnReset = new Button("초기화");
	Label lbInMoneyTot = new Label("현재잔액 :  0원");
	Label lbPayMoneyTot = new Label("결제금액 :  0원");
	Button btnAdmin = new Button("관리자");
	
	
	Japangi() {
		super("자판기");
		this.setSize(1000,850);
		this.getData();
		this.center();
		this.init();
		this.start();
		this.setVisible(true); //이 부분이 있어야 창이 보이게 생성 됨
	}
	void init() { //initialize 줄임말로 쓴 init 	
		
		this.setLayout(null); //absolute 방식으로 레이어를 배치할수 있게 해줌
		
		img1 = Toolkit.getDefaultToolkit().getImage("coffee_img/Espresso.jpg");
		img2 = Toolkit.getDefaultToolkit().getImage("coffee_img/Americano.jpg");
		img3 = Toolkit.getDefaultToolkit().getImage("coffee_img/CaffeLatte.jpg");
		img4 = Toolkit.getDefaultToolkit().getImage("coffee_img/Cappuccino.jpg");
		img5 = Toolkit.getDefaultToolkit().getImage("coffee_img/CaramelMacchiato.jpg");
		img6 = Toolkit.getDefaultToolkit().getImage("coffee_img/CaffeMocha.jpg");
		imgPick = Toolkit.getDefaultToolkit().getImage("coffee_img/HoroloCoffeeLogo.png");
//		imgPick = Toolkit.getDefaultToolkit().getImage("coffee_img/HoroloCoffeeLogo.jpg? jpeg?");
		
		this.add(lbTitle);
		lbTitle.setFont(fontB40);
		lbTitle.setBounds(375, 50, 250, 50);
		
		this.add(lbQty1);
		lbQty1.setFont(fontB20);
		lbQty1.setBounds(105, 270, 100, 30);
		this.add(lbQty2);
		lbQty2.setFont(fontB20);
		lbQty2.setBounds(285, 270, 100, 30);
		this.add(lbQty3);
		lbQty3.setFont(fontB20);
		lbQty3.setBounds(465, 270, 100, 30);
		this.add(lbQty4);
		lbQty4.setFont(fontB20);
		lbQty4.setBounds(105, 550, 100, 30);
		this.add(lbQty5);
		lbQty5.setFont(fontB20);
		lbQty5.setBounds(285, 550, 100, 30);
		this.add(lbQty6);
		lbQty6.setFont(fontB20);
		lbQty6.setBounds(465, 550, 100, 30);
		
		this.add(btn1);
		btn1.setFont(fontB15);
		btn1.setBounds(90, 300, 130, 30);
		this.add(btn2);
		btn2.setFont(fontB15);
		btn2.setBounds(270, 300, 130, 30);
		this.add(btn3);
		btn3.setFont(fontB15);
		btn3.setBounds(450, 300, 130, 30);
		this.add(btn4);
		btn4.setFont(fontB15);
		btn4.setBounds(90, 580, 130, 30);
		this.add(btn5);
		btn5.setFont(fontB15);
		btn5.setBounds(270, 580, 130, 30);
		this.add(btn6);
		btn6.setFont(fontB15);
		btn6.setBounds(450, 580, 130, 30);
		
		this.add(lbQty1Price);
		lbQty1Price.setFont(fontB20);
		lbQty1Price.setBounds(105, 330, 100, 30);
		this.add(lbQty2Price);
		lbQty2Price.setFont(fontB20);
		lbQty2Price.setBounds(285, 330, 100, 30);
		this.add(lbQty3Price);
		lbQty3Price.setFont(fontB20);
		lbQty3Price.setBounds(465, 330, 100, 30);
		this.add(lbQty4Price);
		lbQty4Price.setFont(fontB20);
		lbQty4Price.setBounds(105, 610, 100, 30);
		this.add(lbQty5Price);
		lbQty5Price.setFont(fontB20);
		lbQty5Price.setBounds(285, 610, 100, 30);
		this.add(lbQty6Price);
		lbQty6Price.setFont(fontB20);
		lbQty6Price.setBounds(465, 610, 100, 30);
		
		this.add(lbInMoney);
		lbInMoney.setFont(fontB20);
		lbInMoney.setBounds(90, 670, 130, 30);
		this.add(btn1k);
		btn1k.setFont(fontB30);
		btn1k.setBounds(90, 710, 130, 40);
		this.add(btn5k);
		btn5k.setFont(fontB30);
		btn5k.setBounds(270, 710, 130, 40);
		this.add(btn10k);
		btn10k.setFont(fontB30);
		btn10k.setBounds(450, 710, 130, 40);
		
		this.add(lbInMoneyTot);
		lbInMoneyTot.setFont(fontB30);
		lbInMoneyTot.setBounds(90, 770, 310, 40);
		this.add(lbPayMoneyTot);
		lbPayMoneyTot.setFont(fontB30);
		lbPayMoneyTot.setBounds(450, 770, 310, 40);
		
		this.add(lbPick);
		lbPick.setFont(fontB40);
		lbPick.setBounds(680, 140, 180, 40);
		this.add(lbName);
		lbName.setFont(fontB25);
		lbName.setBounds(670, 420, 200, 40);
		this.add(lbQtyPick);
		lbQtyPick.setFont(fontB25);
		lbQtyPick.setBounds(670, 470, 200, 40);
		this.add(lbPrice);
		lbPrice.setFont(fontB25);
		lbPrice.setBounds(670, 520, 200, 40);
		this.add(lbQtyTot);
		lbQtyTot.setFont(fontB25);
		lbQtyTot.setBounds(670, 570, 200, 40);
		
		this.add(btnPay);
		btnPay.setFont(fontB40);
		btnPay.setBounds(620, 680, 300, 70);
		this.add(btnAdmin);
		btnAdmin.setFont(fontB20);
		btnAdmin.setBounds(770, 760, 70, 40);
		this.add(btnReset);
		btnReset.setFont(fontB20);
		btnReset.setBounds(850, 760, 70, 40);
		
	}
	public void paint(Graphics g) {
		g.drawImage(img1, 80, 110, 150, 150, this);
		g.drawImage(img2, 260, 110, 150, 150, this);
		g.drawImage(img3, 440, 110, 150, 150, this);
		g.drawImage(img4, 80, 390, 150, 150, this);
		g.drawImage(img5, 260, 390, 150, 150, this);
		g.drawImage(img6, 440, 390, 150, 150, this);
		g.drawImage(imgPick, 670, 200, 200, 200, this);
		g.drawRect(80, 110, 150, 150); // 1
		g.drawRect(260, 110, 150, 150); // 2
		g.drawRect(440, 110, 150, 150); // 3
		g.drawRect(80, 390, 150, 150); // 4
		g.drawRect(260, 390, 150, 150); // 5
		g.drawRect(440, 390, 150, 150); // 6
		g.drawRoundRect(620, 110, 300, 540, 20, 20); // 선택창
	}
	void start() {
		btn1.addActionListener(this);
		btn2.addActionListener(this);
		btn3.addActionListener(this);
		btn4.addActionListener(this);
		btn5.addActionListener(this);
		btn6.addActionListener(this);
		
		btn1k.addActionListener(this);
		btn5k.addActionListener(this);
		btn10k.addActionListener(this);
		btnPay.addActionListener(this);
		btnAdmin.addActionListener(this);
		btnReset.addActionListener(this);
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}
	void center() {
		dimen = Toolkit.getDefaultToolkit().getScreenSize();
		dimen1 = this.getSize();
		xpos = (int) (dimen.getWidth() / 2 - dimen1.getWidth() / 2);
		ypos = (int) (dimen.getHeight() / 2 - dimen1.getHeight() / 2);
		this.setLocation(xpos, ypos);
	}
	public void actionPerformed(ActionEvent e) {
		
		String coffeeCount1 = Integer.toString(count[0]);
		String coffeeCount2 = Integer.toString(count[1]);
		String coffeeCount3 = Integer.toString(count[2]);
		String coffeeCount4 = Integer.toString(count[3]);
		String coffeeCount5 = Integer.toString(count[4]);
		String coffeeCount6 = Integer.toString(count[5]);
		
		if(e.getSource() == this.btn1) {
			if(count[0] == 0) {msg("품절 되었습니다"); return;}
			this.lbName.setText(coffeeName[0]);
			this.count[0]--;
			this.qtyCount[0]++;
			this.countTot++;
			this.sumPrice[0] += this.price[0];
			this.pTot += this.price[0];
			this.lbQty1.setText("수량 "+this.count[0]+"잔");
			this.lbQtyPick.setText("수량 : "+this.qtyCount[0]+"잔");
			this.lbPrice.setText("금액 : "+this.sumPrice[0]+"원");
			this.imgPick = Toolkit.getDefaultToolkit().getImage("coffee_img/Espresso.jpg");
			this.repaint();
		}
		if(e.getSource() == this.btn2) {
			if(count[1] == 0) {msg("품절 되었습니다"); return;}
			this.lbName.setText(this.coffeeName[1]);
			this.count[1]--;
			this.qtyCount[1]++;
			this.countTot++;
			this.sumPrice[1] += this.price[1];
			this.pTot += this.price[1];
			this.lbQty2.setText("수량 "+this.count[1]+"잔");
			this.lbQtyPick.setText("수량 : "+this.qtyCount[1]+"잔");
			this.lbPrice.setText("금액 : "+this.sumPrice[1]+"원");
			this.imgPick = Toolkit.getDefaultToolkit().getImage("coffee_img/Americano.jpg");
			this.repaint();
		}
		if(e.getSource() == this.btn3) {
			if(count[2] == 0) {msg("품절 되었습니다"); return;}
			this.lbName.setText(this.coffeeName[2]);
			this.count[2]--;
			this.qtyCount[2]++;
			this.countTot++;
			this.sumPrice[2] += this.price[2];
			this.pTot += this.price[2];
			this.lbQty3.setText("수량 "+this.count[2]+"잔");
			this.lbQtyPick.setText("수량 : "+this.qtyCount[2]+"잔");
			this.lbPrice.setText("금액 : "+this.sumPrice[2]+"원");
			this.imgPick = Toolkit.getDefaultToolkit().getImage("coffee_img/CaffeLatte.jpg");
			this.repaint();
		}
		if(e.getSource() == this.btn4) {
			if(count[3] == 0) {msg("품절 되었습니다"); return;}
			this.lbName.setText(this.coffeeName[3]);
			this.count[3]--;
			this.qtyCount[3]++;
			this.countTot++;
			this.sumPrice[3] += this.price[3];
			this.pTot += this.price[3];
			this.lbQty4.setText("수량 "+this.count[3]+"잔");
			this.lbQtyPick.setText("수량 : "+this.qtyCount[3]+"잔");
			this.lbPrice.setText("금액 : "+this.sumPrice[3]+"원");
			this.imgPick = Toolkit.getDefaultToolkit().getImage("coffee_img/Cappuccino.jpg");
			this.repaint();
		}
		if(e.getSource() == this.btn5) {
			if(count[4] == 0) {msg("품절 되었습니다"); return;}
			this.lbName.setText(this.coffeeName[4]);
			this.count[4]--;
			this.qtyCount[4]++;
			this.countTot++;
			this.sumPrice[4] += this.price[4];
			this.pTot += this.price[4];
			this.lbQty5.setText("수량 "+this.count[4]+"잔");
			this.lbQtyPick.setText("수량 : "+this.qtyCount[4]+"잔");
			this.lbPrice.setText("금액 : "+this.sumPrice[4]+"원");
			this.imgPick = Toolkit.getDefaultToolkit().getImage("coffee_img/CaramelMacchiato.jpg");
			this.repaint();
		}
		if(e.getSource() == this.btn6) {
			if(count[5] == 0) {msg("품절 되었습니다"); return;}
			this.lbName.setText(this.coffeeName[5]);
			this.count[5]--;
			this.qtyCount[5]++;
			this.countTot++;
			this.sumPrice[5] += this.price[5];
			this.pTot += this.price[5];
			this.lbQty6.setText("수량 "+this.count[5]+"잔");
			this.lbQtyPick.setText("수량 : "+this.qtyCount[5]+"잔");
			this.lbPrice.setText("금액 : "+this.sumPrice[5]+"원");
			this.imgPick = Toolkit.getDefaultToolkit().getImage("coffee_img/CaffeMocha.jpg");
			this.repaint();
		}
		if(e.getSource() == this.btn1k) {
			int money = 1000;
			this.moneyTot += money;
		}
		if(e.getSource() == this.btn5k) {
			int money = 5000;
			this.moneyTot += money;
		}
		if(e.getSource() == this.btn10k) {
			int money = 10000;
			this.moneyTot += money;
		}
		this.lbQtyTot.setText("총 수량 : "+this.countTot+"잔");
		this.lbInMoneyTot.setText("현재잔액 :  "+this.moneyTot+"원");
		this.lbPayMoneyTot.setText("결제금액 :  "+this.pTot+"원");
		if(e.getSource() == this.btnPay) {
			if(0 == this.pTot) {
				msg("먼저 커피를 선택 해 주세요");
				return;
			}
			if(this.moneyTot >= this.pTot) {
				update(1, coffeeCount1);
				update(2, coffeeCount2);
				update(3, coffeeCount3);
				update(4, coffeeCount4);
				update(5, coffeeCount5);
				update(6, coffeeCount6);
				msg("감사합니다!");
				if(this.moneyTot > this.pTot) {
					int result = 0;					
					result = this.moneyTot - this.pTot;
					msg("거스름돈은 "+result+"원 입니다.");
				}
				reset();
			}
			if(this.moneyTot < this.pTot) {
				msg("결제 금액이 부족합니다!");
				return;
			}
		}
		if(e.getSource() == this.btnAdmin) {getData(); Japangi_admin ja = new Japangi_admin();}
		if(e.getSource() == this.btnReset) {reset(); getData();}
	}
	void reset() {
		this.lbName.setText("호롤로커피");
		
		this.qtyCount[0] = 0;
		this.qtyCount[1] = 0;
		this.qtyCount[2] = 0;
		this.qtyCount[3] = 0;
		this.qtyCount[4] = 0;
		this.qtyCount[5] = 0;
		this.countTot = 0;
		this.sumPrice[0] = 0;
		this.sumPrice[1] = 0;
		this.sumPrice[2] = 0;
		this.sumPrice[3] = 0;
		this.sumPrice[4] = 0;
		this.sumPrice[5] = 0;
		this.pTot = 0;
		this.moneyTot = 0;
		this.lbQtyPick.setText("수량 : 0잔");
		this.lbPrice.setText("금액 : 0원");
						
		this.lbQtyTot.setText("총 수량 : "+this.countTot+"잔");
		this.lbInMoneyTot.setText("현재잔액 :  "+this.moneyTot+"원");
		this.lbPayMoneyTot.setText("결제금액 :  "+this.pTot+"원");

		imgPick = Toolkit.getDefaultToolkit().getImage("coffee_img/HoroloCoffeeLogo.png");
		this.repaint();
	}
	void getData() {
		try {
			Class.forName("org.gjt.mm.mysql.Driver");
		} catch (ClassNotFoundException ee) {
			System.exit(0);
		}
		Connection conn = null;
		String url = "jdbc:mysql://127.0.0.1:3306/dw202?useUnicode=true&characterEncoding=utf8"; // 나를 지정할 때 127.0.0.1 을 씀
		String id = "root";
		String pass = "qwer";
		Statement stmt = null;
		ResultSet rs = null;
		String query = "select * from coffee_admin";
		try {
			conn = DriverManager.getConnection(url, id, pass);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			int count=0;
			while (rs.next()) {
				coffee[count][0] = rs.getInt("idx")+"";
				coffee[count][1] = rs.getString("name");
				coffee[count][2] = rs.getString("count");
				coffee[count][3] = rs.getString("price");				
				count++;
			}
			
			this.count[0] = Integer.parseInt(coffee[0][2]);
			this.count[1] = Integer.parseInt(coffee[1][2]);
			this.count[2] = Integer.parseInt(coffee[2][2]);
			this.count[3] = Integer.parseInt(coffee[3][2]);
			this.count[4] = Integer.parseInt(coffee[4][2]);
			this.count[5] = Integer.parseInt(coffee[5][2]);
			this.lbQty1.setText("수량 "+this.count[0]+"잔");
			this.lbQty2.setText("수량 "+this.count[1]+"잔");
			this.lbQty3.setText("수량 "+this.count[2]+"잔");
			this.lbQty4.setText("수량 "+this.count[3]+"잔");
			this.lbQty5.setText("수량 "+this.count[4]+"잔");
			this.lbQty6.setText("수량 "+this.count[5]+"잔");
			
			this.coffeeName[0] = (coffee[0][1]);
			this.coffeeName[1] = (coffee[1][1]);
			this.coffeeName[2] = (coffee[2][1]);
			this.coffeeName[3] = (coffee[3][1]);
			this.coffeeName[4] = (coffee[4][1]);
			this.coffeeName[5] = (coffee[5][1]);
			this.btn1.setLabel(coffeeName[0]);
			this.btn2.setLabel(coffeeName[1]);
			this.btn3.setLabel(coffeeName[2]);
			this.btn4.setLabel(coffeeName[3]);
			this.btn5.setLabel(coffeeName[4]);
			this.btn6.setLabel(coffeeName[5]);
			
			this.price[0] = Integer.parseInt(coffee[0][3]); // string을 int로 바꿔
			this.price[1] = Integer.parseInt(coffee[1][3]);
			this.price[2] = Integer.parseInt(coffee[2][3]);
			this.price[3] = Integer.parseInt(coffee[3][3]);
			this.price[4] = Integer.parseInt(coffee[4][3]);
			this.price[5] = Integer.parseInt(coffee[5][3]);
			this.lbQty1Price.setText(price[0]+"원");
			this.lbQty2Price.setText(price[1]+"원");
			this.lbQty3Price.setText(price[2]+"원");
			this.lbQty4Price.setText(price[3]+"원");
			this.lbQty5Price.setText(price[4]+"원");
			this.lbQty6Price.setText(price[5]+"원");
			
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException ee) {
			System.err.println("error = " + ee.toString());
		}
	}
	boolean update(int idx, String count) {
		try {
			Class.forName("org.gjt.mm.mysql.Driver");
		} catch (ClassNotFoundException ee) {
			System.exit(0);
		}
		Connection conn = null;
		String url = "jdbc:mysql://127.0.0.1:3306/dw202?useUnicode=true&characterEncoding=utf8"; // 나를 지정할 때 127.0.0.1 을 씀
		String id = "root";
		String pass = "qwer";
		String query = "update coffee_admin set count = ? where idx = ?";
		try {
			conn = DriverManager.getConnection(url, id, pass);
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, count);
			pstmt.setInt(2, idx);
			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
			System.out.println("수정성공");
		} catch (SQLException ee) {
			System.err.println("회원 정보수정 실패!!"+ee);
			return false;
		}
		return true;
	}
	void msg(String msg) {
		final Dialog dlg = new Dialog(this, "알림 메세지창", true);
		dlg.setLayout(null);
		Label lbMsg = new Label(msg);
		
		dlg.add(lbMsg);
		lbMsg.setFont(fontB20);
		lbMsg.setBounds(60, 100, 300, 30);
		
		dlg.setSize(400, 200);
		dlg.setLocation(800, 400);
		
		dlg.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dlg.setVisible(false);
			}
		});
		dlg.setVisible(true);
	}
}