package sample;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class GamePlayController {

    @FXML
    private AnchorPane GamePlayRoot;
    @FXML
    private ImageView peaShooterBuy;
    @FXML
    private ImageView repeaterBuy;
    @FXML
    private ImageView cherryBombBuy;
    @FXML
    private ImageView jalapenoBuy;
    @FXML
    private ImageView wallnutBuy;
    @FXML
    private ImageView sunBuy;
    @FXML
    private Label sunCountLabel;
    @FXML
    private ImageView GameMenuLoaderButton;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private int levelNumber;
    @FXML
    private GridPane lawn_grid;
    //private GamePlay g;
    private ArrayList<Plant> allPlants;
    private ArrayList<Zombie> allZombies;
    private ArrayList<LawnMower> allMowers;
    private static int sunCount;
    private final int LANE1;
    private final int LANE2;
    private final int LANE3;
    private final int LANE4;
    private final int LANE5;
    public static boolean gameStatus;
    public static Timeline sunTimeline;
    public static Timeline spZ1;
    public static Timeline spZ2;
    private static Label sunCountDisplay;
    private Level l;


    public GamePlayController() {
        allPlants = new ArrayList<Plant>();
        allZombies = new ArrayList<Zombie>();
        allMowers=new ArrayList<LawnMower>();
        LANE1 = 50;
        LANE2 = 150;
        LANE3 = 250;
        LANE4 = 350;
        LANE5 = 450;
        this.l = null;
    }


    public void initialize() throws Exception {
        gameStatus = true;
        sunCount = 2000;
        sunCountDisplay = sunCountLabel;
        sunCountDisplay.setText("2000");
        Random rand = new Random();
        //NormalZombie n = new NormalZombie(1024, 450, GamePlayRoot);
        //n.moveZombie();

        fallingSuns(rand);
        zombieSpawner1(rand);
        zombieSpawner2(rand);
    }

    public static void updateSunCount(int val)
    {
        sunCount+=val;
        getSunCountLabel().setText(Integer.toString(sunCount));
    }

    public static Label getSunCountLabel()
    {
        return(sunCountDisplay);
    }

    public void fallingSuns(Random rand) {
        Timeline sunDropper = new Timeline(new KeyFrame(Duration.seconds(10), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int sunPosition = rand.nextInt(850);
                sunPosition += 100;
                Sun s = new Sun(sunPosition, 0, GamePlayRoot, true);
                s.dropSun();
            }
        }));
        sunDropper.setCycleCount(Timeline.INDEFINITE);
        sunDropper.play();
        sunTimeline = sunDropper;
    }

    public void zombieSpawner1(Random rand){
        Timeline spawnZombie1 = new Timeline(new KeyFrame(Duration.seconds(15), event -> {
            int lane;
            int laneNumber = rand.nextInt(5);
            if(laneNumber==0)
                lane = LANE1;
            else if(laneNumber==1)
                lane = LANE2;
            else if(laneNumber==2)
                lane = LANE3;
            else if(laneNumber==3)
                lane = LANE4;
            else
                lane = LANE5;
            try
            {
                if(l.getZombieList1().get(0)==0) {
                    System.out.println("normal");
                    l.spawnNormalZombie(GamePlayRoot, lane, laneNumber);
                    l.getZombieList1().remove(0);
                }
                else if(l.getZombieList1().get(0)==1)
                {
                    System.out.println("cone");
                    l.spawnConeZombie(GamePlayRoot, lane, laneNumber);
                    l.getZombieList1().remove(0);
                }
                else if(l.getZombieList1().get(0)==2)
                {
                    System.out.println("bucket");
                    l.spawnBucketZombie(GamePlayRoot, lane, laneNumber);
                    l.getZombieList1().remove(0);
                }
            }
            catch(IndexOutOfBoundsException e)
            {
                endZombieSpawner1();
            }
        }));

        spawnZombie1.setCycleCount(Timeline.INDEFINITE);
        spawnZombie1.play();
        spZ1 = spawnZombie1;
    }

    public void zombieSpawner2(Random rand){
        Timeline spawnZombie2 = new Timeline(new KeyFrame(Duration.seconds(27), event -> {
            int lane;
            int laneNumber = rand.nextInt(5);
            if(laneNumber==0)
                lane = LANE1;
            else if(laneNumber==1)
                lane = LANE2;
            else if(laneNumber==2)
                lane = LANE3;
            else if(laneNumber==3)
                lane = LANE4;
            else
                lane = LANE5;
            try
            {
                if(l.getZombieList2().get(0)==0) {
                    System.out.println("normal");
                    l.spawnNormalZombie(GamePlayRoot, lane, laneNumber);
                    l.getZombieList2().remove(0);
                }
                else if(l.getZombieList2().get(0)==1)
                {
                    System.out.println("cone");
                    l.spawnConeZombie(GamePlayRoot, lane, laneNumber);
                    l.getZombieList2().remove(0);
                }
                else if(l.getZombieList2().get(0)==2)
                {
                    System.out.println("bucket");
                    l.spawnBucketZombie(GamePlayRoot, lane, laneNumber);
                    l.getZombieList2().remove(0);
                }
            }
            catch(IndexOutOfBoundsException e)
            {
                endZombieSpawner2();
            }
        }));

        spawnZombie2.setCycleCount(Timeline.INDEFINITE);
        spawnZombie2.play();
        spZ2 = spawnZombie2;
    }

    public void endZombieSpawner1()
    {
        spZ1.stop();
    }

    public void endZombieSpawner2()
    {
        spZ2.stop();
    }

    @FXML
    public void initData(int levelNumber) {
        //System.out.println("initData");
        this.levelNumber = levelNumber;
        SidebarElement.getSideBarElements(levelNumber, GamePlayRoot);
        allMowers.add(new LawnMower(249,LANE1+20,GamePlayRoot,0));
        allMowers.add(new LawnMower(249,LANE2+20,GamePlayRoot,1));
        allMowers.add(new LawnMower(249,LANE3+20,GamePlayRoot,2));
        allMowers.add(new LawnMower(243,LANE4+20,GamePlayRoot,3));
        allMowers.add(new LawnMower(236,LANE5+20,GamePlayRoot,4));
        Level l = new Level(this.levelNumber);
        this.l = l;
    }

    @FXML
    void getGridPosition(MouseEvent event) throws IOException {
        if (SidebarElement.getCardSelected() != -1) {
            Node source = (Node) event.getSource();
            Integer colIndex = lawn_grid.getColumnIndex(source);
            Integer rowIndex = lawn_grid.getRowIndex(source);
            if (colIndex != null && rowIndex != null) {
                boolean flag = true;
                for (Plant p : allPlants) {
                    if (p.getX() == colIndex && p.getY() == rowIndex) {
                        flag = false;
                    }
                }
                if (flag){
                    if (SidebarElement.getElement(SidebarElement.getCardSelected()).getCost() <= sunCount) {
                        placePlant(SidebarElement.getCardSelected(),(int) (source.getLayoutX() + source.getParent().getLayoutX()),(int) (source.getLayoutY() + source.getParent().getLayoutY()), colIndex, rowIndex);
                        updateSunCount((-1)*SidebarElement.getElement(SidebarElement.getCardSelected()).getCost());
                        SidebarElement.getElement(SidebarElement.getCardSelected()).setDisabledOn();
                    } else System.out.println("Not enough sun score" );
                }
                else System.out.println("Cant place more than one plant on cell");

            }
            SidebarElement.setCardSelectedToNull();
        }
    }

    public void placePlant(int val, int x, int y,int row,int col) {
        int z=0;
        switch(row){
            case 0:
                System.out.println("1");
                z=LANE1;
                break;
            case 1:
                System.out.println("2");
                z=LANE2;
                break;
            case 2:
                System.out.println("3");
                z=LANE3;
                break;
            case 3:
                System.out.println("5");
                z=LANE4;
                break;
            case 4:
                System.out.println("6");
                z=LANE5;
                break;
            default:
                System.out.println("Cant find lane "+row);
                break;
        }
        switch (val) {
            case 1:
                allPlants.add(new Sunflower(x, y, GamePlayRoot,lawn_grid,row,col));
                break;
            case 2:
                allPlants.add(new PeaShooter(x, y, GamePlayRoot,lawn_grid,row,col));
                break;
            case 3:
                allPlants.add(new Wallnut(x, y,GamePlayRoot,lawn_grid,row,col));
                break;
            case 4:
                allPlants.add(new CherryBomb(x, y, GamePlayRoot,lawn_grid,row,col));
                break;
            case 5:
                allPlants.add(new Repeater(x, y, GamePlayRoot,lawn_grid,row,col));
                break;
            case 6:
                allPlants.add(new Jalapeno(x, y, GamePlayRoot,lawn_grid,row,col));
                break;
            default:
                System.out.println("No case match" + val);
        }
    }

    @FXML
    void loadGameMenu(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("GameMenu.fxml"));
        Parent gameMenu = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(gameMenu));
        GameMenuController controller = fxmlLoader.<GameMenuController>getController();
        controller.initData(GamePlayRoot, levelNumber);
        stage.show();
    }

}
