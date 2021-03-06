/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eycr.calculadora;

import com.eycr.automaton.AFD;
import com.eycr.automaton.AFN;
import com.eycr.automaton.Converter;
import com.eycr.lexic.LexicAnalyzer;
import com.eycr.utilities.Special;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;
import javax.swing.JOptionPane;

/**
 *
 
 */
public class guiCalculadora extends javax.swing.JFrame {
    private HashMap<Integer,AFN> afns; //HASH de AFNS
    private LexicAnalyzer lexic;
    private sintaxCalculatorPost calc;
    String cadena;
    int numPar_I,numPar_D;
    Stack<String> cadenasTecleadas;
    private AFD n;
    /**
     * Creates new form guiCalculadora
     */
    public guiCalculadora() {
        initComponents();
        afns=new HashMap<>();
        initReglas();
        cadena = "";
        numPar_I = 0;
        numPar_D = 0;
        cadenasTecleadas = new Stack<String>();
    }
    
    public void initReglas(){
        AFN aux = new AFN();
                
                AFN Plus = new AFN('+'); //     +
                AFN Minus = new AFN('-'); //     -
                AFN Prod = new AFN('*'); //     *
                AFN Div = new AFN('/'); //     /
                AFN Poten = new AFN('^'); //     ^
                AFN ParI = new AFN('('); //     (
                AFN ParD = new AFN(')'); //     )
                AFN NumberFloat = new AFN(); //     numero flotante
                AFN SinA = new AFN(); //     sin
                AFN CosA = new AFN(); //     cos
                AFN TanA = new AFN(); //     tan
                AFN ExpA = new AFN(); //     exp
                AFN LogA = new AFN(); //     log
                AFN LnA = new AFN(); //     ln

                Plus.associateToken(10);                
                Minus.associateToken(20);               
                Prod.associateToken(30);                
                Div.associateToken(40);                
                Poten.associateToken(50);                
                ParI.associateToken(60);                
                ParD.associateToken(70);

                //quito el +/-
                //ParD.createBasic('+');
                //aux.createBasic('-');
                //ParD.unir(aux);
                //ParD.Question();
                //----------------------------------trigonometricas-------------------------------------------
                NumberFloat.createBasic('s');
                aux = new AFN();
                aux.createBasic('i');
                NumberFloat.concatenateAFN(aux);
                aux = new AFN();
                aux.createBasic('n');
                NumberFloat.concatenateAFN(aux);
                NumberFloat.associateToken(80);

                SinA.createBasic('c');
                aux = new AFN();
                aux.createBasic('o');
                SinA.concatenateAFN(aux);
                aux = new AFN();
                aux.createBasic('s');
                SinA.concatenateAFN(aux);
                SinA.associateToken(90);

                CosA.createBasic('t');
                aux = new AFN();
                aux.createBasic('a');
                CosA.concatenateAFN(aux);
                aux = new AFN();
                aux.createBasic('n');
                CosA.concatenateAFN(aux);
                CosA.associateToken(100);

                TanA.createBasic('e');
                aux = new AFN();
                aux.createBasic('x');
                TanA.concatenateAFN(aux);
                aux = new AFN();
                aux.createBasic('p');
                TanA.concatenateAFN(aux);
                TanA.associateToken(110);

                ExpA.createBasic('l');
                aux = new AFN();
                aux.createBasic('o');
                ExpA.concatenateAFN(aux);
                aux = new AFN();
                aux.createBasic('g');
                ExpA.concatenateAFN(aux);
                ExpA.associateToken(120);

                LogA.createBasic('l');
                aux = new AFN();
                aux.createBasic('n');
                LogA.concatenateAFN(aux);
                LogA.associateToken(130);

                LnA = new AFN();
                LnA.createBasic('0', '9');
                LnA.positiveClosure();

                //ParD.concatenateAFN(num1);
                AFN punto = new AFN();
                punto.createBasic('.');

                AFN num1 = new AFN();
                num1.createBasic('0', '9');
                num1.positiveClosure();

                punto.concatenateAFN(num1);
                punto.optional();

                LnA.concatenateAFN(punto);
                LnA.associateToken(140);
                
                
                //unirlos
                afns.put(Plus.getId(),Plus);
                afns.put(Minus.getId(),Minus);
                afns.put(Prod.getId(),Prod);
                afns.put(Div.getId(),Div);
                afns.put(Poten.getId(),Poten);
                afns.put(ParI.getId(),ParI);
                afns.put(ParD.getId(),ParD);
                afns.put(NumberFloat.getId(),NumberFloat);
                afns.put(SinA.getId(),SinA);
                afns.put(CosA.getId(),CosA);
                afns.put(TanA.getId(),TanA);
                afns.put(ExpA.getId(),ExpA);
                afns.put(LogA.getId(),LogA);
                afns.put(LnA.getId(),LnA);
                ArrayList<AFN> automatas = new ArrayList<AFN>();
                                
                Special us = new Special();
                us.unir(afns);
                System.out.println("AFNS: " + afns.toString());
                System.out.println("AFN después de unir: " + Plus.toString());
                //Plus.unirAL(automatas);
                //Plus.toString();
                
                Converter afnConverter = new Converter();                 
                this.n = afnConverter.convertAFN(afns.get(29));
                //int numeroInicial = Plus.getInicial().getIdentificador();
    }
    public String concatenateAFN(String cadConc){
    
        this.cadena = this.cadena + cadConc;
        return this.cadena;
    }
    
    public String borrar(){
        cadena = "";
        numPar_I = 0;
        numPar_D = 0;
        cadenasTecleadas = new Stack<String>();
        return this.cadena;
    }
    
    public String borrarCaracter(){
        
        if(cadenasTecleadas.empty())
            return this.cadena;
        String ultimoTecleado = cadenasTecleadas.pop();
        String aux = this.cadena;
        int tam;
        tam=ultimoTecleado.length();
        this.cadena =  aux.substring(0, aux.length()-tam);
        if(ultimoTecleado == "(")
            numPar_I --;
        if(ultimoTecleado == ")")
            numPar_D --;
        return this.cadena;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jtxt_calculadora = new javax.swing.JTextField();
        num_1 = new javax.swing.JButton();
        num_2 = new javax.swing.JButton();
        num_3 = new javax.swing.JButton();
        mas = new javax.swing.JButton();
        num_9 = new javax.swing.JButton();
        multiplicacion = new javax.swing.JButton();
        num_7 = new javax.swing.JButton();
        num_8 = new javax.swing.JButton();
        igual = new javax.swing.JButton();
        division = new javax.swing.JButton();
        num_0 = new javax.swing.JButton();
        punto = new javax.swing.JButton();
        num_6 = new javax.swing.JButton();
        menos = new javax.swing.JButton();
        num_4 = new javax.swing.JButton();
        num_5 = new javax.swing.JButton();
        sin = new javax.swing.JButton();
        cos = new javax.swing.JButton();
        tan = new javax.swing.JButton();
        log = new javax.swing.JButton();
        ln = new javax.swing.JButton();
        par_I = new javax.swing.JButton();
        par_D = new javax.swing.JButton();
        borrar = new javax.swing.JButton();
        borrar_caracter = new javax.swing.JButton();
        potencia = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jtxt_calculadora.setEditable(false);

        num_1.setText("1");
        num_1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                num_1MouseClicked(evt);
            }
        });

        num_2.setText("2");
        num_2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                num_2MouseClicked(evt);
            }
        });

        num_3.setText("3");
        num_3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                num_3MouseClicked(evt);
            }
        });

        mas.setText("+");
        mas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                masMouseClicked(evt);
            }
        });

        num_9.setText("9");
        num_9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                num_9MouseClicked(evt);
            }
        });

        multiplicacion.setText("*");
        multiplicacion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                multiplicacionMouseClicked(evt);
            }
        });

        num_7.setText("7");
        num_7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                num_7MouseClicked(evt);
            }
        });

        num_8.setText("8");
        num_8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                num_8MouseClicked(evt);
            }
        });

        igual.setText("=");
        igual.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                igualMouseClicked(evt);
            }
        });

        division.setText("/");
        division.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                divisionMouseClicked(evt);
            }
        });

        num_0.setText("0");
        num_0.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                num_0MouseClicked(evt);
            }
        });

        punto.setText(".");
        punto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                puntoActionPerformed(evt);
            }
        });

        num_6.setText("6");
        num_6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                num_6MouseClicked(evt);
            }
        });

        menos.setText("-");
        menos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menosMouseClicked(evt);
            }
        });

        num_4.setText("4");
        num_4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                num_4MouseClicked(evt);
            }
        });

        num_5.setText("5");
        num_5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                num_5MouseClicked(evt);
            }
        });

        sin.setText("sin");
        sin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sinMouseClicked(evt);
            }
        });

        cos.setText("cos");
        cos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cosMouseClicked(evt);
            }
        });

        tan.setText("tan");
        tan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tanMouseClicked(evt);
            }
        });

        log.setText("log");
        log.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                logMouseClicked(evt);
            }
        });

        ln.setText("ln");
        ln.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lnMouseClicked(evt);
            }
        });

        par_I.setText("(");
        par_I.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                par_IMouseClicked(evt);
            }
        });

        par_D.setText(")");
        par_D.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                par_DMouseClicked(evt);
            }
        });

        borrar.setText("AC");
        borrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                borrarMouseClicked(evt);
            }
        });

        borrar_caracter.setText("DELETE");
        borrar_caracter.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                borrar_caracterMouseClicked(evt);
            }
        });

        potencia.setText("^");
        potencia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                potenciaMouseClicked(evt);
            }
        });
        potencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                potenciaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jtxt_calculadora)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(num_1)
                                .addGap(18, 18, 18)
                                .addComponent(num_2)
                                .addGap(18, 18, 18)
                                .addComponent(num_3)
                                .addGap(18, 18, 18)
                                .addComponent(mas))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(num_4)
                                .addGap(18, 18, 18)
                                .addComponent(num_5)
                                .addGap(18, 18, 18)
                                .addComponent(num_6)
                                .addGap(18, 18, 18)
                                .addComponent(menos, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(num_7)
                                .addGap(18, 18, 18)
                                .addComponent(num_8)
                                .addGap(18, 18, 18)
                                .addComponent(num_9)
                                .addGap(18, 18, 18)
                                .addComponent(multiplicacion))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(num_0)
                                .addGap(18, 18, 18)
                                .addComponent(punto)
                                .addGap(18, 18, 18)
                                .addComponent(igual)
                                .addGap(18, 18, 18)
                                .addComponent(division)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(log, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(ln, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(sin, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cos, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(tan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                                .addComponent(potencia)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(par_I, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(par_D)
                                .addGap(18, 18, 18)
                                .addComponent(borrar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(borrar_caracter)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jtxt_calculadora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(num_1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(num_2, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(num_3, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(mas, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(potencia, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(par_I, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(par_D, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(num_4, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(num_5, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(num_6, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(menos, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(num_7, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(num_8, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(num_9, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(multiplicacion, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(num_0, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(punto, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(igual, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(division, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(23, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(borrar)
                            .addComponent(borrar_caracter))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(sin)
                        .addGap(18, 18, 18)
                        .addComponent(cos)
                        .addGap(18, 18, 18)
                        .addComponent(tan)
                        .addGap(18, 18, 18)
                        .addComponent(ln)
                        .addGap(18, 18, 18)
                        .addComponent(log)
                        .addGap(19, 19, 19))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void num_1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_num_1MouseClicked
        jtxt_calculadora.setText(concatenateAFN("1"));
        cadenasTecleadas.push("1");
    }//GEN-LAST:event_num_1MouseClicked

    private void num_2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_num_2MouseClicked
        jtxt_calculadora.setText(concatenateAFN("2"));
        cadenasTecleadas.push("2");
    }//GEN-LAST:event_num_2MouseClicked

    private void num_3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_num_3MouseClicked
        jtxt_calculadora.setText(concatenateAFN("3"));
        cadenasTecleadas.push("3");
    }//GEN-LAST:event_num_3MouseClicked

    private void num_4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_num_4MouseClicked
        jtxt_calculadora.setText(concatenateAFN("4"));
        cadenasTecleadas.push("4");
    }//GEN-LAST:event_num_4MouseClicked

    private void num_5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_num_5MouseClicked
        jtxt_calculadora.setText(concatenateAFN("5"));
        cadenasTecleadas.push("5");
    }//GEN-LAST:event_num_5MouseClicked

    private void num_6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_num_6MouseClicked
        jtxt_calculadora.setText(concatenateAFN("6"));
        cadenasTecleadas.push("6");
    }//GEN-LAST:event_num_6MouseClicked

    private void num_7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_num_7MouseClicked
        jtxt_calculadora.setText(concatenateAFN("7"));
        cadenasTecleadas.push("7");
    }//GEN-LAST:event_num_7MouseClicked

    private void num_8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_num_8MouseClicked
        jtxt_calculadora.setText(concatenateAFN("8"));
        cadenasTecleadas.push("8");
    }//GEN-LAST:event_num_8MouseClicked

    private void num_9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_num_9MouseClicked
        jtxt_calculadora.setText(concatenateAFN("9"));
        cadenasTecleadas.push("9");
    }//GEN-LAST:event_num_9MouseClicked

    private void num_0MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_num_0MouseClicked
        jtxt_calculadora.setText(concatenateAFN("0"));
        cadenasTecleadas.push("0");
    }//GEN-LAST:event_num_0MouseClicked

    private void masMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_masMouseClicked
        jtxt_calculadora.setText(concatenateAFN("+"));
        cadenasTecleadas.push("+");
    }//GEN-LAST:event_masMouseClicked

    private void menosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menosMouseClicked
        jtxt_calculadora.setText(concatenateAFN("-"));
        cadenasTecleadas.push("-");
    }//GEN-LAST:event_menosMouseClicked

    private void multiplicacionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_multiplicacionMouseClicked
        jtxt_calculadora.setText(concatenateAFN("*"));
        cadenasTecleadas.push("*");
    }//GEN-LAST:event_multiplicacionMouseClicked

    private void divisionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_divisionMouseClicked
        jtxt_calculadora.setText(concatenateAFN("/"));
        cadenasTecleadas.push("/");
    }//GEN-LAST:event_divisionMouseClicked

    private void par_IMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_par_IMouseClicked
        jtxt_calculadora.setText(concatenateAFN("("));
        numPar_I ++;
        cadenasTecleadas.push("(");
    }//GEN-LAST:event_par_IMouseClicked

    private void par_DMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_par_DMouseClicked
        jtxt_calculadora.setText(concatenateAFN(")"));
        numPar_D ++;
        cadenasTecleadas.push(")");
    }//GEN-LAST:event_par_DMouseClicked

    private void sinMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sinMouseClicked
        jtxt_calculadora.setText(concatenateAFN("sin"));
        cadenasTecleadas.push("sin");
    }//GEN-LAST:event_sinMouseClicked

    private void cosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cosMouseClicked
        jtxt_calculadora.setText(concatenateAFN("cos"));
        cadenasTecleadas.push("cos");
    }//GEN-LAST:event_cosMouseClicked

    private void tanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tanMouseClicked
        jtxt_calculadora.setText(concatenateAFN("tan"));
        cadenasTecleadas.push("tan");
    }//GEN-LAST:event_tanMouseClicked

    private void lnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lnMouseClicked
        jtxt_calculadora.setText(concatenateAFN("ln"));
        cadenasTecleadas.push("ln");
    }//GEN-LAST:event_lnMouseClicked

    private void logMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logMouseClicked
        jtxt_calculadora.setText(concatenateAFN("log"));
        cadenasTecleadas.push("log");
    }//GEN-LAST:event_logMouseClicked

    private void igualMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_igualMouseClicked
        if(numPar_I != numPar_D){
               if(numPar_I < numPar_D)
               jtxt_calculadora.setText("Mas parentesis que cierran de los que abren");
               else
               jtxt_calculadora.setText("Mas parentesis que abren de los que cierran");
        }
        else{
            //System.out.println("Parentesis correctos");
            String cad = jtxt_calculadora.getText();
            this.lexic = new LexicAnalyzer(cad,n.getTable());
            System.out.println("Cadena del jtxt: " + cad);
            if(cad.equals("")){
                JOptionPane.showMessageDialog(null, "Favor de ingresar una expresión","ERROR",JOptionPane.ERROR_MESSAGE);
            }else{
                float res;
                calc = new sintaxCalculatorPost(n.getTable(),cad);
                res = this.calc.evaluate();
                System.out.println("Resultado: " + res);
                if(res == -1f){
                    jtxt_calculadora.setText("Expresión inválida");
                }else{
                    jtxt_calculadora.setText(Float.toString(res));
                }
            }                
        }
    }//GEN-LAST:event_igualMouseClicked

    private void borrarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_borrarMouseClicked
        jtxt_calculadora.setText(borrar());
    }//GEN-LAST:event_borrarMouseClicked

    private void borrar_caracterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_borrar_caracterMouseClicked
        jtxt_calculadora.setText(borrarCaracter());
    }//GEN-LAST:event_borrar_caracterMouseClicked

    private void puntoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_puntoActionPerformed
        jtxt_calculadora.setText(concatenateAFN("."));
        cadenasTecleadas.push(".");
    }//GEN-LAST:event_puntoActionPerformed

    private void potenciaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_potenciaMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_potenciaMouseClicked

    private void potenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_potenciaActionPerformed
        jtxt_calculadora.setText(concatenateAFN("^"));
        cadenasTecleadas.push("^");
    }//GEN-LAST:event_potenciaActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(guiCalculadora.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(guiCalculadora.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(guiCalculadora.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(guiCalculadora.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new guiCalculadora().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton borrar;
    private javax.swing.JButton borrar_caracter;
    private javax.swing.JButton cos;
    private javax.swing.JButton division;
    private javax.swing.JButton igual;
    private javax.swing.JTextField jtxt_calculadora;
    private javax.swing.JButton ln;
    private javax.swing.JButton log;
    private javax.swing.JButton mas;
    private javax.swing.JButton menos;
    private javax.swing.JButton multiplicacion;
    private javax.swing.JButton num_0;
    private javax.swing.JButton num_1;
    private javax.swing.JButton num_2;
    private javax.swing.JButton num_3;
    private javax.swing.JButton num_4;
    private javax.swing.JButton num_5;
    private javax.swing.JButton num_6;
    private javax.swing.JButton num_7;
    private javax.swing.JButton num_8;
    private javax.swing.JButton num_9;
    private javax.swing.JButton par_D;
    private javax.swing.JButton par_I;
    private javax.swing.JButton potencia;
    private javax.swing.JButton punto;
    private javax.swing.JButton sin;
    private javax.swing.JButton tan;
    // End of variables declaration//GEN-END:variables
}
