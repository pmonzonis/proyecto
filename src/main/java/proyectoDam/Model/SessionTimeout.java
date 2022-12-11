/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectoDam.Model;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 *
 * @author Paula Monzonis Fortea
 */
@WebListener
class SessionTimeout implements HttpSessionListener {

 @Override
 public void sessionCreated(HttpSessionEvent event) {
  event.getSession().setMaxInactiveInterval(60*5);
 }

 @Override
 public void sessionDestroyed(HttpSessionEvent se) {

 }
}
