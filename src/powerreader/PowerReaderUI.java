/*
 * PowerReaderUI.java
 *
 * Created on November 24, 2007, 7:09 PM
 */

package powerreader;

// Import J3D Stuff
import com.sun.j3d.utils.behaviors.mouse.MouseWheelZoom;
import com.sun.j3d.utils.behaviors.mouse.MouseTranslate;
import com.sun.j3d.utils.universe.*;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GraphicsConfiguration;
import javax.media.j3d.*;
import javax.vecmath.Vector3f;
import edu.stanford.nlp.io.ui.OpenPageDialog;
import java.util.ArrayList;


import java.io.*;
/**
 *
 * @author  cleung
 */
public class PowerReaderUI extends javax.swing.JFrame {
    
    // Manually added variables
    private Canvas3D theCanvas;
    
    private TextParser textParser;
    private OpenPageDialog opd;
    private ArrayList parseTree;
    private BranchGroup sceneRoot;
    private TransformGroup root_group;
    private Vector3f nextWordLocation;
    
    /** Creates new form PowerReaderUI */
    public PowerReaderUI() {
        initComponents();
        
        //textParser = new TextParser();
        opd = new OpenPageDialog(this, true);
        
        // Now initialize the 3D Canvas
        create3dCanvas();
    }
    
    private void create3dCanvas() {
        
        GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
        
        // construct the 3D image
        Canvas3D canvas3D = new Canvas3D(config);
        SimpleUniverse simpleU = new SimpleUniverse(canvas3D);
        createSceneGraph();
        simpleU.getViewingPlatform().setNominalViewingTransform();       // This will move the ViewPlatform back a bit so the
        simpleU.addBranchGraph(sceneRoot);
        
        m_panel_textArea.setLayout( new BorderLayout() );
        m_panel_textArea.setOpaque( false );
        m_panel_textArea.add("Center", canvas3D);   // <-- HERE IT IS - tada! j3d in swing
    }
    
    private void createSceneGraph() {
        // Create the root of the branch graph
        sceneRoot = new BranchGroup();
        
        sceneRoot.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
        sceneRoot.setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);
        sceneRoot.setCapability(BranchGroup.ALLOW_CHILDREN_READ);
        
        Transform3D transform3d = new Transform3D();
        transform3d.setTranslation(new Vector3f(0,0,-25));
        root_group = new TransformGroup();
        root_group.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE); // allow the mouse behavior to rotate the scene
        root_group.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
        root_group.setCapability(TransformGroup.ALLOW_CHILDREN_WRITE);
        root_group.setCapability(TransformGroup.ALLOW_CHILDREN_EXTEND);
        root_group.setCapability(TransformGroup.ALLOW_CHILDREN_READ);
        root_group.setTransform(transform3d);
        
        MouseWheelZoom mouseWheelZoom = new MouseWheelZoom(root_group);
        mouseWheelZoom.setSchedulingBounds(new BoundingSphere());
        
        MouseTranslate mouseTranslate = new MouseTranslate(root_group);
        mouseTranslate.setSchedulingBounds(new BoundingSphere());
        
        nextWordLocation= new Vector3f(-1.0f,1.0f,0);
        root_group.addChild( new Label3D("Please open a text file.") );
        
        sceneRoot.addChild(mouseWheelZoom);
        sceneRoot.addChild(mouseTranslate);
        
        BranchGroup startText = new BranchGroup();
        startText.addChild(root_group);
        startText.setCapability(BranchGroup.ALLOW_DETACH);
        
        sceneRoot.addChild( startText );  // this is the local origin  - everyone hangs off this - moving this move every one
    }
    
    class Label3D
            extends TransformGroup {
        
        public Label3D( String msg ) {
            super();
            
            // place it in the scene graph
            Transform3D offset = new Transform3D();
            offset.setTranslation(nextWordLocation);
            this.setTransform( offset );
            
            // face it in the scene graph
            Transform3D rotation = new Transform3D();
            TransformGroup rotation_group = new TransformGroup( rotation );
            this.addChild( rotation_group );
            
            // create the 3d text
            Appearance textAppear = new Appearance();
            Material textMaterial = new Material();
            textMaterial.setEmissiveColor(1.0f,1.0f,1.0f);
            textAppear.setMaterial(textMaterial);
            FontExtrusion fontExtrusion = new FontExtrusion();
            Font3D font3D = new Font3D(
                    new Font("Helvetica", Font.PLAIN, 1),
                    new FontExtrusion());
            Text3D textGeom = new Text3D(font3D,msg);
            textGeom.setAlignment(Text3D.ALIGN_CENTER);
            Shape3D textShape = new Shape3D();
            textShape.setGeometry(textGeom);
            textShape.setAppearance(textAppear);
            
            // attach it
            rotation_group.addChild( textShape );
        }
        
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        m_panel_textArea = new javax.swing.JPanel();
        m_panel_controlArea = new javax.swing.JPanel();
        m_slider_readSpeed = new javax.swing.JSlider();
        m_label_readSpeed = new javax.swing.JLabel();
        m_button_play = new javax.swing.JButton();
        m_buton_stop = new javax.swing.JButton();
        m_slider_lod = new javax.swing.JSlider();
        m_label_lod = new javax.swing.JLabel();
        m_slider_zoomLevel = new javax.swing.JSlider();
        m_label_zoomLevel = new javax.swing.JLabel();
        m_button_bgColor = new javax.swing.JButton();
        m_label_hlColor = new javax.swing.JLabel();
        m_button_hlColor = new javax.swing.JButton();
        m_checkBox_showImages = new javax.swing.JCheckBox();
        m_checkBox_wordsGrow = new javax.swing.JCheckBox();
        m_checkBox_speechEnabled = new javax.swing.JCheckBox();
        m_button_fgColor = new javax.swing.JButton();
        m_label_bgColor = new javax.swing.JLabel();
        m_label_fgColor = new javax.swing.JLabel();
        m_button_open = new javax.swing.JButton();
        m_menubar = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Power Reader Alpha");
        //Test
        org.jdesktop.layout.GroupLayout m_panel_textAreaLayout = new org.jdesktop.layout.GroupLayout(m_panel_textArea);
        m_panel_textArea.setLayout(m_panel_textAreaLayout);
        m_panel_textAreaLayout.setHorizontalGroup(
            m_panel_textAreaLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 509, Short.MAX_VALUE)
        );
        m_panel_textAreaLayout.setVerticalGroup(
            m_panel_textAreaLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 552, Short.MAX_VALUE)
        );

        m_label_readSpeed.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        m_label_readSpeed.setText("<--Slow     Read Speed     Fast-->");

        m_button_play.setText("Play");
        m_button_play.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_button_playActionPerformed(evt);
            }
        });

        m_buton_stop.setText("Stop");

        m_label_lod.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        m_label_lod.setText("<--Low   Level of Detail   High-->");

        m_label_zoomLevel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        m_label_zoomLevel.setText("<--Slow     Zoom Level     Fast-->");

        m_button_bgColor.setBackground(new java.awt.Color(255, 153, 0));
        m_button_bgColor.setText("Background Color");
        m_button_bgColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_button_bgColorActionPerformed(evt);
            }
        });

        m_label_hlColor.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        m_label_hlColor.setText("Highlight Color");

        m_button_hlColor.setBackground(new java.awt.Color(255, 0, 0));
        m_button_hlColor.setText("Highlight Color");
        m_button_hlColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_button_hlColorActionPerformed(evt);
            }
        });

        m_checkBox_showImages.setText("Show images");
        m_checkBox_showImages.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        m_checkBox_showImages.setMargin(new java.awt.Insets(0, 0, 0, 0));

        m_checkBox_wordsGrow.setText("Words grow as they are read");
        m_checkBox_wordsGrow.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        m_checkBox_wordsGrow.setMargin(new java.awt.Insets(0, 0, 0, 0));

        m_checkBox_speechEnabled.setText("Audible speech enabled");
        m_checkBox_speechEnabled.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        m_checkBox_speechEnabled.setMargin(new java.awt.Insets(0, 0, 0, 0));

        m_button_fgColor.setBackground(new java.awt.Color(0, 0, 204));
        m_button_fgColor.setText("Foreground Color");
        m_button_fgColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_button_fgColorActionPerformed(evt);
            }
        });

        m_label_bgColor.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        m_label_bgColor.setText("Background Color");

        m_label_fgColor.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        m_label_fgColor.setText("Foreground Color");

        m_button_open.setText("Open...");
        m_button_open.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_button_openActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout m_panel_controlAreaLayout = new org.jdesktop.layout.GroupLayout(m_panel_controlArea);
        m_panel_controlArea.setLayout(m_panel_controlAreaLayout);
        m_panel_controlAreaLayout.setHorizontalGroup(
            m_panel_controlAreaLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, m_panel_controlAreaLayout.createSequentialGroup()
                .add(m_panel_controlAreaLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, m_panel_controlAreaLayout.createSequentialGroup()
                        .addContainerGap()
                        .add(m_button_open, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 262, Short.MAX_VALUE))
                    .add(m_panel_controlAreaLayout.createSequentialGroup()
                        .add(10, 10, 10)
                        .add(m_panel_controlAreaLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(m_panel_controlAreaLayout.createSequentialGroup()
                                .add(37, 37, 37)
                                .add(m_label_readSpeed))
                            .add(org.jdesktop.layout.GroupLayout.TRAILING, m_panel_controlAreaLayout.createSequentialGroup()
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(m_button_play, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(m_buton_stop, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 129, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))))
                    .add(org.jdesktop.layout.GroupLayout.LEADING, m_panel_controlAreaLayout.createSequentialGroup()
                        .addContainerGap()
                        .add(m_panel_controlAreaLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                            .add(org.jdesktop.layout.GroupLayout.LEADING, m_slider_readSpeed, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .add(org.jdesktop.layout.GroupLayout.LEADING, m_slider_lod, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .add(org.jdesktop.layout.GroupLayout.LEADING, m_label_lod, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .add(m_label_zoomLevel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 260, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(org.jdesktop.layout.GroupLayout.LEADING, m_button_bgColor, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 262, Short.MAX_VALUE)
                            .add(org.jdesktop.layout.GroupLayout.LEADING, m_label_hlColor, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 262, Short.MAX_VALUE)
                            .add(org.jdesktop.layout.GroupLayout.LEADING, m_button_hlColor, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 262, Short.MAX_VALUE)
                            .add(org.jdesktop.layout.GroupLayout.LEADING, m_checkBox_showImages)
                            .add(org.jdesktop.layout.GroupLayout.LEADING, m_checkBox_wordsGrow)
                            .add(org.jdesktop.layout.GroupLayout.LEADING, m_checkBox_speechEnabled)
                            .add(m_button_fgColor, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .add(org.jdesktop.layout.GroupLayout.LEADING, m_label_bgColor, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 262, Short.MAX_VALUE)
                            .add(org.jdesktop.layout.GroupLayout.LEADING, m_label_fgColor)
                            .add(org.jdesktop.layout.GroupLayout.LEADING, m_slider_zoomLevel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        m_panel_controlAreaLayout.setVerticalGroup(
            m_panel_controlAreaLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(m_panel_controlAreaLayout.createSequentialGroup()
                .add(m_button_open)
                .add(16, 16, 16)
                .add(m_panel_controlAreaLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(m_buton_stop)
                    .add(m_button_play))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(m_label_readSpeed, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 16, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(m_slider_readSpeed, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 29, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(m_label_zoomLevel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 24, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(m_slider_zoomLevel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 29, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(m_label_lod, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 24, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(m_slider_lod, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 29, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(m_label_fgColor)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(m_button_fgColor)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(m_label_bgColor)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(m_button_bgColor)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(m_label_hlColor)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(m_button_hlColor)
                .add(23, 23, 23)
                .add(m_checkBox_showImages)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(m_checkBox_wordsGrow)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(m_checkBox_speechEnabled)
                .addContainerGap(65, Short.MAX_VALUE))
        );

        jMenu1.setMnemonic('F');
        jMenu1.setText("File");
        jMenu1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu1ActionPerformed(evt);
            }
        });

        m_menubar.add(jMenu1);

        setJMenuBar(m_menubar);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .add(m_panel_textArea, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(1, 1, 1)
                .add(m_panel_controlArea, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(m_panel_textArea, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .add(layout.createSequentialGroup()
                .add(m_panel_controlArea, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(11, 11, 11))
        );
        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void m_button_openActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m_button_openActionPerformed
        opd.setLocation(getLocationOnScreen().x + (getWidth() - opd.getWidth()) / 2, getLocationOnScreen().y + (getHeight() - opd.getHeight()) / 2);
        opd.setVisible(true);
        
        if (opd.getStatus() == OpenPageDialog.APPROVE_OPTION) {
            //parseTree = textParser.loadFile(opd.getPage());
            
            //prepare scenegraph for new text
            //sceneRoot.removeChild(1);
            nextWordLocation.x=-1f;
            nextWordLocation.y=1f;
            root_group.addChild(renderFile(opd.getPage()));
        }
    }//GEN-LAST:event_m_button_openActionPerformed
    
    private void m_button_hlColorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m_button_hlColorActionPerformed
// TODO add your handling code here:
    }//GEN-LAST:event_m_button_hlColorActionPerformed
    
    private void m_button_bgColorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m_button_bgColorActionPerformed
// TODO add your handling code here:
    }//GEN-LAST:event_m_button_bgColorActionPerformed
    
    private void m_button_fgColorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m_button_fgColorActionPerformed
// TODO add your handling code here:
    }//GEN-LAST:event_m_button_fgColorActionPerformed
    
    private void m_button_playActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m_button_playActionPerformed
// TODO add your handling code here:
    }//GEN-LAST:event_m_button_playActionPerformed
    
    private void jMenu1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu1ActionPerformed
// TODO add your handling code here:
    }//GEN-LAST:event_jMenu1ActionPerformed
    
    private BranchGroup renderFile(String infile){
        BranchGroup node = new BranchGroup();
        
        node.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
        node.setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);
        node.setCapability(BranchGroup.ALLOW_CHILDREN_READ);
        node.setCapability(BranchGroup.ALLOW_DETACH);
        
        try {
            BufferedReader in = new BufferedReader(new FileReader(infile));
            String paragraph;
            while ((paragraph = in.readLine()) != null) {
                node.addChild(renderParagraph(paragraph+"."));
                
                // increment scenegraph to next line
                nextWordLocation.x = -1.0f;
                nextWordLocation.y -= 0.1f;
            }
            in.close();
            
        } catch (Exception e) {
            System.out.println(e);
        }
        return node;
    }
    
    private BranchGroup renderParagraph(String para){
        BranchGroup node = new BranchGroup();
        
        node.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
        node.setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);
        node.setCapability(BranchGroup.ALLOW_CHILDREN_READ);
        node.setCapability(BranchGroup.ALLOW_DETACH);
        
        int i;
        String[] sentences= para.split("\\.+");
        for (i=0; i<sentences.length; i++) {
            node.addChild(renderSentence(sentences[i]+"."));
        }
        return node;
    }
    
    private BranchGroup renderSentence(String sentence){
        BranchGroup node = new BranchGroup();
        
        node.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
        node.setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);
        node.setCapability(BranchGroup.ALLOW_CHILDREN_READ);
        node.setCapability(BranchGroup.ALLOW_DETACH);
        
        int i;
        String[] words= sentence.split("\\s+");
        for (i=0; i<words.length; i++) {
            node.addChild(renderWord(words[i]));
        }
        return node;
    }
    
    private BranchGroup renderWord(String word) {
        BranchGroup node = new BranchGroup();
        
        node.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
        node.setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);
        node.setCapability(BranchGroup.ALLOW_CHILDREN_READ);
        node.setCapability(BranchGroup.ALLOW_DETACH);
        
        if (nextWordLocation.x > 0.9f) {
            nextWordLocation.x = -1.0f;
            nextWordLocation.y -= 0.1f;
        }
        
        node.addChild(new Label3D(word));
        
        nextWordLocation.x += 0.05f * word.length();
        return node;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu jMenu1;
    private javax.swing.JButton m_buton_stop;
    private javax.swing.JButton m_button_bgColor;
    private javax.swing.JButton m_button_fgColor;
    private javax.swing.JButton m_button_hlColor;
    private javax.swing.JButton m_button_open;
    private javax.swing.JButton m_button_play;
    private javax.swing.JCheckBox m_checkBox_showImages;
    private javax.swing.JCheckBox m_checkBox_speechEnabled;
    private javax.swing.JCheckBox m_checkBox_wordsGrow;
    private javax.swing.JLabel m_label_bgColor;
    private javax.swing.JLabel m_label_fgColor;
    private javax.swing.JLabel m_label_hlColor;
    private javax.swing.JLabel m_label_lod;
    private javax.swing.JLabel m_label_readSpeed;
    private javax.swing.JLabel m_label_zoomLevel;
    private javax.swing.JMenuBar m_menubar;
    private javax.swing.JPanel m_panel_controlArea;
    private javax.swing.JPanel m_panel_textArea;
    private javax.swing.JSlider m_slider_lod;
    private javax.swing.JSlider m_slider_readSpeed;
    private javax.swing.JSlider m_slider_zoomLevel;
    // End of variables declaration//GEN-END:variables
    
    
}
