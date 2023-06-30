package com.sist.controller;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(METHOD)
/*   @ => type
 *  class class_name{
 *      @ -> Field
 *  	private A a;
 *  
 *  	@ -> constructor
 *  	public class_name(){
 *  		
 *  	}
 *   	@ -> method
 *  	public void display(){
 *  	
 *  	}
 *  	@RequestMapping("list.do")
 *  	public void display1(){
 *  	
 *  	}
 *  	@RequestMapping
 *  	public void display2(){
 *  	
 *  	}
 *  	@RequestMapping
 *  	public void display3(){
 *  	
 *  	}
 *  	@RequestMapping
 *  	public void display4(){
 *  	
 *  	}
 *  	@RequestMapping
 *  	public void display5(){
 *  	
 *  	}
 *  }
 */
public @interface RequestMapping {
	// @RequestMapping("") 안에 구분자를 String으로 쓴다는 뜻..
	public String value();
}
