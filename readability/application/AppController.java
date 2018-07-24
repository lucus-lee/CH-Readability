package application;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class AppController {
	
    @FXML
    private AnchorPane mainStage;

    @FXML
    private Text countText;

    @FXML
    private TextArea inputArea;

    @FXML
    private AnchorPane bottom;

    @FXML
    private Button readButton;

    @FXML
    private Button classifyButton;

    @FXML
    private Button clearButton;

    @FXML
    private TextArea resultArea;
    
    @FXML
    private CheckBox poemCheck;

    @FXML
    private CheckBox oldCheck;
    
    @FXML
    private Button moreInfo;
    
    @FXML
    private Text text1;
    
    @FXML
    private Text text2;

	// used when showing new stage/scene
	private Main mainApp;
	
	// used for getting new objects
	
	
	/**
     * Is called by the main application to give a reference back to itself.
     * Also give reference to AutoSpellingTextArea
     * 
     * 
     * @param mainApp
     */
	public void setMainApp(Main mainApp) {
		this.mainApp = mainApp;
		
	}
	
	@FXML
	void initialize() {
		inputArea.setWrapText(true);
		
	}
	
    @FXML
    private void handleClassify() {
    	
    	
    	String text = inputArea.getText();
//    	Main.showClassifyDialog(text);
    	
    	
    	if(!text.equals("")) {
    		document.Document doc = new document.Document(text);
    		
			Main.showClassifyDialog(doc.gradeOutput(doc.classifyNoRepeat()));
    		
    	}else {
			// reset text field
//			resultArea.setText("");
			Main.showInputErrorDialog("(っ °Д °;)っ好慌，没有看到任何文字！请你再输入一遍吧！");
			
		}  
    }
    
    
    

    @FXML
    void handleClear() {
    	inputArea.clear();
    }

    @FXML
    void handleReadability() {
    	String text = inputArea.getText();
    	double index = 0.0;
    	
    	//Check input correctness
    	
		if(!text.equals("")) {
			
			// create Document representation of  current text
			document.Document doc = new document.Document(text);
			
			
			try {
				index = doc.getScore();
			} catch (ArithmeticException e) {
				Main.showInputErrorDialog("╥﹏╥ 宝宝只认识中文哦！");
			}
			
			if (poemCheck.isSelected()) index += 4.2920812;
			if (oldCheck.isSelected()) index += 32.3677141;
			
			
			//get string with two decimal places for index to
			String res = String.format("%.2f", index);
			String level = calLevel(index);
			
			
			// display string in text field
//			if (index == 0) resultArea.setText("〒▽〒  宝宝会努力学外语！");
//			else resultArea.setText("您输入的文章易读指数是： " + res + "\n" + "这篇文章的适合年级是：" + level);
			
			text1.setText("您输入的文章易读指数是： " + res);
			text2.setText("这篇文章的适合年级是：" + level);
			
			int count = doc.getNumWords();
			countText.setText("Total: " + Integer.toString(count));
			
		}
		else {
			// reset text field
//			resultArea.setText("");
			Main.showInputErrorDialog("(っ °Д °;)っ好慌，没有看到任何文字！请你再输入一遍吧！");
			
		}   	
    }
    

    @FXML
    void handleMoreInfo() {
    	
    	Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("关于软件");
		alert.setHeaderText("软件版本： 1.1.0 是光特别版\r\n" + 
    			"作者：欢欢欢欢欢  tutu_huan@163.com" +
				"版权说明：本软件为是光四季诗歌特别制作。");
		alert.setContentText(
    			"使用说明：\r\n" + "1. 阅读指数的计算基于荆溪昱（1995）的可读性研究。\r\n" + 
    			"2. 软件无法对语义、内容、连贯度做出分析。\r\n" + 
    			"3. 汉字分级基于人教版教材。" + 
    			"计算结果仅供教学参考。\r\n");
		
		
		alert.showAndWait();
    }
    

	private String calLevel(double index) {
		String res = "";
		if (index == 0.0)  res = "宝宝会努力学外语！";
		else if (index <= 20.0)  res = "一年级";
		else if (index <= 30.0)  res = "二年级";
		else if (index <= 50.0)  res = "三年级";
		else if (index <= 60.0)  res = "四年级";
		else if (index <= 80.0)  res = "五年级";
		else if (index <= 90.0)  res = "六年级";
		else if (index <= 110.0)  res = "七年级";
		else if (index <= 130.0)  res = "八年级";
		else if (index <= 140.0)  res = "九年级";
		else if (index < 170.0)  res = "高中以上";
		else if (index >= 170.0)  res = "专家";
		
		
		return res;
	}


}
