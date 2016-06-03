package application;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicMenuUI.ChangeHandler;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {
	
	private Color		현재색상 = Color.BLACK;
	private tools		현재도구 = tools.연필;
	enum tools { 연필, 지우개, 사각형, 원}
	private ColorPicker colorPicker = new ColorPicker();
	
	double 시작x;
	double 시작y;
	
	ComboBox ThicknessChoice=new ComboBox<>(); //나중에 <Image>로 변환
	Spinner spinner = new Spinner();
	
	@Override
	public void start(Stage primaryStage) {
		BorderPane bPane = new BorderPane();
		
		// 캔버스, 레이어 생성
		Canvas canvas = new Canvas(700,500);
		Canvas layerCanvas = new Canvas(700,500);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		GraphicsContext layer = layerCanvas.getGraphicsContext2D();
		double canvasWidth = gc.getCanvas().getWidth();
		double canvasHeight = gc.getCanvas().getHeight();         
		
		
		// 캔버스 테두리 표시
		gc.strokeRect(0, 0, canvasWidth, canvasHeight);
		

		//좌측 도구상자
		GridPane toolBox = addGridPane();
		ToolBar colorPickerBar = new ToolBar();

		//색깔판
		colorPickerBar.getItems().add(colorPicker);
		colorPicker.setPrefSize(100, 30);
		colorPicker.setValue(Color.BLACK);
		//색깔판 리스너
		colorPicker.setOnAction(new EventHandler() {   
			public void handle(Event t) {
				현재색상 = colorPicker.getValue();
			}
		});
		
		//선굵기 spinner
		spinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(1, 100));  
		spinner.setEditable(true);
		spinner.setPrefSize(80, 30);

		//우측 박스
		GridPane rightBox = new GridPane();
		rightBox.setStyle("-fx-background-color: #999999");
		rightBox.add(colorPicker,0,0);
		rightBox.add(spinner, 0, 2);

		
		
		layerCanvas.addEventHandler(MouseEvent.MOUSE_PRESSED, 
				new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				layer.clearRect(0, 0,canvasWidth, canvasHeight);
				
				if(현재도구 == tools.연필) {
					double lineWidth = (double) spinner.getValue();
					double x = event.getX() - lineWidth/2;
					double y = event.getY() - lineWidth/2;
					gc.setFill(현재색상);
					gc.fillOval(x, y, lineWidth, lineWidth);
				}
				else if(현재도구 == tools.지우개) {
					double lineWidth = (double) spinner.getValue();
					double x = event.getX() - lineWidth/2;
					double y = event.getY() - lineWidth/2;
					gc.setFill(Color.WHITE);
					gc.fillOval(x, y, lineWidth, lineWidth);
				}
				else {
					시작x = event.getX();
					시작y = event.getY();
				}
			}
		});

		layerCanvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, 
				new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {	
				layer.clearRect(0, 0,canvasWidth, canvasHeight);
				
				if(현재도구 == tools.연필) {
					double lineWidth = (double) spinner.getValue();
					double x = event.getX() - lineWidth/2;
					double y = event.getY() - lineWidth/2;
					gc.setFill(현재색상);
					gc.setLineWidth(1);
					gc.fillOval(x, y, lineWidth, lineWidth);
				}
				else if(현재도구 == tools.지우개) {
					double lineWidth = (double) spinner.getValue();
					double x = event.getX() - lineWidth/2;
					double y = event.getY() - lineWidth/2;
					gc.setFill(Color.WHITE);
					gc.setLineWidth(1);
					gc.fillOval(x, y, lineWidth, lineWidth);
				}
				else if(현재도구 == tools.사각형) {
					double lineWidth = (double) spinner.getValue();
					double w = event.getX()-시작x;
					double h = event.getY()-시작y;
					layer.setStroke(현재색상);
					layer.setLineWidth(lineWidth);
					layer.strokeRect(시작x, 시작y, w, h);
				}
				else if(현재도구 == tools.원) {
					double lineWidth = (double) spinner.getValue();
					double w = event.getX()-시작x;
					double h = event.getY()-시작y;
					layer.setStroke(현재색상);
					layer.setLineWidth(lineWidth);
					layer.strokeOval(시작x, 시작y, w, h);
				}
			}
		});
		
		layerCanvas.addEventHandler(MouseEvent.MOUSE_MOVED, 
				new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {	
				layer.clearRect(0, 0,canvasWidth, canvasHeight);
				
				if(현재도구 == tools.연필) {
					double lineWidth = (double) spinner.getValue();
					double x = event.getX() - lineWidth/2;
					double y = event.getY() - lineWidth/2;
					
					layer.setFill(Color.WHITE);
					layer.setStroke(Color.BLACK);
					layer.setLineWidth(1);
					layer.strokeOval(x, y, lineWidth, lineWidth);
				}
				else if(현재도구 == tools.지우개) {
					double lineWidth = (double) spinner.getValue();
					double x = event.getX() - lineWidth/2;
					double y = event.getY() - lineWidth/2;

					layer.setFill(Color.WHITE);
					layer.setStroke(Color.BLACK);
					layer.setLineWidth(1);
					layer.strokeOval(x, y, lineWidth, lineWidth);
				}
			}
		});

		layerCanvas.addEventHandler(MouseEvent.MOUSE_RELEASED, 
				new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				layer.clearRect(0, 0,canvasWidth, canvasHeight);
				
				if(현재도구 == tools.사각형) {
					double lineWidth = (double) spinner.getValue();
					double w = event.getX()-시작x;
					double h = event.getY()-시작y;
					gc.setStroke(현재색상);
					gc.setLineWidth(lineWidth);
					gc.strokeRect(시작x, 시작y, w, h);
				}
				else if(현재도구 == tools.원) {
					double lineWidth = (double) spinner.getValue();
					double w = event.getX()-시작x;
					double h = event.getY()-시작y;
					gc.setStroke(현재색상);
					gc.setLineWidth(lineWidth);
					gc.strokeOval(시작x, 시작y, w, h);
				}
			}
		});

		//상단 메뉴
		Menu fileMenu = new Menu("파일");
		Menu modifyMenu = new Menu("편집");
		Menu helpMenu = new Menu("지원");
		//fileMenu.getItems().addAll();			메뉴에 아이템 추가 
		MenuBar menu = new MenuBar();
		menu.getMenus().addAll(fileMenu,modifyMenu,helpMenu);

		bPane.setLeft(toolBox);
		bPane.setTop(menu);
		bPane.setRight(rightBox);
		
		Pane pane = new Pane();
		pane.getChildren().add(canvas);
		pane.getChildren().add(layerCanvas);
		layerCanvas.toFront();
		bPane.setCenter(pane);

		primaryStage.setTitle("BCSD Paint");
		primaryStage.setScene(new Scene(bPane,900,600));
		primaryStage.show();

	}
	
	
	
	public GridPane addGridPane(){
		GridPane pane = new GridPane();  	
		pane.setAlignment(Pos.TOP_LEFT);	//안쪽 버튼들의 정리 위치
		pane.setPadding(new Insets(5));		//얼마나 띄우고 버튼을 배열할까?
		pane.setHgap(3);					//버튼사이의 가로 gap
		pane.setVgap(3);					//세로 gap
		pane.setStyle("-fx-background-color: #999999");		//canvas와 구별하기 위한 배경 색

		//빈 그리드 생성
		
		
		
		for(int i=0;i<10;i++){
			for(int j=0;j<2;j++){
				Button temp = new Button();
				temp.setMinSize(30, 30);
				pane.add(temp, j, i);
			}
		}

		Button 연필 = new Button();
		연필.setMinSize(30, 30);
		연필.setText(String.valueOf("P"));
		연필.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				현재도구 = tools.연필;
				현재색상 = colorPicker.getValue();
			}	
		});
		pane.add(연필, 0, 0);
		
		Button 지우개 = new Button();
		지우개.setMinSize(30, 30);
		지우개.setText(String.valueOf("E"));
		지우개.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				현재도구 = tools.지우개;
				현재색상 = Color.WHITE;
			}	
		});
		pane.add(지우개, 1, 0);
		
		Button 사각형 = new Button();
		사각형.setMinSize(30, 30);
		사각형.setText(String.valueOf("ㅁ"));
		사각형.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				현재도구 = tools.사각형;
				현재색상 = colorPicker.getValue();
			}	
		});
		pane.add(사각형, 0, 1);
		
		Button 원 = new Button();
		원.setMinSize(30, 30);
		원.setText(String.valueOf("ㅇ"));
		원.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				현재도구 = tools.원;
				현재색상 = colorPicker.getValue();
			}	
		});
		pane.add(원, 1, 1);
		
		
		return pane;
	}


	public static void main(String[] args) {
		launch(args);
	}
}
