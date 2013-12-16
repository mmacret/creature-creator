/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * IHM.java
 *
 * Created on 19 mars 2009, 15:12:17
 */
package IHM;

import java.util.logging.Level;
import java.util.logging.Logger;
import simulationModele.*;
import generationModele.*;
import java.awt.Color;
import java.awt.Graphics;
import java.util.*;
import java.util.ArrayList;
import java.util.Map.Entry;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

/**
 *
 * @author mmacret
 */
public class IHM extends javax.swing.JApplet {

    private final Environment environment = new Environment(20, 515, 510);
    private Institution mainInstitution;
    private Norm normInstitution;
    private ArrayList<Norm> norms = new ArrayList<Norm>();
    private generationEngine behaviorGenerator = new generationEngine();
    private Agent tempAgent = new Agent();

    /** Initializes the applet IHM */
    @Override
    public void init() {
        initComponents();

        this.setSize(1145, 725);

        this.environment.setEnvironmentListener(new EnvironmentListener() {

            public void gridChanged(HashMap<Coordinates, Agent> newGrid) {

                try {

                    java.awt.EventQueue.invokeLater(new Runnable() {

                        public void run() {
                            jPanel_SimulationPanel.repaint();
                        }
                    });
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            public void reproduction(Agent maman, Agent papa, Agent fils) {
                jTextAreaLog.append(maman + " and " + papa + " belonging to " + maman.getBehavior().getNormMother() + " give birth to " + fils + "\n");
                jTextAreaLog.setCaretPosition(jTextAreaLog.getDocument().getLength());
                updateStatisticsTable();
            }

            public void combat(Agent winner, Agent loser) {
                jTextAreaLog.append(winner + " belonging to " + winner.getBehavior().getNormMother() + " kills " + loser + " belonging to " + loser.getBehavior().getNormMother() + "\n");
                jTextAreaLog.setCaretPosition(jTextAreaLog.getDocument().getLength());
                updateStatisticsTable();

            }
        });
        try {
            principal();
        } catch (Exception ex) {
            Logger.getLogger(IHM.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void principal() throws Exception {

        //Création de l'institution
        Institution institution = new Institution("Institution");
        // Paramètres à ensemble continu
        institution.addparameter("institutional", new InstitutionalParameter("agressiveness", 0, 90));
        institution.addparameter("institutional", new InstitutionalParameter("size", 10, 20));
        institution.addparameter("institutional", new InstitutionalParameter("moving", 20, 25));
        institution.addparameter("institutional", new InstitutionalParameter("nbChildren", 0, 3));
        //Parametre à ensemble discret
        /*
        List<Double> ensNbChildren = new ArrayList<Double>();
        ensNbChildren.add(1.0);
        ensNbChildren.add(2.0);
        ensNbChildren.add(3.0);
        definitionSet defNbChildren = new definitionSet(ensNbChildren);
        institution.addparameter("institutional", new InstitutionalParameter("nbChildren", defNbChildren));
         */
        mainInstitution = institution;
        normInstitution = new Norm("Institution", mainInstitution).completeNorm();

        //Création de la norme
        Norm norm1 = new Norm("species1", mainInstitution);
        norm1.addParameter("institutional", new Parameter(30, 60, 50, institution.getInstitutionalParameters().get("agressiveness")));
        norm1.addParameter("institutional", new Parameter(11, 19, 15, institution.getInstitutionalParameters().get("size")));
        norm1 = norm1.completeNorm();
        norms.add(norm1);
        System.out.println(norms.get(0).getInstitutionalParameters().get("moving").getDefinitionSet().getBorneSup());

        Norm norm2 = new Norm("species2", mainInstitution);
        norm2.addParameter("institutional", new Parameter(60, 90, 70, institution.getInstitutionalParameters().get("agressiveness")));
        norm2.addParameter("institutional", new Parameter(11, 12, 11, institution.getInstitutionalParameters().get("size")));
        norm2 = norm2.completeNorm();
        norms.add(norm2);

        Norm norm3 = new Norm("species3", mainInstitution);
        norm3.addParameter("institutional", new Parameter(60, 90, 70, institution.getInstitutionalParameters().get("agressiveness")));
        norm3.addParameter("institutional", new Parameter(11, 13, 12, institution.getInstitutionalParameters().get("size")));
        norm3 = norm3.completeNorm();
        norms.add(norm3);

        norms.add(normInstitution);


        for (Norm norm : this.norms) {


            if (!norm.getName().equals("Institution")) {
                addAgents(5, norm);
                jComboBox_SpeciesSelection.addItem(norm);
                jComboBox_SpeciesChoice.addItem(norm);
            }

        }

        
        updateTableInstitutionDesc();

        jSliderDeterminism.setValue((int) mainInstitution.getSigma() * 100);


        updateTree();
        updateStatisticsTable();
        updateTable();







    }

    /** This method is called from within the init() method to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane_IHM = new javax.swing.JTabbedPane();
        jPanel_SimulationBoard = new javax.swing.JPanel();
        jPanel_SimulationControls = new javax.swing.JPanel();
        jButton_Start = new javax.swing.JButton();
        jButton_Pause = new javax.swing.JButton();
        jSlider_SimulationSpeed = new javax.swing.JSlider();
        jButton_Stop = new javax.swing.JButton();
        jLabel_SimulationSpeed = new javax.swing.JLabel();
        jPanel_SimulationPanel = new javax.swing.JPanel(){
            @Override
            public void paintComponent(Graphics g){

                g.setColor(Color.white);
                g.fillRect(0,0,this.getWidth(), this.getHeight());
                environment.lockGrid();
                for(Entry<Coordinates,Agent> entry :environment.getGrid().entrySet()){
                    if(environment.getGrid().containsKey(entry.getKey())){
                        g.setColor(entry.getValue().getBehavior().getNormMother().getColor());
                        //System.out.println("Abscisse: "+keys.getAbscisse());
                        int size = (int)entry.getValue().getBehavior().getParameters().get("size").getValue();
                        g.fillOval((int)entry.getKey().getAbscisse(), (int)entry.getKey().getOrdonnee(), size, size);
                        if(entry.getValue().getBehavior().getNormMother().getName().equals("Institution")){
                            g.drawString("I", (int)entry.getKey().getAbscisse(), (int)entry.getKey().getOrdonnee());
                        }
                        if(entry.getValue().getBehavior().isViolating()){
                            g.drawString("V", (int)entry.getKey().getAbscisse(), (int)entry.getKey().getOrdonnee());
                        }
                    }

                }
                environment.unlockGrid();
            }};
            jPanel_SpeciesList = new javax.swing.JPanel();
            jScrollPane_Species = new javax.swing.JScrollPane();
            jTable1 = new javax.swing.JTable();
            jLabel3 = new javax.swing.JLabel();
            jTextField_TotalPop = new javax.swing.JTextField();
            jRadioButton_ReplacePop = new javax.swing.JRadioButton();
            jRadioButtonAddToPop = new javax.swing.JRadioButton();
            jButton_GeneratePop = new javax.swing.JButton();
            jPanel_GenerationMethod = new javax.swing.JPanel();
            jRadioButton_InstitutionMethod = new javax.swing.JRadioButton();
            jRadioButton_SpeciesMethod = new javax.swing.JRadioButton();
            jLabel7 = new javax.swing.JLabel();
            jComboBox_SpeciesSelection = new javax.swing.JComboBox();
            jCheckBox_ViolatingAgent = new javax.swing.JCheckBox();
            jButton_Generate = new javax.swing.JButton();
            jLabel8 = new javax.swing.JLabel();
            jPanel_AgentCharacteristics = new javax.swing.JPanel();
            label_ind_Size = new java.awt.Label();
            label_ind_Moving = new java.awt.Label();
            label_ind_children = new java.awt.Label();
            label_ind_Agressiveness = new java.awt.Label();
            jTextField_AgressivenessAddAgent = new javax.swing.JTextField();
            jLabel10 = new javax.swing.JLabel();
            jSpinner_NbChildrenAddAgent = new javax.swing.JSpinner();
            jSpinner_MovingFrequencyAddAgent = new javax.swing.JSpinner();
            jSpinner_SizeAddAgent = new javax.swing.JSpinner();
            jLabel11 = new javax.swing.JLabel();
            jButton_Add = new javax.swing.JButton();
            jButton1 = new javax.swing.JButton();
            jPanel1 = new javax.swing.JPanel();
            jScrollPane1 = new javax.swing.JScrollPane();
            jTable_Statistics = new javax.swing.JTable();
            jScrollPane2 = new javax.swing.JScrollPane();
            jTextAreaLog = new javax.swing.JTextArea();
            jPanel_SpeciesDescription = new javax.swing.JPanel();
            jPanel_Species = new javax.swing.JPanel();
            jComboBox_SpeciesChoice = new javax.swing.JComboBox();
            jButton_AddSpecies = new javax.swing.JButton();
            jButtonRemoveSpecies = new javax.swing.JButton();
            jTextField_SpeciesName = new javax.swing.JTextField();
            jLabel_Name1 = new javax.swing.JLabel();
            jPanel_PhysicalParameters = new javax.swing.JPanel();
            label_Size = new java.awt.Label();
            jLabel_Min12 = new javax.swing.JLabel();
            jSpinner_SizeMin = new javax.swing.JSpinner();
            jLabel_Min13 = new javax.swing.JLabel();
            jLabel_Min14 = new javax.swing.JLabel();
            jSpinner_SizeMax = new javax.swing.JSpinner();
            jLabel_Min15 = new javax.swing.JLabel();
            jPanel_BehavioralParameters = new javax.swing.JPanel();
            label_Agressiveness = new java.awt.Label();
            jLabel_Min1 = new javax.swing.JLabel();
            jLabel_Min9 = new javax.swing.JLabel();
            label5 = new java.awt.Label();
            jSlider_Agressiveness_Min = new javax.swing.JSlider();
            jSlider_Agressiveness_Max = new javax.swing.JSlider();
            label8 = new java.awt.Label();
            label_Children = new java.awt.Label();
            jLabel_Min8 = new javax.swing.JLabel();
            jSpinner_NbChildrenMin = new javax.swing.JSpinner();
            jLabel_Min6 = new javax.swing.JLabel();
            jLabel_Min16 = new javax.swing.JLabel();
            jSpinner_NbChildrenMax = new javax.swing.JSpinner();
            jLabel_Min10 = new javax.swing.JLabel();
            label_Moving = new java.awt.Label();
            jLabel_Min7 = new javax.swing.JLabel();
            jSpinner_MovingFrequencyMin = new javax.swing.JSpinner();
            jLabel_Min2 = new javax.swing.JLabel();
            jLabel_Min3 = new javax.swing.JLabel();
            jSpinner_MovingFrequencyMax = new javax.swing.JSpinner();
            jLabel_Min5 = new javax.swing.JLabel();
            jPanel_Institution = new javax.swing.JPanel();
            jLabel2 = new javax.swing.JLabel();
            label6 = new java.awt.Label();
            label9 = new java.awt.Label();
            jSliderDeterminism = new javax.swing.JSlider();
            jLabel1 = new javax.swing.JLabel();
            jScrollPane4 = new javax.swing.JScrollPane();
            jTableInstitutionDesc = new javax.swing.JTable();
            jLabel5 = new javax.swing.JLabel();
            jLabel_Min17 = new javax.swing.JLabel();
            jLabel_Min11 = new javax.swing.JLabel();
            jLabel_Min4 = new javax.swing.JLabel();
            jLabel4 = new javax.swing.JLabel();
            jLabel_Min18 = new javax.swing.JLabel();
            jPanel_CreatureEdition = new javax.swing.JPanel();
            jPanel_Population = new javax.swing.JPanel();
            jScrollPane_Population1 = new javax.swing.JScrollPane();
            jTree_Pop = new javax.swing.JTree();
            jPanel_IndividualCharacteristics = new javax.swing.JPanel();
            label_desc_Agressiveness2 = new java.awt.Label();
            label_Desc_children = new java.awt.Label();
            label_Desc_Moving = new java.awt.Label();
            label_Desc_Size1 = new java.awt.Label();
            jTextField_Desc_Agressiveness = new javax.swing.JTextField();
            jSpinner_DescNbChildren = new javax.swing.JSpinner();
            label_desc_Agressiveness1 = new java.awt.Label();
            jSpinner_DescMovingFrequency = new javax.swing.JSpinner();
            jSpinner_DescSize = new javax.swing.JSpinner();
            label_Desc_Size = new java.awt.Label();
            jCheckBoxViolatingDesc = new javax.swing.JCheckBox();

            jTabbedPane_IHM.setPreferredSize(new java.awt.Dimension(1140, 640));
            jTabbedPane_IHM.addChangeListener(new javax.swing.event.ChangeListener() {
                public void stateChanged(javax.swing.event.ChangeEvent evt) {
                    jTabbedPane_IHMStateChanged(evt);
                }
            });

            jPanel_SimulationBoard.setPreferredSize(new java.awt.Dimension(1140, 660));
            jPanel_SimulationBoard.setLayout(null);

            jPanel_SimulationControls.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Simulation controls", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N
            jPanel_SimulationControls.setName("qsdf"); // NOI18N

            jButton_Start.setText("Start");
            jButton_Start.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jButton_StartstartActionPerformed(evt);
                }
            });

            jButton_Pause.setText("Pause");
            jButton_Pause.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jButton_PauseActionPerformed(evt);
                }
            });

            jSlider_SimulationSpeed.setMinimum(5);
            jSlider_SimulationSpeed.setMinorTickSpacing(1);
            jSlider_SimulationSpeed.setInverted(true);
            jSlider_SimulationSpeed.setPreferredSize(new java.awt.Dimension(100, 25));
            jSlider_SimulationSpeed.addChangeListener(new javax.swing.event.ChangeListener() {
                public void stateChanged(javax.swing.event.ChangeEvent evt) {
                    jSlider_SimulationSpeedStateChanged(evt);
                }
            });

            jButton_Stop.setText("Clear");
            jButton_Stop.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jButton_StopActionPerformed(evt);
                }
            });

            jLabel_SimulationSpeed.setText("Simulation speed:");

            org.jdesktop.layout.GroupLayout jPanel_SimulationControlsLayout = new org.jdesktop.layout.GroupLayout(jPanel_SimulationControls);
            jPanel_SimulationControls.setLayout(jPanel_SimulationControlsLayout);
            jPanel_SimulationControlsLayout.setHorizontalGroup(
                jPanel_SimulationControlsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(jPanel_SimulationControlsLayout.createSequentialGroup()
                    .add(11, 11, 11)
                    .add(jPanel_SimulationControlsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        .add(jPanel_SimulationControlsLayout.createSequentialGroup()
                            .add(jLabel_SimulationSpeed)
                            .add(18, 18, 18)
                            .add(jSlider_SimulationSpeed, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .add(jPanel_SimulationControlsLayout.createSequentialGroup()
                            .add(jButton_Start)
                            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                            .add(jButton_Pause)
                            .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                            .add(jButton_Stop)))
                    .add(79, 79, 79))
            );
            jPanel_SimulationControlsLayout.setVerticalGroup(
                jPanel_SimulationControlsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(jPanel_SimulationControlsLayout.createSequentialGroup()
                    .add(jPanel_SimulationControlsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(jButton_Start)
                        .add(jButton_Pause)
                        .add(jButton_Stop))
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(jPanel_SimulationControlsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        .add(jLabel_SimulationSpeed)
                        .add(jSlider_SimulationSpeed, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(22, 22, 22))
            );

            jPanel_SimulationBoard.add(jPanel_SimulationControls);
            jPanel_SimulationControls.setBounds(10, 20, 290, 100);

            jPanel_SimulationPanel.setBackground(new java.awt.Color(255, 255, 255));
            jPanel_SimulationPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

            org.jdesktop.layout.GroupLayout jPanel_SimulationPanelLayout = new org.jdesktop.layout.GroupLayout(jPanel_SimulationPanel);
            jPanel_SimulationPanel.setLayout(jPanel_SimulationPanelLayout);
            jPanel_SimulationPanelLayout.setHorizontalGroup(
                jPanel_SimulationPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(0, 528, Short.MAX_VALUE)
            );
            jPanel_SimulationPanelLayout.setVerticalGroup(
                jPanel_SimulationPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(0, 498, Short.MAX_VALUE)
            );

            jPanel_SimulationBoard.add(jPanel_SimulationPanel);
            jPanel_SimulationPanel.setBounds(310, 20, 530, 500);

            jPanel_SpeciesList.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Generate population", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N
            jPanel_SpeciesList.setLayout(null);

            jTable1.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {
                    {"species_1", new Long(20), new Long(0)},
                    {"species_2", new Long(60), new Long(12)},
                    {"species_3", new Long(20), new Long(1)},
                    {null, null, null}
                },
                new String [] {
                    "Species name", "Proportion", "Violations %"
                }
            ) {
                Class[] types = new Class [] {
                    java.lang.Object.class, java.lang.Long.class, java.lang.Long.class
                };

                public Class getColumnClass(int columnIndex) {
                    return types [columnIndex];
                }
            });
            jScrollPane_Species.setViewportView(jTable1);

            jPanel_SpeciesList.add(jScrollPane_Species);
            jScrollPane_Species.setBounds(10, 100, 270, 90);

            jLabel3.setText("Total number of agents:");
            jPanel_SpeciesList.add(jLabel3);
            jLabel3.setBounds(10, 30, 160, 16);

            jTextField_TotalPop.setText("20");
            jPanel_SpeciesList.add(jTextField_TotalPop);
            jTextField_TotalPop.setBounds(220, 20, 40, 28);

            jRadioButton_ReplacePop.setSelected(true);
            jRadioButton_ReplacePop.setText("Replace existing population");
            jRadioButton_ReplacePop.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jRadioButton_ReplacePopActionPerformed(evt);
                }
            });
            jPanel_SpeciesList.add(jRadioButton_ReplacePop);
            jRadioButton_ReplacePop.setBounds(10, 70, 207, 23);

            jRadioButtonAddToPop.setText("Add agents to existing population");
            jRadioButtonAddToPop.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jRadioButtonAddToPopActionPerformed(evt);
                }
            });
            jPanel_SpeciesList.add(jRadioButtonAddToPop);
            jRadioButtonAddToPop.setBounds(10, 50, 247, 23);

            jButton_GeneratePop.setText("Generate");
            jButton_GeneratePop.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jButton_GeneratePopActionPerformed(evt);
                }
            });
            jPanel_SpeciesList.add(jButton_GeneratePop);
            jButton_GeneratePop.setBounds(170, 190, 99, 29);

            jPanel_SimulationBoard.add(jPanel_SpeciesList);
            jPanel_SpeciesList.setBounds(10, 130, 290, 230);

            jPanel_GenerationMethod.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Manually add an agent", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N
            jPanel_GenerationMethod.setLayout(null);

            jRadioButton_InstitutionMethod.setFont(new java.awt.Font("Lucida Grande", 0, 10)); // NOI18N
            jRadioButton_InstitutionMethod.setText("generate agent from Institution parameters");
            jRadioButton_InstitutionMethod.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jRadioButton_InstitutionMethodActionPerformed(evt);
                }
            });
            jPanel_GenerationMethod.add(jRadioButton_InstitutionMethod);
            jRadioButton_InstitutionMethod.setBounds(10, 50, 250, 23);

            jRadioButton_SpeciesMethod.setFont(new java.awt.Font("Lucida Grande", 0, 10)); // NOI18N
            jRadioButton_SpeciesMethod.setSelected(true);
            jRadioButton_SpeciesMethod.setText("generate agent from species parameters");
            jRadioButton_SpeciesMethod.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jRadioButton_SpeciesMethodActionPerformed(evt);
                }
            });
            jPanel_GenerationMethod.add(jRadioButton_SpeciesMethod);
            jRadioButton_SpeciesMethod.setBounds(10, 70, 240, 23);

            jLabel7.setText("select species:");
            jPanel_GenerationMethod.add(jLabel7);
            jLabel7.setBounds(10, 100, 92, 16);

            jComboBox_SpeciesSelection.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jComboBox_SpeciesSelectionActionPerformed(evt);
                }
            });
            jPanel_GenerationMethod.add(jComboBox_SpeciesSelection);
            jComboBox_SpeciesSelection.setBounds(110, 100, 140, 20);

            jCheckBox_ViolatingAgent.setText("violating agent?");
            jCheckBox_ViolatingAgent.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jCheckBox_ViolatingAgentActionPerformed(evt);
                }
            });
            jPanel_GenerationMethod.add(jCheckBox_ViolatingAgent);
            jCheckBox_ViolatingAgent.setBounds(30, 130, 131, 23);

            jButton_Generate.setText("Generate");
            jButton_Generate.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jButton_GenerateActionPerformed(evt);
                }
            });
            jPanel_GenerationMethod.add(jButton_Generate);
            jButton_Generate.setBounds(10, 320, 99, 29);

            jLabel8.setText("Choose generation method:");
            jPanel_GenerationMethod.add(jLabel8);
            jLabel8.setBounds(10, 30, 174, 16);

            jPanel_AgentCharacteristics.setBorder(javax.swing.BorderFactory.createTitledBorder("Edit agent's characteristics"));
            jPanel_AgentCharacteristics.setLayout(null);

            label_ind_Size.setFont(new java.awt.Font("Tahoma", 0, 11));
            label_ind_Size.setText("Size :");
            jPanel_AgentCharacteristics.add(label_ind_Size);
            label_ind_Size.setBounds(80, 120, 31, 18);

            label_ind_Moving.setFont(new java.awt.Font("Tahoma", 0, 11));
            label_ind_Moving.setText("Moving frequency :");
            jPanel_AgentCharacteristics.add(label_ind_Moving);
            label_ind_Moving.setBounds(20, 90, 96, 18);

            label_ind_children.setFont(new java.awt.Font("Tahoma", 0, 11));
            label_ind_children.setText("Number of Children :");
            jPanel_AgentCharacteristics.add(label_ind_children);
            label_ind_children.setBounds(10, 60, 106, 18);

            label_ind_Agressiveness.setFont(new java.awt.Font("Tahoma", 0, 11));
            label_ind_Agressiveness.setText("Agressiveness :");
            jPanel_AgentCharacteristics.add(label_ind_Agressiveness);
            label_ind_Agressiveness.setBounds(40, 30, 80, 18);

            jTextField_AgressivenessAddAgent.setEditable(false);
            jTextField_AgressivenessAddAgent.setText("32");
            jTextField_AgressivenessAddAgent.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jTextField_AgressivenessAddAgentActionPerformed(evt);
                }
            });
            jPanel_AgentCharacteristics.add(jTextField_AgressivenessAddAgent);
            jTextField_AgressivenessAddAgent.setBounds(120, 30, 40, 28);

            jLabel10.setText("%");
            jPanel_AgentCharacteristics.add(jLabel10);
            jLabel10.setBounds(180, 30, 9, 16);

            jSpinner_NbChildrenAddAgent.setEnabled(false);
            jSpinner_NbChildrenAddAgent.addChangeListener(new javax.swing.event.ChangeListener() {
                public void stateChanged(javax.swing.event.ChangeEvent evt) {
                    jSpinner_NbChildrenAddAgentStateChanged(evt);
                }
            });
            jPanel_AgentCharacteristics.add(jSpinner_NbChildrenAddAgent);
            jSpinner_NbChildrenAddAgent.setBounds(120, 60, 50, 28);

            jSpinner_MovingFrequencyAddAgent.setEnabled(false);
            jSpinner_MovingFrequencyAddAgent.addChangeListener(new javax.swing.event.ChangeListener() {
                public void stateChanged(javax.swing.event.ChangeEvent evt) {
                    jSpinner_MovingFrequencyAddAgentStateChanged(evt);
                }
            });
            jPanel_AgentCharacteristics.add(jSpinner_MovingFrequencyAddAgent);
            jSpinner_MovingFrequencyAddAgent.setBounds(120, 90, 50, 28);

            jSpinner_SizeAddAgent.setEnabled(false);
            jSpinner_SizeAddAgent.addChangeListener(new javax.swing.event.ChangeListener() {
                public void stateChanged(javax.swing.event.ChangeEvent evt) {
                    jSpinner_SizeAddAgentStateChanged(evt);
                }
            });
            jPanel_AgentCharacteristics.add(jSpinner_SizeAddAgent);
            jSpinner_SizeAddAgent.setBounds(120, 120, 50, 28);

            jLabel11.setText("px");
            jPanel_AgentCharacteristics.add(jLabel11);
            jLabel11.setBounds(180, 120, 20, 16);

            jPanel_GenerationMethod.add(jPanel_AgentCharacteristics);
            jPanel_AgentCharacteristics.setBounds(40, 160, 200, 150);

            jButton_Add.setText("Add agent");
            jButton_Add.setEnabled(false);
            jButton_Add.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jButton_AddActionPerformed(evt);
                }
            });
            jPanel_GenerationMethod.add(jButton_Add);
            jButton_Add.setBounds(120, 320, 140, 29);

            jButton1.setText("Generate and add the agent");
            jPanel_GenerationMethod.add(jButton1);
            jButton1.setBounds(20, 360, 220, 29);

            jPanel_SimulationBoard.add(jPanel_GenerationMethod);
            jPanel_GenerationMethod.setBounds(850, 20, 270, 400);

            jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Statistics", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N
            jPanel1.setLayout(null);

            jTable_Statistics.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {
                    {"species1", "12", "1"},
                    {"species2", "1", "0"},
                    {"species3", "34", "11"},
                    {"customspecies", "7", "0"}
                },
                new String [] {
                    "Species", "Population", "Violators"
                }
            ) {
                Class[] types = new Class [] {
                    java.lang.String.class, java.lang.String.class, java.lang.String.class
                };

                public Class getColumnClass(int columnIndex) {
                    return types [columnIndex];
                }
            });
            jTable_Statistics.setEnabled(false);
            jScrollPane1.setViewportView(jTable_Statistics);

            jPanel1.add(jScrollPane1);
            jScrollPane1.setBounds(10, 20, 270, 120);

            jPanel_SimulationBoard.add(jPanel1);
            jPanel1.setBounds(10, 370, 290, 150);

            jScrollPane2.setAutoscrolls(true);

            jTextAreaLog.setColumns(20);
            jTextAreaLog.setEditable(false);
            jTextAreaLog.setRows(5);
            jScrollPane2.setViewportView(jTextAreaLog);

            jPanel_SimulationBoard.add(jScrollPane2);
            jScrollPane2.setBounds(310, 530, 530, 84);

            jTabbedPane_IHM.addTab("Simulation Panel", jPanel_SimulationBoard);

            jPanel_SpeciesDescription.setPreferredSize(new java.awt.Dimension(1140, 400));
            jPanel_SpeciesDescription.setLayout(null);

            jPanel_Species.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Species", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N

            jComboBox_SpeciesChoice.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jComboBox_SpeciesChoiceActionPerformed(evt);
                }
            });

            jButton_AddSpecies.setText("Add Species");
            jButton_AddSpecies.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jButton_AddSpeciesActionPerformed(evt);
                }
            });

            jButtonRemoveSpecies.setText("Remove Species");
            jButtonRemoveSpecies.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jButtonRemoveSpeciesActionPerformed(evt);
                }
            });

            jTextField_SpeciesName.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jTextField_SpeciesNameActionPerformed(evt);
                }
            });

            jLabel_Name1.setFont(new java.awt.Font("Tahoma", 1, 11));
            jLabel_Name1.setText("Name :");

            jPanel_PhysicalParameters.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Physical parameters", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N
            jPanel_PhysicalParameters.setLayout(null);

            label_Size.setFont(new java.awt.Font("Tahoma", 0, 11));
            label_Size.setText("Size :");
            jPanel_PhysicalParameters.add(label_Size);
            label_Size.setBounds(30, 30, 31, 18);

            jLabel_Min12.setText("from");
            jPanel_PhysicalParameters.add(jLabel_Min12);
            jLabel_Min12.setBounds(80, 30, 30, 16);

            jSpinner_SizeMin.addChangeListener(new javax.swing.event.ChangeListener() {
                public void stateChanged(javax.swing.event.ChangeEvent evt) {
                    jSpinner_SizeMinStateChanged(evt);
                }
            });
            jPanel_PhysicalParameters.add(jSpinner_SizeMin);
            jSpinner_SizeMin.setBounds(110, 30, 37, 28);

            jLabel_Min13.setText("(min)");
            jPanel_PhysicalParameters.add(jLabel_Min13);
            jLabel_Min13.setBounds(140, 30, 32, 16);

            jLabel_Min14.setText("to");
            jPanel_PhysicalParameters.add(jLabel_Min14);
            jLabel_Min14.setBounds(180, 30, 13, 16);

            jSpinner_SizeMax.addChangeListener(new javax.swing.event.ChangeListener() {
                public void stateChanged(javax.swing.event.ChangeEvent evt) {
                    jSpinner_SizeMaxStateChanged(evt);
                }
            });
            jPanel_PhysicalParameters.add(jSpinner_SizeMax);
            jSpinner_SizeMax.setBounds(200, 30, 37, 28);

            jLabel_Min15.setText("(max)");
            jPanel_PhysicalParameters.add(jLabel_Min15);
            jLabel_Min15.setBounds(230, 30, 35, 16);

            jPanel_BehavioralParameters.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Behavioral parameters", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N

            label_Agressiveness.setFont(new java.awt.Font("Tahoma", 0, 11));
            label_Agressiveness.setText("Agressiveness :");

            jLabel_Min1.setText("from");

            jLabel_Min9.setText("to");

            label5.setFont(new java.awt.Font("Tahoma", 0, 11));
            label5.setText("0%");

            jSlider_Agressiveness_Min.setValue(20);
            jSlider_Agressiveness_Min.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseReleased(java.awt.event.MouseEvent evt) {
                    jSlider_Agressiveness_MinMouseReleased(evt);
                }
            });

            jSlider_Agressiveness_Max.setValue(60);
            jSlider_Agressiveness_Max.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseReleased(java.awt.event.MouseEvent evt) {
                    jSlider_Agressiveness_MaxMouseReleased(evt);
                }
            });

            label8.setFont(new java.awt.Font("Tahoma", 0, 11));
            label8.setText("100%");

            label_Children.setFont(new java.awt.Font("Tahoma", 0, 11));
            label_Children.setText("Number of children :");

            jLabel_Min8.setText("from");

            jSpinner_NbChildrenMin.addChangeListener(new javax.swing.event.ChangeListener() {
                public void stateChanged(javax.swing.event.ChangeEvent evt) {
                    jSpinner_NbChildrenMinStateChanged(evt);
                }
            });

            jLabel_Min6.setText("(min)");

            jLabel_Min16.setText("to");

            jSpinner_NbChildrenMax.addChangeListener(new javax.swing.event.ChangeListener() {
                public void stateChanged(javax.swing.event.ChangeEvent evt) {
                    jSpinner_NbChildrenMaxStateChanged(evt);
                }
            });

            jLabel_Min10.setText("(max)");

            label_Moving.setFont(new java.awt.Font("Tahoma", 0, 11));
            label_Moving.setText("Moving frequency :");

            jLabel_Min7.setText("from");

            jSpinner_MovingFrequencyMin.addChangeListener(new javax.swing.event.ChangeListener() {
                public void stateChanged(javax.swing.event.ChangeEvent evt) {
                    jSpinner_MovingFrequencyMinStateChanged(evt);
                }
            });

            jLabel_Min2.setText("(min)");

            jLabel_Min3.setText("to");

            jSpinner_MovingFrequencyMax.addChangeListener(new javax.swing.event.ChangeListener() {
                public void stateChanged(javax.swing.event.ChangeEvent evt) {
                    jSpinner_MovingFrequencyMaxStateChanged(evt);
                }
            });

            jLabel_Min5.setText("(max)");

            org.jdesktop.layout.GroupLayout jPanel_BehavioralParametersLayout = new org.jdesktop.layout.GroupLayout(jPanel_BehavioralParameters);
            jPanel_BehavioralParameters.setLayout(jPanel_BehavioralParametersLayout);
            jPanel_BehavioralParametersLayout.setHorizontalGroup(
                jPanel_BehavioralParametersLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(jPanel_BehavioralParametersLayout.createSequentialGroup()
                    .add(14, 14, 14)
                    .add(label_Agressiveness, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(30, 30, 30)
                    .add(jPanel_BehavioralParametersLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        .add(jLabel_Min1)
                        .add(jPanel_BehavioralParametersLayout.createSequentialGroup()
                            .add(10, 10, 10)
                            .add(jLabel_Min3)))
                    .add(10, 10, 10)
                    .add(label5, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(9, 9, 9)
                    .add(jPanel_BehavioralParametersLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        .add(jSlider_Agressiveness_Max, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 130, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(jSlider_Agressiveness_Min, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 130, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(label8, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(jPanel_BehavioralParametersLayout.createSequentialGroup()
                    .add(14, 14, 14)
                    .add(label_Children, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(6, 6, 6)
                    .add(jLabel_Min8)
                    .add(10, 10, 10)
                    .add(jSpinner_NbChildrenMin, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(3, 3, 3)
                    .add(jLabel_Min6)
                    .add(8, 8, 8)
                    .add(jLabel_Min16)
                    .add(7, 7, 7)
                    .add(jSpinner_NbChildrenMax, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(13, 13, 13)
                    .add(jLabel_Min10))
                .add(jPanel_BehavioralParametersLayout.createSequentialGroup()
                    .add(14, 14, 14)
                    .add(label_Moving, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(14, 14, 14)
                    .add(jLabel_Min7)
                    .add(10, 10, 10)
                    .add(jSpinner_MovingFrequencyMin, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(3, 3, 3)
                    .add(jLabel_Min2)
                    .add(8, 8, 8)
                    .add(jLabel_Min9)
                    .add(7, 7, 7)
                    .add(jSpinner_MovingFrequencyMax, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(13, 13, 13)
                    .add(jLabel_Min5))
            );
            jPanel_BehavioralParametersLayout.setVerticalGroup(
                jPanel_BehavioralParametersLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(jPanel_BehavioralParametersLayout.createSequentialGroup()
                    .add(10, 10, 10)
                    .add(jPanel_BehavioralParametersLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        .add(jPanel_BehavioralParametersLayout.createSequentialGroup()
                            .add(10, 10, 10)
                            .add(label_Agressiveness, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .add(jPanel_BehavioralParametersLayout.createSequentialGroup()
                            .add(jLabel_Min1)
                            .add(4, 4, 4)
                            .add(jLabel_Min3))
                        .add(jPanel_BehavioralParametersLayout.createSequentialGroup()
                            .add(10, 10, 10)
                            .add(label5, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .add(jPanel_BehavioralParametersLayout.createSequentialGroup()
                            .add(20, 20, 20)
                            .add(jSlider_Agressiveness_Max, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .add(jSlider_Agressiveness_Min, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(jPanel_BehavioralParametersLayout.createSequentialGroup()
                            .add(10, 10, 10)
                            .add(label8, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                    .add(11, 11, 11)
                    .add(jPanel_BehavioralParametersLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        .add(label_Children, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(jLabel_Min8)
                        .add(jSpinner_NbChildrenMin, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(jLabel_Min6)
                        .add(jLabel_Min16)
                        .add(jSpinner_NbChildrenMax, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(jLabel_Min10))
                    .add(2, 2, 2)
                    .add(jPanel_BehavioralParametersLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        .add(label_Moving, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(jLabel_Min7)
                        .add(jSpinner_MovingFrequencyMin, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(jLabel_Min2)
                        .add(jLabel_Min9)
                        .add(jSpinner_MovingFrequencyMax, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(jLabel_Min5)))
            );

            org.jdesktop.layout.GroupLayout jPanel_SpeciesLayout = new org.jdesktop.layout.GroupLayout(jPanel_Species);
            jPanel_Species.setLayout(jPanel_SpeciesLayout);
            jPanel_SpeciesLayout.setHorizontalGroup(
                jPanel_SpeciesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(jPanel_SpeciesLayout.createSequentialGroup()
                    .add(14, 14, 14)
                    .add(jPanel_SpeciesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        .add(jPanel_SpeciesLayout.createSequentialGroup()
                            .add(jComboBox_SpeciesChoice, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 124, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(156, 156, 156)
                            .add(jButton_AddSpecies))
                        .add(jPanel_SpeciesLayout.createSequentialGroup()
                            .add(jLabel_Name1)
                            .add(11, 11, 11)
                            .add(jTextField_SpeciesName, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 200, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(30, 30, 30)
                            .add(jButtonRemoveSpecies))
                        .add(jPanel_BehavioralParameters, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 420, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(jPanel_PhysicalParameters, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 420, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
            );
            jPanel_SpeciesLayout.setVerticalGroup(
                jPanel_SpeciesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(jPanel_SpeciesLayout.createSequentialGroup()
                    .add(jPanel_SpeciesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        .add(jButton_AddSpecies)
                        .add(jPanel_SpeciesLayout.createSequentialGroup()
                            .add(10, 10, 10)
                            .add(jComboBox_SpeciesChoice, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(jPanel_SpeciesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        .add(jPanel_SpeciesLayout.createSequentialGroup()
                            .add(10, 10, 10)
                            .add(jLabel_Name1))
                        .add(jPanel_SpeciesLayout.createSequentialGroup()
                            .add(10, 10, 10)
                            .add(jTextField_SpeciesName, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .add(jButtonRemoveSpecies))
                    .add(2, 2, 2)
                    .add(jPanel_BehavioralParameters, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 150, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(10, 10, 10)
                    .add(jPanel_PhysicalParameters, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 60, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
            );

            jPanel_SpeciesDescription.add(jPanel_Species);
            jPanel_Species.setBounds(10, 20, 460, 360);

            jPanel_Institution.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Institution", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N

            jLabel2.setText("Institution determinism specification :");

            label6.setFont(new java.awt.Font("Tahoma", 0, 11));
            label6.setText("0%");

            label9.setFont(new java.awt.Font("Tahoma", 0, 11));
            label9.setText("100%");

            jSliderDeterminism.addChangeListener(new javax.swing.event.ChangeListener() {
                public void stateChanged(javax.swing.event.ChangeEvent evt) {
                    jSliderDeterminismStateChanged(evt);
                }
            });

            jLabel1.setText("The Institution defines the maximal boundaries for each of the parameters :");

            jTableInstitutionDesc.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {
                    {"Aggressiveness", null, null},
                    {"Number of children", null, null},
                    {"Moving frequency", null, null},
                    {"Size", null, null}
                },
                new String [] {
                    "Parameter", "Minimal value", "Maximal value"
                }
            ));
            jTableInstitutionDesc.setEnabled(false);
            jScrollPane4.setViewportView(jTableInstitutionDesc);

            jLabel5.setText("Definition of the parameters :");

            jLabel_Min17.setFont(new java.awt.Font("Lucida Grande", 0, 10)); // NOI18N
            jLabel_Min17.setText("Aggressiveness:      defines the probability the agent will try to fight any other species member when meeting it. ");

            jLabel_Min11.setFont(new java.awt.Font("Lucida Grande", 0, 10)); // NOI18N
            jLabel_Min11.setText("Number of children: defines the number of children an agent will have each time it reproduces.");

            jLabel_Min4.setFont(new java.awt.Font("Lucida Grande", 0, 10)); // NOI18N
            jLabel_Min4.setText("Moving step:            defines the step of an agent each time he moves.");

            jLabel4.setFont(new java.awt.Font("Lucida Grande", 0, 10)); // NOI18N
            jLabel4.setText("Size:               minimal and maximal size of the agent, in pixel.");

            jLabel_Min18.setFont(new java.awt.Font("Lucida Grande", 0, 10)); // NOI18N
            jLabel_Min18.setText("                                It also influences the probability the agent will try to reproduce.");

            org.jdesktop.layout.GroupLayout jPanel_InstitutionLayout = new org.jdesktop.layout.GroupLayout(jPanel_Institution);
            jPanel_Institution.setLayout(jPanel_InstitutionLayout);
            jPanel_InstitutionLayout.setHorizontalGroup(
                jPanel_InstitutionLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(jPanel_InstitutionLayout.createSequentialGroup()
                    .add(jPanel_InstitutionLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        .add(jPanel_InstitutionLayout.createSequentialGroup()
                            .addContainerGap()
                            .add(jLabel2)
                            .add(66, 66, 66)
                            .add(label6, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                            .add(jSliderDeterminism, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 149, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(2, 2, 2)
                            .add(label9, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .add(jPanel_InstitutionLayout.createSequentialGroup()
                            .add(20, 20, 20)
                            .add(jPanel_InstitutionLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                .add(jLabel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 494, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(jScrollPane4, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 390, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                        .add(jPanel_InstitutionLayout.createSequentialGroup()
                            .addContainerGap()
                            .add(jPanel_InstitutionLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                .add(jLabel5, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 205, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(jLabel4, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 340, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(jLabel_Min4)
                                .add(jLabel_Min11, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 470, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(jLabel_Min18, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 560, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(jLabel_Min17, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 560, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))))
                    .add(28, 28, 28))
            );
            jPanel_InstitutionLayout.setVerticalGroup(
                jPanel_InstitutionLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(jPanel_InstitutionLayout.createSequentialGroup()
                    .add(jPanel_InstitutionLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        .add(jPanel_InstitutionLayout.createSequentialGroup()
                            .add(34, 34, 34)
                            .add(label9, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .add(jPanel_InstitutionLayout.createSequentialGroup()
                            .addContainerGap()
                            .add(jPanel_InstitutionLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                .add(jLabel2)
                                .add(jPanel_InstitutionLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                                    .add(jSliderDeterminism, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                    .add(label6, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))))
                    .add(32, 32, 32)
                    .add(jLabel1)
                    .add(4, 4, 4)
                    .add(jScrollPane4, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 90, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(10, 10, 10)
                    .add(jLabel5)
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(jLabel_Min17, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(3, 3, 3)
                    .add(jLabel_Min18, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(jLabel_Min11)
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(jLabel_Min4)
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(jLabel4)
                    .addContainerGap())
            );

            jPanel_SpeciesDescription.add(jPanel_Institution);
            jPanel_Institution.setBounds(490, 20, 630, 370);

            jTabbedPane_IHM.addTab("Species Description", jPanel_SpeciesDescription);

            jPanel_CreatureEdition.setPreferredSize(new java.awt.Dimension(510, 410));
            jPanel_CreatureEdition.setLayout(null);

            jPanel_Population.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Population", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N
            jPanel_Population.setLayout(null);

            jTree_Pop.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
                public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                    jTree_PopValueChanged(evt);
                }
            });
            jScrollPane_Population1.setViewportView(jTree_Pop);

            jPanel_Population.add(jScrollPane_Population1);
            jScrollPane_Population1.setBounds(20, 30, 250, 420);

            jPanel_IndividualCharacteristics.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Individual characteristics", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N
            jPanel_IndividualCharacteristics.setLayout(null);

            label_desc_Agressiveness2.setFont(new java.awt.Font("Tahoma", 0, 11));
            label_desc_Agressiveness2.setText("Agressiveness :");
            jPanel_IndividualCharacteristics.add(label_desc_Agressiveness2);
            label_desc_Agressiveness2.setBounds(40, 40, 80, 18);

            label_Desc_children.setFont(new java.awt.Font("Tahoma", 0, 11));
            label_Desc_children.setText("Number of Children :");
            jPanel_IndividualCharacteristics.add(label_Desc_children);
            label_Desc_children.setBounds(20, 70, 106, 18);

            label_Desc_Moving.setFont(new java.awt.Font("Tahoma", 0, 11));
            label_Desc_Moving.setText("Moving frequency :");
            jPanel_IndividualCharacteristics.add(label_Desc_Moving);
            label_Desc_Moving.setBounds(30, 100, 96, 18);

            label_Desc_Size1.setFont(new java.awt.Font("Tahoma", 0, 11));
            label_Desc_Size1.setText("Size :");
            jPanel_IndividualCharacteristics.add(label_Desc_Size1);
            label_Desc_Size1.setBounds(90, 130, 31, 18);

            jTextField_Desc_Agressiveness.setText("80");
            jTextField_Desc_Agressiveness.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jTextField_Desc_AgressivenessActionPerformed(evt);
                }
            });
            jPanel_IndividualCharacteristics.add(jTextField_Desc_Agressiveness);
            jTextField_Desc_Agressiveness.setBounds(130, 40, 30, 28);

            jSpinner_DescNbChildren.addChangeListener(new javax.swing.event.ChangeListener() {
                public void stateChanged(javax.swing.event.ChangeEvent evt) {
                    jSpinner_DescNbChildrenStateChanged(evt);
                }
            });
            jPanel_IndividualCharacteristics.add(jSpinner_DescNbChildren);
            jSpinner_DescNbChildren.setBounds(130, 70, 50, 28);

            label_desc_Agressiveness1.setFont(new java.awt.Font("Tahoma", 0, 11));
            label_desc_Agressiveness1.setText(" %");
            jPanel_IndividualCharacteristics.add(label_desc_Agressiveness1);
            label_desc_Agressiveness1.setBounds(170, 40, 18, 18);

            jSpinner_DescMovingFrequency.addChangeListener(new javax.swing.event.ChangeListener() {
                public void stateChanged(javax.swing.event.ChangeEvent evt) {
                    jSpinner_DescMovingFrequencyStateChanged(evt);
                }
            });
            jPanel_IndividualCharacteristics.add(jSpinner_DescMovingFrequency);
            jSpinner_DescMovingFrequency.setBounds(130, 100, 50, 28);

            jSpinner_DescSize.addChangeListener(new javax.swing.event.ChangeListener() {
                public void stateChanged(javax.swing.event.ChangeEvent evt) {
                    jSpinner_DescSizeStateChanged(evt);
                }
            });
            jPanel_IndividualCharacteristics.add(jSpinner_DescSize);
            jSpinner_DescSize.setBounds(130, 130, 50, 28);

            label_Desc_Size.setFont(new java.awt.Font("Tahoma", 0, 11));
            label_Desc_Size.setText(" pixel");
            jPanel_IndividualCharacteristics.add(label_Desc_Size);
            label_Desc_Size.setBounds(190, 130, 30, 18);

            jCheckBoxViolatingDesc.setText("Violating");
            jCheckBoxViolatingDesc.setEnabled(false);
            jCheckBoxViolatingDesc.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jCheckBoxViolatingDescActionPerformed(evt);
                }
            });
            jPanel_IndividualCharacteristics.add(jCheckBoxViolatingDesc);
            jCheckBoxViolatingDesc.setBounds(130, 160, 88, 23);
            jCheckBoxViolatingDesc.getAccessibleContext().setAccessibleName("jCheckBox1");

            jPanel_Population.add(jPanel_IndividualCharacteristics);
            jPanel_IndividualCharacteristics.setBounds(290, 30, 240, 200);

            jPanel_CreatureEdition.add(jPanel_Population);
            jPanel_Population.setBounds(290, 20, 550, 470);

            jTabbedPane_IHM.addTab("Creature Edition", jPanel_CreatureEdition);

            org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
            getContentPane().setLayout(layout);
            layout.setHorizontalGroup(
                layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(jTabbedPane_IHM, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 1140, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
            );
            layout.setVerticalGroup(
                layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(jTabbedPane_IHM, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 660, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
            );
        }// </editor-fold>//GEN-END:initComponents

    private void addAgents(int nombre, Norm norm) throws Exception {
        Agent creature = null;
        for (int i = 0; i < nombre; i++) {
            creature = new Agent(behaviorGenerator.generateBehavior(norm), behaviorGenerator, this.environment);
            this.environment.addNewAgent(this.environment.getFreePlace(), creature);
        }

    }

    private void updateTable() {

        Vector<String> columnNames = new Vector<String>();

        columnNames.add("Species Name");
        columnNames.add("Proportion of each species");
        columnNames.add("Percentage of violation");

        Vector<Object> data = new Vector<Object>();

        if (norms.size() != 0) {
            int size = norms.size() - 1;
            int proportion = 100 / size;
            int violation = 0;
            int complement = 0;
            boolean first = true;


            for (Norm norm : norms) {

                Vector<Object> ligne = new Vector<Object>();

                if (!norm.getName().equals("Institution")) {
                    ligne.add(norm);
                    if (first) {
                        complement = 100 - (size - 1) * proportion;
                        ligne.add(complement);
                        first = false;
                    }
                    ligne.add(proportion);
                    ligne.add(violation);

                    data.add(ligne);
                }
            }

        }
        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames);


        jTable1.setModel(tableModel);
    }

    private void updateTableInstitutionDesc() throws Exception {

        String[] columnNames = {"Parameter",
            "Minimal Value",
            "Maximal Value"};


        Object[][] data = {
            {"Agressiveness", mainInstitution.getInstitutionalParameters().get("agressiveness").getDefinitionSet().getBorneInf(),
                mainInstitution.getInstitutionalParameters().get("agressiveness").getDefinitionSet().getBorneSup()},
            {"Number of children", mainInstitution.getInstitutionalParameters().get("nbChildren").getDefinitionSet().getBorneInf(),
                mainInstitution.getInstitutionalParameters().get("nbChildren").getDefinitionSet().getBorneSup()},
            {"Moving Frequency", mainInstitution.getInstitutionalParameters().get("moving").getDefinitionSet().getBorneInf(),
                mainInstitution.getInstitutionalParameters().get("moving").getDefinitionSet().getBorneSup()},
            {"Size", mainInstitution.getInstitutionalParameters().get("size").getDefinitionSet().getBorneInf(),
                mainInstitution.getInstitutionalParameters().get("size").getDefinitionSet().getBorneSup()}
        };


        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames);


        jTableInstitutionDesc.setModel(tableModel);
        
    }

    private void updateStatisticsTable() {
        Vector<String> columnNames = new Vector<String>();

        columnNames.add("Species Name");
        columnNames.add("Population");
        columnNames.add("Violator");

        Vector<Object> data = new Vector<Object>();


        int compteur = 0;
        int violatorsCompteur = 0;


        for (Norm norm : norms) {

            Vector<Object> ligne = new Vector<Object>();

            if (!norm.getName().equals("Institution")) {
                ligne.add(norm);
                for (Agent agent : environment.getGrid().values()) {
                    if (agent.getBehavior().getNormMother().getName().equals(norm.getName())) {
                        compteur = compteur + 1;
                        if (agent.getBehavior().isViolating()) {
                            violatorsCompteur = violatorsCompteur + 1;
                        }
                    }
                }
                ligne.add(compteur);
                ligne.add(violatorsCompteur);

                data.add(ligne);
                compteur = 0;
                violatorsCompteur = 0;
            }
        }





        DefaultTableModel tableStatisticsModel = new DefaultTableModel(data, columnNames);


        jTable_Statistics.setModel(tableStatisticsModel);

    }

    private void updateTree() {
        //Tree Update
        DefaultMutableTreeNode top =
                new DefaultMutableTreeNode(mainInstitution);


        DefaultMutableTreeNode normNode = null;
        DefaultMutableTreeNode agentNode = null;

        for (Norm norm : norms) {
            if (!norm.getName().equals("Institution")) {
                normNode = new DefaultMutableTreeNode(norm);
                for (Agent agent : environment.getGrid().values()) {
                    if (agent.getBehavior().getNormMother().getName().equals(norm.getName())) {
                        agentNode = new DefaultMutableTreeNode(agent);
                        normNode.add(agentNode);
                    }
                }

                top.add(normNode);
            } else {
                for (Agent agent : environment.getGrid().values()) {
                    if (agent.getBehavior().getNormMother().getName().equals(norm.getName())) {
                        top.add(agentNode);
                    }
                }
            }
        }



        DefaultTreeModel treeModel = new DefaultTreeModel(top);
        jTree_Pop.setModel(treeModel);
    }
    private void jButton_StartstartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_StartstartActionPerformed
        // TODO add your handling code here

        if (!environment.isActive()) {
            environment.setActive(true);
            synchronized (this.environment) {
                environment.notify();
            }

        } else {
            environment.start();
        }

    }//GEN-LAST:event_jButton_StartstartActionPerformed

    private void jButton_PauseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_PauseActionPerformed
        // TODO add your handling code here:
        environment.setActive(false);

}//GEN-LAST:event_jButton_PauseActionPerformed

    private void clearGrid() {
        environment.setActive(false);
        environment.Clear();
    }

    private void jButton_StopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_StopActionPerformed

        clearGrid();

}//GEN-LAST:event_jButton_StopActionPerformed

    private void jSlider_SimulationSpeedStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSlider_SimulationSpeedStateChanged

        environment.setSpeed(jSlider_SimulationSpeed.getValue());
}//GEN-LAST:event_jSlider_SimulationSpeedStateChanged

    private void jRadioButton_ReplacePopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton_ReplacePopActionPerformed
        jRadioButtonAddToPop.setSelected(false);
}//GEN-LAST:event_jRadioButton_ReplacePopActionPerformed

    private void jButton_AddSpeciesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_AddSpeciesActionPerformed
        try {

            Norm tempNorm = new Norm("NewSpecies", mainInstitution);
            tempNorm = tempNorm.completeNorm();
            norms.add(tempNorm);

            jSlider_Agressiveness_Max.setValue((int) mainInstitution.getInstitutionalParameters().get("agressiveness").getDefinitionSet().getBorneSup());
            jSlider_Agressiveness_Min.setValue((int) mainInstitution.getInstitutionalParameters().get("agressiveness").getDefinitionSet().getBorneInf());

            jSpinner_SizeMax.setValue((int) mainInstitution.getInstitutionalParameters().get("size").getDefinitionSet().getBorneSup());
            jSpinner_SizeMin.setValue((int) mainInstitution.getInstitutionalParameters().get("size").getDefinitionSet().getBorneInf());

            jSpinner_MovingFrequencyMax.setValue((int) mainInstitution.getInstitutionalParameters().get("moving").getDefinitionSet().getBorneSup());
            jSpinner_MovingFrequencyMin.setValue((int) mainInstitution.getInstitutionalParameters().get("moving").getDefinitionSet().getBorneInf());

            jSpinner_NbChildrenMax.setValue((int) mainInstitution.getInstitutionalParameters().get("nbChildren").getDefinitionSet().getBorneSup());
            jSpinner_NbChildrenMin.setValue((int) mainInstitution.getInstitutionalParameters().get("nbChildren").getDefinitionSet().getBorneInf());


            jTextField_SpeciesName.setText(tempNorm.getName());
            jComboBox_SpeciesChoice.addItem(tempNorm);
            jComboBox_SpeciesSelection.addItem(tempNorm);
            jComboBox_SpeciesChoice.setSelectedItem(tempNorm);

        } catch (Exception ex) {
            Logger.getLogger(IHM.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton_AddSpeciesActionPerformed

    private void update_SpeciesDescription(Norm completedNorm) throws Exception {
        //Be careful of the order !!!
        jSlider_Agressiveness_Max.setValue((int) completedNorm.getInstitutionalParameters().get("agressiveness").getDefinitionSet().getBorneSup());
        jSlider_Agressiveness_Min.setValue((int) completedNorm.getInstitutionalParameters().get("agressiveness").getDefinitionSet().getBorneInf());

        jSpinner_SizeMax.setValue((int) completedNorm.getInstitutionalParameters().get("size").getDefinitionSet().getBorneSup());
        jSpinner_SizeMin.setValue((int) completedNorm.getInstitutionalParameters().get("size").getDefinitionSet().getBorneInf());

        jSpinner_MovingFrequencyMax.setValue((int) completedNorm.getInstitutionalParameters().get("moving").getDefinitionSet().getBorneSup());
        jSpinner_MovingFrequencyMin.setValue((int) completedNorm.getInstitutionalParameters().get("moving").getDefinitionSet().getBorneInf());

        jSpinner_NbChildrenMax.setValue((int) completedNorm.getInstitutionalParameters().get("nbChildren").getDefinitionSet().getBorneSup());
        jSpinner_NbChildrenMin.setValue((int) completedNorm.getInstitutionalParameters().get("nbChildren").getDefinitionSet().getBorneInf());



        jTextField_SpeciesName.setText(completedNorm.getName());

    }
    private void jComboBox_SpeciesChoiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox_SpeciesChoiceActionPerformed
        try {

            update_SpeciesDescription((Norm) jComboBox_SpeciesChoice.getSelectedItem());
        } catch (Exception ex) {
            Logger.getLogger(IHM.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jComboBox_SpeciesChoiceActionPerformed

    private void jTextField_SpeciesNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_SpeciesNameActionPerformed

        Norm tempNorm = (Norm) jComboBox_SpeciesChoice.getSelectedItem();

        String name = jTextField_SpeciesName.getText();

        //Protection
        if (name.equals("Institution")) {
            name = "DefaultName";
        }
        tempNorm.setName(name);
        jComboBox_SpeciesSelection.removeAllItems();
        jComboBox_SpeciesChoice.removeAllItems();
        for (Norm norm : norms) {
            jComboBox_SpeciesChoice.addItem(norm);
            jComboBox_SpeciesSelection.addItem(norm);
        }
        jComboBox_SpeciesChoice.setSelectedItem(tempNorm);
    }//GEN-LAST:event_jTextField_SpeciesNameActionPerformed

    private void jSlider_Agressiveness_MinMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jSlider_Agressiveness_MinMouseReleased
        // TODO add your handling code here:
        try {
            Norm norm = (Norm) this.jComboBox_SpeciesChoice.getSelectedItem();


            //Institution Constraints
            double min = mainInstitution.getInstitutionalParameters().get("agressiveness").getDefinitionSet().getBorneInf();
            double max = mainInstitution.getInstitutionalParameters().get("agressiveness").getDefinitionSet().getBorneSup();

            if (jSlider_Agressiveness_Min.getValue() < min) {
                norm.getInstitutionalParameters().get("agressiveness").getDefinitionSet().setBorneInf(min);
                jSlider_Agressiveness_Min.setValue((int) min);
            }

            if (jSlider_Agressiveness_Min.getValue() > max) {
                norm.getInstitutionalParameters().get("agressiveness").getDefinitionSet().setBorneInf(max);
                jSlider_Agressiveness_Min.setValue((int) max);
            }

            //Min Max Constraints
            if (jSlider_Agressiveness_Min.getValue() < jSlider_Agressiveness_Max.getValue()) {
                norm.getInstitutionalParameters().get("agressiveness").getDefinitionSet().setBorneInf(jSlider_Agressiveness_Min.getValue());
            } else {
                norm.getInstitutionalParameters().get("agressiveness").getDefinitionSet().setBorneInf(jSlider_Agressiveness_Max.getValue());
                jSlider_Agressiveness_Min.setValue(jSlider_Agressiveness_Max.getValue());
            }
        } catch (Exception ex) {
            Logger.getLogger(IHM.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jSlider_Agressiveness_MinMouseReleased

    private void jSlider_Agressiveness_MaxMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jSlider_Agressiveness_MaxMouseReleased
        try {
            Norm norm = (Norm) this.jComboBox_SpeciesChoice.getSelectedItem();

            //Institution Constraints
            double min = mainInstitution.getInstitutionalParameters().get("agressiveness").getDefinitionSet().getBorneInf();
            double max = mainInstitution.getInstitutionalParameters().get("agressiveness").getDefinitionSet().getBorneSup();

            if (jSlider_Agressiveness_Max.getValue() < min) {
                norm.getInstitutionalParameters().get("agressiveness").getDefinitionSet().setBorneInf(min);
                jSlider_Agressiveness_Max.setValue((int) min);
            }

            if (jSlider_Agressiveness_Max.getValue() > max) {
                norm.getInstitutionalParameters().get("agressiveness").getDefinitionSet().setBorneInf(max);
                jSlider_Agressiveness_Max.setValue((int) max);
            }

            //Min Max Constraints
            if (jSlider_Agressiveness_Min.getValue() < jSlider_Agressiveness_Max.getValue()) {
                norm.getInstitutionalParameters().get("agressiveness").getDefinitionSet().setBorneSup(jSlider_Agressiveness_Max.getValue());
            } else {
                norm.getInstitutionalParameters().get("agressiveness").getDefinitionSet().setBorneInf(jSlider_Agressiveness_Min.getValue());
                jSlider_Agressiveness_Max.setValue(jSlider_Agressiveness_Min.getValue());

            }
        } catch (Exception ex) {
            Logger.getLogger(IHM.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jSlider_Agressiveness_MaxMouseReleased

    private void jSpinner_NbChildrenMinStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSpinner_NbChildrenMinStateChanged
        try {
            Norm norm = (Norm) this.jComboBox_SpeciesChoice.getSelectedItem();


            //Institution Constraints
            int min = (int) mainInstitution.getInstitutionalParameters().get("nbChildren").getDefinitionSet().getBorneInf();
            int max = (int) mainInstitution.getInstitutionalParameters().get("nbChildren").getDefinitionSet().getBorneSup();

            if (Integer.parseInt(jSpinner_NbChildrenMin.getValue().toString()) < min) {
                norm.getInstitutionalParameters().get("nbChildren").getDefinitionSet().setBorneInf(min);
                jSpinner_NbChildrenMin.setValue((int) min);
            }

            if (Integer.parseInt(jSpinner_NbChildrenMin.getValue().toString()) > max) {
                norm.getInstitutionalParameters().get("nbChildren").getDefinitionSet().setBorneInf(max);
                jSpinner_NbChildrenMin.setValue((int) max);
            }

            //Min Max Constraints
            if (Integer.parseInt(jSpinner_NbChildrenMin.getValue().toString()) < Integer.parseInt(jSpinner_NbChildrenMax.getValue().toString())) {
                //norm.getInstitutionalParameters().get("nbChildren").getDefinitionSet().setBorneSup(Integer.parseInt(jSpinner_NbChildrenMax.getValue().toString()));
                norm.getInstitutionalParameters().get("nbChildren").getDefinitionSet().setBorneInf(Integer.parseInt(jSpinner_NbChildrenMin.getValue().toString()));
            } else {
                norm.getInstitutionalParameters().get("nbChildren").getDefinitionSet().setBorneInf(Integer.parseInt(jSpinner_NbChildrenMax.getValue().toString()));
                jSpinner_NbChildrenMin.setValue(Integer.parseInt(jSpinner_NbChildrenMax.getValue().toString()));

            }
        } catch (Exception ex) {
            Logger.getLogger(IHM.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jSpinner_NbChildrenMinStateChanged

    private void jSpinner_NbChildrenMaxStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSpinner_NbChildrenMaxStateChanged
        try {
            Norm norm = (Norm) this.jComboBox_SpeciesChoice.getSelectedItem();


            //Institution Constraints
            int min = (int) mainInstitution.getInstitutionalParameters().get("nbChildren").getDefinitionSet().getBorneInf();
            int max = (int) mainInstitution.getInstitutionalParameters().get("nbChildren").getDefinitionSet().getBorneSup();

            if (Integer.parseInt(jSpinner_NbChildrenMax.getValue().toString()) < min) {
                norm.getInstitutionalParameters().get("nbChildren").getDefinitionSet().setBorneInf(min);
                jSpinner_NbChildrenMax.setValue((int) min);
            }

            if (Integer.parseInt(jSpinner_NbChildrenMax.getValue().toString()) > max) {
                norm.getInstitutionalParameters().get("nbChildren").getDefinitionSet().setBorneInf(max);
                jSpinner_NbChildrenMax.setValue((int) max);
            }

            //Min Max Constraints
            if (Integer.parseInt(jSpinner_NbChildrenMin.getValue().toString()) < Integer.parseInt(jSpinner_NbChildrenMax.getValue().toString())) {

                norm.getInstitutionalParameters().get("nbChildren").getDefinitionSet().setBorneSup(Integer.parseInt(jSpinner_NbChildrenMax.getValue().toString()));
            } else {
                norm.getInstitutionalParameters().get("nbChildren").getDefinitionSet().setBorneSup(Integer.parseInt(jSpinner_NbChildrenMin.getValue().toString()));
                jSpinner_NbChildrenMax.setValue(Integer.parseInt(jSpinner_NbChildrenMin.getValue().toString()));

            }
        } catch (Exception ex) {
            Logger.getLogger(IHM.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jSpinner_NbChildrenMaxStateChanged

    private void jSpinner_MovingFrequencyMinStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSpinner_MovingFrequencyMinStateChanged
        try {

            Norm norm = (Norm) this.jComboBox_SpeciesChoice.getSelectedItem();


            //Institution Constraints
            int min = (int) mainInstitution.getInstitutionalParameters().get("moving").getDefinitionSet().getBorneInf();
            int max = (int) mainInstitution.getInstitutionalParameters().get("moving").getDefinitionSet().getBorneSup();

            if (Integer.parseInt(jSpinner_MovingFrequencyMin.getValue().toString()) < min) {
                //norm.getInstitutionalParameters().get("moving").getDefinitionSet().setBorneInf(min);
                jSpinner_MovingFrequencyMin.setValue((int) min);
            }

            if (Integer.parseInt(jSpinner_MovingFrequencyMin.getValue().toString()) > max) {
                //norm.getInstitutionalParameters().get("moving").getDefinitionSet().setBorneInf(max);
                jSpinner_MovingFrequencyMin.setValue((int) max);
            }

            //Min Max Constraints
            if (Integer.parseInt(jSpinner_MovingFrequencyMin.getValue().toString()) < Integer.parseInt(jSpinner_MovingFrequencyMax.getValue().toString())) {

                norm.getInstitutionalParameters().get("moving").getDefinitionSet().setBorneInf(Integer.parseInt(jSpinner_MovingFrequencyMin.getValue().toString()));
            } else {
                norm.getInstitutionalParameters().get("moving").getDefinitionSet().setBorneSup(Integer.parseInt(jSpinner_MovingFrequencyMax.getValue().toString()));
                jSpinner_MovingFrequencyMin.setValue(Integer.parseInt(jSpinner_MovingFrequencyMax.getValue().toString()));

            }
        } catch (Exception ex) {
            Logger.getLogger(IHM.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jSpinner_MovingFrequencyMinStateChanged

    private void jSpinner_MovingFrequencyMaxStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSpinner_MovingFrequencyMaxStateChanged
        try {
            Norm norm = (Norm) this.jComboBox_SpeciesChoice.getSelectedItem();


            //Institution Constraints
            int min = (int) mainInstitution.getInstitutionalParameters().get("moving").getDefinitionSet().getBorneInf();
            int max = (int) mainInstitution.getInstitutionalParameters().get("moving").getDefinitionSet().getBorneSup();
            System.out.println("Blala" + jSpinner_MovingFrequencyMax.getValue());
            if (Integer.parseInt(jSpinner_MovingFrequencyMax.getValue().toString()) < min) {
                //norm.getInstitutionalParameters().get("moving").getDefinitionSet().setBorneSup(min);

                jSpinner_MovingFrequencyMax.setValue((int) min);
            }

            if (Integer.parseInt(jSpinner_MovingFrequencyMax.getValue().toString()) > max) {
                //norm.getInstitutionalParameters().get("moving").getDefinitionSet().setBorneSup(max);
                jSpinner_MovingFrequencyMax.setValue((int) max);
            }

            //Min Max Constraints
            if (Integer.parseInt(jSpinner_MovingFrequencyMin.getValue().toString()) < Integer.parseInt(jSpinner_MovingFrequencyMax.getValue().toString())) {

                norm.getInstitutionalParameters().get("moving").getDefinitionSet().setBorneSup(Integer.parseInt(jSpinner_MovingFrequencyMax.getValue().toString()));
            } else {

                norm.getInstitutionalParameters().get("moving").getDefinitionSet().setBorneSup(Integer.parseInt(jSpinner_MovingFrequencyMin.getValue().toString()));
                jSpinner_MovingFrequencyMax.setValue(Integer.parseInt(jSpinner_MovingFrequencyMin.getValue().toString()));

            }
        } catch (Exception ex) {
            Logger.getLogger(IHM.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jSpinner_MovingFrequencyMaxStateChanged

    private void jSpinner_SizeMinStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSpinner_SizeMinStateChanged
        try {

            Norm norm = (Norm) this.jComboBox_SpeciesChoice.getSelectedItem();


            //Institution Constraints
            int min = (int) mainInstitution.getInstitutionalParameters().get("size").getDefinitionSet().getBorneInf();
            int max = (int) mainInstitution.getInstitutionalParameters().get("size").getDefinitionSet().getBorneSup();

            if (Integer.parseInt(jSpinner_SizeMin.getValue().toString()) < min) {
                //norm.getInstitutionalParameters().get("size").getDefinitionSet().setBorneSup(min);
                jSpinner_SizeMin.setValue((int) min);
            }

            if (Integer.parseInt(jSpinner_SizeMin.getValue().toString()) > max) {
                //norm.getInstitutionalParameters().get("size").getDefinitionSet().setBorneSup(max);
                jSpinner_SizeMin.setValue((int) max);
            }

            //Min Max Constraints
            if (Integer.parseInt(jSpinner_SizeMin.getValue().toString()) < Integer.parseInt(jSpinner_SizeMax.getValue().toString())) {

                norm.getInstitutionalParameters().get("size").getDefinitionSet().setBorneInf(Integer.parseInt(jSpinner_SizeMin.getValue().toString()));
            } else {
                norm.getInstitutionalParameters().get("size").getDefinitionSet().setBorneInf(Integer.parseInt(jSpinner_SizeMax.getValue().toString()));
                jSpinner_SizeMin.setValue(Integer.parseInt(jSpinner_SizeMax.getValue().toString()));

            }
        } catch (Exception ex) {
            Logger.getLogger(IHM.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jSpinner_SizeMinStateChanged

    private void jSpinner_SizeMaxStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSpinner_SizeMaxStateChanged
        try {

            Norm norm = (Norm) this.jComboBox_SpeciesChoice.getSelectedItem();


            //Institution Constraints
            int min = (int) mainInstitution.getInstitutionalParameters().get("size").getDefinitionSet().getBorneInf();
            int max = (int) mainInstitution.getInstitutionalParameters().get("size").getDefinitionSet().getBorneSup();

            if (Integer.parseInt(jSpinner_SizeMax.getValue().toString()) < min) {
                //norm.getInstitutionalParameters().get("size").getDefinitionSet().setBorneSup(min);
                jSpinner_SizeMax.setValue((int) min);
            }

            if (Integer.parseInt(jSpinner_SizeMax.getValue().toString()) > max) {
                //norm.getInstitutionalParameters().get("size").getDefinitionSet().setBorneSup(max);
                jSpinner_SizeMax.setValue((int) max);
            }

            //Min Max Constraints
            if (Integer.parseInt(jSpinner_SizeMin.getValue().toString()) < Integer.parseInt(jSpinner_SizeMax.getValue().toString())) {

                norm.getInstitutionalParameters().get("size").getDefinitionSet().setBorneSup(Integer.parseInt(jSpinner_SizeMax.getValue().toString()));
            } else {
                norm.getInstitutionalParameters().get("size").getDefinitionSet().setBorneSup(Integer.parseInt(jSpinner_SizeMin.getValue().toString()));
                jSpinner_SizeMax.setValue(Integer.parseInt(jSpinner_SizeMin.getValue().toString()));

            }
        } catch (Exception ex) {
            Logger.getLogger(IHM.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jSpinner_SizeMaxStateChanged

    private void jTabbedPane_IHMStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jTabbedPane_IHMStateChanged
        updateTree();
        updateStatisticsTable();
        updateTable();


    }//GEN-LAST:event_jTabbedPane_IHMStateChanged

    private void jTree_PopValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_jTree_PopValueChanged

        DefaultMutableTreeNode node = (DefaultMutableTreeNode) jTree_Pop.getLastSelectedPathComponent();

        if (node == null) //Nothing is selected.
        {
            return;
        }

        Object nodeInfo = node.getUserObject();

        if (nodeInfo instanceof Agent) {
            Agent agentTemp = (Agent) nodeInfo;
            jTextField_Desc_Agressiveness.setText(Integer.toString((int) agentTemp.getBehavior().getParameters().get("agressiveness").getValue()));
            jSpinner_DescMovingFrequency.setValue((int) agentTemp.getBehavior().getParameters().get("moving").getValue());
            jSpinner_DescNbChildren.setValue((int) agentTemp.getBehavior().getParameters().get("nbChildren").getValue());
            jSpinner_DescSize.setValue((int) agentTemp.getBehavior().getParameters().get("size").getValue());
            jCheckBoxViolatingDesc.setSelected(agentTemp.getBehavior().isViolating());
        }

    }//GEN-LAST:event_jTree_PopValueChanged

    private void jRadioButtonAddToPopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonAddToPopActionPerformed
        jRadioButton_ReplacePop.setSelected(false);
    }//GEN-LAST:event_jRadioButtonAddToPopActionPerformed

    private void jButton_GeneratePopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_GeneratePopActionPerformed

        if (jRadioButton_ReplacePop.isSelected()) {
            clearGrid();
        }

        int totalRow = jTable1.getRowCount();

        //Test if the total Proportion is equals to 100.
        double totalProportion = 0.0;

        for (int i = 0; i < totalRow; i++) {
            totalProportion = totalProportion + Double.parseDouble(jTable1.getValueAt(i, 1).toString());
        }
        if (totalProportion != 100.0) {
            return;
        }

        //Test if the violation rates are valid.
        boolean violationValidator = true;
        for (int i = 0; i < totalRow; i++) {
            if (Double.parseDouble(jTable1.getValueAt(i, 2).toString()) > 100.0) {
                violationValidator = false;
            }
        }
        if (!violationValidator) {
            return;
        }

        //Generate the population
        double proportion = 0.0;
        double violationRate;
        Norm norme;
        int totalPop = Integer.parseInt(jTextField_TotalPop.getText());
        int pop;
        int violatingPop = 0;

        int compteurPop = 0;
        int idBigPop = 0;

        for (int i = 0; i < totalRow; i++) {
            norme = (Norm) jTable1.getValueAt(i, 0);

            //To find who has the biggest proportion.
            double tempProp = Double.parseDouble(jTable1.getValueAt(i, 1).toString());
            if (tempProp > proportion) {
                idBigPop = i;
            }
            proportion = tempProp;

            violationRate = Double.parseDouble(jTable1.getValueAt(i, 2).toString());

            pop = (int) (proportion / 100 * totalPop);

            violatingPop = (int) (pop * violationRate / 100);

            //Generate Violating Population
            for (int j = 0; j < violatingPop; j++) {
                try {
                    compteurPop = compteurPop + 1;
                    Agent agent = new Agent(behaviorGenerator.generateViolatingBehavior(norme, 0.33), behaviorGenerator, environment);
                    environment.addNewAgent(environment.getFreePlace(), agent);
                } catch (Exception ex) {
                    Logger.getLogger(IHM.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            //Generate Normal Population
            for (int j = 0; j < pop - violatingPop; j++) {
                try {
                    compteurPop = compteurPop + 1;
                    Agent agent = new Agent(behaviorGenerator.generateBehavior(norme), behaviorGenerator, environment);
                    environment.addNewAgent(environment.getFreePlace(), agent);
                } catch (Exception ex) {
                    Logger.getLogger(IHM.class.getName()).log(Level.SEVERE, null, ex);
                }
            }



        }

        if (compteurPop != totalPop) {
            int nbAgentToAdd = totalPop - compteurPop;
            norme = (Norm) jTable1.getValueAt(idBigPop, 0);
            for (int k = 0; k < nbAgentToAdd; k++) {
                Agent agent;
                try {
                    agent = new Agent(behaviorGenerator.generateBehavior(norme), behaviorGenerator, environment);
                    environment.addNewAgent(environment.getFreePlace(), agent);
                } catch (Exception ex) {
                    Logger.getLogger(IHM.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
        updateStatisticsTable();
    }//GEN-LAST:event_jButton_GeneratePopActionPerformed

    private void jButton_AddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_AddActionPerformed
        try {
            // TODO add your handling code here:
            Agent agent = new Agent(tempAgent);
            environment.addNewAgent(environment.getFreePlace(), agent);
            updateStatisticsTable();
        } catch (Exception ex) {
            Logger.getLogger(IHM.class.getName()).log(Level.SEVERE, null, ex);
        }

}//GEN-LAST:event_jButton_AddActionPerformed

    private void jSpinner_SizeAddAgentStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSpinner_SizeAddAgentStateChanged
        try {
            Norm norm = null;

            double value = Double.parseDouble(jSpinner_SizeAddAgent.getValue().toString());

            if (jRadioButton_InstitutionMethod.isSelected() || jCheckBox_ViolatingAgent.isSelected()) {
                norm = normInstitution;
            } else {

                norm = (Norm) this.jComboBox_SpeciesSelection.getSelectedItem();
            }

            double inf = norm.getInstitutionalParameters().get("size").getDefinitionSet().getBorneInf();
            double sup = norm.getInstitutionalParameters().get("size").getDefinitionSet().getBorneSup();
            if (value > sup) {
                this.jSpinner_SizeAddAgent.setValue((int) sup);
            }
            if (value < inf) {
                this.jSpinner_SizeAddAgent.setValue((int) inf);
            }


            value = Double.parseDouble(jSpinner_SizeAddAgent.getValue().toString());
            tempAgent.getBehavior().getParameters().get("moving").setValue(value);

        } catch (Exception ex) {
            Logger.getLogger(IHM.class.getName()).log(Level.SEVERE, null, ex);
        }
}//GEN-LAST:event_jSpinner_SizeAddAgentStateChanged

    private void jSpinner_MovingFrequencyAddAgentStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSpinner_MovingFrequencyAddAgentStateChanged
        try {
            Norm norm = null;

            double value = Double.parseDouble(jSpinner_MovingFrequencyAddAgent.getValue().toString());

            if (jRadioButton_InstitutionMethod.isSelected() || jCheckBox_ViolatingAgent.isSelected()) {
                norm = normInstitution;
            } else {


                norm = (Norm) this.jComboBox_SpeciesSelection.getSelectedItem();
            }

            double inf = norm.getInstitutionalParameters().get("moving").getDefinitionSet().getBorneInf();
            double sup = norm.getInstitutionalParameters().get("moving").getDefinitionSet().getBorneSup();
            if (value > sup) {
                this.jSpinner_MovingFrequencyAddAgent.setValue((int) sup);
            }
            if (value < inf) {
                this.jSpinner_MovingFrequencyAddAgent.setValue((int) inf);
            }

            value = Double.parseDouble(jSpinner_MovingFrequencyAddAgent.getValue().toString());
            tempAgent.getBehavior().getParameters().get("moving").setValue(value);

        } catch (Exception ex) {
            Logger.getLogger(IHM.class.getName()).log(Level.SEVERE, null, ex);
        }
}//GEN-LAST:event_jSpinner_MovingFrequencyAddAgentStateChanged

    private void jSpinner_NbChildrenAddAgentStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSpinner_NbChildrenAddAgentStateChanged
        try {
            Norm norm = null;

            double value = Double.parseDouble(jSpinner_NbChildrenAddAgent.getValue().toString());

            if (jRadioButton_InstitutionMethod.isSelected() || jCheckBox_ViolatingAgent.isSelected()) {
                norm = normInstitution;
            } else {
                norm = (Norm) this.jComboBox_SpeciesSelection.getSelectedItem();
            }



            double inf = norm.getInstitutionalParameters().get("nbChildren").getDefinitionSet().getBorneInf();
            double sup = norm.getInstitutionalParameters().get("nbChildren").getDefinitionSet().getBorneSup();
            if (value > sup) {
                this.jSpinner_NbChildrenAddAgent.setValue((int) sup);
            }
            if (value < inf) {
                this.jSpinner_NbChildrenAddAgent.setValue((int) inf);
            }

            value = Double.parseDouble(jSpinner_NbChildrenAddAgent.getValue().toString());
            tempAgent.getBehavior().getParameters().get("nbChildren").setValue(value);

        } catch (Exception ex) {
            Logger.getLogger(IHM.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jSpinner_NbChildrenAddAgentStateChanged

    private void jTextField_AgressivenessAddAgentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_AgressivenessAddAgentActionPerformed
        try {
            Norm norm = null;

            double value = Double.valueOf(this.jTextField_AgressivenessAddAgent.getText());

            if (jRadioButton_InstitutionMethod.isSelected() || jCheckBox_ViolatingAgent.isSelected()) {
                norm = normInstitution;
            } else {
                norm = (Norm) this.jComboBox_SpeciesSelection.getSelectedItem();
            }


            double inf = norm.getInstitutionalParameters().get("agressiveness").getDefinitionSet().getBorneInf();
            double sup = norm.getInstitutionalParameters().get("agressiveness").getDefinitionSet().getBorneSup();
            if (value > sup) {
                this.jTextField_AgressivenessAddAgent.setText(Integer.toString((int) sup));
            }
            if (value < inf) {
                this.jTextField_AgressivenessAddAgent.setText(Integer.toString((int) inf));
            }

            value = Double.valueOf(this.jTextField_AgressivenessAddAgent.getText());

            tempAgent.getBehavior().getParameters().get("agressiveness").setValue(value);


        } catch (Exception ex) {
            Logger.getLogger(IHM.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jTextField_AgressivenessAddAgentActionPerformed

    private void jButton_GenerateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_GenerateActionPerformed
        try {

            Agent newAgent = null;

            if (jRadioButton_SpeciesMethod.isSelected()) {
                if (jCheckBox_ViolatingAgent.isSelected()) {

                    newAgent = new Agent(behaviorGenerator.generateViolatingBehavior((Norm) this.jComboBox_SpeciesSelection.getSelectedItem(), 0.40), behaviorGenerator, this.environment);
                } else {
                    newAgent = new Agent(behaviorGenerator.generateBehavior((Norm) this.jComboBox_SpeciesSelection.getSelectedItem()), behaviorGenerator, this.environment);
                }
            } else {

                newAgent = new Agent(behaviorGenerator.generateBehavior(normInstitution), behaviorGenerator, environment);
            }

            tempAgent = newAgent;

            this.jTextField_AgressivenessAddAgent.setText(String.valueOf((int) newAgent.getBehavior().getParameters().get("agressiveness").getValue()));
            this.jSpinner_MovingFrequencyAddAgent.setValue((int) newAgent.getBehavior().getParameters().get("moving").getValue());
            this.jSpinner_NbChildrenAddAgent.setValue((int) newAgent.getBehavior().getParameters().get("nbChildren").getValue());
            this.jSpinner_SizeAddAgent.setValue((int) newAgent.getBehavior().getParameters().get("size").getValue());



            jTextField_AgressivenessAddAgent.setEditable(true);
            jSpinner_NbChildrenAddAgent.setEnabled(true);
            jSpinner_MovingFrequencyAddAgent.setEnabled(true);
            jSpinner_SizeAddAgent.setEnabled(true);
            jButton_Add.setEnabled(true);
        } catch (Exception ex) {
            Logger.getLogger(IHM.class.getName()).log(Level.SEVERE, null, ex);
        }
}//GEN-LAST:event_jButton_GenerateActionPerformed

    private void jCheckBox_ViolatingAgentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox_ViolatingAgentActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_jCheckBox_ViolatingAgentActionPerformed

    private void jComboBox_SpeciesSelectionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox_SpeciesSelectionActionPerformed
        jButton_Add.setEnabled(false);
}//GEN-LAST:event_jComboBox_SpeciesSelectionActionPerformed

    private void jRadioButton_SpeciesMethodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton_SpeciesMethodActionPerformed
        // TODO add your handling code here:
        jRadioButton_InstitutionMethod.setSelected(false);
        jRadioButton_SpeciesMethod.setSelected(true);
        jCheckBox_ViolatingAgent.setEnabled(true);
}//GEN-LAST:event_jRadioButton_SpeciesMethodActionPerformed

    private void jRadioButton_InstitutionMethodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton_InstitutionMethodActionPerformed
        jRadioButton_SpeciesMethod.setSelected(false);
        jRadioButton_InstitutionMethod.setSelected(true);
        jButton_Add.setEnabled(false);
        jCheckBox_ViolatingAgent.setEnabled(false);
}//GEN-LAST:event_jRadioButton_InstitutionMethodActionPerformed

    private void jCheckBoxViolatingDescActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxViolatingDescActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_jCheckBoxViolatingDescActionPerformed

    private void jSliderDeterminismStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSliderDeterminismStateChanged
        double rate = Double.valueOf(jSliderDeterminism.getValue()) / 100;
        mainInstitution.setSigma(rate);
    }//GEN-LAST:event_jSliderDeterminismStateChanged

    private void jTextField_Desc_AgressivenessActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_Desc_AgressivenessActionPerformed
        try {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) jTree_Pop.getLastSelectedPathComponent();
            Agent agentTemp = null;
            double value;

            if (node == null) {
                return;
            }
            Object nodeInfo = node.getUserObject();
            if (nodeInfo instanceof Agent) {
                agentTemp = (Agent) nodeInfo;
            } else {
                return;
            }
            value = Double.valueOf(this.jTextField_Desc_Agressiveness.getText());
            double inf = agentTemp.getBehavior().getParameters().get("agressiveness").getFatherNormParameter().getDefinitionSet().getBorneInf();
            double sup = agentTemp.getBehavior().getParameters().get("agressiveness").getFatherNormParameter().getDefinitionSet().getBorneSup();
            if (value > sup) {
                this.jTextField_Desc_Agressiveness.setText(Integer.toString((int) sup));
            }
            if (value < inf) {
                this.jTextField_Desc_Agressiveness.setText(Integer.toString((int) inf));
            }

            value = Double.valueOf(this.jTextField_Desc_Agressiveness.getText());
            agentTemp.getBehavior().getParameters().get("agressiveness").setValue(value);

        } catch (Exception ex) {
            Logger.getLogger(IHM.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jTextField_Desc_AgressivenessActionPerformed

    private void jSpinner_DescNbChildrenStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSpinner_DescNbChildrenStateChanged
        try {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) jTree_Pop.getLastSelectedPathComponent();
            Agent agentTemp = null;
            double value = Double.parseDouble(jSpinner_DescNbChildren.getValue().toString());

            if (node == null) {
                return;
            }
            Object nodeInfo = node.getUserObject();
            if (nodeInfo instanceof Agent) {
                agentTemp = (Agent) nodeInfo;
            } else {
                return;
            }
            value = Double.parseDouble(jSpinner_DescNbChildren.getValue().toString());

            double inf = agentTemp.getBehavior().getParameters().get("nbChildren").getFatherNormParameter().getDefinitionSet().getBorneInf();
            double sup = agentTemp.getBehavior().getParameters().get("nbChildren").getFatherNormParameter().getDefinitionSet().getBorneSup();

            if (value > sup) {
                this.jSpinner_DescNbChildren.setValue((int) sup);

            }
            if (value < inf) {
                this.jSpinner_DescNbChildren.setValue((int) inf);

            }

            value = Double.parseDouble(jSpinner_DescNbChildren.getValue().toString());

            agentTemp.getBehavior().getParameters().get("nbChildren").setValue(value);
        } catch (Exception ex) {
            Logger.getLogger(IHM.class.getName()).log(Level.SEVERE, null, ex);
        }


    }//GEN-LAST:event_jSpinner_DescNbChildrenStateChanged

    private void jSpinner_DescMovingFrequencyStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSpinner_DescMovingFrequencyStateChanged
        try {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) jTree_Pop.getLastSelectedPathComponent();
            Agent agentTemp = null;
            double value = Double.parseDouble(jSpinner_DescMovingFrequency.getValue().toString());

            if (node == null) {
                return;
            }
            Object nodeInfo = node.getUserObject();
            if (nodeInfo instanceof Agent) {
                agentTemp = (Agent) nodeInfo;
            } else {
                return;
            }
            value = Double.parseDouble(jSpinner_DescMovingFrequency.getValue().toString());
            double inf = agentTemp.getBehavior().getParameters().get("moving").getFatherNormParameter().getDefinitionSet().getBorneInf();
            double sup = agentTemp.getBehavior().getParameters().get("moving").getFatherNormParameter().getDefinitionSet().getBorneSup();
            if (value > sup) {
                this.jSpinner_DescMovingFrequency.setValue((int) sup);
            }
            if (value < inf) {
                this.jSpinner_DescMovingFrequency.setValue((int) inf);
            }

            value = Double.parseDouble(jSpinner_DescMovingFrequency.getValue().toString());
            agentTemp.getBehavior().getParameters().get("moving").setValue(value);
        } catch (Exception ex) {
            Logger.getLogger(IHM.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jSpinner_DescMovingFrequencyStateChanged

    private void jSpinner_DescSizeStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSpinner_DescSizeStateChanged
        try {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) jTree_Pop.getLastSelectedPathComponent();
            Agent agentTemp = null;
            double value = Double.parseDouble(jSpinner_DescSize.getValue().toString());

            if (node == null) {
                return;
            }
            Object nodeInfo = node.getUserObject();
            if (nodeInfo instanceof Agent) {
                agentTemp = (Agent) nodeInfo;
            } else {
                return;
            }
            value = Double.parseDouble(jSpinner_DescSize.getValue().toString());
            double inf = agentTemp.getBehavior().getParameters().get("size").getFatherNormParameter().getDefinitionSet().getBorneInf();
            double sup = agentTemp.getBehavior().getParameters().get("size").getFatherNormParameter().getDefinitionSet().getBorneSup();
            if (value > sup) {
                this.jSpinner_DescSize.setValue((int) sup);
            }
            if (value < inf) {
                this.jSpinner_DescSize.setValue((int) inf);
            }

            value = Double.parseDouble(jSpinner_DescSize.getValue().toString());
            agentTemp.getBehavior().getParameters().get("size").setValue(value);
        } catch (Exception ex) {
            Logger.getLogger(IHM.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jSpinner_DescSizeStateChanged

    private void jButtonRemoveSpeciesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRemoveSpeciesActionPerformed
        Norm tempNorm = (Norm) jComboBox_SpeciesChoice.getSelectedItem();

        HashMap<Coordinates, Agent> tempGrid;
        tempGrid = new HashMap<Coordinates, Agent>(environment.getGrid());

        for (Entry<Coordinates, Agent> entry : tempGrid.entrySet()) {
            if (entry.getValue().getBehavior().getNormMother().getName().equals(tempNorm.getName())) {
                environment.getGrid().remove(entry.getKey());

            }

            norms.remove(tempNorm);
        }

        jComboBox_SpeciesSelection.removeAllItems();
        jComboBox_SpeciesChoice.removeAllItems();
        for (Norm norm : norms) {
            if (!norm.getName().equals("Institution")) {
                jComboBox_SpeciesChoice.addItem(norm);
                jComboBox_SpeciesSelection.addItem(norm);
            }

        }
        jComboBox_SpeciesChoice.setSelectedItem(tempNorm);
    }//GEN-LAST:event_jButtonRemoveSpeciesActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButtonRemoveSpecies;
    private javax.swing.JButton jButton_Add;
    private javax.swing.JButton jButton_AddSpecies;
    private javax.swing.JButton jButton_Generate;
    private javax.swing.JButton jButton_GeneratePop;
    private javax.swing.JButton jButton_Pause;
    private javax.swing.JButton jButton_Start;
    private javax.swing.JButton jButton_Stop;
    private javax.swing.JCheckBox jCheckBoxViolatingDesc;
    private javax.swing.JCheckBox jCheckBox_ViolatingAgent;
    private javax.swing.JComboBox jComboBox_SpeciesChoice;
    private javax.swing.JComboBox jComboBox_SpeciesSelection;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel_Min1;
    private javax.swing.JLabel jLabel_Min10;
    private javax.swing.JLabel jLabel_Min11;
    private javax.swing.JLabel jLabel_Min12;
    private javax.swing.JLabel jLabel_Min13;
    private javax.swing.JLabel jLabel_Min14;
    private javax.swing.JLabel jLabel_Min15;
    private javax.swing.JLabel jLabel_Min16;
    private javax.swing.JLabel jLabel_Min17;
    private javax.swing.JLabel jLabel_Min18;
    private javax.swing.JLabel jLabel_Min2;
    private javax.swing.JLabel jLabel_Min3;
    private javax.swing.JLabel jLabel_Min4;
    private javax.swing.JLabel jLabel_Min5;
    private javax.swing.JLabel jLabel_Min6;
    private javax.swing.JLabel jLabel_Min7;
    private javax.swing.JLabel jLabel_Min8;
    private javax.swing.JLabel jLabel_Min9;
    private javax.swing.JLabel jLabel_Name1;
    private javax.swing.JLabel jLabel_SimulationSpeed;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel_AgentCharacteristics;
    private javax.swing.JPanel jPanel_BehavioralParameters;
    private javax.swing.JPanel jPanel_CreatureEdition;
    private javax.swing.JPanel jPanel_GenerationMethod;
    private javax.swing.JPanel jPanel_IndividualCharacteristics;
    private javax.swing.JPanel jPanel_Institution;
    private javax.swing.JPanel jPanel_PhysicalParameters;
    private javax.swing.JPanel jPanel_Population;
    private javax.swing.JPanel jPanel_SimulationBoard;
    private javax.swing.JPanel jPanel_SimulationControls;
    private javax.swing.JPanel jPanel_SimulationPanel;
    private javax.swing.JPanel jPanel_Species;
    private javax.swing.JPanel jPanel_SpeciesDescription;
    private javax.swing.JPanel jPanel_SpeciesList;
    private javax.swing.JRadioButton jRadioButtonAddToPop;
    private javax.swing.JRadioButton jRadioButton_InstitutionMethod;
    private javax.swing.JRadioButton jRadioButton_ReplacePop;
    private javax.swing.JRadioButton jRadioButton_SpeciesMethod;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane_Population1;
    private javax.swing.JScrollPane jScrollPane_Species;
    private javax.swing.JSlider jSliderDeterminism;
    private javax.swing.JSlider jSlider_Agressiveness_Max;
    private javax.swing.JSlider jSlider_Agressiveness_Min;
    private javax.swing.JSlider jSlider_SimulationSpeed;
    private javax.swing.JSpinner jSpinner_DescMovingFrequency;
    private javax.swing.JSpinner jSpinner_DescNbChildren;
    private javax.swing.JSpinner jSpinner_DescSize;
    private javax.swing.JSpinner jSpinner_MovingFrequencyAddAgent;
    private javax.swing.JSpinner jSpinner_MovingFrequencyMax;
    private javax.swing.JSpinner jSpinner_MovingFrequencyMin;
    private javax.swing.JSpinner jSpinner_NbChildrenAddAgent;
    private javax.swing.JSpinner jSpinner_NbChildrenMax;
    private javax.swing.JSpinner jSpinner_NbChildrenMin;
    private javax.swing.JSpinner jSpinner_SizeAddAgent;
    private javax.swing.JSpinner jSpinner_SizeMax;
    private javax.swing.JSpinner jSpinner_SizeMin;
    private javax.swing.JTabbedPane jTabbedPane_IHM;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTableInstitutionDesc;
    private javax.swing.JTable jTable_Statistics;
    private javax.swing.JTextArea jTextAreaLog;
    private javax.swing.JTextField jTextField_AgressivenessAddAgent;
    private javax.swing.JTextField jTextField_Desc_Agressiveness;
    private javax.swing.JTextField jTextField_SpeciesName;
    private javax.swing.JTextField jTextField_TotalPop;
    private javax.swing.JTree jTree_Pop;
    private java.awt.Label label5;
    private java.awt.Label label6;
    private java.awt.Label label8;
    private java.awt.Label label9;
    private java.awt.Label label_Agressiveness;
    private java.awt.Label label_Children;
    private java.awt.Label label_Desc_Moving;
    private java.awt.Label label_Desc_Size;
    private java.awt.Label label_Desc_Size1;
    private java.awt.Label label_Desc_children;
    private java.awt.Label label_Moving;
    private java.awt.Label label_Size;
    private java.awt.Label label_desc_Agressiveness1;
    private java.awt.Label label_desc_Agressiveness2;
    private java.awt.Label label_ind_Agressiveness;
    private java.awt.Label label_ind_Moving;
    private java.awt.Label label_ind_Size;
    private java.awt.Label label_ind_children;
    // End of variables declaration//GEN-END:variables
}
