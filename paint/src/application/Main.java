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
	
	private Color		������� = Color.BLACK;
	private tools		���絵�� = tools.����;
	enum tools { ����, ���찳, �簢��, ��}
	private ColorPicker colorPicker = new ColorPicker();
	
	double ����x;
	double ����y;
	
	ComboBox ThicknessChoice=new ComboBox<>(); //���߿� <Image>�� ��ȯ
	Spinner spinner = new Spinner();
	
	@Override
	public void start(Stage primaryStage) {
		BorderPane bPane = new BorderPane();
		
		// ĵ����, ���̾� ����
		Canvas canvas = new Canvas(700,500);
		Canvas layerCanvas = new Canvas(700,500);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		GraphicsContext layer = layerCanvas.getGraphicsContext2D();
		double canvasWidth = gc.getCanvas().getWidth();
		double canvasHeight = gc.getCanvas().getHeight();         
		
		
		// ĵ���� �׵θ� ǥ��
		gc.strokeRect(0, 0, canvasWidth, canvasHeight);
		

		//���� ��������
		GridPane toolBox = addGridPane();
		ToolBar colorPickerBar = new ToolBar();

		//������
		colorPickerBar.getItems().add(colorPicker);
		colorPicker.setPrefSize(100, 30);
		colorPicker.setValue(Color.BLACK);
		//������ ������
		colorPicker.setOnAction(new EventHandler() {   
			public void handle(Event t) {
				������� = colorPicker.getValue();
			}
		});
		
		//������ spinner
		spinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(1, 100));  
		spinner.setEditable(true);
		spinner.setPrefSize(80, 30);

		//���� �ڽ�
		GridPane rightBox = new GridPane();
		rightBox.setStyle("-fx-background-color: #999999");
		rightBox.add(colorPicker,0,0);
		rightBox.add(spinner, 0, 2);

		
		
		layerCanvas.addEventHandler(MouseEvent.MOUSE_PRESSED, 
				new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				layer.clearRect(0, 0,canvasWidth, canvasHeight);
				
				if(���絵�� == tools.����) {
					double lineWidth = (double) spinner.getValue();
					double x = event.getX() - lineWidth/2;
					double y = event.getY() - lineWidth/2;
					gc.setFill(�������);
					gc.fillOval(x, y, lineWidth, lineWidth);
				}
				else if(���絵�� == tools.���찳) {
					double lineWidth = (double) spinner.getValue();
					double x = event.getX() - lineWidth/2;
					double y = event.getY() - lineWidth/2;
					gc.setFill(Color.WHITE);
					gc.fillOval(x, y, lineWidth, lineWidth);
				}
				else {
					����x = event.getX();
					����y = event.getY();
				}
			}
		});

		layerCanvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, 
				new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {	
				layer.clearRect(0, 0,canvasWidth, canvasHeight);
				
				if(���絵�� == tools.����) {
					double lineWidth = (double) spinner.getValue();
					double x = event.getX() - lineWidth/2;
					double y = event.getY() - lineWidth/2;
					gc.setFill(�������);
					gc.setLineWidth(1);
					gc.fillOval(x, y, lineWidth, lineWidth);
				}
				else if(���絵�� == tools.���찳) {
					double lineWidth = (double) spinner.getValue();
					double x = event.getX() - lineWidth/2;
					double y = event.getY() - lineWidth/2;
					gc.setFill(Color.WHITE);
					gc.setLineWidth(1);
					gc.fillOval(x, y, lineWidth, lineWidth);
				}
				else if(���絵�� == tools.�簢��) {
					double lineWidth = (double) spinner.getValue();
					double w = event.getX()-����x;
					double h = event.getY()-����y;
					layer.setStroke(�������);
					layer.setLineWidth(lineWidth);
					layer.strokeRect(����x, ����y, w, h);
				}
				else if(���絵�� == tools.��) {
					double lineWidth = (double) spinner.getValue();
					double w = event.getX()-����x;
					double h = event.getY()-����y;
					layer.setStroke(�������);
					layer.setLineWidth(lineWidth);
					layer.strokeOval(����x, ����y, w, h);
				}
			}
		});
		
		layerCanvas.addEventHandler(MouseEvent.MOUSE_MOVED, 
				new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {	
				layer.clearRect(0, 0,canvasWidth, canvasHeight);
				
				if(���絵�� == tools.����) {
					double lineWidth = (double) spinner.getValue();
					double x = event.getX() - lineWidth/2;
					double y = event.getY() - lineWidth/2;
					
					layer.setFill(Color.WHITE);
					layer.setStroke(Color.BLACK);
					layer.setLineWidth(1);
					layer.strokeOval(x, y, lineWidth, lineWidth);
				}
				else if(���絵�� == tools.���찳) {
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
				
				if(���絵�� == tools.�簢��) {
					double lineWidth = (double) spinner.getValue();
					double w = event.getX()-����x;
					double h = event.getY()-����y;
					gc.setStroke(�������);
					gc.setLineWidth(lineWidth);
					gc.strokeRect(����x, ����y, w, h);
				}
				else if(���絵�� == tools.��) {
					double lineWidth = (double) spinner.getValue();
					double w = event.getX()-����x;
					double h = event.getY()-����y;
					gc.setStroke(�������);
					gc.setLineWidth(lineWidth);
					gc.strokeOval(����x, ����y, w, h);
				}
			}
		});

		//��� �޴�
		Menu fileMenu = new Menu("����");
		Menu modifyMenu = new Menu("����");
		Menu helpMenu = new Menu("����");
		//fileMenu.getItems().addAll();			�޴��� ������ �߰� 
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
		pane.setAlignment(Pos.TOP_LEFT);	//���� ��ư���� ���� ��ġ
		pane.setPadding(new Insets(5));		//�󸶳� ���� ��ư�� �迭�ұ�?
		pane.setHgap(3);					//��ư������ ���� gap
		pane.setVgap(3);					//���� gap
		pane.setStyle("-fx-background-color: #999999");		//canvas�� �����ϱ� ���� ��� ��

		//�� �׸��� ����
		
		
		
		for(int i=0;i<10;i++){
			for(int j=0;j<2;j++){
				Button temp = new Button();
				temp.setMinSize(30, 30);
				pane.add(temp, j, i);
			}
		}

		Button ���� = new Button();
		����.setMinSize(30, 30);
		����.setText(String.valueOf("P"));
		����.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				���絵�� = tools.����;
				������� = colorPicker.getValue();
			}	
		});
		pane.add(����, 0, 0);
		
		Button ���찳 = new Button();
		���찳.setMinSize(30, 30);
		���찳.setText(String.valueOf("E"));
		���찳.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				���絵�� = tools.���찳;
				������� = Color.WHITE;
			}	
		});
		pane.add(���찳, 1, 0);
		
		Button �簢�� = new Button();
		�簢��.setMinSize(30, 30);
		�簢��.setText(String.valueOf("��"));
		�簢��.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				���絵�� = tools.�簢��;
				������� = colorPicker.getValue();
			}	
		});
		pane.add(�簢��, 0, 1);
		
		Button �� = new Button();
		��.setMinSize(30, 30);
		��.setText(String.valueOf("��"));
		��.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				���絵�� = tools.��;
				������� = colorPicker.getValue();
			}	
		});
		pane.add(��, 1, 1);
		
		
		return pane;
	}


	public static void main(String[] args) {
		launch(args);
	}
}
