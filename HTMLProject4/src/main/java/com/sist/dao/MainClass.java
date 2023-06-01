package com.sist.dao;

public class MainClass {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			for(int i =1 ; i<=10 ; i++)
			try {
				int a = (int)(Math.random()*3);
				int result = i/a;
				System.out.println(i+" "+result+" "+a);

			} catch (Exception e) {
				// TODO: handle exception
				System.err.println("0발생");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}
