package com.cisdiinfo.exercise;

/**
 * 代理学习<br>
 * 
 * @author Leckie
 * @date 2014年10月25日
 */
public class RobotProxy {
	public static void main(String[] args) {

		if (args == null || args.length < 1) {
			System.out.println("--Please Input your userName--");
			return;
		}
		String userName = args[ 0 ];
		String password = "670B14728AD9902AECBA32E22FA4F6BD"; // 000000
		Robot leckie = new Robot();
		try {
			leckie.study(userName, password);// 开始学习
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}
}
