package Managers;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import Areas.Area;
import Areas.Lobby;
import Areas.Stairway;
import Factories.AreaFactory;
import Scenes.MainMenuScene;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import simple.JSONArray;
import simple.JSONObject;
import simple.parser.JSONParser;
import simple.parser.ParseException;

/**
 * The GridBuilder is generating the grid based on the inserted layout.
 */

public class GridBuilder {

    //Variables
    public static GridPane grid;
    public static boolean correctLayout = true;
    private static int xOffset = 3;
    private static int maxY = 0;
    private static int maxX = 0;
    private int roomNumber = 1; // Give room numbers
    public static int colSize = 48;
    public static int rowSize = 48;
    private int[][] isOccupied;
    private int objectNumber = 1;
    ShortestPath.Dijkstra _ds = new ShortestPath.Dijkstra();

    private void createGrid() {
        grid = new GridPane();
        grid.setGridLinesVisible(false);
        grid.setMaxSize(100, 100);
        int cols = 50;
        int rows = 50;

        for (int i = 0; i < cols; i++) {
            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setMinWidth(colSize);
            grid.getColumnConstraints().add(colConst);
        }
        for (int i = 0; i < rows; i++) {
            RowConstraints rowConst = new RowConstraints();
            rowConst.setMinHeight(rowSize);
            grid.getRowConstraints().add(rowConst);
        }

        // Create elevatorcabin image
        Image elevatorRopeImage = new Image("file:src/Images/elevator_rope.png");
        ImageView elevatorRopeImageView = new ImageView(elevatorRopeImage);
        elevatorRopeImageView.setFitWidth(24);
        elevatorRopeImageView.setFitHeight(48);
        elevatorRopeImageView.setTranslateX(13);
        grid.add(elevatorRopeImageView, xOffset - 1, 1, 1, 1);

        // Create elevator background image
        Image elevatorImage = new Image("file:src/Images/elevator_top.png");
        ImageView elevatorImageImageView = new ImageView(elevatorImage);
        elevatorImageImageView.setFitWidth(96);
        elevatorImageImageView.setFitHeight(48);

        // Create Hbox to contain background images of elevator
        HBox elevatorBackground = new HBox();
        elevatorBackground.setMaxSize(colSize * 2, rowSize * getMaxY());
        BackgroundImage elevatorBG = new BackgroundImage(new Image("file:src/Images/elevator_bg.png", 96, 48, false, false),
                BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        elevatorBackground.setBackground(new Background(elevatorBG));
        elevatorBackground.getChildren().add(elevatorImageImageView);

        // Add the elevator background to the grid
        grid.add(elevatorBackground, xOffset - 1, 1, 1, getMaxY());
        grid.setBackground(new Background(new BackgroundFill(Color.web("#102860"), CornerRadii.EMPTY, Insets.EMPTY)));

    }

    private void getDimensions() {
        try {
            FileReader reader = new FileReader(MainMenuScene.selectedLayout);
            JSONParser jsonParser = new JSONParser();
            JSONArray jsonArr = (JSONArray) jsonParser.parse(reader);

            int x ;
            int y;
            int dimensionW;
            int dimensionH;

            for (Object o : jsonArr) {

                JSONObject obj = (JSONObject) o;
                String dimension = (String) obj.get("Dimension");

                //Spliting dimension and putting it in ints
                String[] parts = dimension.split(",");
                dimensionW = Integer.parseInt(parts[0]);
                String[] partsAfterSpace = parts[1].split("\\s+");
                dimensionH = Integer.parseInt(partsAfterSpace[1]);

                //Splitting position in x & y
                String position = (String) obj.get("Position");
                parts = position.split(",");
                x = Integer.parseInt(parts[0]);
                partsAfterSpace = parts[1].split("\\s+");
                y = Integer.parseInt(partsAfterSpace[1]);

                if (y + dimensionH > maxY) {
                    maxY = y + (dimensionH - 1);
                }

                if (x + dimensionW > maxX) {
                    maxX = x + dimensionW;
                }
           }

            isOccupied = new int[maxX+1][maxY+1];

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ParseException ex) {
            ex.printStackTrace();
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
    }

    private void createRooms() {

        try {
            FileReader reader = new FileReader(MainMenuScene.selectedLayout);
            JSONParser jsonParser = new JSONParser();
            JSONArray jsonArr = (JSONArray) jsonParser.parse(reader);

            // For-each loop to create rooms via the AreaFactory
            int stars;
            long capacity;
            int x;
            int y;
            int dimensionW;
            int dimensionH;

            for (Object o : jsonArr) {
                stars = 0;
                capacity = 0;

                JSONObject obj = (JSONObject) o;
                String areaType = (String) obj.get("AreaType");
                String dimension = (String) obj.get("Dimension");

                //Splitting dimension and putting it in ints
                String[] parts = dimension.split(",");
                dimensionW = Integer.parseInt(parts[0]);
                String[] partsAfterSpace = parts[1].split("\\s+");
                dimensionH = Integer.parseInt(partsAfterSpace[1]);

                //Splitting position in x & y
                String position = (String) obj.get("Position");
                parts = position.split(",");
                x = Integer.parseInt(parts[0]);
                partsAfterSpace = parts[1].split("\\s+");
                y = Integer.parseInt(partsAfterSpace[1]);

                //Check if classification is available
                if (obj.containsKey("Classification")) {
                    String classification = (String) obj.get("Classification");
                    stars = Integer.parseInt(classification.charAt(0) + "");
                }

                //Check if capacity is available
                if (obj.containsKey("Capacity")) {
                    if (obj.get("Capacity") instanceof String)
                        capacity = Long.parseLong(String.valueOf(obj.get("Capacity")));
                    else
                        capacity = (long) obj.get("Capacity");
                }

                //Create room based on parameters in object
                if (isOccupied[x][y] == 1) {
                    System.out.println("x: " + x + " y: " + y);
                    System.out.println("Hier staat al iets, deze sla ik over");
                    System.out.println("object: " + objectNumber);
                    objectNumber += 1;
                    correctLayout = false;
                } else if (isOccupied[x][y] != 1) {

                    if (dimensionH > 1) {
                        y += 1;
                    }

                    // Increment the roomNumber each loop
                    int id = roomNumber;
                    roomNumber++;

                    Area tempRoom = AreaFactory.createArea(id, areaType, dimensionW, dimensionH, stars, capacity, x + xOffset, (getMaxY() - y + 1));
                    Area.getAreaList().add(tempRoom);

                    for (int xOcupied = x; xOcupied < x + dimensionW; xOcupied++) {
                        for (int yOcupied = y; yOcupied > y - dimensionH; yOcupied--) {
                            if (areaType.equals("Cinema")) {
                                isOccupied[xOcupied][yOcupied] = 6;
                            } else {
                                isOccupied[xOcupied][yOcupied] = 1;
                            }
                        }
                    }
                    objectNumber += 1;
                }
            }

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ParseException ex) {
            ex.printStackTrace();
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }

    }

    private void createStairway() {
        for (int i = 0; i < (getMaxY() + 1); i++) {
            Area stairway = AreaFactory.createArea(roomNumber, "Stairway", 1, 1, 0, 0, getMaxX(), 1 + i);
            Area.getAreaList().add(stairway);
            roomNumber++;
        }
    }

    private void createHotelBackground() {
        // Create Hbox to contain background images of floors
        HBox floorBackground = new HBox();
        floorBackground.setMaxSize((getMaxX() - 3) * 48, 48 * getMaxY());

        // Create top-floor background image
        Image topFloorImage = new Image("file:src/Images/top_floor.png");
        ImageView topFloorImageView = new ImageView(topFloorImage);
        topFloorImageView.setFitWidth((getMaxX() - 3) * 48);
        topFloorImageView.setFitHeight(48);

        BackgroundImage myBI = new BackgroundImage(new Image("file:src/Images/floor_bg.png", (getMaxX() - 3) * 48, 48, false, false),
                BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        floorBackground.setBackground(new Background(myBI));
        floorBackground.getChildren().add(topFloorImageView);

        // Add the floor background to the grid
        grid.add(floorBackground, xOffset + 1, 1, getMaxX() - 3, getMaxY());
    }


    private void addElevator() {
        AreaFactory.createArea(1, "Elevator", 1, 1, 0, 0, 2, 2);
    }

    private void addLobby() {
        Area lobby = AreaFactory.createArea(roomNumber, "Lobby", 9, 1, 0, 0, 2, getMaxY() + 1);
        roomNumber++;
        Area.getAreaList().add(lobby);
    }

    private void createEdges() {
        for (Area object : Area.getAreaList()) {
            for (Area object2 : Area.getAreaList()) {
                // Check left for neighbours
                if ((object.getX() - 1 == object2.getX() || object.getX() - 1 == object2.getXEnd()) && object.getRealY() == object2.getRealY()) {
                    //System.out.println("Ik " + object.id + " ben buurtjes met " + object2.id + " met gewicht: " + object2.getDistance());
                    object.neighbours.put(object2, object2.getDistance());
                }
                // Check right for neighbours
                if (object.getXEnd() + 1 == object2.getX() && object.getRealY() == object2.getRealY()) {
                    //System.out.println("Ik " + object.id + " ben buurtjes met " + object2.id + " met gewicht: " + object.getDistance());
                    object.neighbours.put(object2, object2.getDistance());
                }
                if (object instanceof Stairway) {
                    if ((object.getY() == object2.getY() - 1) && object.getX() == object2.getX()) {
                        //System.out.println("Ik " + object.id + " ben buurtjes met " + object2.id + " met gewicht: " + SettingBuilder.stairTime);
                        object.neighbours.put(object2, SettingBuilder.stairTime);
                    }
                    if ((object.getY() == object2.getY() + 1) && object.getX() == object2.getX()) {
                        //System.out.println("Ik " + object.id + " ben buurtjes met " + object2.id + " met gewicht: " + SettingBuilder.stairTime);
                        object.neighbours.put(object2, SettingBuilder.stairTime);
                    }
                }
                if (object instanceof Lobby) {
                    if ((object.getXEnd() == object2.getX() && object.getY() == object2.getY())) {
                        object.neighbours.put(object2, maxX);
                        object2.neighbours.put(object, maxX);
                    }
                }
            }
        }
    }

    public static int getMaxY() {
        return maxY;
    }

    public static int getMaxX() {
        return maxX + xOffset;
    }

    public void buildGrid() {
        getDimensions();
        createGrid();
        createHotelBackground();
        createRooms();
        addElevator();
        addLobby();
        createStairway();
        createEdges();
    }
}
