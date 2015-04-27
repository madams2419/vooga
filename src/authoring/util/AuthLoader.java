package authoring.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javafx.scene.control.TabPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import XML.GameAuthXMLParser;
import authoring.dataEditors.Sprite;
import authoring.panes.centerPane.CenterCanvas;
import authoring.panes.centerPane.CenterPane;
import authoring.panes.centerPane.Region;

public class AuthLoader {

    public void load (CenterPane centerPane) {
        FileChooser myFileChooser = new FileChooser();
        myFileChooser.setTitle("Load Authoring Environment");
        myFileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml"));
        File f = myFileChooser.showOpenDialog(null);
        GameAuthXMLParser.parse(f);
        Map<Integer,Map> images = GameAuthXMLParser.levelSpriteImages;
        ArrayList<Sprite> spriteList = new ArrayList<Sprite>();
        for(Iterator<Entry<Integer,Map>> it = images.entrySet().iterator();it.hasNext();){
            Entry<Integer, Map> level = it.next();
            for(Iterator<Entry<Integer,ArrayList>> ite = level.getValue().entrySet().iterator();ite.hasNext();){
                Entry<Integer, ArrayList> sprite = ite.next();
                String source = ((Map<String, String>) sprite.getValue().get(0)).get("source");
//                System.out.println(sprite.getKey());
                spriteList.add(new Sprite(sprite.getKey(), "file:"+source, centerPane));
            }
        }
        CenterCanvas active = centerPane.getActiveTab();
        active.setContent(new Region(Double.parseDouble(GameAuthXMLParser.myGameCoefs.get("scene_width")),Double.parseDouble(GameAuthXMLParser.myGameCoefs.get("scene_height")),Color.WHITE));
        int count=0;
        for(Iterator<Sprite> it=spriteList.iterator();it.hasNext();){
            Sprite sprite = it.next();
            ArrayList<Map> x=(ArrayList<Map>) GameAuthXMLParser.levelSpriteInfos.get(0).get(count);
            String positions = ((Map<String,String>) x.get(0).get("physics")).get("position");
            String[] position = positions.split(" ");
            sprite.setXPosition(Double.parseDouble(position[0]));
            sprite.setYPosition(Double.parseDouble(position[1]));
            active.myListOfSprites.add(sprite);
//            sprite.setY(value);
            count++;
        }
//        System.out.println(active.myParent.myPanes);
//        spriteList.setX(GameAuthXMLParser.levelSpriteInfos);
//        System.out.println(spriteList);
//        System.out.println(active.myGroup.getChildren());
//        public Sprite (int ID, String imageURI, CenterPane parent)
    }

}
