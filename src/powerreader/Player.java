/*
 * Player.java
 *
 * Created on December 4, 2007, 2:18 AM
 *
 * Play methods for the HierarchyObjects
 *
 */

package powerreader;

import java.util.ArrayList;
import java.util.Iterator;
import javax.media.j3d.Transform3D;
import speech.Speech;
import util.HierarchyObject;
import util.RawTextParser;
import util.TextObject3d;

/**
 *
 * @author Christopher Leung
 */
public class Player extends Thread {
    public static int WPM_FACTOR = 120000;
    
    private HierarchyObject m_root;
    private int m_focusLevel;
    
    private boolean m_growEnabled;
    private float m_highlightColorR;
    private float m_highlightColorG;
    private float m_highlightColorB;
    private float m_baseColorR;
    private float m_baseColorB;
    private float m_baseColorG;
    private Object m_ready;
    
    // The objects to speak, and the item we are focused on
    private ArrayList m_objectsToSpeak;
    private int m_focusIndex;
    
    static private Player m_instance = null;
    
    static {
        m_instance = new Player();
    }
    /** Creates a new instance of Player */
    public Player() {
        
        // Default to document level focus
        m_focusLevel = RawTextParser.LEVEL_DOCUMENT_ID;
        
        // Default colors
        m_highlightColorR = 1.0f;
        m_highlightColorG = 1.0f;
        m_highlightColorB = 1.0f;
        
        m_baseColorR = 1.0f;
        m_baseColorG = 1.0f;
        m_baseColorB = 1.0f;
        
        m_focusIndex = 0;
        m_objectsToSpeak = new ArrayList();
        
        m_ready = new Object();
                
        // Default to an empty document
        m_root = new HierarchyObject(RawTextParser.LEVEL_DOCUMENT_ID,RawTextParser.LEVEL_DOCUMENT_STR);
        
        Object m_ready = Speech.getSync();
    }
    
    static public void setHierarchyRoot(HierarchyObject root) {
        m_instance.m_focusIndex = 0;
        m_instance.m_root = root;
        m_instance.m_objectsToSpeak = m_instance.m_root.getAllChildrenOfLevel(m_instance.m_focusLevel);
    }
    
    static public void setSleepDelay(int factor) {
        Speech.setSpeed(WPM_FACTOR/factor);
    }
    
    static public HierarchyObject getFocusOn() {
        return (HierarchyObject)(m_instance.m_objectsToSpeak.get(m_instance.m_focusIndex));
    }
    static public void setFocusLevel(int focusLevel) {
        // Get all objects on this level
        m_instance.m_focusLevel = focusLevel;
        m_instance.m_focusIndex = 0;
        m_instance.m_objectsToSpeak = m_instance.m_root.getAllChildrenOfLevel(m_instance.m_focusLevel);
    }
    
    static public boolean setFocusOn(HierarchyObject hObj) {
        int findOnLevel = hObj.getLevel();
        ArrayList objectsToSearch = m_instance.m_root.getAllChildrenOfLevel(findOnLevel);
        Iterator it = objectsToSearch.iterator();
        int searchIndex = 0;
        
        while(it.hasNext()) {
            Object objToTest = it.next();
            if(objToTest.equals(hObj)) {
                
                m_instance.m_focusLevel = findOnLevel;
                m_instance.m_focusIndex = searchIndex;
                
                // Highlight the focused
                hObj.color(true);
                
                // Set the objects to speach os the objects that were searched
                m_instance.m_objectsToSpeak = objectsToSearch;
                
                // Set the index
                m_instance.m_focusIndex = searchIndex;
                
                return true;
            }
            searchIndex++;
        }
        return false;
    }
    
    static public void setGrowEnable(boolean active) {
        m_instance.m_growEnabled = active;
    }
    
    static public void setHighlightColor(float r, float g, float b) {
        m_instance.m_highlightColorR = r;
        m_instance.m_highlightColorG = g;
        m_instance.m_highlightColorB = b;
    }
    
    static public void setBaseColor(float r, float g, float b) {
        m_instance.m_baseColorR = r;
        m_instance.m_baseColorG = g;
        m_instance.m_baseColorB = b;
    }
    
    public void run() {
        
        HierarchyObject currentObj = null;
        Transform3D moveScene = new Transform3D();
        TextObject3d theText = null;
        
        for(;;) {
//            synchronized(m_ready) {
            for(int i = m_focusIndex; i < m_objectsToSpeak.size(); i++) {
                // Get current obj
                currentObj = (HierarchyObject)m_objectsToSpeak.get(i);
                
                // Highlight/grow the current object
                currentObj.color(true);
                
                // Disable render on everything but the current object
                disableRenderExcept(currentObj);
                
                if(ConfigurationManager.followFocus()) {
                    // Center the scene on the focused item
                    theText = (TextObject3d) currentObj.getTransformGroup();
                    //              System.out.println("To move, X: " + theText.getLocation().x + " Y: "+ theText.getLocation().y );
                    ConfigurationManager.current_x = -theText.getLocation().x;
                    ConfigurationManager.current_y = -theText.getLocation().y;
                    ConfigurationManager.refreshTranslate();
                    
                }
//                m_instance.m_root.getTransformGroup().setTransform(moveScene);
                
                
                
                synchronized(Speech.getSync()) {
                    try {
                        Speech.speak(currentObj.getValue());
                        Speech.getSync().wait();
                        // Start speaking the object
                        
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
                
                // Shrink the current object
                currentObj.color(false);
                
                m_focusIndex++;
            }
            m_focusIndex = 0;
            //}
        }
    }
    
    static public void playOne() {
        HierarchyObject currentObj = null;
        
        // Get current obj
        currentObj = (HierarchyObject)m_instance.m_objectsToSpeak.get(m_instance.m_focusIndex);
        
        // Start speaking the object
        Speech.speak(currentObj.getValue());
        
        // Highlight/grow the current object
        currentObj.color(true);
        
    }
    // TODO : Stop
    static public void reset() {
        Speech.cancel();
        if(m_instance.isAlive()) {
            m_instance.stop();
            m_instance = new Player();
        }
    }
    
    static public Player getInstance() {
        return m_instance;
    }
    
    static public void play() {
        if(m_instance.isAlive()) {
            m_instance.resume();
        } else {
            m_instance.start();
        }
    }
    
    static public void pause() {
        m_instance.suspend();
        Speech.cancel();
    }
    
    static public void disableRenderExcept(HierarchyObject object) {
        
        // Start with the document level
        HierarchyObject documentParent = object.getParent(RawTextParser.LEVEL_DOCUMENT_ID);
        HierarchyObject paragraphParent = object.getParent(RawTextParser.LEVEL_PARAGRAPH_ID);
        HierarchyObject sentenceParent = object.getParent(RawTextParser.LEVEL_SENTENCE_ID);
        
        // Enable depending on level of detail
        if (ConfigurationManager.getDetailLevel() == RawTextParser.LEVEL_DOCUMENT_ID) {
            enableRenderOfChildren(documentParent);
        } else if(ConfigurationManager.getDetailLevel() == RawTextParser.LEVEL_PARAGRAPH_ID) {
            enableRenderOfChildren(paragraphParent);
        } else if(ConfigurationManager.getDetailLevel() == RawTextParser.LEVEL_SENTENCE_ID) {
            enableRenderOfChildren(sentenceParent);
        }
        
        // Disable Show depending on level of detail
        if (ConfigurationManager.getDetailLevel() > RawTextParser.LEVEL_DOCUMENT_ID) {
            disableRenderOfChildren(documentParent,paragraphParent);
        }
        if(ConfigurationManager.getDetailLevel() > RawTextParser.LEVEL_PARAGRAPH_ID ) {
            disableRenderOfChildren(paragraphParent,sentenceParent);
        }
        if(ConfigurationManager.getDetailLevel() > RawTextParser.LEVEL_SENTENCE_ID) {
            disableRenderOfChildren(sentenceParent,object);
        }
    }
        
    static public void enableRenderOfChildren(HierarchyObject parent) {
        ArrayList children;
        Iterator it;
        HierarchyObject obj;
        for(int i = parent.getLevel()+1; i<RawTextParser.LEVEL_WORD_ID+1; i++) {
            children = parent.getAllChildrenOfLevel(i);
            it = children.iterator();
            while(it.hasNext()) {
                obj = (HierarchyObject) it.next();
                obj.enabledRender();
            }
        }
    }
    static public void disableRenderOfChildren(HierarchyObject parent, HierarchyObject except) {
        ArrayList children = parent.getAllChildrenOfLevel(except.getLevel());
        Iterator it = children.iterator();
        HierarchyObject obj;
        int i = 0;
        while(it.hasNext()) {
            obj = (HierarchyObject) it.next();
            if(!obj.equals(except)) {
                obj.disableRender();
            } else {
                obj.enabledRender();
            }
            i++;
        }
    }
}
