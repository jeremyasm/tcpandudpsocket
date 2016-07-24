package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ReadFromSystemIn {

	public static void main(String[] args) throws IOException{
		BufferedReader buffReader = new BufferedReader(new InputStreamReader(System.in));
		String whatITypedIn = buffReader.readLine();
		System.out.println(whatITypedIn);
	}
}
