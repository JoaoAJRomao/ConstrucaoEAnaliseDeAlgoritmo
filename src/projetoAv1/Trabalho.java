package projetoAv1;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.Scanner;

public class Trabalho {
	public static void main(String[] args) throws IOException {
		String diretorio = System.getProperty("user.dir") + "\\ProjetoUm.txt";
		System.out.println(diretorio);

		Contrato[] contratos = new Contrato[contaLinhas(diretorio)];

		lerArquivo(diretorio, contratos);

		double matriz[][][] = new double[contaLinhas(diretorio)]
										[retornaMaiorMes(contratos, tipoMes.INICIO)]
										[retornaMaiorMes(contratos, tipoMes.FIM)];

		for (Contrato i : contratos) {
			matriz[i.getFornecedor() - 1][i.getMesInicio() - 1][i.getMesFim() - 1] = i.getValor();
		}

		System.out.println("Contrato com menor valor de mercado (vetor): " + retornaContratoDeMenorValor(contratos));
		
//		imprimirMatriz(matriz);
	}

	private static void imprimirMatriz(double[][][] matriz) {
		for (int i = 0; i < matriz.length; i++) {
			for (int j = 0; j < matriz[i].length; j++) {
				for (int k = 0; k < matriz[i][j].length; k++) {
					System.out.printf("%.1f\t",matriz[i][j][k]);
				}
				System.out.println();
			}
		}

	}

	private static Contrato retornaContratoDeMenorValor(Contrato[] contratos) {
		Contrato maisBarato = contratos[0];
		for (Contrato c : contratos) {
			if (c.getValor() < maisBarato.getValor())
				maisBarato = c;
		}
		return maisBarato;
	}

	private static void lerArquivo(String diretorio, Contrato[] contrato) throws FileNotFoundException {
		Scanner in = new Scanner(new FileReader(diretorio));
		String[] string = null;
		int i = 0;
		while (in.hasNextLine()) {
			String line = in.nextLine();
			string = line.split(" ");
			contrato[i] = new Contrato(converteInt(string[0]), converteInt(string[1]), converteInt(string[2]),
					converteDouble(string[3]));
			i++;
		}

	}

	@SuppressWarnings("unused")
	private static int contaLinhas(String diretorio) throws IOException {
		@SuppressWarnings("resource")
		LineNumberReader lineCounter = new LineNumberReader(new InputStreamReader(new FileInputStream(diretorio)));
		String nextLine = null;
		while ((nextLine = lineCounter.readLine()) != null) {
			if (nextLine == null)
				break;
		}
		System.out.println("Total number of line in this file " + lineCounter.getLineNumber());
		return lineCounter.getLineNumber();
	}

	/**
	 * Passe como parametro o tipo[mesInicio|mesFim] desejado
	 * 
	 * @param contratos
	 * @param tipo
	 * @return
	 */
	private static int retornaMaiorMes(Contrato[] contratos, tipoMes tipo) {
		int maiorValor = 0;
		switch (tipo) {
		case INICIO:
			for (Contrato v : contratos) {
				if (v.getMesInicio() > maiorValor)
					maiorValor = v.getMesInicio();
			}
			break;

		case FIM:
			for (Contrato v : contratos) {
				if (v.getMesFim() > maiorValor)
					maiorValor = v.getMesFim();
			}
			break;

		default:
			System.out.println("Parametro inv�lido!");
			break;
		}

		return maiorValor;
	}

	private static int converteInt(String string) {
		return Integer.parseInt(string);
	}

	private static double converteDouble(String string) {
		return Double.parseDouble(string);
	}

	enum tipoMes {
		INICIO, FIM
	}
}
