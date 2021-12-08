/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mthree.guessthenumber.service;

/**
 *
 * @author john
 */
public class GameFinishedException extends Exception {

    public GameFinishedException(String message) {
        super(message);
    }

    public GameFinishedException(String message, Throwable cause) {
        super(message, cause);
    }
}
