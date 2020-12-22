/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eycr.utilities;

import java.util.Scanner;

/**
 *
 * @author firem
 */
public class IOops {
    Scanner sc;
    public IOops()
    {
        sc=new Scanner(System.in);
    }
    public Integer askForToken()
    {
        System.out.println("Give me a token please. If you dont have put -1");
        return sc.nextInt();
    }
    public String askForString()
    {
        System.out.println("Give a string ");
        return sc.next();
    }
    
}