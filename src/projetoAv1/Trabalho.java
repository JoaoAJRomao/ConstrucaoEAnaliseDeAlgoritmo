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

		double matriz[][][] = new double[retornaMaior(contratos, tipo.Forn) + 1][retornaMaior(contratos, tipo.MesINICIO)
				+ 1][retornaMaior(contratos, tipo.MesFIM) + 1];

		for (Contrato i : contratos) {
			System.out.println(i.toString());
			matriz[i.getFornecedor()][i.getMesInicio()][i.getMesFim()] = i.getValor();
		}

		System.out.println("Contrato com o menor valor: " + menorValorContrato(contratos));

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
	private static int retornaMaior(Contrato[] contratos, tipo tipo) {
		int maiorValor = 0;
		switch (tipo) {
		case MesINICIO:
			for (Contrato v : contratos) {
				if (v.getMesInicio() > maiorValor)
					maiorValor = v.getMesInicio();
			}
			break;

		case MesFIM:
			for (Contrato v : contratos) {
				if (v.getMesFim() > maiorValor)
					maiorValor = v.getMesFim();
			}
			break;

		case Forn:
			for (Contrato v : contratos) {
				if (v.getFornecedor() > maiorValor) {
					maiorValor = v.getFornecedor();
				}
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

	enum tipo {
		MesINICIO, MesFIM, Forn
	}

	private static Contrato menorValorContrato(Contrato[] contratos) {
		Contrato contrato = contratos[0];
		for (Contrato i : contratos) {
			if (i.getValor() < contrato.getValor())
				contrato = i;
		}
		return contrato;
	}

}
