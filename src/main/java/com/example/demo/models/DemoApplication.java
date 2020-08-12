package com.example.demo.models;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.Scanner;
import java.util.IllegalFormatException;

//@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {// SpringApplication.run(DemoApplication.class, args);

	Scanner ler = new Scanner(System.in);

	float num1 = 0,num2 = 0;
	int operacao = -1;

		try {
			System.out.println("Entre com o valor do primeiro número");
			num1 = ler.nextFloat();
			System.out.println("Entre com o valor do segundo número");
			num2 = ler.nextFloat();
		}catch(Exception exception){
			System.err.println("A ENTRADA INFORMADA NAO E UM NUMERO");
			System.exit(1);
		}

		while(operacao != 0){
		System.out.println("Selecione oque deseja fazer: \n 1 - Soma \n 2 - Subtracao \n 3 - Multiplicacao \n 4 - Divisão \n 5 - Sair");
		operacao = ler.nextInt();
		switch(operacao) {
			case 1:
				System.out.println(num1+num2);
				break;
			case 2:
				System.out.println(num1-num2);
				break;
			case 3:
				System.out.println(num1*num2);
				break;
			case 4:
				System.out.println(num1/num2);
				break;
			case 5:
				System.exit(0);
			 }
		}

	}

}
