package projetoAv1;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.Scanner;

public class Trabalho {
public static int Nmeses = 0;
public static int Nfornecedores = 0;
	public static void main(String[] args) throws IOException {
		String diretorio = System.getProperty("user.dir") + "\\ProjetoUm.txt";
		System.out.println(diretorio);

		Scanner in = new Scanner(new FileReader(diretorio));
		String[] oneLineString = null;
		String oneline = in.nextLine();
		oneLineString = oneline.split(" ");
		Nmeses =  converteInt(oneLineString[0]);
		Nfornecedores = converteInt(oneLineString[1]);
	
		
		int quantLinhas = (((1+Nmeses)*Nmeses)/2)* Nfornecedores; 

		Contrato[] contratos = new Contrato[quantLinhas];
		lerArquivo(diretorio,contratos,in);

		double matriz[][][] = new double[Nfornecedores+1][Nmeses+1][Nmeses+1];
		matriz[0][0][0] = Double.MAX_VALUE;

		substituiValoresNaMatriz(contratos, matriz);

		//System.out.println("Contrato com o menor valor: " + menorValorContratoV(contratos));
		//System.out.println("Contrato com o menor valor: " + menorValorContratoM(matriz));
		//System.out.println(menorValorContratoIndividualCompleto(matriz,3)); // item D
		//System.out.println(menorValorContratoIndividualIF(matriz,2));  // item E
	}

	private static void substituiValoresNaMatriz(Contrato[] contratos, double[][][] matriz) {
		for (Contrato i : contratos) {
			matriz[i.getFornecedor()][i.getMesInicio()][i.getMesFim()] = i.getValor();
		}
	}


	private static void lerArquivo(String diretorio,Contrato[] contratos,Scanner in) throws FileNotFoundException {
	
		String[] string = null;
		int i = 0;

		while (in.hasNextLine()) {
			String line = in.nextLine();
			string = line.split(" ");
			
			contratos[i] = new Contrato(converteInt(string[0]), converteInt(string[1]), converteInt(string[2]),
					converteDouble(string[3]));
			
			i++;
			
		}

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

	private static Contrato menorValorContratoV(Contrato[] contratos) {
		Contrato contrato = contratos[0];
		for (Contrato i : contratos) {
			if (i.getValor() < contrato.getValor())
				contrato = i;
		}
		return contrato;
	}

	private static String menorValorContratoM(double[][][] matriz) {
		double menorValor = matriz[0][0][0];
		int fornecedor = 0, mesInicio = 0, mesFinal = 0;
		
		for (int i = 1;i < matriz.length; i++) {
			for (int j = 1; j < matriz[i].length; j++) {
				if(matriz[i][j][j] < menorValor) {
					menorValor = matriz[i][j][j];
					fornecedor = i;
					mesInicio = j;
					mesFinal = j;
				}
				
			}
		}
		return "Contrato [fornecedor=" + fornecedor + ", mesInicio=" + mesInicio + ", mesFim=" + mesFinal + ", valor="
				+ menorValor + "]";
	}
	private static String menorValorContratoIndividualCompleto(double[][][]matriz,int m) {
		double menorValor = matriz[0][0][0];
		int j = 1;
		int k = m;
		int fornecedor = 0, mesInicio = 0, mesFinal = 0;
		for(int i = 1;i<matriz.length;i++) {
			if(matriz[i][j][k] < menorValor) {
				menorValor = matriz[i][j][k];
				fornecedor = i;
				mesInicio = j;
				mesFinal = k;
			}
			
		}
		return "Contrato [fornecedor=" + fornecedor + ", mesInicio=" + mesInicio + ", mesFim=" + mesFinal + ", valor="
		+ menorValor + "]";
	}
	private static String menorValorContratoIndividualIF(double[][][]matriz,int m) {
		double menorValor = matriz[0][0][0];
		int j = m;
		int k = m;
		int fornecedor = 0, mesInicio = 0, mesFinal = 0;
		for(int i = 1;i<matriz.length;i++) {
			if(matriz[i][j][k] < menorValor) {
				menorValor = matriz[i][j][k];
				fornecedor = i;
				mesInicio = j;
				mesFinal = k;
			}
			
		}
		return "Contrato [fornecedor=" + fornecedor + ", mesInicio=" + mesInicio + ", mesFim=" + mesFinal + ", valor="
		+ menorValor + "]";
	}
}
